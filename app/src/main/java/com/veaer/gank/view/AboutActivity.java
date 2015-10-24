package com.veaer.gank.view;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.veaer.gank.BuildConfig;
import com.veaer.gank.R;
import com.veaer.gank.util.ShareUtil;
import com.veaer.gank.widget.ToolbarActivity;

import butterknife.Bind;

/**
 * Created by Veaer on 15/10/24.
 */
public class AboutActivity extends ToolbarActivity {

//    @Bind(R.id.toolbar) Toolbar mToolbar;
    @Bind(R.id.tv_version) TextView mVersionTextView;

    @Override
    public int getContentViewID() {
        return R.layout.about_activity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mVersionTextView.setText("v" + BuildConfig.VERSION_NAME);

    }

    @Override public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_share, menu);
        return true;
    }


    @Override public boolean onOptionsItemSelected(MenuItem item) {
        if(R.id.menu_share == item.getItemId()) {
            ShareUtil.share(this);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean canBack() {
        return true;
    }

    @Override
    public String getToolBarTitle() {
        return "关于开发者";
    }
}
