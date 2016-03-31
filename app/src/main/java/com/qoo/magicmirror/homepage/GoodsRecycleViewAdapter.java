package com.qoo.magicmirror.homepage;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.qoo.magicmirror.R;
import com.qoo.magicmirror.net.NetHelper;

import java.util.ArrayList;

/**
 * Created by dllo on 16/3/29.
 *
 * 商品展示的RecycleView的Adapter
 */
public class GoodsRecycleViewAdapter extends RecyclerView.Adapter<GoodsRecycleViewAdapter.MyViewHolder> {

    private ArrayList<GoodsListBean.DataEntity.ListEntity> data;
    private Context context;

    public GoodsRecycleViewAdapter(ArrayList<GoodsListBean.DataEntity.ListEntity> data, Context context) {
        this.data = data;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_fragment_goods, parent, false));
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.nameTv.setText(data.get(position).getGoods_name());
        holder.originTv.setText(data.get(position).getProduct_area());
        holder.pirceTv.setText(data.get(position).getGoods_price());
        holder.describeTv.setText(data.get(position).getBrand());
        new NetHelper(context).setImage(holder.goodPic, data.get(position).getGoods_img());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView nameTv,originTv,pirceTv,describeTv;
        private ImageView goodPic;

        public MyViewHolder(View itemView) {
            super(itemView);
            goodPic = (ImageView) itemView.findViewById(R.id.item_fragment_goods_iv);
            nameTv = (TextView) itemView.findViewById(R.id.item_fragment_goods_name_tv);
            originTv = (TextView) itemView.findViewById(R.id.item_fragment_goods_origin_tv);
            pirceTv = (TextView) itemView.findViewById(R.id.item_fragment_goods_price_tv);
            describeTv = (TextView) itemView.findViewById(R.id.item_fragment_goods_describe_tv);
        }
    }
}
