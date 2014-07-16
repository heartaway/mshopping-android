package com.taobao.tae.Mshopping.demo.task;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.google.gson.Gson;
import com.taobao.api.internal.util.WebUtils;
import com.taobao.tae.Mshopping.demo.MshoppingApplication;
import com.taobao.tae.Mshopping.demo.R;
import com.taobao.tae.Mshopping.demo.activity.ConfirmOrderActivity;
import com.taobao.tae.Mshopping.demo.constant.Constants;
import com.taobao.tae.Mshopping.demo.login.auth.AccessToken;
import com.taobao.tae.Mshopping.demo.model.CreateOrderResp;
import com.taobao.tae.Mshopping.demo.model.ItemModel;
import com.taobao.tae.Mshopping.demo.model.ItemOrderModel;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * 组件化构建订单
 * Created by xinyuan on 14/7/8.
 */
public class GetPayOrderUrlTask extends AsyncTask<String, Integer, String> {

    private Context context;
    private CreateOrderResp createOrderResp;
    private WebView webView;

    /**
     * 初始化 UpdateOrderTask
     *
     * @param context         为 ApplicationContext
     * @param createOrderResp
     */
    public GetPayOrderUrlTask(Context context, CreateOrderResp createOrderResp, WebView webView) {
        super();
        this.context = context;
        this.createOrderResp = createOrderResp;
        this.webView = webView;
    }

    @Override
    protected String doInBackground(String... params) {
        try {
            String json = getAlipayOrderUrlResult();
            return parsePayOrderUrlJSON(json);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(String url) {
        super.onPostExecute(url);
        if (url != null) {
            webView.loadUrl(url);
        } else {
            toast("跳转支付宝失败");
        }
    }

    /**
     * 获取订单支付url
     *
     * @return
     * @throws java.io.IOException
     */
    public String getAlipayOrderUrlResult() throws IOException {
        String result = "";
        String payOrderUrl = Constants.SERVER_DOMAIN + "/api/order/getpayurl";
        int timeout = 30000;
        Map param = new HashMap<String, String>();
        param.put("tradeNos", createOrderResp.getPayOrderId());
        param.put("returnUrl", "http://m.taobao.com");
        try {
            result = WebUtils.doPost(payOrderUrl, param, timeout, timeout);
        } catch (IOException e) {
            Log.e("", e.getMessage());
        }
        return result;
    }

    /**
     * 解析 Json 结果
     *
     * @param json
     * @return
     * @throws java.io.IOException
     */
    public String parsePayOrderUrlJSON(String json) throws IOException {
        try {
            JSONObject jsonObject = new JSONObject(json);
            if (jsonObject.has("error_response")) {
                return null;
            }
            if (jsonObject.has("alibaba_tae_alipay_url_get_response")) {
                return jsonObject.getJSONObject("alibaba_tae_alipay_url_get_response").getString("value");
            }

        } catch (Exception e) {
            Log.e("IOException is : ", e.toString());
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 展示一个粉色的Toast
     *
     * @param message
     */
    public void toast(String message) {
        View toastRoot = LayoutInflater.from(context).inflate(R.layout.toast, null);
        Toast toast = new Toast(context);
        toast.setView(toastRoot);
        TextView tv = (TextView) toastRoot.findViewById(R.id.pink_toast_notice);
        tv.setText(message);
        toast.show();
    }
}
