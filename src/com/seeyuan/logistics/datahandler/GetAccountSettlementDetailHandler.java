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
 * 获取结算单明细
 * 
 * @author zhazhaobao
 * 
 */
public class GetAccountSettlementDetailHandler extends DataHandler {
	private Context mContext;
	private String server_url;
	private PdaRequest<SettleAccountsDetailDto> settleAccountDetailDto;

	public GetAccountSettlementDetailHandler(Context context,
			PdaRequest<SettleAccountsDetailDto> settleAccountDetailDto) {
		this.mContext = context;
		this.server_url = NetWork.GET_ACCOUNT_SETTLEMENT_DETAIL_ACTION;
		this.settleAccountDetailDto = settleAccountDetailDto;
	}

	public void startNetWork() {
		HttpAction httpAction = new HttpAction(HttpAction.REQUEST_TYPE_POST);
		httpAction.setUri(server_url);
		settleAccountDetailDto.setUuId(CommonUtils.getUUID(mContext));
		settleAccountDetailDto.setMemberType(CommonUtils
				.getMemberType(mContext));
		settleAccountDetailDto.setOriginApp("ANDROID");
		String jsonString = new Gson().toJson(settleAccountDetailDto);
		httpAction.addBodyParam("jsonString", jsonString);

		startNetwork(httpAction);
	}

	@Override
	protected void onNetReceiveOk(byte[] receiveBody) {
		sendResult(NetWork.GET_ACCOUNT_SETTLEMENT_DETAIL_OK, receiveBody);
	}

	@Override
	protected void onNetReceiveError(int errorCode) {
		sendResult(NetWork.GET_ACCOUNT_SETTLEMENT_DETAIL_ERROR, errorCode);
	}

	@Override
	protected void onNetReceiveTimeout(int errorCode) {
		sendResult(NetWork.GET_ACCOUNT_SETTLEMENT_DETAIL_ERROR, errorCode);
	}
}
