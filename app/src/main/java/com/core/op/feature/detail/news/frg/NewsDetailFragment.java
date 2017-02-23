package com.core.op.feature.detail.news.frg;

import android.os.Bundle;

import com.core.op.R;
import com.core.op.base.BaseFragment;
import com.core.op.databinding.FrgNewsdetailBinding;
import com.core.op.di.components.NewsDetailComponent;
import com.core.op.lib.utils.inject.AfterViews;
import com.core.op.lib.utils.inject.BeforeViews;
import com.core.op.lib.utils.inject.RootView;
import com.domain.bean.NewsDetail;

@RootView(R.layout.frg_newsdetail)
public final class NewsDetailFragment extends BaseFragment<NewsDetailViewModel, FrgNewsdetailBinding> {

    public static NewsDetailFragment instance(NewsDetail data) {
        NewsDetailFragment fragment = new NewsDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("data", data);
        fragment.setArguments(bundle);
        return fragment;
    }

    @BeforeViews
    void beforViews() {
        getComponent(NewsDetailComponent.class).inject(this);
    }

    @AfterViews
    void afterViews() {
    }
}
