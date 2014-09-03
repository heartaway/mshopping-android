package com.taobao.tae.Mshopping.demo.util;

import com.taobao.tae.Mshopping.demo.api.ApiFacade;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * mulou.zzy
 * taedemo
 */
public class HttpClient {


//    @Test

    public static void main(String[] s) {


        File file = new File("/Users/huamulou/Pictures/6608697102119889260.jpg");

        List<File> fileList = new ArrayList<File>();
        ApiFacade apiFacade = ApiFacade.getInstance();
        fileList.add(file);
        apiFacade.uploadImgs(fileList);
    }
}
