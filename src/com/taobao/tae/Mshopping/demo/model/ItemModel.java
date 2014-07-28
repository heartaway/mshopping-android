package com.taobao.tae.Mshopping.demo.model;

import java.io.Serializable;

/**
 * Created by xinyuan on 14/7/9.
 */
public class ItemModel implements Serializable {
    //商品ID
    private Long itemId;
    //商品数量
    private Integer quantity;
    //商品SKU组合后的 id
    private Long skuId;

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Long getSkuId() {
        return skuId;
    }

    public void setSkuId(Long skuId) {
        this.skuId = skuId;
    }
}
