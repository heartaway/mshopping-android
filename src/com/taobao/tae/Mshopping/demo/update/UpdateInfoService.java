package com.taobao.tae.Mshopping.demo.update;

import android.content.Context;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class UpdateInfoService {
	
    private Context context;
    
    public UpdateInfoService(Context context)
    {
            this.context = context;
    }
    public UpdateInfo getUpdateInfo(int urlId) throws Exception
    {
            String path = context.getResources().getString(urlId);//拿到config.xml里面存放的地址
            URL url = new URL(path);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();//开启一个http链接
            httpURLConnection.setConnectTimeout(5000);//设置链接的超时时间，现在为5秒
            httpURLConnection.setRequestMethod("GET");//设置请求的方式
            InputStream is = httpURLConnection.getInputStream();//拿到一个输入流。里面包涵了update.xml的信息
            return UpdateInfoParser.getUpdateInfo(is);//解析xml
    }

}
