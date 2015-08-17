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
 * 下单
 * 
 * @author zhazhaobao
 * 
 */
public class PlaceAnOrderHandler extends DataHandler {
	private Context mContext;
	private String server_url;
	private PdaRequest<OrderDto> orderInfo;

	public PlaceAnOrderHandler(Context context, PdaRequest<OrderDto> orderInfo) {
		this.mContext = context;
		this.server_url = NetWork.PLACE_AN_ORDER_ACTION;
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
		sendResult(NetWork.PLACE_AN_ORDER_OK, receiveBody);
	}

	@Override
	protected void onNetReceiveError(int errorCode) {
		sendResult(NetWork.PLACE_AN_ORDER_ERROR, errorCode);
	}

	@Override
	protected void onNetReceiveTimeout(int errorCode) {
		sendResult(NetWork.PLACE_AN_ORDER_ERROR, errorCode);
	}
}
