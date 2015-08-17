package com.seeyuan.logistics.datahandler;

import android.content.Context;

import com.google.gson.Gson;
import com.seeyuan.logistics.application.NetWork;
import com.seeyuan.logistics.datacenter.DataHandler;
import com.seeyuan.logistics.entity.OrderDto;
import com.seeyuan.logistics.entity.PdaRequest;
import com.seeyuan.logistics.net.http.HttpAction;
import com.seeyuan.logistics.util.CommonUtils;

/**
 * 修改订单状态
 * 
 * @author zhazhaobao
 * 
 */
public class ChangeOrderStatusHandler extends DataHandler {
	private Context mContext;
	private String server_url;
	private PdaRequest<OrderDto> orderInfo;

	public ChangeOrderStatusHandler(Context context, PdaRequest<OrderDto> orderInfo) {
		this.mContext = context;
		this.server_url = NetWork.CHANGE_ORDER_STATUS_ACTION;
		this.orderInfo = orderInfo;
	}

	public void startNetWork() {
		HttpAction httpAction = new HttpAction(HttpAction.REQUEST_TYPE_POST);
		httpAction.setUri(server_url);
		orderInfo.setUuId(CommonUtils.getUUID(mContext));
		orderInfo.setMemberType(CommonUtils.getMemberType(mContext));
		orderInfo.setOriginApp("ANDROID");
		String jsonString = new Gson().toJson(orderInfo);
		httpAction.addBodyParam("jsonString", jsonString);

		startNetwork(httpAction);
	}

	@Override
	protected void onNetReceiveOk(byte[] receiveBody) {
		sendResult(NetWork.CHANGE_ORDER_STATUS_OK, receiveBody);
	}

	@Override
	protected void onNetReceiveError(int errorCode) {
		sendResult(NetWork.CHANGE_ORDER_STATUS_ERROR, errorCode);
	}

	@Override
	protected void onNetReceiveTimeout(int errorCode) {
		sendResult(NetWork.CHANGE_ORDER_STATUS_ERROR, errorCode);
	}
}
