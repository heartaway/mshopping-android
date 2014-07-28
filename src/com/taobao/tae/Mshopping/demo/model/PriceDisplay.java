package com.taobao.tae.Mshopping.demo.model;

/**
 * Created by xinyuan on 14/7/7.
 */
public enum PriceDisplay {
    HIGHLIGHT(1,"高亮显示"),
    NORMAL(2,"普通显示"),
    DELETELINE(3,"添加删除线");

    private int code;
    private String description;

    PriceDisplay(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

}
