package com.core.op.feature.main;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.databinding.ObservableField;

import com.core.op.AppConfig;
import com.core.op.MainApplication;
import com.core.op.R;
import com.core.op.data.util.CacheUtil;
import com.core.op.databinding.IncludeNavigationHeaderBinding;
import com.core.op.feature.back.SimpleBackActivity;
import com.core.op.feature.back.SimpleBackPage;
import com.core.op.feature.login.LoginActivity;
import com.core.op.feature.photo.PhotoActivity;
import com.core.op.lib.base.BViewModel;
import com.core.op.lib.command.ReplyCommand;
import com.core.op.lib.di.PerActivity;
import com.core.op.utils.CameraUtil;
import com.core.op.utils.DialogUtil;
import com.domain.bean.UserV2;
import com.domain.interactor.user.UploadHeadUseCase;
import com.domain.interactor.user.UserInfoUseCase;
import com.domain.interfaces.OnProgressListener;
import com.orhanobut.logger.Logger;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import javax.inject.Inject;
import javax.inject.Named;

import static com.core.op.AppConfig.CACHE_USERINFO;
import static com.core.op.lib.utils.StrUtil.formatCount;

/**
 * @author op
 * @version 1.0
 * @description
 * @createDate 2016/11/11
 */
@PerActivity
public class NavHeaderViewModel extends BViewModel<IncludeNavigationHeaderBinding> {

    public static final int ACTION_TYPE_ALBUM = 0;
    public static final int ACTION_TYPE_PHOTO = 1;

    public static final String USER_HEAD = "USER_HEAD";

    CameraUtil cameraUtil;
    UserInfoUseCase userInfoUseCase;
    UploadHeadUseCase uploadHeadUseCase;

    private long userId;

    public ObservableField<UserV2> userInfo = new ObservableField<>();

    public ObservableField<String> score = new ObservableField<>();

    public final ReplyCommand clickCommand = new ReplyCommand(() -> {
        verifyLogin();
    });

    public final ReplyCommand headCommand = new ReplyCommand(() -> {
        if (verifyLogin()) {
            showClickAvatar();
        }
    });

    public final ReplyCommand settingClick = new ReplyCommand(() -> {
        SimpleBackActivity.instance(activity, SimpleBackPage.SETTING);
    });


    @Inject
    public NavHeaderViewModel(RxAppCompatActivity activity,
                              @Named("UserInfoUseCase") UserInfoUseCase userInfoUseCase,
                              @Named("UploadHeadUseCase") UploadHeadUseCase uploadHeadUseCase,
                              MainApplication application) {
        super(activity);
        this.userId = application.getLoginUid();
        this.userInfoUseCase = userInfoUseCase;
        this.uploadHeadUseCase = uploadHeadUseCase;
    }

    public void afterViews() {
        cameraUtil = new CameraUtil(activity, "USER_HEAD", binding.ivPortrait);
        cameraUtil.setOnResultLisetener((index, path) -> {
            uploadHeadUseCase.setParams(path);
            uploadHeadUseCase.setOnProgressListener((current, count) -> {
                Logger.i(current + ":" + count);
            });
            uploadHeadUseCase.execute()
                    .compose(this.activity.bindToLifecycle())
                    .subscribe();
        });

        IntentFilter filter = new IntentFilter(
                AppConfig.INTENT_ACTION_USER_CHANGE);
        filter.addAction(AppConfig.INTENT_ACTION_LOGOUT);
        activity.registerReceiver(mReceiver, filter);

        userInfo.set((UserV2) CacheUtil.readObject(activity, CACHE_USERINFO));
    }

    public void unRegisterBroadCast() {
        if (mReceiver != null) {
            activity.unregisterReceiver(mReceiver);
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        cameraUtil.result(requestCode, data);
    }

    private boolean verifyLogin() {
        if (userInfo.get() == null) {
            LoginActivity.instance(activity);
            return false;
        }
        return true;
    }

    private void showClickAvatar() {
        DialogUtil.getSelectDialog(activity,
                activity.getString(R.string.app_mine_head_title),
                activity.getResources().getStringArray(R.array.app_mine_head_select),
                (dialog, position) -> {
                    if (position == 0) {
                        handleSelectPicture();
                    } else {
                        PhotoActivity.instance(getActivity(), userInfo.get().getPortrait().replace("_[0-9]{1,3}", "_200"));
                    }
                }).show();
    }

    /**
     * show select-picture  dialog
     */
    private void handleSelectPicture() {
        DialogUtil.getSelectDialog(activity,
                activity.getString(R.string.app_mine_head_change_title),
                activity.getResources().getStringArray(R.array.app_mine_head_chang),
                (dialog, position) -> {
                    goToSelectPicture(position);
                }).show();
    }

    /**
     * select picture
     *
     * @param position action position
     */
    private void goToSelectPicture(int position) {
        switch (position) {
            case ACTION_TYPE_ALBUM:
                cameraUtil.startPicture(0);
                break;
            case ACTION_TYPE_PHOTO:
                cameraUtil.startCamera(0);
                break;
            default:
                break;
        }
    }


    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(AppConfig.INTENT_ACTION_USER_CHANGE)) {
                userInfo.set((UserV2) CacheUtil.readObject(activity, AppConfig.CACHE_USERINFO));
                score.set(String.format("%s  %s", activity.getString(R.string.app_mine_score), formatCount(userInfo.get().getStatistics().getScore())));
            } else if (intent.getAction().equals(AppConfig.INTENT_ACTION_LOGOUT)) {
                userInfo.set(null);
                score.set("");
            }
        }
    };
}
