package com.core.op.di.modules;

import android.app.Application;
import android.content.Context;

import com.core.op.MainApplication;
import com.core.op.UIThread;
import com.core.op.data.ApiClient;
import com.core.op.data.UrlRoot;
import com.core.op.data.XmlClient;
import com.core.op.data.api.ApiOption;
import com.core.op.data.executor.JobExecutor;
import com.core.op.data.repository.MainRepositoryImp;
import com.core.op.data.repository.UserRepositoryImp;
import com.domain.executor.PostExecutionThread;
import com.domain.executor.ThreadExecutor;
import com.domain.repository.MainRepository;
import com.domain.repository.UserRepository;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Eric on 16/3/22.
 */
@Module
public class AppModule {
    private MainApplication application;

    public AppModule(MainApplication application) {
        this.application = application;
    }

    @Provides
    @Singleton
    MainApplication provideApplicationContext() {
        return application;
    }

    @Provides
    @Singleton
    ApiClient provideApiClient() {
        return ApiOption.Builder.instance(application).url(UrlRoot.API_PATH).build().create(ApiClient.class);
    }

    @Provides
    @Singleton
    XmlClient provideXmlClient() {
        return ApiOption.Builder.instance(application).url(UrlRoot.API_PATH).buildForXml().create(XmlClient.class);
    }

    @Provides
    @Singleton
    ThreadExecutor provideThreadExecutor(JobExecutor jobExecutor) {
        return jobExecutor;
    }

    @Provides
    @Singleton
    PostExecutionThread providePostExecutionThread(UIThread uiThread) {
        return uiThread;
    }

    @Provides
    @Singleton
    MainRepository mainRepository(MainRepositoryImp mainRepository) {
        return mainRepository;
    }

    @Provides
    @Singleton
    UserRepository userRepository(UserRepositoryImp userRepository) {
        return userRepository;
    }
}
