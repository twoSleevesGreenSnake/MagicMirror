package com.qoo.magicmirror.wearatlas;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;
import android.widget.MediaController;
import android.widget.VideoView;

import com.qoo.magicmirror.R;
import com.qoo.magicmirror.base.BaseActivity;
import com.qoo.magicmirror.constants.NetConstants;
import com.qoo.magicmirror.homepage.GoodsListBean;
import com.qoo.magicmirror.net.NetHelper;

import java.util.ArrayList;

/**
 * Created by Giraffe on 16/4/5.
 */
public class WearAtlasActivity extends BaseActivity {
    private Context context;
    private ListView listView;
    private WearAtlasListViewAdapter wearAtlasListViewAdapter;
    private static ArrayList<GoodsListBean.DataEntity.ListEntity.WearVideoEntity> data;
    private VideoView vv;

    public static void setData (GoodsListBean.DataEntity.ListEntity data){
        WearAtlasActivity.data = (ArrayList<GoodsListBean.DataEntity.ListEntity.WearVideoEntity>) data.getWear_video();
    }
    @Override
    protected int setLayout() {
        return R.layout.activity_wearatlas;
    }

    @Override
    protected void initData() {
        data = new ArrayList<>();
        Log.d("1313", "da" + data.size());
        context = this;
        ArrayList<String> token = new ArrayList<>();
        token.add(getString(R.string.token));
        token.add(getString(R.string.device_type));
        token.add(getString(R.string.page));
        token.add(getString(R.string.last_time));
        token.add(getString(R.string.category_id));
        token.add(getString(R.string.version));
        ArrayList<String> value = new ArrayList<>();
        value.add("");
        value.add("1");
        value.add("");
        value.add("");
        value.add("");
        value.add("1.0.1");
        NetHelper netHelper = new NetHelper(this);
//        netHelper.getPostInfo(NetConstants.GOODS_TYPE,token,value,GoodsListBean.DataEntity.ListEntity.class, new NetHelper.NetListener<GoodsListBean.DataEntity.ListEntity>() {
//            @Override
//            public void onSuccess(GoodsListBean.DataEntity.ListEntity goodsListBean) {
//
//
//                data = (ArrayList<GoodsListBean.DataEntity.ListEntity.WearVideoEntity>) goodsListBean.getWear_video();
//
//            }
//
//            @Override
//            public void onFailure() {
//
//            }
//        });
        netHelper.getPostInfo(NetConstants.GOODS_TYPE, token, value, GoodsListBean.class, new NetHelper.NetListener<GoodsListBean>() {

            @Override
            public void onSuccess(GoodsListBean goodsListBean) {
//
                Log.d("11111", "onSuccess: " + goodsListBean.toString());
                data = (ArrayList<GoodsListBean.DataEntity.ListEntity.WearVideoEntity>) goodsListBean.getData().getList().get(0).getWear_video();
                MediaController mc = new MediaController(context);
                vv.setVideoURI(Uri.parse(goodsListBean.getData().getList().get(0).getWear_video().get(0).getData()));
                vv.setMediaController(mc);
                wearAtlasListViewAdapter = new WearAtlasListViewAdapter(data, context);
                listView.setAdapter(wearAtlasListViewAdapter);
//
            }

            @Override
            public void onFailure() {

            }
        });
    }

    @Override
    protected void initView() {
        listView = (ListView) findViewById(R.id.activity_wearatlas_listview);
        View view = LayoutInflater.from(context).inflate(R.layout.head_layout_of_wear_atlas,null);
        listView.addHeaderView(view);
        vv = (VideoView) view.findViewById(R.id.head_layout_of_atlas_vv);
        listView.setDivider(null);

    }
}
