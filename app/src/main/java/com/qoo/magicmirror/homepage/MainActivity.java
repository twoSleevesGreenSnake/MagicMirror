package com.qoo.magicmirror.homepage;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;

import com.qoo.magicmirror.R;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<Fragment> fragments;
    private VerticalViewPagerAdapter adapter;
    private VerticalViewPager verticalViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams. FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initData();
        initView();
    }

    // 初始化数据，绑定组件
    private void initData() {
        fragments = new ArrayList<>();
        verticalViewPager = (VerticalViewPager) findViewById(R.id.homepage_verticalviewpager);
    }

    // 给ViewPager添加Fragment
    private void initView() {
        for (int i = 0; i < 3; i++) {
            fragments.add(new GoodsFragment());
        }
        fragments.add(new ThematicSharingFragment());
        fragments.add(new ShoppingCartFragment());
        adapter = new VerticalViewPagerAdapter(getSupportFragmentManager(), fragments, this);
        verticalViewPager.setAdapter(adapter);
    }
}
