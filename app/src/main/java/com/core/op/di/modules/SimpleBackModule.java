package com.core.op.di.modules;

import com.core.op.lib.di.PerActivity;
import com.domain.interactor.main.NewsDetailUseCase;
import com.domain.interactor.main.SoftwareUseCase;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

@Module
public final class SimpleBackModule {

    public SimpleBackModule() {

    }

//    @Provides
//    SoftwareUseCase provideNewsDetail(SoftwareUseCase useCase) {
//        return useCase;
//    }
}
