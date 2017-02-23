package com.core.op.feature.back.pub;


import com.core.op.MainApplication;
import com.core.op.R;
import com.core.op.lib.base.BFViewModel;
import com.core.op.lib.command.ReplyCommand;
import com.core.op.lib.di.PerActivity;
import com.core.op.lib.utils.AppToast;
import com.core.op.lib.utils.StrUtil;
import com.domain.interactor.main.PubUseCase;
import com.orhanobut.logger.Logger;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import com.core.op.databinding.FrgPubBinding;

import javax.inject.Inject;

@PerActivity
public class PubViewModel extends BFViewModel<FrgPubBinding> {
    public static final int MAX_TEXT_LENGTH = 160;
    public final ReplyCommand sendClick = new ReplyCommand(() -> {
        send();
    });

    PubUseCase useCase;

    MainApplication application;

    @Inject
    public PubViewModel(RxAppCompatActivity activity,
                        PubUseCase useCase,
                        MainApplication application) {
        super(activity);
        this.useCase = useCase;
        this.application = application;
    }

    @Override
    public void afterViews() {
    }

    private void send() {
        if (application.getLoginUid() == 0) {
            AppToast.show(activity, activity.getString(R.string.app_login_error));
            return;
        }
        if (StrUtil.isEmpty(binding.editContent.getText().toString())) {
            AppToast.show(activity, activity.getString(R.string.app_pub_empty));
            return;
        }

        if (binding.editContent.getText().toString().length() > MAX_TEXT_LENGTH) {
            AppToast.show(activity, activity.getString(R.string.app_pub_long));
            return;
        }

        useCase.setParams(binding.editContent.getText().toString());

        useCase.execute()
                .compose(activity.bindToLifecycle())
                .subscribe((data) -> {
                    AppToast.show(activity, activity.getString(R.string.app_pub_send_success));
                }, error -> {
                    AppToast.show(activity, activity.getString(R.string.app_pub_send_fail));
                }, () -> {
                });
    }
}