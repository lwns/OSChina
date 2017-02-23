package com.core.op.feature.detail.blog;

import android.content.Context;
import android.content.Intent;

import com.core.op.di.components.BlogDetailComponent;
import com.core.op.di.components.DaggerBlogDetailComponent;
import com.core.op.di.components.NewsDetailComponent;
import com.core.op.di.modules.BlogDetailModule;
import com.core.op.feature.detail.base.act.DetailActivity;
import com.core.op.lib.di.HasComponent;
import com.core.op.lib.utils.inject.AfterViews;
import com.core.op.lib.utils.inject.BeforeViews;

public final class BlogDetailActivity extends DetailActivity<BlogDetailViewModel> implements HasComponent<BlogDetailComponent> {

    BlogDetailComponent component;

    public final static void instance(Context context, long id) {
        Intent intent = new Intent(context, BlogDetailActivity.class);
        intent.putExtra("id", id);
        context.startActivity(intent);
    }

    @BeforeViews
    void beforViews() {
        component = DaggerBlogDetailComponent.builder()
                .appComponent(getApplicationComponent())
                .activityModule(getActivityModule())
                .blogDetailModule(new BlogDetailModule())
                .build();
        component.inject(this);
    }

    @AfterViews
    void afterViews() {
    }

    @Override
    public BlogDetailComponent getComponent() {
        return component;
    }
}
