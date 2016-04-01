package com.qoo.magicmirror.homepage;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dllo on 16/3/29.
 * <p/>
 * 主页纵向滑动的ViewPager的Fragment的适配器
 */
public class VerticalViewPagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> fragments;
    private List<String> titles;
    private Context context;

    public VerticalViewPagerAdapter(FragmentManager fm, ArrayList<Fragment> fragments, ArrayList<String> titles, Context context) {
        super(fm);
        this.fragments = fragments;
        this.titles = titles;
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        if (position < 3) {
            return GoodsFragment.getInstance(position, titles.get(position), (ArrayList<String>) titles);
        } else {
            return fragments.get(position - 3);
        }
    }

    @Override
    public int getCount() {
        return 5;
    }
}
