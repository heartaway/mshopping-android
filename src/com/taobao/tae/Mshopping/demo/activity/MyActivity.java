package com.taobao.tae.Mshopping.demo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.alipay.android.mini.window.sdk.MiniWebActivity;
import com.taobao.tae.Mshopping.demo.MshoppingApplication;
import com.taobao.tae.Mshopping.demo.R;
import com.taobao.tae.Mshopping.demo.constant.UmengAnalysis;
import com.taobao.tae.Mshopping.demo.login.User;
import com.taobao.tae.Mshopping.demo.util.RemoteImageHelper;
import com.umeng.analytics.MobclickAgent;

public class MyActivity extends BaseActivity {

    private long firstClickBackTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_activity);
        initView();
        MobclickAgent.setDebugMode(UmengAnalysis.isOpenAnalyticsDebug);
    }

    private void initView() {
        if (!((MshoppingApplication) getApplication()).oAuthIsValid()) {

            Button loginButton = (Button) findViewById(R.id.my_login_btn);
            loginButton.setVisibility(View.VISIBLE);
            loginButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent();
                    Bundle bundle = new Bundle();
                    bundle.putInt("ACTIVITY_NAME_KEY", R.string.title_activity_my);
                    intent.putExtras(bundle);
                    intent.setClass(MyActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
            });
        } else {
            Button loginButton = (Button) findViewById(R.id.my_login_btn);
            loginButton.setVisibility(View.INVISIBLE);
            ImageView avatarImageView = (ImageView) findViewById(R.id.my_avatar_img);
            TextView nickTextView = (TextView) findViewById(R.id.my_nick_txt);
            User user = ((MshoppingApplication) getApplication()).getUser();
            RemoteImageHelper remoteImageHelper = new RemoteImageHelper();
            if (user != null) {
                remoteImageHelper.loadRoundImage(avatarImageView, user.getAvatar());
                nickTextView.setText(user.getNick());
            }
            setButtonListen();
        }
    }

    /**
     * 设置 按钮的监听器
     */
    public void setButtonListen() {
        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.personal_center_setting_layout);
        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MyActivity.this, SettingActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                intent.putExtra("ACTIVITY_NAME_KEY", R.string.title_activity_login);
                startActivity(intent);
            }
        });
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                long secondClickBackTime = System.currentTimeMillis();
                if (secondClickBackTime - firstClickBackTime > 2000) {
                    toast("再点一次，退出");
                    firstClickBackTime = secondClickBackTime;
                    return true;
                } else {
                    System.exit(0);
                }
                break;
        }
        return super.onKeyUp(keyCode, event);
    }

}
