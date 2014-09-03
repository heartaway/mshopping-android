package com.taobao.tae.Mshopping.demo.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.alipay.android.app.pay.PayTask;
import com.alipay.android.app.pay.PayTask.OnPayListener;
import com.taobao.tae.Mshopping.demo.R;

public class PayActivity extends BaseActivity {

    /*订单编号*/
    private final String tradeNos = "904684e85b0a404f55c8dfa668df174e";

    /*淘宝创建订单时返回的Token值*/
    private final String externToken = "732718646535091";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.msp_demo_main);
        Button pay = (Button) findViewById(R.id.pay);
        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pay(view);
            }
        });
    }

    // 支付接口调用
    public void pay(View v) {
        try {
            PayTask payTask = new PayTask(PayActivity.this, new OnPayListener() {

                public void onPaySuccess(Context context, String resultStatus,
                                         String memo, String result) {
                }

                public void onPayFailed(Context context, String resultStatus,
                                        String memo, String result) {
                }
            });
            payTask.pay((getTaobaoOrderInfo(tradeNos, externToken, "PARTNER_TAOBAO_ORDER")));
        } catch (Exception e) {
            e.printStackTrace();

            if (e instanceof IllegalArgumentException)
                Toast.makeText(PayActivity.this, e.getMessage(),
                        Toast.LENGTH_SHORT).show();
        }
    }


    private String getTaobaoOrderInfo(String tradeNos, String externToken,
                                      String partner) {
        StringBuilder sb = new StringBuilder("trade_no=\"").append(tradeNos)
                .append("\"&extern_token=\"").append(externToken)
                .append("\"&partner=\"").append(partner).append("\"");
        return sb.toString();
    }


}
