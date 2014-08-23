package com.taobao.tae.Mshopping.demo.login.taobao;

import java.io.Serializable;

/**
 * OAuth2.0授权成功后得到的RefreshToken
 */
public class RefreshToken implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8401737099387908054L;
	private String value;
	private Long reExpiresIn;

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Long getReExpiresIn() {
		return reExpiresIn;
	}

	public void setReExpiresIn(Long reExpiresIn) {
		this.reExpiresIn = reExpiresIn;
	}
}
