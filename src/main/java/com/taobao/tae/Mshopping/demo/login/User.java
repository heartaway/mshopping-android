package com.taobao.tae.Mshopping.demo.login;

import java.io.Serializable;

/**
 * <p></p>
 * User: <a href="mailto:xinyuan.ymm@alibaba-inc.com">心远</a>
 * Date: 14/7/29
 * Time: 下午12:51
 */
public class User implements Serializable {

    /*用户头像 url*/
    private String avatar;

    /*用户昵称*/
    private String nick;

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
