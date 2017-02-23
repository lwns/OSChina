package com.core.op.base;

import com.core.op.BR;

import android.content.Context;
import android.databinding.ViewDataBinding;

import com.core.op.lib.base.BDialog;
import com.core.op.lib.base.BViewModel;
import com.core.op.lib.weight.picker.view.DialogBuilder;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;


/**
 * @author op
 * @version 1.0
 * @description
 * @createDate 2016/12/21
 */
public class BaseDialog<V extends BViewModel, T extends ViewDataBinding> extends BDialog<V, T> {

    public BaseDialog(DialogBuilder builder, V viewModel) {
        super(builder, viewModel);
    }

    @Override
    protected void bindViewModel() {
        binding.setVariable(BR.viewModel, viewModel);
    }
}
