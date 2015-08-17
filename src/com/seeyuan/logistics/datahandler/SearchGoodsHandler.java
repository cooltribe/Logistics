package com.seeyuan.logistics.datahandler;

import android.content.Context;

import com.google.gson.Gson;
import com.seeyuan.logistics.application.NetWork;
import com.seeyuan.logistics.datacenter.DataHandler;
import com.seeyuan.logistics.entity.GoodsDto;
import com.seeyuan.logistics.entity.PdaRequest;
import com.seeyuan.logistics.net.http.HttpAction;
import com.seeyuan.logistics.util.CommonUtils;

public class SearchGoodsHandler extends DataHandler {
	private Context mContext;
	private String server_url;
	private PdaRequest<GoodsDto> goodsInfo;

	public SearchGoodsHandler(Context context, PdaRequest<GoodsDto> goodsInfo) {
		this.mContext = context;
		this.server_url = NetWork.SEARCH_GOODS_ACTION;
		this.goodsInfo = goodsInfo;
	}

	public void startNetWork() {
		HttpAction httpAction = new HttpAction(HttpAction.REQUEST_TYPE_POST);
		httpAction.setUri(server_url);
		goodsInfo.setUuId(CommonUtils.getUUID(mContext));
		goodsInfo.setMemberType(CommonUtils.getMemberType(mContext));
		goodsInfo.setOriginApp("ANDROID");
		String jsonString = new Gson().toJson(goodsInfo);
		httpAction.addBodyParam("jsonString", jsonString);
		startNetwork(httpAction);
	}

	@Override
	protected void onNetReceiveOk(byte[] receiveBody) {
		sendResult(NetWork.SEACH_GOODS_OK, receiveBody);
	}

	@Override
	protected void onNetReceiveError(int errorCode) {
		sendResult(NetWork.SEACH_GOODS_ERROR, errorCode);
	}

	@Override
	protected void onNetReceiveTimeout(int errorCode) {
		sendResult(NetWork.SEACH_GOODS_ERROR, errorCode);
	}
}
