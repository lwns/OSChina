package com.core.op.feature.main.news.blog;

import com.core.op.base.list.BaseListFragment;
import com.core.op.di.components.MainComponent;
import com.core.op.lib.utils.inject.AfterViews;
import com.core.op.lib.utils.inject.BeforeViews;


public final class BlogFragment extends BaseListFragment<BlogViewModel> {

    public static BlogFragment instance() {
        return new BlogFragment();
    }

    @BeforeViews
    void beforViews() {
        getComponent(MainComponent.class).inject(this);
    }

    @AfterViews
    void afterViews() {
    }
}
