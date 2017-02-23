package com.core.op.di.components;

import dagger.Component;


import com.core.op.di.modules.ActivityModule;
import com.core.op.di.modules.QuesDetailModule;
import com.core.op.feature.detail.question.QuesDetailActivity;
import com.core.op.feature.detail.question.frg.QuesDetailFragment;
import com.core.op.lib.di.PerActivity;

import dagger.Component;

@PerActivity
@Component(dependencies = AppComponent.class, modules = {ActivityModule.class, QuesDetailModule.class})
public interface QuesDetailComponent extends ActivityComponent {
    void inject(QuesDetailActivity activity);

    void inject(QuesDetailFragment fragment);
}