package com.qoo.magicmirror.homepage;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.qoo.magicmirror.R;
import com.qoo.magicmirror.base.BaseActivity;

import java.util.ArrayList;

/**
 * App首页
 */
public class MainActivity extends BaseActivity {

    private ArrayList<Fragment> fragments;
    private VerticalViewPagerAdapter adapter;
    private VerticalViewPager verticalViewPager;
    private ArrayList<String> titles;

    @Override
    protected int setLayout() {
        return R.layout.activity_main;
    }

    // 初始化数据，绑定组件
    @Override
    protected void initView() {
        fragments = new ArrayList<>();
        verticalViewPager = (VerticalViewPager) findViewById(R.id.homepage_verticalviewpager);
        titles = new ArrayList<>();
    }

    // 给ViewPager添加Fragment
    @Override
    protected void initData() {
        titles.add(getString(R.string.fragment_goods_title_tv));
        titles.add(getString(R.string.fragment_palingglasses_title_tv));
        titles.add(getString(R.string.fragment_sunglasses_title_tv));
        titles.add(getString(R.string.fragment_discount_title_tv));
        titles.add(getString(R.string.fragment_themtaic_title_tv));
        titles.add(getString(R.string.gohome_tv));
        titles.add(getString(R.string.loginout_tv));

        ThematicFragment thematicFragment = new ThematicFragment();
        Bundle bundle = new Bundle();
        bundle.putStringArrayList("title", titles);
        thematicFragment.setArguments(bundle);
        fragments.add(thematicFragment);
        fragments.add(new ShoppingCartFragment());
        adapter = new VerticalViewPagerAdapter(getSupportFragmentManager(), fragments, titles, this);
        verticalViewPager.setAdapter(adapter);
    }
}
