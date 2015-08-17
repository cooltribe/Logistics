package com.seeyuan.logistics.entity;

import java.io.Serializable;

/**
 * PDA请求对象
 * 
 * @类名: PdaRequest.java
 * @描述: xxx
 * @作者: wangjinkui
 * @日期: 2014年8月21日 下午7:52:20
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
public class PdaRequest<T> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 932755814693221291L;

	/**
	 * 请求用户ID
	 */
	private String uuId;
	
	/**
	 * 用户名
	 */
	private String userName;

	/**
	 * 用户类型
	 */
	private String memberType;

	/**
	 * 请求的其他信息
	 */
	private T data;

	/**
	 * 分页对象。如果返回对象PdaResponse的data为list，则此参数有效。
	 */
	private PdaPagination pagination;

	/**
	 * 来源（WEB端值为WEB,Android端为ANDROID,IOS端为IOS）
	 */
	private String originApp = "ANDROID";

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public PdaPagination getPagination() {
		return pagination;
	}

	public void setPagination(PdaPagination pagination) {
		this.pagination = pagination;
	}

	public String getUuId() {
		return uuId;
	}

	public void setUuId(String uuId) {
		this.uuId = uuId;
	}

	public String getOriginApp() {
		return originApp;
	}

	public void setOriginApp(String originApp) {
		this.originApp = originApp;
	}

	public String getMemberType() {
		return memberType;
	}

	public void setMemberType(String memberType) {
		this.memberType = memberType;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

}
