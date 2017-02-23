package com.domain.interactor.user;

import com.domain.bean.Mention;
import com.domain.bean.SubBean;
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
public class MineBlogUseCase extends UseCase<PageBean<SubBean>> {

    UserRepository repository;

    @Inject
    public MineBlogUseCase(UserRepository repository, ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.repository = repository;
    }

    @Override
    protected Observable<PageBean<SubBean>> buildUseCaseObservable() {
        return repository.getMineBlog(params[0], params[1], params[2]);
    }
}
