package com.core.op.di.components;

import dagger.Component;


import com.core.op.di.modules.ActivityModule;
import com.core.op.di.modules.SplashModule;
import com.core.op.feature.splash.SplashActivity;
import com.core.op.lib.di.PerActivity;

import dagger.Component;

@PerActivity
@Component(dependencies = AppComponent.class, modules = {ActivityModule.class, SplashModule.class})
public interface SplashComponent extends ActivityComponent {
    void inject(SplashActivity activity);
}