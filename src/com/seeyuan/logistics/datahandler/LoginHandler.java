package com.seeyuan.logistics.datahandler;

import org.apache.http.impl.client.DefaultHttpClient;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.seeyuan.logistics.application.NetWork;
import com.seeyuan.logistics.datacenter.DataHandler;
import com.seeyuan.logistics.entity.MemberDto;
import com.seeyuan.logistics.entity.PdaRequest;
import com.seeyuan.logistics.net.http.HttpAction;
import com.seeyuan.logistics.xmlparser.HttpUtil;

public class LoginHandler extends DataHandler {
	private Context mContext;
	private String server_url;
	private PdaRequest<MemberDto> loginInfo;

	public LoginHandler(Context context, PdaRequest<MemberDto> loginInfo) {
		this.mContext = context;
		this.server_url = NetWork.lOGIN_ACTION;
		this.loginInfo = loginInfo;
	}

	public void startNetWork() {
		HttpAction httpAction = new HttpAction(HttpAction.REQUEST_TYPE_POST);
		httpAction.setUri(server_url);
//		httpAction.setUri("http://www.51egoods.com/apps/checkPdaUserLogin.action");
		String jsonString = new Gson().toJson(loginInfo);
		httpAction.addBodyParam("jsonString", jsonString);
//		httpAction.execute(new DefaultHttpClient());
		Log.i("httpAction.getReceiveBody().toString()", httpAction.toString());
		startNetwork(httpAction);
	}

	@Override
	protected void onNetReceiveOk(byte[] receiveBody) {
		sendResult(NetWork.LOGIN_OK, receiveBody);
	}

	@Override
	protected void onNetReceiveError(int errorCode) {
		sendResult(NetWork.LOGIN_ERROR, errorCode);
	}

	@Override
	protected void onNetReceiveTimeout(int errorCode) {
		sendResult(NetWork.LOGIN_ERROR, errorCode);
	}
}
