package com.core.op.base.tab;

import com.core.op.R;
import com.core.op.base.BaseFragment;
import com.core.op.databinding.FrgTabBinding;
import com.core.op.lib.base.BFViewModel;
import com.core.op.lib.utils.inject.RootView;

@RootView(R.layout.frg_tab)
public abstract class TabFragment<V extends BFViewModel> extends BaseFragment<V, FrgTabBinding> {

}
