package com.taobao.tae.Mshopping.demo.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by xinyuan on 14/7/4.
 */
public class EvaluateInfo  implements Serializable {
    //发货速度
    private SingleEvaluateInfo deliverySpeed = new SingleEvaluateInfo("发货速度");
    //服务态度
    private SingleEvaluateInfo serviceAttitude = new SingleEvaluateInfo("服务态度");
    //宝贝与描述相符
    private SingleEvaluateInfo descriptionMatch = new SingleEvaluateInfo("描述相符");

    public EvaluateInfo(String evaluateJsonArray) {
        try {
            JSONArray jsonArray = new JSONArray(evaluateJsonArray);
            for (int i = 0; i < jsonArray.length(); i++) {
                if (jsonArray.get(i) != null) {
                    JSONObject jsonObject = new JSONObject(jsonArray.get(i).toString());
                    if (jsonObject.getString("title") != null && jsonObject.getString("title").contains("发货")) {
                        deliverySpeed.setHighGap(jsonObject.getString("highGap"));
                        deliverySpeed.setScore(jsonObject.getString("score"));
                    } else if (jsonObject.getString("title") != null && jsonObject.getString("title").contains("态度")) {
                        serviceAttitude.setHighGap(jsonObject.getString("highGap"));
                        serviceAttitude.setScore(jsonObject.getString("score"));
                    } else if (jsonObject.getString("title") != null && jsonObject.getString("title").contains("相符")) {
                        descriptionMatch.setHighGap(jsonObject.getString("highGap"));
                        descriptionMatch.setScore(jsonObject.getString("score"));
                    }
                } else {
                    continue;
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public class SingleEvaluateInfo implements Serializable{
        private String title;
        private String score;
        private String highGap;

        public SingleEvaluateInfo(String title) {
            this.title = title;
        }

        public String getTitle() {
            return title;
        }

        public String getScore() {
            return score;
        }

        public void setScore(String score) {
            this.score = score;
        }

        public String getHighGap() {
            return highGap;
        }

        public void setHighGap(String highGap) {
            this.highGap = highGap;
        }
    }

}
