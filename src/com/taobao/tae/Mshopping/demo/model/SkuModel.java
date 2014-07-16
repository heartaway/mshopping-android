package com.taobao.tae.Mshopping.demo.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by xinyuan on 14/7/7.
 */
public class SkuModel implements Serializable {
    private ItemUnitCotrol itemUnitCotrol;
    private HashMap<Long, SkuProperty> skuPropertyMap;
    private HashMap<String, String> ppathIdmap;
    private HashMap<String, ShowItemSku> skuPriceAndQuantity;
    private DefaultShowItemSku defaultShowItemSku;
    private Long relationImgUrlPropId;//如果商品SKU存在关联商品图片变动的属性的话，此字段为属性ID

    public SkuModel(JSONObject skuModelObject, JSONObject apiStackDataObject) {
        try {
            if (skuModelObject != null) {
                initSkuPropertyMap(skuModelObject.getJSONArray("skuProps"));
                initSkuPpathMap(skuModelObject.getJSONObject("ppathIdmap"));
            }
            if (apiStackDataObject != null) {
                initDefaultShowItemSku(apiStackDataObject);
                initSkuPriceAndQuantityMap(apiStackDataObject);
                initItemUnitControl(apiStackDataObject);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


    /**
     * 初始化商品SKU组合时的商品价格、库存
     */
    private void initSkuPriceAndQuantityMap(JSONObject apiStackDataObject) {
        try {
            JSONObject jsonObject;
            skuPriceAndQuantity = new HashMap<String, ShowItemSku>();
            if (apiStackDataObject.has("skuModel")) {
                jsonObject = apiStackDataObject.getJSONObject("skuModel");
                JSONObject skus = jsonObject.getJSONObject("skus");
                Iterator iterator = skus.keys();
                while (iterator.hasNext()) {
                    try {
                        ShowItemSku showItemSku = new ShowItemSku();
                        String key = (String) iterator.next();
                        String value = skus.getString(key);
                        JSONObject sku = new JSONObject(value);

                        HashMap<Integer, PriceUnit> priceUnitHashMap = new HashMap<Integer, PriceUnit>();
                        JSONArray priceUnits = sku.getJSONArray("priceUnits");
                        for (int i = 0; i < priceUnits.length(); i++) {
                            PriceUnit priceUnit = new PriceUnit();
                            JSONObject json = new JSONObject(priceUnits.get(i).toString());
                            if (json.has("name")) {
                                priceUnit.setName(json.getString("name"));
                            }
                            if (json.has("price")) {
                                priceUnit.setPrice(json.getString("price"));
                            }
                            if (json.has("display")) {
                                priceUnit.setDisplay(json.getInt("display"));
                            }
                            priceUnitHashMap.put(priceUnit.getDisplay(), priceUnit);
                        }
                        showItemSku.setPriceUnits(priceUnitHashMap);
                        showItemSku.setQuantity(sku.getInt("quantity"));
                        skuPriceAndQuantity.put(key, showItemSku);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


    /**
     * 初始化默认的商品SKU信息
     *
     * @param apiStackDataObject
     */
    private void initDefaultShowItemSku(JSONObject apiStackDataObject) {
        this.defaultShowItemSku = new DefaultShowItemSku();
        JSONObject jsonObject;
        try {
            if (apiStackDataObject.has("itemInfoModel")) {
                jsonObject = apiStackDataObject.getJSONObject("itemInfoModel");
                defaultShowItemSku.setTotalSoldQuantity(jsonObject.getInt("totalSoldQuantity"));
                defaultShowItemSku.setQuantity(jsonObject.getInt("quantity"));
                HashMap<Integer, PriceUnit> priceUnitHashMap = new HashMap<Integer, PriceUnit>();
                JSONArray priceUnits = jsonObject.getJSONArray("priceUnits");
                for (int i = 0; i < priceUnits.length(); i++) {
                    PriceUnit priceUnit = new PriceUnit();
                    JSONObject json = new JSONObject(priceUnits.get(i).toString());
                    if (json.has("name")) {
                        priceUnit.setName(json.getString("name"));
                    }
                    if (json.has("price")) {
                        priceUnit.setPrice(json.getString("price"));
                    }
                    if (json.has("display")) {
                        priceUnit.setDisplay(json.getInt("display"));
                    }
                    priceUnitHashMap.put(priceUnit.getDisplay(), priceUnit);
                }
                defaultShowItemSku.setPriceUnits(priceUnitHashMap);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * 初始化商品SKU属性
     *
     * @param jsonArray
     */
    private void initSkuPropertyMap(JSONArray jsonArray) {
        this.skuPropertyMap = new HashMap<Long, SkuProperty>();
        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                JSONObject jsonObject = new JSONObject(jsonArray.get(i).toString());
                SkuProperty skuProperty = new SkuProperty();
                skuProperty.setPropId(jsonObject.getLong("propId"));
                skuProperty.setPropName(jsonObject.getString("propName"));
                JSONArray skuValueArray = jsonObject.getJSONArray("values");
                HashMap<Long, SkuPropertyValue> propertyMap = new HashMap<Long, SkuPropertyValue>();
                for (int j = 0; j < skuValueArray.length(); j++) {
                    JSONObject sku = new JSONObject(skuValueArray.get(j).toString());
                    propertyMap.put(sku.getLong("valueId"), new SkuPropertyValue(sku));
                }
                skuProperty.setPropertyMap(propertyMap);
                skuPropertyMap.put(skuProperty.getPropId(), skuProperty);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * 初始化商品SKU映射表
     */
    private void initSkuPpathMap(JSONObject jsonObject) {
        this.ppathIdmap = new HashMap<String, String>();
        Iterator iterator = jsonObject.keys();
        while (iterator.hasNext()) {
            try {
                String key = (String) iterator.next();
                String value = jsonObject.getString(key);
                ppathIdmap.put(key, value);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 商品SKU属性是否存在关联商品图片的字段
     *
     * @return
     */
    public Boolean hasRelationImgUrl() {
        if (skuPropertyMap == null || skuPropertyMap.size() == 0) {
            return false;
        }
        Boolean result = false;
        for (Map.Entry entry : skuPropertyMap.entrySet()) {
            SkuProperty skuProperty = (SkuProperty) entry.getValue();
            result = skuProperty.isRelationImgUrl();
            if (result) {
                relationImgUrlPropId = skuProperty.getPropId();
                break;
            }
        }
        return result;
    }

    /**
     * 根据 ppath 获取 ppathId
     *
     * @param ppath
     * @return
     */
    public String getPpathIdByPath(String ppath) {
        if (ppathIdmap == null) {
            return "";
        }
        return ppathIdmap.get(ppath);
    }


    private void initItemUnitControl(JSONObject apiStackDataObject) {
        try {
            if (apiStackDataObject.has("itemControl") && apiStackDataObject.getJSONObject("itemControl").has("unitControl")) {
                itemUnitCotrol = new ItemUnitCotrol(apiStackDataObject.getJSONObject("itemControl").getJSONObject("unitControl"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * 根据 ppathId 获取商品价格单元
     *
     * @param ppathId
     * @return
     */
    public ShowItemSku getPriceUnitsByPpathId(String ppathId) {
        return this.getSkuPriceAndQuantity().get(ppathId);
    }

    /**
     * 根据 ppath 获取商品价格单元
     *
     * @param ppath
     * @return
     */
    public ShowItemSku getPriceUnitsByPpath(String ppath) {
        return getPriceUnitsByPpathId(getPpathIdByPath(ppath));
    }


    /**
     * 根据 ppathId 获取商品库存
     *
     * @param ppathId
     * @return
     */
    public Integer getQuantity(String ppathId) {
        return null;
    }

    public DefaultShowItemSku getDefaultShowItemSku() {
        return defaultShowItemSku;
    }

    public void setDefaultShowItemSku(DefaultShowItemSku defaultShowItemSku) {
        this.defaultShowItemSku = defaultShowItemSku;
    }

    public HashMap<Long, SkuProperty> getSkuPropertyMap() {
        return skuPropertyMap;
    }

    public void setSkuPropertyMap(HashMap<Long, SkuProperty> skuPropertyMap) {
        this.skuPropertyMap = skuPropertyMap;
    }


    public HashMap<String, ShowItemSku> getSkuPriceAndQuantity() {
        return skuPriceAndQuantity;
    }

    public void setSkuPriceAndQuantity(HashMap<String, ShowItemSku> skuPriceAndQuantity) {
        this.skuPriceAndQuantity = skuPriceAndQuantity;
    }

    public Long getRelationImgUrlPropId() {
        return relationImgUrlPropId;
    }


    public ItemUnitCotrol getItemUnitCotrol() {
        return itemUnitCotrol;
    }

    public void setItemUnitCotrol(ItemUnitCotrol itemUnitCotrol) {
        this.itemUnitCotrol = itemUnitCotrol;
    }
}
