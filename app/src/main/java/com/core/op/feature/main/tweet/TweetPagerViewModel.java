package com.core.op.feature.main.tweet;


import android.databinding.ObservableField;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.core.op.MainApplication;
import com.core.op.R;
import com.core.op.databinding.FrgTweetpagerBinding;
import com.core.op.lib.base.BFViewModel;
import com.core.op.lib.di.PerActivity;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;
import com.trello.rxlifecycle.components.support.RxFragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import static com.core.op.feature.main.tweet.TweetFragment.CATEGORY_TYPE;
import static com.core.op.feature.main.tweet.TweetFragment.CATEGORY_USER;
import static com.core.op.feature.main.tweet.TweetFragment.TWEET_TYPE_HOT;
import static com.core.op.feature.main.tweet.TweetFragment.TWEET_TYPE_NEW;

@PerActivity
public class TweetPagerViewModel extends BFViewModel<FrgTweetpagerBinding> {

    public final String title;

    public final ObservableField<Integer> selectPosition = new ObservableField<>(0);

    public final List<Fragment> fragments = new ArrayList<>();

    public final List<String> titles = new ArrayList<>();

    @Inject
    public TweetPagerViewModel(RxAppCompatActivity activity, MainApplication application) {
        super(activity);
        title = activity.getString(R.string.app_bottom_menu_new);
        titles.addAll(Arrays.asList(activity.getResources().getStringArray(R.array.tweet_tabs)));
        fragments.add(TweetFragment.instance(CATEGORY_TYPE, TWEET_TYPE_NEW));
        fragments.add(TweetFragment.instance(CATEGORY_TYPE, TWEET_TYPE_HOT));
        fragments.add(TweetFragment.instance(CATEGORY_USER, application.getLoginUid()));
    }

    @Override
    public void afterViews() {
        binding.tabLayout.setupWithViewPager(binding.viewPager);
    }
}