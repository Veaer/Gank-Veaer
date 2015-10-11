package com.veaer.gank.widget;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.umeng.analytics.MobclickAgent;
import com.veaer.gank.request.Line;
import com.veaer.gank.request.LineFactory;
import com.veaer.gank.util.DataProvider;
import com.veaer.gank.util.ToastUtils;

import butterknife.ButterKnife;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Veaer on 15/8/15.
 */
public class BaseActivity extends AppCompatActivity {
    public static String TAG;
    public Activity mActivity;
    protected String[] headWhiteList = {};
    protected final static Line mLine = LineFactory.getSingleton();
    protected CompositeSubscription mCompositeSubscription;
    protected final static DataProvider mData = DataProvider.getInstance();

    public < T extends View> T $(int id) {
        return (T)super.findViewById(id);
    }

    public void hideView(int id) {
        findViewById(id).setVisibility(View.GONE);
    }

    public void showView(int id) {
        findViewById(id).setVisibility(View.VISIBLE);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getContentViewID() != 0) {
            setContentView(getContentViewID());
            ButterKnife.bind(this);
        }
        this.mActivity = this;
        TAG = this.getClass().getSimpleName();
    }

    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(getContentViewID() != 0) {
            ButterKnife.unbind(this);
        }
        if (this.mCompositeSubscription != null) {
            this.mCompositeSubscription.unsubscribe();
        }
    }

    public CompositeSubscription getCompositeSubscription() {
        if (this.mCompositeSubscription == null) {
            this.mCompositeSubscription = new CompositeSubscription();
        }

        return this.mCompositeSubscription;
    }

    public void addSubscription(Subscription s) {
        if (this.mCompositeSubscription == null) {
            this.mCompositeSubscription = new CompositeSubscription();
        }

        this.mCompositeSubscription.add(s);
    }

    public int getContentViewID() {
        return 0;
    }

    public void loadError(Throwable throwable) {
        ToastUtils.showShort("貌似网络出了一点问题，快喝口水冷静一下\uD83D\uDE02");
    }
}
