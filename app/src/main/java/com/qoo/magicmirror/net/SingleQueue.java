package com.qoo.magicmirror.net;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by dllo on 16/3/30.
 *
 * 单例SingleQueue
 */
public class SingleQueue {

    private static SingleQueue singleQueue;
    private RequestQueue requestQueue;


    private SingleQueue(Context context) {
        super();
        requestQueue = Volley.newRequestQueue(context);
    }

    public static SingleQueue newSingleQueue(Context context) {
        if (singleQueue == null) {
            synchronized (SingleQueue.class) {
                if (singleQueue == null) {
                    singleQueue = new SingleQueue(context);

                }
            }
        }
        return singleQueue;
    }

    public RequestQueue getRequestQueue() {
        return requestQueue;
    }
}

