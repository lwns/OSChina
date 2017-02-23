package com.core.op.di.components;

import dagger.Component;


import com.core.op.di.modules.ActivityModule;
import com.core.op.di.modules.ActiveDetailModule;
import com.core.op.feature.detail.active.ActiveDetailActivity;
import com.core.op.feature.detail.active.frg.ActiveDetailFragment;
import com.core.op.lib.di.PerActivity;

import dagger.Component;

@PerActivity
@Component(dependencies = AppComponent.class, modules = {ActivityModule.class, ActiveDetailModule.class})
public interface ActiveDetailComponent extends ActivityComponent {

    void inject(ActiveDetailActivity activity);

    void inject(ActiveDetailFragment fragment);
}