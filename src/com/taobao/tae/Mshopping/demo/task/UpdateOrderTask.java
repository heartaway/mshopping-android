package com.taobao.tae.Mshopping.demo.task;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.google.gson.Gson;
import com.taobao.api.internal.util.WebUtils;
import com.taobao.tae.Mshopping.demo.MshoppingApplication;
import com.taobao.tae.Mshopping.demo.R;
import com.taobao.tae.Mshopping.demo.activity.ConfirmOrderActivity;
import com.taobao.tae.Mshopping.demo.constant.Constants;
import com.taobao.tae.Mshopping.demo.login.LoginType;
import com.taobao.tae.Mshopping.demo.login.User;
import com.taobao.tae.Mshopping.demo.login.taobao.AccessToken;
import com.taobao.tae.Mshopping.demo.login.taobao.TaobaoUser;
import com.taobao.tae.Mshopping.demo.model.*;
import com.taobao.tae.Mshopping.demo.util.SecurityKey;
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
public class UpdateOrderTask extends AsyncTask<String, Integer, Boolean> {

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
    public UpdateOrderTask(Context context, ArrayList<ItemModel> itemModels, RelativeLayout confirmOrdcerLayoutView, ConfirmOrderActivity confirmOrderActivity) {
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
    protected Boolean doInBackground(String... params) {
        try {
            String json = getUpdateOrderResult();
            return parseUpdateOrderJSON(json);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    protected void onPostExecute(Boolean result) {
        super.onPostExecute(result);
        if (result) {
            updateView();
        } else {
            confirmOrderActivity.finish();
            toast(errorMessage);
        }
    }

    /**
     * 获取更新订单结果信息
     *
     * @return
     * @throws java.io.IOException
     */
    public String getUpdateOrderResult() throws IOException {
        String result = "";
        String buildOrderUrl = Constants.SERVER_DOMAIN + "/api/order/updateorder";
        int timeout = 30000;
        Map param = new HashMap<String, String>();
        param.put("securityKey", SecurityKey.getKey());
        param.put("sessionKey", accessToken.getValue());
        param.put("updateJson", "");
        try {
            result = WebUtils.doPost(buildOrderUrl, param, timeout, timeout);
        } catch (IOException e) {
            Log.e("", e.getMessage());
        }
        return result;
    }

    /**
     * 解析 订单Json 结果
     *
     * @param json
     * @return
     * @throws java.io.IOException
     */
    public Boolean parseUpdateOrderJSON(String json) throws IOException {
        Boolean result = false;
        try {
            JSONObject jsonObject = new JSONObject(json);

        } catch (Exception e) {
            Log.e("IOException is : ", e.toString());
            e.printStackTrace();
        }
        return result;
    }


    /**
     * 填充页面数据
     */
    public void updateView() {
        //填充 默认买家留言
        if (itemOrderModel.getLeaveMessage() != null) {
            TextView leaveMessageTextView = (TextView) confirmOrdcerLayoutView.findViewById(R.id.confirm_order_leave_message);
            String placeholder = itemOrderModel.getLeaveMessage().getFields().getPlaceholder();
            leaveMessageTextView.setHint(placeholder);
        }

        //填充 商品小计
        if (itemOrderModel.getItemPay() != null) {
            TextView singlePriceTextView = (TextView) confirmOrdcerLayoutView.findViewById(R.id.confirm_order_item_price_txt);
            singlePriceTextView.setText(itemOrderModel.getItemPay().getFields().getAfterPromotionPrice());
            TextView totalPriceTextView = (TextView) confirmOrdcerLayoutView.findViewById(R.id.confirm_order_total_price);
            totalPriceTextView.setText(itemOrderModel.getItemPay().getFields().getPrice());
        }
        //填充商品优惠
        if (itemOrderModel.getItemPay() != null) {
            TextView promotionTextView = (TextView) confirmOrdcerLayoutView.findViewById(R.id.confirm_order_promotion);
            String promotionPrice = itemOrderModel.getItemPromotion().getQuark();
            if (promotionPrice == null || promotionPrice == "") {
                promotionPrice = "0.00";
            }
            if (promotionPrice.startsWith("-")) {
                promotionPrice = promotionPrice.substring(1, promotionPrice.length());
            }
            promotionTextView.setText("为您节省了￥".concat(promotionPrice));
        }
        //填充 商品总计
        if (itemOrderModel.getOrderPay() != null) {
            TextView summaryPriceTextView = (TextView) confirmOrdcerLayoutView.findViewById(R.id.confirm_order_price_summary_value);
            summaryPriceTextView.setText(itemOrderModel.getOrderPay().getFields().getPrice());
        }

    }

    /**
     * 将 itemModels 转换为 taobao.trade.tae.build 接口需要的入参Json格式
     *
     * @param itemModels
     * @return
     */
    private String convertItemModelListToJson(ArrayList<ItemModel> itemModels) {
        if (itemModels == null) {
            return "{}";
        }
        Map map = new HashMap();
        map.put("items", itemModels);
        Gson gson = new Gson();
        gson.toJson(map);
        return gson.toJson(map);
    }


    /**
     * 将 Json 结构列表 从List 转换为 Map
     * 使用 value的 _ 之前的数据作为key，比如address_436168931 ，key＝address
     *
     * @param structure
     * @return
     */
    private Map convertStructureToMap(String structure) {
        structure = structure.replace("[", "");
        structure = structure.replace("]", "");
        structure = structure.replace("\"", "");
        Map map = new HashMap<String, String>();
        for (String value : Arrays.asList(structure.split(","))) {
            String key = "";
            if (value.contains("_")) {
                key = value.split("_")[0];
            } else {
                key = value;
            }
            map.put(key, value);
        }
        return map;
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
