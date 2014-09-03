package com.taobao.tae.Mshopping.demo.util;

import com.google.gson.Gson;

/**
 * mulou.zzy
 * taedemo
 */
public class GsonUtil {



    public static Gson g = new Gson();

    public final static String toJson(Object o){
        if(o == null){
            return null;
        }
        return g.toJson(o);
    }
}
