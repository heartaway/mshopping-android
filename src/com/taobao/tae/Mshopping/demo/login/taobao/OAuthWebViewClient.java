package com.taobao.tae.Mshopping.demo.login.taobao;

import android.graphics.Bitmap;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Created by xinyuan on 14/7/3.
 */
public class OAuthWebViewClient extends WebViewClient {

    public static final String jsObjectName = "getsource";

    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        view.loadUrl(url);
        return true;
    }

    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        Log.d("WebView", "onPageStarted");
        super.onPageStarted(view, url, favicon);
    }

    /**
     * 页面加载完毕后执行预置JS代码
     *
     * @param view
     * @param url
     */
    public void onPageFinished(WebView view, String url) {
        Log.d("WebView", "onPageFinished ");
        view.loadUrl("javascript:window." + jsObjectName + ".showSource('<head>'+" +
                "document.getElementsByTagName('html')[0].innerHTML+'</head>');");
            super.onPageFinished(view, url);
    }
}
