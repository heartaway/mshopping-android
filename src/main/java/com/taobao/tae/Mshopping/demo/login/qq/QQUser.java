package com.taobao.tae.Mshopping.demo.login.qq;

import com.taobao.tae.Mshopping.demo.login.User;
import com.tencent.connect.UserInfo;
import com.tencent.connect.auth.QQAuth;

/**
 * <p>腾讯QQ用户基本信息</p>
 * User: <a href="mailto:xinyuan.ymm@alibaba-inc.com">心远</a>
 * Date: 14/7/29
 * Time: 下午12:16
 */
public class QQUser extends User {

    private QQAuth qqAuth;

    public QQAuth getQqAuth() {
        return qqAuth;
    }

    public void setQqAuth(QQAuth qqAuth) {
        this.qqAuth = qqAuth;
    }
}
