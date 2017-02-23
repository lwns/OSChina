package com.core.op.feature.main.news.active;

import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;
import android.graphics.drawable.Drawable;

import com.core.op.BR;
import com.core.op.R;
import com.core.op.bindingadapter.common.ItemView;
import com.core.op.feature.detail.active.ActiveDetailActivity;
import com.core.op.feature.detail.news.NewsDetailActivity;
import com.core.op.feature.main.news.BannerItemViewModel;
import com.core.op.lib.base.BViewModel;
import com.core.op.lib.command.ReplyCommand;
import com.core.op.lib.di.PerActivity;
import com.domain.bean.Banner;
import com.domain.bean.Event;
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
public class ActiveItemViewModel extends BViewModel {

    public Event event;

    public String status;

    public String type;
    public Drawable drawable;

    public final ItemView itemView = ItemView.of(BR.viewModel, R.layout.include_main_active_banner_item);
    public final ObservableList<BannerItemViewModel> viewModels = new ObservableArrayList<>();

    public final ReplyCommand itemClick = new ReplyCommand(() -> {
        ActiveDetailActivity.instance(activity, event.getId());
    });

    @Inject
    public ActiveItemViewModel(RxAppCompatActivity activity, Event event) {
        super(activity);
        this.event = event;

        switch (event.getStatus()) {
            case Event.STATUS_END:
                status = activity.getString(R.string.app_active_status_end);
                drawable = activity.getResources().getDrawable(R.drawable.shape_text_bg);
                break;
            case Event.STATUS_ING:
                status = activity.getString(R.string.app_active_status_ing);
                drawable = activity.getResources().getDrawable(R.drawable.shape_text_bg_select);
                break;
            case Event.STATUS_SING_UP:
                status = activity.getString(R.string.app_active_status_singup);
                drawable = activity.getResources().getDrawable(R.drawable.shape_text_bg);
                break;
        }

        type = activity.getString(R.string.app_oscsite);
        switch (event.getType()) {
            case Event.EVENT_TYPE_OSC:
                type = activity.getString(R.string.app_active_type_osc);
                break;
            case Event.EVENT_TYPE_TEC:
                type = activity.getString(R.string.app_active_type_tec);
                break;
            case Event.EVENT_TYPE_OTHER:
                type = activity.getString(R.string.app_active_type_other);
                break;
            case Event.EVENT_TYPE_OUTSIDE:
                type = activity.getString(R.string.app_active_type_outside);
                break;
        }
    }

    public ActiveItemViewModel(RxAppCompatActivity activity, List<Banner> banners) {
        super(activity);

        Observable.from(banners).subscribe(data -> {
            viewModels.add(new BannerItemViewModel(activity, data));
        });
    }
}
