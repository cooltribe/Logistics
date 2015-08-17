package com.seeyuan.logistics.datahandler;

import android.content.Context;

import com.google.gson.Gson;
import com.seeyuan.logistics.application.NetWork;
import com.seeyuan.logistics.datacenter.DataHandler;
import com.seeyuan.logistics.entity.MemAccountDto;
import com.seeyuan.logistics.entity.PdaRequest;
import com.seeyuan.logistics.net.http.HttpAction;
import com.seeyuan.logistics.util.CommonUtils;

/**
 * 获取充值账号信息
 * 
 * @author zhazhaobao
 * 
 */
public class GetRechargeAccountHandler extends DataHandler {
	private Context mContext;
	private String server_url;
	private PdaRequest<String> menAccountDto;

	public GetRechargeAccountHandler(Context context,
			PdaRequest<String> memAccountDto) {
		this.mContext = context;
		this.server_url = NetWork.GET_RECHARGE_ACCOUNT_ACTION;
		this.menAccountDto = memAccountDto;
	}

	public void startNetWork() {
		HttpAction httpAction = new HttpAction(HttpAction.REQUEST_TYPE_POST);
		httpAction.setUri(server_url);
		menAccountDto.setUuId(CommonUtils.getUUID(mContext));
		menAccountDto.setMemberType(CommonUtils.getMemberType(mContext));
		menAccountDto.setOriginApp("ANDROID");
		httpAction.addBodyParam("jsonString", new Gson().toJson(menAccountDto));

		startNetwork(httpAction);
	}

	@Override
	protected void onNetReceiveOk(byte[] receiveBody) {
		sendResult(NetWork.GET_RECHARGE_ACCOUNT_OK, receiveBody);
	}

	@Override
	protected void onNetReceiveError(int errorCode) {
		sendResult(NetWork.GET_RECHARGE_ACCOUNT_ERROR, errorCode);
	}

	@Override
	protected void onNetReceiveTimeout(int errorCode) {
		sendResult(NetWork.GET_RECHARGE_ACCOUNT_ERROR, errorCode);
	}
}
