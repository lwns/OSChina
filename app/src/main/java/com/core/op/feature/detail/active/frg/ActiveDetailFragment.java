package com.core.op.feature.detail.active.frg;

import android.os.Bundle;

import com.core.op.R;
import com.core.op.base.BaseFragment;
import com.core.op.databinding.FrgActivedetailBinding;
import com.core.op.di.components.ActiveDetailComponent;
import com.core.op.lib.utils.inject.AfterViews;
import com.core.op.lib.utils.inject.BeforeViews;
import com.core.op.lib.utils.inject.RootView;
import com.domain.bean.EventDetail;

@RootView(R.layout.frg_activedetail)
public final class ActiveDetailFragment extends BaseFragment<ActiveDetailViewModel, FrgActivedetailBinding> {

    public static ActiveDetailFragment instance(EventDetail data) {
        ActiveDetailFragment fragment = new ActiveDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("data", data);
        fragment.setArguments(bundle);
        return fragment;
    }

    @BeforeViews
    void beforViews() {
        getComponent(ActiveDetailComponent.class).inject(this);
    }

    @AfterViews
    void afterViews() {
    }
}
