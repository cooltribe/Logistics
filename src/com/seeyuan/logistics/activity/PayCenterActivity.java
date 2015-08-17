package com.seeyuan.logistics.activity;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.seeyuan.logistics.R;
import com.seeyuan.logistics.application.NetWork;
import com.seeyuan.logistics.customview.MuInputEditText;
import com.seeyuan.logistics.customview.ProgressAlertDialog;
import com.seeyuan.logistics.customview.SingleSelectAlertDlialog;
import com.seeyuan.logistics.datacenter.DataHandler;
import com.seeyuan.logistics.datacenter.OnDataReceiveListener;
import com.seeyuan.logistics.datahandler.GetRechargeAccountHandler;
import com.seeyuan.logistics.entity.CarLengthInfo;
import com.seeyuan.logistics.entity.MemAccountDto;
import com.seeyuan.logistics.entity.PayInfo;
import com.seeyuan.logistics.entity.PdaRequest;
import com.seeyuan.logistics.entity.PdaResponse;
import com.seeyuan.logistics.jsonparser.RechargeAccountJsonParser;
import com.seeyuan.logistics.jsonparser.ResultCodeJsonParser;
import com.seeyuan.logistics.util.CommonUtils;
import com.seeyuan.logistics.util.ToastUtil;
import com.seeyuan.logistics.xmlparser.HttpUtil;

/**
 * 充值中心
 * 
 * @author Administrator
 * 
 */
public class PayCenterActivity extends BaseActivity implements OnClickListener, OnDataReceiveListener {

	private Context context;

	/**
	 * 返回按钮
	 */
	private ImageView maintitle_back_iv;

	/**
	 * 标题title
	 */
	private TextView defaulttitle_title_tv;

	/**
	 * 账号信息
	 */
	private PayInfo payInfo;

	private RadioGroup mRadioGroup;

	/**
	 * 平台账号
	 */
	private TextView Alipay_Account;

	/**
	 * 账户余额
	 */
	private TextView Alipay_Balance;

	/**
	 * 自定义金额
	 */
	private MuInputEditText pay_center_money;

	private final int SHOW_TOAST = 1000;

	private final int SHOW_PROGRESS = 1001;

	private final int CLOSE_PROGRESS = 1002;

	private ProgressAlertDialog progressDialog;

	private MemAccountDto memAccountDto;

