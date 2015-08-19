package com.veaer.gank.request;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by Veaer on 15/8/15.
 */
public class VolleyUtil {

    private static RequestQueue mRequestQueue;

    public static void initialize(Context context) {
        if(mRequestQueue == null) {
            synchronized (VolleyUtil.class){
                if(mRequestQueue == null) {
                    mRequestQueue = Volley.newRequestQueue(context);
                }
            }
        }
        mRequestQueue.start();
    }

    public static RequestQueue getRequestQueue() {
        if(mRequestQueue == null)
            throw new RuntimeException("请先初始化mRequestQueue");
        return mRequestQueue;
    }
}
