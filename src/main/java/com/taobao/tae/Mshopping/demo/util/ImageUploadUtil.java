package com.taobao.tae.Mshopping.demo.util;

import com.taobao.tae.Mshopping.demo.R;
import com.taobao.tae.Mshopping.demo.config.AppConfig;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * mulou.zzy
 * taedemo
 */
public class ImageUploadUtil {


    public static String upload(List<File> files, String name) throws IOException {

        HttpClient httpClient = new DefaultHttpClient();

        HttpPost postMethod = new HttpPost(AppConfig.getInstance().getServer() + "/image/upload");
        MultipartEntity mpEntity = new MultipartEntity(); //文件传输
        for (File file : files) {
            ContentBody cbFile = new FileBody(file);
            mpEntity.addPart(name, cbFile); // <input type="file" name="userfile" />  对应的
        }

        postMethod.setEntity(mpEntity);
//        MultipartEntityBuilder multipartEntityBuilder = MultipartEntityBuilder.create();
//
//
//        for (File file : files) {
//            multipartEntityBuilder.addPart(name, new FileBody(file));
//        }
//
//        postMethod.setEntity(multipartEntityBuilder.build());
        HttpResponse response = httpClient.execute(postMethod); //执行POST方法
        HttpEntity resEntity = response.getEntity();
        String res =  EntityUtils.toString(resEntity);
        if (response.getStatusLine().getStatusCode() == 200) {
            return res;
        }else {
            LogUtil.e(res);
        }
        return null;

    }
}
