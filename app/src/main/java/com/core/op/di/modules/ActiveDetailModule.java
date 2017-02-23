package com.core.op.di.modules;

import com.core.op.lib.di.PerActivity;
import com.domain.interactor.main.BlogDetailUseCase;
import com.domain.interactor.main.EventDetailUseCase;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

@Module
public final class ActiveDetailModule {

    @Provides
    @PerActivity
    @Named("EventDetailUseCase")
    EventDetailUseCase provideEventDetail(EventDetailUseCase useCase) {
        return useCase;
    }
}
