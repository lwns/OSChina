package com.core.op.feature.main.news.info;

import com.core.op.base.list.BaseListFragment;
import com.core.op.di.components.MainComponent;
import com.core.op.lib.utils.inject.AfterViews;
import com.core.op.lib.utils.inject.BeforeViews;

public final class InfoFragment extends BaseListFragment<InfoViewModel> {

    public static InfoFragment instance() {
        return new InfoFragment();
    }

    @BeforeViews
    void beforViews() {
        getComponent(MainComponent.class).inject(this);
    }

    @AfterViews
    void afterViews() {
    }
}
