package com.seeyuan.logistics.entity;

/**
 * 
 * 发布专线信息
 * 
 * @author zhazhaobao
 * 
 */
public class LineSourceInfo {

	/**
	 * 公司名称
	 */
	private String companyName;

	/**
	 * 负责人
	 */
	private String responsiblePerson;

	/**
	 * 负责人手机号码
	 */
	private String responsiblePhone;

	/**
	 * 总部地址
	 */
	private String companyPlace;

	/**
	 * 始发地
	 */
	private String targetFrom;

	/**
	 * 始发地联系人
	 */
	private String targetFromUser;

	/**
	 * 始发地联系人手机号码
	 */
	private String targetFromUserPhone;

	/**
	 * 目的地
	 */
	private String targetTo;

	/**
	 * 目的地联系人
	 */
	private String targetToUser;

	/**
	 * 目的地联系人电话
	 */
	private String targetToUserPhone;

	/**
	 * 可辐射网点
	 */
	private String targetToRange;

	/**
	 * 是否保险
	 */
	private boolean isInsurance = false;

	/**
	 * 是否税票
	 */
	private boolean isTaxReceipt = false;

	/**
	 * 是否代理
	 */
	private boolean isAgency = false;

	/**
	 * 重货价格
	 */
	private String heavyGoodsPrice;

	/**
	 * 轻货价格
	 */
	private String lightGoodsPrice;

	/**
	 * 重泡货基准价格
	 */
	private String heavyCargoPrice;

	/**
	 * 起步价
	 */
	private String startingPrice;

	/**
	 * 货运方式
	 */
	private String transportType;

	/**
	 * 主要车型
	 */
	private String carType;

	/**
	 * 车长
	 */
	private String carLength;

	/**
	 * 每天发车时间
	 */
	private String departTime;

	/**
	 * 有效时间
	 */
	private String vaildTime;

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getResponsiblePerson() {
		return responsiblePerson;
	}

	public void setResponsiblePerson(String responsiblePerson) {
		this.responsiblePerson = responsiblePerson;
	}

	public String getResponsiblePhone() {
		return responsiblePhone;
	}

	public void setResponsiblePhone(String responsiblePhone) {
		this.responsiblePhone = responsiblePhone;
	}

	public String getCompanyPlace() {
		return companyPlace;
	}

	public void setCompanyPlace(String companyPlace) {
		this.companyPlace = companyPlace;
	}

	public String getTargetFrom() {
		return targetFrom;
	}

	public void setTargetFrom(String targetFrom) {
		this.targetFrom = targetFrom;
	}

	public String getTargetFromUser() {
		return targetFromUser;
	}

	public void setTargetFromUser(String targetFromUser) {
		this.targetFromUser = targetFromUser;
	}

	public String getTargetFromUserPhone() {
		return targetFromUserPhone;
	}

	public void setTargetFromUserPhone(String targetFromUserPhone) {
		this.targetFromUserPhone = targetFromUserPhone;
	}

	public String getTargetTo() {
		return targetTo;
	}

	public void setTargetTo(String targetTo) {
		this.targetTo = targetTo;
	}

	public String getTargetToUser() {
		return targetToUser;
	}

	public void setTargetToUser(String targetToUser) {
		this.targetToUser = targetToUser;
	}

	public String getTargetToUserPhone() {
		return targetToUserPhone;
	}

	public void setTargetToUserPhone(String targetToUserPhone) {
		this.targetToUserPhone = targetToUserPhone;
	}

	public String getTargetToRange() {
		return targetToRange;
	}

	public void setTargetToRange(String targetToRange) {
		this.targetToRange = targetToRange;
	}

	public boolean isInsurance() {
		return isInsurance;
	}

	public void setInsurance(boolean isInsurance) {
		this.isInsurance = isInsurance;
	}

	public boolean isTaxReceipt() {
		return isTaxReceipt;
	}

	public void setTaxReceipt(boolean isTaxReceipt) {
		this.isTaxReceipt = isTaxReceipt;
	}

	public boolean isAgency() {
		return isAgency;
	}

	public void setAgency(boolean isAgency) {
		this.isAgency = isAgency;
	}

	public String getHeavyGoodsPrice() {
		return heavyGoodsPrice;
	}

	public void setHeavyGoodsPrice(String heavyGoodsPrice) {
		this.heavyGoodsPrice = heavyGoodsPrice;
	}

	public String getLightGoodsPrice() {
		return lightGoodsPrice;
	}

	public void setLightGoodsPrice(String lightGoodsPrice) {
		this.lightGoodsPrice = lightGoodsPrice;
	}

	public String getHeavyCargoPrice() {
		return heavyCargoPrice;
	}

	public void setHeavyCargoPrice(String heavyCargoPrice) {
		this.heavyCargoPrice = heavyCargoPrice;
	}

	public String getStartingPrice() {
		return startingPrice;
	}

	public void setStartingPrice(String startingPrice) {
		this.startingPrice = startingPrice;
	}

	public String getTransportType() {
		return transportType;
	}

	public void setTransportType(String transportType) {
		this.transportType = transportType;
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

	public String getDepartTime() {
		return departTime;
	}

	public void setDepartTime(String departTime) {
		this.departTime = departTime;
	}

	public String getVaildTime() {
		return vaildTime;
	}

	public void setVaildTime(String vaildTime) {
		this.vaildTime = vaildTime;
	}

}
