package com.seeyuan.logistics.service;

import android.content.Context;

import com.google.gson.Gson;
import com.seeyuan.logistics.application.NetWork;
import com.seeyuan.logistics.datacenter.DataHandler;
import com.seeyuan.logistics.entity.LocationDto;
import com.seeyuan.logistics.entity.PdaRequest;
import com.seeyuan.logistics.net.http.HttpAction;
import com.seeyuan.logistics.util.CommonUtils;

/**
 * 提交个人位置信息
 * 
 * @author Administrator
 * 
 */
public class SubmitCarPositionHandler extends DataHandler {
	private Context mContext;
	private String server_url;
	private PdaRequest<LocationDto> positionInfo;

	public SubmitCarPositionHandler(Context context,
			PdaRequest<LocationDto> positionInfo) {
		this.mContext = context;
		this.server_url = NetWork.SUBMIT_CAR_POSITION_ACTION;
		this.positionInfo = positionInfo;
	}

	public void startNetWork() {
		HttpAction httpAction = new HttpAction(HttpAction.REQUEST_TYPE_POST);
		httpAction.setUri(server_url);
		positionInfo.setUuId(CommonUtils.getUUID(mContext));
		positionInfo.setOriginApp("ANDROID");
		String jsonString = new Gson().toJson(positionInfo);
		httpAction.addBodyParam("jsonString", jsonString);
		startNetwork(httpAction); 
	}

	@Override
	protected void onNetReceiveOk(byte[] receiveBody) {
		// String result = new String(receiveBody);
		sendResult(NetWork.SUBMIT_CAR_POSITION_OK, receiveBody);
	}

	@Override
	protected void onNetReceiveError(int errorCode) {
		sendResult(NetWork.SUBMIT_CAR_POSITION_ERROR, errorCode);
	}

	@Override
	protected void onNetReceiveTimeout(int errorCode) {
		sendResult(NetWork.SUBMIT_CAR_POSITION_ERROR, errorCode);
	}
}
