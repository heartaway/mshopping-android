package com.taobao.tae.Mshopping.demo.model;


/**
 * 用户没有选择商品SKU前，默认展示的商品SKU属性
 * Created by xinyuan on 14/7/7.
 */
public class DefaultShowItemSku extends ShowItemSku {
    /*商品销量（b为全部，c为30天）,为 null 不展示*/
    private Integer totalSoldQuantity;

    public Integer getTotalSoldQuantity() {
        return totalSoldQuantity;
    }

    public void setTotalSoldQuantity(Integer totalSoldQuantity) {
        this.totalSoldQuantity = totalSoldQuantity;
    }

}
