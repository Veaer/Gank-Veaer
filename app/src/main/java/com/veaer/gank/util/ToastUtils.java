package com.veaer.gank.util;

import android.content.Context;
import android.widget.Toast;

import com.veaer.gank.App;


/**
 * Created by Veaer on 15/8/14.
 */
public class ToastUtils {

    Context mContext;

    private ToastUtils() {
    }

    private static void show(Context context, int resId, int duration) {
        Toast.makeText(context, resId, duration).show();
    }

    private static void show(Context context, String message, int duration) {
        Toast.makeText(context, message, duration).show();
    }

    public static void showShort(int resId) {
        Toast.makeText(App.sContext, resId, Toast.LENGTH_SHORT).show();
    }

    public static void showShort(String message) {
        Toast.makeText(App.sContext, message, Toast.LENGTH_SHORT).show();
    }

    public static void showLong(int resId) {
        Toast.makeText(App.sContext, resId, Toast.LENGTH_LONG).show();
    }

    public static void showLong(String message) {
        Toast.makeText(App.sContext, message, Toast.LENGTH_LONG).show();
    }
}
