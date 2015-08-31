package com.veaer.gank.view;

import android.content.Intent;
import android.os.Bundle;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.veaer.gank.R;
import com.veaer.gank.model.VDate;
import com.veaer.gank.model.VPicture;
import com.veaer.gank.model.VVideo;
import com.veaer.gank.widget.BaseActivity;
import com.veaer.gank.widget.HiImageView;

/**
 * Created by Veaer on 15/8/31.
 */
public class HomeActivity extends BaseActivity {
    HiImageView picIv;
    TextView picViaTv;
    TextView videoTv;
    TextView videoViaTv;
    TextView listTv;
    RelativeLayout videoRl;
    VDate vDate;
    VPicture vPicture;
    VVideo vVideo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity_gank);
        vDate = (VDate)getIntent().getSerializableExtra("current_time");
        vPicture = (VPicture)getIntent().getSerializableExtra("current_picture");
        vVideo = (VVideo)getIntent().getSerializableExtra("current_video");
        getViews();
        initView();
    }

    public void getViews() {
        picIv = $(R.id.home_pic);
        picIv.setAspectRatio(0.8F);
        picViaTv = $(R.id.home_pic_via);
        videoTv = $(R.id.home_video);
        videoViaTv = $(R.id.home_video_via);
        videoRl = $(R.id.home_video_layout);
        listTv = $(R.id.home_to_list);
    }

    public void initView() {
        picIv.loadImage(vPicture.url);
        picViaTv.setText("via." + vPicture.who);
        videoTv.setText(vVideo.desc);
        videoViaTv.setText(vDate.TIME  + "  via." + vVideo.who);
        videoRl.setOnClickListener(v -> listener("video"));
        listTv.setOnClickListener(v -> listener("list"));
    }

    public void listener(String label) {
        Intent intent;
        if(label.equals("video")) {
            intent = new Intent(HomeActivity.this, GankVideoActivity.class);
            intent.putExtra("video_url", vVideo.url);
            intent.putExtra("video_title", vVideo.desc);
        } else {
            intent = new Intent(HomeActivity.this, GankListActivity.class);
        }
        startActivity(intent);
        overridePendingTransition(R.anim.slide_right_in, R.anim.slide_right_out);
    }
}
