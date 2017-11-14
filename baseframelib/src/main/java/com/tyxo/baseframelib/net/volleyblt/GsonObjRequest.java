package com.tyxo.baseframelib.net.volleyblt;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.tyxo.baseframelib.bean.BaseResponse;
import com.tyxo.baseframelib.utils.JsonUtils;
import com.tyxo.baseframelib.utils.logblt.AppLogger;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * 请求的Json数据为对象的情况
 */
public class GsonObjRequest<K, T> extends Request<T> {

    private Listener<T> listener;
    private Gson mGson;
    private Map<String, String> params;
    private K k;
    private Class<T> mClazz;

    private GsonObjRequest(int method, String url, ErrorListener listener) {
        super(method, url, listener);
        mGson = new Gson();
        setShouldCache(false);
        setRetryPolicy(new DefaultRetryPolicy(1000 * 30, 0, 0));
    }

    public GsonObjRequest(int method, String url, Map<String, String> params,
                          Class<T> mClazz, Listener<T> listener,
                          ErrorListener errorListener) {
        this(method, url, errorListener);
        this.listener = listener;
        this.params = params;
        this.mClazz = mClazz;
    }

    public GsonObjRequest(String url, K clzz, Class<T> mClazz,
                          Listener<T> listener, ErrorListener errorListener) {
        this(Method.POST, url, null, mClazz, listener, errorListener);
        this.k = clzz;
    }

    public GsonObjRequest(String url, Class<T> mClazz,
                          Listener<T> listener, ErrorListener errorListener) {
        this(Method.GET, url, null, mClazz, listener, errorListener);
    }

    @Override
    protected void deliverResponse(T t) {
        if (listener != null) {
            listener.onResponse(t);
        }
    }

    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse networkResponse) {
        String json = null;
        try {
            json = new String(networkResponse.data,
                    HttpHeaderParser.parseCharset(networkResponse.headers));
//            AppLogger.d("url=" + getUrl());
//            AppLogger.d("json = " + json);
//            String cookie = networkResponse.headers.get("Set-Cookie");
            /**
             * cookie保存只放在登录时
             */
//            if (cookie != null) {
//                SharedPreferencesUtil.Build(AppApplication.getInstance())
//                        .putStringValue(AppConstants.SP_COOKIE_KEY, cookie);
//            }

//            if (!mClazz.getName().equals("java.lang.String")) {
            BaseResponse response = mGson.fromJson(json, BaseResponse.class);
            AppLogger.i("lynet", "gsonRes : " + json);
            if (response.code.equals("401") || response.code.equals("403")) {//session 超时
                VolleyError error = new VolleyError("401");
                return Response.error(error);
            }
//            }

//            T result = mGson.fromJson(json, mClazz);

            T result = JsonUtils.deserialize(json, mClazz);
            return Response.success(result,
                    HttpHeaderParser.parseCacheHeaders(networkResponse));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        } catch (JsonSyntaxException e) {
            return Response.error(new ParseError(e));
        }
    }

    @Override
    public byte[] getPostBody() throws AuthFailureError {
        if (params == null) {
            String str = null;
            try {
                str = JsonUtils.serialize(k);
            } catch (Exception e) {
                e.printStackTrace();
                AppLogger.d(e.toString());
            }
            return str.getBytes();
        } else {
            return super.getPostBody();
        }
    }

    @Override
    public byte[] getBody() throws AuthFailureError {
        return getPostBody();
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        Map<String, String> header = new HashMap<String, String>();
        header.put("content-Type", "application/json;charset=utf-8");

        return header;
    }

    @Override
    public String getBodyContentType() {
        return "application/json;charset=utf-8";
    }

//    @Override
//    protected Map<String, String> getParams() throws AuthFailureError {
//        return params;
//    }

}
