package com.core.op.feature.splash;


import com.core.op.databinding.ActSplashBinding;
import com.core.op.feature.main.MainActivity;
import com.core.op.lib.base.BAViewModel;
import com.core.op.lib.di.PerActivity;
import com.core.op.lib.utils.DeviceUtil;
import com.core.op.lib.utils.PreferenceUtil;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import rx.Observable;

@PerActivity
public class SplashViewModel extends BAViewModel<ActSplashBinding> {

    @Inject
    public SplashViewModel(RxAppCompatActivity activity) {
        super(activity);
    }

    @Override
    public void afterViews() {
        Observable.timer(1, TimeUnit.SECONDS).subscribe(data -> {
            MainActivity.instance(activity);
            activity.finish();
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        int cacheVersion = PreferenceUtil.readInt(activity, "first_install",
                "first_install", -1);
        int currentVersion = DeviceUtil.getVersionCode(activity);
        if (cacheVersion < currentVersion) {
            PreferenceUtil.write(activity, "first_install", "first_install",
                    currentVersion);
        }
    }
}