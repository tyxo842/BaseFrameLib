package com.tyxo.baseframelib.utils;

import android.content.Context;

/**
 * Created by jsyfb-liy on 2017/11/14.
 */

public class AppUtil {

    /**
     * 版本号
     */
    public static final String SP_VERSION_CODE = "version_code";
    /**
     * sharePrefrence中存放的用户名的key
     */
    public static final String SP_USERNAME_KEY = "username_key";
    public static final String SP_USER_FULL_NAME_KEY = "user_full_name_key";
    /**
     * sharePrefrence中存放的用户名的key
     */
    public static final String SP_PASSWORD_KEY = "password_key";
    public static final String SP_USERID_KEY = "userid_key";
    public static final String SP_USER_AVATAR = "user_avatar";
    /**
     * 百度定位返回的对象
     */
    public static final String BD_LOCATION = "BDLocation";

    public static final String SP_COOKIE_KEY = "cookie_key";

    public static final String TRANS_SERIALIZABLE_RAISING_MONEY = "rasing_money";

    public static final String TRANS_SERIALIZABLE_RAISING_MONEY_EDIT = "rasing_money_edit";
    /**
     * 登录环信
     */
    public static final String IS_LOGIN_EASEMOB_CHAT = "is_login_easemob_chat";
    /**
     * 登录环信的密码
     */
    public static final String EM_CHAT_KEY = "EM_CHAT_KEY";
    /**
     * 昵称
     */
    public static final String FULL_NAME = "nick";

    /**
     * 用户的头像
     */
    public static final String HEADER_IMAGE = "avatar";
    public static final String SECRET_KEY = "GzbHMWaSnfWGgx040sTslznABqA2+z9KC969loDirerv7StIQsBMeg==";

    /**
     * 判断是否登陆
     *
     * @param context
     * @return
     */
    public static boolean getIsLogin(Context context) {
        boolean is_login = false;
        SharedPreferencesUtil util = SharedPreferencesUtil.Build(context);
        is_login = util.getBooleanValue("is_login", false);
        return is_login;
    }

    /**
     * 获取版本号
     *
     * @param context
     * @return
     */
    public static int getVersionCode(Context context) {
        SharedPreferencesUtil util = SharedPreferencesUtil.Build(context);
        return util.getIntValue(SP_VERSION_CODE, 0);

    }

    /**
     * 设置登陆后的用户名ID
     *
     * @param context
     * @param userId
     */
    public static void putLoginUserId(Context context, long userId) {
        SharedPreferencesUtil util = SharedPreferencesUtil.Build(context);
        util.putLongValue(SP_USERID_KEY, userId);
    }

    /**
     * 获取登陆后的用户名
     *
     * @return
     * @parajava.lang.Stringm context
     */
    public static long getLoginUserId(Context context) {
        SharedPreferencesUtil util = SharedPreferencesUtil.Build(context);
        return util.getLongValue(SP_USERID_KEY, 0);
    }

    /**
     * @param context
     * @param avatar
     */
    public static void putUserAvatar(Context context, String avatar) {
        SharedPreferencesUtil util = SharedPreferencesUtil.Build(context);
        util.putStringValue(SP_USER_AVATAR, avatar);
    }

    /**
     * @return
     * @parajava.lang.Stringm context
     */
    public static String getLoginAvatar(Context context) {
        SharedPreferencesUtil util = SharedPreferencesUtil.Build(context);
        return util.getStringValue(SP_USER_AVATAR);
    }

    /**
     * 设置登陆后的用户名
     *
     * @param context
     * @param userName
     */
    public static void putLoginUserName(Context context, String userName) {
        SharedPreferencesUtil util = SharedPreferencesUtil.Build(context);
        util.putStringValue(SP_USERNAME_KEY, userName);
    }

    /**
     * 获取登陆后的用户名
     *
     * @return
     */
    public static String getLoginUserFullName(Context context) {
        SharedPreferencesUtil util = SharedPreferencesUtil.Build(context);
        return util.getStringValue(SP_USER_FULL_NAME_KEY, "");
    }

    /**
     * 设置登陆后的用户名
     *
     * @param context
     * @param userFullName
     */
    public static void putLoginUserFullName(Context context, String userFullName) {
        SharedPreferencesUtil util = SharedPreferencesUtil.Build(context);
        util.putStringValue(SP_USER_FULL_NAME_KEY, userFullName);
    }

    /**
     * 获取登陆后的用户名
     *
     * @return
     * @parajava.lang.Stringm context
     */
    public static String getLoginUserName(Context context) {
        SharedPreferencesUtil util = SharedPreferencesUtil.Build(context);
        return util.getStringValue(SP_USERNAME_KEY, "");
    }


    /**
     * 设置登陆后的密码
     *
     * @param context
     * @param userName
     */
    public static void putLoginPassword(Context context, String userName) {
        SharedPreferencesUtil util = SharedPreferencesUtil.Build(context);
        util.putStringValue(SP_PASSWORD_KEY, userName);
    }

    /**
     * 获取登陆后的密码
     *
     * @param context
     * @return
     */
    public static String getLoginPassword(Context context) {

        SharedPreferencesUtil util = SharedPreferencesUtil.Build(context);
        return util.getStringValue(SP_PASSWORD_KEY, "");

    }

