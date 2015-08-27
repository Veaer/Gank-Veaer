package com.veaer.gank.view;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.android.volley.Response;
import com.veaer.gank.App;
import com.veaer.gank.R;
import com.veaer.gank.model.VFeed;
import com.veaer.gank.model.VPicture;
import com.veaer.gank.model.VToday;
import com.veaer.gank.model.VVideo;
import com.veaer.gank.request.VolleyRequestManager;
import com.veaer.gank.util.DateUtil;
import com.veaer.gank.util.URLProvider;
import com.veaer.gank.widget.BaseViewHolder;
import com.veaer.gank.widget.ToolbarActivity;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Veaer on 15/8/19.
 */
public class GankDetailActivity extends ToolbarActivity {
    String time;
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
        VolleyRequestManager.getInstance().getNoJson(URLProvider.VIDEOIMGURL + time, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                vToday = new VToday(response.optString("data"));
                dataList.add(vToday);
                VolleyRequestManager.getInstance().get(URLProvider.DAYURL + DateUtil.getToday(), null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        if(!response.optBoolean("error")) {
                            category = JSON.parseArray(response.optString("category"), String.class);
                            JSONObject results = response.optJSONObject("results");
                            for(String cate : category) {
                                dataList.add(cate);
                                if(cate.contains("视频")) {
                                    dataList.add(JSON.parseArray(results.optString(cate), VVideo.class));
                                } else if(cate.contains("福利")) {
                                    dataList.add(JSON.parseArray(results.optString(cate), VPicture.class));
                                } else {
                                    dataList.add(JSON.parseArray(results.optString(cate), VFeed.class));
                                }
                            }
                            gankDetailAdapter.notifyDataSetChanged();
                        }
                    }
                });
            }
        });
    }

    public class GankDetailAdapter extends RecyclerView.Adapter<BaseViewHolder> {
        final public int TODAY = 1;
        final int PICTURE = 2;
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
                case PICTURE:
                    return new PictureViewHolder(mLayoutInflater.inflate(R.layout.detail_view_holder_picture, parent, false));
                default:
                    return new TitleViewHolder(mLayoutInflater.inflate(R.layout.detail_view_holder_title, parent, false));
            }
        }

        @Override
        public int getItemViewType(int position) {
            String className = dataList.get(position).getClass().getName();
            switch (className) {
                case "VToday":
                    return TODAY;
                case "VPicture":
                    return PICTURE;
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

        public TitleViewHolder(View view) {
            super(view);
            getViewHolderViews(view);
            view.setOnClickListener(v->openVideo());
        }

        public void openVideo() {
        }

        public void getViewHolderViews(View view) {
            titleTv = (TextView)view.findViewById(R.id.detail_title);
//            titleTv.setTypeface(getFont());
        }

        @Override
        public void bindViews(Object object, Object... args) {
            if(object instanceof VToday) {
                titleTv.setText(vToday.pageTitle);
            }
        }
    }

    public class PictureViewHolder extends BaseViewHolder {
        TextView titleTv;

        public PictureViewHolder(View view) {
            super(view);
            getViewHolderViews(view);
        }

        public void getViewHolderViews(View view) {
            titleTv = (TextView)view.findViewById(R.id.detail_title);
//            titleTv.setTypeface(getFont());
        }

        @Override
        public void bindViews(Object object, Object... args) {
            if(object instanceof VToday) {
                titleTv.setText(vToday.pageTitle);
            }
        }
    }


}
