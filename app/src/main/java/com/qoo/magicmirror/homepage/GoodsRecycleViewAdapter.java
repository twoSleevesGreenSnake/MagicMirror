package com.qoo.magicmirror.homepage;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.qoo.magicmirror.R;
import com.qoo.magicmirror.db.MainPageData;
import com.qoo.magicmirror.detail.BrowseGlassesActivity;
import com.qoo.magicmirror.net.NetHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dllo on 16/3/29.
 * <p/>
 * 商品展示的RecycleView的Adapter
 */
public class GoodsRecycleViewAdapter extends RecyclerView.Adapter<GoodsRecycleViewAdapter.MyViewHolder> {

    private ArrayList<GoodsListBean.DataEntity.ListEntity> data;
    private List<MainPageData> noNetData;
    private Context context;
    // MainActivity传递过来的ViewPager的位置

    public GoodsRecycleViewAdapter(ArrayList<GoodsListBean.DataEntity.ListEntity> data, Context context) {
        this.data = data;
        this.context = context;
    }
    public GoodsRecycleViewAdapter(List<MainPageData>data, Context context, int mainPosition) {
        noNetData = data;
        this.context = context;
        this.mainPosition = mainPosition;
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_fragment_goods, parent, false));
    }

    @Override
<<<<<<< HEAD
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.nameTv.setText(data.get(mainPosition).getGoods_name());
        holder.originTv.setText(data.get(mainPosition).getProduct_area());
        holder.pirceTv.setText(data.get(mainPosition).getGoods_price());
        holder.describeTv.setText(data.get(mainPosition).getBrand());
        new NetHelper(context).setImage(holder.goodPic, data.get(mainPosition).getGoods_img(),data.get(position));
=======
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.nameTv.setText(data.get(position).getGoods_name());
        holder.originTv.setText(data.get(position).getProduct_area());
        holder.pirceTv.setText(data.get(position).getGoods_price());
        holder.describeTv.setText(data.get(position).getBrand());
        new NetHelper(context).setImage(holder.goodPic, data.get(position).getGoods_img());
>>>>>>> feature/注册界面开始
        holder.itemLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, BrowseGlassesActivity.class);
                intent.putExtra(Value.putGoodsListBeanDataEntityListEntity, data.get(position));
                Log.i("data", data.get(position).getGoods_data().toString());
                BrowseGlassesActivity.setData(data.get(position));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView nameTv, originTv, pirceTv, describeTv;
        private ImageView goodPic;
        private LinearLayout itemLl;

        public MyViewHolder(View itemView) {
            super(itemView);
            goodPic = (ImageView) itemView.findViewById(R.id.item_fragment_goods_iv);
            nameTv = (TextView) itemView.findViewById(R.id.item_fragment_goods_name_tv);
            originTv = (TextView) itemView.findViewById(R.id.item_fragment_goods_origin_tv);
            pirceTv = (TextView) itemView.findViewById(R.id.item_fragment_goods_price_tv);
            describeTv = (TextView) itemView.findViewById(R.id.item_fragment_goods_describe_tv);
            itemLl = (LinearLayout) itemView.findViewById(R.id.item_fragment_goods_ll);
        }
    }
}
