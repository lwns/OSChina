package com.core.op.di.components;

import dagger.Component;


import com.core.op.di.modules.ActivityModule;
import com.core.op.di.modules.TweetDetailModule;
import com.core.op.feature.detail.tweet.TweetDetailActivity;
import com.core.op.lib.di.PerActivity;

import dagger.Component;

@PerActivity
@Component(dependencies = AppComponent.class, modules = {ActivityModule.class, TweetDetailModule.class})
public interface TweetDetailComponent extends ActivityComponent {
    void inject(TweetDetailActivity activity);
}