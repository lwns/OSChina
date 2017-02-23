package com.core.op.feature.photo;

import android.content.Context;
import android.content.Intent;

import com.core.op.R;
import com.core.op.base.BaseActivity;
import com.core.op.databinding.ActPhotoBinding;
import com.core.op.di.components.DaggerPhotoComponent;
import com.core.op.di.components.PhotoComponent;
import com.core.op.di.modules.PhotoModule;

import com.core.op.lib.utils.inject.AfterViews;
import com.core.op.lib.utils.inject.BeforeViews;
import com.core.op.lib.utils.inject.RootView;


@RootView(R.layout.act_photo)
public final class PhotoActivity extends BaseActivity<PhotoViewModel, ActPhotoBinding> {

    PhotoComponent component;

    public final static void instance(Context context, String imageUrl) {
        Intent intent = new Intent(context, PhotoActivity.class);
        intent.putExtra("url", imageUrl);
        context.startActivity(intent);
    }

    @BeforeViews
    void beforViews() {
        component = DaggerPhotoComponent.builder()
                .appComponent(getApplicationComponent())
                .activityModule(getActivityModule())
                .photoModule(new PhotoModule())
                .build();
        component.inject(this);
    }

    @AfterViews
    void afterViews() {
    }
}
