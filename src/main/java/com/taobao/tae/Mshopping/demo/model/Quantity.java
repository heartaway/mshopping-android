package com.taobao.tae.Mshopping.demo.model;

import com.google.gson.Gson;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by xinyuan on 14/7/10.
 */
public class Quantity {
    private String id;
    private Boolean submit;
    private String tag;
    private String type;
    private String fields;

    public Quantity(JSONObject itemPayJsonObj) {
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
            if (itemPayJsonObj.has("type")) {
                this.type = itemPayJsonObj.getString("type");
            }
            if (itemPayJsonObj.has("submit")) {
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

    public String toJson() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("id", id);
            jsonObject.put("submit", submit);
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

    public Boolean getSubmit() {
        return submit;
    }

    public void setSubmit(Boolean submit) {
        this.submit = submit;
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
}
