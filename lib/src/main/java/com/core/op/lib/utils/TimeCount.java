package com.core.op.lib.utils;

import android.os.CountDownTimer;

/**
 * @Description 获取验证码倒计时
 * @Author Andy.fang
 * @CreateDate 2015/8/3
 * @Version 1.0
 */
public class TimeCount extends CountDownTimer {

    MyTime mMyTime;

    /**
     * @author Andy.fang
     * @version 1.0
     * @description 监听内部
     * @createDate 2015-8-3
     */
    public interface MyTime {// 回调计时器

        public void onTick(long millisUntilFinished);

        public void onFinish();
    }

    public TimeCount(MyTime mMyTime) {
        super(60000, 1000);// 参数依次为总时长,和计时的时间间隔
        this.mMyTime = mMyTime;
    }

    public TimeCount(long millisInFuture, long countDownInterval, MyTime mMyTime) {
        super(millisInFuture, countDownInterval);// 参数依次为总时长,和计时的时间间隔
        this.mMyTime = mMyTime;
    }

    @Override
    public void onTick(long millisUntilFinished) {// 计时过程显示
        mMyTime.onTick(millisUntilFinished);
    }

    @Override
    public void onFinish() {// 计时完毕时触发
        mMyTime.onFinish();
    }

}
