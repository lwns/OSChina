package com.core.op.feature.detail.base;

import com.core.op.lib.base.BViewModel;
import com.core.op.lib.di.PerActivity;
import com.domain.bean.About;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import javax.inject.Inject;

/**
 * @author op
 * @version 1.0
 * @description
 * @createDate 2016/12/29
 */
@PerActivity
public class DetailAboutViewModel extends BViewModel {

    public About about;

    @Inject
    public DetailAboutViewModel(RxAppCompatActivity activity, About about) {
        super(activity);
        this.about = about;
    }

}
