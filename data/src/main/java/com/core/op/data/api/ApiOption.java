package com.core.op.data.api;


import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.core.op.data.api.cookie.ClearableCookieJar;
import com.core.op.data.api.cookie.PersistentCookieJar;
import com.core.op.data.api.cookie.cache.SetCookieCache;
import com.core.op.data.api.cookie.persistence.SharedPrefsCookiePersistor;
import com.core.op.data.util.NetUtil;
import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.google.common.base.Strings;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;
import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;

import static android.R.attr.data;

/**
 * @author op
 * @version 1.0
 * @description
 * @createDate 2016/3/4
 */
public class ApiOption {

    private Map<Class, Object> apis;

    protected Retrofit retrofit;

    public ApiOption(Retrofit retrofit) {
        this.retrofit = retrofit;
        apis = new HashMap<>();
    }

    public <T> T create(final Class<T> service) {
//        return (T) Proxy.newProxyInstance(service.getClassLoader(), new Class<?>[]{service},
//                new InvocationHandler() {
//                    @Override
//                    public Object invoke(Object proxy, Method method, Object... args)
//                            throws Throwable {
//                        Object object = apis.get(service);
//                        if (object == null) {
//                            object = retrofit.create(service);
//                            apis.put(service, object);
//                        }
//                        return method.invoke(object, args);
//                    }
//                });
        return retrofit.create(service);
    }

    /**
     * ApiOption 构建器，主要是构建Retrofit对象出来
     */
    public static final class Builder {

        protected Retrofit retrofit;

        protected OkHttpClient client;

        protected String url;

        // 将自身的实例对象设置为一个属性,并加上Static和final修饰符
        private static Builder instance;

        // 静态方法返回该类的实例
        public synchronized static Builder instance(Application application) {
            if (instance == null)
                instance = new Builder(application);
            return instance;
        }

        public Builder(Application application) {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            ClearableCookieJar cookieJar = new PersistentCookieJar(new SetCookieCache(), new SharedPrefsCookiePersistor(application));
            client = new OkHttpClient()
                    .newBuilder()
                    .cookieJar(cookieJar)
                    .addInterceptor(logging)
                    .addInterceptor(new HeadInterceptor(application))
                    .addInterceptor(new CacheInterceptor(application))
//                    .addInterceptor(new CookieInterceptor(application))
                    .addNetworkInterceptor(new StethoInterceptor())
                    .cache(provideCache(application))
                    .build();
        }

        public Builder client(OkHttpClient client) {
            this.client = client;
            return this;
        }

        public Builder url(String url) {
            this.url = url;
            return this;
        }

        public ApiOption build() {
            if (Strings.isNullOrEmpty(url)) {
                throw new RuntimeException("url is null!");
            }
            retrofit = new Retrofit.Builder()
                    .baseUrl(url)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .client(client)
                    .build();
            return new ApiOption(retrofit);
        }

        public ApiOption buildForXml() {
            if (Strings.isNullOrEmpty(url)) {
                throw new RuntimeException("url is null!");
            }
            retrofit = new Retrofit.Builder()
                    .baseUrl(url)
                    .addConverterFactory(SimpleXmlConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .client(client)
                    .build();
            return new ApiOption(retrofit);
        }


        public Cache provideCache(Application application) {
            return new Cache(application.getCacheDir(), 10240 * 1024);
        }
    }

    /**
     * 添加请求头信息
     */
    static class HeadInterceptor implements Interceptor {

        Application application;

        public HeadInterceptor(Application application) {
            this.application = application;
        }

        @Override
        public Response intercept(Chain chain) throws IOException {
            final Request.Builder builder = chain.request().newBuilder();
//            SharedPreferences preferences = application.getSharedPreferences("cookie", Context.MODE_PRIVATE);
//            Observable.just(preferences.getString("cookie", ""))
//                    .subscribe(data -> {
//                        builder.addHeader("Cookie", data);
//                    });
            Request request = builder.build();
            return chain.proceed(request);
        }
    }

    /**
     * 有网请求，没网不读缓存
     */
    static class CacheInterceptor implements Interceptor {
        Application application;

        public CacheInterceptor(Application application) {
            this.application = application;
        }

        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            if (!NetUtil.isNetworkAvailable(application)) {
                request = request.newBuilder().cacheControl(CacheControl.FORCE_CACHE).build();
            }
            Response response = chain.proceed(request);
            if (NetUtil.isNetworkAvailable(application)) {
                int maxAge = 0;
                response.newBuilder().header("Cache-Control", "public, max-age=" + maxAge)
                        .removeHeader("Pragma")
                        .build();
            } else {
                int maxStale = 60 * 60 * 24 * 7;
                response.newBuilder().header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                        .removeHeader("Pragma")
                        .build();
            }
            return response;
        }
    }

    /**
     * 缓存cookie
     */
    static class CookieInterceptor implements Interceptor {
        Application application;

        public CookieInterceptor(Application application) {
            this.application = application;
        }

        @Override
        public Response intercept(Chain chain) throws IOException {
            Response originalResponse = chain.proceed(chain.request());
            //这里获取请求返回的cookie
            if (!originalResponse.headers("Set-Cookie").isEmpty()) {
                final StringBuffer cookieBuffer = new StringBuffer();
                Observable.from(originalResponse.headers("Set-Cookie"))
                        .map(s -> {
                            String[] cookieArray = s.split(";");
                            return cookieArray[0];
                        })
                        .subscribe(cookie -> {
                            cookieBuffer.append(cookie).append(";");
                        });
                SharedPreferences sharedPreferences = application.getSharedPreferences("cookie", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("cookie", cookieBuffer.toString());
                editor.commit();
            }
            return originalResponse;
        }
    }

}
