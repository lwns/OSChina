package com.core.op.feature.scan;


import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Rect;
import android.os.Bundle;
import android.text.ClipboardManager;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.core.op.R;
import com.core.op.databinding.ActScanBinding;
import com.core.op.feature.login.LoginActivity;
import com.core.op.lib.AppException;
import com.core.op.lib.base.BAViewModel;
import com.core.op.lib.di.PerActivity;
import com.core.op.lib.utils.AppToast;
import com.core.op.lib.utils.StrUtil;
import com.core.op.utils.AccountUtil;
import com.core.op.utils.DialogUtil;
import com.core.op.zxing.activity.CaptureViewModel;
import com.core.op.zxing.camera.CameraManager;
import com.core.op.zxing.decode.DecodeThread;
import com.core.op.zxing.utils.BeepManager;
import com.core.op.zxing.utils.CaptureActivityHandler;
import com.core.op.zxing.utils.InactivityTimer;
import com.google.zxing.Result;
import com.orhanobut.logger.Logger;
import com.tbruyelle.rxpermissions.RxPermissions;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;

import javax.inject.Inject;


@PerActivity
public class ScanViewModel extends CaptureViewModel<ActScanBinding> {

    private static String TAG = ScanViewModel.class.getSimpleName();

    public final String title;

    RxPermissions rxPermissions;

    private boolean isHasSurface = false;

    @Inject
    public ScanViewModel(RxAppCompatActivity activity) {
        super(activity);
        rxPermissions = new RxPermissions(activity);
        title = activity.getString(R.string.app_scan);
    }

    @Override
    public void afterViews() {
        binding.capturePreview.getHolder().addCallback(this);

        cameraTask();
    }

    @Override
    public void onResume() {
        if (binding.capturePreview != null) {
            handler = null;
            if (isHasSurface) {
                // The activity was paused but not stopped, so the surface still
                // exists. Therefore
                // surfaceCreated() won't be called, so init the camera here.
                initCamera(binding.capturePreview.getHolder());
            }
        }
        if (inactivityTimer != null) {
            inactivityTimer.onResume();
        }
        super.onResume();
    }

    @Override
    public void onPause() {
        if (handler != null) {
            handler.quitSynchronously();
            handler = null;
        }
        if (inactivityTimer != null) {
            inactivityTimer.onPause();
        }
        if (beepManager != null) {
            beepManager.close();
        }
        if (cameraManager != null) {
            cameraManager.closeDriver();
        }
        super.onPause();
    }

    @Override
    public void onDestroy() {
        if (inactivityTimer != null) {
            inactivityTimer.shutdown();
        }
        if (binding.capturePreview != null) {
            binding.capturePreview.getHolder().removeCallback(this);
        }
        super.onDestroy();
    }

    @Override
    public void handleDecode(Result rawResult, Bundle bundle) {
        inactivityTimer.onActivity();
        beepManager.playBeepSoundAndVibrate();

        handler.postDelayed(new Runnable() {

            @Override
            public void run() {
                handleText(rawResult.getText());
            }
        }, 800);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        isHasSurface = true;
        initCamera(holder);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        isHasSurface = false;
    }


    private void handleText(String text) {

//        if (StrUtil.isUrl(text)) {
//            showUrlOption(text);
//        } else {
        handleOtherText(text);
        AppToast.show(activity, text);
//        }
    }

    private void handleOtherText(final String text) {
        // 判断是否符合基本的json格式
        if (!text.matches("^\\{.*")) {
            showCopyTextOption(text);
        } else {
            try {
                BarCode barcode = BarCode.parse(text);
                int type = barcode.getType();
                switch (type) {
                    case BarCode.SIGN_IN:// 签到
                        handleSignIn(barcode);
                        break;
                    default:
                        break;
                }
            } catch (AppException e) {
                showCopyTextOption(text);
            }
        }
    }

    private void handleSignIn(BarCode barCode) {
        if (barCode.isRequireLogin() && !AccountUtil.isLogin(activity)) {
            showLogin();
            return;
        }
    }

