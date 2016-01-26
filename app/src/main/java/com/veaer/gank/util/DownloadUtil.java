package com.veaer.gank.util;

import android.os.Environment;

import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.target.Target;

import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

import rx.Observable;

/**
 * Created by Veaer on 16/1/12.
 */
public class DownloadUtil {

    public static Observable<File> download(RequestManager rm, String url) {
        return download(rm, url, Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL);
    }

    public static Observable<File> download(RequestManager rm, String url, int width, int height) {
        return Observable.defer(() -> {
            try {
                return Observable.just(rm.load(url).downloadOnly(width, height).get());
            } catch (Exception e) {
                return Observable.error(e);
            }
        });
    }

    public static Observable<File> copy(File from, File to) {
        return Observable.defer(() -> {
            FileInputStream input = null;
            FileOutputStream output = null;

            try {
                input = new FileInputStream(from);
                output = new FileOutputStream(to);

                FileChannel inputChannel = input.getChannel();
                FileChannel outputChannel = output.getChannel();

                inputChannel.transferTo(0, inputChannel.size(), outputChannel);

                return Observable.just(to);
            } catch (IOException e) {
                return Observable.error(e);
            } finally {
                closeQuietly(input);
                closeQuietly(output);
            }
        });
    }

    public static Observable<File> mkdirsIfNotExists() {
        File appDir = new File(Environment.getExternalStorageDirectory(), "Gank");
        return Observable.defer(() -> {
            if (appDir.mkdirs() || appDir.isDirectory()) {
                return Observable.just(appDir);
            } else {
                return Observable.error(new IOException("Failed to mkdirs " + appDir.getPath()));
            }
        });
    }

    private static void closeQuietly(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException ignored) {
            }
        }
    }
}
