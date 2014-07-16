package com.taobao.tae.Mshopping.demo.model;

import java.io.Serializable;
import java.util.*;

/**
 * Created by xinyuan on 14/7/8.
 */
public class SkuSelect implements Serializable {
    // key 为 propId
    private HashMap<Long, SkuPropertySelect> skuSelectMap;

    public SkuSelect() {
        this.skuSelectMap = new HashMap<Long, SkuPropertySelect>();
    }

    /**
     * 用户是否选择了所有必须选择的SKU属性
     *
     * @return
     */
    public Boolean isSelectedAllSkus() {
        Boolean result = false;
        for (Map.Entry entry : skuSelectMap.entrySet()) {
            SkuPropertySelect skuPropertySelect = (SkuPropertySelect) entry.getValue();
            if (!skuPropertySelect.isSelected) {
                return result;
            }
        }
        return true;
    }

    /**
     * 获取商品SKU属性名称的连接串
     *
     * @return
     */
    public String getPropNameString() {
        String result = "";
        for (Map.Entry entry : skuSelectMap.entrySet()) {
            SkuPropertySelect skuPropertySelect = (SkuPropertySelect) entry.getValue();
            result = result + "," + skuPropertySelect.getPropName();
        }
        return result;
    }

    /**
     * 获取 用户选择的商品属性的属性名称的连接字符串
     *
     * @return
     */
    public String getUserSelectSkuPropNameString() {
        String result = "";
        for (Map.Entry entry : skuSelectMap.entrySet()) {
            SkuPropertySelect skuPropertySelect = (SkuPropertySelect) entry.getValue();
            result = result + "; " + skuPropertySelect.getPropName() + ":" + skuPropertySelect.getSkuName();
        }
        result.trim();
        if (result.startsWith(";")) {
            result = result.substring(1, result.length());
        }
        return result;
    }

    /**
     * 先根据 propId 进行排序
     *
     * @return
     */
    public String getPpath() {
        List<SkuPropertySelect> sortedSkuProperties = new ArrayList<SkuPropertySelect>();
        for (Map.Entry entry : skuSelectMap.entrySet()) {
            SkuPropertySelect skuPropertySelect = (SkuPropertySelect) entry.getValue();
            sortedSkuProperties.add(skuPropertySelect);
        }
        Collections.sort(sortedSkuProperties);
        StringBuilder builder = new StringBuilder();
        for (SkuPropertySelect sku : sortedSkuProperties) {
            builder.append(sku.getPropId());
            builder.append(":");
            builder.append(sku.getSkuId());
            builder.append(";");
        }
        String result = builder.toString();
        if (result.endsWith(";")) {
            result = result.substring(0, result.length() - 1);
        }
        return result;
    }

    public void put(Long propId, String propName) {
        SkuPropertySelect skuPropertySelect = new SkuPropertySelect();
        skuPropertySelect.setPropId(propId);
        skuPropertySelect.setPropName(propName);
        skuPropertySelect.setSelected(false);
        this.skuSelectMap.put(propId, skuPropertySelect);
    }

    public void remove(Long propId) {
        this.skuSelectMap.remove(propId.toString());
    }

    public void setSelectedSkuId(Long propId, Long skuId, String skuName) {
        this.skuSelectMap.get(propId).setSkuId(skuId);
        this.skuSelectMap.get(propId).setSkuName(skuName);
        this.skuSelectMap.get(propId).setSelected(Boolean.TRUE);
    }


    public class SkuPropertySelect implements Comparable<SkuPropertySelect>, Serializable {
        private boolean isSelected;
        private String propName;
        private Long propId;
        private Long skuId;
        private String skuName;

        @Override
        public int compareTo(SkuPropertySelect skuPropertySelect) {
            return this.getPropId().compareTo(skuPropertySelect.getPropId());
        }

        public boolean isSelected() {
            return isSelected;
        }

        public void setSelected(boolean isSelected) {
            this.isSelected = isSelected;
        }

        public String getPropName() {
            return propName;
        }

        public void setPropName(String propName) {
            this.propName = propName;
        }

        public Long getPropId() {
            return propId;
        }

        public void setPropId(Long propId) {
            this.propId = propId;
        }

        public Long getSkuId() {
            return skuId;
        }

        public void setSkuId(Long skuId) {
            this.skuId = skuId;
        }

        public String getSkuName() {
            return skuName;
        }

        public void setSkuName(String skuName) {
            this.skuName = skuName;
        }
    }

    public HashMap<Long, SkuPropertySelect> getSkuSelectMap() {
        return skuSelectMap;
    }

    public void setSkuSelectMap(HashMap<Long, SkuPropertySelect> skuSelectMap) {
        this.skuSelectMap = skuSelectMap;
    }
}
