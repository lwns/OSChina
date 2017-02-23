package com.core.op.feature.back.shake;


import android.databinding.ObservableField;
import android.media.MediaPlayer;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;

import com.core.op.R;
import com.core.op.databinding.FrgShakeBinding;
import com.core.op.feature.back.shake.sensor.SensorViewModel;
import com.core.op.feature.detail.DetailUtil;
import com.core.op.lib.command.ReplyCommand;
import com.core.op.lib.di.PerActivity;
import com.core.op.lib.utils.AppToast;
import com.core.op.lib.utils.DeviceUtil;
import com.domain.bean.ShakeNews;
import com.domain.interactor.main.ShakeUseCase;
import com.orhanobut.logger.Logger;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import rx.Observable;

import static android.R.attr.data;

@PerActivity
public class ShakeViewModel extends SensorViewModel<FrgShakeBinding> {

    public final ObservableField<String> state = new ObservableField<>();

    public final ObservableField<String> time = new ObservableField<>();

    public final ObservableField<Integer> stateVisible = new ObservableField<>(View.VISIBLE);
    public final ObservableField<Integer> loading = new ObservableField<>(View.GONE);

    public final ObservableField<Integer> cardVisible = new ObservableField<>(View.GONE);

    public final ObservableField<Integer> timeVisible = new ObservableField<>(View.INVISIBLE);

    ShakeUseCase useCase;

    public final ObservableField<ShakeNews> shakeNews = new ObservableField<>();

    public final ReplyCommand click = new ReplyCommand(() -> {
        DetailUtil.showDetail(activity, shakeNews.get().getType(), shakeNews.get().getId());
    });

    @Inject
    public ShakeViewModel(RxAppCompatActivity activity,
                          ShakeUseCase useCase) {
        super(activity);
        this.useCase = useCase;
        state.set(activity.getString(R.string.app_shake_state_tip));
    }

    @Override
    public void afterViews() {
        super.afterViews();
        registerSensor();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterSensor();
    }

    @Override
    public void onShake() {
        if (!DeviceUtil.hasInternet(activity)) {
            AppToast.show(activity, activity.getString(R.string.app_no_network));
            mLoading = false;
            return;
        }
        state.set(activity.getString(R.string.app_shake_state_ing));
        loading.set(View.VISIBLE);
        stateVisible.set(View.VISIBLE);
        useCase.execute()
                .compose(fragment.bindToLifecycle())
                .flatMap(data -> {
                    shakeNews.set(data);
                    MediaPlayer.create(activity, R.raw.shake).start();
                    cardVisible.set(View.VISIBLE);

                    ScaleAnimation animation = new ScaleAnimation(0.0f, 1.0f, 0.0f, 1.0f,
                            Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                    animation.setDuration(320);
                    animation.setFillAfter(true);
                    binding.cvShake.startAnimation(animation);
                    loading.set(View.GONE);
                    state.set(activity.getString(R.string.app_shake_state_tip));
                    return Observable.timer(1, TimeUnit.SECONDS);
                })
                .subscribe(data -> {
                }, error -> {
                    mLoading = false;
                    state.set(activity.getString(R.string.app_shake_state_fail));
                    Logger.i(error.getMessage());
                }, () -> {
                    mLoading = false;
                    loading.set(View.GONE);
                });

    }
}