package com.seeyuan.logistics.activity;

import org.apache.http.Header;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.seeyuan.logistics.entity.CarsDto;
import com.seeyuan.logistics.entity.PdaPagination;
import com.seeyuan.logistics.entity.PdaRequest;
import com.seeyuan.logistics.util.CommonUtils;
import com.seeyuan.logistics.xmlparser.HttpUtil;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;

public class SelectCarActivity1 extends Activity {
	private  Context context ;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		context = getApplicationContext();
	}
	private void getCarList(){
		CarsDto carInfo = new CarsDto();
		PdaRequest<CarsDto> request = new PdaRequest<CarsDto>();
		request.setData(carInfo);
		PdaPagination pagination = new PdaPagination();
		pagination.setStartPos(0);
		pagination.setAmount(5);
		request.setPagination(pagination);
		
		request.setUuId(CommonUtils.getUUID(context));
		request.setMemberType(CommonUtils.getMemberType(context));
		String jsonString = new Gson().toJson(request);
		
		RequestParams requestParams = new RequestParams();
		requestParams.put("jsonString", jsonString);
		Log.i("车辆信息canshu", jsonString);
		HttpUtil.post("searchCarByPda.action", requestParams, new JsonHttpResponseHandler(){
			@Override
			public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
				// TODO Auto-generated method stub
				super.onSuccess(statusCode, headers, response);
				Log.i("车辆信息", response.toString());
			}
			@Override
			public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
				// TODO Auto-generated method stub
				super.onFailure(statusCode, headers, throwable, errorResponse);
			}
			@Override
			public void onFinish() {
				// TODO Auto-generated method stub
				super.onFinish();
				Log.i("finishfinishfinish", "finish");
			}
		});
	}
}
