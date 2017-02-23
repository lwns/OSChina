package com.core.op.di.modules;

import com.core.op.lib.di.PerActivity;
import com.domain.interactor.main.BlogDetailUseCase;
import com.domain.interactor.main.NewsDetailUseCase;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

@Module
public final class BlogDetailModule {

    @Provides
    @PerActivity
    @Named("BlogDetailUseCase")
    BlogDetailUseCase provideBlogDetail(BlogDetailUseCase useCase) {
        return useCase;
    }
}
