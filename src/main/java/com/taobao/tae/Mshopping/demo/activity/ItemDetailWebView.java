package com.taobao.tae.Mshopping.demo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.taobao.tae.Mshopping.demo.R;

/**
 * Created by huamulou on 14-8-29.
 */
public class ItemDetailWebView extends BaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.item_detail_webview_activity);
        WebView webView = (WebView) findViewById(R.id.webview);

        Intent intent = getIntent();
        String itemId = (String) intent.getExtras().get("itemId");

        webView.getSettings().setJavaScriptEnabled(true);//设置使用够执行JS脚本
        webView.getSettings().setBuiltInZoomControls(true);//设置使支持缩放
        webView.setWebViewClient(new WebViewClient());


        String url = "http://h5.m.taobao.com/awp/core/detail.htm?id=" + itemId;

        webView.loadUrl(url);
    }
}
