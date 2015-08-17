package com.seeyuan.logistics.datahandler;

import android.content.Context;

import com.google.gson.Gson;
import com.seeyuan.logistics.application.NetWork;
import com.seeyuan.logistics.datacenter.DataHandler;
import com.seeyuan.logistics.entity.DriverDto;
import com.seeyuan.logistics.entity.MemberDto;
import com.seeyuan.logistics.entity.OrderDto;
import com.seeyuan.logistics.entity.PaymentDto;
import com.seeyuan.logistics.entity.PdaRequest;
import com.seeyuan.logistics.net.http.HttpAction;
import com.seeyuan.logistics.util.CommonUtils;

/**
 * 提交支付，进行支付
 * 
 * @author zhazhaobao
 * 
 */
public class SubmitPaymentHandler extends DataHandler {
	private Context mContext;
	private String server_url;
	private PdaRequest<PaymentDto> paymentDto;

	public SubmitPaymentHandler(Context context,
			PdaRequest<PaymentDto> paymentDto) {
		this.mContext = context;
		this.server_url = NetWork.SUBMIT_PAYMENT_ACTION;
		this.paymentDto = paymentDto;
	}

	public void startNetWork() {
		HttpAction httpAction = new HttpAction(HttpAction.REQUEST_TYPE_POST);
		httpAction.setUri(server_url);
		paymentDto.setUuId(CommonUtils.getUUID(mContext));
		paymentDto.setMemberType(CommonUtils.getMemberType(mContext));
		paymentDto.setOriginApp("ANDROID");
		String jsonString = new Gson().toJson(paymentDto);
		httpAction.addBodyParam("jsonString", jsonString);

		startNetwork(httpAction);
	}

	@Override
	protected void onNetReceiveOk(byte[] receiveBody) {
		sendResult(NetWork.SUBMIT_PAYMENT_OK, receiveBody);
	}

	@Override
	protected void onNetReceiveError(int errorCode) {
		sendResult(NetWork.SUBMIT_PAYMENT_ERROR, errorCode);
	}

	@Override
	protected void onNetReceiveTimeout(int errorCode) {
		sendResult(NetWork.SUBMIT_PAYMENT_ERROR, errorCode);
	}
}
