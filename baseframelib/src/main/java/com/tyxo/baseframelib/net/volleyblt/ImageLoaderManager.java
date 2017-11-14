package com.tyxo.baseframelib.net.volleyblt;

import android.graphics.Bitmap;
import android.widget.ImageView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.tyxo.baseframelib.BaseApplication;

/**
 * Created on 2015/10/8.
 */
public class ImageLoaderManager {

    private static ImageLoader.ImageCache mImageCache = new BitmapLruCache();
    private static ImageLoader mImageLoader = new ImageLoader(BaseApplication.getHttpRequestQueue(BaseApplication.getInstance()), mImageCache);

    private static ImageLoader.ImageListener getImageListener(final ImageView view, final Bitmap defaultImage, final Bitmap errorImage) {
        return new ImageLoader.ImageListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // 回调失败
                if (errorImage != null) {
                    view.setImageBitmap(errorImage);
                }
            }

            @Override
            public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
                // 回调成功
                if (response.getBitmap() != null) {
                    view.setImageBitmap(response.getBitmap());
                } else if (defaultImage != null) {
                    view.setImageBitmap(null);
                }
            }
        };

    }

//    private static ImageLoader.ImageListener getRoundImageListener(final ImageView view, final Bitmap defaultImage, final Bitmap errorImage) {
//        return new ImageLoader.ImageListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                // 回调失败
//                if (errorImage != null) {
//                    view.setImageBitmap(ImageUtils.toRoundBitmap(errorImage));
//                }
//            }
//
//            @Override
//            public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
//                // 回调成功
//                if (response.getBitmap() != null) {
//                    view.setImageBitmap(ImageUtils.toRoundBitmap(response.getBitmap()));
//                } else if (defaultImage != null) {
//                    view.setImageBitmap(ImageUtils.toRoundBitmap(defaultImage));
//                }
//            }
//        };
//
//    }

    /**
     * 提供给外部调用方法
     *
     * @param url
     * @param view
     * @param defaultImage
     * @param errorImage
     */
    public static void loadImage(String url, ImageView view, Bitmap defaultImage, Bitmap errorImage) {
        mImageLoader.get(url, ImageLoaderManager.getImageListener(view, defaultImage, errorImage), 0, 0);
    }

    /**
     * 提供给外部调用方法
     *
     * @param url
     * @param view
     * @param defaultImage
     * @param errorImage
     */
    public static void loadImage(String url, ImageView view, Bitmap defaultImage, Bitmap errorImage, int maxWidth, int maxHeight) {
        mImageLoader.get(url, ImageLoaderManager.getImageListener(view, defaultImage, errorImage), maxWidth, maxHeight);
    }

    /**
     * 圆形图片
     * @param url
     * @param view
     * @param defaultImage
     * @param errorImage
     */
//    public static void loadRoundImage(String url, ImageView view, Bitmap defaultImage, Bitmap errorImage) {
//        mImageLoader.get(url, ImageLoaderManager.getRoundImageListener(view, defaultImage, errorImage), 0, 0);
//    }

    /**
     * 圆形图片
     * @param url
     * @param view
     * @param defaultImage
     * @param errorImage
     * @param maxWidth
     * @param maxHeight
     */
//    public static void loadRoundImage(String url, ImageView view, Bitmap defaultImage, Bitmap errorImage, int maxWidth, int maxHeight) {
//        mImageLoader.get(url, ImageLoaderManager.getRoundImageListener(view, defaultImage, errorImage), maxWidth, maxHeight);
//    }

}
