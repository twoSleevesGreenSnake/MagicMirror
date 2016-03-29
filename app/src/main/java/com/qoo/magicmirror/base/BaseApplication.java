package com.qoo.magicmirror.base;

import android.app.Application;
import android.content.Context;

/**
 * Created by dllo on 16/3/29.
 */
public class BaseApplication extends Application {
    public static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
    }

    public static Context getContext() {
        if (context==null){
            
        }
        return context;
    }
}
