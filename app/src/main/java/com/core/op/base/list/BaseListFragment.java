package com.core.op.base.list;

import com.core.op.R;
import com.core.op.base.BaseFragment;
import com.core.op.databinding.FrgBaselistBinding;
import com.core.op.lib.base.BFViewModel;
import com.core.op.lib.utils.inject.AfterViews;
import com.core.op.lib.utils.inject.RootView;

@RootView(R.layout.frg_baselist)
public abstract class BaseListFragment<V extends BFViewModel> extends BaseFragment<V, FrgBaselistBinding> {

}
