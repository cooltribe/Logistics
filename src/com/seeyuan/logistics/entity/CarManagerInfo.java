package com.seeyuan.logistics.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 车辆管理
 * 
 * @author zhazhaobao
 * 
 */
public class CarManagerInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 车牌
	 */
	private String carPlate;

	/**
	 * 车辆状态
	 */
	private String carType;

	private List<CarManagerLineInfo> content = new ArrayList<CarManagerLineInfo>();

	public String getCarPlate() {
		return carPlate;
	}

	public void setCarPlate(String carPlate) {
		this.carPlate = carPlate;
	}

	public String getCarType() {
		return carType;
	}

	public void setCarType(String carType) {
		this.carType = carType;
	}

	public List<CarManagerLineInfo> getContent() {
		return content;
	}

	public void setContent(List<CarManagerLineInfo> content) {
		this.content = content;
	}

}
