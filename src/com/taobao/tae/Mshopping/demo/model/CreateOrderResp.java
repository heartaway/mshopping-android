package com.taobao.tae.Mshopping.demo.model;

import java.io.Serializable;

/**
 * Created by xinyuan on 14/7/14.
 */
public class CreateOrderResp implements Serializable{

    private boolean isSuccess;

    private String payOrderId;

    private String bizOrderId;

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean isSuccess) {
        this.isSuccess = isSuccess;
    }

    public String getPayOrderId() {
        return payOrderId;
    }

    public void setPayOrderId(String payOrderId) {
        this.payOrderId = payOrderId;
    }

    public String getBizOrderId() {
        return bizOrderId;
    }

    public void setBizOrderId(String bizOrderId) {
        this.bizOrderId = bizOrderId;
    }
}
