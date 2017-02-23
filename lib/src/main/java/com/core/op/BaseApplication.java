package com.core.op;

import android.app.Application;

import com.core.op.lib.AppException;
import com.core.op.lib.BuildConfig;

import timber.log.Timber;

/**
 * @author op
 * @version 1.0
 * @description
 * @createDate 2016/8/10
 */
public class BaseApplication extends Application {

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
    }
}
