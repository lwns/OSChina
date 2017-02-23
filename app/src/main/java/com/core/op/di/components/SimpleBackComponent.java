package com.core.op.di.components;

import dagger.Component;


import com.core.op.di.modules.ActivityModule;
import com.core.op.di.modules.SimpleBackModule;
import com.core.op.feature.back.SimpleBackActivity;
import com.core.op.feature.back.active.ActiveFragment;
import com.core.op.feature.back.mine.blog.BlogFragment;
import com.core.op.feature.back.mine.message.MessageFragment;
import com.core.op.feature.back.pub.PubFragment;
import com.core.op.feature.back.setting.SettingFragment;
import com.core.op.feature.back.shake.ShakeFragment;
import com.core.op.feature.back.software.SoftwareFragment;
import com.core.op.feature.back.software.recommend.RecommendFragment;
import com.core.op.lib.di.PerActivity;

@PerActivity
@Component(dependencies = AppComponent.class, modules = {ActivityModule.class, SimpleBackModule.class})
public interface SimpleBackComponent extends ActivityComponent {
    void inject(SimpleBackActivity activity);

    void inject(SettingFragment fragment);

    void inject(SoftwareFragment fragment);

    void inject(RecommendFragment fragment);

    void inject(ShakeFragment fragment);

    void inject(ActiveFragment fragment);

    void inject(PubFragment fragment);

    void inject(MessageFragment fragment);

    void inject(BlogFragment fragment);
}