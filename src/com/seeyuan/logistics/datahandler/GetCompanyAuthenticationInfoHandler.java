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
 * 获取企业认证信息
 * 
 * @author zhazhaobao
 * 
 */
public class GetCompanyAuthenticationInfoHandler extends DataHandler {
	private Context mContext;
	private String server_url;
	private PdaRequest<String> companyAuthDto;

	public GetCompanyAuthenticationInfoHandler(Context context,
			PdaRequest<String> companyAuthDto) {
		this.mContext = context;
		this.server_url = NetWork.GET_COMPANY_AUTHENTICATION_INFO_ACTION;
		this.companyAuthDto = companyAuthDto;
	}

	public void startNetWork() {
		HttpAction httpAction = new HttpAction(HttpAction.REQUEST_TYPE_POST);
		httpAction.setUri(server_url);
		companyAuthDto.setUuId(CommonUtils.getUUID(mContext));
		companyAuthDto.setMemberType(CommonUtils.getMemberType(mContext));
		companyAuthDto.setOriginApp("ANDROID");
		httpAction.addBodyParam("jsonString", new Gson().toJson(companyAuthDto));

		startNetwork(httpAction);
	}

	@Override
	protected void onNetReceiveOk(byte[] receiveBody) {
		sendResult(NetWork.GET_COMPANY_AUTHENTICATION_INFO_OK, receiveBody);
	}

	@Override
	protected void onNetReceiveError(int errorCode) {
		sendResult(NetWork.GET_COMPANY_AUTHENTICATION_INFO_ERROR, errorCode);
	}

	@Override
	protected void onNetReceiveTimeout(int errorCode) {
		sendResult(NetWork.GET_COMPANY_AUTHENTICATION_INFO_ERROR, errorCode);
	}
}
