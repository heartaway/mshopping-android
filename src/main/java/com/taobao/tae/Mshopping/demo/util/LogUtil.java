package com.taobao.tae.Mshopping.demo.util;

import android.util.Log;

/**
 * mulou.zzy
 * taedemo
 */
public class LogUtil {

    private final static String PACKAGENAME = "com.taobao";

    public final static void e(String msg, Throwable e) {


        Log.e(PACKAGENAME, msg, e);
    }

    public final static void e(String msg) {


        Log.e(PACKAGENAME, msg);
    }


    public final static void w(String msg, Throwable e) {

        Log.w(PACKAGENAME, msg, e);
    }


    public final static void i(String msg) {

        Log.i(PACKAGENAME, msg);
    }
}
