package com.veaer.gank.widget;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.view.View;

import com.veaer.gank.view.GankWebActivity;

/**
 * Created by Veaer on 15/10/26.
 */
public class VSpan extends ClickableSpan {
    private String title;
    private String url;

    public VSpan(String url, String title) {
        super();
        this.url = url;
        this.title = title;
    }

    @Override
    public void updateDrawState(@NonNull TextPaint ds) {
        super.updateDrawState(ds);
        ds.setUnderlineText(false);
    }

    @Override
    public void onClick(View widget) {
        Intent intent = new Intent(widget.getContext(), GankWebActivity.class);
        intent.putExtra("feed_url", url);
        intent.putExtra("feed_title", title);
        widget.getContext().startActivity(intent);
    }
}
