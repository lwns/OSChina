package com.core.op.di.modules;

import com.core.op.lib.di.PerActivity;
import com.domain.interactor.main.BannerUseCase;
import com.domain.interactor.main.BlogUseCase;
import com.domain.interactor.main.EventUseCase;
import com.domain.interactor.main.InfoUseCase;
import com.domain.interactor.main.QuestionUseCase;
import com.domain.interactor.main.TweetUseCase;
import com.domain.interactor.main.UserTweetUseCase;
import com.domain.interactor.user.UploadHeadUseCase;
import com.domain.interactor.user.UserInfoUseCase;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

@Module
public final class MainModule {

    @Provides
    @PerActivity
    @Named("info")
    InfoUseCase provideInfo(InfoUseCase useCase) {
        return useCase;
    }

    @Provides
    @PerActivity
    @Named("BannerUseCase")
    BannerUseCase provideBanner(BannerUseCase useCase) {
        return useCase;
    }

    @Provides
    @PerActivity
    @Named("BlogUseCase")
    BlogUseCase provideBlog(BlogUseCase useCase) {
        return useCase;
    }


    @Provides
    @PerActivity
    @Named("QuestionUseCase")
    QuestionUseCase provideQuestion(QuestionUseCase useCase) {
        return useCase;
    }

    @Provides
    @PerActivity
    @Named("EventUseCase")
    EventUseCase provideEvent(EventUseCase useCase) {
        return useCase;
    }

    @Provides
    @PerActivity
    @Named("TweetUseCase")
    TweetUseCase provideTweet(TweetUseCase useCase) {
        return useCase;
    }

    @Provides
    @PerActivity
    @Named("UserTweetUseCase")
    UserTweetUseCase provideUserTweet(UserTweetUseCase useCase) {
        return useCase;
    }

    @Provides
    @PerActivity
    @Named("UserInfoUseCase")
    UserInfoUseCase provideUserInfo(UserInfoUseCase useCase) {
        return useCase;
    }

    @Provides
    @PerActivity
    @Named("UploadHeadUseCase")
    UploadHeadUseCase provideUploadHead(UploadHeadUseCase useCase) {
        return useCase;
    }
}
