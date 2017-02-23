package com.core.op.feature.back.shake;

import com.core.op.R;
import com.core.op.base.BaseFragment;
import com.core.op.databinding.FrgShakeBinding;
import com.core.op.di.components.SimpleBackComponent;
import com.core.op.lib.utils.inject.AfterViews;
import com.core.op.lib.utils.inject.BeforeViews;
import com.core.op.lib.utils.inject.RootView;

import javax.inject.Inject;

@RootView(R.layout.frg_shake)
public final class ShakeFragment extends BaseFragment<ShakeViewModel, FrgShakeBinding> {

    public static ShakeFragment instance() {
        return new ShakeFragment();
    }

    @BeforeViews
    void beforViews() {
        getComponent(SimpleBackComponent.class).inject(this);
    }

    @AfterViews
    void afterViews() {
    }
}
