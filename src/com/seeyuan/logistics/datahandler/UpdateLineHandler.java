package com.seeyuan.logistics.datahandler;

import android.content.Context;

import com.google.gson.Gson;
import com.seeyuan.logistics.application.NetWork;
import com.seeyuan.logistics.datacenter.DataHandler;
import com.seeyuan.logistics.entity.DriverDto;
import com.seeyuan.logistics.entity.MemberDto;
import com.seeyuan.logistics.entity.PdaRequest;
import com.seeyuan.logistics.entity.RouteDto;
import com.seeyuan.logistics.net.http.HttpAction;
import com.seeyuan.logistics.util.CommonUtils;

/**
 * 修改线路信息
 * 
 * @author zhazhaobao
 * 
 */
public class UpdateLineHandler extends DataHandler {
	private Context mContext;
	private String server_url;
	private PdaRequest<RouteDto> routeDto;

	public UpdateLineHandler(Context context, PdaRequest<RouteDto> routeDto) {
		this.mContext = context;
		this.server_url = NetWork.UPDATA_LINE_ACTION;
		this.routeDto = routeDto;
	}

	public void startNetWork() {
		HttpAction httpAction = new HttpAction(HttpAction.REQUEST_TYPE_POST);
		httpAction.setUri(server_url);
		routeDto.setUuId(CommonUtils.getUUID(mContext));
		routeDto.setMemberType(CommonUtils.getMemberType(mContext));
		routeDto.setOriginApp("ANDROID");
		httpAction.addBodyParam("jsonString", new Gson().toJson(routeDto));

		startNetwork(httpAction);
	}

	@Override
	protected void onNetReceiveOk(byte[] receiveBody) {
		sendResult(NetWork.UPDATA_LINE_OK, receiveBody);
	}

	@Override
	protected void onNetReceiveError(int errorCode) {
		sendResult(NetWork.UPDATA_LINE_ERROR, errorCode);
	}

	@Override
	protected void onNetReceiveTimeout(int errorCode) {
		sendResult(NetWork.UPDATA_LINE_ERROR, errorCode);
	}
}
