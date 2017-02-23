package com.core.op.feature.detail.software.frg;

import android.os.Bundle;

import com.core.op.R;
import com.core.op.base.BaseFragment;
import com.core.op.databinding.FrgSoftwaredetailBinding;
import com.core.op.di.components.SoftwareDetailComponent;
import com.core.op.feature.detail.blog.frg.BlogDetailFragment;
import com.core.op.lib.utils.inject.AfterViews;
import com.core.op.lib.utils.inject.BeforeViews;
import com.core.op.lib.utils.inject.RootView;
import com.domain.bean.BlogDetail;
import com.domain.bean.SoftwareDetail;

import javax.inject.Inject;

@RootView(R.layout.frg_softwaredetail)
public final class SoftwareDetailFragment extends BaseFragment<SoftwareDetailViewModel, FrgSoftwaredetailBinding> {


    public static SoftwareDetailFragment instance(SoftwareDetail data) {
        SoftwareDetailFragment fragment = new SoftwareDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("data", data);
        fragment.setArguments(bundle);
        return fragment;
    }

    @BeforeViews
    void beforViews() {
        getComponent(SoftwareDetailComponent.class).inject(this);
    }

    @AfterViews
    void afterViews() {
    }
}
