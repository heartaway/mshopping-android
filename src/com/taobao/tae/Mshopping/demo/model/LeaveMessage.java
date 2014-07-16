package com.taobao.tae.Mshopping.demo.model;

import com.google.gson.Gson;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * 给卖家留言
 * Created by xinyuan on 14/7/9.
 */
public class LeaveMessage implements Serializable {

    private String btn;
    private String id;
    private Boolean submit;
    private String tag;
    private String type;
    private LeaveMessageFields fields;


    public LeaveMessage(JSONObject leaveMessageJsonObj) {
        if (leaveMessageJsonObj == null) {
            return;
        }
        try {
            if (leaveMessageJsonObj.has("btn")) {
                this.btn = leaveMessageJsonObj.getString("btn");
            }
            if (leaveMessageJsonObj.has("id")) {
                this.id = leaveMessageJsonObj.getString("id");
            }
            if (leaveMessageJsonObj.has("submit")) {
                this.submit = leaveMessageJsonObj.getBoolean("submit");
            } else {
                this.submit = false;
            }
            if (leaveMessageJsonObj.has("tag")) {
                this.tag = leaveMessageJsonObj.getString("tag");
            }
            if (leaveMessageJsonObj.has("type")) {
                this.type = leaveMessageJsonObj.getString("type");
            }
            if (leaveMessageJsonObj.has("fields")) {
                this.fields = new LeaveMessageFields(leaveMessageJsonObj.getJSONObject("fields"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public String toJson() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    public class LeaveMessageFields {
        private String name;
        private String placeholder;
        private String value;

        public LeaveMessageFields(JSONObject fieldsJsonObj) {
            try {
                if (fieldsJsonObj.has("name")) {
                    this.name = fieldsJsonObj.getString("name");
                }
                if (fieldsJsonObj.has("placeholder")) {
                    this.placeholder = fieldsJsonObj.getString("placeholder");
                }
                if (fieldsJsonObj.has("value")) {
                    this.value = fieldsJsonObj.getString("value");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPlaceholder() {
            return placeholder;
        }

        public void setPlaceholder(String placeholder) {
            this.placeholder = placeholder;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }

    public String getBtn() {
        return btn;
    }

    public void setBtn(String btn) {
        this.btn = btn;
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

    public LeaveMessageFields getFields() {
        return fields;
    }

    public void setFields(LeaveMessageFields fields) {
        this.fields = fields;
    }

}
