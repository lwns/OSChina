package com.core.op.feature.main.news;


import com.core.op.R;
import com.core.op.base.tab.TabViewModel;
import com.core.op.feature.main.news.active.ActiveFragment;
import com.core.op.feature.main.news.blog.BlogFragment;
import com.core.op.feature.main.news.info.InfoFragment;
import com.core.op.feature.main.news.question.QuestionFragment;
import com.core.op.lib.di.PerActivity;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import java.util.Arrays;

import javax.inject.Inject;

@PerActivity
public class NewViewModel extends TabViewModel {

    @Inject
    public NewViewModel(RxAppCompatActivity activity) {
        super(activity);
        pageLimit.set(4);
        titles.addAll(Arrays.asList(activity.getResources().getStringArray(R.array.new_tabs)));
        fragments.add(InfoFragment.instance());
        fragments.add(BlogFragment.instance());
        fragments.add(QuestionFragment.instance());
        fragments.add(ActiveFragment.instance());
    }

    @Override
    public void afterViews() {
        super.afterViews();
    }
}