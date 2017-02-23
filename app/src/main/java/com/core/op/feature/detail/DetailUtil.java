package com.core.op.feature.detail;

import android.content.Context;

import com.core.op.AppConfig;
import com.core.op.feature.detail.active.ActiveDetailActivity;
import com.core.op.feature.detail.blog.BlogDetailActivity;
import com.core.op.feature.detail.news.NewsDetailActivity;
import com.core.op.feature.detail.question.QuesDetailActivity;
import com.core.op.feature.detail.software.SoftwareDetailActivity;
import com.core.op.feature.detail.tweet.TweetDetailActivity;

/**
 * @author op
 * @version 1.0
 * @description
 * @createDate 2017/1/17
 */
public class DetailUtil {

    public static void showDetail(Context context, int type, long id) {
        showDetail(context, type, id, "");
    }

    /**
     * show detail  method
     *
     * @param context context
     * @param type    type
     * @param id      id
     */
    public static void showDetail(Context context, int type, long id, String href) {
        switch (type) {
            case AppConfig.CATALOG_ALL:
                //新闻链接
//                showUrlRedirect(context, id, href);
                break;
            case AppConfig.CATALOG_SOFTWARE:
                //软件推荐
                SoftwareDetailActivity.instance(context, id);
//                UIUtil.showSoftwareDetailById(context, (int) id);
                break;
            case AppConfig.CATALOG_QUESTION:
                //问答
                QuesDetailActivity.instance(context, id);
                break;
            case AppConfig.CATALOG_BLOG:
                //博客
                BlogDetailActivity.instance(context, id);
                break;
            case AppConfig.CATALOG_TRANSLATION:
                //4.翻译
//                TranslateDetailActivity.show(context, id);
                break;
            case AppConfig.CATALOG_EVENT:
                //活动
                ActiveDetailActivity.instance(context, id);
                break;
            case AppConfig.CATALOG_TWEET:
                // 动弹
                TweetDetailActivity.instance(context, id);
                break;
            default:
                //6.资讯
                NewsDetailActivity.instance(context, id);
                break;
        }
    }
}
