package com.seeyuan.logistics.datahandler;

import android.content.Context;
import android.util.Log;

import com.seeyuan.logistics.application.ApplicationPool;
import com.seeyuan.logistics.application.NetWork;
import com.seeyuan.logistics.datacenter.DataHandler;
import com.seeyuan.logistics.entity.PersonalPositionInfo;
import com.seeyuan.logistics.net.http.HttpAction;

/**
 * 获取车源信息
 * @author Administrator
 *
 */
public class FindAroundCarHandler extends DataHandler {
	private Context mContext;
	private String server_url;
	private PersonalPositionInfo positionInfo;

	public FindAroundCarHandler(Context context,
			PersonalPositionInfo positionInfo) {
		this.mContext = context;
		this.server_url = NetWork.FIND_AROUND_CAR_ACTION;
		this.positionInfo = positionInfo;
	}

	public void startNetWork() {
		HttpAction httpAction = new HttpAction(HttpAction.REQUEST_TYPE_POST);
		httpAction.setUri(server_url);
		httpAction.addBodyParam("user_id", ApplicationPool.userID);
		httpAction.addBodyParam("longitude", positionInfo.getLongitude());
		httpAction.addBodyParam("latitude", positionInfo.getLatitude());

		startNetwork(httpAction);
	}

	@Override
	protected void onNetReceiveOk(byte[] receiveBody) {
		// String result = new String(receiveBody);
		sendResult(NetWork.FIND_AROUND_CAR_OK, receiveBody);
	}

	@Override
	protected void onNetReceiveError(int errorCode) {
		sendResult(NetWork.FIND_AROUND_CAR_ERROR, errorCode);
	}

	@Override
	protected void onNetReceiveTimeout(int errorCode) {
		sendResult(NetWork.FIND_AROUND_CAR_ERROR, errorCode);
	}
}
