package com.veaer.gank.view;

import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.util.Pair;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.bumptech.glide.Glide;
import com.veaer.gank.R;
import com.veaer.gank.util.DownloadUtil;
import com.veaer.gank.util.ShareUtil;
import com.veaer.gank.widget.ToolbarActivity;
import com.veaer.gank.widget.ZoomImageView;

import java.io.File;

import butterknife.Bind;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func2;
import rx.schedulers.Schedulers;

/**
 * Created by Veaer on 15/10/27.
 */
public class PictureActivity extends ToolbarActivity{

    public static final String EXTRA_IMAGE_URL = "image_url";
    public static final String EXTRA_IMAGE_TITLE = "image_title";

    @Bind(R.id.picture)
    ZoomImageView mImageView;

    String mImageUrl, mImageTitle;




    private void parseIntent() {
        mImageUrl = getIntent().getStringExtra(EXTRA_IMAGE_URL);
        mImageTitle = getIntent().getStringExtra(EXTRA_IMAGE_TITLE);
        mToolbar.setTitle(mImageTitle);
    }


    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        parseIntent();

        Glide.with(this).load(mImageUrl).into(mImageView);

        setAppBarAlpha(0.7f);

        mImageView.setOnViewTapListener(((zoomImageView, x, y) -> hideOrShowToolbar()));

    }

    private String makeFileName() {
        return String.format("%d.jpg", System.currentTimeMillis());
    }

    private void notifyMediaScanning(File file) {
        MediaScannerConnection.scanFile(
                mContext.getApplicationContext(),
                new String[]{file.getPath()}, null, null);
    }

    @Override public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_picture, menu);
        return true;
    }

    @Override public boolean onOptionsItemSelected(MenuItem item) {

        Observable<File> observableDownload = DownloadUtil.download(Glide.with(this), mImageUrl);

        Observable<File> observableSave = DownloadUtil.mkdirsIfNotExists()
                .map(directory -> new File(directory, makeFileName()))
                .withLatestFrom(observableDownload, (Func2<File, File, Pair<File, File>>) Pair::new)
                .flatMap(pair -> DownloadUtil.copy(pair.second, pair.first));
        switch (item.getItemId()) {
            case R.id.action_share:
                observableSave
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(file -> ShareUtil.shareImage(this, Uri.fromFile(file), "分享妹纸到..."), e -> Log.e(TAG, "Failed to share picture", e));
                return true;
            case R.id.action_save:
                observableSave
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(file -> {
                            notifyMediaScanning(file);
                            showToast(file.getName());
                        }, e -> Log.e(TAG, "Failed to save picture", e));
                showToast(getString(R.string.save_finish));
                return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public int getContentViewID() {
        return R.layout.picture_activity;
    }

    @Override
    public boolean canBack() {
        return true;
    }

}
