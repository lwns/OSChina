package com.core.op.feature.main.tweet;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import com.core.op.AppConfig;
import com.core.op.BR;
import com.core.op.MainApplication;
import com.core.op.R;
import com.core.op.base.list.BaseListViewModel;
import com.core.op.bindingadapter.common.BaseItemViewSelector;
import com.core.op.bindingadapter.common.ItemView;
import com.core.op.bindingadapter.common.ItemViewSelector;
import com.core.op.data.util.CacheUtil;
import com.domain.bean.Tweet;
import com.domain.bean.UserV2;
import com.domain.bean.base.PageBean;
import com.domain.interactor.main.TweetUseCase;
import com.domain.interactor.main.UserTweetUseCase;
import com.orhanobut.logger.Logger;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;
import com.trello.rxlifecycle.components.support.RxFragment;

import javax.inject.Inject;
import javax.inject.Named;

import rx.Observable;

import static com.core.op.feature.main.tweet.TweetFragment.CACHE_USER_TWEET;
import static com.core.op.feature.main.tweet.TweetFragment.CATEGORY_TYPE;
import static com.core.op.feature.main.tweet.TweetFragment.CATEGORY_USER;
import static com.core.op.feature.main.tweet.TweetFragment.TWEET_TYPE_NEW;
import static com.core.op.lib.utils.StrUtil.formatCount;


public class TweetViewModel extends BaseListViewModel<TweetItemViewModel> {

    private TweetUseCase useCase;
    private UserTweetUseCase userTweetUseCase;

    public int requestCategory;//请求类型
    public int tweetType;
    public long authorId;

    PageBean<Tweet> data;

    private LoginReceiver mReceiver;

    MainApplication application;

    @Inject
    public TweetViewModel(
            @Named("TweetUseCase") TweetUseCase userCase,
            @Named("UserTweetUseCase") UserTweetUseCase userTweetUseCase,
            RxAppCompatActivity activity,
            MainApplication application) {
        super(activity);
        this.useCase = userCase;
        this.userTweetUseCase = userTweetUseCase;
        this.application = application;
    }

    @Override
    public void setFragment(RxFragment fragment) {
        super.setFragment(fragment);
        tweetType = fragment.getArguments().getInt("tweetType", 1);
        requestCategory = fragment.getArguments().getInt("requestCategory", CATEGORY_TYPE);
        authorId = fragment.getArguments().getLong("authorId", 0);

        if (requestCategory == CATEGORY_USER) {
            IntentFilter filter = new IntentFilter(
                    AppConfig.INTENT_ACTION_USER_CHANGE);
            filter.addAction(AppConfig.INTENT_ACTION_LOGOUT);
            if (mReceiver == null) {
                mReceiver = new LoginReceiver();
                activity.registerReceiver(mReceiver, filter);
            }
        }
    }


    private class LoginReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(AppConfig.INTENT_ACTION_USER_CHANGE)) {
                if (application.getLoginUid() != 0) {
                    authorId = application.getLoginUid();
                    refresh();
                } else {
                    authorId = 0;
                }
            } else if (intent.getAction().equals(AppConfig.INTENT_ACTION_LOGOUT)) {
                authorId = 0;
                itemViewModel.clear();
                binding.recyclerView.getAdapter().notifyDataSetChanged();
            }
        }
    }

    @Override
    public void afterViews() {
        super.afterViews();
        refresh();
    }

    @Override
    public void loadMore() {
        switch (requestCategory) {
            case CATEGORY_TYPE:
                loadData(false, data == null ? "" : data.getNextPageToken());
                break;
            case CATEGORY_USER:
                loadUserData(false, data == null ? "" : data.getNextPageToken());
                break;
        }
    }

    @Override
    public void refresh() {
        switch (requestCategory) {
            case CATEGORY_TYPE:
                if (tweetType == 0) {
                    isRefreshing.set(false);
                    return;
                }
                loadData(true, "");
                break;
            case CATEGORY_USER:
                if (authorId != 0) {
                    loadUserData(true, "");
                }
                break;
        }
    }

    /**
     * 用户登陆成功获取用户动弹
     *
     * @param isClean
     * @param nextToken
     */
    public void loadUserData(boolean isClean, String nextToken) {
        isRefreshing.set(true);
        userTweetUseCase.setParams(authorId + "", nextToken);

        userTweetUseCase.execute().compose(fragment.bindToLifecycle())
                .flatMap(d -> {
                    data = d;
                    if (isClean) {
                        itemViewModel.clear();
                    }
                    return Observable.from(d.getItems());
                }).doOnNext(data -> itemViewModel.add(new TweetItemViewModel(activity, data)))
                .subscribe(data -> {
                }, e -> {
                    Logger.e(e, "info execute error");
                }, () -> {
                    isRefreshing.set(false);
                    binding.recyclerView.getAdapter().notifyDataSetChanged();
                });
    }

    /**
     * 热门，最新动弹
     *
     * @param isClean
     * @param nextToken
     */
    public void loadData(boolean isClean, String nextToken) {
        isRefreshing.set(true);
        useCase.setParams(tweetType + "", nextToken);

        useCase.execute().compose(fragment.bindToLifecycle())
                .flatMap(d -> {
                    data = d;
                    if (isClean) {
                        itemViewModel.clear();
                    }
                    return Observable.from(d.getItems());
                }).doOnNext(data -> itemViewModel.add(new TweetItemViewModel(activity, data)))
                .subscribe(data -> {
                }, e -> {
                    Logger.e(e, "info execute error");
                }, () -> {
                    isRefreshing.set(false);
                    binding.recyclerView.getAdapter().notifyDataSetChanged();
                });
    }

    @Override
    public ItemViewSelector<TweetItemViewModel> itemView() {
        return new BaseItemViewSelector<TweetItemViewModel>() {
            @Override
            public void select(ItemView itemView, int position, TweetItemViewModel item) {
                itemView.set(BR.viewModel, R.layout.item_tweet);
            }

            @Override
            public int viewTypeCount() {
                return 1;
            }
        };
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}