package com.qoo.magicmirror.base;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

/**
 * Created by dllo on 16/3/29.
 */
public abstract class BaseActivity extends AppCompatActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(setLayout());
        initView();
        initData();
    }

    public <T extends View> T bindView(int ResId){
        T t = (T) findViewById(ResId);
        return t;
    }
    protected abstract int setLayout();
    protected abstract void initView();
    protected abstract void initData();
}
