package com.core.op.di.components;

import dagger.Component;


import com.core.op.di.modules.ActivityModule;
import com.core.op.di.modules.LoginModule;
import com.core.op.feature.login.LoginActivity;
import com.core.op.lib.di.PerActivity;

import dagger.Component;

@PerActivity
@Component(dependencies = AppComponent.class, modules = {ActivityModule.class, LoginModule.class})
public interface LoginComponent extends ActivityComponent {
    void inject(LoginActivity activity);
}