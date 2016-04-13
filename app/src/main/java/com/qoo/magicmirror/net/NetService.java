package com.qoo.magicmirror.net;

import android.app.Service;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by dllo on 16/4/11.
 */
public class NetService extends Service{
    private boolean hasNet;
    private Thread netThread;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i("netcome", "55555555555555");

        netThread = newThread();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("netcome","55555555555555");
        netThread.start();
        return super.onStartCommand(intent, flags, startId);



    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private boolean  testNet(){
        ConnectivityManager manager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        // 检查网络连接，如果无网络可用，就不需要进行连网操作等
        NetworkInfo info = manager.getActiveNetworkInfo();
        if (info == null || !info.isAvailable()) {
            hasNet = false;
            return hasNet;
        }
        hasNet = true;
        return hasNet;
    }
    private Thread newThread(){

        final Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){
                    testNet();
                    if (hasNet){

                        sendBroadcast(new Intent("com.qoo.magicmirror.NET_COMING"));
                        break;
                    }
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        return thread;
    }

}
