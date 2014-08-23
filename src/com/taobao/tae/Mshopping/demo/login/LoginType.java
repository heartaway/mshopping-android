package com.taobao.tae.Mshopping.demo.login;

/**
 * <p>用户选择的登陆方式</p>
 * User: <a href="mailto:xinyuan.ymm@alibaba-inc.com">心远</a>
 * Date: 14/7/29
 * Time: 上午11:47
 */
public enum LoginType {

    TAOBAO(1, "淘宝账号登陆"),
    WEIBO(2, "微博账号登陆"),
    QQ(3, "腾讯QQ账号登陆");

    private int type;
    private String description;

    LoginType(int type, String description) {
        this.type = type;
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public int getType() {
        return type;
    }

}
