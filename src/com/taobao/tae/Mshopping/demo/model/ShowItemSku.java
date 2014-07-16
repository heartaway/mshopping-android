package com.taobao.tae.Mshopping.demo.model;

import java.io.Serializable;
import java.util.HashMap;

/**
 * Created by xinyuan on 14/7/7.
 */
public class ShowItemSku  implements Serializable {
    /*库存数量*/
    private Integer quantity;
    /*key为 priceunit的display属性*/
    private HashMap<Integer, PriceUnit> priceUnits;

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public HashMap<Integer, PriceUnit> getPriceUnits() {
        return priceUnits;
    }

    public void setPriceUnits(HashMap<Integer, PriceUnit> priceUnits) {
        this.priceUnits = priceUnits;
    }
}
