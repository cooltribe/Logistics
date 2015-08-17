package com.seeyuan.logistics.datahandler;

import android.content.Context;

import com.google.gson.Gson;
import com.seeyuan.logistics.application.NetWork;
import com.seeyuan.logistics.datacenter.DataHandler;
import com.seeyuan.logistics.entity.CarsDto;
import com.seeyuan.logistics.entity.DriverDto;
import com.seeyuan.logistics.entity.PdaRequest;
import com.seeyuan.logistics.net.http.HttpAction;
import com.seeyuan.logistics.util.CommonUtils;

/**
 * 搜索司机
 * 
 * @author zhazhaobao
 * 
 */
public class SearchCarManagerHandler extends DataHandler {
	private Context mContext;
	private String server_url;
	private PdaRequest<CarsDto> carInfo;

	public SearchCarManagerHandler(Context context, PdaRequest<CarsDto> carInfo) {
		this.mContext = context;
		this.server_url = NetWork.SEARCH_CAR_ACTION;
		this.carInfo = carInfo;
	}

	public void startNetWork() {
		HttpAction httpAction = new HttpAction(HttpAction.REQUEST_TYPE_POST);
		httpAction.setUri(server_url);
		carInfo.setUuId(CommonUtils.getUUID(mContext));
		carInfo.setMemberType(CommonUtils.getMemberType(mContext));
		String jsonString = new Gson().toJson(carInfo);
		httpAction.addBodyParam("jsonString", jsonString);

		startNetwork(httpAction);
	}

	@Override
	protected void onNetReceiveOk(byte[] receiveBody) {
		sendResult(NetWork.SEARCH_CAR_OK, receiveBody);
	}

	@Override
	protected void onNetReceiveError(int errorCode) {
		sendResult(NetWork.SEARCH_CAR_ERROR, errorCode);
	}

	@Override
	protected void onNetReceiveTimeout(int errorCode) {
		sendResult(NetWork.SEARCH_CAR_ERROR, errorCode);
	}
}
