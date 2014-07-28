package com.taobao.tae.Mshopping.demo.task;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.*;
import com.taobao.api.internal.util.WebUtils;
import com.taobao.tae.Mshopping.demo.R;
import com.taobao.tae.Mshopping.demo.activity.ItemDetailActivity;
import com.taobao.tae.Mshopping.demo.constant.Constants;
import com.taobao.tae.Mshopping.demo.model.*;
import com.taobao.tae.Mshopping.demo.util.Helper;
import com.taobao.tae.Mshopping.demo.util.RemoteImageHelper;
import com.taobao.tae.Mshopping.demo.util.SecurityKey;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 获取商品图文等信息
 * Created by xinyuan on 14/6/25.
 */
public class GetItemRichDetailTask extends AsyncTask<String, Integer, TaobaoItemRichInfo> {

    private Context context;
    private ItemDetailActivity itemDetailActivity;
    private RemoteImageHelper remoteImageHelper = new RemoteImageHelper();

    public GetItemRichDetailTask(Context context, ItemDetailActivity itemDetailActivity) {
        super();
        this.context = context;
        this.itemDetailActivity = itemDetailActivity;
    }

    @Override
    protected TaobaoItemRichInfo doInBackground(String... params) {
        try {
            return parseRichItemsJSON(params[0]);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取到商品图文信息后的回调方法
     *
     * @param taobaoItemRichInfo
     */
    @Override
    protected void onPostExecute(TaobaoItemRichInfo taobaoItemRichInfo) {
        if (taobaoItemRichInfo == null || taobaoItemRichInfo.getBasicInformation() == null) {
            itemDetailActivity.finish();
            toast("获取商品信息失败");
            return;
        }
        itemDetailActivity.setTaobaoItemRichInfo(taobaoItemRichInfo);
        WindowManager windowManager = (WindowManager) itemDetailActivity.getSystemService(itemDetailActivity.WINDOW_SERVICE);
        int widthScreen = windowManager.getDefaultDisplay().getWidth();

        ImageView imageView = (ImageView) itemDetailActivity.findViewById(R.id.item_detail_main_pic);
        imageView.setLayoutParams(new LinearLayout.LayoutParams(widthScreen, widthScreen));
        remoteImageHelper.loadImage(imageView, taobaoItemRichInfo.getBasicInformation().getPicsPath().get(0).toString());

        TextView titleTextView = (TextView) itemDetailActivity.findViewById(R.id.item_detail_title_txt);
        TextView priceTextView = (TextView) itemDetailActivity.findViewById(R.id.item_detail_price_txt);
        String title = taobaoItemRichInfo.getBasicInformation().getTitle();
        if (title.length() > 20) {
            title = title.substring(0, 20).concat("...");
        }
        titleTextView.setText(title);
        Map<Integer, PriceUnit> priceUnits = taobaoItemRichInfo.getBasicInformation().getDefaultPriceUnits();
        PriceUnit currentPriceUnit = priceUnits.get(PriceDisplay.HIGHLIGHT.getCode());
        priceTextView.setText("¥".concat(currentPriceUnit.getPrice()));
        //如果此商品为促销商品
        if (priceUnits.containsKey(PriceDisplay.DELETELINE.getCode())) {
            TextView deletePriceTextView = (TextView) itemDetailActivity.findViewById(R.id.item_detail_deleteprice_txt);
            deletePriceTextView.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);
            deletePriceTextView.setText(priceUnits.get(PriceDisplay.DELETELINE.getCode()).getPrice());
        }
        ItemUnitCotrol itemUnitCotrol = taobaoItemRichInfo.getBasicInformation().getSkuModel().getItemUnitCotrol();
        if (itemUnitCotrol != null && !itemUnitCotrol.isBuySupport()) {
            Button ButtonView = (Button) itemDetailActivity.findViewById(R.id.item_detail_buy_btn);
            ButtonView.setBackgroundColor(Color.parseColor("#ff8f8f8f"));
            TextView invalidItemTextView = (TextView) itemDetailActivity.findViewById(R.id.item_detail_invaliditem_txt);
            invalidItemTextView.setText(itemUnitCotrol.getErrorMessage());
        }
        SellerInfo sellerInfo = taobaoItemRichInfo.getBasicInformation().getSellerInfo();
        ImageView itemFromImageView = (ImageView) itemDetailActivity.findViewById(R.id.item_detail_from_icon);
        TextView itemFromTextView = (TextView) itemDetailActivity.findViewById(R.id.item_detail_from_txt);
        if (sellerInfo != null && "B".equalsIgnoreCase(sellerInfo.getType())) {
            itemFromImageView.setBackgroundResource(R.drawable.tmall_icon);
            itemFromTextView.setText("天猫特供");
        }
        if (sellerInfo != null && "C".equalsIgnoreCase(sellerInfo.getType())) {
            itemFromImageView.setBackgroundResource(R.drawable.tb_icon);
            itemFromTextView.setText("淘宝特供");
        }
        //动态加载图文信息
        LinearLayout dynamicFillContentLinearLayout = (LinearLayout) itemDetailActivity.findViewById(R.id.item_detail_dynamic_fill_content);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        List<String> images = taobaoItemRichInfo.getImageList();
        for (int i = 0; i < images.size(); i++) {
            ImageView _imageView = new ImageView(itemDetailActivity);
            _imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            _imageView.setLayoutParams(layoutParams);
            remoteImageHelper.loadImage(_imageView, images.get(i));
            dynamicFillContentLinearLayout.addView(_imageView);
        }
    }

    @Override
    protected void onPreExecute() {
    }

    public TaobaoItemRichInfo parseRichItemsJSON(String itemId) throws IOException {
        TaobaoItemBasicInfo taobaoItemBasicInfo = new TaobaoItemBasicInfo();
        TaobaoItemRichInfo taobaoItemRichInfo = new TaobaoItemRichInfo();
        String itemBasicJson = "";
        String itemRichJson = "";
        if (Helper.checkConnection(context)) {
            try {
                int timeout = 30000;
                Map param = new HashMap<String, String>();
                param.put("securityKey", SecurityKey.getKey());
                String itemBasicInfoUrl = Constants.SERVER_DOMAIN + "/api/item/basicinfo/" + itemId;
                String itemRichInfoUrl = Constants.SERVER_DOMAIN + "/api/item/picwordinfo/" + itemId;
                itemBasicJson = WebUtils.doPost(itemBasicInfoUrl, param, timeout, timeout);
                itemRichJson = WebUtils.doPost(itemRichInfoUrl, param, timeout, timeout);
            } catch (IOException e) {
                Log.e("IOException is : ", e.toString());
                e.printStackTrace();
                return null;
            }
        }
        try {
            if (null != itemBasicJson && !"".equals(itemBasicJson)) {
                JSONObject jsonObject = new JSONObject(itemBasicJson);
                String result = jsonObject.has("ret") ? jsonObject.get("ret").toString() : null;
                if (result == null || !result.contains("SUCCESS")) {
                    return taobaoItemRichInfo;
                }
                JSONObject dateJsonObject = jsonObject.getJSONObject("data");
                String sellerJson = dateJsonObject.getString("seller");
                String rateJson = dateJsonObject.getString("rateInfo");
                JSONArray apiStackJsonArray = dateJsonObject.getJSONArray("apiStack");
                if (apiStackJsonArray == null || apiStackJsonArray.length() == 0) {
                    return taobaoItemRichInfo;
                }
                JSONObject esiInfoObject = new JSONObject(apiStackJsonArray.get(0).toString());
                JSONObject apiStackDataObject = new JSONObject(esiInfoObject.getString("value")).getJSONObject("data");
                JSONObject itemInfoObject = dateJsonObject.getJSONObject("itemInfoModel");

                JSONArray picArray = itemInfoObject.getJSONArray("picsPath");
                //这里为商品列表页展示数据使用，所以只展示一张图片
                taobaoItemBasicInfo.setItemId(Long.valueOf(itemId));
                taobaoItemBasicInfo.setTitle(itemInfoObject.getString("title"));
                taobaoItemBasicInfo.setFavcount(itemInfoObject.getString("favcount"));
                taobaoItemBasicInfo.setSku(itemInfoObject.getBoolean("sku"));
                taobaoItemBasicInfo.setItemUrl(itemInfoObject.getString("itemUrl"));
                taobaoItemBasicInfo.setLocation(itemInfoObject.getString("location"));
                ArrayList picsPath = new ArrayList<String>();
                for (int i = 0; i < picArray.length(); i++) {
                    picsPath.add(picArray.getString(i));
                }
                taobaoItemBasicInfo.setPicsPath(picsPath);
                taobaoItemBasicInfo.setSellerInfo(new SellerInfo(sellerJson));
                taobaoItemBasicInfo.setRateInfo(new RateInfo(rateJson));

                if (taobaoItemBasicInfo.getSku()) {
                    JSONObject skuModelObject = dateJsonObject.getJSONObject("skuModel");
                    taobaoItemBasicInfo.setSkuModel(new SkuModel(skuModelObject, apiStackDataObject));
                } else {
                    taobaoItemBasicInfo.setSkuModel(new SkuModel(null, apiStackDataObject));
                }

            }
            if (null != itemRichJson && !"".equals(itemRichJson)) {
                JSONObject jsonObject = new JSONObject(itemRichJson);
                String result = jsonObject.get("ret").toString();
                if (result == null || !result.contains("SUCCESS")) {
                    return taobaoItemRichInfo;
                }
                JSONArray imagesJsonArray = jsonObject.getJSONObject("data").getJSONArray("images");
                List imagesList = new ArrayList();
                for (int i = 0; i < imagesJsonArray.length(); i++) {
                    imagesList.add(imagesJsonArray.get(i).toString());
                }
                taobaoItemRichInfo.setImageList(imagesList);
            }
            taobaoItemRichInfo.setBasicInformation(taobaoItemBasicInfo);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return taobaoItemRichInfo;
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
