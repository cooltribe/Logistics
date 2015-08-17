package com.seeyuan.logistics.entity;

/**
 * 
 * @类名: PdaPagination.java
 * @描述: 分页信息对象。默认情况下认为需要不需要分页。 如果不需要分页，需要调用setNeedsPaginate()方法。
 *      如果不需要排序，需要调用setNeedsSort()方法。
 * @作者: wangjinkui
 * @日期: 2014年8月21日 下午7:53:14
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
public class PdaPagination {

	/**
	 * 标记是否分页。
	 */
	private boolean needsPaginate;

	/**
	 * 标记是否排序。
	 */
	private boolean needsSort;

	/**
	 * 分页的起始记录序号。
	 */
	private int startPos;

	/**
	 * 每页需要取出的记录大小。
	 */
	private int amount;

	/**
	 * 需要排序的属性名。
	 */
	private String sortProperty;

	/**
	 * 是否正向排序
	 */
	private boolean asc;

	/**
	 * 默认构造函数，默认不分页不排序。
	 */
	public PdaPagination() {
		this.needsPaginate = true;
		this.needsSort = false;
		this.startPos = -1;
		this.amount = 0;
		this.sortProperty = null;
		this.asc = false;
	}

	/**
	 * 可以分别指定是否需要分页和需要排序的对象。 如果指定了需要分页，那么调用者必须保证为其添加分页信息，否则默认从第一条记录开始，每页10条记录。
	 * 如果指定了需要排序，那么调用者必须保证为其添加排序信息，否则默认以id属性顺序排列。
	 * 
	 * @param needsPaginate
	 *            是否需要分页。
	 * @param needsSort
	 *            是否需要排序。
	 */
	public PdaPagination(boolean needsPaginate, boolean needsSort) {
		this.needsPaginate = needsPaginate;
		this.needsSort = needsSort;
		this.startPos = 0;
		this.amount = 10;
		this.sortProperty = "id";
		this.asc = true;
	}

	/**
	 * 既分页也排序，并给出相应的参数。
	 * 
	 * @param startPos
	 *            分页的起始记录编号。
	 * @param amount
	 *            每页大小。
	 * @param sortProperty
	 *            需要排序的属性。
	 * @param asc
	 *            是否顺序排序。
	 */
	public PdaPagination(int startPos, int amount, String sortProperty,
			boolean asc) {
		this.needsPaginate = true;
		this.needsSort = true;
		this.startPos = startPos;
		this.amount = amount;
		this.sortProperty = sortProperty;
		this.asc = asc;
	}

	/**
	 * @return the needsPaginate
	 */
	public boolean isNeedsPaginate() {
		return this.needsPaginate;
	}

	/**
	 * @param needsPaginate
	 *            the needsPaginate to set
	 */
	public void setNeedsPaginate(boolean needsPaginate) {
		this.needsPaginate = needsPaginate;
	}

	/**
	 * @return the needsSort
	 */
	public boolean isNeedsSort() {
		return this.needsSort;
	}

	/**
	 * @param needsSort
	 *            the needsSort to set
	 */
	public void setNeedsSort(boolean needsSort) {
		this.needsSort = needsSort;
	}

	/**
	 * @return the startPos
	 */
	public int getStartPos() {
		return this.startPos;
	}

	/**
	 * @param startPos
	 *            the startPos to set
	 */
	public void setStartPos(int startPos) {
		this.startPos = startPos;
	}

	/**
	 * @return the amount
	 */
	public int getAmount() {
		return this.amount;
	}

	/**
	 * @param amount
	 *            the amount to set
	 */
	public void setAmount(int amount) {
		this.amount = amount;
	}

	/**
	 * @return the sortProperty
	 */
	public String getSortProperty() {
		return this.sortProperty;
	}

	/**
	 * @param sortProperty
	 *            the sortProperty to set
	 */
	public void setSortProperty(String sortProperty) {
		this.sortProperty = sortProperty;
	}

	/**
	 * @return the asc
	 */
	public boolean isAsc() {
		return this.asc;
	}

	/**
	 * @param asc
	 *            the asc to set
	 */
	public void setAsc(boolean asc) {
		this.asc = asc;
	}
}
