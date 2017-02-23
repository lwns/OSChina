package com.core.op.feature.back.mine.message;

import com.core.op.lib.base.BViewModel;
import com.core.op.lib.di.PerActivity;
import com.domain.bean.Mention;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import javax.inject.Inject;

/**
 * @author op
 * @version 1.0
 * @description
 * @createDate 2017/2/13
 */
@PerActivity
public class MessageItemViewModel extends BViewModel {

    public final Mention data;

    @Inject
    public MessageItemViewModel(RxAppCompatActivity activity, Mention data) {
        super(activity);
        this.data = data;
    }

}
