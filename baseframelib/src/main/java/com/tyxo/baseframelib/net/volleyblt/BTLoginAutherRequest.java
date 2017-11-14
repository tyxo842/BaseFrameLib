package com.tyxo.baseframelib.net.volleyblt;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.tyxo.baseframelib.BaseApplication;
import com.tyxo.baseframelib.utils.SharedPreferencesUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created on 2016/4/20.
 * 登录请求
 */
public class BTLoginAutherRequest extends JsonObjectRequest {

    private Response.ErrorListener mErrorListener;
    private Response.Listener<JSONObject> mListener;
    private SharedPreferencesUtil preferencesUtil;
    private static final int TIMEOUT = 15 * 1000;
    private static final String COOKIE = "cookie";
    private Map<String, String> sendHeader = new HashMap<String, String>(1);

    public BTLoginAutherRequest(String url, JSONObject jsonRequest, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {
        super(Method.POST, url, jsonRequest, listener, errorListener);
        this.mListener = listener;
        this.mErrorListener = errorListener;
//        setTag(STConfig.VOLLEY_TAG);
        setShouldCache(false);
        setRetryPolicy(new DefaultRetryPolicy(TIMEOUT, 0, 0));
        preferencesUtil = SharedPreferencesUtil.Build(BaseApplication.getInstance());
    }

    @Override
    protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
        try {
//            Map<String, String> responseHeaders = response.headers;
//            String rawCookies = responseHeaders.get("Set-Cookie");//cookie值
//            AppLogger.d("rawCookies = " + rawCookies);
//            if (!TextUtils.isEmpty(rawCookies)) {
//                SharedPreferencesUtil.Build(AppApplication.getInstance()).putStringValue(AppConstants.SP_COOKIE_KEY, rawCookies);
//            }
            String jsonString = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
            JSONObject jsonObject = new JSONObject(jsonString);
            return Response.success(jsonObject, HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        } catch (JSONException je) {
            return Response.error(new ParseError(je));
        }
    }

    @Override
    public String getBodyContentType() {
        return "application/json;charset=utf-8";
    }

    @Override
    protected void deliverResponse(JSONObject response) {
        mListener.onResponse(response);
    }

    @Override
    public void deliverError(VolleyError error) {
        mErrorListener.onErrorResponse(error);
    }

    public void setSendCookie(String cookie) {
        sendHeader.put("Cookie", cookie);
    }

}
