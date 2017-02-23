package com.domain.interactor.main;

import com.domain.bean.Event;
import com.domain.bean.Question;
import com.domain.bean.base.PageBean;
import com.domain.executor.PostExecutionThread;
import com.domain.executor.ThreadExecutor;
import com.domain.interactor.UseCase;
import com.domain.repository.MainRepository;

import javax.inject.Inject;

import rx.Observable;

/**
 * @author op
 * @version 1.0
 * @description
 * @createDate 2016/11/14
 */
public class EventUseCase extends UseCase<PageBean<Event>> {
    MainRepository repository;

    @Inject
    public EventUseCase(MainRepository repository, ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.repository = repository;
    }

    @Override
    protected Observable<PageBean<Event>> buildUseCaseObservable() {
        return params == null ? repository.getEvent("") : repository.getEvent(params[0]);
    }
}
