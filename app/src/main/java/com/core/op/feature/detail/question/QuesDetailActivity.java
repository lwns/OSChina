package com.core.op.feature.detail.question;

import android.content.Context;
import android.content.Intent;

import com.core.op.di.components.DaggerQuesDetailComponent;
import com.core.op.di.components.QuesDetailComponent;
import com.core.op.di.modules.QuesDetailModule;
import com.core.op.feature.detail.base.act.DetailActivity;
import com.core.op.lib.di.HasComponent;
import com.core.op.lib.utils.inject.AfterViews;
import com.core.op.lib.utils.inject.BeforeViews;

public final class QuesDetailActivity extends DetailActivity<QuesDetailViewModel> implements HasComponent<QuesDetailComponent> {

    QuesDetailComponent component;

    public final static void instance(Context context, long id) {
        Intent intent = new Intent(context, QuesDetailActivity.class);
        intent.putExtra("id", id);
        context.startActivity(intent);
    }

    @BeforeViews
    void beforViews() {
        component = DaggerQuesDetailComponent.builder()
                .appComponent(getApplicationComponent())
                .activityModule(getActivityModule())
                .quesDetailModule(new QuesDetailModule())
                .build();
        component.inject(this);
    }

    @AfterViews
    void afterViews() {
    }

    @Override
    public QuesDetailComponent getComponent() {
        return component;
    }
}
