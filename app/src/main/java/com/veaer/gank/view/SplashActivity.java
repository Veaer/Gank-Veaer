package com.veaer.gank.view;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.alibaba.fastjson.JSON;
import com.android.volley.Response;
import com.veaer.gank.R;
import com.veaer.gank.model.VDate;
import com.veaer.gank.model.VFeed;
import com.veaer.gank.model.VPicture;
import com.veaer.gank.model.VVideo;
import com.veaer.gank.request.VolleyRequestManager;
import com.veaer.gank.util.DateUtil;
import com.veaer.gank.util.URLProvider;
import com.veaer.gank.widget.BaseActivity;

import org.json.JSONObject;

import java.util.List;

/**
 * Created by Veaer on 15/8/31.
 */
public class SplashActivity extends BaseActivity {
    int goCount = 1;
    VDate vDate;
    VPicture vPicture;
    VVideo vVideo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_activity_gank);
        loadData();
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                goHome();
            }

        }, 3000);
    }

    public void loadData() {
        VolleyRequestManager.getInstance().get(URLProvider.ALLLURL + "1/1", null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                List<VFeed> feedList = JSON.parseArray(response.optString("results"), VFeed.class);
                vDate = DateUtil.publish2date(feedList.get(0).publishedAt);
                VolleyRequestManager.getInstance().get(URLProvider.DAYURL + vDate.TIME, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        if (!response.optBoolean("error")) {
                            JSONObject results = response.optJSONObject("results");
                            vPicture = JSON.parseArray(results.optString("福利"), VPicture.class).get(0);
                            vVideo = JSON.parseArray(results.optString("休息视频"), VVideo.class).get(0);
                            goHome();
                        }
                    }
                });
            }
        });
    }

    public void goHome() {
        if(goCount >0) {
            goCount--;
            return;
        }
        Intent homeIntent = new Intent(SplashActivity.this, HomeActivity.class);
        homeIntent.putExtra("current_time", vDate);
        homeIntent.putExtra("current_picture", vPicture);
        homeIntent.putExtra("current_video", vVideo);
        startActivity(homeIntent);
        finish();
    }

}
