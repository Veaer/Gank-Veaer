package com.veaer.gank.view;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.veaer.gank.R;
import com.veaer.gank.widget.BaseActivity;

import butterknife.Bind;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by Veaer on 15/8/19.
 */
public class GankDetailActivity extends BaseActivity {

    @Bind(R.id.toolbar) Toolbar mToolbar;
    @Bind(R.id.collapsing_toolbar) CollapsingToolbarLayout mCollapsingToolbarLayout;
    @Bind(R.id.head_image) ImageView headImage;
    private String currentTime;
    private String headUrl;

    @Override
    public int getContentViewID() {
        return R.layout.detail_activity_gank;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        currentTime = getIntent().getStringExtra("current_time");
        headUrl = getIntent().getStringExtra("title_bg");

        initToolBar();
        initData();
    }

    public void initToolBar() {
        mCollapsingToolbarLayout.setTitle(getString(R.string.app_name));

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbar.setNavigationOnClickListener(v -> GankDetailActivity.this.onBackPressed());

        Glide.with(mActivity)
                .load(headUrl)
                .centerCrop()
                .placeholder(R.mipmap.gank_launcher)
                .into(headImage);
    }

    public void initData() {
        Subscription s = mLine.getDayData(currentTime)
                .map(all -> all.results)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(all -> {

                }, throwable -> loadError(throwable));
        addSubscription(s);
    }

}
