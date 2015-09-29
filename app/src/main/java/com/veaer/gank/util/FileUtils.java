
package com.veaer.gank.util;

import android.os.Environment;

import com.veaer.gank.App;

import java.io.File;


public class FileUtils {
	private static final String HTTP_CACHE_DIR = "http";

	public static File getHttpCacheDir() {
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			return new File(App.sContext.getExternalCacheDir(), HTTP_CACHE_DIR);
		}
		return new File(App.sContext.getCacheDir(), HTTP_CACHE_DIR);
	}

}
