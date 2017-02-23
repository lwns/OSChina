package com.core.op.base.tab;


import android.databinding.ObservableField;
import android.support.v4.app.Fragment;

import com.core.op.databinding.FrgTabBinding;
import com.core.op.lib.base.BFViewModel;
import com.core.op.lib.di.PerActivity;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class TabViewModel extends BFViewModel<FrgTabBinding> {

    public final ObservableField<Integer> selectPosition = new ObservableField<>(0);

    public final List<Fragment> fragments = new ArrayList<>();

    public final List<String> titles = new ArrayList<>();

    public final ObservableField<Integer> pageLimit = new ObservableField<>(0);

    @Inject
    public TabViewModel(RxAppCompatActivity activity) {
        super(activity);
    }

    @Override
    public void afterViews() {
        binding.tabLayout.setupWithViewPager(binding.viewPager);
    }
}