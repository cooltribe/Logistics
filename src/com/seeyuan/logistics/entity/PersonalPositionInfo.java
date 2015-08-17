package com.seeyuan.logistics.entity;

/**
 * 个人位置信息
 * 
 * @author zhazhaobao
 * 
 */
public class PersonalPositionInfo {

	/**
	 * 唯一身份识别标示
	 */
	private String userID;

	/**
	 * 经度
	 */
	private String longitude;

	/**
	 * 维度
	 */
	private String latitude;

	/**
	 * 手机号
	 */
	private String phone;

	/**
	 * 省
	 */
	private String province;
	/**
	 * 市
	 */
	private String city;
	/**
	 * 区域
	 */
	private String district;
	/**
	 * 街道
	 */
	private String street;

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String region) {
		this.district = region;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

}
