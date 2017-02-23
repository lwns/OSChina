package com.core.op.di.components;

import dagger.Component;


import com.core.op.di.modules.ActivityModule;
import com.core.op.di.modules.MessageModule;
import com.core.op.feature.message.MessageActivity;
import com.core.op.lib.di.PerActivity;

import dagger.Component;

@PerActivity
@Component(dependencies = AppComponent.class, modules = {ActivityModule.class, MessageModule.class})
public interface MessageComponent extends ActivityComponent {
    void inject(MessageActivity activity);
}