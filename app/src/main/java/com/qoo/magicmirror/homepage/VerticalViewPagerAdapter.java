package com.qoo.magicmirror.homepage;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dllo on 16/3/29.
 *
 * 主页纵向滑动的ViewPager的Fragment的适配器
 */
public class VerticalViewPagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> fragments;
    private Context context;

    public VerticalViewPagerAdapter(FragmentManager fm, ArrayList<Fragment> fragments, Context context) {
        super(fm);
        this.fragments = fragments;
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments != null && fragments.size() > 0 ? fragments.size() : 0;
    }
}
