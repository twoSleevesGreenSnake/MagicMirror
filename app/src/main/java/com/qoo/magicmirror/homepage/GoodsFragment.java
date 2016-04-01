package com.qoo.magicmirror.homepage;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
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
public class GoodsFragment extends Fragment implements View.OnClickListener {

    private RecyclerView recyclerView;
    private GoodsRecycleViewAdapter adapter;
    private ArrayList<GoodsListBean.DataEntity.ListEntity> datas;
    private FrameLayout titleFl;
    private int position;
    private String titles;
    private ArrayList<String> popTitles;
    private TextView titleTv;
    private PopClickListener popClickListener;
    private PopupWindow popupWindow;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        popClickListener = (GoodsFragment.PopClickListener) context;
    }

    /**
     * 提供静态方法，添加fragment
     * 静态方法，返回新的Fragment
     *
     * @param position viewpager的位置
     * @return 新的Fragment
     */
    public static Fragment getInstance(int position, String titles, ArrayList<String> popTitles) {
        Fragment instance = new GoodsFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("position", position);
        bundle.putString("titles", titles);
        bundle.putStringArrayList("poptitles", popTitles);
        instance.setArguments(bundle);
        return instance;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_goods, container, false);
    }

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
        Bundle bundle = getArguments();
        position = bundle.getInt("position");
        titles = bundle.getString("titles");
        popTitles = bundle.getStringArrayList("poptitles");
        Log.d("GoodsFragment", "popTitles:" + popTitles);
        initView();
        titleTv.setText(popTitles.get(position));
        titleFl.setOnClickListener(this);
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
        value.add("");
        value.add(getString(R.string.one_point_zero_point_zero));
        NetHelper netHelper = new NetHelper(getContext());
        netHelper.getPostInfo(NetConstants.GOODS_TYPE, token, value, GoodsListBean.class, new NetHelper.NetListener<GoodsListBean>() {
                    @Override
                    public void onSuccess(GoodsListBean goodsListBean) {
                        datas = (ArrayList<GoodsListBean.DataEntity.ListEntity>) goodsListBean.getData().getList();
                        adapter = new GoodsRecycleViewAdapter(datas, getActivity(), position);
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


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fragment_goods_title_fl:
                showMenuPopWindow(v);
                break;
        }
    }


    /**
     * 显示PopupWindow
     *
     * @param v 父布局
     */
    public void showMenuPopWindow(View v) {
        popupWindow = new PopupWindow(getActivity());
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_menu_popupwindow, null);
        popupWindow.setContentView(view);
        ListView listView = (ListView) view.findViewById(R.id.fragment_menu_lv);
        int checkedPosition = listView.getCheckedItemPosition();
        MenuAdapter menuAdapter = new MenuAdapter(popTitles,checkedPosition);
        listView.setAdapter(menuAdapter);
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
        popupWindow.showAtLocation(v, LinearLayout.HORIZONTAL, 0, 0);
    }

    /**
     * PopupWindow的适配器
     */
    private class MenuAdapter extends BaseAdapter {

        private ArrayList<String> titles;
        private int checkedPosition;

        public MenuAdapter(ArrayList<String> titles, int checkedPosition) {
            this.titles = titles;
            this.checkedPosition = checkedPosition;
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
                    popClickListener.popClickListener(position);
                    popupWindow.dismiss();
                }
            });
            if (checkedPosition == position) {
                myViewholder.lineIv.setVisibility(convertView.VISIBLE);
            } else {
                myViewholder.lineIv.setVisibility(convertView.INVISIBLE);
            }
            return convertView;
        }

        private class MyViewholder {
            TextView titleTv;
            LinearLayout menuLl;
            ImageView lineIv;
        }
    }

    public interface PopClickListener {
        void popClickListener(int popMenuPosition);
    }

}
