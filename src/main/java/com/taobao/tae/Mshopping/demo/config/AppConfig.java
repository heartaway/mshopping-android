package com.taobao.tae.Mshopping.demo.config;

import com.taobao.tae.Mshopping.demo.util.GsonUtil;

import java.lang.reflect.InvocationTargetException;

/**
 * Created by huamulou on 14-8-29.
 */
public class AppConfig {


    private static AppConfig instance;


    private String server;

    public void setServer(String server) {
        this.server = server;
    }

    public String getServer() {
        return server;
    }

    public static void init(String jsonString) throws InvocationTargetException, IllegalAccessException {
        if (instance == null) {
            instance = GsonUtil.g.fromJson(jsonString, AppConfig.class);
        }
    }


    public static AppConfig getInstance() {
        return instance;
    }
}
