package com.domain.interactor.main;

import com.domain.bean.Blog;
import com.domain.bean.Tweet;
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
public class TweetUseCase extends UseCase<PageBean<Tweet>> {
    MainRepository repository;

    @Inject
    public TweetUseCase(MainRepository repository, ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.repository = repository;
    }

    @Override
    protected Observable<PageBean<Tweet>> buildUseCaseObservable() {
        return repository.getTweet(params[0], params[1]);
    }
}
