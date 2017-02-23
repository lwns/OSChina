package com.core.op.feature.main.news.info;


import com.core.op.BR;
import com.core.op.R;
import com.core.op.base.list.BaseListViewModel;
import com.core.op.bindingadapter.common.BaseItemViewSelector;
import com.core.op.bindingadapter.common.ItemView;
import com.core.op.bindingadapter.common.ItemViewSelector;
import com.core.op.lib.di.PerActivity;
import com.domain.bean.Banner;
import com.domain.bean.News;
import com.domain.bean.base.PageBean;
import com.domain.interactor.main.BannerUseCase;
import com.domain.interactor.main.InfoUseCase;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import com.orhanobut.logger.Logger;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;



@PerActivity
public class InfoViewModel extends BaseListViewModel<InfoItemViewModel> {

    InfoUseCase useCase;

    BannerUseCase bannerUseCase;

    PageBean<News> data;

    List<Banner> banners;

    @Inject
    public InfoViewModel(@Named("info") InfoUseCase useCase,
                         @Named("BannerUseCase") BannerUseCase bannerUseCase,
                         RxAppCompatActivity activity) {
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
                        itemViewModel.add(new InfoItemViewModel(activity, d)))
                .subscribe(n -> {
                    binding.recyclerView.getAdapter().notifyDataSetChanged();
                }, e -> {
                    Logger.e(e, "info execute error");
                }, () -> {
                });
    }

    @Override
    public void refresh() {
        useCase.cleanParams();
        loadData(true);
    }

    void loadData(boolean isClean) {
        isRefreshing.set(true);
        bannerUseCase.setParams("1");

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
                    itemViewModel.add(new InfoItemViewModel(activity, banners));
                })
                .flatMap(data -> {
                    this.data = data;
                    return Observable.from(data.getItems());
                })
                .doOnNext(d -> {
                    itemViewModel.add(new InfoItemViewModel(activity, d));
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
    public ItemViewSelector<InfoItemViewModel> itemView() {
        return new BaseItemViewSelector<InfoItemViewModel>() {
            @Override
            public void select(ItemView itemView, int position, InfoItemViewModel itemViewModel) {
                if (position == 0) {
                    itemView.set(BR.viewModel, R.layout.item_news_banner);
                } else {
                    itemView.set(BR.viewModel, R.layout.item_new_info);
                }
            }

            @Override
            public int viewTypeCount() {
                return 2;
            }
        };
    }
}