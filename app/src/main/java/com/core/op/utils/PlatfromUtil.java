package com.core.op.utils;

import android.content.Context;

import com.core.op.R;


public class PlatfromUtil {
    
    public final static int CLIENT_MOBILE = 2;
    public final static int CLIENT_ANDROID = 3;
    public final static int CLIENT_IPHONE = 4;
    public final static int CLIENT_WINDOWS_PHONE = 5;
    public final static int CLIENT_WECHAT = 6;

    public static String setPlatFromString(Context context, int platfrom) {
        String device = "";
        switch (platfrom) {
            case CLIENT_MOBILE:
                device = context.getString(R.string.app_device_mobile);
                break;
            case CLIENT_ANDROID:
                device = context.getString(R.string.app_device_android);
                break;
            case CLIENT_IPHONE:
                device = context.getString(R.string.app_device_iphone);
                break;
            case CLIENT_WINDOWS_PHONE:
                device = context.getString(R.string.app_device_windowsphone);
                break;
            case CLIENT_WECHAT:
                device = context.getString(R.string.app_device_wechat);
                break;
            default:
                device = "";
        }
        return device;
    }
}
