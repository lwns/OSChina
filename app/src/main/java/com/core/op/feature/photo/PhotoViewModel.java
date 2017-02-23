package com.core.op.feature.photo;


import android.databinding.ObservableField;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.style.ReplacementSpan;
import android.view.View;

import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.core.op.AppConfig;
import com.core.op.R;
import com.core.op.databinding.ActPhotoBinding;
import com.core.op.feature.dialog.option.OptionDialog;
import com.core.op.feature.dialog.option.OptionViewModel;
import com.core.op.lib.base.BAViewModel;
import com.core.op.lib.command.ReplyCommand;
import com.core.op.lib.di.PerActivity;
import com.core.op.lib.messenger.Messenger;
import com.core.op.lib.utils.AppToast;
import com.core.op.lib.utils.DeviceUtil;
import com.core.op.lib.utils.ImageUtil;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import java.io.IOException;

import javax.inject.Inject;

@PerActivity
public class PhotoViewModel extends BAViewModel<ActPhotoBinding> {

    OptionDialog dialog;
    public String url;

    public ObservableField<Integer> loading = new ObservableField<>(View.VISIBLE);
    public ObservableField<Integer> optionVisible = new ObservableField<>(View.GONE);
    public ObservableField<Integer> imageVisible = new ObservableField<>(View.GONE);
    public final ReplyCommand<GlideDrawable> onSuccessCommand = new ReplyCommand<>(drawable -> {
        loading.set(View.GONE);
        optionVisible.set(View.VISIBLE);
        imageVisible.set(View.VISIBLE);
    });

    public final ReplyCommand imageClick = new ReplyCommand(() -> {
        activity.finish();
    });

    public final ReplyCommand optionClick = new ReplyCommand(() -> {
        if (!dialog.isShowing()) {
            dialog.show();
        }
    });

    @Inject
    public PhotoViewModel(RxAppCompatActivity activity, OptionDialog dialog) {
        super(activity);
        url = activity.getIntent().getStringExtra("url");
        this.dialog = dialog;
    }

    @Override
    public void afterViews() {
        Messenger.getDefault().register(this, OptionViewModel.OPTION_COPY_URL, () -> {
            DeviceUtil.copyTextToBoard(activity, url);
            AppToast.show(activity, "已复制到剪贴板");
            dialog.dismiss();
        });
        Messenger.getDefault().register(this, OptionViewModel.OPTION_SAVE_IMAGE, () -> {
            final String filePath = AppConfig.DEFAULT_SAVE_IMAGE_PATH
                    + getFileName(url);

            Drawable drawable = binding.photoview.getDrawable();
            if (drawable != null && drawable instanceof BitmapDrawable) {
                Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
                try {
                    ImageUtil.saveImageToSD(activity, filePath, bitmap, 100);
                    AppToast.show(activity, activity.getString(R.string.app_mine_head_save_tip));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            dialog.dismiss();
        });
        Messenger.getDefault().register(this, OptionViewModel.OPTION_SEND_IMAGE, () -> {
            dialog.dismiss();
        });
    }


    private String getFileName(String imgUrl) {
        int index = imgUrl.lastIndexOf('/') + 1;
        if (index == -1) {
            return System.currentTimeMillis() + ".jpeg";
        }
        return imgUrl.substring(index);
    }
}