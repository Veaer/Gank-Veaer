package com.veaer.gank.view;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.umeng.analytics.AnalyticsConfig;
import com.umeng.analytics.MobclickAgent;
import com.veaer.gank.R;
import com.veaer.gank.data.VAll;
import com.veaer.gank.data.VDay;
import com.veaer.gank.model.VDate;
import com.veaer.gank.widget.BaseActivity;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by Veaer on 15/8/31.
 */
public class SplashActivity extends BaseActivity {
    int goCount = 1;
    VDay vDay;
    VDate vDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_activity_gank);
        MobclickAgent.updateOnlineConfig(this);
        AnalyticsConfig.enableEncrypt(true);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        new Handler().postDelayed(() -> goHome(), 1000);
        loadData();
    }

    public void loadData() {
        Subscription splash = mLine.getSplashData()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(vAll -> getTodayData(vAll));
        addSubscription(splash);
    }

    public void getTodayData(VAll vAll) {
        vDate = new VDate(vAll.results.get(0).publishedAt);
        Subscription today = mLine.getDayData(vDate.YEAR, vDate.MONTH, vDate.DAY)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(vDay -> {
                    this.vDay = vDay;
                    goHome();
                });
        addSubscription(today);
    }

    public void goHome() {
        if(goCount >0) {
            goCount--;
            return;
        }
        Intent homeIntent = new Intent(SplashActivity.this, HomeActivity.class);
        mData.put("current_day", vDay);
        mData.put("current_date", vDate);
        startActivity(homeIntent);
        finish();
    }

}
