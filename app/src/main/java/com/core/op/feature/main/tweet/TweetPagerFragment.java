package com.core.op.feature.main.tweet;

import com.core.op.R;
import com.core.op.base.BaseFragment;
import com.core.op.databinding.FrgTweetpagerBinding;
import com.core.op.di.components.MainComponent;
import com.core.op.lib.utils.inject.AfterViews;
import com.core.op.lib.utils.inject.BeforeViews;
import com.core.op.lib.utils.inject.RootView;

@RootView(R.layout.frg_tweetpager)
public final class TweetPagerFragment extends BaseFragment<TweetPagerViewModel, FrgTweetpagerBinding> {

    public static TweetPagerFragment instance() {
        return new TweetPagerFragment();
    }

    @BeforeViews
    void beforViews() {
        getComponent(MainComponent.class).inject(this);
    }

    @AfterViews
    void afterViews() {
        binding.viewPager.setOffscreenPageLimit(3);
    }
}
