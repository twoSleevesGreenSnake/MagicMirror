package com.qoo.magicmirror.base;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by dllo on 16/3/29.
 *
 * RecycleView的Adapter的基类
 */
public abstract class BaseAdapter extends RecyclerView.Adapter {

    // TODO 封装未完成

    /**
     * 绑定行布局的方法
     * @param resId 布局文件中的布局的资源ID
     * @param parent 父类布局
     * @return 布局文件中的布局
     */
    protected View createItemLayout(int resId, ViewGroup parent) {
        return LayoutInflater.from(parent.getContext()).inflate(resId, parent, false);
    }

    /**
     * 内部类
     */
    public abstract class BaseHolder extends RecyclerView.ViewHolder {
        private View view;
        public BaseHolder(View itemView) {
            super(itemView);
            view = itemView;
            initView();
        }

        /**
         * 内部类初始化组件的方法
         */
        protected abstract void initView();

        /**
         * 简化findViewById的方法
         * @param ResId 组件ID
         * @param <T> 需要转化的类型
         * @return 转化后的组件的类型
         */
        public <T extends View> T bindItemView(int ResId) {
            T t = (T) view.findViewById(ResId);
            return t;
        }
    }
}
