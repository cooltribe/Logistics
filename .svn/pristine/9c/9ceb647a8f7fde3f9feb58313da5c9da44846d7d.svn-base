package com.seeyuan.logistics.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.seeyuan.logistics.R;
import com.seeyuan.logistics.application.NetWork;
import com.seeyuan.logistics.customview.MuInputEditText;
import com.seeyuan.logistics.customview.ProgressAlertDialog;
import com.seeyuan.logistics.datacenter.DataHandler;
import com.seeyuan.logistics.datacenter.OnDataReceiveListener;
import com.seeyuan.logistics.datahandler.SendSMSHanlder;
import com.seeyuan.logistics.datahandler.SubmitRegisterAuthcodeHandler;
import com.seeyuan.logistics.entity.MemberDto;
import com.seeyuan.logistics.entity.PdaRequest;
import com.seeyuan.logistics.entity.PdaResponse;
import com.seeyuan.logistics.entity.SmsInfoDto;
import com.seeyuan.logistics.jsonparser.ResultCodeJsonParser;
import com.seeyuan.logistics.jsonparser.SMSJsonParser;
import com.seeyuan.logistics.util.CommonUtils;
import com.seeyuan.logistics.util.ToastUtil;

/**
 * 验证码
 * 
 * @author zhazhaobao
 * 
 */
