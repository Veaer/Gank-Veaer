package com.veaer.gank.model;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Veaer on 15/9/17.
 */
public class VFeeds implements Serializable {
    public String who;
    public Date publishedAt;
    public String desc;
    public String url;
    public String type;
}
