package com.seeyuan.logistics.datahandler;

import android.content.Context;

import com.google.gson.Gson;
import com.seeyuan.logistics.application.NetWork;
import com.seeyuan.logistics.datacenter.DataHandler;
import com.seeyuan.logistics.entity.CarsDto;
import com.seeyuan.logistics.entity.DriverDto;
import com.seeyuan.logistics.entity.PdaRequest;
import com.seeyuan.logistics.net.http.HttpAction;
import com.seeyuan.logistics.util.CommonUtils;

/**
 * 添加新车辆
 * 
 * @author zhazhaobao
 * 
 */
public class GetCurrentOrderDetailHandler extends DataHandler {
	private Context mContext;
	private String server_url;
	private PdaRequest<String> orderInfo;

	public GetCurrentOrderDetailHandler(Context context,
			PdaRequest<String> orderInfo) {
		this.mContext = context;
		this.server_url = NetWork.GET_CURRENT_ORDER_MESSAGE_ACTION;
		this.orderInfo = orderInfo;
	}

	public void startNetWork() {
		HttpAction httpAction = new HttpAction(HttpAction.REQUEST_TYPE_POST);
		httpAction.setUri(server_url);
		orderInfo.setUuId(CommonUtils.getUUID(mContext));
		orderInfo.setMemberType(CommonUtils.getMemberType(mContext));
		orderInfo.setOriginApp("ANDROID");
		httpAction.addBodyParam("jsonString", new Gson().toJson(orderInfo));

		startNetwork(httpAction);
	}

	@Override
	protected void onNetReceiveOk(byte[] receiveBody) {
		sendResult(NetWork.GET_CURRENT_ORDER_MESSAGE_OK, receiveBody);
	}

	@Override
	protected void onNetReceiveError(int errorCode) {
		sendResult(NetWork.GET_CURRENT_ORDER_MESSAGE_ERROR, errorCode);
	}

	@Override
	protected void onNetReceiveTimeout(int errorCode) {
		sendResult(NetWork.GET_CURRENT_ORDER_MESSAGE_ERROR, errorCode);
	}
}
