package com.seeyuan.logistics.datahandler;

import android.content.Context;

import com.seeyuan.logistics.application.NetWork;
import com.seeyuan.logistics.datacenter.DataHandler;
import com.seeyuan.logistics.entity.LoginInfo;
import com.seeyuan.logistics.net.http.HttpAction;

/**
 * 收藏货源
 * 
 * @author zhazhaobao
 * 
 */
public class CollectSourceHandler extends DataHandler {
	private Context mContext;
	private String server_url;
	/**
	 * 类型。1.货源 2.车源
	 */
	private String type;

	public CollectSourceHandler(Context context, String type) {
		this.mContext = context;
		this.server_url = NetWork.COLLECT_SOURCE_ACTION;
		this.type = type;
	}

	public void startNetWork() {
		HttpAction httpAction = new HttpAction(HttpAction.REQUEST_TYPE_POST);
		httpAction.setUri(server_url);
		httpAction.addBodyParam("type", type);

		startNetwork(httpAction);
	}

	@Override
	protected void onNetReceiveOk(byte[] receiveBody) {
		sendResult(NetWork.COLLECT_SOURCE_OK, receiveBody);
	}

	@Override
	protected void onNetReceiveError(int errorCode) {
		sendResult(NetWork.COLLECT_SOURCE_ERROR, errorCode);
	}

	@Override
	protected void onNetReceiveTimeout(int errorCode) {
		sendResult(NetWork.COLLECT_SOURCE_ERROR, errorCode);
	}
}
