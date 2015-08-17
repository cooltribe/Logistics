package com.seeyuan.logistics.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSerializer;

/**
 * web端向pda端响应时的对象（需要经过gson处理后返回给请求段）
 * 
 * @类名: PdaResponse.java
 * @描述: xxx
 * @作者: wangjinkui
 * @日期: 2014年8月21日 下午7:55:58
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
public class PdaResponse<T> implements Serializable {

	private static final long serialVersionUID = -7816945325851639128L;

	private boolean success;

	private String message;

	private T data;

	private long total = 0;

	private long currentTotal = 0;

	public PdaResponse() {
		this(true, "", null);
	}

	public PdaResponse(boolean success, String message, T data) {
		this.success = success;
		this.message = message;
		this.data = data;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}

	public long getCurrentTotal() {
		return currentTotal;
	}

	public void setCurrentTotal(long currentTotal) {
		this.currentTotal = currentTotal;
	}

}
