package com.seeyuan.logistics.entity;

public class EvaluateInfo {

	// 评价内容是否需要单独独立出来一个实体类，待后期和后台协商借口时重新处理。暂分开做处理

	/**
	 * 是否点赞，点踩，TRUE为赞，FALSE为踩
	 */
	private boolean isPraise = false;

	/**
	 * 评价内容
	 */
	private String content;

	private String star;

	public boolean isPraise() {
		return isPraise;
	}

	public void setPraise(boolean isPraise) {
		this.isPraise = isPraise;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getStar() {
		return star;
	}

	public void setStar(String star) {
		this.star = star;
	}

	// 评价对象的区别，待后期和后台协商

}
