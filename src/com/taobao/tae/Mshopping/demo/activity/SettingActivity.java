package com.taobao.tae.Mshopping.demo.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import com.taobao.tae.Mshopping.demo.MshoppingApplication;
import com.taobao.tae.Mshopping.demo.R;

public class SettingActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.system_setting);
        setButtonListener();
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

        /* 用户退出 */
        RelativeLayout logoutRelativeLayout = (RelativeLayout) findViewById(R.id.setting_logout_layout);
        logoutRelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MshoppingApplication) getApplication()).makeOAuthExpire();
                Intent intent = new Intent(getApplication(), PersonalActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("ACTIVITY_NAME_KEY", R.string.title_activity_setting);
                intent.putExtra("action", "logout");
                startActivity(intent);
                finish();
            }
        });
    }


}