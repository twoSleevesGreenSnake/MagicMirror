package com.qoo.magicmirror.wearatlas;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.qoo.magicmirror.R;
import com.qoo.magicmirror.homepage.GoodsListBean;
import com.qoo.magicmirror.net.NetHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Giraffe on 16/4/6.
 */
public class WearAtlasListViewAdapter extends BaseAdapter {
    private List<GoodsListBean.DataEntity.ListEntity.WearVideoEntity> data;
    private Context context;

    public WearAtlasListViewAdapter(List<GoodsListBean.DataEntity.ListEntity.WearVideoEntity> data, Context context) {
        this.data = data;
        this.context = context;
    }

    @Override
    public int getCount() {
        return data != null && data.size() > 0 ? data.size() - 1 : 0;
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.item_wear_atlas_rv, null);
            viewHolder.imageView = (ImageView) convertView.findViewById(R.id.item_wear_atlas_rv_iv);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        GoodsListBean.DataEntity.ListEntity.WearVideoEntity wearVideoEntity = data.get(position + 1);
        if (wearVideoEntity != null) {
            NetHelper.newNetHelper(context).setImage(viewHolder.imageView, wearVideoEntity.getData());

        }
        return convertView;
    }


    public class ViewHolder {
        ImageView imageView;
    }
}
