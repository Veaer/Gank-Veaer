package com.veaer.gank.model;

/**
 * Created by Veaer on 15/8/19.
 */
public class VToday {
    public String imagePreview;
    public String pageTitle;

    public VToday(String resp){
        this.imagePreview = getVideoPreviewImageUrl(resp);
        this.pageTitle = getPageTitle(resp);
    }

    public String getVideoPreviewImageUrl(String resp) {
        int s0 = resp.indexOf("<h1>休息视频</h1>"); if (s0 == -1) return null;
        int s1 = resp.indexOf("<img", s0); if (s1 == -1) return null;
        int s2 = resp.indexOf("http:", s1); if (s2 == -1) return null;
        int e2 = resp.indexOf(".jpg", s2) + ".jpg".length(); if (e2 == -1) return null;
        return resp.substring(s2, e2);
    }

    public String getPageTitle(String resp) {
        int s0 = resp.indexOf("<h1>");
        int s1 = resp.indexOf("</h1>");
        return resp.substring(s0 + 4, s1);
    }
}
