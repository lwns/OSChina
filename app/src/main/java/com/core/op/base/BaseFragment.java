package com.core.op.base;

import android.databinding.ViewDataBinding;

import com.core.op.BR;
import com.core.op.lib.base.BFViewModel;
import com.core.op.lib.base.BFragment;
import com.core.op.lib.di.HasComponent;



/**
 * @author op
 * @version 1.0
 * @description
 * @createDate 2016/8/11
 */
public class BaseFragment<V extends BFViewModel, T extends ViewDataBinding> extends BFragment<V, T> {

    protected <C> C getComponent(Class<C> componentType) {
        return componentType.cast(((HasComponent<C>) getActivity()).getComponent());
    }

    @Override
    protected void bindViewModel() {
        binding.setVariable(BR.viewModel, viewModel);
    }
}
