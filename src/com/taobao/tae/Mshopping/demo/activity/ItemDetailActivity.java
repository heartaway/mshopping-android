package com.taobao.tae.Mshopping.demo.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.*;
import android.widget.*;
import com.taobao.tae.Mshopping.demo.MshoppingApplication;
import com.taobao.tae.Mshopping.demo.R;
import com.taobao.tae.Mshopping.demo.image.ImageFetcher;
import com.taobao.tae.Mshopping.demo.model.*;
import com.taobao.tae.Mshopping.demo.task.GetItemRichDetailTask;
import com.taobao.tae.Mshopping.demo.util.NetWorkStateUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ItemDetailActivity extends BaseActivity {
    private Button buyNowButton;
    private PopupWindow popupWindow;
    private RelativeLayout itemDetailPanel;
    private RelativeLayout itemDetailSkuPanel;
    private View popUpView;
    private TextView addItemCountTextView;
    private TextView reduceItemCountTextView;
    private TaobaoItemRichInfo taobaoItemRichInfo;
    private SkuSelect skuSelect;
    private SkuModel skuModel;
    private Context context;
    private View buyView;
    private View skuPropertiesBackgroudShadowView;
    private View loginNoticeBackgroudShadowView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_detail_frame);
        context = getApplicationContext();
        renderDetailPage();
        onClickBackButton();
        itemDetailPanel = (RelativeLayout) findViewById(R.id.item_detail_all_layout);
        buyNowButton = (Button) findViewById(R.id.item_detail_buy_btn);
        buyNowButton.setOnClickListener(new BuyNowClickListener());
    }

    /**
     * 用户点击返回按钮
     */
    private void onClickBackButton() {
        RelativeLayout backRelativeLayout = (RelativeLayout) findViewById(R.id.item_detail_top_back_btn);
        backRelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ItemDetailActivity.this.finish();
            }
        });
    }


    /**
     * 渲染页面信息
     */
    private void renderDetailPage() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        String itemId = bundle.getString("itemId");
        if (!NetWorkStateUtil.isConnected(this)) {
            finish();
            toast("请检查网络连接");
            return;
        }
        GetItemRichDetailTask getItemRichDetailTask = new GetItemRichDetailTask(this, this);
        getItemRichDetailTask.execute(itemId);
    }


    /**
     * 立即购买 监听器
     */
    private class BuyNowClickListener implements View.OnClickListener {
        public void onClick(View v) {
            ItemUnitCotrol itemUnitCotrol = taobaoItemRichInfo.getBasicInformation().getSkuModel().getItemUnitCotrol();
            if (itemUnitCotrol != null && !itemUnitCotrol.isBuySupport()) {
                toast("商品已下架");
                return;
            }
            buyView = v;
            setBackgroudShaowOnSkuPopUp();
            LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            popUpView = layoutInflater.inflate(R.layout.item_detail_sku_select, null);
            itemDetailSkuPanel = (RelativeLayout) popUpView.findViewById(R.id.item_detail_sku_panel);
            popupWindow = new PopupWindow(popUpView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
            popupWindow.setAnimationStyle(R.style.item_sku_animation);//设置淡入淡出动画效果
            popupWindow.setFocusable(true);// 使其聚集
            popupWindow.setOutsideTouchable(true);// 设置允许在外点击消失
            popupWindow.setBackgroundDrawable(new BitmapDrawable()); // 这个是为了点击“返回Back”也能使其消失，并且并不会影响你的背景
            popupWindow.showAtLocation(v, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
            initItemThumbnail();
            initItemSKUProperties(popUpView);
            listenConfirmButton();
            ImageView cancelPopupImageView = (ImageView) popUpView.findViewById(R.id.item_detail_sku_cancel);
            cancelPopupImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    popupWindow.dismiss();
                }
            });

            popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {
                    itemDetailPanel.removeView(skuPropertiesBackgroudShadowView);
                }
            });
        }

    }

    @Override
    protected void onStop() {
        super.onStop();
        if (popupWindow != null) {
            popupWindow.dismiss();
        }
    }

    /**
     * 商品SKU属性弹出时，设置除SKU视图部分为半透明
     */
    private void setBackgroudShaowOnSkuPopUp() {
        skuPropertiesBackgroudShadowView = new View(ItemDetailActivity.this);
        skuPropertiesBackgroudShadowView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        skuPropertiesBackgroudShadowView.setBackgroundResource(R.color.sku_out_bg_shadow);
        itemDetailPanel.addView(skuPropertiesBackgroudShadowView);
    }

    /**
     * 初始化商品SKU选择时顶部的商品缩略信息
     */
    public void initItemThumbnail() {
        ImageView imageView = (ImageView) popUpView.findViewById(R.id.item_detail_sku_sm_img);
        ImageFetcher imageFetcher = new ImageFetcher(this, 200);
        imageFetcher.loadImage(taobaoItemRichInfo.getBasicInformation().getPicsPath().get(0), imageView);
        TextView titleTextView = (TextView) popUpView.findViewById(R.id.item_deatil_sku_title_txt);
        if (taobaoItemRichInfo.getBasicInformation().getTitle().length() > 15) {
            titleTextView.setText(taobaoItemRichInfo.getBasicInformation().getTitle().substring(0, 15).concat("..."));
        } else {
            titleTextView.setText(taobaoItemRichInfo.getBasicInformation().getTitle());
        }
        TextView priceTextView = (TextView) popUpView.findViewById(R.id.item_deatil_sku_price_txt);
        Map<Integer, PriceUnit> priceUnits = taobaoItemRichInfo.getBasicInformation().getDefaultPriceUnits();
        PriceUnit currentPriceUnit = priceUnits.get(PriceDisplay.HIGHLIGHT.getCode());
        priceTextView.setText("￥".concat(currentPriceUnit.getPrice()));
    }


    /**
     * 初始化商品SKU属性，如果存在的话
     *
     * @param view
     */
    public void initItemSKUProperties(View view) {
        addItemCountTextView = (TextView) popUpView.findViewById(R.id.item_buy_count_add_btn);
        reduceItemCountTextView = (TextView) popUpView.findViewById(R.id.item_buy_count_reduce_btn);
        skuModel = taobaoItemRichInfo.getBasicInformation().getSkuModel();
        Display display = getWindowManager().getDefaultDisplay();
        int maxWidth = display.getWidth() - 10;
        //如果商品存在SKU属性
        if (taobaoItemRichInfo.getBasicInformation().getSku()) {
            skuSelect = new SkuSelect();
            LinearLayout skuPropertiesLayout = (LinearLayout) popUpView.findViewById(R.id.item_detail_dynamic_sku_properties);
            HashMap<Long, SkuProperty> skuPropertyMap = skuModel.getSkuPropertyMap();
            for (Map.Entry entry : skuPropertyMap.entrySet()) {
                final SkuProperty skuProperty = (SkuProperty) entry.getValue();
                TextView textView = new TextView(this);
                textView.setText(skuProperty.getPropName().concat(":"));
                textView.setTextColor(Color.parseColor("#ff000000"));
                textView.setId(skuProperty.getPropId().intValue());
                skuPropertiesLayout.addView(textView);
                skuSelect.put(skuProperty.getPropId().longValue(), skuProperty.getPropName());
                final List<Button> skuViewList = new ArrayList<Button>();
                if (skuProperty.getPropertyMap() != null && skuProperty.getPropertyMap().entrySet().size() > 0) {
                    LinearLayout linearLayout = new LinearLayout(this);
                    linearLayout.setOrientation(LinearLayout.VERTICAL);
                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    layoutParams.setMargins(0, 20, 0, 20);
                    linearLayout.setLayoutParams(layoutParams);

                    LinearLayout newlinearLayout = new LinearLayout(this);
                    newlinearLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                    newlinearLayout.setOrientation(LinearLayout.HORIZONTAL);
                    newlinearLayout.setLayoutParams(layoutParams);

                    int widthSoFar = 40;
                    for (Map.Entry skuEntry : skuProperty.getPropertyMap().entrySet()) {
                        final Long skuId = (Long) skuEntry.getKey();
                        final SkuPropertyValue propertyValue = (SkuPropertyValue) skuEntry.getValue();
                        final String skuName = propertyValue.getName();
                        final Button skuButton = new Button(this);
                        LinearLayout.LayoutParams skuLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                        skuLayoutParams.setMargins(10, 0, 10, 0);
                        skuButton.setText(skuName);
                        skuButton.measure(0, 0);
                        skuButton.setId(skuId.intValue());
                        skuButton.setClickable(true);
                        skuButton.setTextColor(Color.parseColor("#424242"));
                        skuButton.setLayoutParams(skuLayoutParams);
                        skuButton.setBackgroundResource(R.drawable.sku_button_unselect_bg);
                        skuButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                for (Button allButton : skuViewList) {
                                    allButton.setTextColor(Color.parseColor("#ff000000"));
                                    allButton.setBackgroundResource(R.drawable.sku_button_unselect_bg);
                                }
                                skuButton.setTextColor(Color.parseColor("#ffff0066"));
                                skuButton.setBackgroundResource(R.drawable.sku_button_select_bg);
                                /* 当用户选择了所有的SKU分类属性后，商品价格、库存量联动刷新显示 */
                                skuSelect.setSelectedSkuId(skuProperty.getPropId(), skuId, skuName);
                                if (skuSelect.isSelectedAllSkus()) {
                                    String ppath = skuSelect.getPpath();
                                    ShowItemSku showItemSku = skuModel.getPriceUnitsByPpath(ppath);
                                    TextView quantityView = (TextView) popUpView.findViewById(R.id.item_deatil_sku_quantity_txt);
                                    if (showItemSku == null) {
                                        quantityView.setText("(库存: 0 件)");
                                        toast("此商品属性组合下已无商品");
                                        return;
                                    }
                                    PriceUnit priceUnit = showItemSku.getPriceUnits().get(PriceDisplay.HIGHLIGHT.getCode());
                                    final Integer quantity = showItemSku.getQuantity();
                                    quantityView.setText("(库存:".concat(quantity.toString()).concat("件)"));
                                    //更新 商品图片
                                    if (skuModel.hasRelationImgUrl()) {
                                        SkuProperty _skuProperty = skuModel.getSkuPropertyMap().get(skuModel.getRelationImgUrlPropId());
                                        Long selectSkuId = skuSelect.getSkuSelectMap().get(skuModel.getRelationImgUrlPropId()).getSkuId();
                                        SkuPropertyValue skuPropertyValue = _skuProperty.getPropertyMap().get(selectSkuId);
                                        ImageView imageView = (ImageView) popUpView.findViewById(R.id.item_detail_sku_sm_img);
                                        ImageFetcher imageFetcher = new ImageFetcher(context, 200);
                                        imageFetcher.loadImage(skuPropertyValue.getImgUrl(), imageView);
                                    }
                                    //更新商品价格
                                    if (priceUnit != null) {
                                        TextView priceView = (TextView) popUpView.findViewById(R.id.item_deatil_sku_price_txt);
                                        priceView.setText("￥".concat(priceUnit.getPrice()));
                                    }
                                    if (quantity > 1) {
                                        addItemCountTextView.setTextColor(Color.parseColor("#e94277"));
                                    }

                                    addItemCountTextView.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            TextView itemCountTextView = (TextView) popUpView.findViewById(R.id.item_buy_count);
                                            Integer itemCount = Integer.valueOf(itemCountTextView.getText().toString());
                                            if (itemCount == quantity) {
                                                return;
                                            }
                                            itemCount = itemCount + 1;
                                            itemCountTextView.setText(itemCount.toString());
                                            if (itemCount > 1) {
                                                reduceItemCountTextView.setTextColor(Color.parseColor("#e94277"));
                                            }
                                        }
                                    });

                                    reduceItemCountTextView.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            TextView itemCountTextView = (TextView) popUpView.findViewById(R.id.item_buy_count);
                                            Integer itemCount = Integer.valueOf(itemCountTextView.getText().toString());
                                            if (itemCount == 1) {
                                                return;
                                            }
                                            itemCount = itemCount - 1;
                                            itemCountTextView.setText(itemCount.toString());
                                            if (itemCount == 1) {
                                                reduceItemCountTextView.setTextColor(Color.GRAY);
                                            }
                                        }
                                    });
                                }
                            }
                        });
                        skuViewList.add(skuButton);
                        skuButton.measure(0, 0);
                        widthSoFar += skuButton.getMeasuredWidth();
                        if (widthSoFar >= maxWidth) {
                            linearLayout.addView(newlinearLayout);
                            newlinearLayout = new LinearLayout(this);
                            newlinearLayout.setOrientation(LinearLayout.HORIZONTAL);
                            newlinearLayout.setLayoutParams(layoutParams);
                            newlinearLayout.addView(skuButton);
                            widthSoFar = skuButton.getMeasuredWidth();
                        } else {
                            newlinearLayout.addView(skuButton);
                        }

                    }
                    linearLayout.addView(newlinearLayout);
                    skuPropertiesLayout.addView(linearLayout);
                }

                View lineView = new View(this);
                lineView.setBackgroundColor(Color.parseColor("#bbbbbb"));
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 1);
                layoutParams.setMargins(0, 5, 0, 5);
                lineView.setLayoutParams(layoutParams);
                skuPropertiesLayout.addView(lineView);
            }
        } else {
            //不存在商品SKU属性，直接展示商品库存量等信息
            final Integer quantity = skuModel.getDefaultShowItemSku().getQuantity();
            TextView quantityView = (TextView) popUpView.findViewById(R.id.item_deatil_sku_quantity_txt);
            quantityView.setText("(库存:".concat(quantity.toString()).concat("件)"));
            if (quantity > 1) {
                addItemCountTextView.setTextColor(Color.parseColor("#e94277"));
                addItemCountTextView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        TextView itemCountTextView = (TextView) popUpView.findViewById(R.id.item_buy_count);
                        Integer itemCount = Integer.valueOf(itemCountTextView.getText().toString());
                        if (itemCount == quantity) {
                            return;
                        }
                        itemCount = itemCount + 1;
                        itemCountTextView.setText(itemCount.toString());
                        if (itemCount > 1) {
                            reduceItemCountTextView.setTextColor(Color.parseColor("#e94277"));
                        }
                    }
                });

                reduceItemCountTextView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        TextView itemCountTextView = (TextView) popUpView.findViewById(R.id.item_buy_count);
                        Integer itemCount = Integer.valueOf(itemCountTextView.getText().toString());
                        if (itemCount == 1) {
                            return;
                        }
                        itemCount = itemCount - 1;
                        itemCountTextView.setText(itemCount.toString());
                        if (itemCount == 1) {
                            reduceItemCountTextView.setTextColor(Color.GRAY);
                        }
                    }
                });
            } else {
                toast("商品数量不足");
                finish();
            }

        }


    }


    /**
     * 监听 商品SKU浮层中的确定按钮
     * 首先判断用户是否选择了所有的SKU属性，如果没有选择，则提示选择。
     * 如果用户选择了所有必选的SKU属性后，则判断是否登录，已登录，创建订单；未登录 ，跳转登录页面。
     */
    public void listenConfirmButton() {
        Button confirmButton = (Button) popUpView.findViewById(R.id.item_detail_buy_confirm_btn);
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (skuSelect != null && !skuSelect.isSelectedAllSkus()) {
                    toast("请先选择".concat(skuSelect.getPropNameString()));
                    return;
                }
                if (skuSelect != null && skuSelect.isSelectedAllSkus()) {
                    String ppath = skuSelect.getPpath();
                    ShowItemSku showItemSku = skuModel.getPriceUnitsByPpath(ppath);
                    if (showItemSku == null) {
                        toast("此商品属性组合下已无商品".concat(skuSelect.getPropNameString()));
                        return;
                    }
                }

                MshoppingApplication mshoppingApplication = (MshoppingApplication) context;
                TextView itemCountTextView = (TextView) popUpView.findViewById(R.id.item_buy_count);
                final Integer itemCount = Integer.valueOf(itemCountTextView.getText().toString());
                if (mshoppingApplication.oAuthIsExpire()) {
                    //用户登录
                    setBackgroudShadowOnLoginNotice();
                    LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    View loginPopUpView = layoutInflater.inflate(R.layout.item_detail_login_notice, null);
                    final PopupWindow logoinPopupWindow = new PopupWindow(loginPopUpView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
                    logoinPopupWindow.setAnimationStyle(R.style.item_sku_animation);//设置淡入淡出动画效果
                    logoinPopupWindow.setFocusable(true);// 使其聚集
                    logoinPopupWindow.setOutsideTouchable(true);// 设置允许在外点击消失
                    logoinPopupWindow.setBackgroundDrawable(new BitmapDrawable()); // 这个是为了点击“返回Back”也能使其消失，并且并不会影响你的背景
                    logoinPopupWindow.showAtLocation(buyView, Gravity.CENTER_HORIZONTAL, 0, 0);
                    logoinPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                        @Override
                        public void onDismiss() {
                            itemDetailSkuPanel.removeView(loginNoticeBackgroudShadowView);
                        }
                    });

                    Button loginButton = (Button) loginPopUpView.findViewById(R.id.item_detail_login_notice_btn);
                    loginButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            logoinPopupWindow.dismiss();
                            Intent intent = new Intent(context, PersonalActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("taobaoItemBasicInfo", taobaoItemRichInfo.getBasicInformation());
                            bundle.putSerializable("skuSelect", skuSelect);
                            bundle.putInt("count", itemCount);
                            bundle.putInt("ACTIVITY_NAME_KEY", R.string.title_activity_item_detail);
                            intent.putExtras(bundle);
                            startActivity(intent);
                        }
                    });
                } else {
                    //创建订单，跳转到订单确认页面
                    Intent intent = new Intent(context, ConfirmOrderActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("taobaoItemBasicInfo", taobaoItemRichInfo.getBasicInformation());
                    bundle.putSerializable("skuSelect", skuSelect);
                    bundle.putInt("count", itemCount);
                    bundle.putInt("ACTIVITY_NAME_KEY", R.string.title_activity_item_detail);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }

            }
        });
    }


    /**
     * 购买商品用户没有登录时，弹出引导登录的背景遮盖
     */
    private void setBackgroudShadowOnLoginNotice() {
        loginNoticeBackgroudShadowView = new View(this);
        loginNoticeBackgroudShadowView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        loginNoticeBackgroudShadowView.setBackgroundResource(R.color.sku_out_bg_shadow);
        itemDetailSkuPanel.addView(loginNoticeBackgroudShadowView);
        System.out.println();
    }


    public void setTaobaoItemRichInfo(TaobaoItemRichInfo taobaoItemRichInfo) {
        this.taobaoItemRichInfo = taobaoItemRichInfo;
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
