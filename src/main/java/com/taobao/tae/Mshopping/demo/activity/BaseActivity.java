package com.taobao.tae.Mshopping.demo.activity;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.taobao.tae.Mshopping.demo.R;
import com.taobao.tae.Mshopping.demo.util.NetWorkStateUtil;
import com.umeng.analytics.MobclickAgent;

/**
 * Created by xinyuan on 14/7/16.
 */
public class BaseActivity extends Activity {



    protected Bundle bundle;


    /**
     * 检查网络连接
     */
    protected void checkNetwork(){
        if (!NetWorkStateUtil.isConnected(getApplicationContext())) {
            finish();
            toast("请检查网络连接");
        }
    }

    /**
     * 展示一个粉色的Toast
     *
     * @param message
     */
    protected void toast(String message) {
        View toastRoot = getLayoutInflater().inflate(R.layout.toast, null);
        Toast toast = new Toast(getApplicationContext());
        toast.setView(toastRoot);
        TextView tv = (TextView) toastRoot.findViewById(R.id.pink_toast_notice);
        tv.setText(message);
        toast.show();
    }

    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }
    public  void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    public Bundle getBundle() {
        return bundle;
    }

    public void setBundle(Bundle bundle) {
        this.bundle = bundle;
    }
}
