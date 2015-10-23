package com.veaer.gank.view;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.veaer.gank.R;
import com.veaer.gank.widget.ToolbarActivity;


/**
 * Created by Veaer on 15/8/27.
 */
public class GankWebActivity extends ToolbarActivity {

    WebView mWebView;

    Context mContext;
    String mUrl, mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.web_activity_gank);
        mUrl = getIntent().getStringExtra("feed_url");
        mTitle = getIntent().getStringExtra("feed_title");
        initToolBar();
        getViews();
        mContext = this;

        WebSettings settings = mWebView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setLoadWithOverviewMode(true);
        settings.setAppCacheEnabled(true);
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        settings.setSupportZoom(true);
        settings.setDomStorageEnabled(true);
        mWebView.setWebChromeClient(new ChromeClient());
        mWebView.setWebViewClient(new LoveClient());

        mWebView.loadUrl(mUrl);

        if (mTitle != null) setTitle(mTitle);
    }

    public void initToolBar() {
        mToolbar = $(R.id.toolbar);
        mAppBar = $(R.id.app_bar_layout);

        if(Build.VERSION.SDK_INT >= 21) {
            mAppBar.setElevation(10.6f);
        }
        mToolbar.setTitle(mTitle);
        mToolbar.setTitleTextColor(getResources().getColor(R.color.theme_text_color));
        setSupportActionBar(mToolbar);
        mToolbar.setOnClickListener(v -> goTop());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void goTop() {
        mWebView.scrollTo(0,0);
    }

    public void getViews() {
        mWebView = $(R.id.webView);
    }

    private void refresh() {
        mWebView.reload();
    }

    @Override
    public void onPause() {
        super.onPause();
        mWebView.reload();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            switch (keyCode) {
                case KeyEvent.KEYCODE_BACK:
                    if (mWebView.canGoBack()) {
                        mWebView.goBack();
                    }
                    else {
                        finish();
                    }
                    return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    private class ChromeClient extends WebChromeClient {

        @Override public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
        }

        @Override public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
            setTitle(title);
        }
    }

    private class LoveClient extends WebViewClient {

        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if (url != null) view.loadUrl(url);
            return true;
        }
    }

}
