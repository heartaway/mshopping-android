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
 * 买家收货地址
 * Created by xinyuan on 14/7/9.
 */
public class Address implements Serializable {

    //买家ID
    private String id;
    private Boolean submit;
    private String tag;
    private String type;
    private AddressFields fields;

    public Address(JSONObject addressJsonObj) {
        try {
            if (addressJsonObj.has("id")) {
                this.id = addressJsonObj.getString("id");
            }
            if (addressJsonObj.has("submit")) {
                this.submit = addressJsonObj.getBoolean("submit");
            }
            if (addressJsonObj.has("tag")) {
                this.tag = addressJsonObj.getString("tag");
            }
            if (addressJsonObj.has("type")) {
                this.type = addressJsonObj.getString("type");
            }
            if (addressJsonObj.has("fields")) {
                this.fields = new AddressFields(addressJsonObj.getJSONObject("fields"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取用户选择的收获地址连接串
     *
     * @return
     */
    public String getSelectedAddressLikeConcatString() {
        StringBuilder address = new StringBuilder();
        AddressFieldsOptions addressFieldsOptions = fields.getOptions().get(fields.getSelectedId());
        if (addressFieldsOptions.getProvinceName() != null) {
            address.append(addressFieldsOptions.getProvinceName());
        }
        if (addressFieldsOptions.getCityName() != null) {
            address.append(addressFieldsOptions.getCityName());
        }
        if (addressFieldsOptions.getAreaName() != null) {
            address.append(addressFieldsOptions.getAreaName());
        }
        if (addressFieldsOptions.getAddressDetail() != null) {
            address.append(addressFieldsOptions.getAddressDetail());
        }
        return address.toString();
    }


    public String toJson() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("id", id);
            jsonObject.put("submit", submit);
            jsonObject.put("tag", tag);
            jsonObject.put("type", type);
            jsonObject.put("fields", new JSONObject(fields.toJson()));
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

    public AddressFields getFields() {
        return fields;
    }

    public void setFields(AddressFields fields) {
        this.fields = fields;
    }


    public class AddressFields {
        private String agencyReceive;
        private String mdSellerId;
        private String selectedId;
        private Map<String, AddressFieldsOptions> options;

        public AddressFields(JSONObject fieldsJsonObj) {
            try {
                if (fieldsJsonObj.has("agencyReceive")) {
                    this.agencyReceive = fieldsJsonObj.getString("agencyReceive");
                }
                if (fieldsJsonObj.has("mdSellerId")) {
                    this.mdSellerId = fieldsJsonObj.getString("mdSellerId");
                }
                if (fieldsJsonObj.has("selectedId")) {
                    this.selectedId = fieldsJsonObj.getString("selectedId");
                }
                if (fieldsJsonObj.has("options")) {
                    options = new HashMap<String, AddressFieldsOptions>();
                    JSONArray optionsJsonArray = fieldsJsonObj.getJSONArray("options");
                    for (int i = 0; i < optionsJsonArray.length(); i++) {
                        JSONObject jsonObject = optionsJsonArray.getJSONObject(i);
                        AddressFieldsOptions fieldsOptions = new AddressFieldsOptions(jsonObject);
                        options.put(fieldsOptions.getDeliveryAddressId(), fieldsOptions);
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
                jsonObject.put("agencyReceive", agencyReceive);
                jsonObject.put("mdSellerId", mdSellerId);
                jsonObject.put("selectedId", selectedId);
                jsonObject.put("options", new JSONArray(optionsJson));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return jsonObject.toString();
        }

        private List convertMapToList(Map<String, AddressFieldsOptions> map) {
            List list = new ArrayList();
            for (Map.Entry entry : map.entrySet()) {
                list.add(entry.getValue());
            }
            return list;
        }

        public String getAgencyReceive() {
            return agencyReceive;
        }

        public void setAgencyReceive(String agencyReceive) {
            this.agencyReceive = agencyReceive;
        }

        public String getMdSellerId() {
            return mdSellerId;
        }

        public void setMdSellerId(String mdSellerId) {
            this.mdSellerId = mdSellerId;
        }

        public String getSelectedId() {
            return selectedId;
        }

        public void setSelectedId(String selectedId) {
            this.selectedId = selectedId;
        }

        public Map<String, AddressFieldsOptions> getOptions() {
            return options;
        }

        public void setOptions(Map<String, AddressFieldsOptions> options) {
            this.options = options;
        }
    }

    public class AddressFieldsOptions {
        private String addressDetail;
        private String agencyReceiveDesc;
        private String areaName;
        private String cityName;
        private Boolean defaultAddress;
        private String deliveryAddressId;
        private String divisionCode;
        private Boolean enableMDZT;
        private Boolean enableStation;
        private String fullName;
        private String mobile;
        private OverseasTransportInfo overseasTransportInfo;
        private String postCode;
        private String provinceName;
        private String stationId;
        private String tele;
        private String townDivisionId;


        public AddressFieldsOptions(JSONObject optionsJsonObj) {
            try {
                if (optionsJsonObj.has("addressDetail")) {
                    this.addressDetail = optionsJsonObj.getString("addressDetail");
                }
                if (optionsJsonObj.has("agencyReceiveDesc")) {
                    this.agencyReceiveDesc = optionsJsonObj.getString("agencyReceiveDesc");
                }
                if (optionsJsonObj.has("areaName")) {
                    this.areaName = optionsJsonObj.getString("areaName");
                }
                if (optionsJsonObj.has("cityName")) {
                    this.cityName = optionsJsonObj.getString("cityName");
                }
                if (optionsJsonObj.has("defaultAddress")) {
                    this.defaultAddress = optionsJsonObj.getBoolean("defaultAddress");
                }
                if (optionsJsonObj.has("deliveryAddressId")) {
                    this.deliveryAddressId = optionsJsonObj.getString("deliveryAddressId");
                }
                if (optionsJsonObj.has("divisionCode")) {
                    this.divisionCode = optionsJsonObj.getString("divisionCode");
                }
                if (optionsJsonObj.has("enableMDZT")) {
                    this.enableMDZT = optionsJsonObj.getBoolean("enableMDZT");
                }
                if (optionsJsonObj.has("enableStation")) {
                    this.enableStation = optionsJsonObj.getBoolean("enableStation");
                }
                if (optionsJsonObj.has("fullName")) {
                    this.fullName = optionsJsonObj.getString("fullName");
                }
                if (optionsJsonObj.has("mobile")) {
                    this.mobile = optionsJsonObj.getString("mobile");
                }
                if (optionsJsonObj.has("overseasTransportInfo")) {
                    this.overseasTransportInfo = new OverseasTransportInfo(optionsJsonObj.getJSONObject("overseasTransportInfo"));
                }
                if (optionsJsonObj.has("postCode")) {
                    this.postCode = optionsJsonObj.getString("postCode");
                }
                if (optionsJsonObj.has("provinceName")) {
                    this.provinceName = optionsJsonObj.getString("provinceName");
                }
                if (optionsJsonObj.has("stationId")) {
                    this.stationId = optionsJsonObj.getString("stationId");
                }
                if (optionsJsonObj.has("tele")) {
                    this.tele = optionsJsonObj.getString("tele");
                }
                if (optionsJsonObj.has("townDivisionId")) {
                    this.townDivisionId = optionsJsonObj.getString("townDivisionId");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        public String getAddressDetail() {
            return addressDetail;
        }

        public void setAddressDetail(String addressDetail) {
            this.addressDetail = addressDetail;
        }

        public String getAgencyReceiveDesc() {
            return agencyReceiveDesc;
        }

        public void setAgencyReceiveDesc(String agencyReceiveDesc) {
            this.agencyReceiveDesc = agencyReceiveDesc;
        }

        public String getAreaName() {
            return areaName;
        }

        public void setAreaName(String areaName) {
            this.areaName = areaName;
        }

        public String getCityName() {
            return cityName;
        }

        public void setCityName(String cityName) {
            this.cityName = cityName;
        }

        public Boolean getDefaultAddress() {
            return defaultAddress;
        }

        public void setDefaultAddress(Boolean defaultAddress) {
            this.defaultAddress = defaultAddress;
        }

        public String getDeliveryAddressId() {
            return deliveryAddressId;
        }

        public void setDeliveryAddressId(String deliveryAddressId) {
            this.deliveryAddressId = deliveryAddressId;
        }

        public String getDivisionCode() {
            return divisionCode;
        }

        public void setDivisionCode(String divisionCode) {
            this.divisionCode = divisionCode;
        }

        public Boolean getEnableMDZT() {
            return enableMDZT;
        }

        public void setEnableMDZT(Boolean enableMDZT) {
            this.enableMDZT = enableMDZT;
        }

        public Boolean getEnableStation() {
            return enableStation;
        }

        public void setEnableStation(Boolean enableStation) {
            this.enableStation = enableStation;
        }

        public String getFullName() {
            return fullName;
        }

        public void setFullName(String fullName) {
            this.fullName = fullName;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public OverseasTransportInfo getOverseasTransportInfo() {
            return overseasTransportInfo;
        }

        public void setOverseasTransportInfo(OverseasTransportInfo overseasTransportInfo) {
            this.overseasTransportInfo = overseasTransportInfo;
        }

        public String getPostCode() {
            return postCode;
        }

        public void setPostCode(String postCode) {
            this.postCode = postCode;
        }

        public String getProvinceName() {
            return provinceName;
        }

        public void setProvinceName(String provinceName) {
            this.provinceName = provinceName;
        }

        public String getStationId() {
            return stationId;
        }

        public void setStationId(String stationId) {
            this.stationId = stationId;
        }

        public String getTele() {
            return tele;
        }

        public void setTele(String tele) {
            this.tele = tele;
        }

        public String getTownDivisionId() {
            return townDivisionId;
        }

        public void setTownDivisionId(String townDivisionId) {
            this.townDivisionId = townDivisionId;
        }
    }


    public class OverseasTransportInfo {
        private String overseasDivisionId;
        private String overseasUserAddrDivisionId;
        private Boolean supportOverseasTransport;

        public OverseasTransportInfo(JSONObject jsonObject) {
            try {
                if (jsonObject.has("overseasDivisionId")) {
                    this.overseasDivisionId = jsonObject.getString("overseasDivisionId");
                }
                if (jsonObject.has("overseasUserAddrDivisionId")) {
                    this.overseasUserAddrDivisionId = jsonObject.getString("overseasUserAddrDivisionId");
                }
                if (jsonObject.has("supportOverseasTransport")) {
                    this.supportOverseasTransport = jsonObject.getBoolean("supportOverseasTransport");
                }
            } catch (JSONException e) {

            }
        }

        public String getOverseasDivisionId() {
            return overseasDivisionId;
        }

        public void setOverseasDivisionId(String overseasDivisionId) {
            this.overseasDivisionId = overseasDivisionId;
        }

        public String getOverseasUserAddrDivisionId() {
            return overseasUserAddrDivisionId;
        }

        public void setOverseasUserAddrDivisionId(String overseasUserAddrDivisionId) {
            this.overseasUserAddrDivisionId = overseasUserAddrDivisionId;
        }

        public Boolean getSupportOverseasTransport() {
            return supportOverseasTransport;
        }

        public void setSupportOverseasTransport(Boolean supportOverseasTransport) {
            this.supportOverseasTransport = supportOverseasTransport;
        }
    }


}
