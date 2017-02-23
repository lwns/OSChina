package com.core.op.feature.back.setting;

import com.core.op.R;
import com.core.op.base.BaseFragment;
import com.core.op.databinding.FrgSettingBinding;
import com.core.op.di.components.SimpleBackComponent;
import com.core.op.lib.utils.inject.AfterViews;
import com.core.op.lib.utils.inject.BeforeViews;
import com.core.op.lib.utils.inject.RootView;

import javax.inject.Inject;

@RootView(R.layout.frg_setting)
public final class SettingFragment extends BaseFragment<SettingViewModel, FrgSettingBinding> {

    public static SettingFragment instance() {
        return new SettingFragment();
    }

    @BeforeViews
    void beforViews() {
        getComponent(SimpleBackComponent.class).inject(this);
    }

    @AfterViews
    void afterViews() {
    }
}
