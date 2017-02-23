package com.core.op.feature.login;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.Toolbar;

import com.core.op.R;
import com.core.op.base.BackActivity;
import com.core.op.base.BaseActivity;
import com.core.op.databinding.ActLoginBinding;
import com.core.op.di.components.DaggerLoginComponent;
import com.core.op.di.components.LoginComponent;
import com.core.op.di.modules.LoginModule;

import com.core.op.lib.utils.inject.AfterViews;
import com.core.op.lib.utils.inject.BeforeViews;
import com.core.op.lib.utils.inject.RootView;

import javax.inject.Inject;

@RootView(R.layout.act_login)
public final class LoginActivity extends BackActivity<LoginViewModel, ActLoginBinding> {

    LoginComponent component;

    public final static void instance(Context context) {
        context.startActivity(new Intent(context, LoginActivity.class));
    }

    @BeforeViews
    void beforViews() {
        component = DaggerLoginComponent.builder()
                .appComponent(getApplicationComponent())
                .activityModule(getActivityModule())
                .loginModule(new LoginModule())
                .build();
        component.inject(this);
    }

    @AfterViews
    void afterViews() {
    }

    @Override
    protected Toolbar setToolBar() {
        return binding.toolbar;
    }
}
