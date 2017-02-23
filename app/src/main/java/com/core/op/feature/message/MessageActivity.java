package com.core.op.feature.message;

import android.content.Context;
import android.content.Intent;

import com.core.op.R;
import com.core.op.base.BaseActivity;
import com.core.op.databinding.ActMessageBinding;
import com.core.op.di.components.DaggerMessageComponent;
import com.core.op.di.components.MessageComponent;
import com.core.op.di.modules.MessageModule;

import com.core.op.lib.utils.inject.AfterViews;
import com.core.op.lib.utils.inject.BeforeViews;
import com.core.op.lib.utils.inject.RootView;

import javax.inject.Inject;

@RootView(R.layout.act_message)
public final class MessageActivity extends BaseActivity<MessageViewModel, ActMessageBinding> {

    MessageComponent component;

    public final static void instance(Context context) {
        context.startActivity(new Intent(context, MessageActivity.class));
    }

    @BeforeViews
    void beforViews() {
        component = DaggerMessageComponent.builder()
                .appComponent(getApplicationComponent())
                .activityModule(getActivityModule())
                .messageModule(new MessageModule())
                .build();
        component.inject(this);
    }

    @AfterViews
    void afterViews() {
    }
}
