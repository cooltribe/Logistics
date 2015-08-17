package com.seeyuan.logistics.datahandler;

import android.content.Context;

import com.google.gson.Gson;
import com.seeyuan.logistics.application.NetWork;
import com.seeyuan.logistics.datacenter.DataHandler;
import com.seeyuan.logistics.entity.CarsDto;
import com.seeyuan.logistics.entity.DriverDto;
import com.seeyuan.logistics.entity.OrderDto;
import com.seeyuan.logistics.entity.PdaRequest;
import com.seeyuan.logistics.net.http.HttpAction;
import com.seeyuan.logistics.util.CommonUtils;

/**
 * 订单，重新报价，修改价格
 * 
 * @author zhazhaobao
 * 
 */
public class EditOrderPriceHandler extends DataHandler {
	private Context mContext;
	private String server_url;
	private PdaRequest<OrderDto> orderDto;

	public EditOrderPriceHandler(Context context, PdaRequest<OrderDto> orderDto) {
		this.mContext = context;
		this.server_url = NetWork.EDIT_ORDER_PRICE_ACTION;
		this.orderDto = orderDto;
	}

	public void startNetWork() {
		HttpAction httpAction = new HttpAction(HttpAction.REQUEST_TYPE_POST);
		httpAction.setUri(server_url);
		orderDto.setUuId(CommonUtils.getUUID(mContext));
		orderDto.setMemberType(CommonUtils.getMemberType(mContext));
		orderDto.setOriginApp("ANDROID");
		String jsonString = new Gson().toJson(orderDto);
		httpAction.addBodyParam("jsonString", jsonString);

		startNetwork(httpAction);
	}

	@Override
	protected void onNetReceiveOk(byte[] receiveBody) {
		sendResult(NetWork.EDIT_ORDER_PRICE_OK, receiveBody);
	}

	@Override
	protected void onNetReceiveError(int errorCode) {
		sendResult(NetWork.EDIT_ORDER_PRICE_ERROR, errorCode);
	}

	@Override
	protected void onNetReceiveTimeout(int errorCode) {
		sendResult(NetWork.EDIT_ORDER_PRICE_ERROR, errorCode);
	}
}
