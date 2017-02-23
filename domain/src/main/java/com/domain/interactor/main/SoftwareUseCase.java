package com.domain.interactor.main;

import com.domain.bean.SoftwareList;
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
public class SoftwareUseCase extends UseCase<SoftwareList> {
    MainRepository repository;

    @Inject
    public SoftwareUseCase(MainRepository repository, ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.repository = repository;
    }

    @Override
    protected Observable<SoftwareList> buildUseCaseObservable() {
        return repository.getSoftware(params[0], params[1], params[2]);
    }
}
