package com.core.op.feature.detail.news;

import android.content.Context;
import android.content.Intent;

import com.core.op.R;
import com.core.op.di.components.DaggerNewsDetailComponent;
import com.core.op.di.components.NewsDetailComponent;
import com.core.op.di.modules.NewsDetailModule;
import com.core.op.feature.detail.base.act.DetailActivity;
import com.core.op.lib.di.HasComponent;
import com.core.op.lib.utils.inject.AfterViews;
import com.core.op.lib.utils.inject.BeforeViews;

public final class NewsDetailActivity extends DetailActivity<NewsDetailViewModel> implements HasComponent<NewsDetailComponent> {

    NewsDetailComponent component;

    public final static void instance(Context context, long id) {
        Intent intent = new Intent(context, NewsDetailActivity.class);
        intent.putExtra("id", id);
        context.startActivity(intent);
    }

    @BeforeViews
    void beforViews() {
        component = DaggerNewsDetailComponent.builder()
                .appComponent(getApplicationComponent())
                .activityModule(getActivityModule())
                .newsDetailModule(new NewsDetailModule())
                .build();
        component.inject(this);
    }

    @AfterViews
    void afterViews() {
    }

    @Override
    public NewsDetailComponent getComponent() {
        return component;
    }
}
