package com.core.op.data.api.transformer;

import android.content.Context;


import com.core.op.data.api.BaseResponse;
import com.core.op.data.api.Response;

import rx.Observable;

/**
 * @author op
 * @version 1.0
 * @description
 * @createDate 2016/7/21
 */
public class DefaultTransformer<T> implements Observable.Transformer<BaseResponse<T>, T> {

    @Override
    public Observable<T> call(Observable<BaseResponse<T>> Observable) {
        return Observable
                .compose(SchedulerTransformer.create())
                .compose(new ErrorTransformer<>());
    }
}