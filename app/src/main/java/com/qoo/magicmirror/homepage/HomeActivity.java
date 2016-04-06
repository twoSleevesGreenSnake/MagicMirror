package com.qoo.magicmirror.homepage;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.qoo.magicmirror.R;
import com.qoo.magicmirror.base.BaseActivity;
import com.qoo.magicmirror.constants.NetConstants;
import com.qoo.magicmirror.loginandregister.LoginActivity;
import com.qoo.magicmirror.net.NetHelper;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

/**
 * App首页
 */
public class HomeActivity extends BaseActivity implements MenuFragment.MenuClickListener {

    private ArrayList<Fragment> fragments;
    private VerticalViewPagerAdapter adapter;
    private VerticalViewPager verticalViewPager;
    private ArrayList<String> titles;
    private ImageView logoIv;
    private MenuFragment menuFragment;
    private ArrayList<String> categoryId;
    private TextView loginTv;

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
        loginTv = bindView(R.id.activity_login_tv);
    }

    @Override
    protected void initData() {
        categoryId = new ArrayList<String>();
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
        // 给fragment传递titles，并设置点击替换的MenuFragment
        ShoppingCartFragment shoppingCartFragment = new ShoppingCartFragment();
        Bundle bundleS = new Bundle();
        bundleS.putStringArrayList(Value.putShoppingTitle, titles);
        shoppingCartFragment.setArguments(bundleS);
        fragments.add(shoppingCartFragment);
        shoppingCartFragment.setMenuListener(new MenuListener() {
            @Override
            public void clickMenu() {
                getSupportFragmentManager().beginTransaction().show(getSupportFragmentManager().findFragmentByTag(getString(R.string.MENU))).commit();
            }
        });

        // 给fragment传递titles，并且获得滑动事件时Page的position，传递给MenuFragemnt
        menuFragment = new MenuFragment();
        final Bundle bundleM = new Bundle();
        bundleM.putStringArrayList(getString(R.string.MENUTITLE), titles);
        menuFragment.setArguments(bundleM);
        verticalViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                menuFragment.getMenuCheckedPosition(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


        // menufragment占位布局的替换
        getSupportFragmentManager().beginTransaction().replace(R.id.activity_main_menu_fl, menuFragment, getString(R.string.MENU)).hide(menuFragment).commit();

        // 绑定纵向滑动的Viewpager的适配器

        // 点击logo的动画效果
        logoIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 属性动画
                // ObjectAnimator / ValueAnimator 动画的执行类
                // ofFloat构建并返回一个objectanimator动画之间的浮点值(小数值)。
                // 参数1：目标对象（Object target）
                // 参数2：属性名（String propertyName）
                // 参数3~N：小数值
                ObjectAnimator.ofFloat(v, "scaleX", 1.0f, 1.2f, 1.0f, 1.1f, 1.0f).setDuration(500).start();
                ObjectAnimator.ofFloat(v, "scaleY", 1.0f, 1.2f, 1.0f, 1.1f, 1.0f).setDuration(500).start();
            }
        });

        // 启动闪屏页
        startActivity(new Intent(this, WelcomeActivity.class));

        getNetInfo();

        loginTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this, LoginActivity.class));
            }
        });
    }


    /**
     * 接口回调，从MenuFragment传来的listview点击的position，根据position让Viewpager进行自动跳转
     *
     * @param menuPosition 从MenuFragment传来的listview点击的position
     */
    @Override
    public void menuClickListener(int menuPosition) {
        if (menuPosition < 5) {
            verticalViewPager.setCurrentItem(menuPosition);
        } else if (menuPosition == 5) {
            verticalViewPager.setCurrentItem(0);
        }
    }

    public void getNetInfo() {
        NetHelper netHelper = new NetHelper(this);
        ArrayList<String> token = new ArrayList<>();
        token.add(getString(R.string.token));
        ArrayList<String> value = new ArrayList<>();
        value.add("");
        netHelper.getPostInfo(NetConstants.CATEGORY_LIST, token, value, CategoryListBean.class, new NetHelper.NetListener<CategoryListBean>() {

            @Override
            public void onSuccess(CategoryListBean categoryListBean) {
                SharedPreferences sp = getSharedPreferences("categoryId",MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();
                editor.clear();
                editor.commit();
                categoryId = new ArrayList<>();
                Log.d("HomeActivity", "categoryListBean:" + categoryListBean.toString());
                for (int i = 0; i < categoryListBean.getData().size(); i++) {
                    categoryId.add(categoryListBean.getData().get(i).getCategory_id());
                    editor.putString(String.valueOf(i), categoryId.get(i));
                    editor.commit();
                }

                adapter = new VerticalViewPagerAdapter(getSupportFragmentManager(), fragments, titles, HomeActivity.this, categoryId,true);
                verticalViewPager.setAdapter(adapter);
            }

            @Override
            public void onFailure() {
               SharedPreferences sp = getSharedPreferences("categoryId", Context.MODE_PRIVATE);
                Iterator ir = sp.getAll().entrySet().iterator();
                ArrayList<String> count  = new ArrayList<String>();
                while (ir.hasNext()) {
                    Map.Entry en = (Map.Entry) ir.next();
                    String Id = sp.getString(String.valueOf(en.getKey()), "");
                    count.add(Id);
                }
                for (int i = 0; i <count.size() ; i++) {
                   categoryId.add(sp.getString(String.valueOf(i),""));
                }
                Log.i("cateid",categoryId.toString());
                adapter = new VerticalViewPagerAdapter(getSupportFragmentManager(), fragments, titles, HomeActivity.this, categoryId,false);
                verticalViewPager.setAdapter(adapter);

            }
        });


    }
}
