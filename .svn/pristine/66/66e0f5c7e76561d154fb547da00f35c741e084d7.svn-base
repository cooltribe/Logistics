package com.seeyuan.logistics.datahandler;

import android.content.Context;

import com.seeyuan.logistics.application.NetWork;
import com.seeyuan.logistics.datacenter.DataHandler;
import com.seeyuan.logistics.entity.EvaluateInfo;
import com.seeyuan.logistics.entity.LoginInfo;
import com.seeyuan.logistics.net.http.HttpAction;

/**
 * 提交评价
 * 
 * @author zhazhaobao
 * 
 */
public class SubmitEvaluateHandler extends DataHandler {
	private Context mContext;
	private String server_url;
	private EvaluateInfo evaluateInfo;

	public SubmitEvaluateHandler(Context context, EvaluateInfo evaluateInfo) {
		this.mContext = context;
		this.server_url = NetWork.SUBMIT_EVALUATE_ACTION;
		this.evaluateInfo = evaluateInfo;
	}

	public void startNetWork() {
		HttpAction httpAction = new HttpAction(HttpAction.REQUEST_TYPE_POST);
		httpAction.setUri(server_url);
		httpAction.addBodyParam("content", evaluateInfo.getContent());
		httpAction
				.addBodyParam("isPraise", evaluateInfo.isPraise() ? "1" : "0");
		httpAction.addBodyParam("star", evaluateInfo.getStar());

		startNetwork(httpAction);
	}

	@Override
	protected void onNetReceiveOk(byte[] receiveBody) {
		sendResult(NetWork.SUBMIT_EVALUATE_OK, receiveBody);
	}

	@Override
	protected void onNetReceiveError(int errorCode) {
		sendResult(NetWork.SUBMIT_EVALUATE_ERROR, errorCode);
	}

	@Override
	protected void onNetReceiveTimeout(int errorCode) {
		sendResult(NetWork.SUBMIT_EVALUATE_ERROR, errorCode);
	}
}