public class RegisterAuthcodeActivity extends BaseActivity implements
		OnClickListener, OnDataReceiveListener {
	/**
	 * 返回按钮
	 */
	private ImageView maintitle_back_iv;

	/**
	 * 标题title
	 */
	private TextView defaulttitle_title_tv;

	private Context context;

	/**
	 * 注册账号信息
	 */
	private MemberDto registerInfo;

	/**
	 * 下一步
	 */
	private Button rAuthCodeNextBtn;

	/**
	 * 验证码
	 */
	private MuInputEditText getAuthCodeEdt;

	/**
	 * 重新获取验证码
	 */
	private Button rReGetAuthCodeBtn;

	/**
	 * 重新获取验证码计时器
	 */
	private TimeCount timeCount;

	/**
	 * 倒计时时间
	 */
	private long timeNo = 120000L;

	private final int SUBMIT_AUTHCODE_SUCCESS = 200;

	/**
	 * 显示进度条
	 */
	private final int SHOW_PROGRESS = 2000;
	/**
	 * 关闭进度条
	 */
	private final int CLOSE_PROGRESS = 2001;

	private ProgressAlertDialog progressDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.activity_register_authcode);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,
				R.layout.deflaut_titlebar); // titlebar为自己标题栏的布局
		context = getApplicationContext();
		registerInfo = (MemberDto) getIntent().getSerializableExtra(
				"registerInfo");
		initView();
		getAuthcodeSMS();
		timeCount = new TimeCount(timeNo, 1000L);
		timeCount.start();
	}

	@Override
	public void initView() {
		maintitle_back_iv = (ImageView) findViewById(R.id.maintitle_back_iv);
		maintitle_back_iv.setOnClickListener(this);

		defaulttitle_title_tv = (TextView) findViewById(R.id.defaulttitle_title_tv);
		defaulttitle_title_tv.setText(R.string.register_hint);

		rAuthCodeNextBtn = (Button) findViewById(R.id.rAuthCodeNextBtn);

		getAuthCodeEdt = (MuInputEditText) findViewById(R.id.getAuthCodeEdt);
		// getAuthCodeEdt.addTextChangedListener(textWatcherListener);

		rReGetAuthCodeBtn = (Button) findViewById(R.id.rReGetAuthCodeBtn);

	}

	/**
	 * 获取验证码短信
	 */
	private void getAuthcodeSMS() {
		PdaRequest<SmsInfoDto> requset = new PdaRequest<SmsInfoDto>();
		SmsInfoDto smsDto = new SmsInfoDto();
		smsDto.setMobile(registerInfo.getMobile());
		requset.setData(smsDto);
		SendSMSHanlder dataHanlder = new SendSMSHanlder(context, requset);
		dataHanlder.setOnDataReceiveListener(this);
		dataHanlder.startNetWork();
	}

	@SuppressWarnings("unused")
	private Handler myHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case SUBMIT_AUTHCODE_SUCCESS:
				Intent intent = new Intent(RegisterAuthcodeActivity.this,
						RegisterPasswordActivity.class);
				intent.putExtra("registerInfo", registerInfo);
				CommonUtils.addActivity(RegisterAuthcodeActivity.this);
				startActivity(intent);
				break;
			case SHOW_PROGRESS:
				showProgress();
				break;
			case CLOSE_PROGRESS:
				dismissProgress();
				break;

			default:
				getAuthCodeEdt.showPopWindow(RegisterAuthcodeActivity.this,
						msg.obj.toString());
				break;
			}
		};
	};

	private void showProgress() {
		if (progressDialog == null) {
			progressDialog = new ProgressAlertDialog(this);
		} else {
			progressDialog.show();
		}
	}

	private void dismissProgress() {
		if (progressDialog != null) {
			progressDialog.dismiss();
		}
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.maintitle_back_iv:
			if (!RegisterAuthcodeActivity.this.isFinishing()) {
				finish();
			}
			break;

		default:
			break;
		}
	}

	/**
	 * 获取验证码
	 */
	private void doGetAuthcode() {
		getAuthcodeSMS();
		timeCount = new TimeCount(timeNo, 1000L);
		timeCount.start();
	}

	/**
	 * 执行进入设置密码
	 */
	private void doSetPassword() {
		if (isCorrectAuthcode(getAuthCodeEdt.getText().toString())) {
			myHandler.sendEmptyMessage(SHOW_PROGRESS);
			PdaRequest<MemberDto> request = new PdaRequest<MemberDto>();
			registerInfo.setVerifyCode(getAuthCodeEdt.getText().toString());
			request.setData(registerInfo);
			SubmitRegisterAuthcodeHandler dataHandler = new SubmitRegisterAuthcodeHandler(
					context, request);
			dataHandler.setOnDataReceiveListener(this);
			dataHandler.startNetWork();
		} else {
			getAuthCodeEdt.showPopWindow(RegisterAuthcodeActivity.this,
					getResources().getString(R.string.wrong_authcode_hint));
		}
	}

	private boolean isCorrectAuthcode(String authcode) {
		if (TextUtils.isEmpty(authcode) && authcode.length() < 6)
			return false;
		return true;

	}

	@Override
	public void onClickListener(View view) {
		switch (view.getId()) {
		case R.id.callBtn:
			CommonUtils.makeingCalls(context,
					getResources().getString(R.string.Service_phone));
			break;
		case R.id.rAuthCodeNextBtn:
			CommonUtils.closeKeyboard(context, getAuthCodeEdt);
			doSetPassword();
			break;
		case R.id.rReGetAuthCodeBtn:
			doGetAuthcode();
			break;

		default:
			break;
		}
	}

	private TextWatcher textWatcherListener = new TextWatcher() {

		@Override
		public void onTextChanged(CharSequence arg0, int arg1, int arg2,
				int arg3) {

		}

		@Override
		public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
				int arg3) {
		}

		@Override
		public void afterTextChanged(Editable arg0) {
			if (getAuthCodeEdt.length() > 0) {
				rAuthCodeNextBtn.setEnabled(true);
				rAuthCodeNextBtn
						.setBackgroundResource(R.drawable.confirm_back_button_select);
				registerInfo.setVerifyCode(getAuthCodeEdt.getText().toString());
			} else {
				rAuthCodeNextBtn.setEnabled(false);
				rAuthCodeNextBtn
						.setBackgroundResource(R.drawable.submint_btn_unfocaus);
			}
		}
	};

	class TimeCount extends CountDownTimer {

		public TimeCount(long millisInFuture, long countDownInterval) {
			super(millisInFuture, countDownInterval);
		}

		@Override
		public void onFinish() {
			rReGetAuthCodeBtn.setEnabled(true);
			rReGetAuthCodeBtn.setText(getResources().getString(
					R.string.register_reget_authcode_btntext));
		}

		@Override
		public void onTick(long currentTime) {
			rReGetAuthCodeBtn.setEnabled(false);
			rReGetAuthCodeBtn.setText(String.format(
					getResources().getString(R.string.get_authcode_again_hint),
					currentTime / 1000L));
		}

	}

	@Override
	public void onDataReceive(DataHandler dataHandler, int resultCode,
			Object data, int type) {
		myHandler.sendEmptyMessage(CLOSE_PROGRESS);
		switch (resultCode) {
		case NetWork.SUBMIT_REGISTER_AUTHCODE_OK:
			doSubmitAuthcodeSuccess(data);
			break;
		case NetWork.SEND_SMS_ERROR:
		case NetWork.SUBMIT_REGISTER_AUTHCODE_ERROR:
			ToastUtil.show(context,
					getResources().getString(R.string.network_error_hint));
			break;
		case NetWork.SEND_SMS_OK:
			doGetSMSSuccess(data);
			break;

		default:
			break;
		}
	}

	/**
	 * 获取短信验证码成功
	 * 
	 * @param data
	 */
	private void doGetSMSSuccess(Object data) {
		String dataString = null;
		try {
			dataString = new String((byte[]) data, "UTF-8");
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		try {
			PdaResponse<SmsInfoDto> response = SMSJsonParser
					.parserSMSJson(dataString);
			if (response.isSuccess()) {
				timeNo = (long) response.getData().getSmsTime();
				ToastUtil.show(context, "验证码获取成功,请等待短信通知");

			} else {
				String result = response.getMessage();
				int messageCode = Integer.parseInt(result.substring(0,
						result.indexOf("#")));
				String message = result.substring(result.indexOf("#") + 1,
						result.length());
				Message msg = myHandler.obtainMessage();
				msg.what = messageCode;
				msg.obj = message;
				myHandler.sendMessage(msg);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取验证码
	 */
	private void doSubmitAuthcodeSuccess(Object data) {
		String dataString = null;
		try {
			dataString = new String((byte[]) data, "UTF-8");
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		try {
			PdaResponse<String> response = ResultCodeJsonParser
					.parserResultCodeJson(dataString);
			if (response.isSuccess()) {// 登录成功

				myHandler.sendEmptyMessage(SUBMIT_AUTHCODE_SUCCESS);
			} else {// 登录失败
				String result = response.getMessage();
				int messageCode = Integer.parseInt(result.substring(0,
						result.indexOf("#")));
				String message = result.substring(result.indexOf("#") + 1,
						result.length());
				Message msg = myHandler.obtainMessage();
				msg.what = messageCode;
				msg.obj = message;
				myHandler.sendMessage(msg);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
