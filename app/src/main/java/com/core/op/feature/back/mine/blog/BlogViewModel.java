package com.core.op.feature.back.mine.blog;


import com.core.op.BR;
import com.core.op.R;
import com.core.op.base.list.BaseListViewModel;
import com.core.op.bindingadapter.common.BaseItemViewSelector;
import com.core.op.bindingadapter.common.ItemView;
import com.core.op.bindingadapter.common.ItemViewSelector;
import com.core.op.feature.back.mine.message.MessageItemViewModel;
import com.core.op.lib.base.BFViewModel;
import com.core.op.lib.di.PerActivity;
import com.core.op.lib.utils.AppToast;
import com.core.op.utils.AccountUtil;
import com.domain.bean.Mention;
import com.domain.bean.SubBean;
import com.domain.bean.User;
import com.domain.bean.base.PageBean;
import com.domain.interactor.user.MineBlogUseCase;
import com.domain.interactor.user.MineMessageUseCase;
import com.orhanobut.logger.Logger;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import javax.inject.Inject;

import rx.Observable;

@PerActivity
public class BlogViewModel extends BaseListViewModel<BlogItemViewModel> {

    MineBlogUseCase useCase;


    PageBean<SubBean> data;

    User user;

    @Inject
    public BlogViewModel(RxAppCompatActivity activity,
                         MineBlogUseCase useCase) {
        super(activity);
        this.useCase = useCase;
        user = AccountUtil.getUser();

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
        useCase.setParams(user.getId() + "", user.getName(), nextToken);

        useCase.execute().compose(fragment.bindToLifecycle())
                .flatMap(d -> {
                    data = d;
                    if (isClean) {
                        itemViewModel.clear();
                    }
                    return Observable.from(d.getItems());
                }).doOnNext(data -> itemViewModel.add(new BlogItemViewModel(activity, data)))
                .subscribe(data -> {
                }, e -> {
                    isRefreshing.set(false);
                    Logger.e(e, e.getMessage());
                    AppToast.show(activity, e.getMessage());
                }, () -> {
                    isRefreshing.set(false);
                    binding.recyclerView.getAdapter().notifyDataSetChanged();
                });
    }

    @Override
    public ItemViewSelector<BlogItemViewModel> itemView() {
        return new BaseItemViewSelector<BlogItemViewModel>() {
            @Override
            public void select(ItemView itemView, int position, BlogItemViewModel item) {
                itemView.set(BR.viewModel, R.layout.item_mine_blog);
            }
        };
    }
}