package com.taobao.tae.Mshopping.demo.api;

import com.squareup.okhttp.OkHttpClient;
import com.taobao.tae.Mshopping.demo.api.internal.AlipushErrorHandler;
import com.taobao.tae.Mshopping.demo.api.internal.AlipushRequestInterceptor;
import com.taobao.tae.Mshopping.demo.model.BaseApiResult;
import com.taobao.tae.Mshopping.demo.model.TaobaoItemBasicInfo;
import com.taobao.tae.Mshopping.demo.util.JsonConverter;
import retrofit.ErrorHandler;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.client.Client;
import retrofit.client.OkClient;
import retrofit.http.Part;
import retrofit.http.Path;
import retrofit.http.Query;
import retrofit.mime.MultipartTypedOutput;
import retrofit.mime.TypedFile;
import retrofit.mime.TypedOutput;

import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * mulou.zzy
 * taedemo
 */
public class ApiFacade {

    private Client apiClient;
    private RequestInterceptor alipushInterceptor = new AlipushRequestInterceptor();

    private ErrorHandler alipushErrorHandler = new AlipushErrorHandler();

    private static ApiFacade instance;

    private RestAdapter adapter;

    public static ApiFacade getInstance() {
        if (instance == null) {
            synchronized (ApiFacade.class) {
                if (instance == null) {
                    instance = new ApiFacade();
                    instance.init();
                }
            }
        }
        return instance;
    }

    private void init() {
        adapter = getRestAdapter();
        apis = adapter.create(Apis.class);
    }


    private Apis apis;

    private RestAdapter getRestAdapter() {
        OkHttpClient okHttpClient = new OkHttpClient();
        RestAdapter builder = new RestAdapter.Builder()

                .setClient(new OkClient(okHttpClient))
                .setEndpoint("http://10.68.102.241:8080")
                .setRequestInterceptor(alipushInterceptor)
                .setErrorHandler(alipushErrorHandler)
                .setConverter(new JsonConverter())
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build();
        return builder;
    }


    public BaseApiResult<Map<String, String>> uploadImgs(List<File> files) {

        MultipartTypedOutput multipartTypedOutput = new MultipartTypedOutput();
        for (int i = 0; i < files.size(); i++) {
            File file = files.get(i);
            String fileName = file.getName();
            int index = fileName.lastIndexOf(".");
            String mime = null;
            if (index == -1) {
                mime = "image/jpeg";
            } else {
                mime = "image/" + fileName.substring(index + 1, fileName.length());
            }
            TypedFile typedFile = new TypedFile(mime, files.get(i));
            multipartTypedOutput.addPart("files", typedFile);
        }




       return this.apis.upload(multipartTypedOutput);
    }




    public List<TaobaoItemBasicInfo> getItemList( Integer catId,  Integer page,  String securityKey) {
        return this.apis.getItemList(catId, page, securityKey);
    }
}
