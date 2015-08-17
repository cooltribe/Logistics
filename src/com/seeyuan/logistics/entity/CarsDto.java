package com.seeyuan.logistics.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class CarsDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */

	/**
	 * 汽车ID
	 */
	private String carsId;

	/**
	 * 发布人
	 */
	// private String createdBy;

	/**
	 * 最新修改人
	 */
	// private String lastUpdBy;

	/**
	 * 车牌号
	 */
	private String vehicleNum;

	/**
	 * 车型
	 */
	private String type;

	/**
	 * 车长
	 */
	private String length;

	/**
	 * 载重
	 */
	private BigDecimal capacity;

	/**
	 * 发动机型号
	 */
	private String engineNo;

	/**
	 * 车辆识别号
	 */
	private String vin;

	/**
	 * 备注
	 */
	private String remark;

	/**
	 * 行驶证照片
	 */
	private ImageDto vehicleLicensePhoto;

	/**
	 * 车辆照片
	 */
	private ImageDto vehiclePhoto;

	/**
	 * 来源（WEB端值为WEB,Android端为ANDROID,IOS端为IOS）
	 */
	private String originApp;

	private List<RouteDto> routes = new ArrayList<RouteDto>();

	/**
	 * 是否认证
	 */
	private String isChecked;

	private boolean isEditMode = false;
	
	private String creatingMemberId;//创建人
	
	/**
	 * 附属设备
	 * 雨布	雨布
	 * 防坠落装置	防坠落装置
	 * 网兜	网兜
	 * 绳索	绳索
	 *  夹板	夹板
	 */
	private String attaDevice;
	
	/**
	 * 车辆保险单号
	 */
	private String insurance;
	
	/**
	 * 车辆保险图片
	 */
	private ImageDto insuranceImage;
	
	/**
	 * 容积
	 */
	private BigDecimal volume;
	
	/**
	 * 道路运输证号
	 */
	private String transLicense;
	
	/**
	 * 道路运输证图片
	 */
	private ImageDto  transLicenseImage;


	public String getCarsId() {
		return carsId;
	}

	public void setCarsId(String carsId) {
		this.carsId = carsId;
	}

	/*
	 * public String getCreatedBy() { return createdBy; }
	 * 
	 * public void setCreatedBy(String createdBy) { this.createdBy = createdBy;
	 * }
	 * 
	 * public String getLastUpdBy() { return lastUpdBy; }
	 * 
	 * public void setLastUpdBy(String lastUpdBy) { this.lastUpdBy = lastUpdBy;
	 * }
	 */

	public String getVehicleNum() {
		return vehicleNum;
	}

	public void setVehicleNum(String vehicleNum) {
		this.vehicleNum = vehicleNum;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getLength() {
		return length;
	}

	public void setLength(String length) {
		this.length = length;
	}

	public BigDecimal getCapacity() {
		return capacity;
	}

	public void setCapacity(BigDecimal capacity) {
		this.capacity = capacity;
	}

	public String getEngineNo() {
		return engineNo;
	}

	public void setEngineNo(String engineNo) {
		this.engineNo = engineNo;
	}

	public String getVin() {
		return vin;
	}

	public void setVin(String vin) {
		this.vin = vin;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public ImageDto getVehicleLicensePhoto() {
		return vehicleLicensePhoto;
	}

	public void setVehicleLicensePhoto(ImageDto vehicleLicensePhoto) {
		this.vehicleLicensePhoto = vehicleLicensePhoto;
	}

	public ImageDto getVehiclePhoto() {
		return vehiclePhoto;
	}

	public void setVehiclePhoto(ImageDto vehiclePhoto) {
		this.vehiclePhoto = vehiclePhoto;
	}

	public String getOriginApp() {
		return originApp;
	}

	public void setOriginApp(String originApp) {
		this.originApp = originApp;
	}

	public List<RouteDto> getRoutes() {
		return routes;
	}

	public void setRoutes(List<RouteDto> routes) {
		this.routes = routes;
	}

	public String getIsChecked() {
		return isChecked;
	}

	public void setIsChecked(String isChecked) {
		this.isChecked = isChecked;
	}

	public boolean isEditMode() {
		return isEditMode;
	}

	public void setEditMode(boolean isEditMode) {
		this.isEditMode = isEditMode;
	}

	public String getCreatingMemberId() {
		return creatingMemberId;
	}

	public void setCreatingMemberId(String creatingMemberId) {
		this.creatingMemberId = creatingMemberId;
	}

	public String getAttaDevice() {
		return attaDevice;
	}

	public void setAttaDevice(String attaDevice) {
		this.attaDevice = attaDevice;
	}

	public String getInsurance() {
		return insurance;
	}

	public void setInsurance(String insurance) {
		this.insurance = insurance;
	}

	public ImageDto getInsuranceImage() {
		return insuranceImage;
	}

	public void setInsuranceImage(ImageDto insuranceImage) {
		this.insuranceImage = insuranceImage;
	}

	public BigDecimal getVolume() {
		return volume;
	}

	public void setVolume(BigDecimal volume) {
		this.volume = volume;
	}

	public String getTransLicense() {
		return transLicense;
	}

	public void setTransLicense(String transLicense) {
		this.transLicense = transLicense;
	}

	public ImageDto getTransLicenseImage() {
		return transLicenseImage;
	}

	public void setTransLicenseImage(ImageDto transLicenseImage) {
		this.transLicenseImage = transLicenseImage;
	}

}
