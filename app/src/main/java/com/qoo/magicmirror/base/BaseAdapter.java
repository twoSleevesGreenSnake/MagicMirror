package com.qoo.magicmirror.base;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by dllo on 16/3/29.
 */
public abstract class BaseAdapter extends RecyclerView.Adapter {

    protected View createItemLayout(int resId, ViewGroup parent) {
        return LayoutInflater.from(parent.getContext()).inflate(resId, parent, false);
    }

    public abstract class BaseHolder extends RecyclerView.ViewHolder {
        private View view;

        public BaseHolder(View itemView) {
            super(itemView);
            view = itemView;
            initView();
        }

        protected abstract void initView();


        public <T extends View> T bindItemView(int ResId) {
            T t = (T) view.findViewById(ResId);
            return t;
        }
    }
}
