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
 * Created by dllo on 16/3/30.
 */
public class ThemtaicRecycleViewAdapter extends RecyclerView.Adapter<ThemtaicRecycleViewAdapter.MyViewHolder> {

    private Context context;

    public ThemtaicRecycleViewAdapter(Context context) {

        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_fragment_themtaic, parent , false));
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
//        holder.textView.setText(datas.get(position).getTv());
    }


    @Override
    public int getItemCount() {
        return 0;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        public MyViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.tv);
        }
    }
}
