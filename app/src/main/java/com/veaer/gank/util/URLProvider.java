package com.veaer.gank.util;

import java.net.URLEncoder;

/**
 * Created by Veaer on 15/8/17.
 */
public class URLProvider {
    public static final String server = "http://gank.avosapps.com/api/";
    public static final String ANDROIDURL = server + "data/Android/";
    public static final String IOSURL = server + "data/iOS/";
    public static final String PICIURL = server + "data/" + URLEncoder.encode("福利") + "/";
    public static final String VIDEOURL = server + "data/" + URLEncoder.encode("休息视频") + "/";
    public static final String EXPANDURL = server + "data/" + URLEncoder.encode("拓展资源") + "/";
    public static final String HTMLURL = server + "data/" + URLEncoder.encode("前端") +"/";
    public static final String ALLLURL = server + "data/all/";

    public static final String DAYURL = server + "day/";
}
