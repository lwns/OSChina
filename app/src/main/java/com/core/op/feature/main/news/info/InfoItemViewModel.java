package com.core.op.feature.main.news.info;

import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;
import android.graphics.drawable.Drawable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ImageSpan;

import com.core.op.BR;
import com.core.op.R;
import com.core.op.bindingadapter.common.ItemView;
import com.core.op.data.Global;
import com.core.op.feature.detail.base.act.DetailActivity;
import com.core.op.feature.detail.news.NewsDetailActivity;
import com.core.op.feature.main.news.BannerItemViewModel;
import com.core.op.lib.base.BViewModel;
import com.core.op.lib.command.ReplyCommand;
import com.core.op.lib.di.PerActivity;
import com.core.op.lib.utils.DateUtil;
import com.domain.bean.Banner;
import com.domain.bean.News;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;


/**
 * @author op
 * @version 1.0
 * @description
 * @createDate 2016/11/14
 */
@PerActivity
public class InfoItemViewModel extends BViewModel {

    public News news;

    public SpannableString spannable;

    public final ItemView itemView = ItemView.of(BR.viewModel, R.layout.include_main_banner_item);
    public final ObservableList<BannerItemViewModel> viewModels = new ObservableArrayList<>();

    public final ReplyCommand itemClick = new ReplyCommand(() -> {
        NewsDetailActivity.instance(activity, news.getId());
    });

    @Inject
    public InfoItemViewModel(RxAppCompatActivity activity, News news) {
        super(activity);
        this.news = news;

        if (DateUtil.isSameDay(Global.SERVER_TIME, news.getPubDate())) {
            String text = "[icon] " + news.getTitle();
            Drawable drawable = activity.getResources().getDrawable(R.drawable.ic_label_today);
            drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
            ImageSpan imageSpan = new ImageSpan(drawable, ImageSpan.ALIGN_BOTTOM);
            spannable = new SpannableString(text);
            spannable.setSpan(imageSpan, 0, 6, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        } else {
            spannable = new SpannableString(news.getTitle());
        }
    }

    public InfoItemViewModel(RxAppCompatActivity activity, List<Banner> banners) {
        super(activity);

        Observable.from(banners).subscribe(data -> {
            viewModels.add(new BannerItemViewModel(activity, data));
        });
    }
}
