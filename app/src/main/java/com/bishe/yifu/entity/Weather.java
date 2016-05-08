package com.bishe.yifu.entity;

public class Weather {
	String city;// 城市
	String weather;// 天气情况
	String wendu;// 温度
	String windDir;// 风向
	String windPower;// 风力
	String humidity;// 空气湿度
	String reportTime;// 数据发布时间
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getWeather() {
		return weather;
	}
	public void setWeather(String weather) {
		this.weather = weather;
	}
	public String getWendu() {
		return wendu;
	}
	public void setWendu(String wendu) {
		this.wendu = wendu;
	}
	public String getWindDir() {
		return windDir;
	}
	public void setWindDir(String windDir) {
		this.windDir = windDir;
	}
	public String getWindPower() {
		return windPower;
	}
	public void setWindPower(String windPower) {
		this.windPower = windPower;
	}
	public String getHumidity() {
		return humidity;
	}
	public void setHumidity(String humidity) {
		this.humidity = humidity;
	}
	public String getReportTime() {
		return reportTime;
	}
	public void setReportTime(String reportTime) {
		this.reportTime = reportTime;
	}
	public Weather(String city, String weather, String wendu, String windDir, String windPower, String humidity,
				   String reportTime) {
		super();
		this.city = city;
		this.weather = weather;
		this.wendu = wendu;
		this.windDir = windDir;
		this.windPower = windPower;
		this.humidity = humidity;
		this.reportTime = reportTime;
	}



}
