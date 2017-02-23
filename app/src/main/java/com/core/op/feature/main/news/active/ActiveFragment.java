package com.core.op.feature.main.news.active;

import com.core.op.base.list.BaseListFragment;
import com.core.op.di.components.MainComponent;
import com.core.op.lib.utils.inject.AfterViews;
import com.core.op.lib.utils.inject.BeforeViews;

public final class ActiveFragment extends BaseListFragment<ActiveViewModel> {

    public static ActiveFragment instance() {
        return new ActiveFragment();
    }

    @BeforeViews
    void beforViews() {
        getComponent(MainComponent.class).inject(this);
    }

    @AfterViews
    void afterViews() {
    }
}