    /**
     * 是否登陆
     *
     * @param context
     * @param is_login
     */
    public static void putIsLogin(Context context, boolean is_login) {
        SharedPreferencesUtil util = SharedPreferencesUtil.Build(context);
        util.putBooleanValue("is_login", is_login);
    }

    /**
     * 保存版本号
     *
     * @param context
     * @param versionCode
     */
    public static void putVersionCode(Context context, int versionCode) {
        SharedPreferencesUtil util = SharedPreferencesUtil.Build(context);
        util.putIntValue(SP_VERSION_CODE, versionCode);
    }

    /**
     * 是否登录过环信的标志（存）
     *
     * @param context
     * @param isLoginChat
     */
    public static void putLoginChat(Context context, boolean isLoginChat) {
        SharedPreferencesUtil util = SharedPreferencesUtil.Build(context);
        util.putBooleanValue(IS_LOGIN_EASEMOB_CHAT, isLoginChat);
    }

    /**
     * 是否登录过环信的标志（取）
     *
     * @param context
     * @return
     */
    public static boolean isLoginChat(Context context) {
        SharedPreferencesUtil util = SharedPreferencesUtil.Build(context);
        return util.getBooleanValue(IS_LOGIN_EASEMOB_CHAT, false);
    }

    /**
     * 存储环信的密码
     *
     * @param context
     * @param emChatToken
     */
    public static void putEmChatToken(Context context, String emChatToken) {
        SharedPreferencesUtil util = SharedPreferencesUtil.Build(context);
        util.putStringValue(EM_CHAT_KEY, emChatToken);
    }

    /**
     * @param context
     * @return
     */
    public static String getEmChatToken(Context context) {
        SharedPreferencesUtil util = SharedPreferencesUtil.Build(context);
        return util.getStringValue(EM_CHAT_KEY);
    }

    /**
     * 获取用户的昵称
     *
     * @return
     */
    public static String getFullName(Context context) {
        SharedPreferencesUtil util = SharedPreferencesUtil.Build(context);
        return util.getStringValue(FULL_NAME);
    }

    /**
     * 设置用户的昵称
     *
     * @param context
     * @param fullName
     */
    public static void setFullName(Context context, String fullName) {
        SharedPreferencesUtil util = SharedPreferencesUtil.Build(context);
        util.putStringValue(FULL_NAME, fullName);
    }

    /**
     * 获取用户的头像
     *
     * @return
     */
    public static String getHeaderImage(Context context) {
        SharedPreferencesUtil util = SharedPreferencesUtil.Build(context);
        return util.getStringValue(HEADER_IMAGE);
    }

    /**
     * 设置用户的头像
     *
     * @param context
     * @param headerImage
     */
    public static void setHeaderImage(Context context, String headerImage) {
        SharedPreferencesUtil util = SharedPreferencesUtil.Build(context);
        util.putStringValue(HEADER_IMAGE, headerImage);
    }

    /**
     * @param time 闪屏图片更新时间
     */
    public static void saveUpDateTime(Context context, String time) {
        SharedPreferencesUtil util = SharedPreferencesUtil.Build(context);
        util.putStringValue("upDateTime", time);
    }

    /**
     * @param context
     * @return 闪屏图片更新时间
     */
    public static String getUpDateTime(Context context) {
        SharedPreferencesUtil util = SharedPreferencesUtil.Build(context);
        return util.getStringValue("upDateTime");
    }

    public static void saveProvinceId(Context context, String id) {
        SharedPreferencesUtil util = SharedPreferencesUtil.Build(context);
        util.putStringValue("provinceId", id);
    }

    public static void saveCityId(Context context, String id) {
        SharedPreferencesUtil util = SharedPreferencesUtil.Build(context);
        util.putStringValue("cityId", id);
    }

    public static void saveCountryId(Context context, String id) {
        SharedPreferencesUtil util = SharedPreferencesUtil.Build(context);
        util.putStringValue("countryId", id);
    }

    public static String getProvinceId(Context context) {
        SharedPreferencesUtil util = SharedPreferencesUtil.Build(context);
        return util.getStringValue("provinceId");
    }

    public static String getCityId(Context context) {
        SharedPreferencesUtil util = SharedPreferencesUtil.Build(context);
        return util.getStringValue("cityId");
    }

    public static String getCountryId(Context context) {
        SharedPreferencesUtil util = SharedPreferencesUtil.Build(context);
        return util.getStringValue("countryId");
    }

    public static void setBreakInfoTime(Context context, String time) {
        SharedPreferencesUtil util = SharedPreferencesUtil.Build(context);
        util.putStringValue("breakTime", time);
    }

    public static String getBreakInfoTime(Context context) {
        SharedPreferencesUtil util = SharedPreferencesUtil.Build(context);
        return util.getStringValue("breakTime");
    }

    public static void setBreakInfoUrl(Context context, String url) {
        SharedPreferencesUtil util = SharedPreferencesUtil.Build(context);
        util.putStringValue("breakUrl", url);
    }

    public static String getBreakInfoUrl(Context context) {
        SharedPreferencesUtil util = SharedPreferencesUtil.Build(context);
        return util.getStringValue("breakUrl");
    }
}
