package com.seeyuan.logistics.datahandler;

import android.content.Context;

import com.google.gson.Gson;
import com.seeyuan.logistics.application.NetWork;
import com.seeyuan.logistics.datacenter.DataHandler;
import com.seeyuan.logistics.entity.PdaRequest;
import com.seeyuan.logistics.entity.SmsInfoDto;
import com.seeyuan.logistics.net.http.HttpAction;
import com.seeyuan.logistics.util.CommonUtils;

/**
 * 获取短信
 * 
 * @author zhazhaobao
 * 
 */
public class SendSMSHanlder extends DataHandler {
	private Context mContext;
	private String server_url;
	private PdaRequest<SmsInfoDto> smsDto;

	public SendSMSHanlder(Context context, PdaRequest<SmsInfoDto> smsDto) {
		this.mContext = context;
		this.server_url = NetWork.SEND_SMS_ACTION;
		this.smsDto = smsDto;
	}

	public void startNetWork() {
		HttpAction httpAction = new HttpAction(HttpAction.REQUEST_TYPE_POST);
		httpAction.setUri(server_url);
		smsDto.setOriginApp("ANDROID");
		httpAction.addBodyParam("jsonString", new Gson().toJson(smsDto));

		startNetwork(httpAction);
	}

	@Override
	protected void onNetReceiveOk(byte[] receiveBody) {
		sendResult(NetWork.SEND_SMS_OK, receiveBody);
	}

	@Override
	protected void onNetReceiveError(int errorCode) {
		sendResult(NetWork.SEND_SMS_ERROR, errorCode);
	}

	@Override
	protected void onNetReceiveTimeout(int errorCode) {
		sendResult(NetWork.SEND_SMS_ERROR, errorCode);
	}
}
