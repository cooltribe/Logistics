package com.seeyuan.logistics.entity;

import java.io.Serializable;
import java.sql.Timestamp;

public class DriverDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6762359498305297799L;

	/**
	 * 司机的惟一标识
	 */
	private String driverId;

	/**
	 * 司机姓名
	 */
	private String driverName;

	/**
	 * 司机电话号码
	 */
	private String driverTel;

	/**
	 * 司机身份证号码
	 */
	private String idNumber;

	/**
	 * 驾驶证图片
	 */
	private ImageDto driverLicense;

	/**
	 * 身份证正面图片
	 */
	private ImageDto idPictureFront;

	/**
	 * 头像
	 */
	private ImageDto face;

	/**
	 * 类型
	 */
	private String driverType;

	/**
	 * 创建人
	 */
	private String createdBy;

	/**
	 * 创建时间
	 */
	private Timestamp createdTime;

	/**
	 * 修改人
	 */
	private String lastUpdBy;

	/**
	 * 最后一次修改时间
	 */
	private Timestamp lastUpdTime;

	/**
	 * 修改次数
	 */
	private long modificationNum;

	/**
	 * 删除标识
	 */
	private String deletedFlag;

	/**
	 * 数据来源
	 */
	private String originApp;

	/**
	 * 认证未通过原因
	 */
	private String comVetoReason2;
	
	/**
	 * 道路运输从业资格证号
	 */
	private String emplLicense;
	
	/**
	 * 从业资格证图片 
	 */
	private ImageDto empLLicenseImage;


	private boolean isEditMode = false;

	private boolean isAddNewDriver = false;

	private boolean isClicked = false;

	public String getDriverId() {
		return driverId;
	}

	public void setDriverId(String driverId) {
		this.driverId = driverId;
	}

	public String getDriverName() {
		return driverName;
	}

	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}

	public String getDriverTel() {
		return driverTel;
	}

	public void setDriverTel(String driverTel) {
		this.driverTel = driverTel;
	}

	public String getIdNumber() {
		return idNumber;
	}

	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}

	public ImageDto getDriverLicense() {
		return driverLicense;
	}

	public void setDriverLicense(ImageDto driverLicense) {
		this.driverLicense = driverLicense;
	}

	public ImageDto getIdPictureFront() {
		return idPictureFront;
	}

	public void setIdPictureFront(ImageDto idPictureFront) {
		this.idPictureFront = idPictureFront;
	}

	public ImageDto getFace() {
		return face;
	}

	public void setFace(ImageDto face) {
		this.face = face;
	}

	public String getDriverType() {
		return driverType;
	}

	public void setDriverType(String driverType) {
		this.driverType = driverType;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Timestamp getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Timestamp createdTime) {
		this.createdTime = createdTime;
	}

	public String getLastUpdBy() {
		return lastUpdBy;
	}

	public void setLastUpdBy(String lastUpdBy) {
		this.lastUpdBy = lastUpdBy;
	}

	public Timestamp getLastUpdTime() {
		return lastUpdTime;
	}

	public void setLastUpdTime(Timestamp lastUpdTime) {
		this.lastUpdTime = lastUpdTime;
	}

	public long getModificationNum() {
		return modificationNum;
	}

	public void setModificationNum(long modificationNum) {
		this.modificationNum = modificationNum;
	}

	public String getDeletedFlag() {
		return deletedFlag;
	}

	public void setDeletedFlag(String deletedFlag) {
		this.deletedFlag = deletedFlag;
	}

	public String getOriginApp() {
		return originApp;
	}

	public void setOriginApp(String originApp) {
		this.originApp = originApp;
	}

	public String getComVetoReason2() {
		return comVetoReason2;
	}

	public void setComVetoReason2(String comVetoReason2) {
		this.comVetoReason2 = comVetoReason2;
	}

	public boolean isEditMode() {
		return isEditMode;
	}

	public void setEditMode(boolean isEditMode) {
		this.isEditMode = isEditMode;
	}

	public boolean isAddNewDriver() {
		return isAddNewDriver;
	}

	public void setAddNewDriver(boolean isAddNewDriver) {
		this.isAddNewDriver = isAddNewDriver;
	}

	public boolean isClicked() {
		return isClicked;
	}

	public void setClicked(boolean isClicked) {
		this.isClicked = isClicked;
	}

	public String getEmplLicense() {
		return emplLicense;
	}

	public void setEmplLicense(String emplLicense) {
		this.emplLicense = emplLicense;
	}

	public ImageDto getEmpLLicenseImage() {
		return empLLicenseImage;
	}

	public void setEmpLLicenseImage(ImageDto empLLicenseImage) {
		this.empLLicenseImage = empLLicenseImage;
	}

}
