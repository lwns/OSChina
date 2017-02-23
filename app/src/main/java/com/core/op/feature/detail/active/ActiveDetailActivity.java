package com.core.op.feature.detail.active;

import android.content.Context;
import android.content.Intent;

import com.core.op.di.components.ActiveDetailComponent;
import com.core.op.di.components.DaggerActiveDetailComponent;
import com.core.op.di.modules.ActiveDetailModule;
import com.core.op.feature.detail.base.act.DetailActivity;
import com.core.op.feature.detail.blog.BlogDetailActivity;
import com.core.op.lib.di.HasComponent;
import com.core.op.lib.utils.inject.AfterViews;
import com.core.op.lib.utils.inject.BeforeViews;

public final class ActiveDetailActivity extends DetailActivity<ActiveDetailViewModel> implements HasComponent<ActiveDetailComponent> {

    ActiveDetailComponent component;

    public final static void instance(Context context, long id) {
        Intent intent = new Intent(context, ActiveDetailActivity.class);
        intent.putExtra("id", id);
        context.startActivity(intent);
    }

    @BeforeViews
    void beforViews() {
        component = DaggerActiveDetailComponent.builder()
                .appComponent(getApplicationComponent())
                .activityModule(getActivityModule())
                .activeDetailModule(new ActiveDetailModule())
                .build();
        component.inject(this);
    }

    @AfterViews
    void afterViews() {
    }

    @Override
    public ActiveDetailComponent getComponent() {
        return component;
    }
}
