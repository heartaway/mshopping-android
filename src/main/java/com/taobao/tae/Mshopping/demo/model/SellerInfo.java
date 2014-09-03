package com.taobao.tae.Mshopping.demo.model;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * 卖家基本信息
 * Created by xinyuan on 14/7/7.
 */
public class SellerInfo  implements Serializable {
    private Long userNumId;
    /**
     * B卖家或C卖家
     */
    private String type;
    /**
     * 卖家呢称
     */
    private String nick;

    /**
     * 卖家累计信用等级 值为0到20分别对应1星到5皇冠
     * 1-5对应1心到5心
     * 6-10对应1钻到5钻
     * 11-15对应1皇冠到5皇冠
     * 16-20对应1金冠到5金冠
     */
    private Integer creditLevel;
    /**
     * 好评率 例如: 100%
     */
    private String goodRatePercentage;
    /**
     * 卖家店铺名称
     */
    private String shopTitle;
    private Long shopId;
    /**
     * 信用评价
     */
    private EvaluateInfo evaluateInfo;


    public SellerInfo(String sellerJson) {
        try {
            JSONObject sellerObject = new JSONObject(sellerJson);
            this.userNumId = sellerObject.has("userNumId") ? sellerObject.getLong("userNumId") : null;
            this.type = sellerObject.has("type") ? sellerObject.getString("type") : null;
            this.nick = sellerObject.has("nick") ? sellerObject.getString("nick") : null;
            this.creditLevel = sellerObject.has("creditLevel") ? sellerObject.getInt("creditLevel") : null;
            this.goodRatePercentage = sellerObject.has("goodRatePercentage") ? sellerObject.getString("goodRatePercentage") : null;
            this.shopTitle = sellerObject.has("userNumId") ? sellerObject.getString("shopTitle") : null;
            this.shopId = sellerObject.has("shopId") ? sellerObject.getLong("shopId") : null;
            if (sellerObject.has("evaluateInfo")) {
                this.evaluateInfo = new EvaluateInfo(sellerObject.getString("evaluateInfo"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public Long getUserNumId() {
        return userNumId;
    }

    public void setUserNumId(Long userNumId) {
        this.userNumId = userNumId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public Integer getCreditLevel() {
        return creditLevel;
    }

    public void setCreditLevel(Integer creditLevel) {
        this.creditLevel = creditLevel;
    }

    public String getGoodRatePercentage() {
        return goodRatePercentage;
    }

    public void setGoodRatePercentage(String goodRatePercentage) {
        this.goodRatePercentage = goodRatePercentage;
    }

    public String getShopTitle() {
        return shopTitle;
    }

    public void setShopTitle(String shopTitle) {
        this.shopTitle = shopTitle;
    }

    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    public EvaluateInfo getEvaluateInfo() {
        return evaluateInfo;
    }

    public void setEvaluateInfo(EvaluateInfo evaluateInfo) {
        this.evaluateInfo = evaluateInfo;
    }
}
