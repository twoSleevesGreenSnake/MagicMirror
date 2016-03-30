package com.qoo.magicmirror.base;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

/**
 * Created by dllo on 16/3/29.
 */
public abstract class BaseActivity extends AppCompatActivity {
    protected static String token = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(setLayout());// 绑定布局
        initData();// 初始化数据，绑定组件
        initView();// 其他操作
    }

    public <T extends View> T bindView(int ResId) {
        T t = (T) findViewById(ResId);
        return t;
    }

    public static void setToken(String token) {
        BaseActivity.token = token;
    }

    protected abstract int setLayout();

    protected abstract void initData();

    protected abstract void initView();

}
