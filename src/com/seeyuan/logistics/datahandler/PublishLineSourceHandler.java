package com.seeyuan.logistics.datahandler;

import android.content.Context;

import com.seeyuan.logistics.application.NetWork;
import com.seeyuan.logistics.datacenter.DataHandler;
import com.seeyuan.logistics.entity.LineSourceInfo;
import com.seeyuan.logistics.net.http.HttpAction;

/**
 * 发布专线
 * 
 * @author zhazhaobao
 * 
 */
public class PublishLineSourceHandler extends DataHandler {
	private Context mContext;
	private String server_url;
	private LineSourceInfo lineSourceInfo;

	public PublishLineSourceHandler(Context context,
			LineSourceInfo lineSourceInfo) {
		this.mContext = context;
		this.lineSourceInfo = lineSourceInfo;
		this.server_url = NetWork.PUBLISH_LINE_SOURCE_ACTION;
	}

	public void startNetWork() {
		HttpAction httpAction = new HttpAction(HttpAction.REQUEST_TYPE_POST);
		httpAction.setUri(server_url);
		httpAction.addBodyParam("companyName", lineSourceInfo.getCompanyName());
		httpAction.addBodyParam("responsiblePerson",
				lineSourceInfo.getResponsiblePerson());
		httpAction.addBodyParam("responsiblePhone",
				lineSourceInfo.getResponsiblePhone());
		httpAction.addBodyParam("companyPlace",
				lineSourceInfo.getCompanyPlace());
		httpAction.addBodyParam("targetFrom", lineSourceInfo.getTargetFrom());
		httpAction.addBodyParam("targetFromUser",
				lineSourceInfo.getTargetFromUser());
		httpAction.addBodyParam("targetFromUserPhone",
				lineSourceInfo.getTargetFromUserPhone());
		httpAction.addBodyParam("targetTo", lineSourceInfo.getTargetTo());
		httpAction.addBodyParam("targetToUser",
				lineSourceInfo.getTargetToUser());
		httpAction.addBodyParam("targetToUserPhone",
				lineSourceInfo.getTargetToUserPhone());
		httpAction.addBodyParam("targetToRange",
				lineSourceInfo.getTargetToRange());
		httpAction.addBodyParam("insurance", lineSourceInfo.isInsurance() ? "1"
				: "0");
		httpAction.addBodyParam("taxReceipt",
				lineSourceInfo.isTaxReceipt() ? "1" : "0");
		httpAction
				.addBodyParam("agency", lineSourceInfo.isAgency() ? "1" : "0");
		httpAction.addBodyParam("heavyGoodsPrice",
				lineSourceInfo.getHeavyGoodsPrice());
		httpAction.addBodyParam("lightGoodsPrice",
				lineSourceInfo.getLightGoodsPrice());
		httpAction.addBodyParam("heavyCargoPrice",
				lineSourceInfo.getHeavyCargoPrice());
		httpAction.addBodyParam("startingPrice",
				lineSourceInfo.getStartingPrice());
		httpAction.addBodyParam("transportType",
				lineSourceInfo.getTransportType());
		httpAction.addBodyParam("carType", lineSourceInfo.getCarType());
		httpAction.addBodyParam("carLength", lineSourceInfo.getCarLength());
		httpAction.addBodyParam("departTime", lineSourceInfo.getDepartTime());
		httpAction.addBodyParam("vaildTime", lineSourceInfo.getVaildTime());

		startNetwork(httpAction);
	}

	@Override
	protected void onNetReceiveOk(byte[] receiveBody) {
		sendResult(NetWork.PUBLISH_LINE_SOURCE_OK, receiveBody);
	}

	@Override
	protected void onNetReceiveError(int errorCode) {
		sendResult(NetWork.PUBLISH_LINE_SOURCE_ERROR, errorCode);
	}

	@Override
	protected void onNetReceiveTimeout(int errorCode) {
		sendResult(NetWork.PUBLISH_LINE_SOURCE_OK, errorCode);
	}
}
