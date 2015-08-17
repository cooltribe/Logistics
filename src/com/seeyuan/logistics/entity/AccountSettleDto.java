package com.seeyuan.logistics.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * 结算单业务传输类
 * 
 * @类名: AccountSettleDto.java
 * @描述: xxx
 * @作者: wangjinkui
 * @日期: 2014年12月10日 上午10:12:55
 * @版本: V1.0
 *      ====================================================================
 *      ======== 版权所有 2010-2014 江苏辉源供应链管理有限公司（SEARUN）,并保留所有权利。
 *      ------------------
 *      ----------------------------------------------------------
 *      提示：未经许可不能随意拷贝复制使用本软件，否则SEARUN将保留追究的权力。
 *      ----------------------------------
 *      ------------------------------------------ 官方网站：http://www.sy56.com
 *      ======
 *      ======================================================================
 */
public class AccountSettleDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3790021615533284309L;

	private String id;

	private String org_flag; // 组织标识

	/**
	 * 单据号
	 */
	private String billNo;

	/**
	 * 创建时间，生成时间
	 */
	private Timestamp creatingTime;

	/**
	 * 修改时间,实际结算时间
	 */
	private Timestamp lastModifyTime;

	/**
	 * 账户名
	 */
	private String name;

	/**
	 * 账户类型
	 */
	private String accType;

	/**
	 * 账户
	 */
	private String accountNum;

	/**
	 * 处理时效，预计结算时间
	 */
	private Timestamp procDeadline;

	/**
	 * 折算前结算金额
	 */
	private BigDecimal preSettAmount;

	/**
	 * 折算后结算金额
	 */
	private BigDecimal aftSettAmount;

	/**
	 * 费用扣除金额
	 */
	private BigDecimal takeoffAmount;

	/**
	 * 结算金额
	 */
	private BigDecimal settAmount;

	/**
	 * 结算凭证
	 */
	private String voucher;

	/**
	 * 备注
	 */
	private String remark;

	/**
	 * 会员用户名
	 */
	private String userName;

	/**
	 * 状态
	 */
	private String status;

	public String getOrg_flag() {
		return org_flag;
	}

	public void setOrg_flag(String org_flag) {
		this.org_flag = org_flag;
	}

	public String getBillNo() {
		return billNo;
	}

	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAccType() {
		return accType;
	}

	public void setAccType(String accType) {
		this.accType = accType;
	}

	public String getAccountNum() {
		return accountNum;
	}

	public void setAccountNum(String accountNum) {
		this.accountNum = accountNum;
	}

	public Timestamp getProcDeadline() {
		return procDeadline;
	}

	public void setProcDeadline(Timestamp procDeadline) {
		this.procDeadline = procDeadline;
	}

	public BigDecimal getPreSettAmount() {
		return preSettAmount;
	}

	public void setPreSettAmount(BigDecimal preSettAmount) {
		this.preSettAmount = preSettAmount;
	}

	public BigDecimal getAftSettAmount() {
		return aftSettAmount;
	}

	public void setAftSettAmount(BigDecimal aftSettAmount) {
		this.aftSettAmount = aftSettAmount;
	}

	public BigDecimal getTakeoffAmount() {
		return takeoffAmount;
	}

	public void setTakeoffAmount(BigDecimal takeoffAmount) {
		this.takeoffAmount = takeoffAmount;
	}

	public BigDecimal getSettAmount() {
		return settAmount;
	}

	public void setSettAmount(BigDecimal settAmount) {
		this.settAmount = settAmount;
	}

	public String getVoucher() {
		return voucher;
	}

	public void setVoucher(String voucher) {
		this.voucher = voucher;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Timestamp getCreatingTime() {
		return creatingTime;
	}

	public void setCreatingTime(Timestamp creatingTime) {
		this.creatingTime = creatingTime;
	}

	public Timestamp getLastModifyTime() {
		return lastModifyTime;
	}

	public void setLastModifyTime(Timestamp lastModifyTime) {
		this.lastModifyTime = lastModifyTime;
	}

}
