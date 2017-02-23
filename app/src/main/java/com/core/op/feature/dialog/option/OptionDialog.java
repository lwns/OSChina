package com.core.op.feature.dialog.option;

import com.core.op.R;
import com.core.op.base.BaseDialog;
import com.core.op.databinding.DlgOptionBinding;
import com.core.op.lib.base.BDialog;
import com.core.op.lib.utils.inject.AfterViews;
import com.core.op.lib.utils.inject.RootView;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import javax.inject.Inject;

@RootView(R.layout.dlg_option)
public final class OptionDialog extends BaseDialog<OptionViewModel, DlgOptionBinding> {

    @Inject
    public OptionDialog(RxAppCompatActivity activity, OptionViewModel viewModel) {
        super(BDialog.newDialog(activity), viewModel);
    }

    @AfterViews
    void afterViews() {
    }
}
