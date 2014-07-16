package com.taobao.tae.Mshopping.demo.login.auth;

/**
 * 授权业务错误对象
 */
public class AuthError {
	private String error;
	private String error_description;
	public String getError() {
		return error;
	}
	public void setError(String error) {
		this.error = error;
	}
	public String getErrorDescription() {
		return error_description;
	}
	public void setErrorDescription(String error_description) {
		this.error_description = error_description;
	}
}
