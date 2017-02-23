package com.domain.interactor.user;

import com.domain.bean.LoginUserBean;
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
public class UserLoginUseCase extends UseCase<LoginUserBean> {
    UserRepository repository;

    @Inject
    public UserLoginUseCase(UserRepository repository, ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.repository = repository;
    }

    @Override
    protected Observable<LoginUserBean> buildUseCaseObservable() {
        return repository.userLogin(params[0], params[1], params[2]);
    }
}
