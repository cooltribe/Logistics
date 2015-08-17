package com.seeyuan.logistics.entity;

/**
 * 登录信息
 * @author zhazhaobao
 *
 */
public class LoginInfo {

	/**
	 * 账号
	 */
	private String userID;

	/**
	 * 密码
	 */
	private String password;

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
