package com.taobao.tae.Mshopping.demo.model;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by xinyuan on 14/7/10.
 */
public class SkuPropertyValue implements Serializable {
    private Long valueId;
    private String name;
    private String imgUrl;

    public SkuPropertyValue(JSONObject jsonObject) {
        if (jsonObject == null) {
            return;
        }
        try {
            if (jsonObject.has("valueId")) {
                this.valueId = jsonObject.getLong("valueId");
            }
            if (jsonObject.has("name")) {
                this.name = jsonObject.getString("name");
            }
            if (jsonObject.has("imgUrl")) {
                this.imgUrl = jsonObject.getString("imgUrl");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public Long getValueId() {
        return valueId;
    }

    public void setValueId(Long valueId) {
        this.valueId = valueId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}