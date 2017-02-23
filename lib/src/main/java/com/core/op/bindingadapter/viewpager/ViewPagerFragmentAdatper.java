package com.core.op.bindingadapter.viewpager;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * @author op
 * @version 1.0
 * @description
 * @createDate 2016/9/12
 */
public class ViewPagerFragmentAdatper extends FragmentPagerAdapter {

    private List<Fragment> fragments;
    private List<String> titles;

    public ViewPagerFragmentAdatper(FragmentManager fm) {
        super(fm);
    }

    public ViewPagerFragmentAdatper(FragmentManager fm, List<Fragment> fragments) {
        super(fm);
        this.fragments = fragments;
    }


    public ViewPagerFragmentAdatper(FragmentManager fm, List<Fragment> fragments, List<String> titles) {
        super(fm);
        this.fragments = fragments;
        this.titles = titles;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    public void setFragments(List<Fragment> fragments) {
        this.fragments = fragments;
    }

    public void setTitles(List<String> titles) {
        this.titles = titles;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles.get(position);
    }
}
