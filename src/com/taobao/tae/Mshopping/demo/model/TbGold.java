package com.taobao.tae.Mshopping.demo.model;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by xinyuan on 14/7/10.
 */
public class TbGold {
    private String id;
    private String btn;
    private String tag;
    private String type;
    private String fields;
    private Boolean submit;

    public TbGold(JSONObject tbGoldJsonObj) {
        if (tbGoldJsonObj == null) {
            return;
        }
        try {
            if (tbGoldJsonObj.has("id")) {
                this.id = tbGoldJsonObj.getString("id");
            }
            if (tbGoldJsonObj.has("tag")) {
                this.tag = tbGoldJsonObj.getString("tag");
            }
            if (tbGoldJsonObj.has("btn")) {
                this.btn = tbGoldJsonObj.getString("btn");
            }
            if (tbGoldJsonObj.has("type")) {
                this.type = tbGoldJsonObj.getString("type");
            }
            if (tbGoldJsonObj.has("submit")) {
                this.submit = tbGoldJsonObj.getBoolean("submit");
            }else{
                this.submit = false;
            }
            if (tbGoldJsonObj.has("fields")) {
                this.fields = tbGoldJsonObj.getString("fields");
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
