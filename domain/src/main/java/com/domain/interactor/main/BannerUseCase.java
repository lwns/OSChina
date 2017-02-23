package com.domain.interactor.main;

import com.domain.bean.Banner;
import com.domain.bean.News;
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
public class BannerUseCase extends UseCase<PageBean<Banner>> {
    MainRepository repository;

    @Inject
    public BannerUseCase(MainRepository repository, ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.repository = repository;
    }

    @Override
    protected Observable<PageBean<Banner>> buildUseCaseObservable() {
        return repository.getBanner(params[0]);
    }
}
