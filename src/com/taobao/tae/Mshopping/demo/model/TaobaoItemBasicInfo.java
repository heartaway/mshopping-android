package com.taobao.tae.Mshopping.demo.model;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TaobaoItemBasicInfo implements Serializable {
    /*淘宝默认商品主图为 400* 400 ，这里固定设置为 200*200 */
    private int width = 200;
    private int height = 200;
    private String title;
    /* 淘宝商品ID*/
    private Long itemId;
    private String picUrl;
    private String price;
    /*喜欢数*/
    private String favcount;
    /*商品URL*/
    private String itemUrl;
    /*是否存在SKU属性*/
    private Boolean sku;
    /*商品所在地*/
    private String location;
    /*多张商品主图*/
    private List picsPath;
    /*商品类型：taobao、tmall */
    private String itemTypeName;
    /*商品卖家基本信息*/
    private SellerInfo sellerInfo;
    /*商品评价信息*/
    private RateInfo rateInfo;
    private SkuModel skuModel;
    private Date pushTime;

    /**
     * 获取默认的商品价格属性
     *
     * @return
     */
    public Map<Integer, PriceUnit> getDefaultPriceUnits() {
        if (skuModel != null) {
            return skuModel.getDefaultShowItemSku().getPriceUnits();
        }
        return null;
    }

    public int getWidth() {
        return width;
    }

    public String getTitle() {
        return title;
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public int getHeight() {
        return height;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public Date getPushTime() {
        return pushTime;
    }

    public void setPushTime(Date pushTime) {
        this.pushTime = pushTime;
    }

    public String getFavcount() {
        return favcount;
    }

    public void setFavcount(String favcount) {
        this.favcount = favcount;
    }

    public String getItemUrl() {
        return itemUrl;
    }

    public void setItemUrl(String itemUrl) {
        this.itemUrl = itemUrl;
    }

    public Boolean getSku() {
        return sku;
    }

    public void setSku(Boolean sku) {
        this.sku = sku;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getItemTypeName() {
        return itemTypeName;
    }

    public void setItemTypeName(String itemTypeName) {
        this.itemTypeName = itemTypeName;
    }

    public List getPicsPath() {
        return picsPath;
    }

    public void setPicsPath(List picsPath) {
        this.picsPath = picsPath;
    }

    public SellerInfo getSellerInfo() {
        return sellerInfo;
    }

    public void setSellerInfo(SellerInfo sellerInfo) {
        this.sellerInfo = sellerInfo;
    }

    public RateInfo getRateInfo() {
        return rateInfo;
    }

    public void setRateInfo(RateInfo rateInfo) {
        this.rateInfo = rateInfo;
    }

    public SkuModel getSkuModel() {
        return skuModel;
    }

    public void setSkuModel(SkuModel skuModel) {
        this.skuModel = skuModel;
    }
}
