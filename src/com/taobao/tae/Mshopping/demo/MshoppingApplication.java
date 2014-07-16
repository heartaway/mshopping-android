package com.taobao.tae.Mshopping.demo;

import android.app.Application;
import com.taobao.tae.Mshopping.demo.login.auth.AccessToken;
import com.taobao.tae.Mshopping.demo.login.auth.TaobaoUser;

/**
 * Created by xinyuan on 14/7/4.
 */
public class MshoppingApplication extends Application {

    /*用户授权信息*/
    private AccessToken accessToken;
    /*登录用户基本信息*/
    private TaobaoUser taobaoUser;

    public AccessToken getAccessToken() {
        return accessToken;
    }

    /**
     * 判断 授权是否过期
     * 如果已过期，返回true
     *
     * @return
     */
    public boolean oAuthIsExpire() {
        if (accessToken == null) {
            return true;
        } else {
            return accessToken.isExpire();
        }
    }

    /**
     * 清空本地OAuth信息
     */
    public void makeOAuthExpire() {
        accessToken = null;
        taobaoUser = null;
    }

    public void setAccessToken(AccessToken accessToken) {
        this.accessToken = accessToken;
    }

    public TaobaoUser getTaobaoUser() {
        return taobaoUser;
    }

    public void setTaobaoUser(TaobaoUser taobaoUser) {
        this.taobaoUser = taobaoUser;
    }
}
