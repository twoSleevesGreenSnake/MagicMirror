package com.qoo.magicmirror.homepage;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.qoo.magicmirror.R;

import java.util.ArrayList;

/**
 * Created by dllo on 16/4/1.
 *
 * 首页菜单
 */
public class MenuFragment extends Fragment {

    // 布局中除去listview以外的线性布局
    private LinearLayout ll;
    private ListView listView;
    private ListAdapter adapter;
    private ArrayList<String> titles;
    private MenuClickListener menuClickListener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        menuClickListener = (MenuClickListener) context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_menu, null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ll = (LinearLayout) view.findViewById(R.id.fragment_menu_ll);
        listView = (ListView) view.findViewById(R.id.fragment_menu_lv);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();// 接收MainActivity传递的titles，绑定ListView的适配器
        initData();// 设置listview以外的布局的点击事件隐藏fragment

    }

    private void initData() {
        // 点击隐藏fragment
        ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().hide(MenuFragment.this).commit();
            }
        });
    }

    private void initView() {
        // 接收来自MainActivity传来的titles
        Bundle bundle = getArguments();
        titles = bundle.getStringArrayList("menutitle");
        Log.d("MenuFragment", "titles:" + titles);
        adapter = new ListAdapter(titles);
        listView.setAdapter(adapter);
    }


    /**
     * listview的内部适配器
     */
    private class ListAdapter extends BaseAdapter {

        private ArrayList<String> titles;
        private int checkedItemPosition;

        public ListAdapter(ArrayList<String> titles) {
            this.titles = titles;
        }

        @Override
        public int getCount() {
            return titles.size();
        }

        @Override
        public Object getItem(int position) {
            return titles.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, final ViewGroup parent) {
            MyViewholder myViewholder;
            if (convertView == null) {
                convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_fragment_menu_title, parent, false);
                myViewholder = new MyViewholder();
                myViewholder.titleTv = (TextView) convertView.findViewById(R.id.fragment_menu_goods_title_tv);
                myViewholder.menuLl = (LinearLayout) convertView.findViewById(R.id.fragment_menu_popipwindow_title_ll);
                myViewholder.lineIv = (ImageView) convertView.findViewById(R.id.fragment_popupwindow_title_line_iv);
                convertView.setTag(myViewholder);
            } else {
                myViewholder = (MyViewholder) convertView.getTag();
            }
            myViewholder.titleTv.setText(titles.get(position));
            myViewholder.menuLl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    menuClickListener.menuClickListener(position);
                    getActivity().getSupportFragmentManager().beginTransaction().hide(MenuFragment.this).commit();
                }
            });
            return convertView;
        }

        private class MyViewholder {
            TextView titleTv;
            LinearLayout menuLl;
            ImageView lineIv;
        }
    }

    public interface MenuClickListener {
        void menuClickListener(int menuPosition);
    }

}
