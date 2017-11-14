package com.tyxo.baseframelib.net.volleyblt;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.util.LruCache;

import com.android.volley.toolbox.ImageLoader;

/**
 * 图片cache
 */
@SuppressLint("NewApi")
public class BitmapLruCache implements ImageLoader.ImageCache {

    private LruCache<String, Bitmap> mCache;
    private static BitmapLruCache cache;

    // 磁盘缓存大小
    private static final int DISKMAXSIZE = 10 * 1024 * 1024;

    public static BitmapLruCache getInstance() {
        if (cache == null) {
            cache = new BitmapLruCache();
        }
        return cache;
    }

    @SuppressLint("NewApi")
    public BitmapLruCache() {
        int maxSize = (int) (Runtime.getRuntime().maxMemory() / 8);
        mCache = new LruCache<String, Bitmap>(maxSize) {
            @Override
            protected int sizeOf(String key, Bitmap bitmap) {
                return bitmap.getRowBytes() * bitmap.getHeight();
            }
        };
    }

    @SuppressLint("NewApi")
    @Override
    public Bitmap getBitmap(String url) {
        return mCache.get(url);
    }

    @Override
    public void putBitmap(String url, Bitmap bitmap) {
        mCache.put(url, bitmap);
    }

}