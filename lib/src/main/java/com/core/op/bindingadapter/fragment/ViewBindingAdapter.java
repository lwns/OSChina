package com.core.op.bindingadapter.fragment;

import android.databinding.BindingAdapter;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.FrameLayout;


import java.util.ArrayList;
import java.util.List;

/**
 * @author op
 * @version 1.0
 * @description
 * @createDate 2016/10/9
 */
public final class ViewBindingAdapter {

    /**
     * Fragment切换
     * <FrameLayout
     * android:id="@+id/container"
     * android:layout_width="match_parent"
     * android:layout_height="match_parent"
     * app:currentIndex="@{viewModel.currentIndex}"
     * app:fragemnts="@{viewModel.fragments}"
     * app:frgManager="@{viewModel.fragmentManager}"
     * app:index="@{viewModel.index}" />
     */
    @BindingAdapter(value = {"frgManager", "idRes", "fragemnts", "index", "currentIndex"}, requireAll = false)
    public static void switchFragment(final FrameLayout frameLayout, final FragmentManager fragmentManager, final @IdRes int idRes, List<Fragment> items, final int index, final int currentTabIndex) {
        if (items == null) {
            items = new ArrayList<>();
            return;
        }
        FragmentTransaction trx = fragmentManager.beginTransaction();
        trx.hide(items.get(currentTabIndex));
        if (!items.get(index).isAdded()) {
            trx.add(idRes, items.get(index));
        }
        trx.show(items.get(index)).commit();
    }
}
