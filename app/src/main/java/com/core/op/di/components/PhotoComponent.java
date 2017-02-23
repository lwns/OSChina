package com.core.op.di.components;

import dagger.Component;


import com.core.op.di.modules.ActivityModule;
import com.core.op.di.modules.PhotoModule;
import com.core.op.feature.photo.PhotoActivity;
import com.core.op.lib.di.PerActivity;

import dagger.Component;

@PerActivity
@Component(dependencies = AppComponent.class, modules = {ActivityModule.class, PhotoModule.class})
public interface PhotoComponent extends ActivityComponent {
    void inject(PhotoActivity activity);
}