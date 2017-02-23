package com.core.op.feature.main.news.active;


import android.graphics.pdf.PdfDocument;
import android.support.v4.app.FragmentManager;

import com.core.op.BR;
import com.core.op.R;
import com.core.op.base.list.BaseListViewModel;
import com.core.op.bindingadapter.common.BaseItemViewSelector;
import com.core.op.bindingadapter.common.ItemView;
import com.core.op.bindingadapter.common.ItemViewSelector;
import com.core.op.feature.main.news.info.InfoItemViewModel;
import com.core.op.lib.base.BViewModel;
import com.core.op.lib.di.PerActivity;
import com.domain.bean.Banner;
import com.domain.bean.Event;
import com.domain.bean.base.PageBean;
import com.domain.interactor.main.BannerUseCase;
import com.domain.interactor.main.EventUseCase;
import com.orhanobut.logger.Logger;
import com.trello.rxlifecycle.ActivityEvent;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;
import com.trello.rxlifecycle.components.support.RxFragment;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

@PerActivity
public class ActiveViewModel extends BaseListViewModel<ActiveItemViewModel> {

    EventUseCase useCase;

    BannerUseCase bannerUseCase;

    PageBean<Event> data;

    List<Banner> banners;


    @Inject
    public ActiveViewModel(RxAppCompatActivity activity,
                           BannerUseCase bannerUseCase,
                           EventUseCase useCase) {
        super(activity);
        this.useCase = useCase;
        this.bannerUseCase = bannerUseCase;
    }

    @Override
    public void afterViews() {
        super.afterViews();
        refresh();
    }

    @Override
    public void loadMore() {
        useCase.setParams(data != null ? data.getNextPageToken() : "");
        useCase.execute().compose(fragment.bindToLifecycle())
                .flatMap(d -> {
                    this.data = d;
                    return Observable.from(d.getItems());
                })
                .doOnNext(d ->
                        itemViewModel.add(new ActiveItemViewModel(activity, d)))
                .subscribe(n -> {
                }, e -> {
                    Logger.e(e, "info execute error");
                }, () -> {
                    binding.recyclerView.getAdapter().notifyDataSetChanged();
                });
    }

    @Override
    public void refresh() {
        useCase.cleanParams();
        loadData(true);
    }

    void loadData(boolean isClean) {
        isRefreshing.set(true);
        bannerUseCase.setParams("3");//

        bannerUseCase.executeNoSchedule()
                .compose(fragment.bindToLifecycle())
                .flatMap(data -> {
                    banners = data.getItems();
                    return useCase.executeNoSchedule();
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(data -> {
                    if (isClean) itemViewModel.clear();
                    itemViewModel.add(new ActiveItemViewModel(activity, banners));
                })
                .flatMap(data -> {
                    this.data = data;
                    return Observable.from(data.getItems());
                })
                .doOnNext(d -> {
                    itemViewModel.add(new ActiveItemViewModel(activity, d));
                })
                .subscribe(n -> {
                }, e -> {
                    isRefreshing.set(false);
                    Logger.e(e, "info execute error");
                }, () -> {
                    isRefreshing.set(false);
                    binding.recyclerView.getAdapter().notifyDataSetChanged();
                });
    }

    @Override
    public ItemViewSelector<ActiveItemViewModel> itemView() {
        return new BaseItemViewSelector<ActiveItemViewModel>() {
            @Override
            public void select(ItemView itemView, int position, ActiveItemViewModel itemViewModel) {
                if (position == 0) {
                    itemView.set(BR.viewModel, R.layout.item_news_active_banner);
                } else {
                    itemView.set(BR.viewModel, R.layout.item_new_active);
                }
            }

            @Override
            public int viewTypeCount() {
                return 2;
            }
        };
    }
}