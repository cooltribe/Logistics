package com.seeyuan.logistics.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * 入账信息传输类
 * 
 * @类名: AccountInDto.java
 * @描述: xxx
 * @作者: wangjinkui
 * @日期: 2014年12月9日 下午1:39:51
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
public class AccountInDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1468791900762778140L;

	private String id;

	private String billNo; // 单据号

	private String memberId; // 会员ID

	private String accountCode; // 账套标识

	private String accIntype; // 入账类型

	private BigDecimal inAmount; // 充值金额

	private BigDecimal inAmountCon;// 确认充值金额

	private BigDecimal bookRate; // 记账比率

	private BigDecimal settRate;// 结算比率

	private Timestamp procDeadline; // 处理时效

	private ImageDto voucher; // 入账凭证

	private String isCancel; // 是否取消

	private String refPayBillno; // 相关支付单号

	private AccountDto accountDto; // 冲值账户

	public String getBillNo() {
		return billNo;
	}

	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public String getAccountCode() {
		return accountCode;
	}

	public void setAccountCode(String accountCode) {
		this.accountCode = accountCode;
	}

	public String getAccIntype() {
		return accIntype;
	}

	public void setAccIntype(String accIntype) {
		this.accIntype = accIntype;
	}

	public BigDecimal getInAmount() {
		return inAmount;
	}

	public void setInAmount(BigDecimal inAmount) {
		this.inAmount = inAmount;
	}

	public BigDecimal getInAmountCon() {
		return inAmountCon;
	}

	public void setInAmountCon(BigDecimal inAmountCon) {
		this.inAmountCon = inAmountCon;
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

	public Timestamp getProcDeadline() {
		return procDeadline;
	}

	public void setProcDeadline(Timestamp procDeadline) {
		this.procDeadline = procDeadline;
	}

	public ImageDto getVoucher() {
		return voucher;
	}

	public void setVoucher(ImageDto voucher) {
		this.voucher = voucher;
	}

	public String getIsCancel() {
		return isCancel;
	}

	public void setIsCancel(String isCancel) {
		this.isCancel = isCancel;
	}

	public String getRefPayBillno() {
		return refPayBillno;
	}

	public void setRefPayBillno(String refPayBillno) {
		this.refPayBillno = refPayBillno;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public AccountDto getAccountDto() {
		return accountDto;
	}

	public void setAccountDto(AccountDto accountDto) {
		this.accountDto = accountDto;
	}

}
