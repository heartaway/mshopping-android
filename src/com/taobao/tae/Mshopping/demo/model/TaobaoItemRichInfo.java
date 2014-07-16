package com.taobao.tae.Mshopping.demo.model;


import java.io.Serializable;
import java.util.List;

/**
 * 商品 富文本信息
 */
public class TaobaoItemRichInfo implements Serializable {

    private TaobaoItemBasicInfo basicInformation;

    /*图片列表*/
    private List<String> imageList;


    public TaobaoItemBasicInfo getBasicInformation() {
        return basicInformation;
    }

    public void setBasicInformation(TaobaoItemBasicInfo basicInformation) {
        this.basicInformation = basicInformation;
    }

    public List<String> getImageList() {
        return imageList;
    }

    public void setImageList(List<String> imageList) {
        this.imageList = imageList;
    }
}
