package com.core.op.feature.register;


import android.support.v4.app.FragmentManager;

import com.core.op.databinding.ActRegisterBinding;
import com.core.op.lib.base.BAViewModel;
import com.core.op.lib.base.BViewModel;
import com.core.op.lib.di.PerActivity;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import javax.inject.Inject;

@PerActivity
public class RegisterViewModel extends BAViewModel<ActRegisterBinding> {

    @Inject
    public RegisterViewModel(RxAppCompatActivity activity) {
        super(activity);
    }

    @Override
    public void afterViews() {

    }
}