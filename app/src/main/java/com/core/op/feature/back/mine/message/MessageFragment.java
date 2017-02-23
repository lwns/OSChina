package com.core.op.feature.back.mine.message;

import com.core.op.base.list.BaseListFragment;
import com.core.op.di.components.SimpleBackComponent;
import com.core.op.lib.utils.inject.AfterViews;
import com.core.op.lib.utils.inject.BeforeViews;

public final class MessageFragment extends BaseListFragment<MessageViewModel> {

    public static MessageFragment instance() {
        return new MessageFragment();
    }

    @BeforeViews
    void beforViews() {
        getComponent(SimpleBackComponent.class).inject(this);
    }

    @AfterViews
    void afterViews() {
    }
}
