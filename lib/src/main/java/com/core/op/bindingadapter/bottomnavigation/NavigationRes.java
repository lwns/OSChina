package com.core.op.bindingadapter.bottomnavigation;

import android.support.annotation.ArrayRes;
import android.support.annotation.ColorRes;
import android.support.annotation.MenuRes;

/**
 * @author op
 * @version 1.0
 * @description
 * @createDate 2016/9/8
 */
public class NavigationRes {

    @ArrayRes
    private int colors;

    @MenuRes
    private int menu;

    @ColorRes
    private int inactive;

    @ColorRes
    private int accent;

    @ColorRes
    private int defaultBackground;

    public final static NavigationRes of(@ArrayRes int colors, @MenuRes int menu) {
        return new NavigationRes()
                .setColors(colors)
                .setMenu(menu);
    }

    public int getColors() {
        return colors;
    }

    public NavigationRes setColors(int colors) {
        this.colors = colors;
        return this;
    }

    public int getMenu() {
        return menu;
    }

    public NavigationRes setMenu(int menu) {
        this.menu = menu;
        return this;
    }

    public int getInactive() {
        return inactive;
    }

    public NavigationRes setInactive(int inactive) {
        this.inactive = inactive;
        return this;
    }

    public int getAccent() {
        return accent;
    }

    public NavigationRes setAccent(int accent) {
        this.accent = accent;
        return this;
    }

    public int getDefaultBackground() {
        return defaultBackground;
    }

    public NavigationRes setDefaultBackground(int defaultBackground) {
        this.defaultBackground = defaultBackground;
        return this;
    }
}
