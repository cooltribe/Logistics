package com.seeyuan.logistics.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
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
import com.seeyuan.logistics.datahandler.SubmitRetrivevPasswordHandlerHandler;
import com.seeyuan.logistics.entity.MemberDto;
import com.seeyuan.logistics.entity.PdaRequest;
import com.seeyuan.logistics.entity.PdaResponse;
import com.seeyuan.logistics.jsonparser.ResultCodeJsonParser;
import com.seeyuan.logistics.util.CommonUtils;
import com.seeyuan.logistics.util.ToastUtil;

/**
 * 密码找回,重新设置密码
 * 
 * @author zhazhaobao
 * 
 */
public class RetrievePasswordSettingActivity extends BaseActivity implements
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
	 * 输入新密码
	 */
	private MuInputEditText retrieve_new_password_edt;

	/**
	 * 确认新密码
	 */
	private MuInputEditText retrieve_again_password_edt;

	/**
	 * 提交
	 */
	private Button retrieve_submit_button;

	/**
	 * 显示进度条
	 */
	private final int SHOW_PROGRESS = 2000;
	/**
	 * 关闭进度条
	 */
	private final int CLOSE_PROGRESS = 2001;

	private final int SHOW_TOAST = 2002;

	private ProgressAlertDialog progressDialog;

	private MemberDto retrieveInfo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.activity_retrieve_password_set);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,
				R.layout.deflaut_titlebar); // titlebar为自己标题栏的布局
		context = getApplicationContext();
		retrieveInfo = (MemberDto) getIntent()
				.getSerializableExtra("memberDto");
		CommonUtils.addActivity(this);
		initView();
	}

	@Override
	public void initView() {
		maintitle_back_iv = (ImageView) findViewById(R.id.maintitle_back_iv);
		maintitle_back_iv.setOnClickListener(this);

		defaulttitle_title_tv = (TextView) findViewById(R.id.defaulttitle_title_tv);
		defaulttitle_title_tv.setText(R.string.get_password_hint);

		retrieve_new_password_edt = (MuInputEditText) findViewById(R.id.retrieve_new_password_edt);
		// retrieve_new_password_edt.addTextChangedListener(textWatcherListener);
		retrieve_again_password_edt = (MuInputEditText) findViewById(R.id.retrieve_again_password_edt);
		// retrieve_again_password_edt.addTextChangedListener(textWatcherListener);
		// retrieve_submit_button = (Button)
		// findViewById(R.id.retrieve_submit_button);

	}

	private Handler myHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case SHOW_PROGRESS:
				showProgress();
				break;
			case CLOSE_PROGRESS:
				dismissProgress();
				break;
			case SHOW_TOAST:
				ToastUtil.show(context, msg.obj.toString());
				break;

			default:
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
	public void onClickListener(View view) {
		switch (view.getId()) {
		case R.id.retrieve_submit_button:
			new Thread(new Runnable() {

				@Override
				public void run() {
					doSubmitPassword();
				}
			}).start();
			break;

		default:
			break;
		}
	}

	/**
	 * 执行提交密码
	 */
	private void doSubmitPassword() {

		if (!CommonUtils.isPasswordTypeCorrect(retrieve_new_password_edt
				.getText().toString())) {
			retrieve_new_password_edt.showPopWindow(
					RetrievePasswordSettingActivity.this, getResources()
							.getString(R.string.psw_number_format));
			return;
		}

		if (!CommonUtils.isPasswordCorrect(retrieve_new_password_edt.getText()
				.toString(), retrieve_again_password_edt.getText().toString())) {
			retrieve_again_password_edt.showPopWindow(
					RetrievePasswordSettingActivity.this, getResources()
							.getString(R.string.entered_psw_differ));
			return;
		}
		// 执行密码修改
		myHandler.sendEmptyMessage(SHOW_PROGRESS);
		PdaRequest<MemberDto> request = new PdaRequest<MemberDto>();
		retrieveInfo
				.setPassword(retrieve_new_password_edt.getText().toString());
		request.setData(retrieveInfo);
		SubmitRetrivevPasswordHandlerHandler dataHandler = new SubmitRetrivevPasswordHandlerHandler(
				context, request);
		dataHandler.setOnDataReceiveListener(this);
		dataHandler.startNetWork();
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.maintitle_back_iv:
			if (!RetrievePasswordSettingActivity.this.isFinishing()) {
				finish();
			}
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
			if (retrieve_new_password_edt.length() > 0
					&& retrieve_again_password_edt.length() > 0) {
				retrieve_submit_button.setEnabled(true);
				retrieve_submit_button
						.setBackgroundResource(R.drawable.confirm_back_button_select);
			} else {
				retrieve_submit_button.setEnabled(false);
				retrieve_submit_button
						.setBackgroundResource(R.drawable.submint_btn_unfocaus);
			}
		}
	};

	@Override
	public void onDataReceive(DataHandler dataHandler, int resultCode,
			Object data, int type) {
		myHandler.sendEmptyMessage(CLOSE_PROGRESS);
		switch (resultCode) {
		case NetWork.SUBMIT_RETRIVEV_PASSWORD_OK:
			doSubmitRetrievePasswordSuccess(data);
			break;
		case NetWork.SUBMIT_RETRIVEV_PASSWORD_ERROR:
			ToastUtil.show(context,
					getResources().getString(R.string.network_error_hint));
			break;

		default:
			break;
		}
	}

	private void doSubmitRetrievePasswordSuccess(Object data) {
		String dataString = null;
		try {
			dataString = new String((byte[]) data, "UTF-8");
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		Message msg = myHandler.obtainMessage();
		msg.what = SHOW_TOAST;
		try {
			PdaResponse<String> response = ResultCodeJsonParser
					.parserResultCodeJson(dataString);
			if (null != response && response.isSuccess()) {
				String result = response.getMessage();
				Intent intent = new Intent(this, LoginActivity.class);
				intent.putExtra("toast", "修改密码成功,请重新登录");
				startActivity(intent);
				CommonUtils.finishAllActivity();
			} else {// 登录失败
				try {
					msg.obj = getResources().getString(
							R.string.network_error_hint);
					msg.sendToTarget();
				} catch (Exception e) {
					e.printStackTrace();
					msg.obj = getResources().getString(
							R.string.network_error_hint);
					myHandler.sendMessage(msg);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			msg.obj = getResources().getString(R.string.network_error_hint);
			myHandler.sendMessage(msg);
		}
	}
}
