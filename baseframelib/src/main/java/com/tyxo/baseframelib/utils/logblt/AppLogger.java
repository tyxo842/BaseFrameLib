package com.tyxo.baseframelib.utils.logblt;

import android.text.TextUtils;
import android.util.Log;

/**
 * Wrapper API for sending log output.
 */
public class AppLogger {
    protected static final String TAG = "HXXT";
    public static final boolean DEBUG_MODE = true;

    public AppLogger() {
    }

    /**
     * Send a VERBOSE log message.
     *
     * @param msg The message you would like logged.
     */
    public static void v(String msg) {
        if (DEBUG_MODE) {
            android.util.Log.v(TAG, buildMessage(msg));
        }
    }

    /**
     * Send a VERBOSE log message and log the exception.
     *
     * @param msg The message you would like logged.
     * @param thr An exception to log
     */
    public static void v(String msg, Throwable thr) {
        if (DEBUG_MODE) {
            android.util.Log.v(TAG, buildMessage(msg), thr);
        }
    }

    /**
     * Send a DEBUG log message.
     *
     * @param msg
     */
    public static void d(String msg) {
        if (DEBUG_MODE) {
            android.util.Log.d(TAG, buildMessage(msg));
        }
    }

    public static void d(String tag, String msg) {  //信息太长,分段打印
        //因为String的length是字符数量不是字节数量所以为了防止中文字符过多，
        //  把4*1024的MAX字节打印长度改为2001字符数
        int max_str_length = 2001 - tag.length();
        //大于4000时
        while (msg.length() > max_str_length) {
            Log.d(tag, msg.substring(0, max_str_length));
            msg = msg.substring(max_str_length);
        }
        //剩余部分
        Log.d(tag, msg);
    }

    /**
     * Send a DEBUG log message and log the exception.
     *
     * @param msg The message you would like logged.
     * @param thr An exception to log
     */
    public static void d(String msg, Throwable thr) {
        if (DEBUG_MODE) {
            android.util.Log.d(TAG, buildMessage(msg), thr);
        }
    }

    /**
     * Send an INFO log message.
     *
     * @param msg The message you would like logged.
     */
    public static void i(String msg) {
        if (DEBUG_MODE) {
            android.util.Log.i(TAG, buildMessage(msg));
        }
    }

    public static void i(String tag, String msg) {
        if (DEBUG_MODE) {
            /*if (msg.length() > LENGTH) {//信息太长,分段打印,中间会缺失
                for (int i = 0; i < msg.length(); i += LENGTH) {
                    if (i + LENGTH < msg.length()) {
                        android.util.Log.i(tag, msg.substring(i, i + LENGTH));
                    } else {
                        android.util.Log.i(tag, msg.substring(i, msg.length()));
                    }
                }
            } else {
                android.util.Log.i(tag, msg);
            }*/
            largeLog(tag, msg);
        }
    }

    public static void largeLog(String tag, String str) {
        //因为String的length是字符数量不是字节数量所以为了防止中文字符过多，
        //  把4*1024的MAX字节打印长度改为2001字符数
        int max_str_length = 2001 - tag.length();
        //大于4000时
        while (str.length() > max_str_length) {
            Log.i(tag, str.substring(0, max_str_length));
            str = str.substring(max_str_length);
        }
        //剩余部分
        Log.i(tag, str);
    }

    /**
     * Send a INFO log message and log the exception.
     *
     * @param msg The message you would like logged.
     * @param thr An exception to log
     */
    public static void i(String msg, Throwable thr) {
        if (DEBUG_MODE) {
            android.util.Log.i(TAG, buildMessage(msg), thr);
        }
    }

    /**
     * Send an ERROR log message.
     *
     * @param msg The message you would like logged.
     */
    public static void e(String msg) {
        if (DEBUG_MODE) {
            android.util.Log.e(TAG, buildMessage(msg));
        }
    }

    /**
     * Send a WARN log message
     *
     * @param msg The message you would like logged.
     */
    public static void w(String msg) {
        if (DEBUG_MODE) {
            android.util.Log.w(TAG, buildMessage(msg));
        }
    }

    /**
     * Send a WARN log message and log the exception.
     *
     * @param msg The message you would like logged.
     * @param thr An exception to log
     */
    public static void w(String msg, Throwable thr) {
        if (DEBUG_MODE) {
            android.util.Log.w(TAG, buildMessage(msg), thr);
        }
    }

    /**
     * Send an empty WARN log message and log the exception.
     *
     * @param thr An exception to log
     */
    public static void w(Throwable thr) {
        if (DEBUG_MODE) {
            android.util.Log.w(TAG, buildMessage(""), thr);
        }
    }

    /**
     * Send an ERROR log message and log the exception.
     *
     * @param msg The message you would like logged.
     * @param thr An exception to log
     */
    public static void e(String msg, Throwable thr) {
        if (DEBUG_MODE) {
            android.util.Log.e(TAG, buildMessage(msg), thr);
        }
    }

    /**
     * Building Message
     *
     * @param msg The message you would like logged.
     * @return Message String
     */
    protected static String buildMessage(String msg) {
        if (TextUtils.isEmpty(msg)) {
            return "failed null";
        }
        StackTraceElement caller = new Throwable().fillInStackTrace()
                .getStackTrace()[2];

        return new StringBuilder()
                // .append(".")
                // .append(caller.getClassName())
                .append(caller.getMethodName()).append("(): ").append(msg)
                .toString();
    }
}
