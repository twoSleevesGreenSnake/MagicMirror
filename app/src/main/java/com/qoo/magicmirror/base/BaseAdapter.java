package com.qoo.magicmirror.base;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by dllo on 16/3/29.
 */
public abstract class BaseAdapter extends RecyclerView.Adapter {
    protected static final int HEAD_MODE = 1;
    protected static final int CONTENT_MODE = 2;

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return HEAD_MODE;
        } else {
            return CONTENT_MODE;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }
    public  class BaseHeadHolder extends RecyclerView.ViewHolder{

        public BaseHeadHolder(View itemView) {
            super(itemView);
        }
    }

}
