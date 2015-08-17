package com.seeyuan.logistics.entity;

import java.io.Serializable;
import java.math.BigDecimal;

public class CompanyAuthDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5644010834879891387L;

	/**
	 * ID
	 */
	private String companyAuthId;
	
	/**
	 * 公司名称
	 */
	private String comName;
	
	/**
	 * 公司地址
	 */
	private String comAddress;
	
	/**
	 * 公司详细地址
	 */
	private String comAddrDetail;   
	
	/**
	 * 注册资金
	 */
	private BigDecimal regisCapital;       
	
	/**
	 * 经营范围
	 */
	private String busiScope;         
	
	/**
	 * 公司简介
	 */
	private String comDescribe;   
	
	/**
	 * 注册人姓名	
	 */
	private String legalPersom; 
	
	/**
	 * 注册人的身份证
	 */
	private String legalIdNum;
	
	/**
	 * 注册人的手机号
	 */
	private String legalMobile; 
	
	/**
	 * 固定电话
	 */
	private String phoneNum;     
	
	/**
	 * 开户银行
	 */
	private String bank;    
	
	/**
	 * 开户行所在省
	 */
	private String bankProvince; 
	
	/**
	 * 开户行所在市
	 */
	private String bankCity;  
	
	/**
	 * 开户行账号
	 */
	private String bankAccount;
	
	/**
	 * 公司商标图片URL
	 */
	private ImageDto companyLogo;       
	
	/**
	 * 营业执照注册号
	 */
	private String busiLicenseNum;   
	
	/**
	 * 营业执照图片URL
	 */
	private String busiLicense;
	
	/**
	 * 税务登记证号
	 */
	private String taxRegistNum;  
	
	/**
	 * 税务登记证号图片
	 */
	private String taxRegist;
	
	/**
	 * 组织机构代码证号
	 */
	private String orgCodeNum;
	
	/**
	 * 组织机构代码证号图片
	 */
	private String orgCode;
	
	/**
	 * 道路许可证号
	 */
	private String roadTransNum;
	
	/**
	 * 道路许可证图片存放
	 */
	private ImageDto roadTransPermit;
	
	/**
	 * 公司认证是否认证
	 */
	private String isComChecked;
	
	/**
	 * 公司认证失败原因
	 */
	private String comVetoReason;

	

	public String getCompanyAuthId() {
		return companyAuthId;
	}

	public void setCompanyAuthId(String companyAuthId) {
		this.companyAuthId = companyAuthId;
	}

	public String getComName() {
		return comName;
	}

	public void setComName(String comName) {
		this.comName = comName;
	}

	public String getComAddress() {
		return comAddress;
	}

	public void setComAddress(String comAddress) {
		this.comAddress = comAddress;
	}

	public String getComAddrDetail() {
		return comAddrDetail;
	}

	public void setComAddrDetail(String comAddrDetail) {
		this.comAddrDetail = comAddrDetail;
	}

	public BigDecimal getRegisCapital() {
		return regisCapital;
	}

	public void setRegisCapital(BigDecimal regisCapital) {
		this.regisCapital = regisCapital;
	}

	public String getBusiScope() {
		return busiScope;
	}

	public void setBusiScope(String busiScope) {
		this.busiScope = busiScope;
	}

	public String getComDescribe() {
		return comDescribe;
	}

	public void setComDescribe(String comDescribe) {
		this.comDescribe = comDescribe;
	}

	public String getLegalPersom() {
		return legalPersom;
	}

	public void setLegalPersom(String legalPersom) {
		this.legalPersom = legalPersom;
	}

	public String getLegalIdNum() {
		return legalIdNum;
	}

	public void setLegalIdNum(String legalIdNum) {
		this.legalIdNum = legalIdNum;
	}

	public String getLegalMobile() {
		return legalMobile;
	}

	public void setLegalMobile(String legalMobile) {
		this.legalMobile = legalMobile;
	}

	public String getPhoneNum() {
		return phoneNum;
	}

	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}

	public String getBank() {
		return bank;
	}

	public void setBank(String bank) {
		this.bank = bank;
	}

	public String getBankProvince() {
		return bankProvince;
	}

	public void setBankProvince(String bankProvince) {
		this.bankProvince = bankProvince;
	}

	public String getBankCity() {
		return bankCity;
	}

	public void setBankCity(String bankCity) {
		this.bankCity = bankCity;
	}

	public String getBankAccount() {
		return bankAccount;
	}

	public void setBankAccount(String bankAccount) {
		this.bankAccount = bankAccount;
	}

	public ImageDto getCompanyLogo() {
		return companyLogo;
	}

	public void setCompanyLogo(ImageDto companyLogo) {
		this.companyLogo = companyLogo;
	}

	public String getBusiLicenseNum() {
		return busiLicenseNum;
	}

	public void setBusiLicenseNum(String busiLicenseNum) {
		this.busiLicenseNum = busiLicenseNum;
	}

	public String getBusiLicense() {
		return busiLicense;
	}

	public void setBusiLicense(String busiLicense) {
		this.busiLicense = busiLicense;
	}

	public String getTaxRegistNum() {
		return taxRegistNum;
	}

	public void setTaxRegistNum(String taxRegistNum) {
		this.taxRegistNum = taxRegistNum;
	}

	public String getTaxRegist() {
		return taxRegist;
	}

	public void setTaxRegist(String taxRegist) {
		this.taxRegist = taxRegist;
	}

	public String getOrgCodeNum() {
		return orgCodeNum;
	}

	public void setOrgCodeNum(String orgCodeNum) {
		this.orgCodeNum = orgCodeNum;
	}

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public String getRoadTransNum() {
		return roadTransNum;
	}

	public void setRoadTransNum(String roadTransNum) {
		this.roadTransNum = roadTransNum;
	}

	public ImageDto getRoadTransPermit() {
		return roadTransPermit;
	}

	public void setRoadTransPermit(ImageDto roadTransPermit) {
		this.roadTransPermit = roadTransPermit;
	}

	public String getIsComChecked() {
		return isComChecked;
	}

	public void setIsComChecked(String isComChecked) {
		this.isComChecked = isComChecked;
	}

	public String getComVetoReason() {
		return comVetoReason;
	}

	public void setComVetoReason(String comVetoReason) {
		this.comVetoReason = comVetoReason;
	} 
	
	
}
