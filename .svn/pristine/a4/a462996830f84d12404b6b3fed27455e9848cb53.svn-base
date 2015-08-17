package com.seeyuan.logistics.entity;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 会员结算账户业务传输类
 * 
 * @类名: MemAccountDto.java
 * @描述: xxx
 * @作者: wangjinkui
 * @日期: 2014年12月8日 上午10:55:11
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
public class MemAccountDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8821036192041005569L;

	/**
	 * 会员账户ID
	 */
	private String id;

	private String accountCode; // 账套标识

	private BigDecimal balance; // 平台账号的余额

	private String freezeRes; // 账户类型（关联数据字典表TYPE_CODE='FREEZE_RES'） 1.交易担保
								// 2.平台担保金 3.待结算

	private String isFreeze; // 是否冻结

	private BigDecimal bookRate; // 记账比率

	private BigDecimal settRate;// 结算比率

	private String remark; // 备注

	public String getAccountCode() {
		return accountCode;
	}

	public void setAccountCode(String accountCode) {
		this.accountCode = accountCode;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	public String getFreezeRes() {
		return freezeRes;
	}

	public void setFreezeRes(String freezeRes) {
		this.freezeRes = freezeRes;
	}

	public String getIsFreeze() {
		return isFreeze;
	}

	public void setIsFreeze(String isFreeze) {
		this.isFreeze = isFreeze;
	}

	public BigDecimal getBookRate() {
		return bookRate;
	}

	public void setBookRate(BigDecimal bookRate) {
		this.bookRate = bookRate;
	}

	public BigDecimal getSettRate() {
		return settRate;
	}

	public void setSettRate(BigDecimal settRate) {
		this.settRate = settRate;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}
