package com.core.op.feature.back.software;


import com.core.op.R;
import com.core.op.base.tab.TabViewModel;
import com.core.op.feature.back.software.recommend.RecommendFragment;
import com.core.op.lib.di.PerActivity;
import com.domain.bean.SoftwareList;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import java.util.Arrays;

import javax.inject.Inject;

@PerActivity
public class SoftwareViewModel extends TabViewModel {

    @Inject
    public SoftwareViewModel(RxAppCompatActivity activity) {
        super(activity);
        pageLimit.set(4);
        titles.addAll(Arrays.asList(activity.getResources().getStringArray(R.array.software_tabs)));
        fragments.add(RecommendFragment.instance(SoftwareList.CATALOG_RECOMMEND));
        fragments.add(RecommendFragment.instance(SoftwareList.CATALOG_TIME));
        fragments.add(RecommendFragment.instance(SoftwareList.CATALOG_VIEW));
        fragments.add(RecommendFragment.instance(SoftwareList.CATALOG_LIST_CN));
    }

    @Override
    public void afterViews() {
        super.afterViews();
    }
}