package com.penner.architecture.util;

import android.util.Log;

/**
 * Created by penneryu on 16/9/12.
 */
public class LogUtils {

    public static void v(String tag, String msg) {
        if (Log.VERBOSE >= ConfigInfo.LOG_LEVEL) {
            Log.v(tag, msg);
        }
    }

    public static void d(String tag, String msg) {
        if (Log.DEBUG >= ConfigInfo.LOG_LEVEL) {
            Log.d(tag, msg);
        }
    }

    public static void i(String tag, String msg) {
        if (Log.INFO >= ConfigInfo.LOG_LEVEL) {
            Log.i(tag, msg);
        }
    }

    public static void w(String tag, String msg) {
        if (Log.WARN >= ConfigInfo.LOG_LEVEL) {
            Log.w(tag, msg);
        }
    }

    public static void e(String tag, String msg) {
        if (Log.ERROR >= ConfigInfo.LOG_LEVEL) {
            Log.e(tag, msg);
        }
    }

    public static void e(String tag, String msg, Throwable ex) {
        if (Log.ERROR >= ConfigInfo.LOG_LEVEL) {
            Log.e(tag, msg);
        }
    }
}
