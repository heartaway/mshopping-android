package com.taobao.tae.Mshopping.demo.api;

import com.taobao.tae.Mshopping.demo.model.BaseApiResult;
import com.taobao.tae.Mshopping.demo.model.TaobaoItemBasicInfo;

import retrofit.http.GET;
import retrofit.http.Multipart;
import retrofit.http.POST;
import retrofit.http.Part;
import retrofit.http.Path;
import retrofit.http.Query;
import retrofit.mime.MultipartTypedOutput;
import retrofit.mime.TypedFile;
import retrofit.mime.TypedOutput;

import java.util.List;
import java.util.Map;

/**
 * mulou.zzy
 * taedemo
 */
public interface Apis {


    @Multipart
//    @Headers("content-type:multipart/form-data")
    @POST("/image/upload")
    BaseApiResult<Map<String, String>> upload(@Part("files") TypedOutput files);



    @GET("/api/itemlist/new/{catId}/{page}")
    List<TaobaoItemBasicInfo> getItemList(@Path("catId") Integer catId, @Path("page") Integer page,@Query("securityKey") String securityKey);


}
