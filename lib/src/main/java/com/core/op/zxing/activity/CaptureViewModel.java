package com.core.op.zxing.activity;

import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.view.SurfaceHolder;

import com.core.op.lib.base.BAViewModel;
import com.core.op.zxing.camera.CameraManager;
import com.core.op.zxing.utils.BeepManager;
import com.core.op.zxing.utils.CaptureActivityHandler;
import com.core.op.zxing.utils.InactivityTimer;
import com.google.zxing.Result;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

/**
 * @author op
 * @version 1.0
 * @description
 * @createDate 2017/1/13
 */
public abstract class CaptureViewModel<T> extends BAViewModel<T> implements SurfaceHolder.Callback {

    protected CameraManager cameraManager;
    protected CaptureActivityHandler handler;
    protected InactivityTimer inactivityTimer;
    protected BeepManager beepManager;

    protected Rect mCropRect = null;

    public CaptureViewModel(RxAppCompatActivity activity) {
        super(activity);
    }

    public abstract void handleDecode(final Result rawResult, Bundle bundle);

    public CameraManager getCameraManager() {
        return cameraManager;
    }

    public Handler getHandler() {
        return handler;
    }

    public Rect getCropRect() {
        return mCropRect;
    }
}
