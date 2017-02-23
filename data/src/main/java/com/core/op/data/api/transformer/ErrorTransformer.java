package com.core.op.data.api.transformer;


import com.core.op.data.api.BaseResponse;
import com.core.op.data.api.exception.RepositoryException;

import rx.Observable;
import rx.Subscriber;

import static com.core.op.data.Global.SERVER_TIME;

/**
 * @author op
 * @version 1.0
 * @description 对response数据进行拦截处理
 * @createDate 2016/3/24
 */
public class ErrorTransformer<T> implements Observable.Transformer<BaseResponse<T>, T> {

    @Override
    public Observable<T> call(Observable<BaseResponse<T>> comicVineResponseObservable) {
        return comicVineResponseObservable.flatMap(response -> flatResponse(response));
    }

    /**
     * 对网络接口返回的Response进行分割操作
     *
     * @param response
     * @param <T>
     * @return
     */
    public <T> Observable<T> flatResponse(final BaseResponse<T> response) {
        return Observable.create(new Observable.OnSubscribe<T>() {

            @Override
            public void call(Subscriber<? super T> subscriber) {
                if (response.isSuccess()) {
                    if (!subscriber.isUnsubscribed()) {
                        SERVER_TIME = response.getTime();
                        subscriber.onNext(response.getResult());
                    }
                } else {
                    if (!subscriber.isUnsubscribed()) {
                        subscriber.onError(new RepositoryException(response.getMessage()));
                    }
                    return;
                }
//                if (!response.getResponse().isSuccessful() || response.getBody() == null) {
//                    subscriber.onError(new RepositoryException());
//                } else if (!response.getBody().getStatus().equals("S")) {
//                    subscriber.onError(new RepositoryException(response.getBody().getMessage()));
//                }

                if (!subscriber.isUnsubscribed()) {
                    subscriber.onCompleted();
                }
            }
        });
    }
}
