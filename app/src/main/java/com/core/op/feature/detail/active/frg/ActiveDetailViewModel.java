package com.core.op.feature.detail.active.frg;


import android.databinding.ObservableField;

import com.core.op.R;
import com.core.op.databinding.FrgActivedetailBinding;
import com.core.op.feature.detail.base.frg.DetailViewModel;
import com.core.op.lib.di.PerActivity;
import com.core.op.lib.messenger.Messenger;
import com.domain.bean.Event;
import com.domain.bean.EventDetail;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import javax.inject.Inject;

import static com.core.op.feature.detail.base.act.DetailViewModel.REFESHC_COMPLATE;

@PerActivity
public class ActiveDetailViewModel extends DetailViewModel<EventDetail, FrgActivedetailBinding> {

    public final ObservableField<EventDetail> data = new ObservableField<>();

    public final ObservableField<String> status = new ObservableField<>();

    public final ObservableField<String> type = new ObservableField<>();

    public final ObservableField<String> apply = new ObservableField<>();

    public final ObservableField<Boolean> signEnable = new ObservableField<>(true);

    public final Runnable finishCommand = () -> {
        Messenger.getDefault().sendNoMsg(REFESHC_COMPLATE);
    };

    @Inject
    public ActiveDetailViewModel(
            RxAppCompatActivity activity) {
        super(activity);
    }

    @Override
    public void afterViews() {
        data.set((EventDetail) fragment.getArguments().getSerializable("data"));

        switch (data.get().getStatus()) {
            case Event.STATUS_END:
                status.set(activity.getResources().getString(R.string.app_active_detail_status_end));
                break;
            case Event.STATUS_ING:
                status.set(activity.getResources().getString(R.string.app_active_detail_status_ing));
                break;
            case Event.STATUS_SING_UP:
                status.set(activity.getResources().getString(R.string.app_active_detail_status_sing_up));
                break;
        }

        type.set(activity.getString(R.string.app_active_detail_type_osc));
        switch (data.get().getType()) {
            case Event.EVENT_TYPE_OSC:
                type.set(activity.getString(R.string.app_active_detail_type_osc));
                break;
            case Event.EVENT_TYPE_TEC:
                type.set(activity.getString(R.string.app_active_detail_type_tec));
                break;
            case Event.EVENT_TYPE_OTHER:
                type.set(activity.getString(R.string.app_active_detail_type_other));
                break;
            case Event.EVENT_TYPE_OUTSIDE:
                type.set(activity.getString(R.string.app_active_detail_type_outside));
                break;
        }

        apply.set(activity.getString(R.string.app_active_detail_status_ing));
        switch (data.get().getApplyStatus()) {
            case EventDetail.APPLY_STATUS_UN_SIGN:
                apply.set(activity.getString(R.string.app_active_apply_status_unsign));
                break;
            case EventDetail.APPLY_STATUS_AUDIT:
                apply.set(activity.getString(R.string.app_active_apply_status_audit));
                break;
            case EventDetail.APPLY_STATUS_CONFIRMED:
                apply.set(activity.getString(R.string.app_active_apply_status_confirmed));
                break;
            case EventDetail.APPLY_STATUS_PRESENTED:
                apply.set(activity.getString(R.string.app_active_apply_status_presented));
                break;
            case EventDetail.APPLY_STATUS_CANCELED:
                apply.set(activity.getString(R.string.app_active_apply_status_canceled));
                break;
            case EventDetail.APPLY_STATUS_REFUSED:
                apply.set(activity.getString(R.string.app_active_apply_status_refused));
                break;
        }

        if (data.get().getStatus() != EventDetail.STATUS_ING ||
                data.get().getApplyStatus() != EventDetail.APPLY_STATUS_UN_SIGN) {
            signEnable.set(false);
        }
    }
}