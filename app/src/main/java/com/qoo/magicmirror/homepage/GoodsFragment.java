package com.qoo.magicmirror.homepage;


import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.qoo.magicmirror.R;
import com.qoo.magicmirror.constants.NetConstants;
import com.qoo.magicmirror.net.NetHelper;

import java.util.ArrayList;

/**
 * Created by dllo on 16/3/29.
 * <p/>
 * 商品展示的Fragment
 */
public class GoodsFragment extends Fragment {

    private RecyclerView recyclerView;
    private GoodsRecycleViewAdapter adapter;
    private ArrayList<GoodsListBean.DataEntity.ListEntity> datas;
    // 标题的帧布局
    private FrameLayout titleFl;
    // MainActivity适配器传递过来的Viewpager的position
    private int position;
    // MainActivity传递过来的标题集合
    private ArrayList<String> popTitles;
    // 标题
    private TextView titleTv;
    private MenuListener menuListener;
    private ArrayList<String> categoryId;

    /**
     * 相当于初始化
     *
     * @param menuListener
     */
    public void setMenuListener(MenuListener menuListener) {
        this.menuListener = menuListener;
    }

    /**
     * 提供静态方法，添加fragment
     * 静态方法，返回新的Fragment
     *
     * @return 新的Fragment
     */
    public static Fragment getInstance(int position, ArrayList<String> popTitles,ArrayList<String> categoryId) {
        Fragment instance = new GoodsFragment();
        Bundle bundle = new Bundle();
        bundle.putStringArrayList("categoryId", categoryId);
        bundle.putInt(Value.putPosition, position);
        bundle.putStringArrayList(Value.putPopTitles, popTitles);
        instance.setArguments(bundle);
        return instance;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_goods, container, false);
    }

    // 绑定组件ID，初始化
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = (RecyclerView) view.findViewById(R.id.fragment_goods_rv);
        titleFl = (FrameLayout) view.findViewById(R.id.fragment_goods_title_fl);
        titleTv = (TextView) view.findViewById(R.id.fragment_title_tv);
        datas = new ArrayList<>();
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();// 接收静态方法传来的值，设置标题，标题点击事件
        initView();// 解析数据

    }


    private void initData() {
        Bundle bundle = getArguments();
        categoryId = bundle.getStringArrayList("categoryId");
        position = bundle.getInt(Value.putPosition);
        popTitles = bundle.getStringArrayList(Value.putPopTitles);
        Log.d("GoodsFragment", "+++++" + categoryId);
        titleTv.setText(popTitles.get(position));
        titleFl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                menuListener.clickMenu();
            }
        });
    }

    private void initView() {
        ArrayList<String> token = new ArrayList<>();
        token.add(getString(R.string.token));
        token.add(getString(R.string.device_type));
        token.add(getString(R.string.page));
        token.add(getString(R.string.last_time));
        token.add(getString(R.string.category_id));
        token.add(getString(R.string.version));
        ArrayList<String> value = new ArrayList<>();
        value.add("");
        value.add(getString(R.string.one));
        value.add("");
        value.add("");
        value.add(categoryId.get(position));
        value.add(getString(R.string.one_point_zero_point_one));
        NetHelper netHelper = new NetHelper(getContext());
        netHelper.getPostInfo(NetConstants.GOODS_TYPE, token, value, GoodsListBean.class, new NetHelper.NetListener<GoodsListBean>() {
                    @Override
                    public void onSuccess(GoodsListBean goodsListBean) {
                        datas = (ArrayList<GoodsListBean.DataEntity.ListEntity>) goodsListBean.getData().getList();
                        adapter = new GoodsRecycleViewAdapter(datas, getActivity());
                        GridLayoutManager gm = new GridLayoutManager(getActivity(), 1);
                        gm.setOrientation(GridLayoutManager.HORIZONTAL);
                        recyclerView.setLayoutManager(gm);
                        recyclerView.setAdapter(adapter);
                    }

                    @Override
                    public void onFailure() {

                    }
                }
        );
    }
}
