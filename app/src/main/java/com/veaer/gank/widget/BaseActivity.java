package com.veaer.gank.widget;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;

import com.umeng.analytics.MobclickAgent;
import com.veaer.gank.R;
import com.veaer.gank.request.Line;
import com.veaer.gank.request.LineFactory;

import butterknife.ButterKnife;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Veaer on 15/8/15.
 */
public class BaseActivity extends AppCompatActivity {
    public static String TAG;
    public Activity mActivity;
    public Context mContext;
    protected final static Line mLine = LineFactory.getSingleton();
    protected CompositeSubscription mCompositeSubscription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getContentViewID() != 0) {
            setContentView(getContentViewID());
            ButterKnife.bind(this);
        }
        this.mContext = this.mActivity = this;
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
        showToast(getString(R.string.error));
    }


    public void showToast(int id) {
        showToast(getString(id));
    }

    public void showToast(String msg) {
        if(null == msg) { return; }
        Snackbar.make(getWindow().getDecorView(), msg, Snackbar.LENGTH_SHORT).show();
    }

}
