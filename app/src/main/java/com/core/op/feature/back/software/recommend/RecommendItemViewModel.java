package com.core.op.feature.back.software.recommend;

import com.core.op.feature.detail.software.SoftwareDetailActivity;
import com.core.op.lib.base.BViewModel;
import com.core.op.lib.command.ReplyCommand;
import com.domain.bean.SoftwareDec;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

/**
 * @author op
 * @version 1.0
 * @description
 * @createDate 2017/1/11
 */
public class RecommendItemViewModel extends BViewModel {

    public SoftwareDec softwareDec;

    public final ReplyCommand itemClick = new ReplyCommand(() -> {
        SoftwareDetailActivity.instance(activity, softwareDec.getId());
    });

    public RecommendItemViewModel(RxAppCompatActivity activity, SoftwareDec softwareDec) {
        super(activity);
        this.softwareDec = softwareDec;
    }

}
