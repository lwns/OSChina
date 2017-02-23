package com.core.op.feature.back.setting;


import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.FragmentManager;

import com.core.op.AppConfig;
import com.core.op.MainApplication;
import com.core.op.data.util.CacheUtil;
import com.core.op.databinding.FrgSettingBinding;
import com.core.op.lib.base.BFViewModel;
import com.core.op.lib.base.BViewModel;
import com.core.op.lib.command.ReplyCommand;
import com.core.op.lib.di.PerActivity;
import com.core.op.lib.utils.AppToast;
import com.core.op.utils.AccountUtil;
import com.core.op.utils.DialogUtil;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;
import com.trello.rxlifecycle.components.support.RxFragment;

import javax.inject.Inject;

import static android.R.attr.data;
import static com.core.op.AppConfig.CACHE_USERINFO;

@PerActivity
public class SettingViewModel extends BFViewModel<FrgSettingBinding> {
    MainApplication application;
    public final ReplyCommand cleanClick = new ReplyCommand(() -> {
        DialogUtil.getConfirmDialog(getActivity(), "是否清空缓存?", new DialogInterface.OnClickListener
                () {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                AppToast.show(activity, "缓存清楚成功！");
            }
        }).show();
    });

    public final ReplyCommand exitClick = new ReplyCommand(() -> {
        AccountUtil.clearUserCache();
        CacheUtil.deleteObject(activity, CACHE_USERINFO);
        activity.sendBroadcast(new Intent(AppConfig.INTENT_ACTION_LOGOUT));
        AppToast.show(activity, "注销成功！");
    });

    public final ReplyCommand updateClick = new ReplyCommand(() -> {
        AccountUtil.clearUserCache();
        AppToast.show(activity, "敬请期待！");
    });
    public final ReplyCommand aboutClick = new ReplyCommand(() -> {
        AccountUtil.clearUserCache();
        AppToast.show(activity, "敬请期待！");
    });
    public final ReplyCommand ideaClick = new ReplyCommand(() -> {
        AccountUtil.clearUserCache();
        AppToast.show(activity, "敬请期待！");
    });

    @Inject
    public SettingViewModel(RxAppCompatActivity activity,
                            MainApplication application) {
        super(activity);
        this.application = application;
    }

    @Override
    public void afterViews() {

    }
}