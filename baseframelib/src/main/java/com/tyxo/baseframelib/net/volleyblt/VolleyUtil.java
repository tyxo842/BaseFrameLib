package com.tyxo.baseframelib.net.volleyblt;

import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;

import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.tyxo.baseframelib.BaseApplication;
import com.tyxo.baseframelib.utils.AndroidUtils;
import com.tyxo.baseframelib.utils.AppUtil;
import com.tyxo.baseframelib.utils.logblt.AppLogger;

import java.util.Map;

public class VolleyUtil {
    private RequestQueue queue;
    private static VolleyUtil mInstance;
    private Context mContext;
    private static int retryCount = 0;

    // private String tempUrl;
    private VolleyUtil(Context mContext) {
        this.mContext = mContext;
    }

    public static VolleyUtil getInstance(Context mContext) {
        if (mInstance == null) {
            mInstance = new VolleyUtil(mContext);
        }
        retryCount = 0;
        return mInstance;
    }

    public static VolleyUtil getInstance() {
        return getInstance(BaseApplication.getInstance().getApplicationContext());
    }

    public <K, T> void doGsonObjRequestGet(String url, final Class<T> clz,
                                           final Map<String, String> params,
                                           final IVolleyListener<T> volleyListener) {
        if (!AndroidUtils.isNetworkAvailable(mContext)) {
            AndroidUtils.showToast(mContext, "当前网络不可用，请检查网络设置");
            return;
        }
        queue = BaseApplication.getHttpRequestQueue(mContext);
        // final String newUrl = url + "?"
        // + new HttpRequest(mContext).joinParasWithEncodedValue(params);
        String tempUrl = url;
        if (params != null && !params.isEmpty()) {
            Uri.Builder builder = Uri.parse(url).buildUpon();
            for (Map.Entry<String, String> entry : params.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
                builder.appendQueryParameter(key, value);
            }
            tempUrl = builder.toString();
        }
        final String newUrl = tempUrl;

        GsonObjRequest<K, T> gsonObjRequest = new GsonObjRequest<K, T>(
                Method.GET, newUrl, null, clz, new Response.Listener<T>() {

            @Override
            public void onResponse(T response) {
                volleyListener.onResponse(response);
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                AppLogger.e(error.getLocalizedMessage());
                if (error != null && !TextUtils.isEmpty(error.getMessage()) && "401".equals(error.getMessage())) {// 401session超时
                    AppUtil.putIsLogin(mContext, false);

                    /*if (retryCount == 0) {//自动登录,验证cookie后,访问网络请求
                        retryCount = 1;
                        User.getInstance().autoLogin(
                                new User.AutoLoginCallback() {

                                    @Override
                                    public void onfailure(VolleyError loginError) {
                                        gotoLogin();
                                    }

                                    @Override
                                    public void onSuccess() {
                                        mInstance.doGsonObjRequestGet(
                                                newUrl, clz, params,
                                                volleyListener);
                                        retryCount = 0;
                                    }
                                });
                    } else {
//                        gotoLogin();
                    }*/
                    mInstance.doGsonObjRequestGet(newUrl, clz, params, volleyListener);//不带cookie,直接访问
                } else {
                    volleyListener.onErrorResponse(error);
                }
            }
        });

        queue.add(gsonObjRequest);
    }

    public <T, K> void doGsonObjRequestPost(final String url, final K obj,
                                            final Class<T> clz, final IVolleyListener<T> volleyListener) {
        if (!AndroidUtils.isNetworkAvailable(mContext)) {
            AndroidUtils.showToast(mContext, "当前网络不可用，请检查网络设置");
            return;
        }
        queue = BaseApplication.getHttpRequestQueue(mContext);
        GsonObjRequest<K, T> gsonObjRequest = new GsonObjRequest<K, T>(url,
                obj, clz, new Response.Listener<T>() {

            @Override
            public void onResponse(T response) {
                volleyListener.onResponse(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error != null && !TextUtils.isEmpty(error.getMessage()) && error.getMessage().equals("401")) {// 401session超时
                    AppUtil.putIsLogin(mContext, false);
                    /*if (retryCount == 0) {
                        User.getInstance().autoLogin(
                                new User.AutoLoginCallback() {

                                    @Override
                                    public void onfailure(VolleyError loginError) {
                                        gotoLogin();
                                    }

                                    @Override
                                    public void onSuccess() {
                                        mInstance.doGsonObjRequestPost(
                                                url, obj, clz,
                                                volleyListener);
                                    }
                                });
                        retryCount = 1;
                    } else {
//                        gotoLogin();
                    }*/
                    mInstance.doGsonObjRequestPost(url, obj, clz, volleyListener);
                } else {
                    volleyListener.onErrorResponse(error);
                }
            }
        });
        queue.add(gsonObjRequest);
    }

    /*private void gotoLogin() {
        AppLogger.d("LoginActivity 启动");
//        AppApplication.getInstance().exitApp();
        Intent intent = new Intent(mContext, LoginActivity.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        // intent.putExtra(AppConstants.TRANS_BOOLEAN_RELOGIN, true);
        mContext.startActivity(intent);
//        if(mContext instanceof  Activity)
//        ((Activity) mContext).finish();
    }*/
}
