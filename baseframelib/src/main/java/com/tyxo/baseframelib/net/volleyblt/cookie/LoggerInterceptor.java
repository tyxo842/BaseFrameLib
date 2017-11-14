package com.tyxo.baseframelib.net.volleyblt.cookie;

import android.text.TextUtils;

import com.tyxo.baseframelib.utils.logblt.AppLogger;

import java.io.IOException;

import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;

/**
 * Created by wanghelin on 2016/10/31.<br>
 * www.baolaitong.com
 */

public class LoggerInterceptor implements Interceptor {
    public static final String TAG = "OkHttpLog";
    private String tag;
    private boolean showResponse;

    public LoggerInterceptor(String tag, boolean showResponse) {
        if (TextUtils.isEmpty(tag)) {
            tag = TAG;
        }
        this.showResponse = showResponse;
        this.tag = tag;
    }

    public LoggerInterceptor(String tag) {
        this(tag, false);
    }

    public LoggerInterceptor() {
        this("", true);
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        logForRequest(request);
        Response response = chain.proceed(request);
        return logForResponse(response);
    }

    private Response logForResponse(Response response) {
        try {
            //===>response log
            AppLogger.d("========response'log=======");
            Response.Builder builder = response.newBuilder();
            Response clone = builder.build();
            AppLogger.i("lynet", "url: " + clone.request().url());
            AppLogger.d("url : " + clone.request().url());
            AppLogger.d("code : " + clone.code());
            AppLogger.d("protocol : " + clone.protocol());
            if (!TextUtils.isEmpty(clone.message()))
                AppLogger.d("message : " + clone.message());

            if (showResponse) {
                ResponseBody body = clone.body();
                if (body != null) {
                    MediaType mediaType = body.contentType();
                    if (mediaType != null) {
                        AppLogger.d("responseBody's contentType : " + mediaType.toString());
                        if (isText(mediaType)) {
                            String resp = body.string();
                            AppLogger.d("responseBody's content : " + resp);
                            body = ResponseBody.create(mediaType, resp);
//                            if (clone.request().url().toString().contains("getFundDoctorList")) {
//                                Debug.show(resp);
//                            }
                            return response.newBuilder().body(body).build();
                        } else {
                            AppLogger.d("responseBody's content : " + " maybe [file part] , too large too print , ignored!");
                        }
                    }
                }
            }

            AppLogger.d("========response'log=======end");
        } catch (Exception e) {
//            e.printStackTrace();
        }

        return response;
    }

    private void logForRequest(Request request) {
        try {
            String url = request.url().toString();
            Headers headers = request.headers();

            AppLogger.d("========request'log=======");
            AppLogger.d("method : " + request.method());
            AppLogger.d("url : " + url);
            if (headers != null && headers.size() > 0) {
                AppLogger.d("headers : " + headers.toString());
            }
            RequestBody requestBody = request.body();
            if (requestBody != null) {
                MediaType mediaType = requestBody.contentType();
                if (mediaType != null) {
                    AppLogger.d("requestBody's contentType : " + mediaType.toString());
                    if (isText(mediaType)) {
                        AppLogger.d("requestBody's content : " + bodyToString(request));
                        AppLogger.i("lynet", "request: " + bodyToString(request));
                    } else {
                        AppLogger.d("requestBody's content : " + " maybe [file part] , too large too print , ignored!");
                    }
                }
            }
            AppLogger.d("========request'log=======end");
        } catch (Exception e) {
//            e.printStackTrace();
        }
    }

    private boolean isText(MediaType mediaType) {
        if (mediaType.type() != null && mediaType.type().equals("text")) {
            return true;
        }
        if (mediaType.subtype() != null) {
            if (mediaType.subtype().equals("json") ||
                    mediaType.subtype().equals("xml") ||
                    mediaType.subtype().equals("html") ||
                    mediaType.subtype().equals("webviewhtml")
                    )
                return true;
        }
        return false;
    }

    private String bodyToString(final Request request) {
        try {
            final Request copy = request.newBuilder().build();
            final Buffer buffer = new Buffer();
            copy.body().writeTo(buffer);
            return buffer.readUtf8();
        } catch (final IOException e) {
            return "something error when show requestBody.";
        }
    }
}
