package com.taobao.tae.Mshopping.demo.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import com.taobao.tae.Mshopping.demo.R;

public class ItemDetailActivity extends Activity implements OnClickListener{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.item_detail);

		Intent intent = getIntent();
		String url = intent.getStringExtra("url");
	}
	
	public void onClick(View paramView) {

	}

}
