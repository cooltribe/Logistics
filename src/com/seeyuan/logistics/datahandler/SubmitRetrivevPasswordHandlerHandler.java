package com.seeyuan.logistics.datahandler;

import android.content.Context;

import com.google.gson.Gson;
import com.seeyuan.logistics.application.NetWork;
import com.seeyuan.logistics.datacenter.DataHandler;
import com.seeyuan.logistics.entity.CarsDto;
import com.seeyuan.logistics.entity.MemberDto;
import com.seeyuan.logistics.entity.PdaRequest;
import com.seeyuan.logistics.net.http.HttpAction;

/**
 * 找回密码，提交密码
 * 
 * @author zhazhaobao
 * 
 */
public class SubmitRetrivevPasswordHandlerHandler extends DataHandler {
	private Context mContext;
	private String server_url;
	private PdaRequest<MemberDto> accountInfo;

	public SubmitRetrivevPasswordHandlerHandler(Context context,
			PdaRequest<MemberDto> accountInfo) {
		this.mContext = context;
		this.server_url = NetWork.SUBMIT_RETRIVEV_PASSWORD_ACTION;
		this.accountInfo = accountInfo;
	}

	public void startNetWork() {
		HttpAction httpAction = new HttpAction(HttpAction.REQUEST_TYPE_POST);
		httpAction.setUri(server_url);
		accountInfo.setOriginApp("ANDROID");
		httpAction.addBodyParam("jsonString", new Gson().toJson(accountInfo));

		startNetwork(httpAction);
	}

	@Override
	protected void onNetReceiveOk(byte[] receiveBody) {
		sendResult(NetWork.SUBMIT_RETRIVEV_PASSWORD_OK, receiveBody);
	}

	@Override
	protected void onNetReceiveError(int errorCode) {
		sendResult(NetWork.SUBMIT_RETRIVEV_PASSWORD_OK, errorCode);
	}

	@Override
	protected void onNetReceiveTimeout(int errorCode) {
		sendResult(NetWork.SUBMIT_RETRIVEV_PASSWORD_OK, errorCode);
	}
}
