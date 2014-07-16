package com.taobao.tae.Mshopping.demo.model;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by xinyuan on 14/7/10.
 */
public class ItemInfo {
    private String id;
    private String quark;
    private String tag;
    private String type;
    private ItemInfoFields fields;

    public ItemInfo(JSONObject itemInfoJsonObj) {
        if (itemInfoJsonObj == null) {
            return;
        }
        try {
            if (itemInfoJsonObj.has("id")) {
                this.id = itemInfoJsonObj.getString("id");
            }
            if (itemInfoJsonObj.has("tag")) {
                this.tag = itemInfoJsonObj.getString("tag");
            }
            if (itemInfoJsonObj.has("quark")) {
                this.quark = itemInfoJsonObj.getString("quark");
            }
            if (itemInfoJsonObj.has("type")) {
                this.type = itemInfoJsonObj.getString("type");
            }
            if (itemInfoJsonObj.has("fields")) {
                this.fields = new ItemInfoFields(itemInfoJsonObj.getJSONObject("fields"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public class ItemInfoFields {
        private String itemUrl;
        private String pic;
        private String price;
        private String sellerNick;
        private String skuInfo;//Json 串存储
        private String title;

        public ItemInfoFields(JSONObject fieldsJsonObj) {
            try {
                if (fieldsJsonObj.has("itemUrl")) {
                    this.itemUrl = fieldsJsonObj.getString("itemUrl");
                }
                if (fieldsJsonObj.has("pic")) {
                    this.pic = fieldsJsonObj.getString("pic");
                }
                if (fieldsJsonObj.has("price")) {
                    this.price = fieldsJsonObj.getString("price");
                }
                if (fieldsJsonObj.has("sellerNick")) {
                    this.sellerNick = fieldsJsonObj.getString("sellerNick");
                }
                if (fieldsJsonObj.has("skuInfo")) {
                    this.skuInfo = fieldsJsonObj.getString("skuInfo");
                }
                if (fieldsJsonObj.has("title")) {
                    this.title = fieldsJsonObj.getString("title");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        public String getItemUrl() {
            return itemUrl;
        }

        public void setItemUrl(String itemUrl) {
            this.itemUrl = itemUrl;
        }

        public String getPic() {
            return pic;
        }

        public void setPic(String pic) {
            this.pic = pic;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getSellerNick() {
            return sellerNick;
        }

        public void setSellerNick(String sellerNick) {
            this.sellerNick = sellerNick;
        }

        public String getSkuInfo() {
            return skuInfo;
        }

        public void setSkuInfo(String skuInfo) {
            this.skuInfo = skuInfo;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
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

    public ItemInfoFields getFields() {
        return fields;
    }

    public void setFields(ItemInfoFields fields) {
        this.fields = fields;
    }
}
