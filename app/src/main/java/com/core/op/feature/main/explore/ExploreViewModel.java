package com.core.op.feature.main.explore;


import android.content.Intent;
import android.support.v4.app.FragmentManager;

import com.core.op.databinding.FrgExploreBinding;
import com.core.op.feature.back.SimpleBackActivity;
import com.core.op.feature.back.SimpleBackPage;
import com.core.op.feature.scan.ScanActivity;
import com.core.op.lib.base.BFViewModel;
import com.core.op.lib.command.ReplyCommand;
import com.core.op.lib.di.PerActivity;
import com.core.op.utils.CameraUtil;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;
import com.trello.rxlifecycle.components.support.RxFragment;

import javax.inject.Inject;

@PerActivity
public class ExploreViewModel extends BFViewModel<FrgExploreBinding> {

    public final ReplyCommand softClick = new ReplyCommand(() -> {
        SimpleBackActivity.instance(activity, SimpleBackPage.SOFTWARE);
    });

    public final ReplyCommand scanClick = new ReplyCommand(() -> {
        ScanActivity.instance(activity);
    });

      public final ReplyCommand shakeClick = new ReplyCommand(() -> {
        SimpleBackActivity.instance(activity, SimpleBackPage.SHAKE);
    });
    public final ReplyCommand activeClick = new ReplyCommand(() -> {
        SimpleBackActivity.instance(activity, SimpleBackPage.ACTIVE);
    });


    @Inject
    public ExploreViewModel(RxAppCompatActivity activity) {
        super(activity);
    }


    @Override
    public void afterViews() {
    }
}