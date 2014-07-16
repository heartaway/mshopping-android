package com.taobao.tae.Mshopping.demo.activity;

import android.app.Activity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.taobao.tae.Mshopping.demo.R;

/**
 * Created by xinyuan on 14/7/16.
 */
public class BaseActivity extends Activity {

    /**
     * 展示一个粉色的Toast
     *
     * @param message
     */
    public void toast(String message) {
        View toastRoot = getLayoutInflater().inflate(R.layout.toast, null);
        Toast toast = new Toast(getApplicationContext());
        toast.setView(toastRoot);
        TextView tv = (TextView) toastRoot.findViewById(R.id.pink_toast_notice);
        tv.setText(message);
        toast.show();
    }
}
