package com.core.op.lib.base;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;

import com.core.op.lib.AppException;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import com.core.op.lib.utils.inject.InjectUtil;

import javax.inject.Inject;


/**
 * @author op
 * @version 1.0
 * @description
 * @createDate 2016/2/1
 */
public abstract class BActivity<V extends BAViewModel, T extends ViewDataBinding> extends RxAppCompatActivity {

    protected LayoutInflater inflater;

    protected T binding;

    @Inject
    protected V viewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        inflater = LayoutInflater.from(this);
        initBeforeView();
        initRootView();
        viewModel.setBinding(binding);
        bindViewModel();
        initAfterView();
        viewModel.setFragmentManager(this.getSupportFragmentManager());
        viewModel.afterViews();
    }

    protected abstract void bindViewModel();

    protected void initBeforeView() {
        InjectUtil.injectBeforeView(this);
    }

    protected void initRootView() {
        try {
            binding = InjectUtil.injectBindRootView(this);
        } catch (Exception e) {
            AppException.binding(e).printStackTrace();
        }
    }

    protected void initAfterView() {
        InjectUtil.injectAfterView(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        viewModel.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        viewModel.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        viewModel.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        viewModel.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        viewModel.onDestroy();
    }
}
