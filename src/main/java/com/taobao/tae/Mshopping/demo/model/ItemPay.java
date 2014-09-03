package com.taobao.tae.Mshopping.demo.model;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * 商品小计
 * Created by xinyuan on 14/7/9.
 */
public class ItemPay implements Serializable {
    private String id;
    private String quark;
    private String tag;
    private String type;
    private Boolean submit;
    private ItemPayFields fields;

    public ItemPay(JSONObject itemPayJsonObj) {
        if (itemPayJsonObj == null) {
            return;
        }
        try {
            if (itemPayJsonObj.has("id")) {
                this.id = itemPayJsonObj.getString("id");
            }
            if (itemPayJsonObj.has("tag")) {
                this.tag = itemPayJsonObj.getString("tag");
            }
            if (itemPayJsonObj.has("quark")) {
                this.quark = itemPayJsonObj.getString("quark");
            }
            if(itemPayJsonObj.has("submit")){
                this.submit = itemPayJsonObj.getBoolean("submit");
            }else {
                this.submit = false;
            }
            if (itemPayJsonObj.has("type")) {
                this.type = itemPayJsonObj.getString("type");
            }
            if (itemPayJsonObj.has("fields")) {
                this.fields = new ItemPayFields(itemPayJsonObj.getJSONObject("fields"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public class ItemPayFields {
        private String afterPromotionPrice;
        private String price;
        private String quantity;
        private String unitPrice;
        private String weight;

        public ItemPayFields(JSONObject fieldsJsonObj) {
            try {
                if (fieldsJsonObj.has("afterPromotionPrice")) {
                    this.afterPromotionPrice = fieldsJsonObj.getString("afterPromotionPrice");
                }
                if (fieldsJsonObj.has("price")) {
                    this.price = fieldsJsonObj.getString("price");
                }
                if (fieldsJsonObj.has("quantity")) {
                    this.quantity = fieldsJsonObj.getString("quantity");
                }
                if (fieldsJsonObj.has("unitPrice")) {
                    this.unitPrice = fieldsJsonObj.getString("unitPrice");
                }
                if (fieldsJsonObj.has("weight")) {
                    this.weight = fieldsJsonObj.getString("weight");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        public String getAfterPromotionPrice() {
            return afterPromotionPrice;
        }

        public void setAfterPromotionPrice(String afterPromotionPrice) {
            this.afterPromotionPrice = afterPromotionPrice;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getQuantity() {
            return quantity;
        }

        public void setQuantity(String quantity) {
            this.quantity = quantity;
        }

        public String getUnitPrice() {
            return unitPrice;
        }

        public void setUnitPrice(String unitPrice) {
            this.unitPrice = unitPrice;
        }

        public String getWeight() {
            return weight;
        }

        public void setWeight(String weight) {
            this.weight = weight;
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getQuark() {
        return quark;
    }

    public void setQuark(String quark) {
        this.quark = quark;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public ItemPayFields getFields() {
        return fields;
    }

    public void setFields(ItemPayFields fields) {
        this.fields = fields;
    }

    public Boolean getSubmit() {
        return submit;
    }

    public void setSubmit(Boolean submit) {
        this.submit = submit;
    }
}
