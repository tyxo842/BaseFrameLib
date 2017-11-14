package com.tyxo.baseframelib.net.volleyblt;

import okhttp3.OkHttpClient;

/**
 * Created by wanghelin on 2016/10/14.<br>
 * www.baolaitong.com
 */
public class OkHttpUtils {
    public static final long DEFAULT_MILLISECONDS = 100000L;
    private static OkHttpUtils mInstance;

    private OkHttpClient mOkHttpClient;

    public OkHttpUtils(OkHttpClient okHttpClient) {
        if (okHttpClient == null) {
            mOkHttpClient = new OkHttpClient();
        } else {
            mOkHttpClient = okHttpClient;
        }
    }

    public static OkHttpUtils initClient(OkHttpClient okHttpClient) {
        if (mInstance == null) {
            synchronized (OkHttpUtils.class) {
                if (mInstance == null) {
                    mInstance = new OkHttpUtils(okHttpClient);
                }
            }
        }
        return mInstance;
    }

    public static OkHttpUtils getInstance() {
        return initClient(null);
    }

    public OkHttpClient getOkHttpClient() {
        return mOkHttpClient;
    }

}
