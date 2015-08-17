package com.seeyuan.logistics.datahandler;

import android.content.Context;

import com.google.gson.Gson;
import com.seeyuan.logistics.application.NetWork;
import com.seeyuan.logistics.datacenter.DataHandler;
import com.seeyuan.logistics.entity.PdaRequest;
import com.seeyuan.logistics.entity.SettleAccountsDetailDto;
import com.seeyuan.logistics.net.http.HttpAction;
import com.seeyuan.logistics.util.CommonUtils;

/**
 * 获取交易管理日志
 * 
 * @author zhazhaobao
 * 
 */
public class GetDealManagerHandler extends DataHandler {
	private Context mContext;
	private String server_url;
	private PdaRequest<String> dealManagerDto;

	public GetDealManagerHandler(Context context,
			PdaRequest<String> dealManagerDto) {
		this.mContext = context;
		this.server_url = NetWork.GET_DEAL_MANAGER_ACTION;
		this.dealManagerDto = dealManagerDto;
	}

	public void startNetWork() {
		HttpAction httpAction = new HttpAction(HttpAction.REQUEST_TYPE_POST);
		httpAction.setUri(server_url);
		dealManagerDto.setUuId(CommonUtils.getUUID(mContext));
		dealManagerDto.setMemberType(CommonUtils.getMemberType(mContext));
		dealManagerDto.setOriginApp("ANDROID");
		String jsonString = new Gson().toJson(dealManagerDto);
		httpAction.addBodyParam("jsonString", jsonString);

		startNetwork(httpAction);
	}

	@Override
	protected void onNetReceiveOk(byte[] receiveBody) {
		sendResult(NetWork.GET_DEAL_MANAGER_OK, receiveBody);
	}

	@Override
	protected void onNetReceiveError(int errorCode) {
		sendResult(NetWork.GET_DEAL_MANAGER_ERROR, errorCode);
	}

	@Override
	protected void onNetReceiveTimeout(int errorCode) {
		sendResult(NetWork.GET_DEAL_MANAGER_ERROR, errorCode);
	}
}
