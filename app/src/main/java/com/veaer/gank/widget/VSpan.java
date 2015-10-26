package com.veaer.gank.widget;

import android.content.Intent;
import android.os.Parcel;
import android.support.annotation.NonNull;
import android.text.TextPaint;
import android.text.style.URLSpan;
import android.view.View;

import com.veaer.gank.util.ToastUtils;
import com.veaer.gank.view.GankWebActivity;

/**
 * Created by Veaer on 15/10/26.
 */
public class VSpan extends URLSpan {
    private String title;

    public VSpan(String url) {
        super(url);
    }

    public VSpan(String url, String title) {
        super(url);
        this.title = title;
    }

    public VSpan(Parcel src) {
        super(src);
    }

    @Override
    public void updateDrawState(@NonNull TextPaint ds) {
        super.updateDrawState(ds);
        ds.setUnderlineText(false);
    }

    @Override
    public void onClick(View widget) {
        Intent intent = new Intent(widget.getContext(), GankWebActivity.class);
        intent.putExtra("feed_url", getURL());
        intent.putExtra("feed_title", title);
        widget.getContext().startActivity(intent);
    }
}
