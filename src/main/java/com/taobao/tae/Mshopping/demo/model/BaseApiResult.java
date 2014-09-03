package com.taobao.tae.Mshopping.demo.model;

import java.io.Serializable;

/**
 * mulou.zzy
 * taedemo
 */
public class BaseApiResult<T> implements Serializable {


    private Integer code;
    private T data;

    private String msg;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public void setData(T data) {
        this.data = data;
    }


    public boolean isSuccess() {
        return this.code != null && this.code > 0;
    }

    public Integer getCode() {
        return code;
    }

    public T getData() {
        return data;
    }

    @Override
    public String toString() {
        return "BaseApiResult{" +
                "code=" + code +
                ", data=" + data +
                ", msg='" + msg + '\'' +
                '}';
    }

    public BaseApiResult() {
    }

    public BaseApiResult(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public BaseApiResult(Integer code, T data, String msg) {
        this.code = code;
        this.data = data;
        this.msg = msg;
    }
}
