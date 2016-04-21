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
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.qoo.magicmirror.R;
import com.qoo.magicmirror.constants.NetConstants;
import com.qoo.magicmirror.net.NetHelper;

import java.util.ArrayList;

/**
 * Created by dllo on 16/3/29.
 *
 * 专题分享的Fragment
 */
public class ThematicFragment extends Fragment {

    private RecyclerView recyclerView;
    private ThematicRecycleViewAdapter adapter;
    // MainActivity传递过来的标题集合
    private ArrayList<String> titles;
    // 标题
    private TextView titleTv;
    // 标题所在的相对布局
    private RelativeLayout relativeLayout;
    private ArrayList<ThematicBean.DataEntity.ListEntity> datas;
    private NetHelper netHelper;
    private MenuListener menuListener;


    public void setMenuListener(MenuListener menuListener) {
        this.menuListener = menuListener;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_thematicsharing, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = (RecyclerView) view.findViewById(R.id.fragment_themtaic_rv);
        titleTv = (TextView) view.findViewById(R.id.fragment_themtaic_title_tv);
        relativeLayout = (RelativeLayout) view.findViewById(R.id.fragment_thematic_rl);
        datas = new ArrayList<>();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();// 接收接收MainActivity传递的titles
        initView();// 解析recycleView数据
    }

    private void initData() {
        // 设置标题
        Bundle bundle = getArguments();
        titles = bundle.getStringArrayList(getString(R.string.themtaictitle));
        titleTv.setText(titles.get(3));
    }

    private void initView() {
        netHelper = NetHelper.newNetHelper(getContext());
        ArrayList<String> token = new ArrayList<>();
        ArrayList<String> value = new ArrayList<>();
        token.add(getString(R.string.token));
        token.add(getString(R.string.story_uid));
        token.add(getString(R.string.device_type));
        token.add(getString(R.string.page));
        token.add(getString(R.string.last_time));
        value.add("");
        value.add("");
        value.add(getString(R.string.one));
        value.add("");
        value.add("");
        netHelper.getPostInfo(NetConstants.THEMATIC_TYPE, token, value, ThematicBean.class, new NetHelper.NetListener<ThematicBean>() {
            @Override
            public void onSuccess(ThematicBean thematicBean) {
                datas = (ArrayList<ThematicBean.DataEntity.ListEntity>) thematicBean.getData().getList();
                adapter = new ThematicRecycleViewAdapter(datas, getActivity());
                GridLayoutManager gm = new GridLayoutManager(getActivity(), 1);
                gm.setOrientation(LinearLayoutManager.HORIZONTAL);
                recyclerView.setLayoutManager(gm);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure() {

            }
        });

        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                menuListener.clickMenu();
            }
        });
    }
}
