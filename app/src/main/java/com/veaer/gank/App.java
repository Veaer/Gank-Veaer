package com.veaer.gank;

import android.app.Application;
import android.content.Context;

import com.veaer.gank.util.LocalDisplay;

/**
 * Created by Veaer on 15/8/15.
 */
public class App extends Application {

    public static Context sContext;

    @Override public void onCreate() {
        super.onCreate();
        sContext = this;

        //初始化测量工具
        LocalDisplay.init(sContext);
    }

}
