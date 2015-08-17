package com.seeyuan.logistics.datahandler;

import android.content.Context;

import com.google.gson.Gson;
import com.seeyuan.logistics.application.NetWork;
import com.seeyuan.logistics.datacenter.DataHandler;
import com.seeyuan.logistics.entity.GoodsDto;
import com.seeyuan.logistics.entity.OrderDto;
import com.seeyuan.logistics.entity.PdaRequest;
import com.seeyuan.logistics.net.http.HttpAction;
import com.seeyuan.logistics.util.CommonUtils;

/**
 * 获取个人货源信息
 * 
 * @author zhazhaobao
 * 
 */
public class GetOrderOperationDataHandler extends DataHandler {
	private Context mContext;
	private String server_url;
	private PdaRequest<OrderDto> orderDto;

	public GetOrderOperationDataHandler(Context context,
			PdaRequest<OrderDto> orderDto) {
		this.mContext = context;
		this.server_url = NetWork.GET_ORDER_OPERATION_DATA_ACTION;
		this.orderDto = orderDto;
	}

	public void startNetWork() {
		HttpAction httpAction = new HttpAction(HttpAction.REQUEST_TYPE_POST);
		httpAction.setUri(server_url);
		orderDto.setUuId(CommonUtils.getUUID(mContext));
		orderDto.setMemberType(CommonUtils.getMemberType(mContext));
		orderDto.setOriginApp("ANDROID");
		httpAction.addBodyParam("jsonString", new Gson().toJson(orderDto));

		startNetwork(httpAction);
	}

	@Override
	protected void onNetReceiveOk(byte[] receiveBody) {
		sendResult(NetWork.GET_ORDER_OPERATION_DATA_OK, receiveBody);
	}

	@Override
	protected void onNetReceiveError(int errorCode) {
		sendResult(NetWork.GET_ORDER_OPERATION_DATA_ERROR, errorCode);
	}

	@Override
	protected void onNetReceiveTimeout(int errorCode) {
		sendResult(NetWork.GET_ORDER_OPERATION_DATA_ERROR, errorCode);
	}
}
