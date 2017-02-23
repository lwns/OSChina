package com.core.op.feature.detail.tweet;


import android.databinding.ObservableField;
import android.support.v4.app.FragmentManager;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.View;

import com.core.op.R;
import com.core.op.databinding.ActTweetdetailBinding;
import com.core.op.lib.base.BAViewModel;
import com.core.op.lib.di.PerActivity;
import com.core.op.lib.utils.AssimilateUtils;
import com.core.op.utils.PlatfromUtil;
import com.domain.bean.About;
import com.domain.bean.Tweet;
import com.domain.interactor.main.TweetDetailUseCase;
import com.orhanobut.logger.Logger;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import javax.inject.Inject;
import javax.inject.Named;

import static android.R.attr.data;

@PerActivity
public class TweetDetailViewModel extends BAViewModel<ActTweetdetailBinding> {

    TweetDetailUseCase useCase;

    public ObservableField<String> title = new ObservableField<>();

    public ObservableField<String> device = new ObservableField<>();
    public ObservableField<Spannable> content = new ObservableField<>();

    public ObservableField<Integer> titleVisible = new ObservableField<>(View.VISIBLE);
    public ObservableField<Integer> contentVisible = new ObservableField<>(View.VISIBLE);

    public ObservableField<String> refTitle = new ObservableField<>();
    public ObservableField<SpannableStringBuilder> refContent = new ObservableField<>();

    public ObservableField<Tweet> data = new ObservableField<>();

    protected long dataId;

    @Inject
    public TweetDetailViewModel(RxAppCompatActivity activity,
                                @Named("TweetDetailUseCase") TweetDetailUseCase useCase) {
        super(activity);
        this.useCase = useCase;

        dataId = activity.getIntent().getExtras().getLong("id", 0);
        if (dataId == 0) activity.finish();
    }

    @Override
    public void afterViews() {
        title.set(activity.getString(R.string.app_tweet_detail_title));
        useCase.setParams(dataId + "");

        useCase.execute()
                .compose(activity.bindToLifecycle())
                .subscribe(data -> {
                    this.data.set(data);
                    device.set(PlatfromUtil.setPlatFromString(activity, data.getAppClient()));
                    content.set(AssimilateUtils.assimilate(activity, data.getContent()));

                    /* -- about reference -- */
                    if (data.getAbout() != null) {
                        titleVisible.set(View.VISIBLE);
                        About about = data.getAbout();
                        if (!About.check(about)) {
                            titleVisible.set(View.VISIBLE);
                            refTitle.set("不存在或已删除的内容");
                            refContent.set(new SpannableStringBuilder("抱歉，该内容不存在或已被删除"));
                        } else {
                            if (about.getType() == 100) {
                                titleVisible.set(View.GONE);
                                String aname = "@" + about.getTitle();
                                String cnt = about.getContent();
                                Spannable spannable = AssimilateUtils.assimilate(activity, cnt);
                                SpannableStringBuilder builder = new SpannableStringBuilder();
                                builder.append(aname + ": ");
                                builder.append(spannable);
                                ForegroundColorSpan span = new ForegroundColorSpan(
                                        activity.getResources().getColor(R.color.app_theme_colorPrimary));
                                builder.setSpan(span, 0, aname.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
                                refContent.set(builder);
                            } else {
                                titleVisible.set(View.VISIBLE);
                                refTitle.set(about.getTitle());
                                refContent.set(new SpannableStringBuilder(about.getContent()));
                            }
                        }
                    }
                }, error -> {
                    Logger.e(error.getMessage());
                });
    }
}