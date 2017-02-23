package com.core.op.feature.back.software;

import com.core.op.base.tab.TabFragment;
import com.core.op.di.components.SimpleBackComponent;
import com.core.op.lib.utils.inject.AfterViews;
import com.core.op.lib.utils.inject.BeforeViews;

public final class SoftwareFragment extends TabFragment<SoftwareViewModel> {

    public static SoftwareFragment instance() {
        return new SoftwareFragment();
    }

    @BeforeViews
    void beforViews() {
        getComponent(SimpleBackComponent.class).inject(this);
    }

    @AfterViews
    void afterViews() {
    }
}
