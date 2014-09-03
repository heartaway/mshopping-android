package com.taobao.tae.Mshopping.demo.model;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * 商品单个SKU属性
 * Created by xinyuan on 14/7/7.
 */
public class SkuProperty implements Serializable {
    private Long propId;
    private String propName;
    private HashMap<Long, SkuPropertyValue> propertyMap;

    /**
     * 此 SKU 属性是否关联了 商品图片
     *
     * @return
     */
    public Boolean isRelationImgUrl() {
        Boolean result = false;
        if (propertyMap == null || propertyMap.size() == 0) {
            return false;
        }
        for (Map.Entry entry : propertyMap.entrySet()) {
            SkuPropertyValue skuPropertyValue = (SkuPropertyValue) entry.getValue();
            if (skuPropertyValue.getImgUrl() != null) {
                result = true;
                break;
            }
        }
        return result;
    }

    public Long getPropId() {
        return propId;
    }

    public void setPropId(Long propId) {
        this.propId = propId;
    }

    public String getPropName() {
        return propName;
    }

    public void setPropName(String propName) {
        this.propName = propName;
    }

    public HashMap<Long, SkuPropertyValue> getPropertyMap() {
        return propertyMap;
    }

    public void setPropertyMap(HashMap<Long, SkuPropertyValue> propertyMap) {
        this.propertyMap = propertyMap;
    }
}
