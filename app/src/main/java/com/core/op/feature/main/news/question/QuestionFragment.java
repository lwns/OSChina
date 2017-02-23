package com.core.op.feature.main.news.question;

import com.core.op.R;
import com.core.op.base.list.BaseListFragment;
import com.core.op.di.components.MainComponent;
import com.core.op.lib.utils.inject.AfterViews;
import com.core.op.lib.utils.inject.BeforeViews;
import com.core.op.lib.utils.inject.RootView;

public final class QuestionFragment extends BaseListFragment<QuestionViewModel> {

    public static QuestionFragment instance() {
        return new QuestionFragment();
    }

    @BeforeViews
    void beforViews() {
        getComponent(MainComponent.class).inject(this);
    }

    @AfterViews
    void afterViews() {
    }
}
