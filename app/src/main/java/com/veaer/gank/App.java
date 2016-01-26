package com.veaer.gank;

import android.app.Application;
import android.content.Context;

/**
 * Created by Veaer on 15/8/15.
 */
public class App extends Application {

    public static Context sContext;

    @Override public void onCreate() {
        super.onCreate();
        sContext = this;
    }

}
