package com.domain.interactor.user;

import com.domain.bean.Banner;
import com.domain.bean.LoginUserBean;
import com.domain.bean.base.PageBean;
import com.domain.executor.PostExecutionThread;
import com.domain.executor.ThreadExecutor;
import com.domain.interactor.UseCase;
import com.domain.repository.MainRepository;
import com.domain.repository.UserRepository;

import javax.inject.Inject;

import rx.Observable;

/**
 * @author op
 * @version 1.0
 * @description
 * @createDate 2016/11/14
 */
public class LoginUseCase extends UseCase<LoginUserBean> {
    UserRepository repository;

    @Inject
    public LoginUseCase(UserRepository repository, ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.repository = repository;
    }

    @Override
    protected Observable<LoginUserBean> buildUseCaseObservable() {
        return repository.login(params[0], params[1]);
    }
}
