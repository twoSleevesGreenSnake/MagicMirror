package com.qoo.magicmirror.homepage;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.qoo.magicmirror.R;
import com.qoo.magicmirror.constants.NetConstants;
import com.qoo.magicmirror.net.NetHelper;
import com.squareup.okhttp.Request;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by dllo on 16/3/29.
 * <p/>
 * 商品展示的Fragment
 */
public class GoodsFragment extends Fragment implements View.OnClickListener {

    private RecyclerView recyclerView;
    private GoodsRecycleViewAdapter adapter;
    private ArrayList<GoodsListBean.DataEntity.ListEntity> datas;
    private Handler handler;
    private RelativeLayout titleRl;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_goods, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = (RecyclerView) view.findViewById(R.id.fragment_goods_rv);
        titleRl = (RelativeLayout) view.findViewById(R.id.fragment_goods_title_rl);
        datas = new ArrayList<>();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                adapter = new GoodsRecycleViewAdapter(datas, getActivity());
                GridLayoutManager gm = new GridLayoutManager(getActivity(), 1);
                gm.setOrientation(GridLayoutManager.HORIZONTAL);
                recyclerView.setLayoutManager(gm);
                recyclerView.setAdapter(adapter);
                return false;
            }
        });
        titleRl.setOnClickListener(this);
    }

    private void initView() {
        ArrayList<String> token = new ArrayList<>();
        token.add("token");
        token.add("device_type");
        token.add("page");
        token.add("last_time");
        token.add("category_id");
        token.add("version");
        ArrayList<String> value = new ArrayList<>();
        value.add("");
        value.add("1");
        value.add("");
        value.add("");
        value.add("");
        value.add("1.0.0");
        NetHelper netHelper = new NetHelper(getContext());
        netHelper.getPostInfo(NetConstants.GOODS_TYPE, token, value, GoodsListBean.class, new NetHelper.NetListener<GoodsListBean>() {
            @Override
            public void onSuccess(GoodsListBean goodsListBean) {
                datas = (ArrayList<GoodsListBean.DataEntity.ListEntity>) goodsListBean.getData().getList();
                handler.sendEmptyMessage(1);

            }
            @Override
            public void onFailure(Request request, IOException e) {
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fragment_goods_title_rl:
                showMenuPopWindow(v);
                break;
        }
    }

    /**
     * 显示PopupWindow
     * @param v 父布局
     */
    private void showMenuPopWindow(View v) {
        final PopupWindow popupWindow = new PopupWindow(getActivity());
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_menu_popupwindow, null);
        popupWindow.setContentView(view);

        popupWindow.setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
        popupWindow.setHeight(LinearLayout.LayoutParams.MATCH_PARENT);
        popupWindow.setAnimationStyle(R.anim.fragment_menu_popupwindow);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
        // 指定位置显示
        popupWindow.showAtLocation(v,LinearLayout.HORIZONTAL,0,0);
    }
}
