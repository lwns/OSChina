package com.core.op.lib.utils;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.ClipboardManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Resources;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.PowerManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import com.core.op.BaseApplication;

import java.io.File;
import java.lang.reflect.Field;
import java.util.List;

/**
 * @author op
 * @version 1.0
 * @description
 * @createDate 2015/5/21
 */
public class DeviceUtil {

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 sp
     */
    public static int px2sp(Context context, float pxValue) {
        float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 sp 的单位 转成为 px
     */
    public static int sp2px(Context context, float spValue) {
        float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    /**
     * 获取屏幕宽度
     */
    public static int getScreenW(Context context) {
        DisplayMetrics dm = new DisplayMetrics();
        dm = context.getResources().getDisplayMetrics();
        int w = dm.widthPixels;
        return w;
    }

    /**
     * 获取屏幕高度
     */
    public static int getScreenH(Context context) {
        DisplayMetrics dm = new DisplayMetrics();
        dm = context.getResources().getDisplayMetrics();
        int h = dm.heightPixels;
        // int h = aty.getWindowManager().getDefaultDisplay().getHeight();
        return h;
    }


    /**
     * 屏幕实际宽高
     *
     * @param activity
     * @return
     */
    public static int[] getRealScreenSize(Activity activity) {
        int[] size = new int[2];
        int screenWidth = 0, screenHeight = 0;
        WindowManager w = activity.getWindowManager();
        Display d = w.getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        d.getMetrics(metrics);
        screenWidth = metrics.widthPixels;
        screenHeight = metrics.heightPixels;
        if (Build.VERSION.SDK_INT >= 14 && Build.VERSION.SDK_INT < 17)
            try {
                screenWidth = (Integer) Display.class.getMethod("getRawWidth").invoke(d);
                screenHeight = (Integer) Display.class.getMethod("getRawHeight").invoke(d);
            } catch (Exception ignored) {
            }
        if (Build.VERSION.SDK_INT >= 17)
            try {
                Point realSize = new Point();
                Display.class.getMethod("getRealSize", Point.class).invoke(d, realSize);
                screenWidth = realSize.x;
                screenHeight = realSize.y;
            } catch (Exception ignored) {
            }
        size[0] = screenWidth;
        size[1] = screenHeight;
        return size;
    }

    /**
     * 状态栏高度// 默认为38，貌似大部分是这样的
     *
     * @param context
     * @return
     */
    public static int getStatusBarHeight(Context context) {
        Class<?> c = null;
        Object obj = null;
        Field field = null;
        int x = 0;
        try {
            c = Class.forName("com.android.internal.R$dimen");
            obj = c.newInstance();
            field = c.getField("status_bar_height");
            x = Integer.parseInt(field.get(obj).toString());
            return context.getResources().getDimensionPixelSize(x);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * actionBar高度
     *
     * @param context
     * @return
     */
    public static int getActionBarHeight(Context context) {
        int actionBarHeight = 0;
        TypedValue tv = new TypedValue();
        if (context.getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true))
            actionBarHeight = TypedValue.complexToDimensionPixelSize(tv.data, context.getResources().getDisplayMetrics());

        if (actionBarHeight == 0 && context.getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true)) {
            actionBarHeight = TypedValue.complexToDimensionPixelSize(tv.data, context.getResources().getDisplayMetrics());
        }

        return actionBarHeight;
    }

    /**
     * 是否有状态栏
     *
     * @param activity
     * @return
     */
    public static boolean hasStatusBar(Activity activity) {
        WindowManager.LayoutParams attrs = activity.getWindow().getAttributes();
        if ((attrs.flags & WindowManager.LayoutParams.FLAG_FULLSCREEN) == WindowManager.LayoutParams.FLAG_FULLSCREEN) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * 应用包是否存在
     *
     * @param pckName
     * @return
     */
    public static boolean isPackageExist(Context context, String pckName) {
        try {
            PackageInfo pckInfo = context.getPackageManager().getPackageInfo(pckName, 0);
            if (pckInfo != null)
                return true;
        } catch (PackageManager.NameNotFoundException e) {

        }
        return false;
    }

    /**
     * 隐藏软键盘
     *
     * @param view
     */
    public static void hideSoftKeyboard(Context context, View view) {
        if (view == null)
            return;
        ((InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    /**
     * 切换软键盘的状态
     * 如当前为收起变为弹出,若当前为弹出变为收起
     */
    public static void toggleSoftKeyboard(Context context) {
        InputMethodManager inputMethodManager =
                (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
    }


    /**
     * 是否是横屏
     *
     * @return
     */
    public static boolean isLandscape(Context context) {
        boolean flag;
        if (context.getResources().getConfiguration().orientation == 2)
            flag = true;
        else
            flag = false;
        return flag;
    }

    /**
     * 是否是竖屏
     *
     * @return
     */
    public static boolean isPortrait(Context context) {
        boolean flag = true;
        if (context.getResources().getConfiguration().orientation != 1)
            flag = false;
        return flag;
    }

    /**
     * 是否是平板
     *
     * @return
     */
    public static boolean isTablet(Context context) {
        if ((0xf & context.getResources().getConfiguration().screenLayout) >= 3) {
            return true;
        }
        return false;
    }

    /**
     * 跳转到应用市场
     *
     * @param context
     * @param pck
     */
    public static void gotoMarket(Context context, String pck) {
        if (!isHaveMarket(context)) {
            return;
        }
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("market://details?id=" + pck));
        if (intent.resolveActivity(context.getPackageManager()) != null) {
            context.startActivity(intent);
        }
    }

    /**
     * 是否有应用市场应用
     *
     * @return
     */
    public static boolean isHaveMarket(Context context) {
        Intent intent = new Intent();
        intent.setAction("android.intent.action.MAIN");
        intent.addCategory("android.intent.category.APP_MARKET");
        PackageManager pm = context.getPackageManager();
        List<ResolveInfo> infos = pm.queryIntentActivities(intent, 0);
        return infos.size() > 0;
    }

    /**
     * 设置全屏
     *
     * @param activity
     */
    public static void setFullScreen(Activity activity) {
        WindowManager.LayoutParams params = activity.getWindow().getAttributes();
        params.flags |= WindowManager.LayoutParams.FLAG_FULLSCREEN;
        activity.getWindow().setAttributes(params);
        activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
    }

    /**
     * 取消全屏
     *
     * @param activity
     */
    public static void cancelFullScreen(Activity activity) {
        WindowManager.LayoutParams params = activity.getWindow().getAttributes();
        params.flags &= (~WindowManager.LayoutParams.FLAG_FULLSCREEN);
        activity.getWindow().setAttributes(params);
        activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
    }

    /**
     * 包名信息
     *
     * @param pckName
     * @return
     */
    public static PackageInfo getPackageInfo(Context context, String pckName) {
        try {
            return context.getPackageManager().getPackageInfo(pckName, 0);
        } catch (PackageManager.NameNotFoundException e) {
        }
        return null;
    }

    /**
     * 版本号
     *
     * @return
     */
    public static int getVersionCode(Context context) {
        int versionCode = 0;
        try {
            versionCode = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionCode;
        } catch (PackageManager.NameNotFoundException ex) {
            versionCode = 0;
        }
        return versionCode;
    }

    /**
     * 某个包的版本号
     *
     * @param packageName
     * @return
     */
    public static int getVersionCode(Context context, String packageName) {
        int versionCode = 0;
        try {
            versionCode = context.getPackageManager().getPackageInfo(packageName, 0).versionCode;
        } catch (PackageManager.NameNotFoundException ex) {
            versionCode = 0;
        }
        return versionCode;
    }

    /**
     * 获取版本名
     *
     * @return
     */
    public static String getVersionName(Context context) {
        String name = "";
        try {
            name = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException ex) {
            name = "";
        }
        return name;
    }

    /**
     * 是否屏幕亮起
     *
     * @return
     */
    public static boolean isScreenOn(Context context) {
        PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        return pm.isScreenOn();
    }

    /**
     * 安装apk包
     *
     * @param context
     * @param file
     */
    public static void installAPK(Context context, File file) {
        if (file == null || !file.exists())
            return;
        Intent intent = new Intent();
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
        context.startActivity(intent);
    }

    /**
     * apk包的intent
     *
     * @param file
     * @return
     */
    public static Intent getInstallApkIntent(File file) {
        Intent intent = new Intent();
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
        return intent;
    }

    /**
     * 获取一个apk的信息
     *
     * @param cxt     应用上下文
     * @param apkPath apk所在绝对路径
     * @return
     */
    public static PackageInfo getAppInfo(Context cxt, String apkPath)
            throws PackageManager.NameNotFoundException {
        PackageManager pm = cxt.getPackageManager();
        PackageInfo pkgInfo = null;
        pkgInfo = pm.getPackageArchiveInfo(apkPath,
                PackageManager.GET_ACTIVITIES);
        return pkgInfo;
    }

    /**
     * 获取一个apk中，在Manifest.xml中声明的第一个Activity的信息
     *
     * @param cxt     应用上下文
     * @param apkPath apk所在绝对路径
     * @return
     */
    public static ActivityInfo getActivityInfo(Context cxt,
                                               String apkPath) throws PackageManager.NameNotFoundException {
        return getActivityInfo(getAppInfo(cxt, apkPath), 0);
    }

    /**
     * 获取一个apk中，在Manifest.xml中声明的第index个Activity的信息<br>
     * 注意index的大小不正确可能会报ArrayIndexOutOfBoundsException
     *
     * @param cxt-    应用上下文
     * @param apkPath apk所在绝对路径
     * @param index   要获取的Activity在Manifest.xml中声明的序列（从0开始）
     * @return
     * @throws ArrayIndexOutOfBoundsException index超出范围会报
     */
    public static ActivityInfo getActivityInfo(Context cxt,
                                               String apkPath, int index) throws PackageManager.NameNotFoundException {
        return getActivityInfo(getAppInfo(cxt, apkPath), index);
    }

    /**
     * 获取一个apk中，在Manifest.xml中声明的第index个Activity的信息<br>
     * <b>注意</b>index的大小不正确可能会报ArrayIndexOutOfBoundsException
     *
     * @param pkgInfo Activity所在应用的PackageInfo
     * @param index   Activity在插件Manifest.xml中的序列（从0开始）
     * @return
     * @throws ArrayIndexOutOfBoundsException index超出范围会报
     */
    public static ActivityInfo getActivityInfo(PackageInfo pkgInfo,
                                               int index) {
        return pkgInfo.activities[index];
    }

    /**
     * 获取应用图标
     *
     * @param cxt     应用上下文
     * @param apkPath apk所在绝对路径
     * @return
     */
    public static Drawable getAppIcon(Context cxt, String apkPath)
            throws PackageManager.NameNotFoundException {
        PackageManager pm = cxt.getPackageManager();
        PackageInfo pkgInfo = getAppInfo(cxt, apkPath);
        if (pkgInfo == null) {
            return null;
        } else {
            ApplicationInfo appInfo = pkgInfo.applicationInfo;
            if (Build.VERSION.SDK_INT >= 8) {
                appInfo.sourceDir = apkPath;
                appInfo.publicSourceDir = apkPath;
            }
            return pm.getApplicationIcon(appInfo);
        }
    }

    /**
     * 获取指定APK应用名
     *
     * @param cxt     应用上下文
     * @param apkPath apk所在绝对路径
     * @return
     */
    public static CharSequence getAppName(Context cxt, String apkPath)
            throws PackageManager.NameNotFoundException {
        PackageManager pm = cxt.getPackageManager();
        PackageInfo pkgInfo = getAppInfo(cxt, apkPath);
        if (pkgInfo == null) {
            return null;
        } else {
            ApplicationInfo appInfo = pkgInfo.applicationInfo;
            if (Build.VERSION.SDK_INT >= 8) {
                appInfo.sourceDir = apkPath;
                appInfo.publicSourceDir = apkPath;
            }
            return pm.getApplicationLabel(appInfo);
        }
    }

    /**
     * 获取到指定包名的插件的资源属性
     *
     * @param cxt     当前Context
     * @param pkgName 要打开的资源所在的包名
     * @return
     */
    public static Resources getResFromPkgName(Context cxt,
                                              String pkgName) throws PackageManager.NameNotFoundException {
        return getCxtFromPkgName(cxt, pkgName).getResources();
    }

    /**
     * 获取到指定包名的插件的资源属性
     *
     * @param cxt     当前Context
     * @param apkPath 要打开的apk所在的路径
     * @return
     */
    public static Resources getResFromApkPath(Context cxt,
                                              String apkPath) throws PackageManager.NameNotFoundException {
        return getCxtFromApkPath(cxt, apkPath).getResources();
    }

    /**
     * 获取指定APP包名的Context对象
     *
     * @param cxt     当前Context
     * @param pkgName 要打开的资源所在的包名
     * @return
     */
    public static Context getCxtFromPkgName(Context cxt,
                                            String pkgName) throws PackageManager.NameNotFoundException {
        return cxt.createPackageContext(pkgName,
                Context.CONTEXT_IGNORE_SECURITY);
    }

    /**
     * 获取指定APP包名的Context对象
     *
     * @param cxt     当前Context
     * @param apkPath 要打开的apk所在的路径
     * @return
     */
    public static Context getCxtFromApkPath(Context cxt,
                                            String apkPath) throws PackageManager.NameNotFoundException {
        return getCxtFromPkgName(cxt,
                getAppInfo(cxt, apkPath).packageName);
    }

    /**
     * 打开打电话应用
     *
     * @param context
     * @param number
     */
    public static void openDial(Context context, String number) {
        Uri uri = Uri.parse("tel:" + number);
        Intent it = new Intent(Intent.ACTION_DIAL, uri);
        context.startActivity(it);
    }

    /**
     * 打开邮箱
     *
     * @param context
     * @param smsBody
     * @param tel
     */
    public static void openSMS(Context context, String smsBody, String tel) {
        Uri uri = Uri.parse("smsto:" + tel);
        Intent it = new Intent(Intent.ACTION_SENDTO, uri);
        it.putExtra("sms_body", smsBody);
        context.startActivity(it);
    }

    /**
     * 打开打电话应用
     *
     * @param context
     */
    public static void openDail(Context context) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    /**
     * 打开发短信应用
     *
     * @param context
     */
    public static void openSendMsg(Context context) {
        Uri uri = Uri.parse("smsto:");
        Intent intent = new Intent(Intent.ACTION_SENDTO, uri);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    /**
     * 打开相机
     *
     * @param context
     */
    public static void openCamera(Context context) {
        Intent intent = new Intent(); // 调用照相机
        intent.setAction("android.media.action.STILL_IMAGE_CAMERA");
        intent.setFlags(0x34c40000);
        context.startActivity(intent);
    }

    /**
     * 发送邮件
     *
     * @param context
     * @param subject 主题
     * @param content 内容
     * @param emails  邮件地址
     */
    public static void sendEmail(Context context, String subject, String content, String... emails) {
        try {
            Intent intent = new Intent(Intent.ACTION_SEND);
            // 模拟器
            // intent.setType("text/plain");
            intent.setType("message/rfc822"); // 真机
            intent.putExtra(Intent.EXTRA_EMAIL, emails);
            intent.putExtra(Intent.EXTRA_SUBJECT, subject);
            intent.putExtra(Intent.EXTRA_TEXT, content);
            context.startActivity(intent);
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * IMEI号
     *
     * @return
     */
    public static String getIMEI(Context context) {
        TelephonyManager tel = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        return tel.getDeviceId();
    }

    /**
     * 电话类型
     *
     * @return
     */
    public static String getPhoneType() {
        return Build.MODEL;
    }

    /**
     * 打开应用
     *
     * @param context
     * @param packageName
     */
    public static void openApp(Context context, String packageName) {
        Intent mainIntent = context.getPackageManager().getLaunchIntentForPackage(packageName);
        if (mainIntent == null) {
            mainIntent = new Intent(packageName);
        } else {
        }
        context.startActivity(mainIntent);
    }

    /**
     * 打开应用中的某个activity
     *
     * @param context
     * @param packageName
     * @param activityName
     * @return
     */
    public static boolean openAppActivity(Context context, String packageName, String activityName) {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        ComponentName cn = new ComponentName(packageName, activityName);
        intent.setComponent(cn);
        try {
            context.startActivity(intent);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 复制到剪切板
     *
     * @param string
     */
    public static void copyTextToBoard(Context context, String string) {
        if (TextUtils.isEmpty(string))
            return;
        ClipboardManager clip = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        clip.setText(string);
    }

    /**
     * 调用系统安装了的应用分享
     *
     * @param context
     * @param title
     * @param url
     */
    public static void showSystemShareOption(Activity context, final String title, final String url) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_SUBJECT, "分享：" + title);
        intent.putExtra(Intent.EXTRA_TEXT, title + " " + url);
        context.startActivity(Intent.createChooser(intent, "选择分享"));
    }


    public static boolean hasInternet(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();
        return info != null && info.isAvailable() && info.isConnected();
    }

    public static boolean isWifiOpen(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();
        if (info == null) return false;
        if (!info.isAvailable() || !info.isConnected()) return false;
        if (info.getType() != ConnectivityManager.TYPE_WIFI) return false;
        return true;
    }

}
