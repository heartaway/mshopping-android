package com.taobao.tae.Mshopping.demo.api.internal;

import retrofit.RequestInterceptor;

/**
 *
 * <P></P>
 * User: <a href="mailto:xhm.xuhm@alibaba-inc.com">苍旻</a>
 * Date: 14-2-19
 * Time: 上午10:51
 */
public class AlipushRequestInterceptor implements RequestInterceptor {

    @Override
    public void intercept(RequestFacade request) {
//        request.addHeader("Date", DateUtil.formatRfc822Date(new Date()));
//        request.addHeader("Content-Type", "text/xml;charset=UTF-8");
    }

}
