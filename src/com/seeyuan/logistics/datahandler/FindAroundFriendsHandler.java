package com.seeyuan.logistics.datahandler;

import android.content.Context;

import com.seeyuan.logistics.application.ApplicationPool;
import com.seeyuan.logistics.application.NetWork;
import com.seeyuan.logistics.datacenter.DataHandler;
import com.seeyuan.logistics.entity.PersonalPositionInfo;
import com.seeyuan.logistics.net.http.HttpAction;

public class FindAroundFriendsHandler extends DataHandler {
	private Context mContext;
	private String server_url;
	private PersonalPositionInfo positionInfo;

	public FindAroundFriendsHandler(Context context,
			PersonalPositionInfo positionInfo) {
		this.mContext = context;
		this.server_url = NetWork.FIND_AROUND_FIRENDS_ACTION;
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
		sendResult(NetWork.FIND_AROUND_FIRENDS_OK, receiveBody);
	}

	@Override
	protected void onNetReceiveError(int errorCode) {
		sendResult(NetWork.FIND_AROUND_FIRENDS_ERROR, errorCode);
	}

	@Override
	protected void onNetReceiveTimeout(int errorCode) {
		sendResult(NetWork.FIND_AROUND_FIRENDS_ERROR, errorCode);
	}
}
