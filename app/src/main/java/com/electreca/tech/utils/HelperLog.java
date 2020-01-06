package com.electreca.tech.utils;

import android.util.Log;

public class HelperLog {
    public static void printExceptionStack(Exception e) {
        e.printStackTrace();
    }

    public static void i(String key, String msg) {
        Log.i(key, msg);
    }

    public static void d(String key, String msg) {
        Log.d(key, msg);
    }

    public static void v(String key, String msg) {
        Log.v(key, msg);
    }

    public static void e(String key, String msg) {
        Log.e(key, msg);
    }



    public static void printExceptionStack(String tag, Exception e) {
        HelperLog.e(tag, Log.getStackTraceString(e));
    }

}
