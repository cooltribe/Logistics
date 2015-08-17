package com.seeyuan.logistics.entity;

import java.io.Serializable;

public class UserInfo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private long TYPE_ID;
	private int LEVEL_ID;// 会员级别
	private String uuId;// 身份标示符
	private long memberType;// 用户类型
	private String USER_NAME;// 会员用户名
	private String EMAIL;// 邮件地址
	private String IS_EMAIL_PROVEN;// 邮件是否验证 Y 已验证 N 未验证
	private String MOBILE;// 手机
	private String PASSWORD;// 密码
	private String REGISTER_TIME;// 注册时间
	private String NICKNAME;// 昵称
	private String SEX;// 性别 0女 1男
	private String BIRTHDAY;// 出生日期
	private String PROVINCE;// 省
	private String CITY;// 市
	private String REGION;// 区
	private String ADDRESS;// 详细地址
	private String ZIP;// 邮编
	private String TEL;// 固定电话
	private String QQ;// QQ
	private String WECHAT;// 微信号
	private String INFO_COMPL;// 个人资料完善程度
	private String REFEREE_NAME;// 推荐人
	private String REFEREE_MOBILE;// 推荐人手机号
	private String FACE;// 头像URL
	private String REGISTER_IP;// 注册IP
	private String LAST_SEND_EMAIL;// 最后一次发送激活邮件时间
	private String LAST_LAT;// 最近位置_纬度
	private String LAST_LNG;// 最近位置_经度
	private String LAST_LOCATION;// 最后位置_详情
	private String LOGIN_COUNT;// 当月登录次数
	private String LASTLOGIN_TIME;// 最后登录时间
	private String LOGIN_IP;// 最近登录IP
	private String PIN_CODE;// 识别号
	private String STATUS;// 账号状态:关联数据字典，TYPE_CODE='MEMBER_STATUS'
	private String REMARK;// 备注
	private String CREATETIME;// 创建时间
	private String isLogin;// 是否登录 Y/N
	private String FACE_LOCATION_URL;//头像本地URL

	public long getTYPE_ID() {
		return TYPE_ID;
	}

	public void setTYPE_ID(long tYPE_ID) {
		TYPE_ID = tYPE_ID;
	}

	public int getLEVEL_ID() {
		return LEVEL_ID;
	}

	public void setLEVEL_ID(int lEVEL_ID) {
		LEVEL_ID = lEVEL_ID;
	}

	public String getUuId() {
		return uuId;
	}

	public void setUuId(String uuId) {
		this.uuId = uuId;
	}

	public String getUSER_NAME() {
		return USER_NAME;
	}

	public void setUSER_NAME(String uSER_NAME) {
		USER_NAME = uSER_NAME;
	}

	public String getEMAIL() {
		return EMAIL;
	}

	public void setEMAIL(String eMAIL) {
		EMAIL = eMAIL;
	}

	public String getIS_EMAIL_PROVEN() {
		return IS_EMAIL_PROVEN;
	}

	public void setIS_EMAIL_PROVEN(String iS_EMAIL_PROVEN) {
		IS_EMAIL_PROVEN = iS_EMAIL_PROVEN;
	}

	public String getMOBILE() {
		return MOBILE;
	}

	public void setMOBILE(String mOBILE) {
		MOBILE = mOBILE;
	}

	public String getPASSWORD() {
		return PASSWORD;
	}

	public void setPASSWORD(String pASSWORD) {
		PASSWORD = pASSWORD;
	}

	public String getREGISTER_TIME() {
		return REGISTER_TIME;
	}

	public void setREGISTER_TIME(String rEGISTER_TIME) {
		REGISTER_TIME = rEGISTER_TIME;
	}

	public String getNICKNAME() {
		return NICKNAME;
	}

	public void setNICKNAME(String nICKNAME) {
		NICKNAME = nICKNAME;
	}

	public String getSEX() {
		return SEX;
	}

	public void setSEX(String sEX) {
		SEX = sEX;
	}

	public String getBIRTHDAY() {
		return BIRTHDAY;
	}

	public void setBIRTHDAY(String bIRTHDAY) {
		BIRTHDAY = bIRTHDAY;
	}

	public String getPROVINCE() {
		return PROVINCE;
	}

	public void setPROVINCE(String pROVINCE) {
		PROVINCE = pROVINCE;
	}

	public String getCITY() {
		return CITY;
	}

	public void setCITY(String cITY) {
		CITY = cITY;
	}

	public String getREGION() {
		return REGION;
	}

	public void setREGION(String rEGION) {
		REGION = rEGION;
	}

	public String getADDRESS() {
		return ADDRESS;
	}

	public void setADDRESS(String aDDRESS) {
		ADDRESS = aDDRESS;
	}

	public String getZIP() {
		return ZIP;
	}

	public void setZIP(String zIP) {
		ZIP = zIP;
	}

	public String getTEL() {
		return TEL;
	}

	public void setTEL(String tEL) {
		TEL = tEL;
	}

	public String getQQ() {
		return QQ;
	}

	public void setQQ(String qQ) {
		QQ = qQ;
	}

	public String getWECHAT() {
		return WECHAT;
	}

	public void setWECHAT(String wECHAT) {
		WECHAT = wECHAT;
	}

	public String getINFO_COMPL() {
		return INFO_COMPL;
	}

	public void setINFO_COMPL(String iNFO_COMPL) {
		INFO_COMPL = iNFO_COMPL;
	}

	public String getREFEREE_NAME() {
		return REFEREE_NAME;
	}

	public void setREFEREE_NAME(String rEFEREE_NAME) {
		REFEREE_NAME = rEFEREE_NAME;
	}

	public String getREFEREE_MOBILE() {
		return REFEREE_MOBILE;
	}

	public void setREFEREE_MOBILE(String rEFEREE_MOBILE) {
		REFEREE_MOBILE = rEFEREE_MOBILE;
	}

	public String getFACE() {
		return FACE;
	}

	public void setFACE(String fACE) {
		FACE = fACE;
	}

	public String getREGISTER_IP() {
		return REGISTER_IP;
	}

	public void setREGISTER_IP(String rEGISTER_IP) {
		REGISTER_IP = rEGISTER_IP;
	}

	public String getLAST_SEND_EMAIL() {
		return LAST_SEND_EMAIL;
	}

	public void setLAST_SEND_EMAIL(String lAST_SEND_EMAIL) {
		LAST_SEND_EMAIL = lAST_SEND_EMAIL;
	}

	public String getLAST_LAT() {
		return LAST_LAT;
	}

	public void setLAST_LAT(String lAST_LAT) {
		LAST_LAT = lAST_LAT;
	}

	public String getLAST_LNG() {
		return LAST_LNG;
	}

	public void setLAST_LNG(String lAST_LNG) {
		LAST_LNG = lAST_LNG;
	}

	public String getLOGIN_COUNT() {
		return LOGIN_COUNT;
	}

	public void setLOGIN_COUNT(String lOGIN_COUNT) {
		LOGIN_COUNT = lOGIN_COUNT;
	}

	public String getLASTLOGIN_TIME() {
		return LASTLOGIN_TIME;
	}

	public void setLASTLOGIN_TIME(String lASTLOGIN_TIME) {
		LASTLOGIN_TIME = lASTLOGIN_TIME;
	}

	public String getLOGIN_IP() {
		return LOGIN_IP;
	}

	public void setLOGIN_IP(String lOGIN_IP) {
		LOGIN_IP = lOGIN_IP;
	}

	public String getPIN_CODE() {
		return PIN_CODE;
	}

	public void setPIN_CODE(String pIN_CODE) {
		PIN_CODE = pIN_CODE;
	}

	public String getSTATUS() {
		return STATUS;
	}

	public void setSTATUS(String sTATUS) {
		STATUS = sTATUS;
	}

	public String getREMARK() {
		return REMARK;
	}

	public void setREMARK(String rEMARK) {
		REMARK = rEMARK;
	}

	public String getCREATETIME() {
		return CREATETIME;
	}

	public void setCREATETIME(String cREATETIME) {
		CREATETIME = cREATETIME;
	}

	public String getIsLogin() {
		return isLogin;
	}

	public void setIsLogin(String isLogin) {
		this.isLogin = isLogin;
	}

	public long getMemberType() {
		return memberType;
	}

	public void setMemberType(long memberType) {
		this.memberType = memberType;
	}

	public String getLAST_LOCATION() {
		return LAST_LOCATION;
	}

	public void setLAST_LOCATION(String lAST_LOCATION) {
		LAST_LOCATION = lAST_LOCATION;
	}

	public String getFACE_LOCATION_URL() {
		return FACE_LOCATION_URL;
	}

	public void setFACE_LOCATION_URL(String fACE_LOCATION_URL) {
		FACE_LOCATION_URL = fACE_LOCATION_URL;
	}

}
