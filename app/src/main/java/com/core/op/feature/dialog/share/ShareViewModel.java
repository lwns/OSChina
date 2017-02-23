package com.core.op.feature.dialog.share;


import com.core.op.databinding.DlgShareBinding;
import com.core.op.lib.base.BViewModel;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import javax.inject.Inject;

public class ShareViewModel extends BViewModel<DlgShareBinding> {


    @Inject
    public ShareViewModel(RxAppCompatActivity activity) {
        super(activity);
    }

}