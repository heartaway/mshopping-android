package com.taobao.tae.Mshopping.demo.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.taobao.api.internal.util.StringUtils;
import com.taobao.tae.Mshopping.demo.R;
import com.taobao.tae.Mshopping.demo.constant.Constants;
import com.taobao.tae.Mshopping.demo.constant.UmengAnalysis;
import com.taobao.tae.Mshopping.demo.image.ImageFetcher;
import com.taobao.tae.Mshopping.demo.model.*;
import com.taobao.tae.Mshopping.demo.task.BuildOrderTask;
import com.taobao.tae.Mshopping.demo.task.CreateOrderTask;
import com.taobao.tae.Mshopping.demo.task.UpdateOrderTask;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.Map;

public class ConfirmOrderActivity extends BaseActivity {

    private TaobaoItemBasicInfo taobaoItemBasicInfo;
    private SkuSelect skuSelect;
    //用户在商品详情中选择购买的商品数量
    private Integer count;
    //商品库存
    private Integer quantity;
    //商品价格
    private Double price;
    //购买的商品列表
    private ArrayList<ItemModel> itemModels;

    private RelativeLayout confirmOrdcerLayoutView;
    private ItemOrderModel itemOrderModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.confirm_order_activity);
        getParams();
        buildOrderInfo();
        addButtonListener();
        initView();
        MobclickAgent.setDebugMode(UmengAnalysis.isOpenAnalyticsDebug);
    }

    /**
     * 构建订单信息
     */
    public void buildOrderInfo() {
        confirmOrdcerLayoutView = (RelativeLayout) findViewById(R.id.confirm_order_global_layout);
        BuildOrderTask buildOrderTask = new BuildOrderTask(getApplicationContext(), itemModels, confirmOrdcerLayoutView, this);
        buildOrderTask.execute();
    }


    /**
     * 获取商品详情页传递过来的商品信息
     * 目前只支持单品的支付购买，不支持多个商品的同时构建订单
     */
    public void getParams() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle.containsKey("taobaoItemBasicInfo")) {
            taobaoItemBasicInfo = (TaobaoItemBasicInfo) bundle.getSerializable("taobaoItemBasicInfo");
        }
        if (bundle.containsKey("skuSelect")) {
            skuSelect = (SkuSelect) bundle.getSerializable("skuSelect");
        }
        if (bundle.containsKey("count")) {
            count = bundle.getInt("count");
        }
        if (taobaoItemBasicInfo == null || count == null) {
            finish();
        }

        if (skuSelect != null) {
            String ppath = skuSelect.getPpath();
            ShowItemSku showItemSku = taobaoItemBasicInfo.getSkuModel().getPriceUnitsByPpath(ppath);
            String ppathId = taobaoItemBasicInfo.getSkuModel().getPpathIdByPath(ppath);
            PriceUnit priceUnit = showItemSku.getPriceUnits().get(PriceDisplay.HIGHLIGHT.getCode());
            quantity = showItemSku.getQuantity();
            price = Double.valueOf(priceUnit.getPrice());
            ItemModel item = new ItemModel();
            item.setItemId(taobaoItemBasicInfo.getItemId());
            item.setQuantity(count);
            item.setSkuId(Long.valueOf(ppathId));
            itemModels = new ArrayList<ItemModel>();
            itemModels.add(item);
        } else {
            //商品 无 SKU 属性
            DefaultShowItemSku defaultShowItemSku = taobaoItemBasicInfo.getSkuModel().getDefaultShowItemSku();
            PriceUnit _priceUnit = defaultShowItemSku.getPriceUnits().get(PriceDisplay.HIGHLIGHT.getCode());
            price = Double.valueOf(_priceUnit.getPrice());
            quantity = defaultShowItemSku.getQuantity();
            ItemModel item = new ItemModel();
            item.setItemId(taobaoItemBasicInfo.getItemId());
            item.setQuantity(count);
            itemModels = new ArrayList<ItemModel>();
            itemModels.add(item);
        }
    }

    /**
     * 添加界面上的按钮的监听器
     */
    public void addButtonListener() {
        RelativeLayout backBtnLayout = (RelativeLayout) findViewById(R.id.confirm_order_top_back_btn);
        backBtnLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putString("itemId", taobaoItemBasicInfo.getItemId().toString());
                bundle.putInt("ACTIVITY_NAME_KEY", R.string.title_activity_pay_order);
                intent.putExtras(bundle);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.setClass(ConfirmOrderActivity.this, ItemDetailActivity.class);
                startActivity(intent);
                finish();
            }
        });

        Button confirmView = (Button) findViewById(R.id.confirm_order_confirm_btn);
        confirmView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView leaveMessage = (TextView) findViewById(R.id.confirm_order_leave_message);
                itemOrderModel.getLeaveMessage().getFields().setValue(leaveMessage.getText().toString());
                CreateOrderTask createOrderTask = new CreateOrderTask(getApplicationContext(), itemModels, confirmOrdcerLayoutView, ConfirmOrderActivity.this);
                createOrderTask.execute(itemOrderModel.getCreateOrderRequiredJson());
            }
        });
    }

    /**
     * 初始化确认订单页的数据
     */
    public void initView() {
        TextView shopTitleView = (TextView) findViewById(R.id.confirm_order_shop_title);
        shopTitleView.setText(taobaoItemBasicInfo.getSellerInfo().getShopTitle());

        ImageView imageView = (ImageView) findViewById(R.id.confirm_order_item_img);
        ImageFetcher imageFetcher = new ImageFetcher(this, 200);
        imageFetcher.loadImage(taobaoItemBasicInfo.getPicsPath().get(0), imageView);
        TextView titleTextView = (TextView) findViewById(R.id.confirm_order_item_title_txt);
        if (taobaoItemBasicInfo.getTitle().length() > 15) {
            titleTextView.setText(taobaoItemBasicInfo.getTitle().substring(0, 15).concat("..."));
        } else {
            titleTextView.setText(taobaoItemBasicInfo.getTitle());
        }
        if (skuSelect != null) {
            TextView skuTextView = (TextView) findViewById(R.id.confirm_order_item_sku_txt);
            String skuPropString = skuSelect.getUserSelectSkuPropNameString();
            if (skuPropString.length() > 20) {
                skuPropString = skuPropString.substring(0, 20).concat("...");
            }
            skuTextView.setText(skuPropString);
        }
        SellerInfo sellerInfo = taobaoItemBasicInfo.getSellerInfo();
        ImageView itemFromImageView = (ImageView)findViewById(R.id.confirm_order_item_from_icon);
        TextView itemFromTextView = (TextView)findViewById(R.id.confirm_order_item_from_text);
        if (sellerInfo != null && "B".equalsIgnoreCase(sellerInfo.getType())) {
            itemFromImageView.setBackgroundResource(R.drawable.tmall_icon);
            itemFromTextView.setText("天猫店铺");
        }
        if (sellerInfo != null && "C".equalsIgnoreCase(sellerInfo.getType())) {
            itemFromImageView.setBackgroundResource(R.drawable.tb_icon);
            itemFromTextView.setText("淘宝店铺");
        }

        TextView priceTextView = (TextView) findViewById(R.id.confirm_order_item_price_txt);
        priceTextView.setText("￥".concat(price.toString()));

        TextView itemCountTextView = (TextView) findViewById(R.id.confirm_order_item_buy_count_value);
        itemCountTextView.setText(count.toString());
    }

    public ItemOrderModel getItemOrderModel() {
        return itemOrderModel;
    }

    public void setItemOrderModel(ItemOrderModel itemOrderModel) {
        this.itemOrderModel = itemOrderModel;
    }

}