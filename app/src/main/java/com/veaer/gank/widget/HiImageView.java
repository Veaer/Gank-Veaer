package com.veaer.gank.widget;

import android.content.Context;
import android.net.Uri;
import android.util.AttributeSet;

import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.view.SimpleDraweeView;

/**
 * Created by Veaer on 15/8/17.
 */
public class HiImageView extends SimpleDraweeView {
    public HiImageView(Context context, GenericDraweeHierarchy hierarchy) {
        super(context, hierarchy);
    }

    public HiImageView(Context context) {
        super(context);
    }

    public HiImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public HiImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public String toString() {
        return "HTImageView";
    }

    public void loadImage(String url) {
        super.setImageURI(Uri.parse(url));
    }
}
