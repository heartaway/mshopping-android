package com.taobao.tae.Mshopping.demo.login;

import android.content.Context;
import com.taobao.tae.Mshopping.demo.login.auth.AccessToken;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by xinyuan on 14/7/3.
 */
public class TopAndroidClient {
    private static final java.util.concurrent.ConcurrentHashMap<java.lang.String, com.taobao.tae.Mshopping.demo.login.TopAndroidClient> CLIENT_STORE = new ConcurrentHashMap<String, TopAndroidClient>();
    private static final java.lang.String OAUTH_CLIENT_ID = "client_id";
    private static final java.lang.String OAUTH_REDIRECT_URI = "redirect_uri";
    private static final java.lang.String OAUTH_CLIENT_SECRET = "client_secret";
    private static final java.lang.String OAUTH_REFRESH_TOKEN = "refresh_token";
    private static final java.lang.String SDK_TRACK_ID = "track-id";
    private static final java.lang.String SDK_DEVICE_UUID = "device-uuid";
    private static final java.lang.String SDK_TIMESTAMP = "timestamp";
    private static final java.lang.String SDK_CLIENT_SYSVERSION = "client-sysVersion";
    private static final java.lang.String SDK_CLIENT_SYSNAME = "client-sysName";
    private static final java.lang.String SDK_VERSION = "sdk-version";
    private static final java.lang.String SYS_NAME = "Android";
    private static final java.lang.String SESSION_DIR = "top.session";
    private static final java.lang.String LOG_TAG = "TopAndroidClient";
    private java.lang.String appKey;
    private java.lang.String appSecret;
    private java.lang.String redirectURI;
    private java.util.concurrent.ConcurrentHashMap<java.lang.Long, AccessToken> tokenStore;
    private android.content.Context context;
    private int connectTimeout;
    private int readTimeout;


    public static void registerAndroidClient(Context context, String appKey, String appSecret, String callBackUrl) {
        TopAndroidClient client = new TopAndroidClient();
        client.setContext(context);
        client.setAppKey(appKey);
        client.setAppSecret(appSecret);
        client.setRedirectURI(callBackUrl);
        CLIENT_STORE.put(appKey, client);
    }


    public static TopAndroidClient getAndroidClientByAppKey(String appKey) {
        if (CLIENT_STORE.containsKey(appKey)) {
            return CLIENT_STORE.get(appKey);
        } else {
            return null;
        }
    }

    public String getOauthUrl() {
        StringBuffer url = new StringBuffer("https://oauth.taobao.com/authorize?response_type=code");
        url.append("&client_id=");
        url.append(appKey);
        url.append("&redirect_uri=");
        url.append("urn:ietf:wg:oauth:2.0:oob");
        url.append("&view=wap");
        //每次授权时都强制用户输入用户名和密码
        url.append("&force_login=true");
        return url.toString();
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public String getAppSecret() {
        return appSecret;
    }

    public void setAppSecret(String appSecret) {
        this.appSecret = appSecret;
    }

    public String getRedirectURI() {
        return redirectURI;
    }

    public void setRedirectURI(String redirectURI) {
        this.redirectURI = redirectURI;
    }
}
