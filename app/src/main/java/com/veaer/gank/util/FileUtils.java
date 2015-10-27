
package com.veaer.gank.util;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;

import com.squareup.picasso.Picasso;
import com.veaer.gank.App;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import rx.Observable;
import rx.Subscriber;
import rx.schedulers.Schedulers;


public class FileUtils {
	private static final String HTTP_CACHE_DIR = "http";

	public static File getHttpCacheDir() {
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			return new File(App.sContext.getExternalCacheDir(), HTTP_CACHE_DIR);
		}
		return new File(App.sContext.getCacheDir(), HTTP_CACHE_DIR);
	}

	public static Observable<Uri> saveImageAndGetPathObservable(Context context, String url,
																String title) {
		return Observable.create(new Observable.OnSubscribe<Bitmap>() {
			@Override public void call(Subscriber<? super Bitmap> subscriber) {
				Bitmap bitmap = null;
				try {
					bitmap = Picasso.with(context).load(url).get();
				} catch (IOException e) {
					subscriber.onError(e);
				}
				if (bitmap == null) {
					subscriber.onError(new Exception("无法下载到图片"));
				}
				subscriber.onNext(bitmap);
				subscriber.onCompleted();
			}
		}).flatMap(bitmap -> {
			File appDir = new File(Environment.getExternalStorageDirectory(), "Gank");
			if (!appDir.exists()) {
				appDir.mkdir();
			}
			String fileName = title.replace('/', '-') + ".jpg";
			File file = new File(appDir, fileName);
			try {
				FileOutputStream fos = new FileOutputStream(file);
				assert bitmap != null;
				bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
				fos.flush();
				fos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

			Uri uri = Uri.fromFile(file);
			// 通知图库更新
			Intent scannerIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri);
			context.sendBroadcast(scannerIntent);
			return Observable.just(uri);
		}).subscribeOn(Schedulers.io());
	}

}
