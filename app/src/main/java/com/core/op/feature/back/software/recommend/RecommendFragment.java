package com.core.op.feature.back.software.recommend;

import android.os.Bundle;

import com.core.op.R;
import com.core.op.base.BaseFragment;
import com.core.op.base.list.BaseListFragment;
import com.core.op.databinding.FrgRecommendBinding;
import com.core.op.di.components.SimpleBackComponent;
import com.core.op.lib.utils.inject.AfterViews;
import com.core.op.lib.utils.inject.BeforeViews;
import com.core.op.lib.utils.inject.RootView;

public final class RecommendFragment extends BaseListFragment<RecommendViewModel> {

    public static RecommendFragment instance(String tag) {
        RecommendFragment recommendFragment = new RecommendFragment();
        Bundle bundle = new Bundle();
        bundle.putString(RecommendViewModel.BUNDLE_SOFTWARE, tag);
        recommendFragment.setArguments(bundle);
        return recommendFragment;
    }

    @BeforeViews
    void beforViews() {
        getComponent(SimpleBackComponent.class).inject(this);
    }

    @AfterViews
    void afterViews() {
    }
}
