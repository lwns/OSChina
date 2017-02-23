package com.core.op.di.modules;

import com.core.op.lib.di.PerActivity;
import com.domain.interactor.main.NewsDetailUseCase;
import com.domain.interactor.user.UploadHeadUseCase;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

@Module
public final class NewsDetailModule {

    @Provides
    @PerActivity
    @Named("NewsDetailUseCase")
    NewsDetailUseCase provideNewsDetail(NewsDetailUseCase useCase) {
        return useCase;
    }
}
