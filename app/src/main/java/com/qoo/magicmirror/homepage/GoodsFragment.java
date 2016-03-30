package com.qoo.magicmirror.homepage;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.qoo.magicmirror.R;
import com.qoo.magicmirror.net.NetHelper;
import com.squareup.okhttp.Request;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by dllo on 16/3/29.
 * <p/>
 * 商品展示的Fragment
 */
public class GoodsFragment extends Fragment {

    private RecyclerView recyclerView;
    private GoodsRecycleViewAdapter adapter;
    private TextView titleTv;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_goods, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        data = new ArrayList<>();
        recyclerView = (RecyclerView) view.findViewById(R.id.fragment_goods_rv);
        titleTv = (TextView) view.findViewById(R.id.fragment_title_tv);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
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
        NetHelper netHelper = new NetHelper(getActivity());
        netHelper.getPostInfo("index.php/products/goods_list", token, value, null, new NetHelper.NetListener() {
            @Override
            public void onSuccess(Object o) {

            }

            @Override
            public void onFailure(Request request, IOException e) {

            }
        });


    }
}