	private final int REFRESH_BACK = 10000;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.activity_pay_center); // 软件activity的布局
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.deflaut_titlebar); // titlebar为自己标题栏的布局

		context = getApplicationContext();
		payInfo = new PayInfo();
		
		initView();
	}

	@Override
	protected void onResume() {
		super.onResume();
		getRechargeAccount();
	}

	private Handler myHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case SHOW_TOAST:
				ToastUtil.show(context, msg.obj.toString());
				break;
			case SHOW_PROGRESS:
				showProgress();
				break;
			case CLOSE_PROGRESS:
				dismissProgress();
				break;

			default:
				break;
			}
		};
	};

	private TextView payType;

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
		case R.id.Alipay_But:
			
			doPay();
			break;

		default:
			break;
		}
	}
	
	/**
	 * 获取支付单号
	 * @param <T>
	 */
	private <T> void getPayNum(final String amount,final Intent intent){
		RequestParams params = new RequestParams();
		PdaRequest<T> request = new PdaRequest<T>();
		request.setData(null);
		request.setUuId(CommonUtils.getUUID(context));
		String jsonString = new Gson().toJson(request);
		params.put("jsonString", jsonString);
		Log.i("canshu", jsonString);
		
		HttpUtil.post("apps/getAccountInInfo.action", params, new JsonHttpResponseHandler(){
			@Override
			public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
				// TODO Auto-generated method stub
				super.onSuccess(statusCode, headers, response);
				Log.i("支付单号", response.toString());
				if (response.optBoolean("success")) {
					payInfo.setAmount(amount);
					intent.putExtra("payInfo", payInfo);
					intent.putExtra("payNo", response.optString("data"));
					startActivityForResult(intent, REFRESH_BACK);
				}
			}
			@Override
			public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
				// TODO Auto-generated method stub
				super.onFailure(statusCode, headers, throwable, errorResponse);
				Toast.makeText(context, "网络异常，请稍后重试", Toast.LENGTH_LONG).show();
			}
			@Override
			public void onFinish() {
				// TODO Auto-generated method stub
				super.onFinish();
				Log.i("finish", "finish");
				dismissProgress();
			}
		});
	}
	/**
	 * 确认充值
	 */
	private void doPay() {
		Intent intent;
		String amount = getSelectedRechargeAmount();
		
			if (payType.getText().toString().equals("支付宝")) {
				intent = new Intent(PayCenterActivity.this, DepositAlipay.class);
				if (TextUtils.isEmpty(amount) && pay_center_money.getVisibility() == View.VISIBLE) {
					Message msg = myHandler.obtainMessage();
					msg.what = SHOW_TOAST;
					msg.obj = "请输入金额";
					myHandler.sendMessage(msg);
				} else {
					showProgress();
					getPayNum(amount,intent);
					
				}
			} else if (payType.getText().toString().equals("线下支付")) {
				intent = new Intent(PayCenterActivity.this, PayDetailActivity.class);
				if (TextUtils.isEmpty(amount) && pay_center_money.getVisibility() == View.VISIBLE) {
					Message msg = myHandler.obtainMessage();
					msg.what = SHOW_TOAST;
					msg.obj = "请输入金额";
					myHandler.sendMessage(msg);
				} else {
					payInfo.setAmount(amount);
					intent.putExtra("payInfo", payInfo);
					startActivityForResult(intent, REFRESH_BACK);
				}
			} else {
				Toast.makeText(context, "请选择充值方式", Toast.LENGTH_LONG).show();
			}
			
	}

	/**
	 * 获取所选择的充值金额
	 * 
	 * @return
	 */
	private String getSelectedRechargeAmount() {
		String result = "";
		switch (mRadioGroup.getCheckedRadioButtonId()) {
		case R.id.Alipay_Price_100:
			result = "100";
			break;
		case R.id.Alipay_Price_200:
			result = "200";
			break;
		case R.id.Alipay_Price_300:
			result = "300";
			break;
		case R.id.Alipay_Price_400:
			result = "400";
			break;
		case R.id.Alipay_Price_500:
			result = "500";
			break;
		case R.id.Alipay_Price_other:
			result = pay_center_money.getText().toString();
			break;
		default:
			break;
		}
		return result;
	}

	@Override
	public void initView() {
		maintitle_back_iv = (ImageView) findViewById(R.id.maintitle_back_iv);
		maintitle_back_iv.setOnClickListener(this);

		defaulttitle_title_tv = (TextView) findViewById(R.id.defaulttitle_title_tv);
		defaulttitle_title_tv.setText(R.string.top_up);

		payType = (TextView) findViewById(R.id.pay_type);
		payType.setOnClickListener(this);
		mRadioGroup = (RadioGroup) findViewById(R.id.Alipay_PriceGroup);
		mRadioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				if (checkedId == R.id.Alipay_Price_other) {
					pay_center_money.setVisibility(View.VISIBLE);
				} else {
					pay_center_money.setText("");
					pay_center_money.setVisibility(View.GONE);
				}
			}
		});

		pay_center_money = (MuInputEditText) findViewById(R.id.pay_center_money);

		Alipay_Account = (TextView) findViewById(R.id.Alipay_Account);
		Alipay_Account.setText(CommonUtils.getUserName(context));

		Alipay_Balance = (TextView) findViewById(R.id.Alipay_Balance);

	}

	/**
	 * 刷新充值账号信息
	 */
	private void showRechargeView(MemAccountDto memAccountDto) {
		if (null == memAccountDto)
			return;
		Alipay_Balance.setText(null == memAccountDto.getBalance() ? "" : memAccountDto.getBalance().toString());
	}

	/**
	 * 付款类型选择
	 */
	private void doPayTypeSlect() { 

		List<CarLengthInfo> mDataList = new ArrayList<CarLengthInfo>();
		final TypedArray typedArray = getResources().obtainTypedArray(R.array.pay_type);

		int size = typedArray.length();
		for (int i = 0; i < size; i++) {
			CarLengthInfo indexInfo = new CarLengthInfo();
			indexInfo.setCar_Length(typedArray.getString(i));
			mDataList.add(indexInfo);
		}

		final SingleSelectAlertDlialog ad = new SingleSelectAlertDlialog(PayCenterActivity.this);
		ad.setTitle("选择支付类型");
		ad.setListContentForCarLength(mDataList);
		ad.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View view, int arg2, long arg3) {
				if (null == view)
					return;
				ad.dismiss();
				Log.i("付款选择", typedArray.getText(arg2).toString());
				payType.setText(typedArray.getText(arg2).toString());
			}
		});
		// ad.setPositiveButton("确定", new OnClickListener() {
		//
		// @Override
		// public void onClick(View v) {
		// ad.dismiss();
		// Toast.makeText(HaveGoodsActivity.this, "被点到确定",
		// Toast.LENGTH_LONG).show();
		//
		// }
		// });

		// ad.setNegativeButton("取消", new OnClickListener() {
		//
		// @Override
		// public void onClick(View v) {
		// ad.dismiss();
		// Toast.makeText(HaveGoodsActivity.this, "被点到取消",
		// Toast.LENGTH_LONG).show();
		// }
		// });
		typedArray.recycle();
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.maintitle_back_iv:
			if (!PayCenterActivity.this.isFinishing()) {
				finish();
			}
			break;
		case R.id.pay_type:
			doPayTypeSlect();
			break;
		}
	}

	/**
	 * 获取充值账号信息
	 */
	private void getRechargeAccount() {
		myHandler.sendEmptyMessage(SHOW_PROGRESS);
		PdaRequest<String> request = new PdaRequest<String>();
		request.setData("");
		GetRechargeAccountHandler dataHandler = new GetRechargeAccountHandler(context, request);
		dataHandler.setOnDataReceiveListener(this);
		dataHandler.startNetWork();
	}

	@Override
	public void onDataReceive(DataHandler dataHandler, int resultCode, Object data, int type) {
		myHandler.sendEmptyMessage(CLOSE_PROGRESS);
		switch (resultCode) {
		case NetWork.GET_RECHARGE_ACCOUNT_OK:
			doGetRechargeAccountSuccess(data);
			break;
		case NetWork.GET_RECHARGE_ACCOUNT_ERROR:
			Message msg = myHandler.obtainMessage();
			msg.what = SHOW_TOAST;
			msg.obj = getResources().getString(R.string.network_error_hint);
			myHandler.sendMessage(msg);
			break;
		}
	}

	/**
	 * 获取充值账号信息成功
	 * 
	 * @param data
	 */
	private void doGetRechargeAccountSuccess(Object data) {
		String dataString = null;
		try {
			dataString = new String((byte[]) data, "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
		String message = "";
		try {
			PdaResponse<MemAccountDto> response = RechargeAccountJsonParser.parserRechargeAccountJson(dataString);
			if (!response.isSuccess()) {
				String result = response.getMessage();
				int messageCode = Integer.parseInt(result.substring(0, result.indexOf("#")));
				message = result.substring(result.indexOf("#") + 1, result.length());
			} else {
				memAccountDto = response.getData();
				showRechargeView(memAccountDto);
				return;
			}
		} catch (Exception e) {
			e.printStackTrace();
			message = "获取账号信息失败,请重新获取";
		}
		Message msg = myHandler.obtainMessage();
		msg.what = SHOW_TOAST;
		msg.obj = message;
		myHandler.sendMessage(msg);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode != RESULT_OK) {
			return;
		}
		switch (requestCode) {
		case REFRESH_BACK:
			ToastUtil.show(context, "充值成功");
			// Alipay_Balance.setText(text)
			getRechargeAccount();
			break;

		default:
			break;
		}
	}
}
