package com.seeyuan.logistics.datahandler;

import android.content.Context;

import com.google.gson.Gson;
import com.seeyuan.logistics.application.NetWork;
import com.seeyuan.logistics.datacenter.DataHandler;
import com.seeyuan.logistics.entity.MemberDto;
import com.seeyuan.logistics.entity.PdaRequest;
import com.seeyuan.logistics.entity.RegisterInfo;
import com.seeyuan.logistics.net.http.HttpAction;

/**
 * 提交用户验证码
 * 
 * @author zhazhaobao
 * 
 */
public class SubmitRegisterAuthcodeHandler extends DataHandler {
	private Context mContext;
	private String server_url;
	private PdaRequest<MemberDto> registerInfo;

	public SubmitRegisterAuthcodeHandler(Context context,
			PdaRequest<MemberDto> registerInfo) {
		this.mContext = context;
		this.server_url = NetWork.SUBMIT_REGISTER_AUTHCODE_ACTION;
		this.registerInfo = registerInfo;
	}

	public void startNetWork() {
		HttpAction httpAction = new HttpAction(HttpAction.REQUEST_TYPE_POST);
		httpAction.setUri(server_url);
		String jsonString = new Gson().toJson(registerInfo);
		httpAction.addBodyParam("jsonString", jsonString);
		startNetwork(httpAction);
	}

	@Override
	protected void onNetReceiveOk(byte[] receiveBody) {
		// String result = new String(receiveBody);
		sendResult(NetWork.SUBMIT_REGISTER_AUTHCODE_OK, receiveBody);
	}

	@Override
	protected void onNetReceiveError(int errorCode) {
		sendResult(NetWork.SUBMIT_REGISTER_AUTHCODE_ERROR, errorCode);
	}

	@Override
	protected void onNetReceiveTimeout(int errorCode) {
		sendResult(NetWork.SUBMIT_REGISTER_AUTHCODE_ERROR, errorCode);
	}
}
