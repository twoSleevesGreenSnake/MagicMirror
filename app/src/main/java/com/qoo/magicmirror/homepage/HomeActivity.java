package com.qoo.magicmirror.homepage;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

import com.qoo.magicmirror.R;
import com.qoo.magicmirror.base.BaseActivity;

import java.util.ArrayList;

/**
 * App首页
 */
public class HomeActivity extends BaseActivity implements MenuFragment.MenuClickListener {

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

    @Override
    protected void initData() {
        titles.add(getString(R.string.fragment_goods_title_tv));
        titles.add(getString(R.string.fragment_palingglasses_title_tv));
        titles.add(getString(R.string.fragment_sunglasses_title_tv));
        titles.add(getString(R.string.fragment_themtaic_title_tv));
        titles.add(getString(R.string.fragment_shoppingcart_title_tv));
        titles.add(getString(R.string.gohome_tv));
        titles.add(getString(R.string.loginout_tv));

        // 给fragment传递titles，并设置点击替换的MenuFragment
        ThematicFragment thematicFragment = new ThematicFragment();
        Bundle bundleT = new Bundle();
        bundleT.putStringArrayList(Value.putThematicTitle, titles);
        thematicFragment.setArguments(bundleT);
        fragments.add(thematicFragment);
        thematicFragment.setMenuListener(new MenuListener() {
            @Override
            public void clickMenu() {
                getSupportFragmentManager().beginTransaction().show(getSupportFragmentManager().findFragmentByTag("menu")).commit();
            }
        });
        ShoppingCartFragment shoppingCartFragment = new ShoppingCartFragment();
        Bundle bundleS = new Bundle();
        bundleS.putStringArrayList(Value.putShoppingTitle, titles);
        shoppingCartFragment.setArguments(bundleS);
        fragments.add(shoppingCartFragment);
        shoppingCartFragment.setMenuListener(new MenuListener() {
            @Override
            public void clickMenu() {
                getSupportFragmentManager().beginTransaction().show(getSupportFragmentManager().findFragmentByTag("menu")).commit();
            }
        });
        MenuFragment menuFragment = new MenuFragment();
        Bundle bundleM = new Bundle();
        bundleM.putStringArrayList("menutitle", titles);
        menuFragment.setArguments(bundleM);


        getSupportFragmentManager().beginTransaction().replace(R.id.activity_main_menu_fl, menuFragment, "menu").hide(menuFragment).commit();

        // 绑定纵向滑动的Viewpager的适配器
        adapter = new VerticalViewPagerAdapter(getSupportFragmentManager(), fragments, titles, HomeActivity.this);
        verticalViewPager.setAdapter(adapter);

        // 点击logo的动画效果
        logoIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

        startActivity(new Intent(this, WelcomeActivity.class));
    }

    /**
     * 接口回调，从MenuFragment传来的listview点击的position，根据position让Viewpager进行自动跳转
     * @param menuPosition 从MenuFragment传来的listview点击的position
     */
    @Override
    public void menuClickListener(int menuPosition) {
        if (menuPosition < 5) {
            verticalViewPager.setCurrentItem(menuPosition);
        } else if(menuPosition == 5) {
            verticalViewPager.setCurrentItem(0);
        }
    }
}
