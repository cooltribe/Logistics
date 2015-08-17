package com.seeyuan.logistics.datahandler;

import java.util.List;

import android.content.Context;

import com.google.gson.Gson;
import com.seeyuan.logistics.application.NetWork;
import com.seeyuan.logistics.datacenter.DataHandler;
import com.seeyuan.logistics.entity.DriverDto;
import com.seeyuan.logistics.entity.GoodsDto;
import com.seeyuan.logistics.entity.PdaRequest;
import com.seeyuan.logistics.net.http.HttpAction;
import com.seeyuan.logistics.util.CommonUtils;

public class DeleteGoodsHandler extends DataHandler {
	private Context mContext;
	private String server_url;
	private PdaRequest<List<GoodsDto>> goodsDto;

	public DeleteGoodsHandler(Context context,
			PdaRequest<List<GoodsDto>> goodsDto) {
		this.mContext = context;
		this.server_url = NetWork.DELETE_GOODS_ACTION;
		this.goodsDto = goodsDto;
	}

	public void startNetWork() {
		HttpAction httpAction = new HttpAction(HttpAction.REQUEST_TYPE_POST);
		httpAction.setUri(server_url);
		goodsDto.setUuId(CommonUtils.getUUID(mContext));
		goodsDto.setMemberType(CommonUtils.getMemberType(mContext));
		goodsDto.setOriginApp("ANDROID");
		httpAction.addBodyParam("jsonString", new Gson().toJson(goodsDto));

		startNetwork(httpAction);
	}

	@Override
	protected void onNetReceiveOk(byte[] receiveBody) {
		sendResult(NetWork.DELETE_GOODS_OK, receiveBody);
	}

	@Override
	protected void onNetReceiveError(int errorCode) {
		sendResult(NetWork.DELETE_GOODS_ERROR, errorCode);
	}

	@Override
	protected void onNetReceiveTimeout(int errorCode) {
		sendResult(NetWork.DELETE_GOODS_ERROR, errorCode);
	}

}
