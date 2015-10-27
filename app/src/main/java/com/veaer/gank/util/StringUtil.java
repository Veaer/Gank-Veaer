package com.veaer.gank.util;

import android.graphics.Typeface;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;

import com.veaer.gank.model.VFeed;
import com.veaer.gank.widget.VSpan;

import java.util.List;

/**
 * Created by Veaer on 15/8/30.
 */
public class StringUtil {
    public static String generateGankLink(String time) {
        return "http://gank.io/" + time;
    }

    public static SpannableStringBuilder generateFeed(List<VFeed> feedList, int color) {
        SpannableStringBuilder builder = new SpannableStringBuilder();
        int start;
        for (VFeed feed : feedList) {
            start = builder.length();
            builder.append(" • ");
            builder.setSpan(new StyleSpan(Typeface.BOLD), start, builder.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            start = builder.length();
            builder.append(feed.desc);
            builder.setSpan(new VSpan(feed.url, feed.desc), start, builder.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            builder.setSpan(new ForegroundColorSpan(color), start, builder.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            builder.append("（");
            builder.append(feed.who);
            builder.append("）\n");
        }
        return builder;
    }
}
