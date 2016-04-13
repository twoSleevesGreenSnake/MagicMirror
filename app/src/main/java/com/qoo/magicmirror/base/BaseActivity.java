package com.qoo.magicmirror.base;

import android.app.ActionBar;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Point;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.qoo.magicmirror.R;
import com.qoo.magicmirror.loginandregister.LoginActivity;
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
 * 所有activity的基类
 *
 */
public abstract class BaseActivity extends AutoLayoutActivity {

    protected static String token = "";
    protected static  final Map<Class<? extends BaseActivity>,BaseActivity> activities = new HashMap<>();
    private Point point;
    private NetReceivier receivier = new NetReceivier();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        activities.put(getClass(), this);
        setContentView(setLayout());// 绑定布局
        point = new Point();
        getWindowManager().getDefaultDisplay().getSize(point);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.qoo.magicmirror.NET_COMING");
        registerReceiver(receivier,intentFilter);
        initView();// 初始化数据，绑定组件
        initData();// 其他操作
    }

    /**
     * 获得屏幕尺寸的方法
     * @return .x 或者.y直接获取宽高
     */
    public Point getScreenSize() {
        return point;
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
        unregisterReceiver(receivier);
        super.onDestroy();
        deleteSelf();
    }

    /**
     * 检测是否登录的方法
     * @param tokenListener
     */
    protected void judgeToken(TokenListener tokenListener){
        if (token==null||token.equals("")){
            l("token的值");
            startActivity(new Intent(this, LoginActivity.class));
            tokenListener.tokenIsNothing();
        }
        else {
            tokenListener.logInSuccess();
        }
    }
    protected void netFailed(){
        t(getString(R.string.netFailedToast));
    }
    protected void t(String content){
        Toast.makeText(this, content, Toast.LENGTH_SHORT).show();
    }

    /**
     * 简单的log
     * @param content
     */
    protected void l(String content) {
        String className = getClass().getName();
        int index = className.lastIndexOf(".");
        String result = className.substring(index + 1, className.length());
        Log.d(result + getString(R.string.log_class) + new Throwable().getStackTrace()[1].getMethodName()
                + getString(R.string.log_method), content);
    }

    /**
     * 检测是否登录的接口
     */
    public interface TokenListener{
        void tokenIsNothing();
        void logInSuccess();
    }

    /**
     * 点击关闭页面的方法
     * @param v
     */
    protected void clickColse(View v){
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    class NetReceivier extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            onNetCome();

        }
    }
    protected void onNetCome(){

    }

}
