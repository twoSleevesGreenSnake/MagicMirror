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
 *
 * 所有activity的基类
 */
public abstract class BaseActivity extends AutoLayoutActivity {

    /**
     * 登陆之后token的值
     */
    protected static String token = "";
    /**
     * activity的集合
     */
    protected static final Map<Class<? extends BaseActivity>, BaseActivity> activities = new HashMap<>();
    /**
     * Point类中有x，y
     */
    private Point point;
    /**
     * 动态广播
     */
    private NetReceiver receiver = new NetReceiver();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // 取消标题栏
        cancleFlags();
        super.onCreate(savedInstanceState);
        // 将当前的Activity放入到Activity的Map集合中
        activities.put(getClass(), this);
        // 初始化布局
        setContentView(setLayout());
        // 获取Point的值
        getPointValue();
        // 注册广播
        registerReciver();
        // 初始化组件
        initView();
        // 其他操作
        initData();
    }

    /**
     * 取消Activity的标题栏
     */
    private void cancleFlags() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    /**
     * 简化findViewById的方法
     * @param ResId 资源ID
     * @param <T>
     * @return
     */
    public <T extends View> T bindView(int ResId) {
        T t = (T) findViewById(ResId);
        return t;
    }

    /**
     * 初始化布局
     * @return 布局文件中的布局
     */
    protected abstract int setLayout();

    /**
     * 初始化组件
     */
    protected abstract void initView();

    /**
     * 其他操作
     */
    protected abstract void initData();

    /**
     * 获取point的值
     */
    private void getPointValue() {
        point = new Point();
        getWindowManager().getDefaultDisplay().getSize(point);
    }

    /**
     * 获得屏幕尺寸的方法
     * @return .x 或者.y直接获取宽高
     */
    public Point getScreenSize() {
        return point;
    }

    /**
     * 动态注册广播的方法
     */
    private void registerReciver() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(getString(R.string.com_qoo_magicmirror_NET_COMING));
        registerReceiver(receiver, intentFilter);
    }

    /**
     * 设置token的值
     * @param token token值
     */
    public static void setToken(String token) {
        BaseActivity.token = token;
    }

    /**
     * 获取activity的方法
     * @return 当前的activity的map集合
     */
    public Map<Class<? extends BaseActivity>, BaseActivity> getActivitys() {
        return activities;
    }

    /**
     * 获取activity
     * @param cls 类
     * @param <T> 一个继承BaseActivity不确定类型
     * @return
     */
    public <T extends BaseActivity> T getActivity(Class<T> cls) {
        T t = (T) getActivitys().get(cls);
        return t;
    }

    /**
     * 退出应用的方法
     * Iterator迭代器，所有的集合都继承自迭代器
     * Iterator的内部是一个Map集合
     * Map集合是所有继承自BaseActivity的Activity的集合
     */
    public void destoryAllActivitys() {
        Iterator<Map.Entry<Class<? extends BaseActivity>, BaseActivity>> it = activities.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<Class<? extends BaseActivity>, BaseActivity> entry = it.next();
            entry.getValue().finish();
        }
    }

    /**
     * 移除自己的方法
     */
    private void deleteSelf() {
        activities.remove(getClass());
    }

    /**
     * 系统的onDestroy方法
     * 取消广播注册
     * 在Activity销毁的时候将自己移除
     */
    @Override
    protected void onDestroy() {
        unregisterReceiver(receiver);
        super.onDestroy();
        deleteSelf();
    }

    /**
     * 判断token是否有值
     * 检测是否登录的方法
     * 有token直接继续执行
     * 没有token跳转到登陆界面
     * @param tokenListener 判断token回调的监听
     */
    protected void judgeToken(TokenListener tokenListener) {
        if (token == null || token.equals("")) {
            startActivity(new Intent(this, LoginActivity.class));
            tokenListener.tokenIsNothing();
        } else {
            tokenListener.logInSuccess();
        }
    }

    /**
     * 网络失败后Toast
     */
    protected void netFailed() {
        t(getString(R.string.netFailedToast));
    }

    /**
     * 简单封装的Toast的方法
     * @param content 上下文
     */
    protected void t(String content) {
        Toast.makeText(this, content, Toast.LENGTH_SHORT).show();
    }

    /**
     * 简单的封装的BaseActivity的log
     * @param content 上下文
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
    public interface TokenListener {
        void tokenIsNothing();
        void logInSuccess();
    }

    /**
     * 点击关闭页面的方法
     * @param v 当前的视图
     */
    protected void clickColse(View v) {
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    /**
     * 接收服务传来的广播
     * 动态监测网络
     */
    class NetReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            onNetCome();
        }
    }

    /**
     * 广播来了之后想完成其他功能时调用
     */
    protected void onNetCome() {

    }

}
