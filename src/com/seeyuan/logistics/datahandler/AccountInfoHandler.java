package com.seeyuan.logistics.datahandler;

import android.content.Context;

import com.google.gson.Gson;
import com.seeyuan.logistics.application.NetWork;
import com.seeyuan.logistics.datacenter.DataHandler;
import com.seeyuan.logistics.entity.PdaRequest;
import com.seeyuan.logistics.net.http.HttpAction;
import com.seeyuan.logistics.util.CommonUtils;

/**
 * 充值账号信息
 * @author zhazhaobao
 *
 */
public class AccountInfoHandler extends DataHandler{
	private Context mContext;
	private String server_url;
	private PdaRequest<String> accountInfo;

	public AccountInfoHandler(Context context, PdaRequest<String> accountInfo) {
		this.mContext = context;
		this.server_url = NetWork.GET_ACCOUNT_INFO_ACTION;
		this.accountInfo = accountInfo;
	}

	public void startNetWork() {
		HttpAction httpAction = new HttpAction(HttpAction.REQUEST_TYPE_POST);
		httpAction.setUri(server_url);
		accountInfo.setUuId(CommonUtils.getUUID(mContext));
		accountInfo.setMemberType(CommonUtils.getMemberType(mContext));
		accountInfo.setOriginApp("ANDROID");
		httpAction.addBodyParam("jsonString", new Gson().toJson(accountInfo));

		startNetwork(httpAction);
	}

	@Override
	protected void onNetReceiveOk(byte[] receiveBody) {
		sendResult(NetWork.GET_ACCOUNT_INFO_OK, receiveBody);
	}

	@Override
	protected void onNetReceiveError(int errorCode) {
		sendResult(NetWork.GET_ACCOUNT_INFO_ERROR, errorCode);
	}

	@Override
	protected void onNetReceiveTimeout(int errorCode) {
		sendResult(NetWork.GET_ACCOUNT_INFO_ERROR, errorCode);
	}
}
