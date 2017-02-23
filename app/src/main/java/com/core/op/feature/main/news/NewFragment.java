package com.core.op.feature.main.news;

import com.core.op.base.tab.TabFragment;
import com.core.op.di.components.MainComponent;
import com.core.op.lib.utils.inject.AfterViews;
import com.core.op.lib.utils.inject.BeforeViews;

public final class NewFragment extends TabFragment<NewViewModel> {

    public static NewFragment instance() {
        return new NewFragment();
    }

    @BeforeViews
    void beforViews() {
        getComponent(MainComponent.class).inject(this);
    }

    @AfterViews
    void afterViews() {
    }
}
