package com.tyxo.baseframelib.net.volleyblt;

import com.android.volley.AuthFailureError;
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
import com.tyxo.baseframelib.BaseApplication;
import com.tyxo.baseframelib.utils.AppUtil;
import com.tyxo.baseframelib.utils.SharedPreferencesUtil;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * Wrapper for Volley requests to facilitate parsing of json responses.
 *
 * @param <T>
 */
public class GsonRequest<T> extends Request<T> {

    /**
     * Gson parser
     */
    private final Gson mGson;

    /**
     * Class type for the response
     */
    private final Class<T> mClass;

    /**
     * Callback for response delivery
     */
    private final Listener<T> mListener;

    private final Map<String, String> params;
    private Map<String, String> sendHeader = new HashMap<String, String>(1);

    /**
     * @param method        Request type.. Method.GET etc
     * @param url           path for the requests
     * @param objectClass   expected class type for the response. Used by gson for
     *                      serialization.
     * @param listener      handler for the response
     * @param errorListener handler for errors
     */
    public GsonRequest(int method, String url, Class<T> objectClass,
                       Listener<T> listener, ErrorListener errorListener, Map<String, String> params) {
        super(method, url, errorListener);
        this.mClass = objectClass;
        this.mListener = listener;
        this.params = params;
        mGson = new Gson();
        setShouldCache(false);
    }

    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {
        try {
            String json = new String(response.data,
                    HttpHeaderParser.parseCharset(response.headers));
            return Response.success(mGson.fromJson(json, mClass),
                    HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        } catch (JsonSyntaxException e) {
            return Response.error(new ParseError(e));
        }
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        String cookie = SharedPreferencesUtil.Build(BaseApplication.getInstance()).getStringValue(AppUtil.SP_COOKIE_KEY);
        if (cookie != null && cookie.length() > 0) {
            sendHeader.put("Cookie", cookie);
        }
        return sendHeader;
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        Map<String, String> params = new HashMap<String, String>();
        return params;
    }

    @Override
    public byte[] getBody() throws AuthFailureError {
        return super.getBody();
    }

    @Override
    public String getBodyContentType() {
        return "application/json;charset=utf-8";
    }

    @Override
    protected void deliverResponse(T response) {
        mListener.onResponse(response);
    }

    @Override
    public void deliverError(VolleyError error) {
        super.deliverError(error);
    }

}
