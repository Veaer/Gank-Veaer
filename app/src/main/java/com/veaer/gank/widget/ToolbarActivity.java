package com.veaer.gank.widget;

import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.ActionBar;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initToolBar();

    }

    public void initToolBar() {
        if(Build.VERSION.SDK_INT >= 21) {
            mAppBar.setElevation(10.6f);
        }
        mToolbar.setTitle(getToolBarTitle());
        mToolbar.setTitleTextColor(getResources().getColor(R.color.theme_text_color));
        mToolbar.setOnClickListener(view -> onToolbarClick());
        setSupportActionBar(mToolbar);
        if(canBack()) {
            ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    public String getToolBarTitle() {
        return "";
    }

    public boolean canBack() {
        return false;
    }

    public void onToolbarClick() {}

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
