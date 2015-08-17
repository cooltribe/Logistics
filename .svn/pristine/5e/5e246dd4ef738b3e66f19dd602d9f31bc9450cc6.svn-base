package com.seeyuan.logistics.activity;

import org.json.JSONException;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.SlidingDrawer;

import com.seeyuan.logistics.R;
import com.seeyuan.logistics.application.ApplicationPool;
import com.seeyuan.logistics.application.ConstantPool;
import com.seeyuan.logistics.application.NetWork;
import com.seeyuan.logistics.datacenter.DataHandler;
import com.seeyuan.logistics.datacenter.OnDataReceiveListener;
import com.seeyuan.logistics.datahandler.LoginHandler;
import com.seeyuan.logistics.entity.MemberDto;
import com.seeyuan.logistics.entity.PdaRequest;
import com.seeyuan.logistics.entity.PdaResponse;
import com.seeyuan.logistics.entity.UserInfo;
import com.seeyuan.logistics.jsonparser.LoginJsonParser;
import com.seeyuan.logistics.provider.DBOperate;
import com.seeyuan.logistics.service.LoginIMServerService;
import com.seeyuan.logistics.util.CommonUtils;
import com.seeyuan.logistics.util.MD5Util;

public class WelcomeActivity extends BaseActivity implements
		OnDataReceiveListener {

	/**
	 * 欢迎界面图片
	 */
	private ImageView welcome_img_iv;

	/**
	 * 用户信息
	 */
	private UserInfo mUserInfo;
	private DBOperate dbOperate;
	private Context context;
	private SharedPreferences sPreferences;
	/**
	 * 登录成功
	 */
	private final int LOGIN_CODE_SUCCESS = 100;
	private final int LOGIN_CODE_FAILED = 101;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_welcome);
		context = getApplicationContext();
		sPreferences = getSharedPreferences(ConstantPool.LOGISTICS_PREFERENCES,
				Context.MODE_PRIVATE);
		dbOperate = DBOperate.getInstance(context);
		initView();

		myHandler.postDelayed(new Runnable() {
			
			@Override
			public void run() {
				Intent intent = new Intent(WelcomeActivity.this,
						LoginActivity.class);
				startActivity(intent);
				CommonUtils.finishActivity(WelcomeActivity.this);				
			}
		}, 3000);
	}

	@Override
	public void onClickListener(View view) {
		switch (view.getId()) {

		default:
			break;
		}
	}

	@Override
	public void initView() {
		welcome_img_iv = (ImageView) findViewById(R.id.welcome_img_iv);
	}

	private Handler myHandler = new Handler() {

		public void handleMessage(Message msg) {
			switch (msg.what) {
			case LOGIN_CODE_SUCCESS:
				login();
				break;
			case LOGIN_CODE_FAILED:
				doLoginFailed();
				break;
			}
		}
	};

	private void login() {
		Intent imIntent = new Intent(WelcomeActivity.this,
				LoginIMServerService.class);
		startService(imIntent);
		Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
		startActivity(intent);
		finish();
	}

	/**
	 * 提交登录信息，自动登录
	 */
	private void submitLoginInfo() {
		mUserInfo = dbOperate.getUesrInfoByUUID(CommonUtils.getUUID(context));
		if (null == mUserInfo || mUserInfo.getIsLogin().equalsIgnoreCase("N")) {
			myHandler.sendEmptyMessage(LOGIN_CODE_FAILED);
			return;
		}

		MemberDto loginInfo = new MemberDto();
		loginInfo.setUserName(mUserInfo.getUSER_NAME());
		loginInfo.setPassword((MD5Util.getMD5String(mUserInfo.getUSER_NAME()
				+ mUserInfo.getPASSWORD())).toLowerCase());
		PdaRequest<MemberDto> request = new PdaRequest<MemberDto>();
		request.setData(loginInfo);
		LoginHandler loginHandler = new LoginHandler(context, request);
		loginHandler.setOnDataReceiveListener(this);
		loginHandler.startNetWork();

	}

	@Override
	public void onDataReceive(DataHandler dataHandler, int resultCode,
			Object data, int type) {
		switch (resultCode) {
		case NetWork.LOGIN_OK:
			doLoginSuccess(data);
			break;
		case NetWork.LOGIN_ERROR:
			myHandler.sendEmptyMessage(LOGIN_CODE_FAILED);
			break;

		default:
			break;
		}
	}

	/**
	 * 登录失败
	 */
	private void doLoginFailed() {
		Intent intent = new Intent(WelcomeActivity.this, LoginActivity.class);
		startActivity(intent);
		finish();
	}

	/**
	 * 登录成功
	 */
	private void doLoginSuccess(Object data) {
		String dataString = null;
		try {
			dataString = new String((byte[]) data, "UTF-8");
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		try {
			PdaResponse<MemberDto> response = LoginJsonParser
					.parserLoginJson(dataString);
			if (response.isSuccess()) {// 登录成功
				MemberDto result = response.getData();
				// 保存登录信息
				Editor editor = sPreferences.edit();
				editor.putString("uuId", result.getUuId());
				editor.putString("memberType", result.getMemberType()
						.toString());
				editor.putString("userName", result.getUserName());
				editor.commit();
				ApplicationPool.setUUID(result.getUuId());
				ApplicationPool
						.setMemberType(result.getMemberType().toString());
				ApplicationPool.setUserName(result.getUserName());
				UserInfo userInfo = new UserInfo();
				userInfo.setUuId(result.getUuId());
				userInfo.setUSER_NAME(result.getUserName());
				userInfo.setPASSWORD(CommonUtils.getPassword(context));
				userInfo.setMOBILE(result.getMobile());
				userInfo.setMemberType(result.getMemberType());
				userInfo.setIsLogin("Y");
				dbOperate.updateUserInfo(userInfo);
				myHandler.sendEmptyMessage(LOGIN_CODE_SUCCESS);
			} else {// 登录失败
				String result = response.getMessage();
				int messageCode = Integer.parseInt(result.substring(0,
						result.indexOf("#")));
				String message = result.substring(result.indexOf("#") + 1,
						result.length());
				Message msg = myHandler.obtainMessage();
				msg.what = LOGIN_CODE_FAILED;
				msg.obj = message;
				myHandler.sendMessage(msg);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

}
