package com.qoo.magicmirror.homepage;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.qoo.magicmirror.R;

import java.util.ArrayList;

/**
 * Created by dllo on 16/3/29.
 *
 * 购物车页面
 */
public class ShoppingCartFragment extends Fragment {

    // 接收MainActivity传递的titles
    private ArrayList<String> shoppingTitle;
    // 标题
    private TextView titleTv;
    // 标题所在的相对布局
    private RelativeLayout titleRl;
    private MenuListener menuListener;

    public void setMenuListener(MenuListener menuListener) {
        this.menuListener = menuListener;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_shoppingcart, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        shoppingTitle = new ArrayList<>();
        titleTv = (TextView) view.findViewById(R.id.fragment_shopping_title_tv);
        titleRl = (RelativeLayout) view.findViewById(R.id.fragment_shopping_title_rl);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();// 接收MainActivity传递的titles
        initData();// 设置listview以外的布局的点击事件隐藏fragment
    }

    private void initData() {
        titleRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                menuListener.clickMenu();
            }
        });
    }

    private void initView() {
        // 设置标题
        Bundle bundle = getArguments();
        shoppingTitle = bundle.getStringArrayList("shoppingTitle");
        titleTv.setText(shoppingTitle.get(4));
    }
}
