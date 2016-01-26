package com.veaer.gank.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.veaer.gank.R;
import com.veaer.gank.data.VAll;
import com.veaer.gank.data.VDay;
import com.veaer.gank.model.VDate;
import com.veaer.gank.widget.BaseActivity;

import butterknife.Bind;
import butterknife.OnClick;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by Veaer on 15/8/31.
 */
public class HomeActivity extends BaseActivity {
    @Bind(R.id.home_pic)
    ImageView picIv;
    @Bind(R.id.home_pic_via)
    TextView picViaTv;
    @Bind(R.id.home_video)
    TextView videoTv;
    @Bind(R.id.home_video_via)
    TextView videoViaTv;
    @Bind(R.id.home_to_list)
    TextView listTv;
    @Bind(R.id.home_video_layout)
    RelativeLayout videoRl;

    VDay vDay;
    VDate vDate;

    @Override
    public int getContentViewID() {
        return R.layout.home_activity_gank;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadData();
    }

    public void initView() {
        Glide.with(mActivity).load(vDay.results.picList.get(0).url).into(picIv);
        picViaTv.append("via." + vDay.results.picList.get(0).who);
        videoTv.setText(vDay.results.videoList.get(0).desc);
        videoViaTv.append(vDate.TIME  + "  via." + vDay.results.videoList.get(0).who);
        videoRl.setOnClickListener(v -> listener("video"));
        listTv.setOnClickListener(v -> listener("list"));
    }

    public void listener(String label) {
        Intent intent;
        if(label.equals("video")) {
            intent = new Intent(HomeActivity.this, GankWebActivity.class);
            intent.putExtra("feed_url", vDay.results.videoList.get(0).url);
            intent.putExtra("feed_title", vDay.results.videoList.get(0).desc);
        } else {
            intent = new Intent(HomeActivity.this, GankListActivity.class);
        }
        startActivity(intent);
        overridePendingTransition(R.anim.slide_right_in, R.anim.slide_right_out);
    }

    public void loadData() {
        Subscription splash = mLine.getSplashData()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::getTodayData, this::loadError);
        addSubscription(splash);
    }

    public void getTodayData(VAll vAll) {
        vDate = new VDate(vAll.results.get(0).publishedAt);
        Subscription today = mLine.getDayData(vDate.YEAR, vDate.MONTH, vDate.DAY)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(vDay -> {
                    this.vDay = vDay;
                    initView();
                }, this::loadError);
        addSubscription(today);
    }

    @OnClick(R.id.home_pic) public void toPicActivity(View view) {
        Intent picIntent = new Intent(this, PictureActivity.class);
        picIntent.putExtra("image_url", vDay.results.picList.get(0).url);
        picIntent.putExtra("image_title", vDate.TIME);
        startActivity(picIntent);
    }
}
