package com.core.op.data.api.subscriber;

import android.content.Context;

import rx.Subscriber;

/**
 * @author op
 * @version 1.0
 * @description
 * @createDate 2016/7/21
 */
public abstract class BaseSubscriber<T> extends Subscriber<T> {
    private Context mContext;

    public BaseSubscriber(Context context) {
        mContext = context;
    }

    public Context getContext() {
        return mContext;
    }
}
