package com.taobao.tae.Mshopping.demo.activity;

import android.content.Intent;
import android.os.Bundle;
import com.taobao.tae.Mshopping.demo.MshoppingApplication;
import com.taobao.tae.Mshopping.demo.R;
import com.taobao.tae.Mshopping.demo.constant.Constants;
import com.taobao.tae.Mshopping.demo.constant.UmengAnalysis;
import com.taobao.tae.Mshopping.demo.login.LoginType;
import com.taobao.tae.Mshopping.demo.login.qq.QQUser;
import com.tencent.connect.UserInfo;
import com.tencent.connect.auth.QQAuth;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;
import com.umeng.analytics.MobclickAgent;
import org.json.JSONException;
import org.json.JSONObject;


/**
 * 腾讯QQ授权登录Activity
 * Created by xinyuan on 14/7/3.
 */
public class QQOAuthLoginActivity extends BaseActivity {

    private static final String TAG = QQOAuthLoginActivity.class.getName();
    public static String mAppid;
    public static QQAuth mQQAuth;
    private UserInfo userInfo;
    private QQUser qqUser;
    private Tencent tencent;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAppid = Constants.QQ_APP_ID;
        mQQAuth = QQAuth.createInstance(mAppid, getApplicationContext());
        tencent = Tencent.createInstance(mAppid, QQOAuthLoginActivity.this);
        qqUser = new QQUser();
        login();
        MobclickAgent.setDebugMode(UmengAnalysis.isOpenAnalyticsDebug);
    }

    private void login() {
        if (!mQQAuth.isSessionValid()) {
            IUiListener listener = new BaseUiListener() {
                @Override
                protected void doComplete(JSONObject values) {
                    getUserInfo();
                }
            };
            mQQAuth.login(this, "all", listener);
            tencent.login(this, "all", listener);
        } else {
            mQQAuth.logout(this);
        }
    }


    /**
     * 获取用户基本信息
     */
    private void getUserInfo() {
        if (mQQAuth != null && mQQAuth.isSessionValid()) {

            IUiListener listener = new IUiListener() {

                @Override
                public void onError(UiError e) {
                }

                @Override
                public void onComplete(final Object response) {
                    JSONObject json = (JSONObject) response;
                    try {
                        if (json.has("nickname")) {
                            qqUser.setNick(json.getString("nickname"));
                        }
                        if (json.has("figureurl")) {
                            qqUser.setAvatar(json.getString("figureurl_qq_2"));
                        }
                        ((MshoppingApplication) getApplication()).setUser(qqUser);
                        ((MshoppingApplication) getApplication()).setLoginType(LoginType.QQ.getType());
                        Intent intent = new Intent();
                        Bundle bundle = new Bundle();
                        bundle.putInt("ACTIVITY_NAME_KEY", R.string.title_activity_qq_login);
                        intent.putExtras(bundle);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.setClass(QQOAuthLoginActivity.this, HomeActivity.class);
                        startActivity(intent);
                        toast("登录成功");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }

                @Override
                public void onCancel() {
                }
            };
            userInfo = new UserInfo(this, mQQAuth.getQQToken());
            userInfo.getUserInfo(listener);
            qqUser.setQqAuth(mQQAuth);
        }
    }

    private class BaseUiListener implements IUiListener {
        @Override
        public void onComplete(Object response) {
            doComplete((JSONObject) response);
        }

        protected void doComplete(JSONObject values) {
        }

        @Override
        public void onError(UiError uiError) {
            toast("QQ登录失败");
            finish();
        }

        @Override
        public void onCancel() {
            finish();
        }
    }

}
