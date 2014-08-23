package com.taobao.tae.Mshopping.demo.activity;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.auth.WeiboAuth;
import com.sina.weibo.sdk.auth.WeiboAuthListener;
import com.sina.weibo.sdk.auth.sso.SsoHandler;
import com.sina.weibo.sdk.exception.WeiboException;
import com.taobao.tae.Mshopping.demo.MshoppingApplication;
import com.taobao.tae.Mshopping.demo.R;
import com.taobao.tae.Mshopping.demo.constant.Constants;
import com.taobao.tae.Mshopping.demo.constant.UmengAnalysis;
import com.taobao.tae.Mshopping.demo.login.LoginType;
import com.taobao.tae.Mshopping.demo.login.weibo.AccessTokenKeeper;
import com.taobao.tae.Mshopping.demo.login.weibo.WeiboUser;
import com.taobao.tae.Mshopping.demo.util.Helper;
import com.umeng.analytics.MobclickAgent;
import org.json.JSONObject;

import java.io.IOException;


/**
 * 新浪微博授权登录Activity
 * Created by xinyuan on 14/7/29.
 */
public class WeiboOAuthLoginActivity extends BaseActivity {

    private static final String TAG = WeiboOAuthLoginActivity.class.getName();

    /**
     * 微博 Web 授权类，提供登陆等功能
     */
    private WeiboAuth mWeiboAuth;

    /**
     * 封装了 "access_token"，"expires_in"，"refresh_token"，并提供了他们的管理功能
     */
    private Oauth2AccessToken mAccessToken;

    /**
     * 注意：SsoHandler 仅当 SDK 支持 SSO 时有效
     */
    private SsoHandler mSsoHandler;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weibo_login_activity);
        // 创建微博实例
        mWeiboAuth = new WeiboAuth(this, Constants.WEIBO_APP_KEY, Constants.WEIBO_REDIRECT_URL, Constants.WEIBO_SCOPE);
        // 通过单点登录 (SSO) 获取 Token
        mSsoHandler = new SsoHandler(WeiboOAuthLoginActivity.this, mWeiboAuth);
        mSsoHandler.authorize(new AuthListener());
        // 从 SharedPreferences 中读取上次已保存好 AccessToken 等信息，
        // 第一次启动本应用，AccessToken 不可用
        mAccessToken = AccessTokenKeeper.readAccessToken(this);
        if (mAccessToken.isSessionValid()) {
            //TODO
        }
        MobclickAgent.setDebugMode(UmengAnalysis.isOpenAnalyticsDebug);
    }

    /**
     * 当 SSO 授权 Activity 退出时，该函数被调用。
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (mSsoHandler != null) {
            mSsoHandler.authorizeCallBack(requestCode, resultCode, data);
        }
    }

    /**
     * 微博认证授权回调类。
     * 1. SSO 授权时，需要在 {@link #onActivityResult} 中调用 {@link SsoHandler#authorizeCallBack} 后，
     * 该回调才会被执行。
     * 2. 非 SSO 授权时，当授权结束后，该回调就会被执行。
     * 当授权成功后，请保存该 access_token、expires_in、uid 等信息到 SharedPreferences 中。
     */
    class AuthListener implements WeiboAuthListener {

        @Override
        public void onComplete(Bundle values) {
            // 从 Bundle 中解析 Token
            mAccessToken = Oauth2AccessToken.parseAccessToken(values);
            if (mAccessToken.isSessionValid()) {
                // 保存 Token 到 SharedPreferences
                AccessTokenKeeper.writeAccessToken(WeiboOAuthLoginActivity.this, mAccessToken);
                GetWeiboUserInfoTask getWeiboUserInfoTask = new GetWeiboUserInfoTask();
                getWeiboUserInfoTask.execute();
                finish();
            } else {
                String code = values.getString("code");
                finish();
                toast("授权失败");
            }
        }

        @Override
        public void onCancel() {
            finish();
            toast("取消登陆");
        }

        @Override
        public void onWeiboException(WeiboException e) {
            e.printStackTrace();
            finish();
            toast("授权失败");

        }
    }


    public class GetWeiboUserInfoTask extends AsyncTask<String, Integer, Boolean> {

        private Context context;

        @Override
        protected Boolean doInBackground(String... params) {
            try {
                String json = getWeiboUserInformation();
                return parseJSON(json);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return false;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);
        }

        /**
         * 获取用户基本信息
         */
        private String getWeiboUserInformation() {
            try {
                Oauth2AccessToken accessToken = AccessTokenKeeper.readAccessToken(WeiboOAuthLoginActivity.this);
                StringBuilder url = new StringBuilder("https://api.weibo.com/2/users/show.json");
                url.append("?access_token=");
                url.append(accessToken.getToken());
                url.append("&uid=");
                url.append(accessToken.getUid());
                String json = Helper.getStringFromUrl(url.toString());
                return json;
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }


        /**
         * 解析 Json 结果
         *
         * @param json
         * @return
         * @throws java.io.IOException
         */
        public Boolean parseJSON(String json) throws IOException {
            Boolean result = false;
            try {
                WeiboUser weiboUser = new WeiboUser();
                JSONObject jsonObject = new JSONObject(json);
                if(jsonObject.has("name")){
                    weiboUser.setNick(jsonObject.getString("name"));
                }
                if(jsonObject.has("profile_image_url")){
                    weiboUser.setAvatar(jsonObject.getString("profile_image_url"));
                }
                ((MshoppingApplication) getApplication()).setUser(weiboUser);
                ((MshoppingApplication) getApplication()).setLoginType(LoginType.WEIBO.getType());
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putInt("ACTIVITY_NAME_KEY", R.string.title_activity_weibo_login);
                intent.putExtras(bundle);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.setClass(WeiboOAuthLoginActivity.this, HomeActivity.class);
                startActivity(intent);
                toast("登录成功");
            } catch (Exception e) {
                Log.e("IOException is : ", e.toString());
                e.printStackTrace();
            }
            return result;
        }

    }
}
