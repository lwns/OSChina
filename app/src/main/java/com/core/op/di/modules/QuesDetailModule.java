package com.core.op.di.modules;

import com.core.op.lib.di.PerActivity;
import com.domain.interactor.main.QuesDetailUseCase;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

@Module
public final class QuesDetailModule {
    @Provides
    @PerActivity
    @Named("QuesDetailUseCase")
    QuesDetailUseCase provideQuesDetail(QuesDetailUseCase useCase) {
        return useCase;
    }
}
