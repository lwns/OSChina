package com.domain.interactor.main;

import com.domain.bean.Banner;
import com.domain.bean.Blog;
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
public class BlogUseCase extends UseCase<PageBean<Blog>> {
    MainRepository repository;

    @Inject
    public BlogUseCase(MainRepository repository, ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.repository = repository;
    }

    @Override
    protected Observable<PageBean<Blog>> buildUseCaseObservable() {
        return repository.getBlog(params[0], params[1]);
    }
}
