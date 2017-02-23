package com.core.op.feature.detail.blog.frg;

import android.os.Bundle;

import com.core.op.R;
import com.core.op.base.BaseFragment;
import com.core.op.databinding.FrgBlogdetailBinding;
import com.core.op.di.components.BlogDetailComponent;
import com.core.op.feature.detail.news.frg.NewsDetailFragment;
import com.core.op.lib.utils.inject.AfterViews;
import com.core.op.lib.utils.inject.BeforeViews;
import com.core.op.lib.utils.inject.RootView;
import com.domain.bean.BlogDetail;
import com.domain.bean.NewsDetail;

@RootView(R.layout.frg_blogdetail)
public final class BlogDetailFragment extends BaseFragment<BlogDetailViewModel, FrgBlogdetailBinding> {

    public static BlogDetailFragment instance(BlogDetail data) {
        BlogDetailFragment fragment = new BlogDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("data", data);
        fragment.setArguments(bundle);
        return fragment;
    }

    @BeforeViews
    void beforViews() {
        getComponent(BlogDetailComponent.class).inject(this);
    }

    @AfterViews
    void afterViews() {
    }
}
