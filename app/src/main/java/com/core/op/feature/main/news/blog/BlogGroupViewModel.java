package com.core.op.feature.main.news.blog;

import com.core.op.lib.base.BViewModel;
import com.core.op.lib.di.PerActivity;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import javax.inject.Inject;

/**
 * @author op
 * @version 1.0
 * @description
 * @createDate 2016/11/16
 */
@PerActivity
public class BlogGroupViewModel extends BViewModel {

    RxAppCompatActivity activity;

    @Inject
    public BlogGroupViewModel(RxAppCompatActivity activity) {
        this.activity = activity;
    }

}
