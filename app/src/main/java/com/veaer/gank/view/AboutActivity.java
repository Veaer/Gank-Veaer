package com.veaer.gank.view;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
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

    @Bind(R.id.tv_version) TextView mVersionTextView;
    @Bind(R.id.donate_tv) TextView donateTv;

    @Override
    public int getContentViewID() {
        return R.layout.about_activity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mVersionTextView.append(BuildConfig.VERSION_NAME);
        donateTv.setOnClickListener(v -> {
            ClipboardManager cmb = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
            cmb.setPrimaryClip(ClipData.newPlainText(null, getText(R.string.email)));
            showToast(getString(R.string.copy_finish));
        });

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
        return getString(R.string.action_about);
    }
}
