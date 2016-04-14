package com.qoo.magicmirror.base;

import android.app.Application;
import android.content.Context;
import android.os.Environment;

import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.zhy.autolayout.config.AutoLayoutConifg;

import java.io.File;

/**
 * Created by dllo on 16/3/29.
 */
public class BaseApplication extends Application {

    public static Context context;
    private ImageLoader imageLoader;
    private ImageLoaderConfiguration configuration;
    private String diskPath;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;

    }

    public static Context getContext() {
        if (context == null) {
        }
        return context;
    }
}
