package com.veaer.gank.request;


import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;


public class CacheInterceptor implements Interceptor {
    public static final String TAG = CacheInterceptor.class.getSimpleName();
    private static final String CACHE_CONTROL = "Cache-Control";

    @Override
    public Response intercept(Chain chain) throws IOException {

        Request request = chain.request();
        Response response = chain.proceed(request);
        return response.newBuilder()
                .header(CACHE_CONTROL, "public, max-age=" + 60 * 60 * 24) // 缓存一天
                .build();
    }
}
