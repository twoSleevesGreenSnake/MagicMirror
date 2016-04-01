package com.qoo.magicmirror.homepage;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.Toast;

import com.qoo.magicmirror.R;
import com.qoo.magicmirror.base.BaseActivity;

import java.util.ArrayList;

/**
 * App首页
 */
public class MainActivity extends BaseActivity implements GoodsFragment.PopClickListener {

    private ArrayList<Fragment> fragments;
    private VerticalViewPagerAdapter adapter;
    private VerticalViewPager verticalViewPager;
    private ArrayList<String> titles;
    private ImageView logoIv;

    @Override
    protected int setLayout() {
        return R.layout.activity_main;
    }

    // 初始化数据，绑定组件
    @Override
    protected void initView() {
        fragments = new ArrayList<>();
        verticalViewPager = bindView(R.id.homepage_verticalviewpager);
        logoIv = bindView(R.id.main_activity_logo_img);
        titles = new ArrayList<>();
    }

    // 给ViewPager添加Fragment
    @Override
    protected void initData() {
        titles.add(getString(R.string.fragment_goods_title_tv));
        titles.add(getString(R.string.fragment_palingglasses_title_tv));
        titles.add(getString(R.string.fragment_sunglasses_title_tv));
        titles.add(getString(R.string.fragment_themtaic_title_tv));
        titles.add(getString(R.string.fragment_shoppingcart_title_tv));
        titles.add(getString(R.string.gohome_tv));
        titles.add(getString(R.string.loginout_tv));

        ThematicFragment thematicFragment = new ThematicFragment();
        Bundle bundleT = new Bundle();
        bundleT.putStringArrayList("themtaictitle", titles);
        thematicFragment.setArguments(bundleT);
        fragments.add(thematicFragment);
        ShoppingCartFragment shoppingCartFragment = new ShoppingCartFragment();
        Bundle bundleS = new Bundle();
        bundleS.putStringArrayList("shoppingTitle", titles);
        shoppingCartFragment.setArguments(bundleS);
        fragments.add(shoppingCartFragment);
        adapter = new VerticalViewPagerAdapter(getSupportFragmentManager(), fragments, titles, this);
        verticalViewPager.setAdapter(adapter);

        logoIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "点了", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void popClickListener(int popMenuPosition) {
        verticalViewPager.setCurrentItem(popMenuPosition);
    }
}
