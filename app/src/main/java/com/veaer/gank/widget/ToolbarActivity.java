package com.veaer.gank.widget;

import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.animation.DecelerateInterpolator;

import com.veaer.gank.R;

import butterknife.Bind;


public abstract class ToolbarActivity extends BaseActivity {

    @Bind(R.id.app_bar_layout)
    protected AppBarLayout mAppBar;
    @Bind(R.id.toolbar)
    protected Toolbar mToolbar;
    protected boolean mIsHidden = false;

    protected void setAppBarAlpha(float alpha) {
        mAppBar.setAlpha(alpha);
    }

    protected void hideOrShowToolbar() {
        mAppBar.animate()
            .translationY(mIsHidden ? 0 : -mAppBar.getHeight())
            .setInterpolator(new DecelerateInterpolator(2))
            .start();

        mIsHidden = !mIsHidden;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        else {
            return super.onOptionsItemSelected(item);
        }
    }
}
