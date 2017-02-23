package com.core.op.feature.detail.news;


import android.databinding.ObservableField;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.core.op.R;
import com.core.op.base.BaseFragment;
import com.core.op.feature.detail.base.act.DetailViewModel;
import com.core.op.feature.detail.news.frg.NewsDetailFragment;
import com.core.op.lib.di.PerActivity;
import com.core.op.lib.weight.EmptyLayout;
import com.domain.bean.NewsDetail;
import com.domain.interactor.main.NewsDetailUseCase;
import com.orhanobut.logger.Logger;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import javax.inject.Inject;
import javax.inject.Named;

@PerActivity
public class NewsDetailViewModel extends DetailViewModel<NewsDetail> {

    NewsDetailUseCase useCase;


    @Inject
    public NewsDetailViewModel(RxAppCompatActivity activity,
                               @Named("NewsDetailUseCase") NewsDetailUseCase usercase) {
        super(activity);
        this.useCase = usercase;
        title.set(activity.getString(R.string.app_news_detail_title));
    }

    @Override
    protected void loadData() {
        useCase.setParams(getDataId() + "");
        useCase.execute()
                .compose(activity.bindToLifecycle())
                .subscribe(data -> {
                    this.data = data;
                    addFragment(R.id.fl_content, getDataViewFragment());
                }, error -> {
                    Logger.e(error.getMessage());
                });
    }

    @Override
    protected BaseFragment getDataViewFragment() {
        return NewsDetailFragment.instance(data);
    }


}