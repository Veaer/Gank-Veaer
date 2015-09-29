package com.veaer.gank.data;

import com.google.gson.annotations.SerializedName;
import com.veaer.gank.model.VFeeds;

import java.util.List;

/**
 * Created by Veaer on 15/9/17.
 */
public class VDay extends VData {

    public List<String> category;
    public Results results;

    public class Results {
        @SerializedName("iOS") public List<VFeeds> iosList;
        @SerializedName("福利") public List<VFeeds> picList;
        @SerializedName("Android") public List<VFeeds> androidList;
        @SerializedName("瞎推荐") public List<VFeeds> newsList;
        @SerializedName("休息视频") public List<VFeeds> videoList;
    }
}
