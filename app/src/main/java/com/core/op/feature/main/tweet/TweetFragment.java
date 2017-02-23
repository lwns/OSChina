package com.core.op.feature.main.tweet;

import android.os.Bundle;

import com.core.op.base.list.BaseListFragment;
import com.core.op.di.components.MainComponent;
import com.core.op.lib.utils.inject.AfterViews;
import com.core.op.lib.utils.inject.BeforeViews;

public final class TweetFragment extends BaseListFragment<TweetViewModel> {

    public static final int CATEGORY_TYPE = 1; //请求最新或者最热
    public static final int CATEGORY_USER = 2; //请求用户

    public static final int TWEET_TYPE_NEW = 1;
    public static final int TWEET_TYPE_HOT = 2;

    public static final String CACHE_NEW_TWEET = "cache_new_tweet";
    public static final String CACHE_HOT_TWEET = "cache_hot_tweet";
    public static final String CACHE_USER_TWEET = "cache_user_tweet";

    public static TweetFragment instance(int requestCategory, int tweetType) {
        TweetFragment fragment = new TweetFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("requestCategory", requestCategory);
        bundle.putInt("tweetType", tweetType);
        fragment.setArguments(bundle);
        return fragment;
    }

    public static TweetFragment instance(int requestCategory, long authorId) {
        TweetFragment fragment = new TweetFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("requestCategory", requestCategory);
        bundle.putLong("authorId", authorId);
        fragment.setArguments(bundle);
        return fragment;
    }

    @BeforeViews
    void beforViews() {
        getComponent(MainComponent.class).inject(this);
    }

    @AfterViews
    void afterViews() {
    }
}
