package com.taobao.tae.Mshopping.demo.task;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.google.gson.Gson;
import com.taobao.api.internal.util.WebUtils;
import com.taobao.tae.Mshopping.demo.MshoppingApplication;
import com.taobao.tae.Mshopping.demo.R;
import com.taobao.tae.Mshopping.demo.activity.ConfirmOrderActivity;
import com.taobao.tae.Mshopping.demo.activity.ItemDetailActivity;
import com.taobao.tae.Mshopping.demo.config.AppConfig;
import com.taobao.tae.Mshopping.demo.constant.Constants;
import com.taobao.tae.Mshopping.demo.login.LoginType;
import com.taobao.tae.Mshopping.demo.login.User;
import com.taobao.tae.Mshopping.demo.login.taobao.AccessToken;
import com.taobao.tae.Mshopping.demo.login.taobao.TaobaoUser;
import com.taobao.tae.Mshopping.demo.model.*;
import com.taobao.tae.Mshopping.demo.util.RemoteImageHelper;
import com.taobao.tae.Mshopping.demo.util.SecurityKey;
import org.json.JSONObject;

import java.io.IOException;
import java.util.*;

/**
 * 组件化构建订单
 * Created by xinyuan on 14/7/8.
 */
public class BuildOrderTask extends AsyncTask<String, Integer, Boolean> {

    private Context context;
    private AccessToken accessToken;
    private ArrayList<ItemModel> itemModels;
    private ItemOrderModel itemOrderModel;
    private RelativeLayout confirmOrdcerLayoutView;
    private ConfirmOrderActivity confirmOrderActivity;
    private String errorMessage;
    private RemoteImageHelper remoteImageHelper = new RemoteImageHelper();

