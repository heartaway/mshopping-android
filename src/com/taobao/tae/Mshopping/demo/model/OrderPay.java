package com.taobao.tae.Mshopping.demo.model;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * 总金额
 * Created by xinyuan on 14/7/9.
 */
public class OrderPay implements Serializable {
    private String id;
    private String quark;
    private String tag;
    private String type;
    private OrderPayFields fields;

    public OrderPay(JSONObject orderPayJsonObj) {
        if (orderPayJsonObj == null) {
            return;
        }
        try {
            if (orderPayJsonObj.has("id")) {
                this.id = orderPayJsonObj.getString("id");
            }
            if (orderPayJsonObj.has("tag")) {
                this.tag = orderPayJsonObj.getString("tag");
            }
            if (orderPayJsonObj.has("quark")) {
                this.quark = orderPayJsonObj.getString("quark");
            }
            if (orderPayJsonObj.has("type")) {
                this.type = orderPayJsonObj.getString("type");
            }
            if (orderPayJsonObj.has("fields")) {
                this.fields = new OrderPayFields(orderPayJsonObj.getJSONObject("fields"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public class OrderPayFields {
        private String price;
        private String quantity;
        private Boolean hasService;
        private Boolean isOrderGroup;
        private String weight;

        public OrderPayFields(JSONObject fieldsJsonObj) {
            try {
                if (fieldsJsonObj.has("hasService")) {
                    this.hasService = fieldsJsonObj.getBoolean("hasService");
                }
                if (fieldsJsonObj.has("price")) {
                    this.price = fieldsJsonObj.getString("price");
                }
                if (fieldsJsonObj.has("quantity")) {
                    this.quantity = fieldsJsonObj.getString("quantity");
                }
                if (fieldsJsonObj.has("isOrderGroup")) {
                    this.isOrderGroup = fieldsJsonObj.getBoolean("isOrderGroup");
                }
                if (fieldsJsonObj.has("weight")) {
                    this.weight = fieldsJsonObj.getString("weight");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
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

        public Boolean getHasService() {
            return hasService;
        }

        public void setHasService(Boolean hasService) {
            this.hasService = hasService;
        }

        public Boolean getIsOrderGroup() {
            return isOrderGroup;
        }

        public void setIsOrderGroup(Boolean isOrderGroup) {
            this.isOrderGroup = isOrderGroup;
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

    public OrderPayFields getFields() {
        return fields;
    }

    public void setFields(OrderPayFields fields) {
        this.fields = fields;
    }
}
