package com.seeyuan.logistics.datahandler;

import android.content.Context;

import com.google.gson.Gson;
import com.seeyuan.logistics.application.NetWork;
import com.seeyuan.logistics.datacenter.DataHandler;
import com.seeyuan.logistics.entity.AccountInDto;
import com.seeyuan.logistics.entity.PdaRequest;
import com.seeyuan.logistics.net.http.HttpAction;
import com.seeyuan.logistics.util.CommonUtils;

/**
 * 会员充值
 * @author zhazhaobao
 *
 */
public class RechargeHandler extends DataHandler{
	private Context mContext;
	private String server_url;
	private PdaRequest<AccountInDto> accountInfo;

	public RechargeHandler(Context context, PdaRequest<AccountInDto> accountInfo) {
		this.mContext = context;
		this.server_url = NetWork.RECHARGE_ACTION;
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
		sendResult(NetWork.RECHARGE_OK, receiveBody);
	}

	@Override
	protected void onNetReceiveError(int errorCode) {
		sendResult(NetWork.RECHARGE_ERROR, errorCode);
	}

	@Override
	protected void onNetReceiveTimeout(int errorCode) {
		sendResult(NetWork.RECHARGE_ERROR, errorCode);
	}
}
