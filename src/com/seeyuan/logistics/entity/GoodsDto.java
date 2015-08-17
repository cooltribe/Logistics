package com.seeyuan.logistics.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 货源数据传输类
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
public class GoodsDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8808472832946189560L;
	
	/**
	 * 货源ID
	 */
	private String goodsId;
	
	private String originApp;//数据来源应用的代码:
	
	private String goodsName;//货物名称
	
	private String goodsType;//货物类型
	
	private BigDecimal goodsWeight;//货物重量(吨)
	
	private Double goodsVolume;//货物体积(方)
	
	private Integer count;//货物件数
	
	private String  vehType;//车型要求
	
	private String vehLegth;//车长要求(m)
	
	private String packages;//包装要求
	
	private BigDecimal cost;//期望运费(元)
	
	private String setout;//始发地
	
	private String setoutDetail;//始发地地址
	
	private String  destination;//目的地
	
	private String  destinationDetail;//目的地地址
	
	private String disMode;//配送方式（关联数据字典）
	
	private String notice;//注意事项（关联数据字典）
	
	private String  isAlwaysValid;//是否长期货源 Y 是 N 否
	
	private Date  validDeadline;//有效截止日期
	
	private Date  deliveryDateF;//发货日期_从
	
	private Date  deliveryDateT;//发货日期_到
	
	private Date  receiveDateF;//收货日期_从
	
	private Date  receiveDateT;//收货日期_到
	
	private String  remark;//补充说明
	
	private String   contactName;//联系人姓名
	
	private String mPhone;//手机
	
	private String fPhone;//固定电话
	
	private String companyName;//公司名称
	
	private String payType;//可支付方式（关联数据字典表）
	
	private String isNeedInvoice;//是否需要发票 Y 需要 N 不需要
	
	private String invoiceTitle;//发票抬头
	
	private ImageDto goodsPicture; //货源相片
	
	private String isBackPre;//是否回程优先(Y 是 N 否)
	
	private String creatingMemberId;//创建人
	
	private List<String> ids;//id集合
	

	public String getOriginApp() {
		return originApp;
	}

	public void setOriginApp(String originApp) {
		this.originApp = originApp;
	}

	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	public String getGoodsType() {
		return goodsType;
	}

	public void setGoodsType(String goodsType) {
		this.goodsType = goodsType;
	}

	public BigDecimal getGoodsWeight() {
		return goodsWeight;
	}

	public void setGoodsWeight(BigDecimal goodsWeight) {
		this.goodsWeight = goodsWeight;
	}

	public Double getGoodsVolume() {
		return goodsVolume;
	}

	public void setGoodsVolume(Double goodsVolume) {
		this.goodsVolume = goodsVolume;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public String getVehType() {
		return vehType;
	}

	public void setVehType(String vehType) {
		this.vehType = vehType;
	}

	public String getVehLegth() {
		return vehLegth;
	}

	public void setVehLegth(String vehLegth) {
		this.vehLegth = vehLegth;
	}

	public String getPackages() {
		return packages;
	}

	public void setPackages(String packages) {
		this.packages = packages;
	}

	public BigDecimal getCost() {
		return cost;
	}

	public void setCost(BigDecimal cost) {
		this.cost = cost;
	}

	public String getSetout() {
		return setout;
	}

	public void setSetout(String setout) {
		this.setout = setout;
	}

	public String getSetoutDetail() {
		return setoutDetail;
	}

	public void setSetoutDetail(String setoutDetail) {
		this.setoutDetail = setoutDetail;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public String getDestinationDetail() {
		return destinationDetail;
	}

	public void setDestinationDetail(String destinationDetail) {
		this.destinationDetail = destinationDetail;
	}

	public String getDisMode() {
		return disMode;
	}

	public void setDisMode(String disMode) {
		this.disMode = disMode;
	}

	public String getNotice() {
		return notice;
	}

	public void setNotice(String notice) {
		this.notice = notice;
	}

	public String getIsAlwaysValid() {
		return isAlwaysValid;
	}

	public void setIsAlwaysValid(String isAlwaysValid) {
		this.isAlwaysValid = isAlwaysValid;
	}

	public Date getValidDeadline() {
		return validDeadline;
	}

	public void setValidDeadline(Date validDeadline) {
		this.validDeadline = validDeadline;
	}

	public Date getDeliveryDateF() {
		return deliveryDateF;
	}

	public void setDeliveryDateF(Date deliveryDateF) {
		this.deliveryDateF = deliveryDateF;
	}

	public Date getDeliveryDateT() {
		return deliveryDateT;
	}

	public void setDeliveryDateT(Date deliveryDateT) {
		this.deliveryDateT = deliveryDateT;
	}

	public Date getReceiveDateF() {
		return receiveDateF;
	}

	public void setReceiveDateF(Date receiveDateF) {
		this.receiveDateF = receiveDateF;
	}

	public Date getReceiveDateT() {
		return receiveDateT;
	}

	public void setReceiveDateT(Date receiveDateT) {
		this.receiveDateT = receiveDateT;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getContactName() {
		return contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	public String getmPhone() {
		return mPhone;
	}

	public void setmPhone(String mPhone) {
		this.mPhone = mPhone;
	}

	public String getfPhone() {
		return fPhone;
	}

	public void setfPhone(String fPhone) {
		this.fPhone = fPhone;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
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

	public String getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(String goodsId) {
		this.goodsId = goodsId;
	}

	public ImageDto getGoodsPicture() {
		return goodsPicture;
	}

	public void setGoodsPicture(ImageDto goodsPicture) {
		this.goodsPicture = goodsPicture;
	}

	public String getIsBackPre() {
		return isBackPre;
	}

	public void setIsBackPre(String isBackPre) {
		this.isBackPre = isBackPre;
	}

	public List<String> getIds() {
		return ids;
	}

	public void setIds(List<String> ids) {
		this.ids = ids;
	}

	public String getCreatingMemberId() {
		return creatingMemberId;
	}

	public void setCreatingMemberId(String creatingMemberId) {
		this.creatingMemberId = creatingMemberId;
	}}
