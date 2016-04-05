package com.qoo.magicmirror.base;

import android.app.ActionBar;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.qoo.magicmirror.net.NetHelper;
import com.zhy.autolayout.AutoLayoutActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by dllo on 16/3/29.
 */

/**
 * 所有acitivity的基类
 *
 */
public abstract class BaseActivity extends AutoLayoutActivity {

    protected static String token = "";
    protected Map<Class<? extends BaseActivity>,BaseActivity> activities;
    private ProgressBar progressBar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);

//        get
        activities = new HashMap<>();
        activities.put(getClass(),this);
        setContentView(setLayout());// 绑定布局
        initView();// 初始化数据，绑定组件
        progressBar = new ProgressBar(this);
//        progressBar.setText("sdfsdfsdfsdfsdfsdfdsfsdfsdfsdfsdfsdfsdfdsfsdfsdfsdfsdfsdfsdfdsfsdfsdfsdfsdfsdfsdfdsfsdfsdfsdfsdfsdfsdfdsfsdfsdfsdfsdfsdfsdfdsfsdfsdfsdfsdfsdfsdfdsf");
        int screenWidth = getWindowManager().getDefaultDisplay().getWidth();
        int screenHeight = getWindowManager().getDefaultDisplay().getHeight();
//        getWindow().addContentView(textView, new ViewGroup.LayoutParams(screenWidth, screenHeight));
        initData();// 其他操作
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
//

    public Map<Class<? extends BaseActivity>, BaseActivity> getActivitys() {
        return activities;
    }

    /**
     * 退出应用的方法
     */
    public void destoryAllActivitys(){
        Iterator<Map.Entry<Class<? extends BaseActivity>,BaseActivity>> it = activities.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<Class<? extends BaseActivity>,BaseActivity> entry = it.next();
            entry.getValue().finish();
        }
    }
    private void deleteSelf(){
        activities.remove(getClass());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        deleteSelf();
    }
    protected void removeWaitView(){
//        textView.setVisibility(View.GONE);
    }
}
