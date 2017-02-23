package com.core.op.di.components;

import dagger.Component;


import com.core.op.di.modules.ActivityModule;
import com.core.op.di.modules.SoftwareDetailModule;
import com.core.op.feature.detail.software.SoftwareDetailActivity;
import com.core.op.feature.detail.software.frg.SoftwareDetailFragment;
import com.core.op.lib.di.PerActivity;

import dagger.Component;

@PerActivity
@Component(dependencies = AppComponent.class, modules = {ActivityModule.class, SoftwareDetailModule.class})
public interface SoftwareDetailComponent extends ActivityComponent {
    void inject(SoftwareDetailActivity activity);

    void inject(SoftwareDetailFragment fragment);
}