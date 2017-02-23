package com.core.op.feature.back.mine.blog;

import com.core.op.base.list.BaseListFragment;
import com.core.op.di.components.SimpleBackComponent;
import com.core.op.lib.utils.inject.AfterViews;
import com.core.op.lib.utils.inject.BeforeViews;

public final class BlogFragment extends BaseListFragment<BlogViewModel> {

    public static BlogFragment instance() {
        return new BlogFragment();
    }

    @BeforeViews
    void beforViews() {
        getComponent(SimpleBackComponent.class).inject(this);
    }

    @AfterViews
    void afterViews() {
    }
}
