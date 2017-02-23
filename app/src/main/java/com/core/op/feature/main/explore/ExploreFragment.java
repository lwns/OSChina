package com.core.op.feature.main.explore;

import android.app.Activity;
import android.content.Intent;

import com.core.op.R;
import com.core.op.base.BaseFragment;
import com.core.op.databinding.FrgExploreBinding;
import com.core.op.di.components.MainComponent;
import com.core.op.lib.utils.inject.AfterViews;
import com.core.op.lib.utils.inject.BeforeViews;
import com.core.op.lib.utils.inject.RootView;

import javax.inject.Inject;

import static android.R.attr.data;

@RootView(R.layout.frg_explore)
public final class ExploreFragment extends BaseFragment<ExploreViewModel, FrgExploreBinding> {

    public static ExploreFragment instance() {
        return new ExploreFragment();
    }

    @BeforeViews
    void beforViews() {
        getComponent(MainComponent.class).inject(this);
    }

    @AfterViews
    void afterViews() {
    }

}
