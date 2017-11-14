package com.tyxo.baseframelib.net.volleyblt;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

/**
 * Created on 2016/11/25.<br>
 */

public class JsonRequest extends JsonObjectRequest {
    //
    private Response.ErrorListener mErrorListener;
    private Response.Listener<JSONObject> mListener;
    private static final int TIMEOUT = 10 * 1000;
    private String JsonStr;

    public JsonRequest(int method, String url, JSONObject jsonRequest, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {
        super(method, url, jsonRequest, listener, errorListener);
        this.mListener = listener;
        this.mErrorListener = errorListener;
//        setTag(STConfig.VOLLEY_TAG);
        setShouldCache(false);
        setRetryPolicy(new DefaultRetryPolicy(
                TIMEOUT, 0, 0));
        JsonStr = jsonRequest.toString();
    }

//    public OTPJsonRequest(String url, JSONObject jsonRequest, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {
//        super(url, jsonRequest, listener, errorListener);
//        this.mListener = listener;
//        this.mErrorListener = errorListener;
//    }

//    @Override
//    public RetryPolicy getRetryPolicy() {
//        RetryPolicy retryPolicy = new DefaultRetryPolicy(TIMEOUT, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
//        return retryPolicy;
//    }

    @Override
    protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
        try {
            String jsonString = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
            return Response.success(new JSONObject(jsonString), HttpHeaderParser.parseCacheHeaders(response));
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

    @Override
    public byte[] getPostBody() {
        return JsonStr.getBytes();
    }

    @Override
    public byte[] getBody() {
        return getPostBody();
    }


}
