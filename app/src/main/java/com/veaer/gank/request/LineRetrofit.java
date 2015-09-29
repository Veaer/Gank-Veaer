/*
 * Copyright (C) 2015 Drakeet <drakeet.me@gmail.com>
 *
 * This file is part of Meizhi
 *
 * Meizhi is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Meizhi is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Meizhi.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.veaer.gank.request;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.okhttp.Cache;
import com.squareup.okhttp.OkHttpClient;
import com.veaer.gank.util.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import retrofit.RestAdapter;
import retrofit.client.OkClient;
import retrofit.converter.GsonConverter;

/**
 * Created by Veaer on 15/9/8.
 */
public class LineRetrofit {

    final Line service;
    private static Cache cache;

    final static Gson gson =
        new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").serializeNulls().create();

    LineRetrofit() {
        OkHttpClient client = new OkHttpClient();
        client.setReadTimeout(12, TimeUnit.SECONDS);
        int cacheSize = 10 * 1024 * 1024; // 10 MiB
        cache = new Cache(FileUtils.getHttpCacheDir(), cacheSize);
        client.setCache(cache);
        client.networkInterceptors().add(new CacheInterceptor());

        RestAdapter restAdapter = new RestAdapter.Builder().setClient(new OkClient(client))
            .setEndpoint("http://gank.avosapps.com/api/")
            .setConverter(new GsonConverter(gson))
            .build();
        service = restAdapter.create(Line.class);
    }

    public Line getService() {
        return service;
    }

    public static void clearCache() {
        try {
            cache.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (File item : FileUtils.getHttpCacheDir().listFiles()) {
            item.delete();
        }
    }
}
