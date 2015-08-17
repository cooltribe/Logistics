package com.seeyuan.logistics.entity;

public class FutureWeatherInfo {

	/**
	 * 温度
	 */
	private String temperature;

	/**
	 * 天气
	 */
	private String weather;

	/**
	 * 日期
	 */
	private String data;

	/**
	 * 星期
	 */
	private String week;

	/**
	 * 风向
	 */
	private String wind;

	public String getTemperature() {
		return temperature;
	}

	public void setTemperature(String temperature) {
		this.temperature = temperature;
	}

	public String getWeather() {
		return weather;
	}

	public void setWeather(String weather) {
		this.weather = weather;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getWeek() {
		return week;
	}

	public void setWeek(String week) {
		this.week = week;
	}

	public String getWind() {
		return wind;
	}

	public void setWind(String wind) {
		this.wind = wind;
	}

}
