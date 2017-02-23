package com.core.op.feature.login;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.databinding.ObservableField;
import android.os.Bundle;
import android.text.TextUtils;

import com.core.op.AppConfig;
import com.core.op.data.util.CacheUtil;
import com.core.op.MainApplication;
import com.core.op.R;
import com.core.op.data.util.NetUtil;
import com.core.op.databinding.ActLoginBinding;
import com.core.op.lib.base.BAViewModel;
import com.core.op.lib.command.ReplyCommand;
import com.core.op.lib.di.PerActivity;
import com.core.op.lib.utils.AppToast;
import com.core.op.lib.utils.DeviceUtil;
import com.core.op.utils.AccountUtil;
import com.domain.interactor.user.LoginUseCase;
import com.domain.interactor.user.UserInfoUseCase;
import com.domain.interactor.user.UserLoginUseCase;
import com.orhanobut.logger.Logger;
import com.tencent.mm.sdk.modelmsg.SendAuth;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.controller.listener.SocializeListeners;
import com.umeng.socialize.exception.SocializeException;
import com.umeng.socialize.sso.SinaSsoHandler;

import java.util.Map;
import java.util.Set;

import javax.inject.Inject;
import javax.inject.Named;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static android.R.attr.data;
import static android.app.Activity.RESULT_OK;
import static com.core.op.AppConfig.CACHE_USERINFO;

@PerActivity
public class LoginViewModel extends BAViewModel<ActLoginBinding> implements IUiListener {

    private MainApplication application;

    private static final int LOGIN_TYPE_SINA = 1;
    private static final int LOGIN_TYPE_QQ = 2;
    private static final int LOGIN_TYPE_WX = 3;

    public static final int REQUEST_CODE_INIT = 0;//登陆成功发送广播的request code
    private static final String BUNDLE_KEY_REQUEST_CODE = "BUNDLE_KEY_REQUEST_CODE";
    private final int requestCode = REQUEST_CODE_INIT;

    private int loginType;

    public final ReplyCommand wbClick = new ReplyCommand(() -> {
        wbLogin();
    });

    public final ReplyCommand wxClick = new ReplyCommand(() -> {
        wxLogin();
    });

    public final ReplyCommand qqClick = new ReplyCommand(() -> {
        qqLogin();
    });

    public final ReplyCommand loginClick = new ReplyCommand(() -> {
        login();
    });

    BroadcastReceiver receiver;//微信登陆广播接收器

    private UMSocialService mController;//新浪微博服务

    LoginUseCase useCase;

    UserLoginUseCase userLoginUseCase;

    UserInfoUseCase userInfoUseCase;

    public ObservableField<String> userName = new ObservableField<>("");
    public ObservableField<String> password = new ObservableField<>("");

    @Inject
    public LoginViewModel(@Named("LoginUseCase") LoginUseCase useCase,
                          @Named("UserLoginUseCase") UserLoginUseCase userLoginUseCase,
                          @Named("UserInfoUseCase") UserInfoUseCase userInfoUseCase,
                          MainApplication mainApplication,
                          RxAppCompatActivity activity) {
        super(activity);
        this.application = mainApplication;
        this.userInfoUseCase = userInfoUseCase;
        this.useCase = useCase;
        this.userLoginUseCase = userLoginUseCase;
    }

    @Override
    public void afterViews() {
    }


    @Override
    public void onComplete(Object o) {
        openIdLogin(AppConfig.QQ, o.toString());
    }

    @Override
    public void onError(UiError uiError) {
    }

    @Override
    public void onCancel() {
    }

    private void wbLogin() {
        if (mController == null)
            mController = UMServiceFactory.getUMSocialService("com.umeng.login");
        loginType = LOGIN_TYPE_SINA;
        SinaSsoHandler sinaSsoHandler = new SinaSsoHandler();
        mController.getConfig().setSsoHandler(sinaSsoHandler);
        mController.doOauthVerify(activity, SHARE_MEDIA.SINA,
                new SocializeListeners.UMAuthListener() {

                    @Override
                    public void onStart(SHARE_MEDIA arg0) {
                    }

                    @Override
                    public void onError(SocializeException arg0,
                                        SHARE_MEDIA arg1) {
                        AppToast.show(activity, "新浪授权失败");
                    }

                    @Override
                    public void onComplete(Bundle value, SHARE_MEDIA arg1) {
                        if (value != null && !TextUtils.isEmpty(value.getString("uid"))) {
                            // 获取平台信息
                            mController.getPlatformInfo(activity, SHARE_MEDIA.SINA, new SocializeListeners.UMDataListener() {
                                @Override
                                public void onStart() {

                                }

                                @Override
                                public void onComplete(int i, Map<String, Object> map) {
                                    if (i == 200 && map != null) {
                                        StringBuilder sb = new StringBuilder("{");
                                        Set<String> keys = map.keySet();
                                        int index = 0;
                                        for (String key : keys) {
                                            index++;
                                            String jsonKey = key;
                                            if (jsonKey.equals("uid")) {
                                                jsonKey = "openid";
                                            }
                                            sb.append(String.format("\"%s\":\"%s\"", jsonKey, map.get(key).toString()));
                                            if (index != map.size()) {
                                                sb.append(",");
                                            }
                                        }
                                        sb.append("}");
                                        openIdLogin(AppConfig.WEIBO, sb.toString());
                                    } else {
                                        AppToast.show(activity, "发生错误：" + i);
                                    }
                                }
                            });
                        } else {
                            AppToast.show(activity, "授权失败");
                        }
                    }

                    @Override
                    public void onCancel(SHARE_MEDIA arg0) {
                        AppToast.show(activity, "已取消新浪登陆");
                    }
                });
    }

