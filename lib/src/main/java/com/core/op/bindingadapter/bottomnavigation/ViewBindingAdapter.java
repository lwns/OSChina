package com.core.op.bindingadapter.bottomnavigation;

import android.app.Activity;
import android.databinding.BindingAdapter;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationAdapter;

import com.core.op.lib.command.ReplyCommand;


public final class ViewBindingAdapter {


    /**
     * 底部导航菜单控件初始化
     * @param bottomNavigation
     * @param navigationRes
     */
    @BindingAdapter(value = {"navigation"}, requireAll = false)
    public static void bottomNavigationInit(final AHBottomNavigation bottomNavigation, final NavigationRes navigationRes) {
        if (navigationRes == null) {
            throw new IllegalArgumentException("no tab init!");
        }
        if (navigationRes.getInactive() != 0) {//非选中颜色
            bottomNavigation.setInactiveColor(bottomNavigation.getResources().getColor(navigationRes.getInactive()));//非选中颜色
        }
        if (navigationRes.getAccent() != 0) {//选中颜色
            bottomNavigation.setAccentColor(bottomNavigation.getResources().getColor(navigationRes.getAccent()));
        }
        if (navigationRes.getDefaultBackground() != 0){//默認背景顏色
            bottomNavigation.setDefaultBackgroundColor(bottomNavigation.getResources().getColor(navigationRes.getDefaultBackground()));
        }
        int[] tabColors = bottomNavigation.getContext().getResources().getIntArray(navigationRes.getColors());
        AHBottomNavigationAdapter navigationAdapter = new AHBottomNavigationAdapter((Activity) bottomNavigation.getContext(), navigationRes.getMenu());
        navigationAdapter.setupWithBottomNavigation(bottomNavigation, tabColors);
    }


    @BindingAdapter(value = {"onTabSelectedCommand"}, requireAll = false)
    public static void onTabSelectedCommand(final AHBottomNavigation bottomNavigation, final ReplyCommand<NavigationDataWrapper> replyCommand) {
        bottomNavigation.setOnTabSelectedListener(new AHBottomNavigation.OnTabSelectedListener() {
            @Override
            public boolean onTabSelected(int position, boolean wasSelected) {
                if (replyCommand != null) {
                    replyCommand.execute(new NavigationDataWrapper(position, wasSelected));
                }
                return true;
            }
        });
    }


    public static class NavigationDataWrapper {
        public int position;
        public boolean hasSelect;

        public NavigationDataWrapper(int position, boolean hasSelect) {
            this.position = position;
            this.hasSelect = hasSelect;
        }
    }
}

