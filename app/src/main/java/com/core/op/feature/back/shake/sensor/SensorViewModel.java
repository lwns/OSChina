package com.core.op.feature.back.shake.sensor;

import android.app.Service;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Vibrator;

import com.core.op.lib.base.BFViewModel;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import java.lang.reflect.Type;

/**
 * @author op
 * @version 1.0
 * @description
 * @createDate 2017/1/17
 */
public abstract class SensorViewModel<T> extends BFViewModel<T> implements SensorEventListener {
    public static final int UPTATE_INTERVAL_TIME = 50;

    protected SensorManager mSensor = null;
    protected Vibrator mVibrator = null;
    protected int mSpeedThreshold = 45;// 这个值越大需要越大的力气来摇晃手机

    private float mSensorLastX;
    private float mSensorLastY;
    private float mSensorLastZ;
    private long mSensorLastUpdateTime;

    protected boolean mLoading;
    protected boolean mIsRegister;
    protected int mDelayTime = 5;

    public SensorViewModel(RxAppCompatActivity activity) {
        super(activity);
    }

    @Override
    public void afterViews() {
        mSensor = (SensorManager) getActivity()
                .getSystemService(Context.SENSOR_SERVICE);
        mVibrator = (Vibrator) getActivity().getSystemService(Service.VIBRATOR_SERVICE);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        long currentUpdateTime = System.currentTimeMillis();
        long timeInterval = currentUpdateTime - mSensorLastUpdateTime;
        if (timeInterval < UPTATE_INTERVAL_TIME) {
            return;
        }
        mSensorLastUpdateTime = currentUpdateTime;

        float x = event.values[0];
        float y = event.values[1];
        float z = event.values[2];

        float deltaX = x - mSensorLastX;
        float deltaY = y - mSensorLastY;
        float deltaZ = z - mSensorLastZ;

        mSensorLastX = x;
        mSensorLastY = y;
        mSensorLastZ = z;

        double speed = (Math.sqrt(deltaX * deltaX + deltaY * deltaY + deltaZ
                * deltaZ) / timeInterval) * 100;
        if (speed >= mSpeedThreshold && !mLoading) {
            mLoading = true;
            mVibrator.vibrate(300);
            onShake();
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    public abstract void onShake();


    public void registerSensor() {
        if (mSensor != null && !mIsRegister) {
            Sensor sensor = mSensor.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            if (sensor != null) {
                mIsRegister = true;
                mSensor.registerListener(this, sensor,
                        SensorManager.SENSOR_DELAY_GAME);
            }
        }
    }

    public void unregisterSensor() {
        if (mSensor != null && mIsRegister) {
            mIsRegister = false;
            mSensor.unregisterListener(this);
        }
    }
}
