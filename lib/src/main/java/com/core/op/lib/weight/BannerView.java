package com.core.op.lib.weight;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;

import com.core.op.lib.R;
import com.core.op.lib.databinding.WeightBannerBinding;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * 自定义Banner无限轮播控件
 */
public class BannerView extends RelativeLayout {

    private WeightBannerBinding binding;

    private CompositeSubscription compositeSubscription;

    //默认轮播时间，10s
    private int delayTime = 10;

    private Context context;

    private boolean isStopScroll = false;

    public BannerView(Context context) {
        this(context, null);
    }

    public BannerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BannerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        LayoutInflater inflater = LayoutInflater.from(context);
        binding = DataBindingUtil.inflate(inflater, R.layout.weight_banner, this, true);
        init();
    }

    public ViewPager getViewPager() {
        return binding.viewpager;
    }

    private void init() {
        binding.viewpager.clearOnPageChangeListeners();
        binding.viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int pos) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {

                switch (state) {
                    case ViewPager.SCROLL_STATE_IDLE:
                        if (isStopScroll) {
                            startScroll();
                        }
                        break;
                    case ViewPager.SCROLL_STATE_DRAGGING:
                        stopScroll();
                        break;
                }
            }
        });
        compositeSubscription = new CompositeSubscription();
    }

    /**
     * 设置轮播间隔时间
     *
     * @param time 轮播间隔时间，单位秒
     */
    public BannerView delayTime(int time) {
        this.delayTime = time;
        return this;
    }

    /**
     * 图片开始轮播
     */
    public void startScroll() {
        destory();
        compositeSubscription = new CompositeSubscription();
        Subscription subscription = Observable.interval(delayTime, TimeUnit.SECONDS, AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Long>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onNext(Long aLong) {
                        binding.viewpager.setCurrentItem(binding.viewpager.getCurrentItem() + 1);
                    }
                });
        isStopScroll = false;
        compositeSubscription.add(subscription);
    }

    /**
     * 图片停止轮播
     */
    public void stopScroll() {
        isStopScroll = true;
        destory();
    }

    public void destory() {
        if (compositeSubscription != null) {
            compositeSubscription.unsubscribe();
        }
    }
}
