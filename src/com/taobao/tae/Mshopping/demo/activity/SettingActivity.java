package com.taobao.tae.Mshopping.demo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.taobao.tae.Mshopping.demo.MshoppingApplication;
import com.taobao.tae.Mshopping.demo.R;
import com.taobao.tae.Mshopping.demo.constant.Constants;
import com.taobao.tae.Mshopping.demo.constant.UmengAnalysis;
import com.umeng.analytics.MobclickAgent;

public class SettingActivity extends BaseActivity {

    private MshoppingApplication mshoppingApplication;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.system_setting);
        mshoppingApplication = (MshoppingApplication)getApplication();
        setButtonListener();
        initView();
        MobclickAgent.setDebugMode(UmengAnalysis.isOpenAnalyticsDebug);
    }

    public void initView(){
        TextView loginoutView = (TextView)findViewById(R.id.setting_loginout_btn);
        if(mshoppingApplication.oAuthIsValid()){
            loginoutView.setText("退出登录");
        }
    }

    /**
     * 设置界面中按钮的监听器
     */
    public void setButtonListener() {
        /* 顶部回退按钮 */
        RelativeLayout backRelativeLayout = (RelativeLayout) findViewById(R.id.setting_top_back_btn);
        backRelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        /* 用户退出 或者登录 */
        RelativeLayout logoutRelativeLayout = (RelativeLayout) findViewById(R.id.setting_logout_layout);
        logoutRelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mshoppingApplication.oAuthIsValid()){
                    mshoppingApplication.makeOAuthExpire();
                    Intent intent = new Intent(getApplication(), HomeActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    intent.putExtra("ACTIVITY_NAME_KEY", R.string.title_activity_setting);
                    startActivity(intent);
                    finish();
                }else{
                    Intent intent = new Intent(getApplication(), LoginActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    intent.putExtra("ACTIVITY_NAME_KEY", R.string.title_activity_setting);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }


}