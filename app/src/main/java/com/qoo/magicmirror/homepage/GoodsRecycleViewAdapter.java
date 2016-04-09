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
import com.qoo.magicmirror.constants.Value;
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
    private String type;
    private boolean hasNet;

    // MainActivity传递过来的ViewPager的位置

    public GoodsRecycleViewAdapter(ArrayList<GoodsListBean.DataEntity.ListEntity> data, Context context,String type) {
        this.type = type;
        this.data = data;
        this.context = context;
    }
    public GoodsRecycleViewAdapter(List<MainPageData>data,String type, Context context) {
        this.type = type;
        noNetData = data;
        this.context = context;

    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_fragment_goods, parent, false));
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        if (hasNet) {
            holder.nameTv.setText(data.get(position).getGoods_name());
            holder.originTv.setText(data.get(position).getProduct_area());
            holder.priceTv.setText(data.get(position).getGoods_price());
            holder.describeTv.setText(data.get(position).getBrand());
            new NetHelper(context).setImage(holder.goodPic, data.get(position).getGoods_img(), data.get(position), type);
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
        else {
            holder.nameTv.setText(noNetData.get(position).getName());
            holder.originTv.setText(noNetData.get(position).getArea());
            holder.priceTv.setText(noNetData.get(position).getPrice());
            holder.describeTv.setText(noNetData.get(position).getBrand());
            new NetHelper(context).setImage(holder.goodPic, noNetData.get(position).getPath());
        }
    }

    @Override
    public int getItemCount() {
        if (data==null||data.size()==0){
            hasNet = false;
            Log.i("size",noNetData.size()+"");
            return noNetData!=null&&noNetData.size()>0?noNetData.size():0;
        }
        else {
            hasNet = true;
            return data!=null&&data.size()>0?data.size():0;
        }
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView nameTv, originTv, priceTv, describeTv;
        private ImageView goodPic;
        private LinearLayout itemLl;

        public MyViewHolder(View itemView) {
            super(itemView);
            goodPic = (ImageView) itemView.findViewById(R.id.item_fragment_goods_iv);
            nameTv = (TextView) itemView.findViewById(R.id.item_fragment_goods_name_tv);
            originTv = (TextView) itemView.findViewById(R.id.item_fragment_goods_origin_tv);
            priceTv = (TextView) itemView.findViewById(R.id.item_fragment_goods_price_tv);
            describeTv = (TextView) itemView.findViewById(R.id.item_fragment_goods_describe_tv);
            itemLl = (LinearLayout) itemView.findViewById(R.id.item_fragment_goods_ll);
        }
    }
}
