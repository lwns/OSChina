package com.core.op.bindingadapter.common.factories;

import android.support.v4.view.ViewPager;

import com.core.op.bindingadapter.common.BindingLoopViewPagerAdapter;
import com.core.op.bindingadapter.common.BindingViewPagerAdapter;
import com.core.op.bindingadapter.common.ItemViewArg;


public interface BindingViewPagerAdapterFactory {
    <T> BindingViewPagerAdapter<T> create(ViewPager viewPager, ItemViewArg<T> arg);

    BindingViewPagerAdapterFactory DEFAULT = new BindingViewPagerAdapterFactory() {
        @Override
        public <T> BindingViewPagerAdapter<T> create(ViewPager viewPager, ItemViewArg<T> arg) {
            return new BindingViewPagerAdapter<>(arg);
        }
    };

    BindingViewPagerAdapterFactory BANNER = new BindingViewPagerAdapterFactory() {
        @Override
        public <T> BindingViewPagerAdapter<T> create(ViewPager viewPager, ItemViewArg<T> arg) {
            return new BindingLoopViewPagerAdapter<>(arg);
        }
    };
}
