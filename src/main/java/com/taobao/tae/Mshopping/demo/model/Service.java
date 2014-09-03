package com.taobao.tae.Mshopping.demo.model;

import com.google.gson.Gson;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by xinyuan on 14/7/10.
 */
public class Service {
    private String id;
    private String btn;
    private String quark;
    private String tag;
    private String type;
    private String fields;
    private Boolean submit;

    public Service(JSONObject serviceJsonObj) {
        if (serviceJsonObj == null) {
            return;
        }
        try {
            if (serviceJsonObj.has("id")) {
                this.id = serviceJsonObj.getString("id");
            }
            if (serviceJsonObj.has("btn")) {
                this.btn = serviceJsonObj.getString("btn");
            }
            if (serviceJsonObj.has("tag")) {
                this.tag = serviceJsonObj.getString("tag");
            }
            if (serviceJsonObj.has("quark")) {
                this.quark = serviceJsonObj.getString("quark");
            }
            if (serviceJsonObj.has("type")) {
                this.type = serviceJsonObj.getString("type");
            }
            if (serviceJsonObj.has("submit")) {
                this.submit = serviceJsonObj.getBoolean("submit");
            } else {
                this.submit = false;
            }
            if (serviceJsonObj.has("fields")) {
                this.fields = serviceJsonObj.getString("fields");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String toJson() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("id", id);
            jsonObject.put("quark", quark);
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

    public String getBtn() {
        return btn;
    }

    public void setBtn(String btn) {
        this.btn = btn;
    }

    public Boolean getSubmit() {
        return submit;
    }

    public void setSubmit(Boolean submit) {
        this.submit = submit;
    }
}
