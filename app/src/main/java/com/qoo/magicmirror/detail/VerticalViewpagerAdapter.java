package com.qoo.magicmirror.detail;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.qoo.magicmirror.R;
import com.qoo.magicmirror.base.BaseActivity;

import java.util.ArrayList;

/**
 * Created by Giraffe on 16/3/29.
 */
public class VerticalViewpagerAdapter extends PagerAdapter {
    private ArrayList<View> views;
    private Context context;

    public VerticalViewpagerAdapter(ArrayList<View> views) {
        this.views = views;
    }

    @Override
    public int getCount() {
        return views.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return (view == object);
    }

    //实例化页卡
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(views.get(position));
//        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.activity_specialtopic_detail_viewpager, null);
//        view.setOnLongClickListener(new View.OnLongClickListener() {
//            @Override
//            public boolean onLongClick(View v) {
//                v.setVisibility(View.INVISIBLE);
//
//                return false;
//            }
//        });

        return views.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(views.get(position));

    }


}
