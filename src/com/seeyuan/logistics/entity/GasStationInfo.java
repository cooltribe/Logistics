package com.seeyuan.logistics.entity;

public class GasStationInfo {

	/**
	 * ID
	 */
	private String id;
	/**
	 * 加油站名称
	 */
	private String name;
	/**
	 * 城市邮编
	 */
	private String area;
	/**
	 * 城市区域
	 */
	private String areaname;
	/**
	 * 加油站地址
	 */
	private String address;
	/**
	 * 运营商类型
	 */
	private String brandname;
	/**
	 * 加油站类型
	 */
	private String type;
	/**
	 * 是否打折加油站
	 */
	private String discount;
	/**
	 * 尾气排放标准
	 */
	private String exhaust;
	/**
	 * 谷歌地图坐标
	 */
	private String position;
	/**
	 * 百度地图纬度
	 */
	private String lon;
	/**
	 * 百度地图经度
	 */
	private String lat;
	/**
	 * 省控基准油价
	 */
	private String price;
	/**
	 * 加油站油价
	 */
	private String gastprice;
	/**
	 * 加油卡信息
	 */
	private String fwlsmc;
	/**
	 * 与坐标的距离，单位M
	 */
	private String distance;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getAreaname() {
		return areaname;
	}

	public void setAreaname(String areaname) {
		this.areaname = areaname;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getBrandname() {
		return brandname;
	}

	public void setBrandname(String brandname) {
		this.brandname = brandname;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDiscount() {
		return discount;
	}

	public void setDiscount(String discount) {
		this.discount = discount;
	}

	public String getExhaust() {
		return exhaust;
	}

	public void setExhaust(String exhaust) {
		this.exhaust = exhaust;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getLon() {
		return lon;
	}

	public void setLon(String lon) {
		this.lon = lon;
	}

	public String getLat() {
		return lat;
	}

	public void setLat(String lat) {
		this.lat = lat;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getGastprice() {
		return gastprice;
	}

	public void setGastprice(String gastprice) {
		this.gastprice = gastprice;
	}

	public String getFwlsmc() {
		return fwlsmc;
	}

	public void setFwlsmc(String fwlsmc) {
		this.fwlsmc = fwlsmc;
	}

	public String getDistance() {
		return distance;
	}

	public void setDistance(String distance) {
		this.distance = distance;
	}

}
