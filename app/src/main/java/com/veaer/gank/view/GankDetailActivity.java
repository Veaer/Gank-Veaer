package com.veaer.gank.view;

import android.os.Bundle;

import com.veaer.gank.R;
import com.veaer.gank.widget.ToolbarActivity;

/**
 * Created by Veaer on 15/8/19.
 */
public class GankDetailActivity extends ToolbarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_activity_gank);
    }

}
