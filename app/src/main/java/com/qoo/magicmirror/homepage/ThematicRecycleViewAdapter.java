package com.qoo.magicmirror.homepage;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.qoo.magicmirror.R;
import com.qoo.magicmirror.detail.SpecialTopicDetailActivity;
import com.qoo.magicmirror.net.NetHelper;

import java.util.ArrayList;

/**
 * Created by dllo on 16/4/1.
 *
 * 专题分享的RecycleView的适配器
 */
public class ThematicRecycleViewAdapter extends RecyclerView.Adapter<ThematicRecycleViewAdapter.MyViewHolder> {


    private ArrayList<ThematicBean.DataEntity.ListEntity> datas;
    private Context context;

    public ThematicRecycleViewAdapter(ArrayList<ThematicBean.DataEntity.ListEntity> datas, Context context) {
        this.datas = datas;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_fragment_themtaic, parent , false));
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.nameTv.setText(datas.get(position).getStory_title());
        new NetHelper(context).setImage(holder.img, datas.get(position).getStory_img());
        holder.itemRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, SpecialTopicDetailActivity.class);
                intent.putExtra(Value.putStoryId, datas.get(position).getStory_id());
                context.startActivity(intent);
            }
        });
    }


    @Override
    public int getItemCount() {
        return datas.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView nameTv;
        ImageView img;
        RelativeLayout itemRl;
        public MyViewHolder(View itemView) {
            super(itemView);
            nameTv = (TextView) itemView.findViewById(R.id.item_fragment_thematic_name_tv);
            img = (ImageView) itemView.findViewById(R.id.item_fragment_thematic_iv);
            itemRl = (RelativeLayout) itemView.findViewById(R.id.item_fragment_thematic_rl);
        }
    }
}
