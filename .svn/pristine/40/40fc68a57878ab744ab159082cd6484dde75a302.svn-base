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
 * 发布货源
 * 
 * @author zhazhaobao
 * 
 */
public class PublishGoodsSourceHandler extends DataHandler {
	private Context mContext;
	private String server_url;
	private PdaRequest<GoodsDto> goodsSourceInfo;

	public PublishGoodsSourceHandler(Context context,
			PdaRequest<GoodsDto> goodsSourceInfo) {
		this.mContext = context;
		this.goodsSourceInfo = goodsSourceInfo;
		this.server_url = NetWork.PUBLISH_GOODS_SOURCE_ACTION;
	}

	public void startNetWork() {
		HttpAction httpAction = new HttpAction(HttpAction.REQUEST_TYPE_POST);
		httpAction.setUri(server_url);
		goodsSourceInfo.setUuId(CommonUtils.getUUID(mContext));
		goodsSourceInfo.setMemberType(CommonUtils.getMemberType(mContext));
		goodsSourceInfo.setOriginApp("ANDROID");
		String jsonString = new Gson().toJson(goodsSourceInfo);
		httpAction.addBodyParam("jsonString", jsonString);
		startNetwork(httpAction);
	}

	@Override
	protected void onNetReceiveOk(byte[] receiveBody) {
		sendResult(NetWork.PUBLISH_GOODS_SOURCE_OK, receiveBody);
	}

	@Override
	protected void onNetReceiveError(int errorCode) {
		sendResult(NetWork.PUBLISH_GOODS_SOURCE_ERROR, errorCode);
	}

	@Override
	protected void onNetReceiveTimeout(int errorCode) {
		sendResult(NetWork.PUBLISH_GOODS_SOURCE_ERROR, errorCode);
	}
}
