package com.seeyuan.logistics.entity;

/**
 * 
 * 车辆类型
 * 
 * @author zhazhaobao
 * 
 */
public class CarTypeInfo {

	/**
	 * 车辆类型
	 */
	private String car_type;

	/**
	 * 提示标签是否显示
	 */
	private boolean isCarSignVisible;

	public String getCar_type() {
		return car_type;
	}

	public void setCar_type(String car_type) {
		this.car_type = car_type;
	}

	public boolean isCarSignVisible() {
		return isCarSignVisible;
	}

	public void setCarSignVisible(boolean isCarSignVisible) {
		this.isCarSignVisible = isCarSignVisible;
	}

}
