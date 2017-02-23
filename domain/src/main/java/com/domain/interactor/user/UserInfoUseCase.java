package com.domain.interactor.user;

import com.domain.bean.LoginUserBean;
import com.domain.bean.User;
import com.domain.bean.UserV2;
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
public class UserInfoUseCase extends UseCase<UserV2> {
    UserRepository repository;

    @Inject
    public UserInfoUseCase(UserRepository repository, ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.repository = repository;
    }

    @Override
    protected Observable<UserV2> buildUseCaseObservable() {
        return repository.getUserInfo();
    }
}
