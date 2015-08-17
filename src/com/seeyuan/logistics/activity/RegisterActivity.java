package com.seeyuan.logistics.activity;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.seeyuan.logistics.R;
import com.seeyuan.logistics.application.NetWork;
import com.seeyuan.logistics.customview.MuInputEditText;
import com.seeyuan.logistics.customview.ProgressAlertDialog;
import com.seeyuan.logistics.customview.SingleSelectAlertDlialog;
import com.seeyuan.logistics.datacenter.DataHandler;
import com.seeyuan.logistics.datacenter.OnDataReceiveListener;
import com.seeyuan.logistics.datahandler.SubmitRegisterInfoHandler;
import com.seeyuan.logistics.entity.MemberDto;
import com.seeyuan.logistics.entity.PdaRequest;
import com.seeyuan.logistics.entity.PdaResponse;
import com.seeyuan.logistics.jsonparser.LoginJsonParser;
import com.seeyuan.logistics.jsonparser.ResultCodeJsonParser;
import com.seeyuan.logistics.util.CommonUtils;
import com.seeyuan.logistics.util.ToastUtil;

/**
 * 输入账号信息
 * 
 * @author zhazhaobao
 * 
 */
@SuppressLint({ "NewApi", "HandlerLeak" })
public class RegisterActivity extends BaseActivity implements OnClickListener,
		OnDataReceiveListener {
	/**
	 * 返回按钮
	 */
	private ImageView maintitle_back_iv;

	/**
	 * 标题title
	 */
	private TextView defaulttitle_title_tv;

	/**
	 * 注册手机号
	 */
	private MuInputEditText registerPNum;

	/**
	 * 用户名
	 */
	private MuInputEditText userName;

	/**
	 * 用户类型
	 */
	private EditText userType;

	private Context context;

	/**
	 * 会员类型
	 */
	private List<String> userTypeList;

	/**
	 * 二级会员类型
	 */
	private List<String> secendUserTypeList;

	/**
	 * 下一步
	 */
	private Button rNextBtn;

	/**
	 * 注册账号信息
	 */
	private MemberDto registerInfo = new MemberDto();

	private final int GET_AUTHCODE_SUCCESS = 100;

	private final int GET_AUTHCODE_ERROR = 101;
	
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
		setContentView(R.layout.activity_register);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,
				R.layout.deflaut_titlebar); // titlebar为自己标题栏的布局
		context = getApplicationContext();
		initView();
	}

	@Override
	public void initView() {
		maintitle_back_iv = (ImageView) findViewById(R.id.maintitle_back_iv);
		maintitle_back_iv.setOnClickListener(this);

		defaulttitle_title_tv = (TextView) findViewById(R.id.defaulttitle_title_tv);
		defaulttitle_title_tv.setText(R.string.register_hint);

		registerPNum = (MuInputEditText) findViewById(R.id.registerPNum);
		// registerPNum.addTextChangedListener(textWatcherListener);

		userName = (MuInputEditText) findViewById(R.id.userName);
		
		referee_mobile = (MuInputEditText) findViewById(R.id.referee_mobile_num);
		
		userType = (EditText) findViewById(R.id.userType);
		// userType.addTextChangedListener(textWatcherListener);

		rNextBtn = (Button) findViewById(R.id.rNextBtn);

	}

	private Handler myHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case GET_AUTHCODE_SUCCESS:
				Intent intent = new Intent(RegisterActivity.this,
						RegisterAuthcodeActivity.class);
				intent.putExtra("registerInfo", registerInfo);
				CommonUtils.addActivity(RegisterActivity.this);
				startActivity(intent);
				break;
			case SHOW_PROGRESS:
				showProgress();
				break;
			case CLOSE_PROGRESS:
				dismissProgress();
				break;

			default:
				ToastUtil.show(context, msg.obj.toString());
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
			if (!RegisterActivity.this.isFinishing()) {
				finish();
			}
			break;

		default:
			break;
		}
	}

	@Override
	public void onClickListener(View view) {
		switch (view.getId()) {
		case R.id.userType:
			CommonUtils.closeKeyboard(context, registerPNum);
			CommonUtils.closeKeyboard(context, userName);
			CommonUtils.closeKeyboard(context, referee_mobile);
			doUserType();
			break;
		case R.id.rNextBtn:
			doGotoAuthcode();
			break;

		default:
			break;
		}
	}

	/**
	 * 执行获取验证码
	 */
	private void doGotoAuthcode() {

		if (!CommonUtils.isMobileNO(registerPNum.getText().toString())) {
			registerPNum.showPopWindow(RegisterActivity.this, getResources()
					.getString(R.string.wrong_phone_number_hint));
			return;
		}
		
		if ( referee_mobile.getText().toString().length() != 0 && !CommonUtils.isMobileNO(referee_mobile.getText().toString())) {
			referee_mobile.showPopWindow(RegisterActivity.this, getResources()
					.getString(R.string.wrong_phone_number_hint));
			return;
		}
		
		if (userName.getText().length() == 0) {
			userName.showPopWindow(RegisterActivity.this, "请输入正确的用户名");
			return;
		}

		if (userType.getText().length() == 0) {
			String message = "请选择正确的用户类型";
			Message msg = myHandler.obtainMessage();
			msg.what = GET_AUTHCODE_ERROR;
			msg.obj = message;
			myHandler.sendMessage(msg);
			return;
		}
		myHandler.sendEmptyMessage(SHOW_PROGRESS);
		registerInfo.setMobile(registerPNum.getText().toString());
		registerInfo.setUserName(userName.getText().toString());
		registerInfo.setReferee_mobile(referee_mobile.getText().toString());
		registerInfo.setMemberType(CommonUtils.getUserType(userType.getText()
				.toString()));
		PdaRequest<MemberDto> request = new PdaRequest<MemberDto>();
		request.setData(registerInfo);
		SubmitRegisterInfoHandler dataHandler = new SubmitRegisterInfoHandler(
				context, request);
		dataHandler.setOnDataReceiveListener(this);
		dataHandler.startNetWork();

	}

	private boolean isCorrectPhoneNo(String phoneNo) {
		if (!TextUtils.isEmpty(phoneNo) && phoneNo.length() < 11)
			return false;
		return true;
	}

	/**
	 * 执行用户类型操作
	 */
	private void doUserType() {
		String[] data = getResources().getStringArray(R.array.user_type);
		userTypeList = new ArrayList<String>();
		int dataSize = data.length;
		for (int i = 0; i < dataSize; i++) {
			userTypeList.add(data[i]);
		}
		final SingleSelectAlertDlialog dialog = new SingleSelectAlertDlialog(
				RegisterActivity.this);
		dialog.setTitle(getResources().getString(R.string.select_vop_type_hint));
		dialog.setListContentForNormalText(userTypeList);
		dialog.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				userType.setText(userTypeList.get((int)id));
				dialog.dismiss();
				// SecendUserTypeSelect(userTypeList.get(position));
			}
		});
	}

	/**
	 * 第二级VIP类型选择
	 */
	private void SecendUserTypeSelect(final String fristSelectType) {
		String[] secendData = null;
		if (fristSelectType.equalsIgnoreCase(userTypeList.get(0))) {
			// 车主
			secendData = getResources().getStringArray(R.array.user_type_car);
		} else if (fristSelectType.equalsIgnoreCase(userTypeList.get(1))) {
			// 货主
			secendData = getResources().getStringArray(R.array.user_type_goods);
		} else if (fristSelectType.equalsIgnoreCase(userTypeList.get(2))) {
			// 物流商
			secendData = getResources().getStringArray(
					R.array.user_type_company);
		}

		secendUserTypeList = new ArrayList<String>();
		int size = secendData.length;
		for (int i = 0; i < size; i++) {
			secendUserTypeList.add(secendData[i]);
		}
		final SingleSelectAlertDlialog dialog = new SingleSelectAlertDlialog(
				RegisterActivity.this);
		dialog.setTitle(getResources().getString(R.string.select_vop_type_hint));
		dialog.setListContentForNormalText(secendUserTypeList);
		dialog.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				userType.setText(secendUserTypeList.get((int)id));
				dialog.dismiss();
			}
		});
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
			if (userName.length() > 0 && registerPNum.length() > 0
					&& userType.length() > 0 && referee_mobile.length() > 0) {
				rNextBtn.setEnabled(true);
				rNextBtn.setBackgroundResource(R.drawable.confirm_back_button_select);
			} else {
				rNextBtn.setEnabled(false);
				rNextBtn.setBackgroundResource(R.drawable.submint_btn_unfocaus);
			}
			registerInfo.setMobile(registerPNum.getText().toString());
			registerInfo.setReferee_mobile(referee_mobile.getText().toString());
			registerInfo.setUserName(userName.getText().toString());
			if (!TextUtils.isEmpty(userType.getText().toString())) {
				registerInfo.setMemberType(CommonUtils.getUserType(userType
						.getText().toString()));
			}
		}
	};

	private MuInputEditText referee_mobile;

	/**
	 * 返回用户类型标示
	 * 
	 * @param userType
	 * @return
	 */
	private long changeUserType(String userType) {
		return 0;
	}

	@Override
	public void onDataReceive(DataHandler dataHandler, int resultCode,
			Object data, int type) {
		myHandler.sendEmptyMessage(CLOSE_PROGRESS);
		switch (resultCode) {
		case NetWork.SUBMIT_REGISTER_INFO_OK:
			doGetAuthcode(data);
			break;
		case NetWork.SUBMIT_REGISTER_INFO_ERROR:
			ToastUtil.show(RegisterActivity.this,
					getResources().getString(R.string.network_error_hint));
			break;

		default:
			break;
		}
	}

	/**
	 * 获取验证码
	 */
	private void doGetAuthcode(Object data) {
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
				myHandler.sendEmptyMessage(GET_AUTHCODE_SUCCESS);
			} else {// 登录失败
				try {
					String result = response.getMessage();
					int messageCode = Integer.parseInt(result.substring(0,
							result.indexOf("#")));
					String message = result.substring(result.indexOf("#") + 1,
							result.length());
					Message msg = myHandler.obtainMessage();
					msg.what = messageCode;
					msg.obj = message;
					myHandler.sendMessage(msg);
				} catch (Exception e) {
					e.printStackTrace();
					ToastUtil.show(
							context,
							getResources().getString(
									R.string.network_error_hint));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
