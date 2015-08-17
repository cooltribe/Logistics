package com.seeyuan.logistics.datahandler;

import android.content.Context;

import com.google.gson.Gson;
import com.seeyuan.logistics.application.NetWork;
import com.seeyuan.logistics.datacenter.DataHandler;
import com.seeyuan.logistics.entity.GoodsDto;
import com.seeyuan.logistics.entity.PdaRequest;
import com.seeyuan.logistics.net.http.HttpAction;
import com.seeyuan.logistics.util.CommonUtils;

/**
 * 获取个人货源信息
 * 
 * @author zhazhaobao
 * 
 */
public class GetGoodsInfoHandler extends DataHandler {
	private Context mContext;
	private String server_url;
	private PdaRequest<GoodsDto> goodsDto;

	public GetGoodsInfoHandler(Context context, PdaRequest<GoodsDto> goodsDto) {
		this.mContext = context;
		this.server_url = NetWork.GET_GOODS_INFO_ACTION;
		this.goodsDto = goodsDto;
	}

	public void startNetWork() {
		HttpAction httpAction = new HttpAction(HttpAction.REQUEST_TYPE_POST);
		httpAction.setUri(server_url);
		goodsDto.setUuId(CommonUtils.getUUID(mContext));
		goodsDto.setMemberType(CommonUtils.getMemberType(mContext));
		goodsDto.setOriginApp("ANDROID");
		String jsonString = new Gson().toJson(goodsDto);
		httpAction.addBodyParam("jsonString", jsonString);

		startNetwork(httpAction);
	}

	@Override
	protected void onNetReceiveOk(byte[] receiveBody) {
		sendResult(NetWork.GET_GOODS_INFO_OK, receiveBody);
	}

	@Override
	protected void onNetReceiveError(int errorCode) {
		sendResult(NetWork.GET_GOODS_INFO_ERROR, errorCode);
	}

	@Override
	protected void onNetReceiveTimeout(int errorCode) {
		sendResult(NetWork.GET_GOODS_INFO_ERROR, errorCode);
	}
}
