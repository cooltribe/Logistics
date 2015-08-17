package com.seeyuan.logistics.datahandler;

import android.content.Context;

import com.google.gson.Gson;
import com.seeyuan.logistics.application.ApplicationPool;
import com.seeyuan.logistics.application.NetWork;
import com.seeyuan.logistics.datacenter.DataHandler;
import com.seeyuan.logistics.entity.MemberDto;
import com.seeyuan.logistics.entity.PdaRequest;
import com.seeyuan.logistics.entity.PersonalPositionInfo;
import com.seeyuan.logistics.net.http.HttpAction;
import com.seeyuan.logistics.util.CommonUtils;

/**
 * 提交个人位置信息
 * 
 * @author Administrator
 * 
 */
public class SubmitPersonalPositionHandler extends DataHandler {
	private Context mContext;
	private String server_url;
	private PdaRequest<MemberDto> positionInfo;
	
	public SubmitPersonalPositionHandler(Context context,
			PdaRequest<MemberDto> positionInfo) {
		this.mContext = context;
		this.server_url = NetWork.SUBMIT_PERSONAL_POSITION_ACTION;
		this.positionInfo = positionInfo;
	}

	public void startNetWork() {
		HttpAction httpAction = new HttpAction(HttpAction.REQUEST_TYPE_POST);
		httpAction.setUri(server_url);
		positionInfo.setUuId(CommonUtils.getUUID(mContext));
		positionInfo.setMemberType(CommonUtils.getMemberType(mContext));
		positionInfo.setOriginApp("ANDROID");
		String jsonString = new Gson().toJson(positionInfo);
		httpAction.addBodyParam("jsonString", jsonString);
		startNetwork(httpAction);
	}

	@Override
	protected void onNetReceiveOk(byte[] receiveBody) {
		// String result = new String(receiveBody);
		sendResult(NetWork.SUBMIT_PERSONAL_POSITION_OK, receiveBody);
	}

	@Override
	protected void onNetReceiveError(int errorCode) {
		sendResult(NetWork.SUBMIT_PERSONAL_POSITION_ERROR, errorCode);
	}

	@Override
	protected void onNetReceiveTimeout(int errorCode) {
		sendResult(NetWork.SUBMIT_PERSONAL_POSITION_ERROR, errorCode);
	}
}
