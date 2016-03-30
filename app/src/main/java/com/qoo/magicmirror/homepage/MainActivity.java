package com.qoo.magicmirror.homepage;

import android.support.v4.app.Fragment;

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

    @Override
    protected int setLayout() {
        return R.layout.activity_main;
    }

    // 初始化数据，绑定组件
    @Override
    protected void initData() {
        fragments = new ArrayList<>();
        verticalViewPager = (VerticalViewPager) findViewById(R.id.homepage_verticalviewpager);
    }

    // 给ViewPager添加Fragment
    @Override
    protected void initView() {
        for (int i = 0; i < 3; i++) {
            fragments.add(new GoodsFragment());
        }
        fragments.add(new ThematicFragment());
        fragments.add(new ShoppingCartFragment());
        adapter = new VerticalViewPagerAdapter(getSupportFragmentManager(), fragments, this);
        verticalViewPager.setAdapter(adapter);
    }
}
