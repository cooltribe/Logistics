package com.seeyuan.logistics.entity;

import java.io.Serializable;
import java.util.Date;

public class MemberDto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -553825592621796153L;
	
	/**
	 * 用户ID
	 */
	private String uuId;
	
	/**
	 * 用户名
	 */
	private String userName;
	
	/**
	 * 级别类型
	 */
	private Long levelId;
	
	/**
	 * EMAIL号
	 */
	private String email;
	
	/**
	 * 邮件是否被认证
	 */
	private String isEmailProven;
	
	/**
	 * 昵称
	 */
	private String nickName;
	
	/**
	 * 性别
	 */
	private String sex;
	
	/**
	 * 生日
	 */
	private  Date birthday;
	
	/**
	 * 邮编
	 */
	private String zip;
	
	/**
	 * 固定电话号码
	 */
	private String tel;
	
	/**
	 * qq
	 */
	private String qq;
	
	/**
	 * 微信号
	 */
	private String wechat;
	
	/**
	 * 资料完成度
	 */
	private String infoCompl;
	
	/**
	 * 头像
	 */
	private ImageDto face;
	
	/**
	 * 备注
	 */
	private String remark;
	
	/**
	 * 密码
	 */
	private String password;
	
	/**
	 * 手机号码
	 */
	private String mobile;
	
	/**
	 * 省份
	 */
	private String province;
	
	/**
	 * 城市
	 */
	private String city;
	
	/**
	 * 区域
	 */
	private String region;
	
	/**
	 * 会员类型
	 */
	private Long memberType;
	
	/**
	 * 验证码
	 */
	private String verifyCode;
	
	/**
	 * 最后上传纬度
	 */
	private double last_lat;
	
	/**
	 * 最后上传经度
	 */
	private double last_lng;
	
	/**
	 * 位置信息描述
	 */
	private String last_location;
	
	/**
	 * 详细地址
	 */
	private String address;
	
	/**
	 * 推荐人手机号
	 */
	private String  referee_mobile;
	
	

	public String getReferee_mobile() {
		return referee_mobile;
	}

	public void setReferee_mobile(String referee_mobile) {
		this.referee_mobile = referee_mobile;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUuId() {
		return uuId;
	}

	public void setUuId(String uuId) {
		this.uuId = uuId;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
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

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getVerifyCode() {
		return verifyCode;
	}

	public void setVerifyCode(String verifyCode) {
		this.verifyCode = verifyCode;
	}

	public double getLast_lat() {
		return last_lat;
	}

	public void setLast_lat(double last_lat) {
		this.last_lat = last_lat;
	}

	public double getLast_lng() {
		return last_lng;
	}

	public void setLast_lng(double last_lng) {
		this.last_lng = last_lng;
	}

	public String getLast_location() {
		return last_location;
	}

	public void setLast_location(String last_location) {
		this.last_location = last_location;
	}

	public Long getMemberType() {
		return memberType;
	}

	public void setMemberType(Long memberType) {
		this.memberType = memberType;
	}

	public Long getLevelId() {
		return levelId;
	}

	public void setLevelId(Long levelId) {
		this.levelId = levelId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getIsEmailProven() {
		return isEmailProven;
	}

	public void setIsEmailProven(String isEmailProven) {
		this.isEmailProven = isEmailProven;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getQq() {
		return qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}

	public String getWechat() {
		return wechat;
	}

	public void setWechat(String wechat) {
		this.wechat = wechat;
	}

	public String getInfoCompl() {
		return infoCompl;
	}

	public void setInfoCompl(String infoCompl) {
		this.infoCompl = infoCompl;
	}

	public ImageDto getFace() {
		return face;
	}

	public void setFace(ImageDto face) {
		this.face = face;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}



}
