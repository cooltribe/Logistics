package com.seeyuan.logistics.datahandler;

import android.content.Context;

import com.baidu.platform.comapi.basestruct.GeoPoint;
import com.seeyuan.logistics.application.ApplicationPool;
import com.seeyuan.logistics.application.NetWork;
import com.seeyuan.logistics.datacenter.DataHandler;
import com.seeyuan.logistics.net.http.HttpAction;

public class GasStationHandler extends DataHandler {

	private Context mContext;
	private String server_url;
	private GeoPoint position;

	public GasStationHandler(Context context, GeoPoint position) {
		this.mContext = context;
		this.server_url = NetWork.GAS_STATION_SOURCE_ACTION;
		this.position = position;
	}

	public void startNetWork() {
		HttpAction httpAction = new HttpAction(HttpAction.REQUEST_TYPE_POST);
		httpAction.setUri(server_url);
		// httpAction.addBodyParam("lon",
		// String.valueOf(position.getLongitudeE6()));
		// httpAction
		// .addBodyParam("lat", String.valueOf(position.getLatitudeE6()));
		httpAction.addBodyParam("lon", "118.799306");
		httpAction.addBodyParam("lat", "31.93714");
		httpAction.addBodyParam("r", "3000");
		httpAction.addBodyParam("dtype", "json");
		httpAction.addBodyParam("page", "1");
		httpAction.addBodyParam("key", ApplicationPool.gasStationKey);

		startNetwork(httpAction);
	}

	@Override
	protected void onNetReceiveOk(byte[] receiveBody) {
		sendResult(NetWork.GAS_STATION_SOURCE_OK, receiveBody);
	}

	@Override
	protected void onNetReceiveError(int errorCode) {
		sendResult(NetWork.GAS_STATION_SOURCE_ERROR, errorCode);
	}

	@Override
	protected void onNetReceiveTimeout(int errorCode) {
		sendResult(NetWork.GAS_STATION_SOURCE_ERROR, errorCode);
	}
}
