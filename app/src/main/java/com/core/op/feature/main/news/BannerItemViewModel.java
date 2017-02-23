package com.core.op.feature.main.news;

import android.databinding.ObservableField;

import com.core.op.feature.detail.DetailUtil;
import com.core.op.lib.base.BViewModel;
import com.core.op.lib.command.ReplyCommand;
import com.core.op.lib.di.PerActivity;
import com.domain.bean.Banner;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import javax.inject.Inject;

/**
 * @author op
 * @version 1.0
 * @description
 * @createDate 2016/10/10
 */
@PerActivity
public class BannerItemViewModel extends BViewModel {

    public final ObservableField<String> imageUrl = new ObservableField<>();

    public final ObservableField<String> title = new ObservableField<>();

    public Banner banner;

    public final ReplyCommand click = new ReplyCommand(() -> {
        DetailUtil.showDetail(activity, banner.getType(), banner.getId());
    });

    @Inject
    public BannerItemViewModel(RxAppCompatActivity activity, Banner banner) {
        super(activity);
        this.banner = banner;
        imageUrl.set(banner.getImg());
        this.title.set(banner.getName());
    }

}
