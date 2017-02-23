package com.core.op.feature.splash;

import android.content.Context;
import android.content.Intent;

import com.core.op.R;
import com.core.op.base.BaseActivity;
import com.core.op.databinding.ActSplashBinding;
import com.core.op.di.components.DaggerSplashComponent;
import com.core.op.di.components.SplashComponent;
import com.core.op.di.modules.SplashModule;
import com.core.op.lib.utils.inject.AfterViews;
import com.core.op.lib.utils.inject.BeforeViews;
import com.core.op.lib.utils.inject.RootView;

@RootView(R.layout.act_splash)
public final class SplashActivity extends BaseActivity<SplashViewModel, ActSplashBinding> {

    SplashComponent component;

    public final static void instance(Context context) {
        context.startActivity(new Intent(context, SplashActivity.class));
    }

    @BeforeViews
    void beforViews() {
        component = DaggerSplashComponent.builder()
                .appComponent(getApplicationComponent())
                .activityModule(getActivityModule())
                .splashModule(new SplashModule())
                .build();
        component.inject(this);
    }

    @AfterViews
    void afterViews() {
    }


}
