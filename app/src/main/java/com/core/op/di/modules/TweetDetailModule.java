package com.core.op.di.modules;

import com.core.op.lib.di.PerActivity;
import com.domain.interactor.main.NewsDetailUseCase;
import com.domain.interactor.main.TweetDetailUseCase;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

@Module
public final class TweetDetailModule {

    @Provides
    @PerActivity
    @Named("TweetDetailUseCase")
    TweetDetailUseCase provideTweetDetail(TweetDetailUseCase useCase) {
        return useCase;
    }
}
