package com.tyxo.baseframelib.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import java.util.Set;

/**
 * SharedPreferences 工具类
 *
 * @author kkklaf
 */
public class SharedPreferencesUtil {
    /**
     * 上下文
     */
    static Context mContext;
    /**
     * sharedPreferences对象
     */
    static SharedPreferences mSharedPreferences;
    /**
     * sharedPreferences 的名字
     */
    static String mSharedName;

    static SharedPreferencesUtil mInstance;

    private SharedPreferencesUtil() {

    }

    /**
     * 构造一个shared preferences 实例
     *
     * @param context 上下文
     */
    public static SharedPreferencesUtil Build(Context context) {

        mContext = context;
        mSharedName = mContext.getPackageName();
        Build(context, mSharedName);
        return mInstance;
    }

    /**
     * 构造一个shared preferences 实例
     *
     * @param context    上下文
     * @param sharedName 名字
     */
    public static SharedPreferencesUtil Build(Context context, String sharedName) {
        if (mInstance == null) {
            mInstance = new SharedPreferencesUtil();
            mContext = context;
            mSharedPreferences = mContext.getSharedPreferences(sharedName,
                    Context.MODE_PRIVATE);
        }
        return mInstance;

    }

    /**
     * 放入整型值
     *
     * @param key   键
     * @param value 值
     */
    public void putIntValue(String key, int value) {
        Editor editor = mSharedPreferences.edit();
        editor.putInt(key, value);
        editor.commit();
    }

    /**
     * 取出制定键的整型值,不存在指定键则返回0
     *
     * @param key 键
     * @return 整型值
     */
    public int getIntValue(String key) {
        return mSharedPreferences.getInt(key, 0);
    }

    /**
     * 取出指定键的整型值
     *
     * @param key          键
     * @param defalutValue 默认值
     * @return 整型值
     */
    public int getIntValue(String key, int defalutValue) {
        return mSharedPreferences.getInt(key, defalutValue);
    }

    /**
     * 放入字符串值
     *
     * @param key   键
     * @param value 值
     */
    public void putStringValue(String key, String value) {
        Editor editor = mSharedPreferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    /**
     * 获取指定键的字符串值,若不存在则返回""
     *
     * @param key 制定键
     * @return 字符串值
     */
    public String getStringValue(String key) {
        return mSharedPreferences.getString(key, "");
    }

    /**
     * 获取指定键的字符串值,若不存在则返回默认值
     *
     * @param key          制定键
     * @param defalutValue 默认值
     * @return 字符串值
     */
    public String getStringValue(String key, String defalutValue) {
        return mSharedPreferences.getString(key, defalutValue);
    }

    /**
     * 放入Boolean值
     *
     * @param key   制定键
     * @param value Boolean值
     */
    public void putBooleanValue(String key, Boolean value) {
        Editor editor = mSharedPreferences.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    /**
     * 获取指定键的Boolean值，若不存在则返回false
     *
     * @param key 制定键
     * @return Boolean值
     */
    public Boolean getBooleanValue(String key) {
        return mSharedPreferences.getBoolean(key, false);
    }

    /**
     * 获取指定键的Boolean值，若不存在则返回默认值
     *
     * @param key          制定键
     * @param defalutValue 默认值
     * @return Boolean值
     */
    public Boolean getBooleanValue(String key, Boolean defalutValue) {
        return mSharedPreferences.getBoolean(key, defalutValue);
    }

    /**
     * 放入Long值
     *
     * @param key   指定键
     * @param value long值
     */
    public void putLongValue(String key, long value) {
        Editor editor = mSharedPreferences.edit();
        editor.putLong(key, value);
        editor.commit();
    }

    /**
     * 获取指定键对应的long值,若不存在则返回0
     *
     * @param key 指定键
     * @return long值
     */
    public long getLongValue(String key) {
        return mSharedPreferences.getLong(key, 0);
    }

    /**
     * 获取指定键对应的long值,若不存在则返回默认值
     *
     * @param key          指定键
     * @param defalutValue 默认值
     * @return long值
     */
    public long getLongValue(String key, long defalutValue) {
        return mSharedPreferences.getLong(key, defalutValue);
    }

    /**
     * 放入float型的键值对
     *
     * @param key   键
     * @param value 值
     */
    public void putFloatValue(String key, float value) {
        Editor editor = mSharedPreferences.edit();
        editor.putFloat(key, value);
        editor.commit();
    }

    /**
     * 获取指定键的float值，若不存在则返回0
     *
     * @param key 键
     * @return float值
     */
    public float getFloatValue(String key) {
        return mSharedPreferences.getFloat(key, 0);
    }

    /**
     * 获取指定键的float值，若不存在则返回默认值
     *
     * @param key          指定键
     * @param defalutValue ，默认值
     * @return
     */
    public float getFloatValue(String key, float defalutValue) {
        return mSharedPreferences.getFloat(key, defalutValue);
    }

    /**
     * 放入Set<String>类型的键值对,api-11
     *
     * @param key   键
     * @param value Set<String>值
     */
    @SuppressLint("NewApi")
    public void putStringSetValue(String key, Set<String> value) {
        Editor editor = mSharedPreferences.edit();
        editor.putStringSet(key, value);
        editor.commit();
    }

    /**
     * 获取指定键的Set<String>值，若不存在则返回null
     *
     * @param key 键
     * @return Set<String>值
     */
    @SuppressLint("NewApi")
    public Set<String> getStringSetValue(String key) {
        return mSharedPreferences.getStringSet(key, null);
    }

    /**
     * 获取指定键的Set<String>值，若不存在则返回默认值
     *
     * @param key          键
     * @param defalutValue 默认值
     * @return Set<String>值
     */
    @SuppressLint("NewApi")
    public Set<String> getStringSetValue(String key, Set<String> defalutValue) {
        return mSharedPreferences.getStringSet(key, defalutValue);
    }

    /**
     * 清除sharePrefrence数据
     */
    public void clear() {
        Editor editor = mSharedPreferences.edit();
        editor.clear();
        editor.commit();
    }
}
