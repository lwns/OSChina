package com.core.op.feature.dialog.option;


import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import com.core.op.AppConfig;
import com.core.op.R;
import com.core.op.databinding.DlgOptionBinding;
import com.core.op.lib.base.BViewModel;
import com.core.op.lib.command.ReplyCommand;
import com.core.op.lib.di.PerActivity;
import com.core.op.lib.messenger.Messenger;
import com.core.op.lib.utils.DeviceUtil;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import java.io.IOException;

import javax.inject.Inject;

@PerActivity
public class OptionViewModel extends BViewModel<DlgOptionBinding> {

    public final static String OPTION_SAVE_IMAGE = "OPTION_SAVE_IMAGE";
    public final static String OPTION_SEND_IMAGE = "OPTION_SEND_IMAGE";
    public final static String OPTION_COPY_URL = "OPTION_COPY_URL";

    public final ReplyCommand saveClick = new ReplyCommand(() -> {
        Messenger.getDefault().sendNoMsg(OPTION_SAVE_IMAGE);
    });

    public final ReplyCommand sendClick = new ReplyCommand(() -> {
        Messenger.getDefault().sendNoMsg(OPTION_SEND_IMAGE);

    });

    public final ReplyCommand copyClick = new ReplyCommand(() -> {
        Messenger.getDefault().sendNoMsg(OPTION_COPY_URL);
    });

    @Inject
    public OptionViewModel(RxAppCompatActivity activity) {
        super(activity);
    }

}