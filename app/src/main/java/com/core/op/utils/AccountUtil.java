package com.core.op.utils;

import android.app.Application;
import android.content.Context;
import android.text.TextUtils;

import com.core.op.lib.utils.PreferenceUtil;
import com.domain.bean.User;
import com.orhanobut.logger.Logger;

/**
 * 账户辅助类，
 * 用于更新用户信息和保存当前账户等操作
 */
public final class AccountUtil {
    private User user;
    private Application application;
    private static AccountUtil instances;

    private AccountUtil(Application application) {
        this.application = application;
    }

    public static void init(Application application) {
        instances = new AccountUtil(application);
    }

    public static boolean isLogin(Context context) {
        return getUserId() > 0;
//        return getUserId() > 0 && !TextUtils.isEmpty(getCookie(context));
    }

    public static String getCookie(Context context) {
        String cookie = PreferenceUtil.readString(context, "cookie", "cookie");
        return cookie == null ? "" : cookie;
    }

    public static long getUserId() {
        return getUser().getId();
    }

    public synchronized static User getUser() {
        if (instances == null) {
            Logger.e("AccountHelper instances is null, you need call init() method.");
            return new User();
        }
        if (instances.user == null)
            instances.user = PreferenceUtil.loadFormSource(instances.application, User.class);
        if (instances.user == null)
            instances.user = new User();
        return instances.user;
    }

    public static void updateUserCache(User user) {
        if (user == null)
            return;
        // 保留Cookie信息
//        if (TextUtils.isEmpty(user.getCookie()) && instances.user != user)
//            user.setCookie(instances.user.getCookie());
        instances.user = user;
        PreferenceUtil.save(instances.application, user);
    }

    public static void clearUserCache() {
        instances.user = null;
        PreferenceUtil.remove(instances.application, User.class);
        PreferenceUtil.remove(instances.application, "cookie");
    }
}
