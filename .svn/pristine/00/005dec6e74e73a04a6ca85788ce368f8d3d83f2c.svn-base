package com.seeyuan.logistics.datahandler;

import android.content.Context;

import com.google.gson.Gson;
import com.seeyuan.logistics.application.NetWork;
import com.seeyuan.logistics.datacenter.DataHandler;
import com.seeyuan.logistics.entity.CarsDto;
import com.seeyuan.logistics.entity.MemberAuthDto;
import com.seeyuan.logistics.entity.PdaRequest;
import com.seeyuan.logistics.net.http.HttpAction;
import com.seeyuan.logistics.util.CommonUtils;

/**
 * 获取实名认证信息
 * 
 * @author zhazhaobao
 * 
 */
public class GetCertificationInfoHandler extends DataHandler {
	private Context mContext;
	private String server_url;
	private PdaRequest<String> menberAuthDto;

	public GetCertificationInfoHandler(Context context,
			PdaRequest<String> menberAuthDto) {
		this.mContext = context;
		this.server_url = NetWork.GET_CERTIFICATION_INFO_ACTION;
		this.menberAuthDto = menberAuthDto;
	}

	public void startNetWork() {
		HttpAction httpAction = new HttpAction(HttpAction.REQUEST_TYPE_POST);
		httpAction.setUri(server_url);
		menberAuthDto.setUuId(CommonUtils.getUUID(mContext));
		menberAuthDto.setMemberType(CommonUtils.getMemberType(mContext));
		menberAuthDto.setOriginApp("ANDROID");
		httpAction.addBodyParam("jsonString", new Gson().toJson(menberAuthDto));

		startNetwork(httpAction);
	}

	@Override
	protected void onNetReceiveOk(byte[] receiveBody) {
		sendResult(NetWork.GET_CERTIFICATION_INFO_OK, receiveBody);
	}

	@Override
	protected void onNetReceiveError(int errorCode) {
		sendResult(NetWork.GET_CERTIFICATION_INFO_ERROR, errorCode);
	}

	@Override
	protected void onNetReceiveTimeout(int errorCode) {
		sendResult(NetWork.GET_CERTIFICATION_INFO_ERROR, errorCode);
	}
}
