package com.core.op;

import android.os.Environment;

import java.io.File;

/**
 * @author op
 * @version 1.0
 * @description
 * @createDate 2016/12/1
 */
public class AppConfig {

    // 默认存放图片的路径
    public final static String DEFAULT_SAVE_IMAGE_PATH = Environment
            .getExternalStorageDirectory()
            + File.separator
            + "OSChina"
            + File.separator + "osc_img" + File.separator;

    // 默认存放文件下载的路径
    public final static String DEFAULT_SAVE_FILE_PATH = Environment
            .getExternalStorageDirectory()
            + File.separator
            + "OSChina"
            + File.separator + "download" + File.separator;


    /*第三方qq登陆*/
    public static final String QQ = "qq";
    public static final String APP_QQ_KEY = "1105860442";//QQ APPID

    /*第三方微信登陆*/
    public static final String WECHAT = "wechat";
    public static final String WEICHAT_APPID = "wxa8213dc827399101";//微信appID
    public static final String BUNDLE_KEY_OPENIDINFO = "bundle_key_openid_info";

    /*第三方微博登陆*/
    public static final String WEIBO = "weibo";

    /*登陆成功后发送广播的Action*/
    public static final String INTENT_ACTION_USER_CHANGE = "net.oschina.action.USER_CHANGE";//改变用户
    public static final String INTENT_ACTION_LOGOUT = "net.oschina.action.LOGOUT";//用户登出

    /*CacheKey*/
    public static final String CACHE_USERINFO = "CACHE_USERINFO";

    /*文件存放地址*/
    public final static String FILE_SAVE_PATH = "oschina" + File.separator;

    /*详情跳转id*/
    public static final int CATALOG_ALL = 0;//url网页
    public static final int CATALOG_SOFTWARE = 1;    // 软件推荐-不支持(默认软件评论其实是动弹)
    public static final int CATALOG_QUESTION = 2;    // 讨论区帖子
    public static final int CATALOG_BLOG = 3;    // 博客
    public static final int CATALOG_TRANSLATION = 4;    // 翻译文章
    public static final int CATALOG_EVENT = 5;    // 活动类型
    public static final int CATALOG_NEWS = 6;    // 资讯类型
    public static final int CATALOG_TWEET = 100;  // 动弹
}
