package com.taobao.tae.Mshopping.demo.login.taobao;


import java.io.Serializable;
import java.util.Date;
import java.util.Map;
import java.util.Set;

/**
 * OAuth2.0授权成功后得到的AccessToken
 * 可参考top文档：http://open.taobao.com/doc/detail.htm?id=118
 * Created by xinyuan on 14/7/3.
 */
public class AccessToken implements Serializable {

    private static final long serialVersionUID = 7322593516469872908L;

    public static final String KEY_ACCESS_TOKEN = "access_token";
    //refresh token是一个用户对应一个应用唯一刷新标识，用来延长sessionkey的失效时间，其本身也具有失效概念。
    public static final String KEY_REFRESH_TOKEN = "refresh_token";
    //Access token过期时间
    public static final String KEY_EXPIRES_IN = "expires_in";
    //Access token的类型目前只支持bearer
    public static final String KEY_TOKEN_TYPE = "token_type";
    //Refresh token过期时间
    public static final String KEY_RE_EXPIRES_IN = "re_expires_in";
    //r1级别API或字段的访问过期时间
    public static final String KEY_R1_EXPIRES_IN = "r1_expires_in";
    //r2级别API或字段的访问过期时间
    public static final String KEY_R2_EXPIRES_IN = "r2_expires_in";
    //w1级别API或字段的访问过期时间
    public static final String KEY_W1_EXPIRES_IN = "w1_expires_in";
    //w2级别API或字段的访问过期时间
    public static final String KEY_W2_EXPIRES_IN = "w2_expires_in";
    //淘宝帐号对应id
    public static final String KEY_TAOBAO_USER_ID = "taobao_user_id";
    //淘宝账号
    public static final String KEY_TAOBAO_USER_NICK = "taobao_user_nick";
    //淘宝子账号对应id
    public static final String KEY_SUB_TAOBAO_USER_ID = "sub_taobao_user_id";
    //淘宝子账号
    public static final String KEY_SUB_TAOBAO_USER_NICK = "sub_taobao_user_nick";

    public static final String KEY_MOBILE_TOKEN = "mobile_token";

    private String value;
    private Long expiresIn;
    private String tokenType;
    private Long userId;
    private RefreshToken refreshToken;
    private Set<String> scope;
    private Map<String, String> additionalInformation;
    private Date startDate;//失效时间开始计时时间点

    /**
     * 判断 AccessKey是否 有效
     * 如果有效返回 true，失效 返回false
     *
     * @return
     */
    public boolean isSessionValid() {
        if (value == null || "".equals(value)) {
            return false;
        }
        Date currentDate = new Date();
        if (currentDate.getTime() > startDate.getTime() + expiresIn * 1000) {
            return false;
        } else {
            return true;
        }
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Long getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(Long expiresIn) {
        this.expiresIn = expiresIn;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public RefreshToken getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(RefreshToken refreshToken) {
        this.refreshToken = refreshToken;
    }

    public Set<String> getScope() {
        return scope;
    }

    public void setScope(Set<String> scope) {
        this.scope = scope;
    }

    public Map<String, String> getAdditionalInformation() {
        return additionalInformation;
    }

    public void setAdditionalInformation(
            Map<String, String> additionalInformation) {
        this.additionalInformation = additionalInformation;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
