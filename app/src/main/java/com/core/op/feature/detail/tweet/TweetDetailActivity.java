package com.core.op.feature.detail.tweet;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.Toolbar;

import com.core.op.R;
import com.core.op.base.BackActivity;
import com.core.op.databinding.ActTweetdetailBinding;
import com.core.op.di.components.DaggerTweetDetailComponent;
import com.core.op.di.components.TweetDetailComponent;
import com.core.op.di.modules.TweetDetailModule;
import com.core.op.lib.utils.inject.AfterViews;
import com.core.op.lib.utils.inject.BeforeViews;
import com.core.op.lib.utils.inject.RootView;

@RootView(R.layout.act_tweetdetail)
public final class TweetDetailActivity extends BackActivity<TweetDetailViewModel, ActTweetdetailBinding> {

    TweetDetailComponent component;

    public final static void instance(Context context, long id) {
        Intent intent = new Intent(context, TweetDetailActivity.class);
        intent.putExtra("id", id);
        context.startActivity(intent);
    }

    @BeforeViews
    void beforViews() {
        component = DaggerTweetDetailComponent.builder()
                .appComponent(getApplicationComponent())
                .activityModule(getActivityModule())
                .tweetDetailModule(new TweetDetailModule())
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
