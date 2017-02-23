package com.core.op.feature.detail.software;

import android.content.Context;
import android.content.Intent;

import com.core.op.di.components.DaggerSoftwareDetailComponent;
import com.core.op.di.components.SoftwareDetailComponent;
import com.core.op.di.modules.SoftwareDetailModule;
import com.core.op.feature.detail.base.act.DetailActivity;
import com.core.op.feature.detail.question.QuesDetailActivity;
import com.core.op.lib.di.HasComponent;
import com.core.op.lib.utils.inject.AfterViews;
import com.core.op.lib.utils.inject.BeforeViews;

public final class SoftwareDetailActivity extends DetailActivity<SoftwareDetailViewModel> implements HasComponent<SoftwareDetailComponent> {

    SoftwareDetailComponent component;

    public final static void instance(Context context, long id) {
        Intent intent = new Intent(context, SoftwareDetailActivity.class);
        intent.putExtra("id", id);
        context.startActivity(intent);
    }

    @BeforeViews
    void beforViews() {
        component = DaggerSoftwareDetailComponent.builder()
                .appComponent(getApplicationComponent())
                .activityModule(getActivityModule())
                .softwareDetailModule(new SoftwareDetailModule())
                .build();
        component.inject(this);
    }

    @AfterViews
    void afterViews() {
    }

    @Override
    public SoftwareDetailComponent getComponent() {
        return component;
    }
}
