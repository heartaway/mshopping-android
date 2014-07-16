package com.taobao.tae.Mshopping.demo.model;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 使用天猫点券
 * Created by xinyuan on 14/7/14.
 */
public class CouponCard {
    private String id;
    private String btn;
    private String tag;
    private String type;
    private Boolean submit;
    private String fields;

    public CouponCard(JSONObject agencyJsonObj) {
        if (agencyJsonObj == null) {
            return;
        }
        try {
            if (agencyJsonObj.has("id")) {
                this.id = agencyJsonObj.getString("id");
            }
            if (agencyJsonObj.has("tag")) {
                this.tag = agencyJsonObj.getString("tag");
            }
            if (agencyJsonObj.has("btn")) {
                this.btn = agencyJsonObj.getString("btn");
            }
            if (agencyJsonObj.has("submit")) {
                this.submit = agencyJsonObj.getBoolean("submit");
            }else{
                this.submit = false;
            }
            if (agencyJsonObj.has("type")) {
                this.type = agencyJsonObj.getString("type");
            }
            if (agencyJsonObj.has("fields")) {
                this.fields = agencyJsonObj.getString("fields");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String toJson() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("id", id);
            jsonObject.put("submit", submit);
            jsonObject.put("btn", btn);
            jsonObject.put("tag", tag);
            jsonObject.put("type", type);
            jsonObject.put("fields", new JSONObject(fields));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject.toString();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBtn() {
        return btn;
    }

    public void setBtn(String btn) {
        this.btn = btn;
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

    public Boolean getSubmit() {
        return submit;
    }

    public void setSubmit(Boolean submit) {
        this.submit = submit;
    }

    public String getFields() {
        return fields;
    }

    public void setFields(String fields) {
        this.fields = fields;
    }
}
