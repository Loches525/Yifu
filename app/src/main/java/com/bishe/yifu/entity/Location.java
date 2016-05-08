package com.bishe.yifu.entity;

import java.util.List;

import com.amap.api.location.AMapLocalDayWeatherForecast;
import com.amap.api.location.AMapLocalWeatherForecast;
import com.amap.api.location.AMapLocalWeatherListener;
import com.amap.api.location.AMapLocalWeatherLive;
import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.location.LocationManagerProxy;
import com.amap.api.location.LocationProviderProxy;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

/**
 * Created by jzh on 2015/10/6.
 */
public class Location {

	private LocationManagerProxy mLocationManagerProxy;// 高德定位
	public String location;
	public String city;
	Context context;
	update_location ul = null;

	public Location(Context context, update_location ul) {
		this.context = context;
		this.ul = ul;
		mLocationManagerProxy = LocationManagerProxy.getInstance(context);
		// 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
		// 注意设置合适的定位时间的间隔，并且在合适时间调用removeUpdates()方法来取消定位请求
		// 在定位结束后，在合适的生命周期调用destroy()方法
		// 其中如果间隔时间为-1，则定位只定一次
		mLocationManagerProxy.requestWeatherUpdates(LocationManagerProxy.WEATHER_TYPE_LIVE, alll);
		mLocationManagerProxy.requestLocationData(LocationProviderProxy.AMapNetwork, -1, 15, all);
		mLocationManagerProxy.setGpsEnable(false);

	}

	AMapLocalWeatherListener alll = new AMapLocalWeatherListener() {

		@Override
		public void onWeatherLiveSearched(AMapLocalWeatherLive aMapLocalWeatherLive) {
			if (aMapLocalWeatherLive != null && aMapLocalWeatherLive.getAMapException().getErrorCode() == 0) {
				String city = aMapLocalWeatherLive.getCity();// 城市
				String weather = aMapLocalWeatherLive.getWeather();// 天气情况
				String wendu = aMapLocalWeatherLive.getTemperature();// 温度
				String windDir = aMapLocalWeatherLive.getWindDir();// 风向
				String windPower = aMapLocalWeatherLive.getWindPower();// 风力
				String humidity = aMapLocalWeatherLive.getHumidity();// 空气湿度
				String reportTime = aMapLocalWeatherLive.getReportTime();// 数据发布时间
				Weather w = new Weather(city, weather, wendu, windDir, windPower, humidity, reportTime);
				ul.update_tianqi(w);
				// if (weather.contains("雨")) {
				// iv_tianqi.setImageResource(R.mipmap.yu_60);
				// } else if (weather.equals("阴")) {
				// iv_tianqi.setImageResource(R.mipmap.yun_26);
				// } else if (weather.contains("云")) {
				// iv_tianqi.setImageResource(R.mipmap.c_28);
				// } else if (weather.contains("雪")) {
				// iv_tianqi.setImageResource(R.mipmap.c_14);
				// } else if (weather.equals("晴")) {
				// iv_tianqi.setImageResource(R.mipmap.c_32);
				// } else if (weather.contains("沙")) {
				// iv_tianqi.setImageResource(R.mipmap.c_62);
				// } else if (weather.contains("霾") ||
				// weather.contains("雾")) {
				// iv_tianqi.setImageResource(R.mipmap.c_63);
				// }

			}
		}

		@Override
		public void onWeatherForecaseSearched(AMapLocalWeatherForecast aMapLocalWeatherForecast) {
		}
	};

	AMapLocationListener all = new AMapLocationListener() {

		@Override
		public void onLocationChanged(android.location.Location location) {

		}

		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {

		}

		@Override
		public void onProviderEnabled(String provider) {

		}

		@Override
		public void onProviderDisabled(String provider) {

		}

		@Override
		public void onLocationChanged(AMapLocation amapLocation) {
			if (amapLocation != null && amapLocation.getAMapException().getErrorCode() == 0) {
				// 获取位置信息
				location = amapLocation.getAddress();
				city = amapLocation.getProvince() + "·" + amapLocation.getCity() + amapLocation.getDistrict();
				ul.update_dizhi(location);
				// stopLocation();
			}
		}
	};

	/**
	 * 停止定位
	 */
	public void stopLocation() {
		if (mLocationManagerProxy != null) {
			mLocationManagerProxy.removeUpdates(all);
			mLocationManagerProxy.destory();
		}
		mLocationManagerProxy = null;
	}

	public interface update_location {
		public void update_dizhi(String location);

		public void update_tianqi(Weather w);
	}

}
