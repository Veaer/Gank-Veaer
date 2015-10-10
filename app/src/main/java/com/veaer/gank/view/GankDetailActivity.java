package com.veaer.gank.view;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableStringBuilder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.veaer.gank.App;
import com.veaer.gank.R;
import com.veaer.gank.model.VFeed;
import com.veaer.gank.model.VToday;
import com.veaer.gank.model.VVideo;
import com.veaer.gank.util.StringStyleUtil;
import com.veaer.gank.widget.BaseViewHolder;
import com.veaer.gank.widget.HiImageView;
import com.veaer.gank.widget.ToolbarActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Veaer on 15/8/19.
 */
public class GankDetailActivity extends ToolbarActivity {
    String time;
    String titleBgUrl;
    RecyclerView detailListRv;
    GankDetailAdapter gankDetailAdapter = new GankDetailAdapter();
    List<String> category = new ArrayList<>();
    List<Object> dataList = new ArrayList<>();
    VToday vToday;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_activity_gank);
        time = getIntent().getStringExtra("current_time");
        titleBgUrl = getIntent().getStringExtra("title_bg");
        initToolBar();
        getViews();
        initView();
    }

    public void getViews() {
        detailListRv = $(R.id.rv_detail_list);
        detailListRv.setAdapter(gankDetailAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        detailListRv.setLayoutManager(linearLayoutManager);
        detailListRv.setItemAnimator(new DefaultItemAnimator());
    }

    public void initToolBar() {
        mToolbar = $(R.id.toolbar);
        mAppBar = $(R.id.app_bar_layout);

        if(Build.VERSION.SDK_INT >= 21) {
            mAppBar.setElevation(10.6f);
        }
        mToolbar.setTitle("CONTENTS");
        mToolbar.setTitleTextColor(getResources().getColor(R.color.theme_text_color));
        mToolbar.setOnClickListener(view -> onToolbarClick());
        setSupportActionBar(mToolbar);
    }

    public void onToolbarClick() {
        detailListRv.smoothScrollToPosition(0);
    }


    public void initView() {
//        VolleyRequestManager.getInstance().getNoJson(URLProvider.VIDEOIMGURL + time, new Response.Listener<JSONObject>() {
//            @Override
//            public void onResponse(JSONObject response) {
//                vToday = new VToday(response.optString("data"));
//                dataList.add(vToday);
//                VolleyRequestManager.getInstance().get(URLProvider.DAYURL + time, null, new Response.Listener<JSONObject>() {
//                    @Override
//                    public void onResponse(JSONObject response) {
//                        if(!response.optBoolean("error")) {
//                            category = JSON.parseArray(response.optString("category"), String.class);
//                            JSONObject results = response.optJSONObject("results");
//                            List<Object> videoList = new ArrayList<>();
//                            List<Object> feedList = new ArrayList<>();
//                            for(String cate : category) {
//                                if(cate.contains("视频")) {
//                                    videoList.add(cate);
//                                    videoList.addAll(JSON.parseArray(results.optString(cate), VVideo.class));
//                                } else if(!cate.contains("福利")) {
//                                    feedList.add(cate);
//                                    feedList.addAll(JSON.parseArray(results.optString(cate), VFeed.class));
//                                }
//                            }
//                            dataList.addAll(feedList);
//                            dataList.addAll(videoList);
//                            gankDetailAdapter.notifyDataSetChanged();
//                        }
//                    }
//                });
//            }
//        });
    }

    public class GankDetailAdapter extends RecyclerView.Adapter<BaseViewHolder> {
        final int TODAY = 1;
        final int LABEL = 2;
        final int FEED = 3;
        final int VIDEO = 4;
        private LayoutInflater mLayoutInflater;

        public GankDetailAdapter() {
            mLayoutInflater = LayoutInflater.from(App.sContext);
        }

        @Override
        public int getItemCount() {
            return dataList.size();
        }

        @Override
        public void onBindViewHolder(BaseViewHolder holder, int position) {
            holder.bindViews(dataList.get(position));
        }

        @Override
        public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            switch (viewType) {
                case TODAY:
                    return new TitleViewHolder(mLayoutInflater.inflate(R.layout.detail_view_holder_title, parent, false));
                case LABEL:
                    return new LabelViewHolder(mLayoutInflater.inflate(R.layout.detail_view_holder_label, parent, false));
                case FEED:
                    return new FeedViewHolder(mLayoutInflater.inflate(R.layout.detail_view_holder_feed, parent, false));
                case VIDEO:
                    return new VideoViewHolder(mLayoutInflater.inflate(R.layout.detail_view_holder_video, parent, false));
                default:
                    return new TitleViewHolder(mLayoutInflater.inflate(R.layout.detail_view_holder_title, parent, false));
            }
        }

        @Override
        public int getItemViewType(int position) {
            String longName = dataList.get(position).getClass().getName();
            String className = longName.substring(longName.lastIndexOf(".") + 1, longName.length());
            switch (className) {
                case "VToday":
                    return TODAY;
                case "String":
                    return LABEL;
                case "VFeed":
                    return FEED;
                case "VVideo":
                    return VIDEO;
                default:
                    return TODAY;
            }
        }
    }

    public class TitleViewHolder extends BaseViewHolder {
        TextView titleTv;
        HiImageView titleBg;

        public TitleViewHolder(View view) {
            super(view);
        }

        @Override
        public void getViewHolderViews() {
            titleTv = $(R.id.detail_title);
//            titleTv.setTypeface(getFont());
            titleBg = $(R.id.title_bg);
            titleBg.setAspectRatio(0.7f);
        }

        @Override
        public void bindViews(Object object, Object... args) {
            titleTv.setText(vToday.pageTitle);
            titleBg.loadImage(titleBgUrl);
        }
    }

    public class LabelViewHolder extends BaseViewHolder {
        TextView labelTv;

        public LabelViewHolder(View view) {
            super(view);
        }

        @Override
        public void getViewHolderViews() {
            labelTv = $(R.id.detail_label);
        }

        @Override
        public void bindViews(Object object, Object... args) {
            labelTv.setText(object.toString());
        }
    }

    public class FeedViewHolder extends BaseViewHolder {
        TextView labelTv;
        VFeed vFeed;

        public FeedViewHolder(View view) {
            super(view);
            view.setOnClickListener(v -> openWeb());
        }

        public void openWeb() {
            Intent videoIntent = new Intent(GankDetailActivity.this, GankWebActivity.class);
            videoIntent.putExtra("feed_url", vFeed.url);
            videoIntent.putExtra("feed_title", vFeed.desc);
            startActivity(videoIntent);
            overridePendingTransition(R.anim.slide_right_in, R.anim.slide_right_out);
        }

        @Override
        public void getViewHolderViews() {
            labelTv = $(R.id.detail_label);
        }

        @Override
        public void bindViews(Object object, Object... args) {
            vFeed = (VFeed)object;
            SpannableStringBuilder builder = new SpannableStringBuilder(vFeed.desc).append(
                    StringStyleUtil.format(getApplicationContext(), " (via. " + vFeed.who + ")",
                            R.style.ViaTextAppearance));
            labelTv.setText(builder);

        }
    }

    public class VideoViewHolder extends BaseViewHolder {
        HiImageView videoCover;
        TextView labelTv;
        String url;
        String title;

        public VideoViewHolder(View view) {
            super(view);
            view.setOnClickListener(v -> openVideo());
        }

        public void openVideo() {
            Intent videoIntent = new Intent(GankDetailActivity.this, GankVideoActivity.class);
            videoIntent.putExtra("video_url", url);
            videoIntent.putExtra("video_title", title);
            startActivity(videoIntent);
            overridePendingTransition(R.anim.slide_right_in, R.anim.slide_right_out);
        }

        @Override
        public void getViewHolderViews() {
            videoCover = $(R.id.detail_video_cover);
            labelTv = $(R.id.detail_label);
        }

        @Override
        public void bindViews(Object object, Object... args) {
            VVideo vVideo = (VVideo)object;
            SpannableStringBuilder builder = new SpannableStringBuilder(vVideo.desc).append(
                    StringStyleUtil.format(getApplicationContext(), " (via. " + vVideo.who + ")",
                            R.style.ViaTextAppearance));
            labelTv.setText(builder);
            if(vToday.hasPreview) {
                url = vVideo.url;
                videoCover.loadImage(vToday.imagePreview);
            } else {
                url = vVideo.url;
//                url = vToday.imagePreview;
                videoCover.setVisibility(View.GONE);
            }
            title = vVideo.desc;
        }
    }



}
