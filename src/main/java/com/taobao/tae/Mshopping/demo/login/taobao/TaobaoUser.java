package com.taobao.tae.Mshopping.demo.login.taobao;

import com.taobao.tae.Mshopping.demo.login.User;

/**
 * Created by xinyuan on 14/7/3.
 */
public class TaobaoUser extends User {

    private AccessToken accessToken;

    public AccessToken getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(AccessToken accessToken) {
        this.accessToken = accessToken;
    }
}
