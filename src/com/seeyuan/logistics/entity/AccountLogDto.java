package com.seeyuan.logistics.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

/**
  * 交易日志传输类
  * @类名: AccountLogDto.java
  * @描述: xxx
  * @作者: wangjinkui
  * @日期: 2014年12月9日 下午4:25:09
  * @版本:  V1.0
  * ============================================================================
  * 版权所有 2010-2014 江苏辉源供应链管理有限公司（SEARUN）,并保留所有权利。
  * ----------------------------------------------------------------------------
  * 提示：未经许可不能随意拷贝复制使用本软件，否则SEARUN将保留追究的权力。
  * ----------------------------------------------------------------------------
  * 官方网站：http://www.sy56.com
  * ============================================================================
 */
public class AccountLogDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1371777266898365444L;
	
	private String id;
	
	private Timestamp createdTime; //操作时间
	
	private String refBillNo;//相关单据号，关联：入账单、支付单、仲裁支付单、结算单
	
	private String inRefType;//转入方类型（关联数据字典表TYPE_CODE='ACC_REF_TYPE'）
	
	private String inRefId;//转入方
	
	private String outRefType;//转出方类型（关联数据字典表TYPE_CODE='ACC_REF_TYPE'）
	
	private String outRefId;//转出方
	
	private BigDecimal amount;//金额
	
	private String remark;//备注
	
	private String usernameZc;//转出方名字
	
	private String realNameZc;//转出方真实名字
	
	private String usernameZr;//转入方名字
	
	private String realNameZr;//转入方真实名字


	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getRefBillNo() {
		return refBillNo;
	}

	public void setRefBillNo(String refBillNo) {
		this.refBillNo = refBillNo;
	}

	public String getInRefType() {
		return inRefType;
	}

	public void setInRefType(String inRefType) {
		this.inRefType = inRefType;
	}

	public String getInRefId() {
		return inRefId;
	}

	public void setInRefId(String inRefId) {
		this.inRefId = inRefId;
	}

	public String getOutRefType() {
		return outRefType;
	}

	public void setOutRefType(String outRefType) {
		this.outRefType = outRefType;
	}

	public String getOutRefId() {
		return outRefId;
	}

	public void setOutRefId(String outRefId) {
		this.outRefId = outRefId;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getUsernameZc() {
		return usernameZc;
	}

	public void setUsernameZc(String usernameZc) {
		this.usernameZc = usernameZc;
	}

	public String getRealNameZc() {
		return realNameZc;
	}

	public void setRealNameZc(String realNameZc) {
		this.realNameZc = realNameZc;
	}

	public String getUsernameZr() {
		return usernameZr;
	}

	public void setUsernameZr(String usernameZr) {
		this.usernameZr = usernameZr;
	}

	public String getRealNameZr() {
		return realNameZr;
	}

	public void setRealNameZr(String realNameZr) {
		this.realNameZr = realNameZr;
	}

	public Timestamp getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Timestamp createdTime) {
		this.createdTime = createdTime;
	}
	
	
}
