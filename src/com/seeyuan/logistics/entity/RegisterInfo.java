package com.seeyuan.logistics.entity;

import java.io.Serializable;

/**
 * 注册信息
 * 
 * @author zhazhaobao
 * 
 */
public class RegisterInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 账号
	 */
	private String userID;

	/**
	 * 用户名
	 */
	private String userName;

	/**
	 * 用户类型
	 */
	private String userType;

	/**
	 * 验证码
	 */
	private String authcode;

	/**
	 * 密码
	 */
	private String password;

	/**
	 * 确认密码
	 */
	private String secendPassword;

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public String getAuthcode() {
		return authcode;
	}

	public void setAuthcode(String authcode) {
		this.authcode = authcode;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSecendPassword() {
		return secendPassword;
	}

	public void setSecendPassword(String secendPassword) {
		this.secendPassword = secendPassword;
	}

}
