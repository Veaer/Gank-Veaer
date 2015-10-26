package com.veaer.gank.view;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.veaer.gank.R;
import com.veaer.gank.data.VDay;
import com.veaer.gank.model.VDate;
import com.veaer.gank.model.VFeed;
import com.veaer.gank.util.StringStyleUtil;
import com.veaer.gank.widget.BaseActivity;
import com.veaer.gank.widget.BaseViewHolder;

import java.util.List;

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
    @Bind(R.id.rv_detail_list) RecyclerView detailListView;
    private VDay today;
    private VDate vDate;
    private String headUrl;

    @Override
    public int getContentViewID() {
        return R.layout.detail_activity_gank;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        vDate = (VDate)getIntent().getSerializableExtra("current_time");
        headUrl = getIntent().getStringExtra("title_bg");

        initToolBar();
        initData();
    }

    public void initToolBar() {
        mCollapsingToolbarLayout.setTitle(vDate.TIME);

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
        Subscription s = mLine.getDayData(vDate.YEAR, vDate.MONTH, vDate.DAY)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(dayData -> {
                    int fuliIndex = dayData.category.indexOf("福利");
                    dayData.category.remove(fuliIndex);
                    this.today = dayData;
                    initView();
                }, throwable -> loadError(throwable));
        addSubscription(s);
    }

    public void initView() {
        detailListView.setLayoutManager(new LinearLayoutManager(mContext));
        detailListView.setAdapter(new GankDetailAdapter());
    }


    public class GankDetailAdapter extends RecyclerView.Adapter<DetailViewHolder> {
        private LayoutInflater mLayoutInflater;

        public GankDetailAdapter() {
            mLayoutInflater = LayoutInflater.from(mContext);
        }

        @Override
        public int getItemCount() {
            return today.category.size();
        }

        @Override
        public void onBindViewHolder(DetailViewHolder holder, int position) {
            switch (today.category.get(position)) {
                case "iOS":
                    holder.bindViews(today.category.get(position), today.results.iosList);
                    break;
                case "App":
                    holder.bindViews(today.category.get(position), today.results.appList);
                    break;
                case "Android":
                    holder.bindViews(today.category.get(position), today.results.androidList);
                    break;
                case "拓展资源":
                    holder.bindViews(today.category.get(position), today.results.newsList);
                    break;
                case "休息视频":
                    holder.bindViews(today.category.get(position), today.results.videoList);
                    break;
                case "瞎推荐":
                    holder.bindViews(today.category.get(position), today.results.introsList);
                    break;
                case "前端":
                    holder.bindViews(today.category.get(position), today.results. frontList);
                    break;
            }
        }


        @Override
        public DetailViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new DetailViewHolder(mLayoutInflater.inflate(R.layout.detail_view_holder_detail, parent, false));
        }
    }

    public class DetailViewHolder extends BaseViewHolder {
        @Bind(R.id.category_title) TextView titleTv;
        @Bind(R.id.feed_content) TextView contentTv;

        public DetailViewHolder(View view) {
            super(view);
        }

        public void bindViews(String title, List<VFeed> feeds) {
            titleTv.setText(title);
            contentTv.setText(StringStyleUtil.generate(feeds, mContext.getResources().getColor(R.color.md_blue_600)));
            contentTv.setMovementMethod(LinkMovementMethod.getInstance());
        }
    }

}
