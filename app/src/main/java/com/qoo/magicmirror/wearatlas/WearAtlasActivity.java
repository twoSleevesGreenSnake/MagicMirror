package com.qoo.magicmirror.wearatlas;

import android.widget.ListView;

import com.qoo.magicmirror.R;
import com.qoo.magicmirror.base.BaseActivity;

/**
 * Created by Giraffe on 16/4/5.
 */
public class WearAtlasActivity extends BaseActivity {
    private ListView listView;
    private WearAtlasListViewAdapter wearAtlasListViewAdapter;
    private
    @Override
    protected int setLayout() {
        return R.layout.activity_wearatlas;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        listView = (ListView) findViewById(R.id.activity_wearatlas_listview);
        wearAtlasListViewAdapter = new WearAtlasListViewAdapter();
    }
}
