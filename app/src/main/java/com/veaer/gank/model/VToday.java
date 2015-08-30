package com.veaer.gank.model;

/**
 * Created by Veaer on 15/8/19.
 */
public class VToday {
    public String imagePreview;
    public String pageTitle;
    public boolean hasPreview = false;

    public VToday(String resp){
        this.imagePreview = getVideoPreviewImageUrl(resp);
        this.pageTitle = getPageTitle(resp);
    }

    public String getVideoPreviewImageUrl(String resp) {
        int s0 = resp.indexOf("休息视频</h1>"); if (s0 == -1) return null;
        int v0 = resp.indexOf("pluginspage", s0);
        if(v0 == -1) {
            int d = resp.indexOf("</div>", s0);
            int s1 = resp.indexOf("<img", s0); if(s1 == -1 || s1 > d) return null;
            int s2 = resp.indexOf("http:", s1); if (s2 == -1) return null;
            int e2 = resp.indexOf(".jpg", s2) + ".jpg".length(); if (e2 == -1) return null;
            this.hasPreview = true;
            return resp.substring(s2, e2);
        }
        int v1 = resp.indexOf("src", v0); if(v1 == -1) return null;
        int v2 = resp.indexOf("http:", v1); if(v2 == -1) return null;
        int v3 = resp.indexOf("\"", v2); if(v3 == -1) return null;
        return resp.substring(v2, v3);
    }

    public String getPageTitle(String resp) {
        int s0 = resp.indexOf("<h1>");
        int s1 = resp.indexOf("</h1>");
        return resp.substring(s0 + 4, s1);
    }
}
