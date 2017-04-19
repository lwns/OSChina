package com.core.op.feature.main.news.info;

import android.databinding.ObservableField;

import com.core.op.lib.base.BViewModel;
import com.core.op.lib.di.PerActivity;
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

    RxAppCompatActivity activity;

    public final ObservableField<String> imageUrl = new ObservableField<>();

    public final ObservableField<String> title = new ObservableField<>();
    // viewModel for recycler header viewPager

    @Inject
    public BannerItemViewModel(RxAppCompatActivity activity, String url, String title) {
        imageUrl.set(url);
        this.title.set(title);
    }

}
