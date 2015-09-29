package com.veaer.gank.view;

import android.content.Intent;
import android.os.Bundle;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.veaer.gank.R;
import com.veaer.gank.data.VDay;
import com.veaer.gank.model.VDate;
import com.veaer.gank.widget.BaseActivity;
import com.veaer.gank.widget.HiImageView;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Veaer on 15/8/31.
 */
public class HomeActivity extends BaseActivity {
    @Bind(R.id.home_pic)
    HiImageView picIv;
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity_gank);
        ButterKnife.bind(this);
        vDay = mData.get("current_day");
        vDate = mData.get("current_date");
        initView();
    }

    public void initView() {
        picIv.setAspectRatio(0.8F);
        picIv.loadImage(vDay.results.picList.get(0).url);
        picViaTv.setText("via." + vDay.results.picList.get(0).who);
        videoTv.setText(vDay.results.videoList.get(0).desc);
        videoViaTv.setText(vDate.TIME  + "  via." + vDay.results.videoList.get(0).who);
        videoRl.setOnClickListener(v -> listener("video"));
        listTv.setOnClickListener(v -> listener("list"));
    }

    public void listener(String label) {
        Intent intent;
        if(label.equals("video")) {
            intent = new Intent(HomeActivity.this, GankVideoActivity.class);
            intent.putExtra("video_url", vDay.results.videoList.get(0).url);
            intent.putExtra("video_title", vDay.results.videoList.get(0).desc);
        } else {
            intent = new Intent(HomeActivity.this, GankListActivity.class);
        }
        startActivity(intent);
        overridePendingTransition(R.anim.slide_right_in, R.anim.slide_right_out);
    }
}
