package com.tyxo.baseframelib;

import android.app.Application;
import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.tyxo.baseframelib.net.volleyblt.OkHttp3Stack;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by jsyfb-liy on 2017/11/14.
 */

public class BaseApplication extends Application {

    private static BaseApplication instance;
    private static ExecutorService mThreadPool;
    private static RequestQueue mHttpRequestQueue;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    public static BaseApplication getInstance() {
        return instance;
    }

    /**
     * 创建并获取线程池
     */
    public static ExecutorService getThreadPool() {
        if (mThreadPool == null) {
            return Executors.newFixedThreadPool(5);
        }
        return mThreadPool;
    }

    public static RequestQueue getHttpRequestQueue(Context context) {
        if (mHttpRequestQueue == null) {
            mHttpRequestQueue = Volley.newRequestQueue(context, new OkHttp3Stack(context));
        }
        return mHttpRequestQueue;
    }
}
