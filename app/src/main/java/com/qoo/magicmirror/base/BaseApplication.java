package com.qoo.magicmirror.base;

import android.app.Application;
import android.content.Context;

import com.zhy.autolayout.config.AutoLayoutConifg;

/**
 * Created by dllo on 16/3/29.
 */
public class BaseApplication extends Application {
    public static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        //默认使用的高度是设备的可用高度，也就是不包括状态栏和底部的操作栏的，拿设备的物理高度进行百分比化:
        AutoLayoutConifg.getInstance().useDeviceSize();
    }

    public static Context getContext() {
        if (context == null) {
        }
        return context;
    }
}
