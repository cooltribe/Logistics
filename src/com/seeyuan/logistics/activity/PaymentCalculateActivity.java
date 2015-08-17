package com.seeyuan.logistics.activity;

import java.math.BigDecimal;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.seeyuan.logistics.R;
import com.seeyuan.logistics.application.NetWork;
import com.seeyuan.logistics.customview.ProgressAlertDialog;
import com.seeyuan.logistics.datacenter.DataHandler;
import com.seeyuan.logistics.datacenter.OnDataReceiveListener;
import com.seeyuan.logistics.datahandler.GetCalculateInfoHandler;
import com.seeyuan.logistics.entity.AccountDto;
import com.seeyuan.logistics.entity.GoodsDto;
import com.seeyuan.logistics.entity.OrderDto;
import com.seeyuan.logistics.entity.PdaRequest;
import com.seeyuan.logistics.entity.PdaResponse;
import com.seeyuan.logistics.jsonparser.AccountInfoJsonParser;
import com.seeyuan.logistics.util.CommonUtils;
import com.seeyuan.logistics.util.ToastUtil;

/**
 * 订单-支付计算
 * 
 * @author zhazhaobao
 * 
 */
public class PaymentCalculateActivity extends BaseActivity implements
		OnClickListener, OnDataReceiveListener {
	public static PaymentCalculateActivity instance;
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
	 * 订单号
	 */
	private TextView pay_detail_money;

	/**
	 * 线路信息
	 */
	private TextView pay_detail_account;

	/**
	 * 成交时间
	 */
	private TextView pay_detail_type;

	/**
	 * 成交金额
	 */
	private TextView payment_order_money;

	/**
	 * 应付总额
	 */
	private TextView payment_order_all_money;

	private final int SHOW_PROGRESS = 1000;

	private final int CLOSE_PROGRESS = 1001;

	private final int SHOW_TOAST = 1002;

	private ProgressAlertDialog progressDialog;

	private OrderDto orderDto;

	private GoodsDto goodsDto;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.activity_payment_calculate); // 软件activity的布局
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,
				R.layout.deflaut_titlebar); // titlebar为自己标题栏的布局
		context = getApplicationContext();
		orderDto = (OrderDto) getIntent().getSerializableExtra("orderInfo");
		goodsDto = null == orderDto ? new GoodsDto() : orderDto.getGoods();
		instance = this;
		initView();
//		getCalculateInfo();
	}

	@Override
	public void initView() {
		maintitle_back_iv = (ImageView) findViewById(R.id.maintitle_back_iv);
		maintitle_back_iv.setOnClickListener(this);

		defaulttitle_title_tv = (TextView) findViewById(R.id.defaulttitle_title_tv);
		defaulttitle_title_tv.setText("订单结算");

		pay_detail_money = (TextView) findViewById(R.id.pay_detail_money);
		pay_detail_money.setText(null == orderDto ? "" : orderDto.getOrderNo());

		pay_detail_account = (TextView) findViewById(R.id.pay_detail_account);
		String from = TextUtils.isEmpty(goodsDto.getSetout()) ? "未知" : goodsDto
				.getSetout();
		String to = TextUtils.isEmpty(goodsDto.getDestination()) ? "未知"
				: goodsDto.getDestination();
		pay_detail_account.setText(from + "→" + to);

		pay_detail_type = (TextView) findViewById(R.id.pay_detail_type);

		payment_order_money = (TextView) findViewById(R.id.payment_order_money);
		payment_order_money.setText(null == orderDto.getTransAmount() ? ""
				: orderDto.getTransAmount().toString());

		payment_order_all_money = (TextView) findViewById(R.id.payment_order_all_money);
		BigDecimal orderMoney = ((BigDecimal) (null == orderDto
				.getTransAmount() ? BigDecimal.ZERO : orderDto.getTransAmount()))
				.add((BigDecimal) (null == orderDto.getInsuranceAmount() ? BigDecimal.ZERO
						: orderDto.getInsuranceAmount()))
				.add((BigDecimal) (null == orderDto.getProtectAmount() ? BigDecimal.ZERO
						: orderDto.getProtectAmount()));

		payment_order_all_money.setText(String.valueOf(orderMoney));
	}

	/**
	 * 获取订单结算计算规则
	 */
	private void getCalculateInfo() {
		new Thread(new Runnable() {

			@Override
			public void run() {
				PdaRequest<String> request = new PdaRequest<String>();
				request.setData("");
				GetCalculateInfoHandler dataHandler = new GetCalculateInfoHandler(
						context, request);
				dataHandler
						.setOnDataReceiveListener(PaymentCalculateActivity.this);
				dataHandler.startNetWork();
			}
		}).start();
	}

	@SuppressWarnings("unused")
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
		case R.id.bt_save:
			doPay();
			break;

		default:
			break;
		}
	}

	/**
	 * 去付款
	 */
	private void doPay() {
		Intent intent = new Intent(PaymentCalculateActivity.this,
				PaymentDetailActivity.class);
		intent.putExtra("payMoney", payment_order_all_money.getText().toString());
		intent.putExtra("orderInfo", orderDto);
		startActivity(intent);
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.maintitle_back_iv:
			CommonUtils.finishActivity(this);
			break;

		default:
			break;
		}
	}

	@Override
	public void onDataReceive(DataHandler dataHandler, int resultCode,
			Object data, int type) {
		myHandler.sendEmptyMessage(CLOSE_PROGRESS);
		switch (resultCode) {
		case NetWork.GET_CALCULATE_INFO_OK:
			doGetCalculateInfoSuccess(data);
			break;
		case NetWork.GET_CERTIFICATION_INFO_ERROR:
			doGetCalculateInfoError();
			break;

		default:
			break;
		}
	}

	/**
	 * 异常
	 */
	private void doGetCalculateInfoError() {
		Message msg = myHandler.obtainMessage();
		msg.what = SHOW_TOAST;
		msg.obj = getResources().getString(R.string.network_error_hint);
		myHandler.sendMessage(msg);
	}

	/**
	 * 获取结算计算方法成功
	 * 
	 * @param data
	 */
	private void doGetCalculateInfoSuccess(Object data) {
		String dataString = null;
		try {
			dataString = new String((byte[]) data, "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
		String message = "";
		try {
			PdaResponse<List<AccountDto>> response = AccountInfoJsonParser
					.parserOrderOperationDataJson(dataString);
			if (!response.isSuccess()) {
				String result = response.getMessage();
				int messageCode = Integer.parseInt(result.substring(0,
						result.indexOf("#")));
				message = result.substring(result.indexOf("#") + 1,
						result.length());
			} else {

				return;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		Message msg = myHandler.obtainMessage();
		msg.what = SHOW_TOAST;
		msg.obj = message;
		myHandler.sendMessage(msg);
	}

}
