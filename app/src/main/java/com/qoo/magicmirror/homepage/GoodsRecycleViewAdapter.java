package com.qoo.magicmirror.homepage;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.qoo.magicmirror.R;

import java.util.ArrayList;

/**
 * Created by dllo on 16/3/29.
 *
 * 商品展示的RecycleView的Adapter
 */
public class GoodsRecycleViewAdapter extends RecyclerView.Adapter<GoodsRecycleViewAdapter.MyViewHolder> {

//    private ArrayList<FragmentTitleBean.DataEntity> data;
    private Context context;

//    public GoodsRecycleViewAdapter(ArrayList<FragmentTitleBean.DataEntity> data, Context context) {
//        this.data = data;
//        this.context = context;
//    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_fragment_goods, parent, false));
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
//        holder.textView.setText(data.get(position).getCategory_name());
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView textView;

        public MyViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.textview);
        }
    }
}
