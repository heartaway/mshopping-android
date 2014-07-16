package com.taobao.tae.Mshopping.demo.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * 评价信息
 * Created by xinyuan on 14/7/7.
 */
public class RateInfo implements Serializable {

    /* 总共评价数量rateCounts */
    private Integer rateCounts;

    /* 评价列表 */
    private ArrayList<RateDetail> rateDetailList;

    public RateInfo(String rateInfoJson) {
        try {
            JSONObject rateJsonObject = new JSONObject(rateInfoJson);
            this.rateCounts = rateJsonObject.getInt("rateCounts");
            this.rateDetailList = new ArrayList<RateDetail>();
            if (rateJsonObject.has("rateDetailList")) {
                JSONArray rateDetailJsonArray = rateJsonObject.getJSONArray("rateDetailList");
                for (int i = 0; i < rateDetailJsonArray.length(); i++) {
                    RateDetail rateDetail = new RateDetail();
                    JSONObject json = new JSONObject(rateDetailJsonArray.get(i).toString());
                    rateDetail.setNick(json.getString("nick"));
                    rateDetail.setHeadPic(json.getString("headPic"));
                    rateDetail.setFeedback(json.getString("feedback"));
                    rateDetail.setSubInfo(json.getString("subInfo"));
                    rateDetail.setStar(json.getInt("star"));
                    //TODO暂且不设置ratePicList
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public class RateDetail implements Serializable {
        private String nick;
        private String headPic;
        private Integer star;
        private String feedback;
        private String subInfo;
        private ArrayList<String> ratePicList;

        public String getNick() {
            return nick;
        }

        public void setNick(String nick) {
            this.nick = nick;
        }

        public String getHeadPic() {
            return headPic;
        }

        public void setHeadPic(String headPic) {
            this.headPic = headPic;
        }

        public Integer getStar() {
            return star;
        }

        public void setStar(Integer star) {
            this.star = star;
        }

        public String getFeedback() {
            return feedback;
        }

        public void setFeedback(String feedback) {
            this.feedback = feedback;
        }

        public String getSubInfo() {
            return subInfo;
        }

        public void setSubInfo(String subInfo) {
            this.subInfo = subInfo;
        }

        public ArrayList<String> getRatePicList() {
            return ratePicList;
        }

        public void setRatePicList(ArrayList<String> ratePicList) {
            this.ratePicList = ratePicList;
        }
    }


    public Integer getRateCounts() {
        return rateCounts;
    }

    public void setRateCounts(Integer rateCounts) {
        this.rateCounts = rateCounts;
    }

    public ArrayList<RateDetail> getRateDetailList() {
        return rateDetailList;
    }

    public void setRateDetailList(ArrayList<RateDetail> rateDetailList) {
        this.rateDetailList = rateDetailList;
    }
}
