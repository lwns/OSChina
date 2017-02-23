package com.core.op.feature.detail.news.frg;

import com.core.op.lib.base.BViewModel;
import com.core.op.lib.di.PerActivity;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import javax.inject.Inject;

/**
 * @author op
 * @version 1.0
 * @description
 * @createDate 2016/12/29
 */
@PerActivity
public class NewsDetailCommentViewModel extends BViewModel {

    @Inject
    public NewsDetailCommentViewModel(RxAppCompatActivity activity) {
        super(activity);
    }

}
