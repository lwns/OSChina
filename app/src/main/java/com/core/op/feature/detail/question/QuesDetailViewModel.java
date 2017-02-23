package com.core.op.feature.detail.question;


import com.core.op.R;
import com.core.op.base.BaseFragment;
import com.core.op.feature.detail.base.act.DetailViewModel;
import com.core.op.feature.detail.news.frg.NewsDetailFragment;
import com.core.op.feature.detail.question.frg.QuesDetailFragment;
import com.core.op.lib.di.PerActivity;
import com.domain.bean.QuestionDetail;
import com.domain.interactor.main.QuesDetailUseCase;
import com.orhanobut.logger.Logger;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import javax.inject.Inject;
import javax.inject.Named;

@PerActivity
public class QuesDetailViewModel extends DetailViewModel<QuestionDetail> {

    QuesDetailUseCase useCase;

    @Inject
    public QuesDetailViewModel(RxAppCompatActivity activity,
                               @Named("QuesDetailUseCase") QuesDetailUseCase questionUseCase) {
        super(activity);
        this.useCase = questionUseCase;
        title.set(activity.getString(R.string.app_question_detail_title));
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
        return QuesDetailFragment.instance(data);
    }
}