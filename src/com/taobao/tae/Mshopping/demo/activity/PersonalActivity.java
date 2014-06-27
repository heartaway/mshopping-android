package com.taobao.tae.Mshopping.demo.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.taobao.tae.Mshopping.demo.R;

public class PersonalActivity extends Activity {

	
	WebView webview;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.personal_activity);
		webview = (WebView) findViewById(R.id.my_webview);
		WebSettings wSet = webview.getSettings();
		wSet.setJavaScriptEnabled(true);
		// 如果页面中链接，如果希望点击链接继续在当前browser中响应，
		// 而不是新开Android的系统browser中响应该链接，必须覆盖webview的WebViewClient对象
		webview.setWebViewClient(new WebViewClient() {
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				// 重写此方法表明点击网页里面的链接还是在当前的webview里跳转，不跳到浏览器那边
				view.loadUrl(url);
				return true;
			}
		});
		webview.loadUrl("http://my.m.taobao.com/myTaobao.htm");

	}



}
