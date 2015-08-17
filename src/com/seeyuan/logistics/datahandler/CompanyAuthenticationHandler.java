package com.seeyuan.logistics.datahandler;

import android.content.Context;

import com.google.gson.Gson;
import com.seeyuan.logistics.application.NetWork;
import com.seeyuan.logistics.datacenter.DataHandler;
import com.seeyuan.logistics.entity.CompanyAuthDto;
import com.seeyuan.logistics.entity.PdaRequest;
import com.seeyuan.logistics.net.http.HttpAction;
import com.seeyuan.logistics.util.CommonUtils;

/**
 * 企业认证
 * 
 * @author zhazhaobao
 * 
 */
public class CompanyAuthenticationHandler extends DataHandler {
	private Context mContext;
	private String server_url;
	private PdaRequest<CompanyAuthDto> companyAuthDto;

	public CompanyAuthenticationHandler(Context context,
			PdaRequest<CompanyAuthDto> companyAuthDto) {
		this.mContext = context;
		this.server_url = NetWork.COMPANY_AUTHENTICATION_ACTION;
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
		sendResult(NetWork.COMPANY_AUTHENTICATION_OK, receiveBody);
	}

	@Override
	protected void onNetReceiveError(int errorCode) {
		sendResult(NetWork.COMPANY_AUTHENTICATION_ERROR, errorCode);
	}

	@Override
	protected void onNetReceiveTimeout(int errorCode) {
		sendResult(NetWork.COMPANY_AUTHENTICATION_ERROR, errorCode);
	}
}
