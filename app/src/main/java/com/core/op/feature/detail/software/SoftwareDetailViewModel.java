package com.core.op.feature.detail.software;


import com.core.op.R;
import com.core.op.base.BaseFragment;
import com.core.op.feature.detail.base.act.DetailViewModel;
import com.core.op.feature.detail.software.frg.SoftwareDetailFragment;
import com.core.op.lib.base.BAViewModel;
import com.core.op.lib.di.PerActivity;
import com.domain.bean.SoftwareDec;
import com.domain.bean.SoftwareDetail;
import com.domain.interactor.main.NewsDetailUseCase;
import com.domain.interactor.main.SoftwareDetailUseCase;
import com.orhanobut.logger.Logger;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import javax.inject.Inject;

@PerActivity
public class SoftwareDetailViewModel extends DetailViewModel<SoftwareDetail> {

    SoftwareDetailUseCase useCase;

    @Inject
    public SoftwareDetailViewModel(RxAppCompatActivity activity,
                                   SoftwareDetailUseCase useCase) {
        super(activity);
        this.useCase = useCase;
        title.set(activity.getString(R.string.app_software_detail));
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
        return SoftwareDetailFragment.instance(data);
    }
}