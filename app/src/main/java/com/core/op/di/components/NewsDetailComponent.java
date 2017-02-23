package com.core.op.di.components;

import dagger.Component;


import com.core.op.di.modules.ActivityModule;
import com.core.op.di.modules.NewsDetailModule;
import com.core.op.feature.detail.news.NewsDetailActivity;
import com.core.op.feature.detail.news.frg.NewsDetailFragment;
import com.core.op.lib.di.PerActivity;

import dagger.Component;

@PerActivity
@Component(dependencies = AppComponent.class, modules = {ActivityModule.class, NewsDetailModule.class})
public interface NewsDetailComponent extends ActivityComponent {
    void inject(NewsDetailActivity activity);

    void inject(NewsDetailFragment fragment);
}