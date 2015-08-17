package com.seeyuan.logistics.entity;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 支付传输类
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
public class PaymentDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1468791900762778140L;

	private BigDecimal inAmount; // 充值金额

	private BigDecimal payAccount; // 付款金额

	private boolean isAccBalence; // 是否用余额支付

	private ImageDto voucher; // 入账凭证

	private AccountDto accountDto; // 支付账户

	private String orderId; // 订单ID

	public BigDecimal getInAmount() {
		return inAmount;
	}

	public void setInAmount(BigDecimal inAmount) {
		this.inAmount = inAmount;
	}

	public ImageDto getVoucher() {
		return voucher;
	}

	public void setVoucher(ImageDto voucher) {
		this.voucher = voucher;
	}

	public AccountDto getAccountDto() {
		return accountDto;
	}

	public void setAccountDto(AccountDto accountDto) {
		this.accountDto = accountDto;
	}

	public BigDecimal getPayAccount() {
		return payAccount;
	}

	public void setPayAccount(BigDecimal payAccount) {
		this.payAccount = payAccount;
	}

	public boolean isAccBalence() {
		return isAccBalence;
	}

	public void setAccBalence(boolean isAccBalence) {
		this.isAccBalence = isAccBalence;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

}
