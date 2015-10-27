package com.veaer.gank.view;

import android.os.Bundle;
import android.os.Environment;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AlertDialog;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.veaer.gank.R;
import com.veaer.gank.util.FileUtils;
import com.veaer.gank.util.ShareUtil;
import com.veaer.gank.util.ToastUtils;
import com.veaer.gank.widget.ToolbarActivity;

import java.io.File;

import butterknife.Bind;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * Created by Veaer on 15/10/27.
 */
public class PictureActivity extends ToolbarActivity{

    public static final String EXTRA_IMAGE_URL = "image_url";
    public static final String EXTRA_IMAGE_TITLE = "image_title";
    public static final String TRANSIT_PIC = "picture";

    @Bind(R.id.picture)
    ImageView mImageView;

    PhotoViewAttacher mPhotoViewAttacher;
    String mImageUrl, mImageTitle;


    @Override
    public int getContentViewID() {
        return R.layout.picture_activity;
    }

    @Override public boolean canBack() {
        return true;
    }


    private void parseIntent() {
        mImageUrl = getIntent().getStringExtra(EXTRA_IMAGE_URL);
        mImageTitle = getIntent().getStringExtra(EXTRA_IMAGE_TITLE);
        mToolbar.setTitle(mImageTitle);
    }


    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        parseIntent();

        // init image view
        ViewCompat.setTransitionName(mImageView, TRANSIT_PIC);
        Picasso.with(this).load(mImageUrl).into(mImageView);

        // set up app bar
        setAppBarAlpha(0.7f);
        setupPhotoAttacher();
    }


    private void setupPhotoAttacher() {
        mPhotoViewAttacher = new PhotoViewAttacher(mImageView);
        mPhotoViewAttacher.setOnViewTapListener((view, v, v1) -> hideOrShowToolbar());
        mPhotoViewAttacher.setOnLongClickListener(v -> {
            new AlertDialog.Builder(PictureActivity.this).setMessage(
                    getString(R.string.save_pic))
                    .setNegativeButton(android.R.string.cancel, (dialog, which) -> {
                        dialog.dismiss();
                    })
                    .setPositiveButton(android.R.string.ok, (dialog, which) -> {
                        saveImageToGallery();
                        dialog.dismiss();
                    })
                    .show();
            return true;
        });
    }


    private void saveImageToGallery() {
        Subscription s = FileUtils.saveImageAndGetPathObservable(this, mImageUrl, mImageTitle)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(uri -> {
                    File appDir = new File(Environment.getExternalStorageDirectory(), "Gank");
                    String msg = String.format(getString(R.string.picture_has_save_to),
                            appDir.getAbsolutePath());
                    ToastUtils.showShort(msg);
                }, error -> ToastUtils.showLong(error.getMessage() + "\n再试试..."));
        addSubscription(s);
    }


    @Override public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_picture, menu);
        return true;
    }


    @Override public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_share:
                FileUtils.saveImageAndGetPathObservable(this, mImageUrl, mImageTitle)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(uri -> ShareUtil.shareImage(this, uri, "分享妹纸到..."),
                                error -> ToastUtils.showLong(error.getMessage()));
                return true;
            case R.id.action_save:
                saveImageToGallery();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
