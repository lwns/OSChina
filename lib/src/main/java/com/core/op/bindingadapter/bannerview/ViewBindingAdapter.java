package com.core.op.bindingadapter.bannerview;

import android.databinding.BindingAdapter;

import com.core.op.bindingadapter.common.BindingViewPagerAdapter;
import com.core.op.bindingadapter.common.ItemViewArg;
import com.core.op.bindingadapter.common.factories.BindingViewPagerAdapterFactory;
import com.core.op.lib.weight.BannerView;

import java.util.List;


/**
 * Created by kelin on 16-3-24.
 */
public final class ViewBindingAdapter {

    @SuppressWarnings("unchecked")
    @BindingAdapter(value = {"itemView", "items", "adapter", "pageTitles"}, requireAll = false)
    public static <T> void initBanner(BannerView bannerView, ItemViewArg<T> arg, List<T> items, BindingViewPagerAdapterFactory factory, BindingViewPagerAdapter.PageTitles<T> pageTitles) {
        if (arg == null) {
            throw new IllegalArgumentException("itemView must not be null");
        }
        if (factory == null) {
            factory = BindingViewPagerAdapterFactory.BANNER;
        }
        BindingViewPagerAdapter<T> adapter = (BindingViewPagerAdapter<T>) bannerView.getViewPager().getAdapter();
        if (adapter == null) {
            adapter = factory.create(bannerView.getViewPager(), arg);
            adapter.setItems(items);
            adapter.setPageTitles(pageTitles);
            bannerView.getViewPager().setAdapter(adapter);
        } else {
            adapter.setItems(items);
            adapter.setPageTitles(pageTitles);
        }
        if (items != null && items.size() > 1)
            bannerView.delayTime(5).startScroll();
    }
}

