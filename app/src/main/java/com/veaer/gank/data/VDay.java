package com.veaer.gank.data;

import com.google.gson.annotations.SerializedName;
import com.veaer.gank.model.VFeed;

import java.util.List;

/**
 * Created by Veaer on 15/9/17.
 */
public class VDay extends VData {

    public List<String> category;
    public Results results;

    public class Results {
        @SerializedName("iOS") public List<VFeed> iosList;
        @SerializedName("福利") public List<VFeed> picList;
        @SerializedName("Android") public List<VFeed> androidList;
        @SerializedName("拓展资源") public List<VFeed> newsList;
        @SerializedName("瞎推荐") public List<VFeed> introsList;
        @SerializedName("休息视频") public List<VFeed> videoList;
        @SerializedName("前端") public List<VFeed> frontList;
        @SerializedName("App") public List<VFeed> appList;
    }
}
