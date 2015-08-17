package com.seeyuan.logistics.datahandler;

import android.content.Context;

import com.google.gson.Gson;
import com.seeyuan.logistics.application.NetWork;
import com.seeyuan.logistics.datacenter.DataHandler;
import com.seeyuan.logistics.entity.DriverDto;
import com.seeyuan.logistics.entity.MemberDto;
import com.seeyuan.logistics.entity.PdaRequest;
import com.seeyuan.logistics.net.http.HttpAction;
import com.seeyuan.logistics.util.CommonUtils;

/**
 * 修改用户信息
 * 
 * @author zhazhaobao
 * 
 */
public class UpdateUserInfoHandler extends DataHandler {
	private Context mContext;
	private String server_url;
	private PdaRequest<MemberDto> userInfo;

	public UpdateUserInfoHandler(Context context, PdaRequest<MemberDto> userInfo) {
		this.mContext = context;
		this.server_url = NetWork.UPDATE_USERINFO_ACTION;
		this.userInfo = userInfo;
	}

	public void startNetWork() {
		HttpAction httpAction = new HttpAction(HttpAction.REQUEST_TYPE_POST);
		httpAction.setUri(server_url);
		userInfo.setUuId(CommonUtils.getUUID(mContext));
		userInfo.setMemberType(CommonUtils.getMemberType(mContext));
		userInfo.setOriginApp("ANDROID");
		String jsonString = new Gson().toJson(userInfo);
		httpAction.addBodyParam("jsonString", jsonString);

		startNetwork(httpAction);
	}

	@Override
	protected void onNetReceiveOk(byte[] receiveBody) {
		sendResult(NetWork.UPDATE_USERINFO_OK, receiveBody);
	}

	@Override
	protected void onNetReceiveError(int errorCode) {
		sendResult(NetWork.UPDATE_USERINFO_ERROR, errorCode);
	}

	@Override
	protected void onNetReceiveTimeout(int errorCode) {
		sendResult(NetWork.UPDATE_USERINFO_ERROR, errorCode);
	}
}
