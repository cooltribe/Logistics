package com.seeyuan.logistics.datahandler;

import android.content.Context;

import com.google.gson.Gson;
import com.seeyuan.logistics.application.NetWork;
import com.seeyuan.logistics.datacenter.DataHandler;
import com.seeyuan.logistics.entity.PdaRequest;
import com.seeyuan.logistics.entity.RouteDto;
import com.seeyuan.logistics.net.http.HttpAction;
import com.seeyuan.logistics.util.CommonUtils;

/**
 * 发布车源
 * 
 * @author zhazhaobao
 * 
 */
public class PublishCarSourceHandler extends DataHandler {
	private Context mContext;
	private String server_url;
	private PdaRequest<RouteDto> carSourceInfo;

	public PublishCarSourceHandler(Context context, PdaRequest<RouteDto> carSourceInfo) {
		this.mContext = context;
		this.server_url = NetWork.PUBLISH_CAR_SOURCE_ACTION;
		this.carSourceInfo = carSourceInfo;
	}

	public void startNetWork() {
		HttpAction httpAction = new HttpAction(HttpAction.REQUEST_TYPE_POST);
		httpAction.setUri(server_url);
		carSourceInfo.setUuId(CommonUtils.getUUID(mContext));
		carSourceInfo.setMemberType(CommonUtils.getMemberType(mContext));
		carSourceInfo.setOriginApp("ANDROID");
		String jsonString = new Gson().toJson(carSourceInfo);
		httpAction.addBodyParam("jsonString", jsonString);
		startNetwork(httpAction);
	}

	@Override
	protected void onNetReceiveOk(byte[] receiveBody) {
		sendResult(NetWork.PUBLISH_CAR_SOURCE_OK, receiveBody);
	}

	@Override
	protected void onNetReceiveError(int errorCode) {
		sendResult(NetWork.PUBLISH_CAR_SOURCE_ERROR, errorCode);
	}

	@Override
	protected void onNetReceiveTimeout(int errorCode) {
		sendResult(NetWork.PUBLISH_CAR_SOURCE_ERROR, errorCode);
	}
}
