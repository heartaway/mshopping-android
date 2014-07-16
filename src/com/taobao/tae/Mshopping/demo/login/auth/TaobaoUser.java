package com.taobao.tae.Mshopping.demo.login.auth;

import java.io.Serializable;

/**
 * Created by xinyuan on 14/7/3.
 */
public class TaobaoUser implements Serializable {

    /**
     * 用户 昵称
     */
    public String nick;

    /**
     * 用户头像
     */
    public String avatar;

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
