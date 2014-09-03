package com.taobao.tae.Mshopping.demo.model;

import java.io.Serializable;

/**
 * 商品价格单元
 * Created by xinyuan on 14/7/7.
 */
public class PriceUnit  implements Serializable {
    /*价格名字*/
    private String name;
    /*价格 , 如： 1.00,100-102*/
    private String price;
    /*展现的样式：1 ： 高亮显示 2 ： 普通显示 3 : 加删除线 (默认是删除的，设置了valid就有效)*/
    private Integer display;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public Integer getDisplay() {
        return display;
    }

    public void setDisplay(Integer display) {
        this.display = display;
    }
}
