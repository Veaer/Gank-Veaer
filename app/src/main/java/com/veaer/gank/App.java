package com.veaer.gank;

import android.app.Application;
import android.content.Context;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.backends.okhttp.OkHttpImagePipelineConfigFactory;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import com.squareup.okhttp.OkHttpClient;
import com.veaer.gank.request.VolleyUtil;
import com.veaer.gank.util.LocalDisplay;

/**
 * Created by Veaer on 15/8/15.
 */
public class App extends Application {

    public static Context sContext;

    @Override public void onCreate() {
        super.onCreate();
        sContext = this;

        //快速debug
        //初始化Volley
        VolleyUtil.initialize(sContext);

        //初始化 Fresco
        OkHttpClient okHttpClient = new OkHttpClient();
        ImagePipelineConfig config = OkHttpImagePipelineConfigFactory.newBuilder(sContext, okHttpClient).build();
        Fresco.initialize(getApplicationContext(), config);

        //初始化测量工具
        LocalDisplay.init(sContext);
    }

}
