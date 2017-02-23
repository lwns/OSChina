package com.domain.interactor.user;

import com.domain.bean.Mention;
import com.domain.bean.UserV2;
import com.domain.bean.base.PageBean;
import com.domain.executor.PostExecutionThread;
import com.domain.executor.ThreadExecutor;
import com.domain.interactor.UseCase;
import com.domain.repository.UserRepository;

import javax.inject.Inject;

import rx.Observable;

/**
 * @author op
 * @version 1.0
 * @description
 * @createDate 2016/11/14
 */
public class MineMessageUseCase extends UseCase<PageBean<Mention>> {

    UserRepository repository;

    @Inject
    public MineMessageUseCase(UserRepository repository, ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.repository = repository;
    }

    @Override
    protected Observable<PageBean<Mention>> buildUseCaseObservable() {
        return repository.getMineMessage(params[0]);
    }
}
