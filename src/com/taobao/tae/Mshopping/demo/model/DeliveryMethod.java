package com.taobao.tae.Mshopping.demo.model;

import com.google.gson.Gson;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 配送方式
 * Created by xinyuan on 14/7/9.
 */
public class DeliveryMethod implements Serializable {

    private String id;
    private String btn;
    private String quark;
    private Boolean submit;
    private String tag;
    private String type;
    private DeliveryMethodFields fields;


    public DeliveryMethod(JSONObject deliveryJsonObj) {
        if (deliveryJsonObj == null) {
            return;
        }
        try {
            if (deliveryJsonObj.has("btn")) {
                this.btn = deliveryJsonObj.getString("btn");
            }
            if (deliveryJsonObj.has("id")) {
                this.id = deliveryJsonObj.getString("id");
            }
            if (deliveryJsonObj.has("quark")) {
                this.quark = deliveryJsonObj.getString("quark");
            }
            if (deliveryJsonObj.has("submit")) {
                this.submit = deliveryJsonObj.getBoolean("submit");
            } else {
                this.submit = false;
            }
            if (deliveryJsonObj.has("tag")) {
                this.tag = deliveryJsonObj.getString("tag");
            }
            if (deliveryJsonObj.has("type")) {
                this.type = deliveryJsonObj.getString("type");
            }
            if (deliveryJsonObj.has("fields")) {
                this.fields = new DeliveryMethodFields(deliveryJsonObj.getJSONObject("fields"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    /**
     * 获取用户选择的收货地址连接串
     *
     * @return
     */
    public DeliveryFieldsOption getSelectedDeliveryMethod() {
        return fields.getOptions().get(fields.getSelectedId());
    }


    public String toJson() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("id", id);
            jsonObject.put("btn", btn);
            jsonObject.put("quark", quark);
            jsonObject.put("submit", submit);
            jsonObject.put("tag", tag);
            jsonObject.put("type", type);
            jsonObject.put("fields", new JSONObject(fields.toJson()));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject.toString();
    }


    public class DeliveryMethodFields {
        private String selectedId;
        private String title;
        private Map<String, DeliveryFieldsOption> options;

        public DeliveryMethodFields(JSONObject fieldsJsonObj) {
            try {
                if (fieldsJsonObj.has("title")) {
                    this.title = fieldsJsonObj.getString("title");
                }
                if (fieldsJsonObj.has("selectedId")) {
                    this.selectedId = fieldsJsonObj.getString("selectedId");
                }
                if (fieldsJsonObj.has("options")) {
                    options = new HashMap<String, DeliveryFieldsOption>();
                    JSONArray optionsJsonArray = fieldsJsonObj.getJSONArray("options");
                    for (int i = 0; i < optionsJsonArray.length(); i++) {
                        JSONObject jsonObject = optionsJsonArray.getJSONObject(i);
                        DeliveryFieldsOption fieldsOptions = new DeliveryFieldsOption(jsonObject);
                        options.put(fieldsOptions.getOptionId(), fieldsOptions);
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        public String toJson() {
            JSONObject jsonObject = new JSONObject();
            try {
                Gson gson = new Gson();
                String optionsJson = gson.toJson(convertMapToList(options));
                jsonObject.put("selectedId", selectedId);
                jsonObject.put("title", title);
                jsonObject.put("options", new JSONArray(optionsJson));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return jsonObject.toString();
        }

        private List convertMapToList(Map<String, DeliveryFieldsOption> map) {
            List list = new ArrayList();
            for (Map.Entry entry : map.entrySet()) {
                list.add(entry.getValue());
            }
            return list;
        }

        public String getSelectedId() {
            return selectedId;
        }

        public void setSelectedId(String selectedId) {
            this.selectedId = selectedId;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public Map<String, DeliveryFieldsOption> getOptions() {
            return options;
        }

        public void setOptions(Map<String, DeliveryFieldsOption> options) {
            this.options = options;
        }
    }


    public class DeliveryFieldsOption {
        private String optionId;
        private String name;
        private String value;

        public DeliveryFieldsOption(JSONObject optionsJsonObj) {
            try {
                if (optionsJsonObj.has("optionId")) {
                    this.optionId = optionsJsonObj.getString("optionId");
                }
                if (optionsJsonObj.has("name")) {
                    this.name = optionsJsonObj.getString("name");
                }
                if (optionsJsonObj.has("value")) {
                    this.value = optionsJsonObj.getString("value");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        public String getOptionId() {
            return optionId;
        }

        public void setOptionId(String optionId) {
            this.optionId = optionId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
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

    public String getQuark() {
        return quark;
    }

    public void setQuark(String quark) {
        this.quark = quark;
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

    public DeliveryMethodFields getFields() {
        return fields;
    }

    public void setFields(DeliveryMethodFields fields) {
        this.fields = fields;
    }

}
