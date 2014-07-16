package com.taobao.tae.Mshopping.demo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.taobao.tae.Mshopping.demo.MshoppingApplication;
import com.taobao.tae.Mshopping.demo.R;
import com.taobao.tae.Mshopping.demo.fegment.OAuthLoginFragment;
import com.taobao.tae.Mshopping.demo.fegment.PersonCenterFragment;
import com.taobao.tae.Mshopping.demo.util.NetWorkStateUtil;

public class PersonalActivity extends FragmentActivity {

    protected PersonCenterFragment personCenterFragment = new PersonCenterFragment();
    protected OAuthLoginFragment oAuthLoginFragment = new OAuthLoginFragment();
    private FragmentManager fragmentManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.personal_activity);
        fragmentManager = this.getSupportFragmentManager();
        Bundle bundle = getIntent().getExtras();
        if (!NetWorkStateUtil.isConnected(this)) {
            finish();
            toast("请检查网络连接");
        }
        if (((MshoppingApplication) this.getApplication()).oAuthIsExpire()) {
            oAuthLoginFragment.setBundle(bundle);
            taobaoOauthLogin();
        } else {
            initPersonCenter();
        }
        RelativeLayout backBtnLayout = (RelativeLayout) findViewById(R.id.tb_login_top_back_btn);
        backBtnLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goBack();
            }
        });
    }

    /**
     * 初始化个人中心
     */
    public void initPersonCenter() {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.personal_main_content, personCenterFragment);
        fragmentTransaction.commit();
    }

    /**
     * 进行淘宝登录授权
     */
    public void taobaoOauthLogin() {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.personal_main_content, oAuthLoginFragment);
        fragmentTransaction.commit();
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
        //Activity栈无数据时
        int fromActivity = getIntent().getIntExtra("ACTIVITY_NAME_KEY", 0);
        //用户退出登录后 点击顶部返回
        String action = getIntent().getStringExtra("action");
        Boolean toHomePage = false;
        if (fromActivity == 0) {
            toHomePage = true;
        }
        if ("logout".equals(action) && fromActivity == R.string.title_activity_setting) {
            toHomePage = true;
        }
        if (toHomePage) {
            Intent intent = new Intent();
            Bundle bundle = new Bundle();
            bundle.putInt("ACTIVITY_NAME_KEY", R.string.title_activity_personal);
            intent.putExtras(bundle);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.setClass(PersonalActivity.this, HomeActivity.class);
            startActivity(intent);
        }
        finish();
    }


    /**
     * 展示一个粉色的Toast
     *
     * @param message
     */
    public void toast(String message) {
        View toastRoot = LayoutInflater.from(this).inflate(R.layout.toast, null);
        Toast toast = new Toast(this);
        toast.setView(toastRoot);
        TextView tv = (TextView) toastRoot.findViewById(R.id.pink_toast_notice);
        tv.setText(message);
        toast.show();
    }

}