    /**
     * 初始化 BuildOrderTask
     *
     * @param context    为 ApplicationContext
     * @param itemModels
     */
    public BuildOrderTask(Context context, ArrayList<ItemModel> itemModels, RelativeLayout confirmOrdcerLayoutView, ConfirmOrderActivity confirmOrderActivity) {
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
            String json = getBuildOrderResult();
            return parseBuildOrderJSON(json);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    protected void onPostExecute(Boolean result) {
        super.onPostExecute(result);
        if (result) {
            initView();
        } else {
            int fromActivity = confirmOrderActivity.getIntent().getIntExtra("ACTIVITY_NAME_KEY", 0);
            if (fromActivity == R.string.title_activity_login) {
                //来自 淘宝登录后的跳转，则直接返回商品详情页，而不是上一级
                Intent intent = new Intent(confirmOrderActivity, ItemDetailActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("itemId", itemModels.get(0).getItemId().toString());
                confirmOrderActivity.startActivity(intent);
            } else {
                confirmOrderActivity.finish();
            }
            toast(errorMessage);
        }
    }

    /**
     * 获取构建的订单结果信息
     *
     * @return
     * @throws IOException
     */
    public String getBuildOrderResult() throws IOException {
        String result = "";
        String buildOrderUrl = AppConfig.getInstance().getServer() + "/api/order/buildorder";
        int timeout = 30000;
        Map param = new HashMap<String, String>();
        param.put("securityKey", SecurityKey.getKey());
        param.put("sessionKey", accessToken.getValue());
        param.put("itemsJson", convertItemModelListToJson(itemModels));
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
     * @throws IOException
     */
    public Boolean parseBuildOrderJSON(String json) throws IOException {
        Boolean result = false;
        try {
            itemOrderModel = new ItemOrderModel();
            Map<String, String> rootStructureMap = new HashMap<String, String>();
            Map<String, String> orderStructureMap = new HashMap<String, String>();
            Map<String, String> itemStructureMap = new HashMap<String, String>();
            JSONObject jsonObject = new JSONObject(json);

            if (jsonObject.has("hierarchy") && jsonObject.getJSONObject("hierarchy").has("structure")) {
                String rootKey = jsonObject.getJSONObject("hierarchy").getString("root");
                String rootStructure = jsonObject.getJSONObject("hierarchy").getJSONObject("structure").getString(rootKey);
                rootStructureMap = convertStructureToMap(rootStructure);
                itemOrderModel.setRootStructureMap(rootStructureMap);
                if (rootStructureMap.containsKey("order")) {
                    String orderStructure = jsonObject.getJSONObject("hierarchy").getJSONObject("structure").getString(rootStructureMap.get("order"));
                    orderStructureMap = convertStructureToMap(orderStructure);
                    itemOrderModel.setOrderStructureMap(orderStructureMap);
                    if (orderStructureMap.containsKey("item")) {
                        String itemStructure = jsonObject.getJSONObject("hierarchy").getJSONObject("structure").getString(orderStructureMap.get("item"));
                        itemStructureMap = convertStructureToMap(itemStructure);
                        itemOrderModel.setItemStructureMap(itemStructureMap);
                    }
                } else if (rootStructureMap.containsKey("invalidGroup")) {
                    errorMessage = "商品已下架 或 数量不足";
                    return result;
                }
            }

            if (jsonObject.has("linkage")) {
                itemOrderModel.setLinkage(jsonObject.getString("linkage"));
            }

            if (jsonObject.has("data")) {
                JSONObject dataJsonObj = jsonObject.getJSONObject("data");

                //收货地址
                if (rootStructureMap.containsKey("address") && dataJsonObj.has(rootStructureMap.get("address"))) {
                    JSONObject addressJsonObj = dataJsonObj.getJSONObject(rootStructureMap.get("address"));
                    Address address = new Address(addressJsonObj);
                    itemOrderModel.setAddress(address);
                }
                //配送方式
                if (orderStructureMap.containsKey("deliveryMethod")) {
                    JSONObject deliverJsonObj = dataJsonObj.getJSONObject(orderStructureMap.get("deliveryMethod"));
                    DeliveryMethod deliveryMethod = new DeliveryMethod(deliverJsonObj);
                    itemOrderModel.setDeliveryMethod(deliveryMethod);
                }
                //买家留言
                if (orderStructureMap.containsKey("memo")) {
                    JSONObject messageJsonObj = dataJsonObj.getJSONObject(orderStructureMap.get("memo"));
                    LeaveMessage leaveMessage = new LeaveMessage(messageJsonObj);
                    itemOrderModel.setLeaveMessage(leaveMessage);
                }
                //服务
                if (orderStructureMap.containsKey("service")) {
                    JSONObject serviceJsonObj = dataJsonObj.getJSONObject(orderStructureMap.get("service"));
                    Service service = new Service(serviceJsonObj);
                    itemOrderModel.setService(service);
                }
                //商品基本信息
                if (itemStructureMap.containsKey("itemInfo")) {
                    JSONObject itemInfoJsonObj = dataJsonObj.getJSONObject(itemStructureMap.get("itemInfo"));
                    ItemInfo itemInfo = new ItemInfo(itemInfoJsonObj);
                    itemOrderModel.setItemInfo(itemInfo);
                }

                //库存
                if (itemStructureMap.containsKey("quantity")) {
                    JSONObject quantityJsonObj = dataJsonObj.getJSONObject(itemStructureMap.get("quantity"));
                    Quantity quantity = new Quantity(quantityJsonObj);
                    itemOrderModel.setQuantity(quantity);
                }

                //商品小计
                if (itemStructureMap.containsKey("itemPay")) {
                    JSONObject itemPayJsonObj = dataJsonObj.getJSONObject(itemStructureMap.get("itemPay"));
                    ItemPay itemPay = new ItemPay(itemPayJsonObj);
                    itemOrderModel.setItemPay(itemPay);
                }
                //商品优惠
                if (itemStructureMap.containsKey("promotion")) {
                    JSONObject promotionJsonObj = dataJsonObj.getJSONObject(itemStructureMap.get("promotion"));
                    ItemPromotion itemPromotion = new ItemPromotion(promotionJsonObj);
                    itemOrderModel.setItemPromotion(itemPromotion);
                }

                //商品总计
                if (orderStructureMap.containsKey("orderPay")) {
                    JSONObject orderPayJsonObj = dataJsonObj.getJSONObject(orderStructureMap.get("orderPay"));
                    OrderPay orderPay = new OrderPay(orderPayJsonObj);
                    itemOrderModel.setOrderPay(orderPay);
                }


                //代付
                if (rootStructureMap.containsKey("agencyPay") && dataJsonObj.has(rootStructureMap.get("agencyPay"))) {
                    JSONObject agencyPayJsonObj = dataJsonObj.getJSONObject(rootStructureMap.get("agencyPay"));
                    AgencyPay agencyPay = new AgencyPay(agencyPayJsonObj);
                    itemOrderModel.setAgencyPay(agencyPay);
                }

                //淘金币
                if (rootStructureMap.containsKey("tbGold") && dataJsonObj.has(rootStructureMap.get("tbGold"))) {
                    JSONObject tbGoldJsonObj = dataJsonObj.getJSONObject(rootStructureMap.get("tbGold"));
                    TbGold tbGold = new TbGold(tbGoldJsonObj);
                    itemOrderModel.setTbGold(tbGold);
                }

                //匿名购买
                if (rootStructureMap.containsKey("anonymous") && dataJsonObj.has(rootStructureMap.get("anonymous"))) {
                    JSONObject anonymousJsonObj = dataJsonObj.getJSONObject(rootStructureMap.get("anonymous"));
                    Anonymous anonymous = new Anonymous(anonymousJsonObj);
                    itemOrderModel.setAnonymous(anonymous);
                }

                //使用天猫点券
                if (rootStructureMap.containsKey("couponCard") && dataJsonObj.has(rootStructureMap.get("couponCard"))) {
                    JSONObject couponCardJsonObj = dataJsonObj.getJSONObject(rootStructureMap.get("couponCard"));
                    CouponCard couponCard = new CouponCard(couponCardJsonObj);
                    itemOrderModel.setCouponCard(couponCard);
                }

                confirmOrderActivity.setItemOrderModel(itemOrderModel);
                result = true;
            }

            //处理各种服务端异常
            if (jsonObject.has("error_response")) {
                //error code  = 15 表示 用户存在的未付款订单过多
                if ("15".equals(jsonObject.getJSONObject("error_response").getString("code"))) {
                    result = false;
                    errorMessage = "您的未付款订单过多";
                    return result;
                }
            }
        } catch (Exception e) {
            Log.e("IOException is : ", e.toString());
            e.printStackTrace();
        }
        if (result == false && errorMessage == null) {
            errorMessage = "创建订单失败";
        }
        return result;
    }


    /**
     * 填充页面数据
     */
    public void initView() {
        //填充 收货地址
        if (itemOrderModel.getAddress() != null) {
            TextView addressTextView = (TextView) confirmOrdcerLayoutView.findViewById(R.id.confirm_order_address_value);
            addressTextView.setText(itemOrderModel.getAddress().getSelectedAddressLikeConcatString());
        }
        //填充 配送方式
        if (itemOrderModel.getDeliveryMethod() != null) {
            TextView deliveryMethodTextView = (TextView) confirmOrdcerLayoutView.findViewById(R.id.confirm_order_delivery_method_name);
            DeliveryMethod.DeliveryFieldsOption deliveryMethod = itemOrderModel.getDeliveryMethod().getSelectedDeliveryMethod();
            deliveryMethodTextView.setText(deliveryMethod.getName());
        }
        //填充 默认买家留言
        if (itemOrderModel.getLeaveMessage() != null) {
            TextView leaveMessageTextView = (TextView) confirmOrdcerLayoutView.findViewById(R.id.confirm_order_leave_message);
            String placeholder = itemOrderModel.getLeaveMessage().getFields().getPlaceholder();
            leaveMessageTextView.setHint(placeholder);
        }
        //填充 商品基本信息
        if (itemOrderModel.getItemInfo() != null) {
            ImageView imageView = (ImageView) confirmOrdcerLayoutView.findViewById(R.id.confirm_order_item_img);
            remoteImageHelper.loadImage(imageView, itemOrderModel.getItemInfo().getFields().getPic());
        }
        //填充 商品小计
        if (itemOrderModel.getItemPay() != null) {
            TextView singlePriceTextView = (TextView) confirmOrdcerLayoutView.findViewById(R.id.confirm_order_item_price_txt);
            singlePriceTextView.setText(itemOrderModel.getItemPay().getFields().getAfterPromotionPrice());
            TextView totalPriceTextView = (TextView) confirmOrdcerLayoutView.findViewById(R.id.confirm_order_total_price);
            totalPriceTextView.setText(itemOrderModel.getItemPay().getFields().getPrice());
        }
        //填充商品优惠
        if (itemOrderModel.getItemPromotion() != null) {
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
