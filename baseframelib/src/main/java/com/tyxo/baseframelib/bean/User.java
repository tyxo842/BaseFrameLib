package com.tyxo.baseframelib.bean;

import com.android.volley.VolleyError;

import java.io.Serializable;

/**
 * 用户实体
 *
 * @author DEV
 */
public class User implements Serializable {
    private static User mUser;
    private static Integer lock = 0;

    public static User getInstance() {
        if (mUser == null) {
            mUser = new User();
        }
        return mUser;
    }

    public interface AutoLoginCallback {
        void onSuccess();

        void onfailure(VolleyError volleyError);
    }

    private AutoLoginCallback mAutoLoginCallback;

    public static final String RELOGIN_ERROR = "error_relogin";

    public synchronized void autoLogin(AutoLoginCallback autoLoginCallback) {
//        new SharedPrefsCookiePersistor(BaseApplication.getInstance()).clear();
//        CookieJar cookieJar = OkHttpUtils.getInstance().getOkHttpClient().cookieJar();
//        if (cookieJar instanceof ClearableCookieJar) {
//            ((ClearableCookieJar) cookieJar).clear();
//        }
//        this.mAutoLoginCallback = autoLoginCallback;
//        if (AppUtil.getIsLogin(BaseApplication.getInstance())) {
//            if (mAutoLoginCallback != null) {
//                mAutoLoginCallback.onSuccess();
//            }
//            return;
//        }
//        Req130002 req10002 = new Req130002();
//        String userName = AppUtil.getLoginUserName(BaseApplication.getInstance());
//        String passWord = AppUtil.getLoginPassword(BaseApplication.getInstance());
//        if (TextUtils.isEmpty(userName) || TextUtils.isEmpty(passWord)) {
//            VolleyError error = new VolleyError("");
//            mAutoLoginCallback.onfailure(error);
//            return;
//        }
//        req10002.setUsername(AppUtil.getLoginUserName(BaseApplication.getInstance()));
//        req10002.setPassword(AppUtil.getLoginPassword(BaseApplication.getInstance()));
//        req10002.setVia(AppUtil.VIA);
//
//        String url = AppUtil.URL_130002;
//        final User currentUser = this;
//        VolleyUtil.getInstance(BaseApplication.getInstance()).doGsonObjRequestPost(url, req10002, BaseResponse.class,
//                new IVolleyListener<BaseResponse>() {
//
//                    @Override
//                    public void onResponse(BaseResponse response) {
//                        if ("0".equals(response.code)) {
//                            AppLogger.e("code=" + response.code + ",msg=" + response.message);
//                            AppUtil.putIsLogin(BaseApplication.getInstance(), true);
//                            if (mAutoLoginCallback != null) {
//                                mAutoLoginCallback.onSuccess();
//                            }
//                        } else {
//                            if (mAutoLoginCallback != null) {
//                                AppUtil.putIsLogin(BaseApplication.getInstance(), false);
//                                VolleyError error = new VolleyError(RELOGIN_ERROR);
//                                mAutoLoginCallback.onfailure(error);
//                            }
//                        }
//                        lock = 0;
////                            currentUser.notify();
//                    }
//
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        AppUtil.putIsLogin(BaseApplication.getInstance(), false);
//                        if (mAutoLoginCallback != null) {
//                            VolleyError e = new VolleyError(RELOGIN_ERROR);
//                            mAutoLoginCallback.onfailure(e);
//                        }
//                        lock = 0;
////                            currentUser.notify();
//                    }
//                });
    }
}

