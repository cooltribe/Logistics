package com.seeyuan.logistics.activity;

import java.io.File;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;

import com.google.gson.Gson;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.seeyuan.logistics.R;
import com.seeyuan.logistics.application.NetWork;
import com.seeyuan.logistics.datacenter.DataHandler;
import com.seeyuan.logistics.datacenter.OnDataReceiveListener;
import com.seeyuan.logistics.datahandler.LoginHandler;
import com.seeyuan.logistics.entity.MemberDto;
import com.seeyuan.logistics.entity.PdaRequest;
import com.seeyuan.logistics.util.MD5Util;
import com.seeyuan.logistics.util.ToastUtil;
import com.seeyuan.logistics.xmlparser.HttpUtil;

public class Test extends Activity implements OnDataReceiveListener ,OnClickListener{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		StrictMode.ThreadPolicy policy=new StrictMode.ThreadPolicy.Builder().permitAll().build();
		StrictMode.setThreadPolicy(policy);
		setContentView(R.layout.activity_test);
//		doLoginSubmit();
		getResulet1();
	}
	@SuppressWarnings("deprecation")
	private void getResulet1(){
		try {
			MemberDto loginInfo = new MemberDto();
			loginInfo.setUserName("oscar1102");
			loginInfo.setPassword((MD5Util.getMD5String("oscar1102" + "12345678".toString())).toLowerCase());
			PdaRequest<MemberDto> request = new PdaRequest<MemberDto>();
			request.setData(loginInfo);
			
			HttpClient client = HttpUtil.getHttpClient();
			String jsonString = new Gson().toJson(request);
			
			
			HttpPost post = new HttpPost("");
			MultipartEntity multipartEntity =  new MultipartEntity(); 
			multipartEntity.addPart("jsonString", new StringBody(jsonString,
					Charset.forName(HTTP.UTF_8)));
			post.setEntity(multipartEntity);
			HttpResponse response = client.execute(post);
			System.err.println("mmmmmmmm:" + response.getStatusLine().getStatusCode());
			if (response.getStatusLine().getStatusCode() == 200) {
				HttpEntity entity = response.getEntity();
				String string = EntityUtils.toString(entity);
				System.out.println("xxxxxxxxxxxx" + string);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	public void getResult(){
		try {
//			if (login_userID.getText().length() == 0) {
//				login_userID.showPopWindow(LoginActivity1.this, "请输入正确的账号");
//				return;
//			}
//
//			if (!CommonUtils.isPasswordTypeCorrect(login_password.getText().toString())) {
//				login_password.showPopWindow(LoginActivity1.this, getResources().getString(R.string.psw_number_format));
//				return;
//			}
//			myHandler.sendEmptyMessage(SHOW_PROGRESS);
//			CommonUtils.closeKeyboard(context, login_password);
//			CommonUtils.closeKeyboard(context, login_userID);
			MemberDto loginInfo = new MemberDto();
			loginInfo.setUserName("oscar1102".toString());
			loginInfo.setPassword((MD5Util.getMD5String("oscar1102" + "12345678".toString())).toLowerCase());
			PdaRequest<MemberDto> request = new PdaRequest<MemberDto>();
			request.setData(loginInfo);
			
			String jsonString = new Gson().toJson(request);
			RequestParams requestParams = new RequestParams();
			requestParams.put("jsonString", jsonString);
			Log.i("2canshu", requestParams.toString());
			HttpUtil.post("", requestParams, new JsonHttpResponseHandler(){
			@Override
			public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
				// TODO Auto-generated method stub
				super.onSuccess(statusCode, headers, response);
				try {
					Log.i("jieguo", response.toString());
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			@Override
				public void onStart() {
					// TODO Auto-generated method stub
					super.onStart();
					Log.i("start", "start");
				}
			@Override
				public void onCancel() {
					// TODO Auto-generated method stub
					super.onCancel();
					Log.i("cancel", "cancel");
				}
			@Override
				public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
					// TODO Auto-generated method stub
					super.onFailure(statusCode, headers, responseString, throwable);
					try {
						Log.i("fail", throwable.toString());
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			@Override
				public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
					// TODO Auto-generated method stub
					super.onFailure(statusCode, headers, throwable, errorResponse);
					try {
						Log.i("fail1", throwable.toString() + errorResponse.toString());
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			@Override
				public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
					// TODO Auto-generated method stub
					super.onSuccess(statusCode, headers, response);
					Log.i("cancel1", "cancel1");
				}
			@Override
				public void onSuccess(int statusCode, Header[] headers, String responseString) {
					// TODO Auto-generated method stub
					super.onSuccess(statusCode, headers, responseString);
					Log.i("cancel2", "cancel2");
				}
				@Override
				public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
					// TODO Auto-generated method stub
					super.onFailure(statusCode, headers, throwable, errorResponse);
					try {
						Log.i("errorjieguo", errorResponse.toString());
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				@Override
				public void onFinish() {
					// TODO Auto-generated method stub
					super.onFinish();
					Log.i("finish", "finishfinishfinishfinish");
				}
			});
			
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	/**
	 * 执行登录操作
	 */
	private void doLoginSubmit() {

		MemberDto loginInfo = new MemberDto();
		loginInfo.setUserName("oscar1103");
		loginInfo.setPassword((MD5Util.getMD5String("oscar1102" + "12345678".toString())).toLowerCase());
		PdaRequest<MemberDto> request = new PdaRequest<MemberDto>();
		request.setData(loginInfo);
		LoginHandler loginHandler = new LoginHandler(this, request);
		loginHandler.setOnDataReceiveListener(this);
		loginHandler.startNetWork();

	}
	@Override
	public void onDataReceive(DataHandler dataHandler, int resultCode, Object data, int type) {
		// TODO Auto-generated method stub
		switch (resultCode) {
		case NetWork.LOGIN_OK:
			ToastUtil.show(this,
					"ok");
//			doLoginSuccess(data);
			break;
		case NetWork.LOGIN_ERROR:
//			myHandler.sendEmptyMessage(CLOSE_PROGRESS);
			ToastUtil.show(this,
					getResources().getString(R.string.network_error_hint));
			break;

		default:

			break;
		}
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}

}
