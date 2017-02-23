package com.core.op.feature.back.software.recommend;


import com.core.op.BR;
import com.core.op.R;
import com.core.op.base.list.BaseListViewModel;
import com.core.op.bindingadapter.common.BaseItemViewSelector;
import com.core.op.bindingadapter.common.ItemView;
import com.core.op.bindingadapter.common.ItemViewSelector;
import com.domain.interactor.main.SoftwareUseCase;
import com.orhanobut.logger.Logger;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import javax.inject.Inject;

import rx.Observable;


public class RecommendViewModel extends BaseListViewModel<RecommendItemViewModel> {

    public static final String BUNDLE_SOFTWARE = "BUNDLE_SOFTWARE";

    SoftwareUseCase useCase;

    @Inject
    public RecommendViewModel(RxAppCompatActivity activity,
                              SoftwareUseCase useCase) {
        super(activity);
        this.useCase = useCase;
    }

    @Override
    public void afterViews() {
        super.afterViews();
        refresh();
    }

    @Override
    public void loadMore() {
        useCase.setParams(bundle.getString(BUNDLE_SOFTWARE), currentPage++ + "", "20");
        loadData(false);
    }

    @Override
    public void refresh() {
        currentPage = 1;
        useCase.setParams(bundle.getString(BUNDLE_SOFTWARE), currentPage + "", "20");
        loadData(true);
    }

    private void loadData(boolean isClean) {
        isRefreshing.set(true);
        useCase.execute().compose(fragment.bindToLifecycle())
                .flatMap(data -> {
                    if (isClean) {
                        itemViewModel.clear();
                    }
                    return Observable.from(data.getList());
                })
                .doOnNext(d -> {
                    itemViewModel.add(new RecommendItemViewModel(activity, d));
                })
                .subscribe(d -> {
                }, error -> {
                    Logger.i(error.getMessage());
                }, () -> {
                    isRefreshing.set(false);
                    binding.recyclerView.getAdapter().notifyDataSetChanged();
                });
    }

    @Override
    public ItemViewSelector<RecommendItemViewModel> itemView() {
        return new BaseItemViewSelector<RecommendItemViewModel>() {
            @Override
            public void select(ItemView itemView, int position, RecommendItemViewModel item) {
                itemView.set(BR.viewModel, R.layout.item_software_recommend);
            }

            @Override
            public int viewTypeCount() {
                return 1;
            }
        };
    }
}