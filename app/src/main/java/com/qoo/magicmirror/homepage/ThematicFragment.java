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

import java.util.ArrayList;

/**
 * Created by dllo on 16/3/29.
 *
 * 专题分享的Fragment
 */
public class ThematicFragment extends Fragment {

    private RecyclerView recyclerView;
    private ThemtaicRecycleViewAdapter adapter;
    private ArrayList<String> titles;
    private TextView titleTv;


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
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Bundle bundle = getArguments();
        titles = bundle.getStringArrayList("themtaictitle");
        Log.d("themtaictitle", "titles:" + titles);
        titleTv.setText(titles.get(3));

        GridLayoutManager gm = new GridLayoutManager(getActivity(), 1);
        gm.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(gm);
//        adapter = new ThemtaicRecycleViewAdapter(datas, getActivity());
        recyclerView.setAdapter(adapter);
    }
}