    private void qqLogin() {
        loginType = LOGIN_TYPE_QQ;
        Tencent mTencent = Tencent.createInstance(AppConfig.APP_QQ_KEY,
                activity);
        mTencent.login(activity, "all", this);
    }

    private void wxLogin() {
        loginType = LOGIN_TYPE_WX;
        IWXAPI api = WXAPIFactory.createWXAPI(activity, AppConfig.WEICHAT_APPID, false);
        api.registerApp(AppConfig.WEICHAT_APPID);

        if (!api.isWXAppInstalled()) {
            AppToast.show(activity, "手机中没有安装微信客户端");
            return;
        }
        // 唤起微信登录授权
        final SendAuth.Req req = new SendAuth.Req();
        req.scope = "snsapi_userinfo";
        req.state = "wechat_login";
        api.sendReq(req);
        // 注册一个广播，监听微信的获取openid返回（类：WXEntryActivity中）
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(AppConfig.WECHAT);
        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent != null) {
                    String openid_info = intent.getStringExtra(AppConfig.BUNDLE_KEY_OPENIDINFO);
                    openIdLogin(AppConfig.WECHAT, openid_info);
                    // 注销这个监听广播
                    if (receiver != null) {
                        activity.unregisterReceiver(receiver);
                    }
                }
            }
        };
        activity.registerReceiver(receiver, intentFilter);
    }

    /***
     * @param catalog    第三方登录的类别
     * @param openIdInfo 第三方的信息
     */
    private void openIdLogin(final String catalog, final String openIdInfo) {
        useCase.cleanParams();
        useCase.setParams(catalog, openIdInfo);
        useCase.execute().compose(activity.bindToLifecycle())
                .subscribe(data -> {
                    Logger.i(data.getResult().getErrorMessage() + "：" + data.getResult().getErrorCode());

                    if (data.getResult().OK()) {
//                        handleLoginBean(loginUserBean, headers);

                        AccountUtil.updateUserCache(data.getUser());
                    } else {
                        // 前往绑定或者注册操作
//                        Intent intent = new Intent(activity, LoginBindActivityChooseActivity.class);
//                        intent.putExtra(LoginBindActivityChooseActivity.BUNDLE_KEY_CATALOG, catalog);
//                        intent.putExtra(LoginBindActivityChooseActivity.BUNDLE_KEY_OPENIDINFO, openIdInfo);
//                        startActivityForResult(intent, REQUEST_CODE_OPENID);
                    }
                }, error -> {
                    Logger.e(error, "info execute error");
                });
    }

    private void login() {
        if (prepareForLogin()) {
            return;
        }
        userLoginUseCase.setParams(userName.get(), password.get(), "1");
        userLoginUseCase.executeNoSchedule()
                .subscribeOn(Schedulers.io())
                .compose(activity.bindToLifecycle())
                .doOnNext(data -> {
                    data.getUser().setAccount(userName.get());
                    data.getUser().setPwd(password.get());
                    data.getUser().setRememberMe(true);
//                    application.saveUserInfo(data.getUser());
                    AccountUtil.updateUserCache(data.getUser());
                })
                .flatMap(data ->
                        userInfoUseCase.execute().compose(activity.bindToLifecycle())
                )
                .doOnNext(data -> {
                    CacheUtil.saveObject(activity, data, CACHE_USERINFO);
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(d -> {
                    Intent intent = new Intent();
                    intent.putExtra(BUNDLE_KEY_REQUEST_CODE, requestCode);
                    activity.setResult(RESULT_OK, intent);
                    activity.sendBroadcast(new Intent(AppConfig.INTENT_ACTION_USER_CHANGE));
                    DeviceUtil.hideSoftKeyboard(activity, activity.getWindow().getDecorView());
                    activity.finish();
                }, e -> {
                    Logger.i(e.getMessage());
                });
    }

    private boolean prepareForLogin() {
        if (!NetUtil.isNetworkAvailable(activity)) {
            AppToast.show(activity, activity.getString(R.string.app_no_network));
            return true;
        }
        if (userName.get().length() == 0) {
            binding.etUsername.setError("请输入邮箱/用户名");
            binding.etUsername.requestFocus();
            return true;
        }

        if (password.get().length() == 0) {
            binding.etPassword.setError("请输入密码");
            binding.etPassword.requestFocus();
            return true;
        }
        return false;
    }
}