package com.taobao.tae.Mshopping.demo.api.internal;


import com.google.gson.Gson;
import com.taobao.api.ApiException;
import com.taobao.tae.Mshopping.demo.model.BaseApiResult;
import com.taobao.tae.Mshopping.demo.util.GsonUtil;
import com.taobao.tae.Mshopping.demo.util.LogUtil;
import com.taobao.tae.Mshopping.demo.util.ResponseUtils;
import org.json.JSONObject;
import retrofit.ErrorHandler;
import retrofit.RetrofitError;
import retrofit.client.Response;

import java.io.InputStream;

/**
 *
 * <P></P>
 * User: <a href="mailto:xhm.xuhm@alibaba-inc.com">苍旻</a>
 * Date: 14-2-19
 * Time: 下午7:51
 */
public class AlipushErrorHandler implements ErrorHandler {
    @Override
    public Throwable handleError(RetrofitError cause) {
        Response r = cause.getResponse();
        if (r != null&&r.getStatus()>=400) {
            try {
                InputStream bodyStream = r.getBody().in();
                byte[] body = ResponseUtils.readStream(bodyStream);
                String msg = new String(body, "UTF-8");
                BaseApiResult result = GsonUtil.g.fromJson(msg, BaseApiResult.class);
                if (result==null){
                    result = new BaseApiResult();
                    result.setMsg("system error");
                    result.setCode(-400);
                }
                return new ApiException(result.toString());
            } catch (Exception e) {
                LogUtil.e(r.getReason() + r.getStatus() + e.getMessage() , e);
                BaseApiResult result =new BaseApiResult();
                result.setMsg("system error");
                result.setCode(-500);
                return new ApiException(result.toString());
            }

        }
        return cause;
    }

}
