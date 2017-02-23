package com.core.op.feature.back;


import android.content.Intent;
import android.databinding.ObservableField;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.core.op.R;
import com.core.op.databinding.ActSimplebackBinding;
import com.core.op.lib.base.BAViewModel;
import com.core.op.lib.di.PerActivity;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import java.lang.ref.WeakReference;

import javax.inject.Inject;

@PerActivity
public class SimpleBackViewModel extends BAViewModel<ActSimplebackBinding> {

    public final ObservableField<String> title = new ObservableField<>();

    protected WeakReference<Fragment> mFragment;

    @Inject
    public SimpleBackViewModel(RxAppCompatActivity activity) {
        super(activity);
    }

    @Override
    public void afterViews() {
        initFragemnt();
    }

    private void initFragemnt() {
        Intent intent = activity.getIntent();
        int page = intent.getIntExtra("page", -1);
        if (intent == null) {
            throw new RuntimeException("you must provide a page info to display");
        }
        SimpleBackPage backPage = SimpleBackPage.getPageByValue(page);
        if (backPage == null) {
            throw new IllegalArgumentException("can not find page by value:" + backPage);
        }

        title.set(activity.getString(backPage.getTitle()));

        try {
            Fragment fragment = (Fragment) backPage.getClz().newInstance();
            Bundle args = intent.getBundleExtra("data");
            if (args != null) {
                fragment.setArguments(args);
            }
            addFragment(R.id.fl_content, fragment);
            mFragment = new WeakReference<>(fragment);
        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalArgumentException(
                    "generate fragment error. by value:" + page);
        }
    }
}