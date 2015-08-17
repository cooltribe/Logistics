package com.seeyuan.logistics.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * 订单数据传输类
 * 
 * @类名: GoodsDto.java
 * @描述: xxx
 * @作者: wangjinkui
 * @日期: 2014年8月29日 上午10:07:54
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
public class OrderDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4959670648387042093L;

	/**
	 * 订单ID
	 */
	private String orderId;

	/**
	 * 订单编号
	 */
	private String orderNo;

	/**
	 * 司机ID
	 */
	private DriverDto driver;

	/**
	 * 货源ID
	 */
	private GoodsDto goods;

	/**
	 * 车源ID
	 */
	private CarsDto cars;

	/**
	 * 货源方会员ID
	 */
	private String goodsMembId;

	/**
	 * 车源方会员ID
	 */
	private String vehicleMembId;

	/**
	 * 当前操作会员ID
	 */
	private String curOpMembId;

	/**
	 * 订单达成方式
	 */
	private String oderCraType;

	/**
	 * 可支付方式
	 */
	private String payType;

	/**
	 * 是否需要发票
	 */
	private String isNeedInvoice;

	/**
	 * 抬头
	 */
	private String invoiceTitle;

	/**
	 * 上次运输费用
	 */
	private BigDecimal lastTransAmount;

	/**
	 * 运输费用
	 */
	private BigDecimal transAmount;

	/**
	 * 保险费用
	 */
	private BigDecimal insuranceAmount;

	/**
	 * 担保费用
	 */
	private BigDecimal ProtectAmount;

	/**
	 * 订单总额
	 */
	private BigDecimal orderAmount;

	/**
	 * 描述
	 */
	private String curStatusDesc;

	/**
	 * 是否取消
	 */
	private String isCancel;

	/**
	 * 取消原因
	 */
	private String cancelReason;

	/***
	 * 货物清单图片路径
	 */
	private ImageDto goodsList;

	/**
	 * 回单图片路径
	 */
	private ImageDto peceipt;

	/**
	 * 订单状态
	 */
	private String orderStatus;

	/**
	 * 付款状态
	 */
	private String payStatus;

	/**
	 * 货源方评价状态
	 */
	private String gdsEvaStatus;

	/**
	 * 车源方评价状态
	 */
	private String vehEvaStatus;

	/**
	 * 订单状态集合（bargain 交易中，execute为 执行中，complete完成）
	 */
	private String allOrderStatus;

	/**
	 * 货源发布人
	 */
	private String goodsUserName;

	/**
	 * 货主真实姓名
	 */
	private String goodsRealName;

	/**
	 * 更改订单命令( CreateOrderByGoods 货源方发起订单 CreateOrderByVehicle 车辆方发起订单
	 * ConfirmationOrder 确认订单 DeliverGoods 发货 ArrivalGoods 到货 ConfirmTransaction
	 * 确认交易)
	 */
	private String command;

	/**
	 * 操作日志列表
	 */
	private List<OrderTraceDto> orderTraces;

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getGoodsMembId() {
		return goodsMembId;
	}

	public void setGoodsMembId(String goodsMembId) {
		this.goodsMembId = goodsMembId;
	}

	public String getVehicleMembId() {
		return vehicleMembId;
	}

	public void setVehicleMembId(String vehicleMembId) {
		this.vehicleMembId = vehicleMembId;
	}

	public String getCurOpMembId() {
		return curOpMembId;
	}

	public void setCurOpMembId(String curOpMembId) {
		this.curOpMembId = curOpMembId;
	}

	public String getOderCraType() {
		return oderCraType;
	}

	public void setOderCraType(String oderCraType) {
		this.oderCraType = oderCraType;
	}

	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	public String getIsNeedInvoice() {
		return isNeedInvoice;
	}

	public void setIsNeedInvoice(String isNeedInvoice) {
		this.isNeedInvoice = isNeedInvoice;
	}

	public String getInvoiceTitle() {
		return invoiceTitle;
	}

	public void setInvoiceTitle(String invoiceTitle) {
		this.invoiceTitle = invoiceTitle;
	}

	public BigDecimal getLastTransAmount() {
		return lastTransAmount;
	}

	public void setLastTransAmount(BigDecimal lastTransAmount) {
		this.lastTransAmount = lastTransAmount;
	}

	public BigDecimal getTransAmount() {
		return transAmount;
	}

	public void setTransAmount(BigDecimal transAmount) {
		this.transAmount = transAmount;
	}

	public BigDecimal getInsuranceAmount() {
		return insuranceAmount;
	}

	public void setInsuranceAmount(BigDecimal insuranceAmount) {
		this.insuranceAmount = insuranceAmount;
	}

	public BigDecimal getProtectAmount() {
		return ProtectAmount;
	}

	public void setProtectAmount(BigDecimal protectAmount) {
		ProtectAmount = protectAmount;
	}

	public BigDecimal getOrderAmount() {
		return orderAmount;
	}

	public void setOrderAmount(BigDecimal orderAmount) {
		this.orderAmount = orderAmount;
	}

	public String getCurStatusDesc() {
		return curStatusDesc;
	}

	public void setCurStatusDesc(String curStatusDesc) {
		this.curStatusDesc = curStatusDesc;
	}

	public String getIsCancel() {
		return isCancel;
	}

	public void setIsCancel(String isCancel) {
		this.isCancel = isCancel;
	}

	public String getCancelReason() {
		return cancelReason;
	}

	public void setCancelReason(String cancelReason) {
		this.cancelReason = cancelReason;
	}

	public ImageDto getGoodsList() {
		return goodsList;
	}

	public void setGoodsList(ImageDto goodsList) {
		this.goodsList = goodsList;
	}

	public ImageDto getPeceipt() {
		return peceipt;
	}

	public void setPeceipt(ImageDto peceipt) {
		this.peceipt = peceipt;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public String getPayStatus() {
		return payStatus;
	}

	public void setPayStatus(String payStatus) {
		this.payStatus = payStatus;
	}

	public String getGdsEvaStatus() {
		return gdsEvaStatus;
	}

	public void setGdsEvaStatus(String gdsEvaStatus) {
		this.gdsEvaStatus = gdsEvaStatus;
	}

	public String getVehEvaStatus() {
		return vehEvaStatus;
	}

	public void setVehEvaStatus(String vehEvaStatus) {
		this.vehEvaStatus = vehEvaStatus;
	}

	public String getAllOrderStatus() {
		return allOrderStatus;
	}

	public void setAllOrderStatus(String allOrderStatus) {
		this.allOrderStatus = allOrderStatus;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public DriverDto getDriver() {
		return driver;
	}

	public void setDriver(DriverDto driver) {
		this.driver = driver;
	}

	public GoodsDto getGoods() {
		return goods;
	}

	public void setGoods(GoodsDto goods) {
		this.goods = goods;
	}

	public CarsDto getCars() {
		return cars;
	}

	public void setCars(CarsDto cars) {
		this.cars = cars;
	}

	public String getGoodsUserName() {
		return goodsUserName;
	}

	public void setGoodsUserName(String goodsUserName) {
		this.goodsUserName = goodsUserName;
	}

	public String getGoodsRealName() {
		return goodsRealName;
	}

	public void setGoodsRealName(String goodsRealName) {
		this.goodsRealName = goodsRealName;
	}

	public String getCommand() {
		return command;
	}

	public void setCommand(String command) {
		this.command = command;
	}

	public List<OrderTraceDto> getOrderTraces() {
		return orderTraces;
	}

	public void setOrderTraces(List<OrderTraceDto> orderTraces) {
		this.orderTraces = orderTraces;
	}

}
