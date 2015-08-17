package com.seeyuan.logistics.datahandler;

import android.content.Context;

import com.seeyuan.logistics.application.ApplicationPool;
import com.seeyuan.logistics.application.NetWork;
import com.seeyuan.logistics.datacenter.DataHandler;
import com.seeyuan.logistics.net.http.HttpAction;

public class WeatherHandler extends DataHandler {
	private Context mContext;
	private String server_url;
	private String cityName;

	public WeatherHandler(Context context, String cityName) {
		this.mContext = context;
		this.server_url = NetWork.WEATHER_SOURCE_ACTION;
		this.cityName = cityName;
	}

	public void startNetWork() {
		HttpAction httpAction = new HttpAction(HttpAction.REQUEST_TYPE_GET);
		httpAction.setUri(server_url + "key=" + ApplicationPool.weatherKey
				+ "&dtype=" + "json" + "&cityname=" + cityName + "&format="
				+ "2");

		startNetwork(httpAction);
	}

	@Override
	protected void onNetReceiveOk(byte[] receiveBody) {
		// String result = new String(receiveBody);
		sendResult(NetWork.WEATHER_SOURCE_OK, receiveBody);
	}

	@Override
	protected void onNetReceiveError(int errorCode) {
		sendResult(NetWork.WEATHER_SOURCE_ERROR, errorCode);
	}

	@Override
	protected void onNetReceiveTimeout(int errorCode) {
		sendResult(NetWork.WEATHER_SOURCE_ERROR, errorCode);
	}
}
