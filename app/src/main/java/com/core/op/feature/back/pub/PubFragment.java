package com.core.op.feature.back.pub;

import android.content.Context;
import android.view.inputmethod.InputMethodManager;

import com.core.op.R;
import com.core.op.base.BaseFragment;
import com.core.op.databinding.FrgPubBinding;
import com.core.op.di.components.SimpleBackComponent;
import com.core.op.lib.utils.inject.AfterViews;
import com.core.op.lib.utils.inject.BeforeViews;
import com.core.op.lib.utils.inject.RootView;

import javax.inject.Inject;

@RootView(R.layout.frg_pub)
public final class PubFragment extends BaseFragment<PubViewModel, FrgPubBinding> {

    public static PubFragment instance() {
        return new PubFragment();
    }

    @BeforeViews
    void beforViews() {
        getComponent(SimpleBackComponent.class).inject(this);
    }

    @AfterViews
    void afterViews() {
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(binding.editContent, InputMethodManager.SHOW_FORCED);
    }
}
