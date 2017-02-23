package com.core.op.di.components;

import dagger.Component;


import com.core.op.di.modules.ActivityModule;
import com.core.op.di.modules.BlogDetailModule;
import com.core.op.feature.detail.blog.BlogDetailActivity;
import com.core.op.feature.detail.blog.frg.BlogDetailFragment;
import com.core.op.lib.di.PerActivity;

import dagger.Component;

@PerActivity
@Component(dependencies = AppComponent.class, modules = {ActivityModule.class, BlogDetailModule.class})
public interface BlogDetailComponent extends ActivityComponent {
    void inject(BlogDetailActivity activity);

    void inject(BlogDetailFragment fragment);

}