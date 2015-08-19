package com.veaer.gank.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;

import com.veaer.gank.R;

/**
 * Created by Veaer on 15/8/16.
 */
public class HiSwipeRefreshLayout extends SwipeRefreshLayout {

    private CanChildScrollUpCallback mCanChildScrollUpCallback;
    private Drawable mForegroundDrawable;

    public HiSwipeRefreshLayout(Context context) {
        this(context, null);
    }

    public HiSwipeRefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        final TypedArray a =
                context.obtainStyledAttributes(attrs, R.styleable.HiSwipeRefreshLayout, 0, 0);

        mForegroundDrawable = a.getDrawable(R.styleable.HiSwipeRefreshLayout_foreground);
        if (mForegroundDrawable != null) {
            mForegroundDrawable.setCallback(this);
            setWillNotDraw(false);
        }
        a.recycle();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (mForegroundDrawable != null) {
            mForegroundDrawable.setBounds(0, 0, w, h);
        }
    }

    public void setCanChildScrollUpCallback(CanChildScrollUpCallback canChildScrollUpCallback) {
        mCanChildScrollUpCallback = canChildScrollUpCallback;
    }

    public interface CanChildScrollUpCallback {
        boolean canSwipeRefreshChildScrollUp();
    }

    @Override
    public boolean canChildScrollUp() {
        if (mCanChildScrollUpCallback != null) {
            return mCanChildScrollUpCallback.canSwipeRefreshChildScrollUp();
        }
        return super.canChildScrollUp();
    }
}
