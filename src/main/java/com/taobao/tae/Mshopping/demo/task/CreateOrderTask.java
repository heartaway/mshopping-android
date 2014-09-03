package com.taobao.tae.Mshopping.demo.task;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.taobao.api.internal.util.WebUtils;
import com.taobao.tae.Mshopping.demo.MshoppingApplication;
import com.taobao.tae.Mshopping.demo.R;
import com.taobao.tae.Mshopping.demo.activity.ConfirmOrderActivity;
import com.taobao.tae.Mshopping.demo.activity.ItemDetailActivity;
import com.taobao.tae.Mshopping.demo.activity.PayOrderActivity;
import com.taobao.tae.Mshopping.demo.config.AppConfig;
import com.taobao.tae.Mshopping.demo.constant.Constants;
import com.taobao.tae.Mshopping.demo.login.LoginType;
import com.taobao.tae.Mshopping.demo.login.User;
import com.taobao.tae.Mshopping.demo.login.taobao.AccessToken;
import com.taobao.tae.Mshopping.demo.login.taobao.TaobaoUser;
import com.taobao.tae.Mshopping.demo.model.CreateOrderResp;
import com.taobao.tae.Mshopping.demo.model.ItemModel;
import com.taobao.tae.Mshopping.demo.model.ItemOrderModel;
import com.taobao.tae.Mshopping.demo.util.SecurityKey;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * 组件化构建订单
 * Created by xinyuan on 14/7/8.
 */
public class CreateOrderTask extends AsyncTask<String, Integer, CreateOrderResp> {

    private Context context;
    private AccessToken accessToken;
    private ArrayList<ItemModel> itemModels;
    private ItemOrderModel itemOrderModel;
    private RelativeLayout confirmOrdcerLayoutView;
    private ConfirmOrderActivity confirmOrderActivity;
    private String errorMessage;

    /**
     * 初始化 UpdateOrderTask
     *
     * @param context    为 ApplicationContext
     * @param itemModels
     */
    public CreateOrderTask(Context context, ArrayList<ItemModel> itemModels, RelativeLayout confirmOrdcerLayoutView, ConfirmOrderActivity confirmOrderActivity) {
        super();
        this.context = context;
        User user = ((MshoppingApplication) context).getUser();
        if(((MshoppingApplication) context).getLoginType() == LoginType.TAOBAO.getType()){
            this.accessToken = ((TaobaoUser)user).getAccessToken();
        }
        this.itemModels = itemModels;
        this.confirmOrdcerLayoutView = confirmOrdcerLayoutView;
        this.confirmOrderActivity = confirmOrderActivity;
    }

    @Override
    protected CreateOrderResp doInBackground(String... params) {
        try {
            String json = getCreateOrderResult(params[0]);
            return parseCreateOrderJSON(json);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(CreateOrderResp createOrderResp) {
        super.onPostExecute(createOrderResp);
        if (createOrderResp != null && createOrderResp.isSuccess()) {
            //跳转支付宝支付页面
            Intent intent = new Intent();
            intent.putExtra("createOrderResp", createOrderResp);
            if (itemModels != null && itemModels.size() > 0) {
                intent.putExtra("itemId", itemModels.get(0).getItemId().toString());
            }
            intent.putExtra("ACTIVITY_NAME_KEY", R.string.title_activity_confirm_order);
            intent.setClass(confirmOrderActivity, PayOrderActivity.class);
            confirmOrderActivity.startActivity(intent);
        } else {
            int fromActivity = confirmOrderActivity.getIntent().getIntExtra("ACTIVITY_NAME_KEY", 0);
            if (fromActivity == R.string.title_activity_login) {
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putString("itemId", itemModels.get(0).getItemId().toString());
                bundle.putInt("ACTIVITY_NAME_KEY", R.string.title_activity_confirm_order);
                intent.putExtras(bundle);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.setClass(confirmOrderActivity, ItemDetailActivity.class);
                confirmOrderActivity.startActivity(intent);
            }
            confirmOrderActivity.finish();
            if (errorMessage == null) {
                errorMessage = "提交订单失败";
            }
            toast(errorMessage);
        }
    }

    /**
     * 获取更新订单结果信息
     *
     * @return
     * @throws java.io.IOException
     */
    public String getCreateOrderResult(String submitJson) throws IOException {
        String result = "";
        String buildOrderUrl = AppConfig.getInstance().getServer() + "/api/order/createorder";
        int timeout = 30000;
        Map param = new HashMap<String, String>();
        param.put("securityKey", SecurityKey.getKey());
        param.put("sessionKey", accessToken.getValue());
        param.put("submitJson", submitJson);
        try {
            result = WebUtils.doPost(buildOrderUrl, param, timeout, timeout);
        } catch (IOException e) {
            Log.e("", e.getMessage());
        }
        return result;
    }

    /**
     * 解析 创建的订单 Json 结果
     *
     * @param json
     * @return
     * @throws java.io.IOException
     */
    public CreateOrderResp parseCreateOrderJSON(String json) throws IOException {
        CreateOrderResp createOrderResp = null;
        try {
            JSONObject jsonObject = new JSONObject(json);
            if (jsonObject.has("trade_tae_create_response") && jsonObject.getJSONObject("trade_tae_create_response").has("create_order_resp")) {
                JSONObject order = jsonObject.getJSONObject("trade_tae_create_response").getJSONObject("create_order_resp");
                createOrderResp = new CreateOrderResp();
                createOrderResp.setPayOrderId(order.getString("pay_order_id"));
                createOrderResp.setBizOrderId(order.getString("biz_order_id"));
                createOrderResp.setSuccess(order.getBoolean("is_success"));
            }
        } catch (Exception e) {
            Log.e("IOException is : ", e.toString());
            e.printStackTrace();
        }
        return createOrderResp;
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
