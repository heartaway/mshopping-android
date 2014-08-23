package com.taobao.tae.Mshopping.demo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.taobao.api.internal.util.WebUtils;
import com.taobao.tae.Mshopping.demo.MshoppingApplication;
import com.taobao.tae.Mshopping.demo.R;
import com.taobao.tae.Mshopping.demo.constant.Constants;
import com.taobao.tae.Mshopping.demo.constant.UmengAnalysis;
import com.taobao.tae.Mshopping.demo.login.LoginType;
import com.taobao.tae.Mshopping.demo.login.taobao.OAuthWebViewClient;
import com.taobao.tae.Mshopping.demo.login.taobao.TopAndroidClient;
import com.taobao.tae.Mshopping.demo.login.taobao.AccessToken;
import com.taobao.tae.Mshopping.demo.login.taobao.RefreshToken;
import com.taobao.tae.Mshopping.demo.login.taobao.TaobaoUser;
import com.umeng.analytics.MobclickAgent;
import com.umeng.analytics.MobclickAgentJSInterface;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 淘宝授权登录Activity
 * Created by xinyuan on 14/7/3.
 */
public class TaobaoOAuthLoginActivity extends BaseActivity {

    private TopAndroidClient client;
    private WebView webView;
    private OAuthWebViewClient webViewClient;
    private Bundle bundle;
    private AccessToken accessToken;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.taobao_login_activity);
        webView = (WebView) findViewById(R.id.tb_login_oauth_wv);
        new MobclickAgentJSInterface(this, webView);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setBuiltInZoomControls(true);
        webView.requestFocus();
        webViewClient = new OAuthWebViewClient();
        webView.setWebViewClient(webViewClient);
        TopAndroidClient.registerAndroidClient(this.getApplicationContext(), Constants.TAOBAO_APP_KEY, Constants.TAOBAO_APP_SECRET, "");
        client = TopAndroidClient.getAndroidClientByAppKey(Constants.TAOBAO_APP_KEY);
        InJavaScriptGetSource javaScriptGetSource = new InJavaScriptGetSource();
        webView.addJavascriptInterface(javaScriptGetSource, OAuthWebViewClient.jsObjectName);
        webView.loadUrl(client.getOauthUrl());
        addBtnListener();
        MobclickAgent.setDebugMode(UmengAnalysis.isOpenAnalyticsDebug);
    }

    public void addBtnListener() {
        RelativeLayout backRelativeLayout = (RelativeLayout) findViewById(R.id.tb_login_top_back_btn);
        backRelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    /**
     * 内部类 执行用户登录授权
     */
    public class InJavaScriptGetSource {

        private String authCode;

        /**
         * 通过webview源码，获取授权码
         *
         * @param html
         */
        public void showSource(String html) {
            if (html.indexOf("授权码获取成功") > 0) {
                int start = html.indexOf("<p>授权码：") + "<p>授权码：".length();
                int end = html.indexOf("</p>");
                String result = html.substring(start, end).trim();
                authCode = result;
                getOAuthUserInformation();
            }
            if (html.indexOf("出错") > 0) {
                Intent intent = new Intent(getApplicationContext(), TaobaoOAuthLoginActivity.class);
                intent.putExtras(bundle);
                intent.putExtra("ACTIVITY_NAME_KEY", R.string.title_activity_taobao_login);
                startActivity(intent);
                toast("您取消了登录");
            }
        }

        /**
         * 获取买家基本信息
         */
        public void getOAuthUserInformation() {
            String redirect_url = "urn:ietf:wg:oauth:2.0:oob";
            String tbPostSessionUrl = "https://oauth.taobao.com/token";  //获取访问令牌
            Map<String, String> param = new HashMap<String, String>();
            param.put("grant_type", "authorization_code");
            param.put("code", authCode);
            param.put("client_id", Constants.TAOBAO_APP_KEY);
            param.put("client_secret", Constants.TAOBAO_APP_SECRET);
            param.put("redirect_uri", redirect_url);
            param.put("scope", "item");
            param.put("view", "wap");
            param.put("state", "1212");
            try {
                int timeout = 30000;
                String response = WebUtils.doPost(tbPostSessionUrl, param, timeout, timeout);
                JSONObject ansyJson = new JSONObject(response);
                String _access_token = ansyJson.getString(AccessToken.KEY_ACCESS_TOKEN);

                String _access_user_url = "https://eco.taobao.com/router/rest?" +
                        "access_token=" + _access_token +
                        "&method=taobao.user.buyer.get&v=2.0&fields=user_id,nick,sex,birthday,avatar,vip_info&format=json";
                String _userJson = WebUtils.doGet(_access_user_url, new HashMap<String, String>());
                JSONObject ansyUserJson = new JSONObject(_userJson).getJSONObject("user_buyer_get_response").getJSONObject("user"); //JSON解析
                setAccessToken(ansyJson);
                setTaobaoUser(ansyUserJson);


                if (bundle != null && R.string.title_activity_item_detail == bundle.getInt("ACTIVITY_NAME_KEY")) {
                    Intent intent = new Intent(getApplicationContext(), ConfirmOrderActivity.class);
                    intent.putExtras(bundle);
                    intent.putExtra("ACTIVITY_NAME_KEY", R.string.title_activity_login);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                    if (bundle != null) {
                        intent.putExtras(bundle);
                    }
                    intent.putExtra("ACTIVITY_NAME_KEY", R.string.title_activity_taobao_login);
                    startActivity(intent);
                }

            } catch (Exception eg) {
                Log.e("", "OAuth json perse exception." + eg.getMessage());
            }
        }

        private void setAccessToken(JSONObject ansyJson) {
            try {
                RefreshToken refreshToken = new RefreshToken();
                refreshToken.setValue(ansyJson.getString(AccessToken.KEY_REFRESH_TOKEN));
                refreshToken.setReExpiresIn(ansyJson.getLong(AccessToken.KEY_RE_EXPIRES_IN));
                accessToken = new AccessToken();
                accessToken.setValue(ansyJson.getString(AccessToken.KEY_ACCESS_TOKEN));
                accessToken.setExpiresIn(ansyJson.getLong(AccessToken.KEY_EXPIRES_IN));
                accessToken.setStartDate(new Date());
                accessToken.setUserId(ansyJson.getLong(AccessToken.KEY_TAOBAO_USER_ID));
                accessToken.setTokenType(ansyJson.getString(AccessToken.KEY_TOKEN_TYPE));
                accessToken.setRefreshToken(refreshToken);
            } catch (JSONException eg) {
                Log.e("", "OAuth json perse exception." + eg.getMessage());
            }
        }


        private void setTaobaoUser(JSONObject ansyUserJson) {
            try {
                TaobaoUser taobaoUser = new TaobaoUser();
                taobaoUser.setAvatar(ansyUserJson.getString("avatar"));
                taobaoUser.setNick(ansyUserJson.getString("nick"));
                taobaoUser.setAccessToken(accessToken);
                ((MshoppingApplication) getApplication()).setLoginType(LoginType.TAOBAO.getType());
                ((MshoppingApplication) getApplication()).setUser(taobaoUser);
            } catch (JSONException eg) {
                Log.e("", "OAuth json perse exception." + eg.getMessage());
            }
        }
    }

    /**
     * 展示一个粉色的Toast
     *
     * @param message
     */
    public void toast(String message) {
        View toastRoot = findViewById(R.layout.toast);
        Toast toast = new Toast(this);
        toast.setView(toastRoot);
        TextView tv = (TextView) toastRoot.findViewById(R.id.pink_toast_notice);
        tv.setText(message);
        toast.show();
    }

    public Bundle getBundle() {
        return bundle;
    }

    public void setBundle(Bundle bundle) {
        this.bundle = bundle;
    }
}
