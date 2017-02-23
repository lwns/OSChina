package com.core.op.di.components;

import dagger.Component;


import com.core.op.di.modules.ActivityModule;
import com.core.op.di.modules.ScanModule;
import com.core.op.feature.scan.ScanActivity;
import com.core.op.lib.di.PerActivity;

import dagger.Component;

@PerActivity
@Component(dependencies = AppComponent.class, modules = {ActivityModule.class, ScanModule.class})
public interface ScanComponent extends ActivityComponent {
    void inject(ScanActivity activity);
}