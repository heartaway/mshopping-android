package com.taobao.tae.Mshopping.demo;

import android.app.Application;
import com.taobao.tae.Mshopping.demo.login.LoginType;
import com.taobao.tae.Mshopping.demo.login.User;
import com.taobao.tae.Mshopping.demo.login.qq.QQUser;
import com.taobao.tae.Mshopping.demo.login.taobao.TaobaoUser;
import com.taobao.tae.Mshopping.demo.login.weibo.AccessTokenKeeper;

/**
 * Created by xinyuan on 14/7/4.
 */
public class MshoppingApplication extends Application {

    /*用户选择的登陆方式*/
    private int loginType = 0;

    private User user;

    /**
     * 判断 授权（淘宝、QQ、微博）是否有效
     * 如果有效，返回true
     *
     * @return
     */
    public boolean oAuthIsValid() {
        if (loginType == 0) {
            return false;
        }
        if (loginType == LoginType.TAOBAO.getType()) {
            TaobaoUser taobaoUser = (TaobaoUser) user;
            Boolean isValid = taobaoUser.getAccessToken().isSessionValid();
            if (isValid) {
                return true;
            } else {
                return false;
            }
        }
        if (loginType == LoginType.QQ.getType()) {
            QQUser qqUser = (QQUser) user;
            Boolean isValid = qqUser.getQqAuth().isSessionValid();
            if (isValid) {
                return true;
            } else {
                return false;
            }
        }

        if (loginType == LoginType.WEIBO.getType()) {
            Boolean isValid = AccessTokenKeeper.readAccessToken(this).isSessionValid();
            if (isValid) {
                return true;
            } else {
                return false;
            }
        }

        return false;
    }

    /**
     * 清空本地OAuth信息
     */
    public void makeOAuthExpire() {
        user = null;
        loginType = 0;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getLoginType() {
        return loginType;
    }

    public void setLoginType(int loginType) {
        this.loginType = loginType;
    }
}
