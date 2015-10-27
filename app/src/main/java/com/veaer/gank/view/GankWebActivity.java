package com.veaer.gank.view;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.veaer.gank.R;
import com.veaer.gank.util.ShareUtil;
import com.veaer.gank.util.ToastUtils;
import com.veaer.gank.widget.ToolbarActivity;

import butterknife.Bind;


/**
 * Created by Veaer on 15/8/27.
 */
public class GankWebActivity extends ToolbarActivity {

    @Bind(R.id.webView) WebView mWebView;

    String mUrl, mTitle;

    @Override
    public boolean canBack() {
        return true;
    }

    @Override
    public int getContentViewID() {
        return R.layout.web_activity_gank;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mUrl = getIntent().getStringExtra("feed_url");
        mTitle = getIntent().getStringExtra("feed_title");

        mToolbar.setTitle(mTitle);

        WebSettings settings = mWebView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setLoadWithOverviewMode(true);
        settings.setAppCacheEnabled(true);
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        settings.setSupportZoom(true);
        settings.setDomStorageEnabled(true);
        mWebView.setWebChromeClient(new ChromeClient());
        mWebView.setWebViewClient(new LoveClient());
        mWebView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);

        mWebView.loadUrl(mUrl);

    }

    public void onToolbarClick() {
        mWebView.scrollTo(0,0);
    }


    private void refresh() {
        mWebView.reload();
    }

    @Override public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_web, menu);
        return true;
    }


    @Override public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.menu_refresh:
                refresh();
                return true;
            case R.id.menu_share:
                ShareUtil.share(this, getString(R.string.share_url) + mUrl + getString(R.string.app_download));
                return true;
            case R.id.menu_open:
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                Uri uri = Uri.parse(mUrl);
                intent.setData(uri);
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                } else {
                    ToastUtils.showLong(R.string.open_fail);
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
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
