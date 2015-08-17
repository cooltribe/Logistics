package com.seeyuan.logistics.entity;

import java.io.Serializable;

/**
 * 货物信息
 * 
 * @author zhazhaobao
 * 
 */
public class GoodsSourceInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 流向,从哪里
	 */
	private String targetFrom;

	/**
	 * 流向。去哪里
	 */
	private String targetTo;

	/**
	 * 用户名
	 */
	private String userName;

	/**
	 * 货物名称
	 */
	private String goodsName;

	/**
	 * 星级
	 */
	private int star;

	/**
	 * 分类
	 */
	private String goodsType;

	/**
	 * 重量
	 */
	private String goodsWeight;

	/**
	 * 体积
	 */
	private String goodsBulk;

	/**
	 * 车型
	 */
	private String carType;

	/**
	 * 车长
	 */
	private String carLength;

	/**
	 * 经度
	 */
	private String latitude;

	/**
	 * 维度
	 */
	private String longitude;

	/**
	 * 手机号码
	 */
	private String phone;

	/**
	 * 距离
	 */
	private String distance;

	/**
	 * 时间
	 */
	private String time;

	/**
	 * 认证
	 */
	private boolean isAuthentication;

	/**
	 * 担保
	 */
	private boolean isAssurance;

	/**
	 * 运输方式
	 */
	private String transpotType;

	/**
	 * 货物规格
	 */
	private String goodsStandard;

	/**
	 * 期望运费
	 */
	private String expectFreight;

	/**
	 * 货物编号
	 */
	private String goodsNumber;

	/**
	 * 公司名称
	 */
	private String companyName;

	/**
	 * 备注
	 */
	private String remark;

	/**
	 * 是否收藏
	 */
	private boolean isCollected = false;

	/**
	 * 有效时间
	 */
	private String validTime;

	/**
	 * 期望费用
	 */
	private String expectCost;

	public String getTargetFrom() {
		return targetFrom;
	}

	public void setTargetFrom(String targetFrom) {
		this.targetFrom = targetFrom;
	}

	public String getTargetTo() {
		return targetTo;
	}

	public void setTargetTo(String targetTo) {
		this.targetTo = targetTo;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public int getStar() {
		return star;
	}

	public void setStar(int star) {
		this.star = star;
	}

	public String getGoodsType() {
		return goodsType;
	}

	public void setGoodsType(String goodsType) {
		this.goodsType = goodsType;
	}

	public String getGoodsWeight() {
		return goodsWeight;
	}

	public void setGoodsWeight(String goodsWeight) {
		this.goodsWeight = goodsWeight;
	}

	public String getCarType() {
		return carType;
	}

	public void setCarType(String carType) {
		this.carType = carType;
	}

	public String getCarLength() {
		return carLength;
	}

	public void setCarLength(String carLength) {
		this.carLength = carLength;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getDistance() {
		return distance;
	}

	public void setDistance(String distance) {
		this.distance = distance;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public boolean isAuthentication() {
		return isAuthentication;
	}

	public void setAuthentication(boolean isAuthentication) {
		this.isAuthentication = isAuthentication;
	}

	public boolean isAssurance() {
		return isAssurance;
	}

	public void setAssurance(boolean isAssurance) {
		this.isAssurance = isAssurance;
	}

	public String getTranspotType() {
		return transpotType;
	}

	public void setTranspotType(String transpotType) {
		this.transpotType = transpotType;
	}

	public String getGoodsStandard() {
		return goodsStandard;
	}

	public void setGoodsStandard(String goodsStandard) {
		this.goodsStandard = goodsStandard;
	}

	public String getExpectFreight() {
		return expectFreight;
	}

	public void setExpectFreight(String expectFreight) {
		this.expectFreight = expectFreight;
	}

	public String getGoodsNumber() {
		return goodsNumber;
	}

	public void setGoodsNumber(String goodsNumber) {
		this.goodsNumber = goodsNumber;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public boolean isCollected() {
		return isCollected;
	}

	public void setCollected(boolean isCollected) {
		this.isCollected = isCollected;
	}

	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	public String getGoodsBulk() {
		return goodsBulk;
	}

	public void setGoodsBulk(String goodsBulk) {
		this.goodsBulk = goodsBulk;
	}

	public String getValidTime() {
		return validTime;
	}

	public void setValidTime(String validTime) {
		this.validTime = validTime;
	}

	public String getExpectCost() {
		return expectCost;
	}

	public void setExpectCost(String expectCost) {
		this.expectCost = expectCost;
	}

}
