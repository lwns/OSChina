package com.domain.interactor.main;

import com.domain.bean.News;
import com.domain.bean.base.PageBean;
import com.domain.executor.PostExecutionThread;
import com.domain.executor.ThreadExecutor;
import com.domain.interactor.UseCase;
import com.domain.repository.MainRepository;

import javax.inject.Inject;

import rx.Observable;

import static javax.swing.text.html.HTML.Tag.I;

/**
 * @author op
 * @version 1.0
 * @description
 * @createDate 2016/11/14
 */
public class InfoUseCase extends UseCase<PageBean<News>> {
    MainRepository repository;

    @Inject
    public InfoUseCase(MainRepository repository, ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.repository = repository;
    }

    @Override
    protected Observable<PageBean<News>> buildUseCaseObservable() {
        return params == null ? repository.getNews("") : repository.getNews(params[0]);
    }
}
