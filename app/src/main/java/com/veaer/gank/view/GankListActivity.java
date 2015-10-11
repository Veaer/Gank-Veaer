package com.veaer.gank.view;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.veaer.gank.App;
import com.veaer.gank.R;
import com.veaer.gank.data.VAll;
import com.veaer.gank.model.VDate;
import com.veaer.gank.model.VFeeds;
import com.veaer.gank.util.ToastUtils;
import com.veaer.gank.widget.BaseViewHolder;
import com.veaer.gank.widget.HiSwipeRefreshLayout;
import com.veaer.gank.widget.ToolbarActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by Veaer on 15/8/16.
 */
public class GankListActivity extends ToolbarActivity {
    @Bind(R.id.rv_gank_list)
    RecyclerView gankListRv;
    @Bind(R.id.swipe_refresh_layout)
    HiSwipeRefreshLayout refreshLayout;
    int nextPage = 1;
    boolean canAdd = true;
    GankListAdapter gankListAdapter = new GankListAdapter();
    List<VFeeds> feedsList = new ArrayList<>();

    @Override
    public int getContentViewID() {
        return R.layout.list_activity_gank_list;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initToolBar();
        initViews();
        initData(1, true);
    }

    public void initViews() {
        gankListRv.setAdapter(gankListAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        gankListRv.setLayoutManager(linearLayoutManager);
        gankListRv.addOnScrollListener(getScrollToBottomListener(linearLayoutManager));

        refreshLayout.setColorSchemeResources(R.color.refresh_progress_3,
                R.color.refresh_progress_2, R.color.refresh_progress_1);
        refreshLayout.setOnRefreshListener(() -> initData(1, true));
    }

    public void initToolBar() {
        if(Build.VERSION.SDK_INT >= 21) {
            mAppBar.setElevation(10.6f);
        }
        mToolbar.setTitle("HISTORY");
        mToolbar.setTitleTextColor(getResources().getColor(R.color.theme_text_color));
        mToolbar.setOnClickListener(view -> onToolbarClick());
        setSupportActionBar(mToolbar);
    }

    public void initData(int page, boolean is_refresh) {
        if(!canAdd) {
            ToastUtils.showShort("滑不动了哟");
            setRefreshing(false);
            return;
        }

        Subscription s = Observable.zip(mLine.getPicList(page), mLine.getVideoData(page),
                this::mixPicVideo)
                .map(all -> all.results)
                .flatMap(Observable::from)
                .toSortedList((feed1, feed2) -> feed2.publishedAt.compareTo(feed1.publishedAt))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(all -> {
                    if (all.size() < 10) {
                        canAdd = false;
                    }
                    if (all.size() == 0) {
                        canAdd = false;
                        ToastUtils.showShort("滑不动了哟");
                    }
                    if (is_refresh) {
                        feedsList.clear();
                        feedsList.addAll(all);
                        nextPage = 1;
                    } else {
                        feedsList.addAll(all);
                    }
                    gankListAdapter.notifyDataSetChanged();
                    setRefreshing(false);
                }, throwable -> loadError(throwable));
        addSubscription(s);
    }

    public VAll mixPicVideo(VAll pic, VAll video) {
        int maxLength = pic.results.size() < video.results.size() ? pic.results.size() : video.results.size();
        for (int i = 0; i < maxLength; i++) {
            VFeeds f = pic.results.get(i);
            f.desc = video.results.get(i).desc;
        }
        return pic;
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
                boolean isBottom = linearLayoutManager.findLastVisibleItemPosition() == (feedsList.size() - 1);
                if (!refreshLayout.isRefreshing() && isBottom) {
                    refreshLayout.setRefreshing(true);
                    nextPage++;
                    initData(nextPage, false);
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
            return feedsList.size();
        }

        @Override
        public void onBindViewHolder(GankItemViewHolder holder, int position) {
            if(position < feedsList.size()) holder.bindViews(feedsList.get(position));
        }

        @Override
        public GankItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new GankItemViewHolder(mLayoutInflater.inflate(R.layout.list_view_holder_list_item, parent, false));
        }
    }

    public class GankItemViewHolder extends BaseViewHolder {
        VDate vDate;

        @Bind(R.id.desc)
        TextView descTv;
        @Bind(R.id.year)
        TextView yearTv;
        @Bind(R.id.month)
        TextView monthTv;
        @Bind(R.id.day)
        TextView dayTv;
        @Bind(R.id.picture)
        ImageView pictureIV;
        VFeeds vFeeds;

        public GankItemViewHolder(View view) {
            super(view);
            view.setOnClickListener(v -> toActivity());
        }

        public void toActivity() {
            Intent intent = new Intent(getApplicationContext(), GankDetailActivity.class);
            intent.putExtra("current_time", vDate.TIME);
            intent.putExtra("title_bg", vFeeds.url);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_right_in, R.anim.slide_right_out);
        }

        public void bindViews(VFeeds vFeeds) {
            this.vFeeds = vFeeds;
            vDate = new VDate(vFeeds.publishedAt);
            yearTv.setText(vDate.YEAR + "");
            monthTv.setText(vDate.getMonth());
            dayTv.setText(vDate.DAY + "");
            descTv.setText(vFeeds.desc);
            Glide.with(mActivity)
                    .load(vFeeds.url)
                    .centerCrop()
                    .placeholder(R.mipmap.gank_launcher)
                    .into(pictureIV);
        }
    }
}
