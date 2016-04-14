package com.qoo.magicmirror.homepage;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;
import android.view.ViewGroup;

import com.qoo.magicmirror.R;

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
    private FragmentManager fm;
    private ArrayList<String> categoryId;
    private boolean hasNet;

    public VerticalViewPagerAdapter(FragmentManager fm, ArrayList<Fragment> fragments, ArrayList<String> titles, Context context, ArrayList<String> categoryId, boolean hasNet) {
        super(fm);
        this.fm = fm;
        this.fragments = fragments;
        this.titles = titles;
        this.context = context;
        this.categoryId = categoryId;
        this.hasNet = hasNet;
    }

    @Override
    public Fragment getItem(int position) {
        if (position < 3) {

            final GoodsFragment fragment = (GoodsFragment) GoodsFragment.getInstance(position, (ArrayList<String>) titles, categoryId, hasNet);
            Log.i("hasNet", hasNet + "");
            fragment.setMenuListener(new MenuListener() {
                @Override
                public void clickMenu() {
                    fm.beginTransaction().setCustomAnimations(R.anim.fragment_menu_anim, R.anim.fragment_menu_anim)
                            .show(VerticalViewPagerAdapter.this.fm.findFragmentByTag("menu")).addToBackStack(null).commit();
                }
            });
            return fragment;
        } else {
            return fragments.get(position - 3);
        }
    }

    @Override
    public int getCount() {
        return 5;
    }

    public void upData() {
        if (fragments.size() > 0) {
            for (int i = 0; i < fm.getFragments().size(); i++) {
                fm.beginTransaction().remove(fm.getFragments().get(i)).commit();

            }
        }
    }
}
