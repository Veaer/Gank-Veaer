package com.veaer.gank.view;

import android.content.Intent;
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
import com.veaer.gank.model.VPicture;
import com.veaer.gank.model.VVideo;
import com.veaer.gank.model.VDate;
import com.veaer.gank.request.VolleyRequestManager;
import com.veaer.gank.util.DateUtil;
import com.veaer.gank.util.ToastUtils;
import com.veaer.gank.util.URLProvider;
import com.veaer.gank.widget.HiImageView;
import com.veaer.gank.widget.HiSwipeRefreshLayout;
import com.veaer.gank.widget.ToolbarActivity;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Veaer on 15/8/16.
 */
public class GankListActivity extends ToolbarActivity {
    int nextPage = 1;
    RecyclerView gankListRv;
    HiSwipeRefreshLayout refreshLayout;
    boolean canAdd = true;
    GankListAdapter gankListAdapter = new GankListAdapter();
    List<VPicture> pictureList = new ArrayList<>();
    List<VVideo> videoList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_activity_gank_list);
        initToolBar();
        getViews();
        initView(1, true);
    }

    public void getViews() {
        gankListRv = $(R.id.rv_gank_list);
        gankListRv.setAdapter(gankListAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        gankListRv.setLayoutManager(linearLayoutManager);
        gankListRv.addOnScrollListener(getScrollToBottomListener(linearLayoutManager));
        gankListRv.setItemAnimator(new DefaultItemAnimator());
        refreshLayout = $(R.id.swipe_refresh_layout);
        refreshLayout.setColorSchemeResources(R.color.refresh_progress_3,
                R.color.refresh_progress_2, R.color.refresh_progress_1);
        refreshLayout.setOnRefreshListener(() -> initView(1, true));
    }

    public void initToolBar() {
        mToolbar = $(R.id.toolbar);
        mAppBar = $(R.id.app_bar_layout);

        if(Build.VERSION.SDK_INT >= 21) {
            mAppBar.setElevation(10.6f);
        }
        mToolbar.setTitle("HISTORY");
        mToolbar.setTitleTextColor(getResources().getColor(R.color.theme_text_color));
        mToolbar.setOnClickListener(view -> onToolbarClick());
        setSupportActionBar(mToolbar);
    }

    public void initView(int page, boolean refresh) {
        if(!canAdd) {
            ToastUtils.showShort("滑不动了哟");
            setRefreshing(false);
            return;
        }
        VolleyRequestManager.getInstance().get(!refresh, URLProvider.PICIURL + "10/" + page, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                List<VPicture> resultPicture = JSON.parseArray(response.optString("results"), VPicture.class);
                VolleyRequestManager.getInstance().get(URLProvider.VIDEOURL + "10/" + page, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        List<VVideo> resultVideo = JSON.parseArray(response.optString("results"), VVideo.class);

                        if(resultPicture.size() == 0) {
                            canAdd = false;
                            ToastUtils.showShort("滑不动了哟");
                        }
                        if (refresh) {
                            pictureList = resultPicture;
                            videoList = resultVideo;
                        } else {
                            pictureList.addAll(resultPicture);
                            videoList.addAll(resultVideo);
                            nextPage++;
                        }
                        gankListAdapter.notifyDataSetChanged();
                        setRefreshing(false);
                    }
                });
            }
        });
    }

    public void onToolbarClick() {
        gankListRv.smoothScrollToPosition(0);
    }

    public void setRefreshing(boolean refreshing) {
        if (!refreshing) {
            // 防止刷新消失太快，让子弹飞一会儿
            refreshLayout.postDelayed(() -> refreshLayout.setRefreshing(false), 1000);
        } else {
            refreshLayout.setRefreshing(true);
        }
    }

    RecyclerView.OnScrollListener getScrollToBottomListener(final LinearLayoutManager linearLayoutManager) {
        return new RecyclerView.OnScrollListener() {
            @Override public void onScrolled(RecyclerView rv, int dx, int dy) {
                boolean isBottom = linearLayoutManager.findLastVisibleItemPosition() == (pictureList.size() - 1);
                if (!refreshLayout.isRefreshing() && isBottom) {
                    refreshLayout.setRefreshing(true);
                    nextPage += 1;
                    initView(nextPage, false);
                }
            }
        };
    }







    public class GankListAdapter extends RecyclerView.Adapter<GankItemViewHolder> {
        private LayoutInflater mLayoutInflater;

        public GankListAdapter() {
            mLayoutInflater = LayoutInflater.from(App.sContext);
        }

        @Override
        public int getItemCount() {
            return pictureList.size();
        }

        @Override
        public void onBindViewHolder(GankItemViewHolder holder, int position) {
            if(position < pictureList.size() && position < videoList.size()) holder.bindViews(pictureList.get(position), videoList.get(position));
        }

        @Override
        public GankItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new GankItemViewHolder(mLayoutInflater.inflate(R.layout.list_view_holder_list_item, parent, false));
        }
    }

    public class GankItemViewHolder extends RecyclerView.ViewHolder {
        VDate vDate;

        TextView descTv;
        TextView yearTv;
        TextView monthTv;
        TextView dayTv;
        HiImageView pictureIV;

        public GankItemViewHolder(View view) {
            super(view);
            getViewHolderViews(view);
            view.setOnClickListener(v -> toActivity());
        }

        public void toActivity() {
            Intent intent = new Intent(getApplicationContext(), GankDetailActivity.class);
            intent.putExtra("current_time", vDate.TIME);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_right_in, R.anim.slide_right_out);
        }

        public void getViewHolderViews(View view) {
            descTv = (TextView)view.findViewById(R.id.desc);
//            descTv.setTypeface(getFont());
            yearTv = (TextView)view.findViewById(R.id.year);
            monthTv = (TextView)view.findViewById(R.id.month);
            dayTv = (TextView)view.findViewById(R.id.day);
            pictureIV = (HiImageView)view.findViewById(R.id.picture);
        }

        public void bindViews(VPicture mPicture, VVideo mVideo) {
            vDate = DateUtil.publish2date(mPicture.publishedAt);
            yearTv.setText(vDate.YEAR);
            monthTv.setText(vDate.MONTH);
            dayTv.setText(vDate.DAY);
            descTv.setText(mVideo.desc);
            pictureIV.loadImage(mPicture.url);
        }
    }
}
