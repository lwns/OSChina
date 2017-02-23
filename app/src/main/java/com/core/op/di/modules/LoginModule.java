package com.core.op.di.modules;

import com.core.op.lib.di.PerActivity;
import com.domain.interactor.main.InfoUseCase;
import com.domain.interactor.user.LoginUseCase;
import com.domain.interactor.user.UserInfoUseCase;
import com.domain.interactor.user.UserLoginUseCase;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

@Module
public final class LoginModule {

    @Provides
    @PerActivity
    @Named("LoginUseCase")
    LoginUseCase provideUser(LoginUseCase useCase) {
        return useCase;
    }

    @Provides
    @PerActivity
    @Named("UserLoginUseCase")
    UserLoginUseCase provideUserLogin(UserLoginUseCase useCase) {
        return useCase;
    }

    @Provides
    @PerActivity
    @Named("UserInfoUseCase")
    UserInfoUseCase provideUserInfo(UserInfoUseCase useCase) {
        return useCase;
    }
}
