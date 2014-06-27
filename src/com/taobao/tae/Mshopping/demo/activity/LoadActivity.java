package com.taobao.tae.Mshopping.demo.activity;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import com.taobao.tae.Mshopping.demo.R;
import com.taobao.tae.Mshopping.demo.base.BaseActivity;

public class LoadActivity extends BaseActivity {
    private ImageView imageView = null;

    //进度条对话框 下载新apk
    private ProgressDialog progressDialog;

    private static final String TAG = "Security";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.load_activity);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        findViewById();
        initView();
        //设计一个进度条
        progressDialog = new ProgressDialog(this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        //延迟3秒钟
        new Thread() {
            public void run() {
                try {
                    sleep(3000);
                    loadHomeActivity();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            ;
        }.start();
    }


    protected void findViewById() {
        imageView = (ImageView) findViewById(R.id.splash_loading_item);
    }

    @Override
    protected void initView() {
        Animation translate = AnimationUtils.loadAnimation(this,
                R.anim.splash_loading);

        translate.setAnimationListener(new AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                // TODO Auto-generated method stub
            }

        });
        imageView.setAnimation(translate);

    }

    //跳转 到 首页
    private void loadHomeActivity() {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
        finish();
    }


}
