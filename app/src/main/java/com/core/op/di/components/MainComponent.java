package com.core.op.di.components;

import com.core.op.di.modules.ActivityModule;
import com.core.op.di.modules.MainModule;
import com.core.op.feature.main.MainActivity;
import com.core.op.feature.main.explore.ExploreFragment;
import com.core.op.feature.main.news.NewFragment;
import com.core.op.feature.main.news.active.ActiveFragment;
import com.core.op.feature.main.news.blog.BlogFragment;
import com.core.op.feature.main.news.info.InfoFragment;
import com.core.op.feature.main.news.question.QuestionFragment;
import com.core.op.feature.main.tweet.TweetFragment;
import com.core.op.feature.main.tweet.TweetPagerFragment;
import com.core.op.lib.di.PerActivity;

import dagger.Component;

@PerActivity
@Component(dependencies = AppComponent.class, modules = {ActivityModule.class, MainModule.class})
public interface MainComponent extends ActivityComponent {
    void inject(MainActivity activity);

    void inject(NewFragment fragment);

    void inject(InfoFragment fragment);

    void inject(BlogFragment fragment);

    void inject(QuestionFragment fragment);

    void inject(ActiveFragment fragment);

    void inject(TweetFragment fragment);

    void inject(TweetPagerFragment fragment);

    void inject(ExploreFragment fragment);
}