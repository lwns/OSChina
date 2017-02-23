package com.core.op.feature.detail.active;


import com.core.op.R;
import com.core.op.base.BaseFragment;
import com.core.op.feature.detail.active.frg.ActiveDetailFragment;
import com.core.op.feature.detail.base.act.DetailViewModel;
import com.core.op.feature.detail.blog.frg.BlogDetailFragment;
import com.core.op.lib.di.PerActivity;
import com.domain.bean.EventDetail;
import com.domain.interactor.main.EventDetailUseCase;
import com.orhanobut.logger.Logger;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import javax.inject.Inject;
import javax.inject.Named;

@PerActivity
public class ActiveDetailViewModel extends DetailViewModel<EventDetail> {


    EventDetailUseCase useCase;

    @Inject
    public ActiveDetailViewModel(RxAppCompatActivity activity,
                                 @Named("EventDetailUseCase") EventDetailUseCase useCase) {
        super(activity);
        this.useCase = useCase;
        title.set(activity.getString(R.string.app_active_detail_title));
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
        return ActiveDetailFragment.instance(data);
    }
}