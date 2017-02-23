package com.core.op.di.components;

import dagger.Component;


import com.core.op.di.modules.ActivityModule;
import com.core.op.di.modules.RegisterModule;
import com.core.op.feature.register.RegisterActivity;
import com.core.op.lib.di.PerActivity;

import dagger.Component;

@PerActivity
@Component(dependencies = AppComponent.class, modules = {ActivityModule.class, RegisterModule.class})
public interface RegisterComponent extends ActivityComponent {
    void inject(RegisterActivity activity);
}