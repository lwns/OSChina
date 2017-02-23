package com.core.op.feature.message;


import android.support.v4.app.FragmentManager;

import com.core.op.databinding.ActMessageBinding;
import com.core.op.lib.base.BAViewModel;
import com.core.op.lib.base.BViewModel;
import com.core.op.lib.di.PerActivity;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import javax.inject.Inject;

@PerActivity
public class MessageViewModel extends BAViewModel<ActMessageBinding> {

    @Inject
    public MessageViewModel(RxAppCompatActivity activity) {
        super(activity);
    }

    @Override
    public void afterViews() {

    }
}