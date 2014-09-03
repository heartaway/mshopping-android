package com.taobao.tae.Mshopping.demo.Exception;

/**
 * mulou.zzy
 * taedemo
 */
public class ApiException extends Exception {
    public ApiException() {
        super();
    }

    public ApiException(String message) {
        super(message);
    }

    public ApiException(String message, Throwable cause) {
        super(message, cause);
    }

    public ApiException(Throwable cause) {
        super(cause);
    }
}
