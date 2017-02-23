package com.core.op.feature.back.active;

import com.core.op.base.list.BaseListFragment;
import com.core.op.di.components.SimpleBackComponent;
import com.core.op.feature.main.news.active.ActiveViewModel;
import com.core.op.lib.utils.inject.AfterViews;
import com.core.op.lib.utils.inject.BeforeViews;

public final class ActiveFragment extends BaseListFragment<ActiveViewModel> {

    public static ActiveFragment instance() {
        return new ActiveFragment();
    }

    @BeforeViews
    void beforViews() {
        getComponent(SimpleBackComponent.class).inject(this);
    }

    @AfterViews
    void afterViews() {
    }
}
