package com.taobao.tae.Mshopping.demo.model;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * 商品优惠
 * Created by xinyuan on 14/7/9.
 */
public class ItemPromotion implements Serializable {
    private String id;
    private String quark;
    private String tag;
    private String type;
    private Boolean submit;
    private String fields;

    public ItemPromotion(JSONObject itemPayJsonObj) {
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
            if (itemPayJsonObj.has("type")) {
                this.type = itemPayJsonObj.getString("type");
            }
            if(itemPayJsonObj.has("submit")){
                this.submit = itemPayJsonObj.getBoolean("submit");
            }else{
                this.submit = false;
            }
            if (itemPayJsonObj.has("fields")) {
                this.fields = itemPayJsonObj.getString("fields");
            }
        } catch (JSONException e) {
            e.printStackTrace();
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

    public String getFields() {
        return fields;
    }

    public void setFields(String fields) {
        this.fields = fields;
    }

    public Boolean getSubmit() {
        return submit;
    }

    public void setSubmit(Boolean submit) {
        this.submit = submit;
    }
}
