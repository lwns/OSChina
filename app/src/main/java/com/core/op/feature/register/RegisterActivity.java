package com.core.op.feature.register;

import android.content.Context;
import android.content.Intent;

import com.core.op.R;
import com.core.op.base.BaseActivity;
import com.core.op.databinding.ActRegisterBinding;
import com.core.op.di.components.DaggerRegisterComponent;
import com.core.op.di.components.RegisterComponent;
import com.core.op.di.modules.RegisterModule;

import com.core.op.lib.utils.inject.AfterViews;
import com.core.op.lib.utils.inject.BeforeViews;
import com.core.op.lib.utils.inject.RootView;

import javax.inject.Inject;

@RootView(R.layout.act_register)
public final class RegisterActivity extends BaseActivity<RegisterViewModel, ActRegisterBinding> {

    RegisterComponent component;

    public final static void instance(Context context) {
        context.startActivity(new Intent(context, RegisterActivity.class));
    }

    @BeforeViews
    void beforViews() {
        component = DaggerRegisterComponent.builder()
                .appComponent(getApplicationComponent())
                .activityModule(getActivityModule())
                .registerModule(new RegisterModule())
                .build();
        component.inject(this);
    }

    @AfterViews
    void afterViews() {
    }
}
