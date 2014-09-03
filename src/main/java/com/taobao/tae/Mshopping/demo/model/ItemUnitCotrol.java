package com.taobao.tae.Mshopping.demo.model;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by xinyuan on 14/7/14.
 */
public class ItemUnitCotrol implements Serializable {

    private boolean buySupport;
    private boolean cartSupport;
    private String errorMessage;
    private String cartText;
    private String buyText;
    private String submitText;

    public ItemUnitCotrol(JSONObject jsonObject) {
        if (jsonObject == null) {
            return;
        }
        try {
            if (jsonObject.has("buySupport")) {
                this.buySupport = jsonObject.getBoolean("buySupport");
            }
            if (jsonObject.has("cartSupport")) {
                this.cartSupport = jsonObject.getBoolean("cartSupport");
            }
            if (jsonObject.has("errorMessage")) {
                this.errorMessage = jsonObject.getString("errorMessage");
            }
            if (jsonObject.has("cartText")) {
                this.cartText = jsonObject.getString("cartText");
            }
            if (jsonObject.has("buyText")) {
                this.buyText = jsonObject.getString("buyText");
            }
            if (jsonObject.has("submitText")) {
                this.submitText = jsonObject.getString("submitText");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public boolean isBuySupport() {
        return buySupport;
    }

    public void setBuySupport(boolean buySupport) {
        this.buySupport = buySupport;
    }

    public boolean isCartSupport() {
        return cartSupport;
    }

    public void setCartSupport(boolean cartSupport) {
        this.cartSupport = cartSupport;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getCartText() {
        return cartText;
    }

    public void setCartText(String cartText) {
        this.cartText = cartText;
    }

    public String getBuyText() {
        return buyText;
    }

    public void setBuyText(String buyText) {
        this.buyText = buyText;
    }

    public String getSubmitText() {
        return submitText;
    }

    public void setSubmitText(String submitText) {
        this.submitText = submitText;
    }
}
