package com.core.op;

import android.app.Application;

import com.core.op.di.components.AppComponent;
import com.core.op.di.components.DaggerAppComponent;
import com.core.op.di.modules.AppModule;
import com.core.op.lib.AppException;
import com.core.op.lib.utils.CyptoUtil;
import com.core.op.lib.utils.PreferenceUtil;
import com.core.op.utils.AccountUtil;
import com.domain.bean.User;
import com.facebook.stetho.Stetho;
import com.orhanobut.logger.LogLevel;
import com.orhanobut.logger.Logger;

import timber.log.Timber;

/**
 * @author op
 * @version 1.0
 * @description
 * @createDate 2016/8/10
 */
public class MainApplication extends Application {

    private AppComponent appComponent;

    private long loginUid;

    @Override
    public void onCreate() {
        super.onCreate();
        Thread.setDefaultUncaughtExceptionHandler(AppException
                .getAppExceptionHandler(this));

//        LeakCanary.install(this);

        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
            Timber.tag("YTP");
        }
        Stetho.initializeWithDefaults(this);

        Logger.init("YTP")                 // default PRETTYLOGGER or use just init()
                .methodCount(3)                 // default 2
                .hideThreadInfo()               // default shown
                .logLevel(LogLevel.FULL)        // default LogLevel.FULL
                .methodOffset(2);

        appComponent = DaggerAppComponent.builder().appModule(new AppModule(this)).build();

        AccountUtil.init(this);
        initLogin();
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }

    public long getLoginUid() {
        return loginUid;
    }

    private void initLogin() {
        User user = AccountUtil.getUser();
        if (null != user && user.getId() > 0) {
            loginUid = user.getId();
        } else {
            AccountUtil.clearUserCache();
        }
    }

//    public void saveUserInfo(User user) {
//        this.loginUid = user.getId();
//        PreferenceUtil.write(this, "user.uid", String.valueOf(user.getId()));
//        PreferenceUtil.write(this, "user.name", user.getName());
//        PreferenceUtil.write(this, "user.face", user.getPortrait());// 用户头像-文件名
//        PreferenceUtil.write(this, "user.account", user.getAccount());
//        PreferenceUtil.write(this, "user.pwd",
//                CyptoUtil.encode("oschinaApp", user.getPwd()));
//        PreferenceUtil.write(this, "user.location", user.getLocation());
//        PreferenceUtil.write(this, "user.followers", String.valueOf(user.getFollowers()));
//        PreferenceUtil.write(this, "user.fans", String.valueOf(user.getFans()));
//        PreferenceUtil.write(this, "user.score", String.valueOf(user.getScore()));
//        PreferenceUtil.write(this, "user.favoritecount",
//                String.valueOf(user.getFavoritecount()));
//        PreferenceUtil.write(this, "user.gender", String.valueOf(user.getGender()));
//        PreferenceUtil.write(this, "user.isRememberMe",
//                String.valueOf(user.isRememberMe()));// 是否记住我的信息
//    }
//
//
//    /**
//     * 获得登录用户的信息
//     *
//     * @return
//     */
//    public User getLoginUser() {
//        User user = new User();
//        user.setId(Integer.valueOf(PreferenceUtil.readStringWithDefV(this, "user.uid", "0")));
//        user.setName(PreferenceUtil.readString(this, "user.name"));
//        user.setPortrait(PreferenceUtil.readString(this, "user.face"));
//        user.setAccount(PreferenceUtil.readString(this, "user.account"));
//        user.setLocation(PreferenceUtil.readString(this, "user.location"));
//        user.setFollowers(Integer.valueOf(PreferenceUtil.readStringWithDefV(this, "user.followers", "0")));
//        user.setFans(Integer.valueOf(PreferenceUtil.readStringWithDefV(this, "user.fans", "0")));
//        user.setScore(Integer.valueOf(PreferenceUtil.readStringWithDefV(this, "user.score", "0")));
//        user.setFavoritecount(Integer.valueOf(
//                PreferenceUtil.readStringWithDefV(this, "user.favoritecount", "0")));
//        user.setRememberMe(Boolean.valueOf(PreferenceUtil.readString(this, "user.isRememberMe")));
//        user.setGender(PreferenceUtil.readString(this, "user.gender"));
//        return user;
//    }
//
//    /**
//     * 清除登录信息
//     */
//    public void cleanLoginInfo() {
//        this.loginUid = 0;
//        PreferenceUtil.remove(this, "user.uid", "user.name", "user.face", "user.location",
//                "user.followers", "user.fans", "user.score",
//                "user.isRememberMe", "user.gender", "user.favoritecount");
//    }

}
