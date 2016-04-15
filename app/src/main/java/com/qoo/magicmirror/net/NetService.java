package com.qoo.magicmirror.net;

import android.app.Service;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.qoo.magicmirror.R;

/**
 * Created by dllo on 16/4/11.
 *
 * 动态监测的Service
 */
public class NetService extends Service{
    /**
     * 是否有网
     */
    private boolean hasNet;
    private Thread netThread;

    @Override
    public void onCreate() {
        super.onCreate();
        netThread = newThread();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
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

                        sendBroadcast(new Intent(getString(R.string.com_qoo_magicmirror_net_coming)));
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
