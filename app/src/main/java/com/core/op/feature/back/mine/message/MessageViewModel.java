package com.core.op.feature.back.mine.message;


import com.core.op.BR;
import com.core.op.R;
import com.core.op.base.list.BaseListViewModel;
import com.core.op.bindingadapter.common.BaseItemViewSelector;
import com.core.op.bindingadapter.common.ItemView;
import com.core.op.bindingadapter.common.ItemViewSelector;
import com.core.op.lib.di.PerActivity;
import com.domain.bean.Mention;
import com.domain.bean.base.PageBean;
import com.domain.interactor.user.MineMessageUseCase;
import com.orhanobut.logger.Logger;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import javax.inject.Inject;

import rx.Observable;

@PerActivity
public class MessageViewModel extends BaseListViewModel<MessageItemViewModel> {

    MineMessageUseCase useCase;


    PageBean<Mention> data;

    @Inject
    public MessageViewModel(RxAppCompatActivity activity,
                            MineMessageUseCase useCase) {
        super(activity);
        this.useCase = useCase;
    }

    @Override
    public void afterViews() {
        refresh();
    }

    @Override
    public void loadMore() {
        loadData(false, data.getNextPageToken());
    }

    @Override
    public void refresh() {
        loadData(true, "");
    }

    public void loadData(boolean isClean, String nextToken) {
        isRefreshing.set(true);
        useCase.setParams(nextToken);

        useCase.execute().compose(fragment.bindToLifecycle())
                .flatMap(d -> {
                    data = d;
                    if (isClean) {
                        itemViewModel.clear();
                    }
                    return Observable.from(d.getItems());
                }).doOnNext(data -> itemViewModel.add(new MessageItemViewModel(activity, data)))
                .subscribe(data -> {
                }, e -> {
                    Logger.e(e, "info execute error");
                }, () -> {
                    isRefreshing.set(false);
                    binding.recyclerView.getAdapter().notifyDataSetChanged();
                });
    }

    @Override
    public ItemViewSelector<MessageItemViewModel> itemView() {
        return new BaseItemViewSelector<MessageItemViewModel>() {
            @Override
            public void select(ItemView itemView, int position, MessageItemViewModel item) {
                itemView.set(BR.viewModel, R.layout.item_mine_message);
            }
        };
    }
}