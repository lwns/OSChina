package com.core.op.lib.base;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.core.op.lib.R;
import com.core.op.lib.utils.inject.InjectUtil;
import com.core.op.lib.weight.picker.view.BasePickerView;
import com.core.op.lib.weight.picker.view.DialogBuilder;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import javax.inject.Inject;

/**
 * @author op
 * @version 1.0
 * @description
 * @createDate 2016/12/21
 */
public abstract class BDialog<V extends BViewModel, T extends ViewDataBinding> extends BasePickerView {

    protected LayoutInflater inflater;

    protected V viewModel;

    protected T binding;

    public BDialog(DialogBuilder builder, V viewModel) {
        super(builder);
        this.viewModel = viewModel;
        binding = DataBindingUtil.
                inflate(LayoutInflater.from(context), InjectUtil.injectFrgRootView(this), contentContainer, true);
        viewModel.setBinding(binding);
        bindViewModel();
        InjectUtil.injectAfterView(this);
    }

    public static DialogBuilder newDialog(Context context) {
        return new DialogBuilder(context);
    }

    protected abstract void bindViewModel();

}
