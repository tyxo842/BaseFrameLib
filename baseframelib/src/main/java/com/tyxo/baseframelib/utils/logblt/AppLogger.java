package com.tyxo.baseframelib.utils.logblt;

import android.text.TextUtils;

/**
 * Wrapper API for sending log output.
 */
public class AppLogger {
    protected static final String TAG = "TYXO";
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
        if (DEBUG_MODE) {
            if (msg.length() > 4000) {
                for (int i = 0; i < msg.length(); i += 4000) {
                    if (i + 4000 < msg.length()) {
                        android.util.Log.d(tag, msg.substring(i, i + 4000));
                    } else {
                        android.util.Log.d(tag, msg.substring(i, msg.length()));
                    }
                }
            } else {
                android.util.Log.d(tag, msg);
            }
        }
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

    public static void i(String tag, String msg) {  //信息太长,分段打印
        if (DEBUG_MODE) {
            if (msg.length() > 4000) {
                for (int i = 0; i < msg.length(); i += 4000) {
                    if (i + 4000 < msg.length()) {
                        android.util.Log.i(tag, msg.substring(i, i + 4000));
                    } else {
                        android.util.Log.i(tag, msg.substring(i, msg.length()));
                    }
                }
            } else {
                android.util.Log.i(tag, msg);
            }
        }
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
