package com.core.op.feature.detail.blog;


import android.widget.TextView;

import com.core.op.R;
import com.core.op.base.BaseFragment;
import com.core.op.feature.detail.base.act.DetailViewModel;
import com.core.op.feature.detail.blog.frg.BlogDetailFragment;
import com.core.op.lib.di.PerActivity;
import com.domain.bean.BlogDetail;
import com.domain.interactor.main.BlogDetailUseCase;
import com.orhanobut.logger.Logger;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import javax.inject.Inject;
import javax.inject.Named;

@PerActivity
public class BlogDetailViewModel extends DetailViewModel<BlogDetail> {


    BlogDetailUseCase useCase;

    @Inject
    public BlogDetailViewModel(RxAppCompatActivity activity,
                               @Named("BlogDetailUseCase") BlogDetailUseCase useCase) {
        super(activity);
        this.useCase = useCase;
        title.set(activity.getString(R.string.app_blog_detail_title));
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
        return BlogDetailFragment.instance(data);
    }
}