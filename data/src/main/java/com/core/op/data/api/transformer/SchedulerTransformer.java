package com.core.op.data.api.transformer;


import com.core.op.data.api.BaseResponse;
import com.core.op.data.api.Response;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * @author op
 * @version 1.0
 * @description 请求线程
 * @createDate 2016/7/21
 */
public class SchedulerTransformer<T> implements Observable.Transformer<BaseResponse<T>, BaseResponse<T>> {

    @Override
    public Observable<BaseResponse<T>> call(Observable<BaseResponse<T>> observable) {
        return observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public static <T> SchedulerTransformer<T> create() {
        return new SchedulerTransformer<T>();
    }
}
