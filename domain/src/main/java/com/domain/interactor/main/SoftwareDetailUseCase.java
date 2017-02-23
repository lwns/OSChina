package com.domain.interactor.main;

import com.domain.bean.BlogDetail;
import com.domain.bean.SoftwareDetail;
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
public class SoftwareDetailUseCase extends UseCase<SoftwareDetail> {
    MainRepository repository;

    @Inject
    public SoftwareDetailUseCase(MainRepository repository, ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.repository = repository;
    }

    @Override
    protected Observable<SoftwareDetail> buildUseCaseObservable() {
        return repository.getSoftwareDetail(params[0]);
    }
}
