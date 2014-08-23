package com.taobao.tae.Mshopping.demo.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import com.taobao.tae.Mshopping.demo.R;
import com.taobao.tae.Mshopping.demo.constant.Constants;
import com.taobao.tae.Mshopping.demo.constant.UmengAnalysis;
import com.umeng.analytics.MobclickAgent;

public class LoginActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.login_activity);
        bundle = getIntent().getExtras();
        checkNetwork();
        setListener();
        MobclickAgent.setDebugMode(UmengAnalysis.isOpenAnalyticsDebug);
    }


    private void setListener() {
        RelativeLayout backBtnLayout = (RelativeLayout) findViewById(R.id.login_top_back_btn);
        backBtnLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goBack();
            }
        });

        ImageButton qqLoginButton= (ImageButton) findViewById(R.id.login_qq_btn);
        qqLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, QQOAuthLoginActivity.class);
                if (bundle != null) {
                    intent.putExtras(bundle);
                }
                intent.putExtra("ACTIVITY_NAME_KEY", R.string.title_activity_login);
                startActivity(intent);
            }
        });

        ImageButton taobaoLoginButton= (ImageButton) findViewById(R.id.login_taobao_btn);
        taobaoLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, TaobaoOAuthLoginActivity.class);
                if (bundle != null) {
                    intent.putExtras(bundle);
                }
                intent.putExtra("ACTIVITY_NAME_KEY", R.string.title_activity_login);
                startActivity(intent);
            }
        });

        ImageButton weiboLoginButton= (ImageButton) findViewById(R.id.login_weibo_btn);
        weiboLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, WeiboOAuthLoginActivity.class);
                if (bundle != null) {
                    intent.putExtras(bundle);
                }
                intent.putExtra("ACTIVITY_NAME_KEY", R.string.title_activity_login);
                startActivity(intent);
            }
        });

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            goBack();
        }
        return false;
    }

    /**
     * 用户触发回退后操作，包括顶部回退&物理键回退
     */
    public void goBack() {
        this.finish();
    }


    public static String getDeviceInfo(Context context) {
        try{
            org.json.JSONObject json = new org.json.JSONObject();
            android.telephony.TelephonyManager tm = (android.telephony.TelephonyManager) context
                    .getSystemService(Context.TELEPHONY_SERVICE);

            String device_id = tm.getDeviceId();

            android.net.wifi.WifiManager wifi = (android.net.wifi.WifiManager) context.getSystemService(Context.WIFI_SERVICE);

            String mac = wifi.getConnectionInfo().getMacAddress();
            json.put("mac", mac);

            if( TextUtils.isEmpty(device_id) ){
                device_id = mac;
            }

            if( TextUtils.isEmpty(device_id) ){
                device_id = android.provider.Settings.Secure.getString(context.getContentResolver(),android.provider.Settings.Secure.ANDROID_ID);
            }

            json.put("device_id", device_id);

            return json.toString();
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