    private void showLogin() {
        DialogUtil.getConfirmDialog(activity, "请先登录，再进行", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                LoginActivity.instance(activity);
            }
        }).show();
    }

    private void showCopyTextOption(final String text) {
        DialogUtil.getConfirmDialog(activity, text, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                ClipboardManager cbm = (ClipboardManager) activity.getSystemService(Context.CLIPBOARD_SERVICE);
                cbm.setText(text);
                AppToast.show(activity, "复制成功");
                activity.finish();
            }
        }, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                activity.finish();
            }
        }).show();
    }

    private void cameraTask() {
//        rxPermissions.requestEach(Manifest.permission.CAMERA, Manifest.permission.VIBRATE)
//                .subscribe(permission -> { // will emit 2 Permission objects
//                    if (permission.granted) {
//                        initCamera();
//                        // `permission.name` is granted !
//                    } else if (permission.shouldShowRequestPermissionRationale) {
//                        // Denied permission without ask never again
//                    } else {
//                        // Denied permission with ask never again
//                        // Need to go to the settings
//                    }
//                });
        rxPermissions.request(Manifest.permission.VIBRATE, Manifest.permission.CAMERA)
                .subscribe(granted -> {
                    if (granted) {
                        initCamera();
                    } else {
                        AppToast.show(activity, activity.getString(R.string.app_permission_camer_refuse));
                    }
                });
    }


    private void initCamera(SurfaceHolder surfaceHolder) {
        if (cameraManager == null)
            return;

        if (surfaceHolder == null) {
            throw new IllegalStateException("No SurfaceHolder provided");
        }
        if (cameraManager.isOpen()) {
            Logger.w(TAG,
                    "initCamera() while already open -- late SurfaceView callback?");
            return;
        }
        try {
            cameraManager.openDriver(surfaceHolder);
            // Creating the handler starts the preview, which can also throw a
            // RuntimeException.
            if (handler == null) {
                handler = new CaptureActivityHandler(this, cameraManager,
                        DecodeThread.ALL_MODE);
            }

            initCrop();
        } catch (IOException | RuntimeException e) {
            Logger.w(TAG, "Unexpected error initializing camera", e);
        }
    }

    /**
     * 初始化截取的矩形区域
     */
    @SuppressWarnings("SuspiciousNameCombination")
    private void initCrop() {
        int cameraWidth = cameraManager.getCameraResolution().y;
        int cameraHeight = cameraManager.getCameraResolution().x;

        /** 获取布局中扫描框的位置信息 */
        int[] location = new int[2];
        binding.captureCropView.getLocationInWindow(location);

        int cropLeft = location[0];
        int cropTop = location[1] - getStatusBarHeight();

        int cropWidth = binding.captureCropView.getWidth();
        int cropHeight = binding.captureCropView.getHeight();

        /** 获取布局容器的宽高 */
        int containerWidth = binding.captureContainer.getWidth();
        int containerHeight = binding.captureContainer.getHeight();

        /** 计算最终截取的矩形的左上角顶点x坐标 */
        int x = cropLeft * cameraWidth / containerWidth;
        /** 计算最终截取的矩形的左上角顶点y坐标 */
        int y = cropTop * cameraHeight / containerHeight;

        /** 计算最终截取的矩形的宽度 */
        int width = cropWidth * cameraWidth / containerWidth;
        /** 计算最终截取的矩形的高度 */
        int height = cropHeight * cameraHeight / containerHeight;

        /** 生成最终的截取的矩形 */
        mCropRect = new Rect(x, y, width + x, height + y);
    }

    private int getStatusBarHeight() {
        try {
            Class<?> c = Class.forName("com.android.internal.R$dimen");
            Object obj = c.newInstance();
            Field field = c.getField("status_bar_height");
            int x = Integer.parseInt(field.get(obj).toString());
            return activity.getResources().getDimensionPixelSize(x);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }


    private void initCamera() {

        inactivityTimer = new InactivityTimer(activity);
        beepManager = new BeepManager(this);

        TranslateAnimation animation = new TranslateAnimation(
                Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, 0.9f);
        animation.setDuration(4500);
        animation.setRepeatCount(-1);
        animation.setRepeatMode(Animation.RESTART);
        binding.captureScanLine.startAnimation(animation);

        cameraManager = new CameraManager(activity.getApplication());
    }
}