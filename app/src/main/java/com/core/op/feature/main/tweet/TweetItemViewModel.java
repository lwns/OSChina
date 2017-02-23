package com.core.op.feature.main.tweet;

import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;

import com.core.op.R;
import com.core.op.feature.detail.tweet.TweetDetailActivity;
import com.core.op.lib.base.BViewModel;
import com.core.op.lib.command.ReplyCommand;
import com.core.op.lib.di.PerActivity;
import com.core.op.lib.emoji.InputHelper;
import com.core.op.lib.utils.AssimilateUtils;
import com.core.op.utils.PlatfromUtil;
import com.domain.bean.Tweet;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

/**
 * @author op
 * @version 1.0
 * @description
 * @createDate 2016/11/16
 */
public class TweetItemViewModel extends BViewModel {

    public final static int CLIENT_MOBILE = 2;
    public final static int CLIENT_ANDROID = 3;
    public final static int CLIENT_IPHONE = 4;
    public final static int CLIENT_WINDOWS_PHONE = 5;
    public final static int CLIENT_WECHAT = 6;


    public Tweet tweet;

    public String device;

    public Spannable spannable;

    ReplyCommand headOnClick = new ReplyCommand(() -> {

    });

    public final ReplyCommand itemClick = new ReplyCommand(() -> {
        TweetDetailActivity.instance(activity, tweet.getId());
    });

    public TweetItemViewModel(RxAppCompatActivity activity, Tweet tweet) {
        super(activity);
        this.tweet = tweet;
        device = PlatfromUtil.setPlatFromString(activity, tweet.getAppClient());

        String content = "";
        if (!TextUtils.isEmpty(tweet.getContent())) {
            content = tweet.getContent().replaceAll("[\n\\s]+", " ");
        }
        Spannable s = AssimilateUtils.assimilateOnlyAtUser(activity, content);
        s = AssimilateUtils.assimilateOnlyTag(activity, s);
        s = AssimilateUtils.assimilateOnlyLink(activity, s);
        spannable = InputHelper.displayEmoji(activity, s);
    }
}
