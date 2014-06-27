package com.taobao.tae.Mshopping.demo.model;

import java.util.Date;

public class TaobaoItemBasicInformation {
    /*淘宝默认商品主图为 400* 400 ，这里固定设置为 200*200 */
    private int width = 200;
    private int height = 200;
    private String title;
    /* 淘宝商品ID*/
    private Long itemId;
    private String picUrl;
    private String price;
    private Date pushTime;

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
}
