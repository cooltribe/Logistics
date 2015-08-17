package com.seeyuan.logistics.activity;

import java.math.BigDecimal;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.seeyuan.logistics.R;
import com.seeyuan.logistics.activity.TabPublishGoodsActivity.Filter;
import com.seeyuan.logistics.application.NetWork;
import com.seeyuan.logistics.customview.MuInputEditText;
import com.seeyuan.logistics.customview.ProgressAlertDialog;
import com.seeyuan.logistics.datacenter.DataHandler;
import com.seeyuan.logistics.datacenter.OnDataReceiveListener;
import com.seeyuan.logistics.datahandler.PlaceAnOrderHandler;
import com.seeyuan.logistics.entity.CarsDto;
import com.seeyuan.logistics.entity.DriverDto;
import com.seeyuan.logistics.entity.GoodsDto;
import com.seeyuan.logistics.entity.MemberDto;
import com.seeyuan.logistics.entity.OrderDto;
import com.seeyuan.logistics.entity.PdaRequest;
import com.seeyuan.logistics.entity.PdaResponse;
import com.seeyuan.logistics.jsonparser.LoginJsonParser;
import com.seeyuan.logistics.jsonparser.ResultCodeJsonParser;
import com.seeyuan.logistics.util.CommonUtils;
import com.seeyuan.logistics.util.ToastUtil;

/**
 * 下单，货源订单
 * 
 * @author zhazhaobao
 * 
 */
public class PlaceAnGoodsOrderActivity extends BaseActivity implements
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

	private GoodsDto mGoodsDto;

	private DriverDto mDriverDto;

	private CarsDto mCarsDto;

	private OrderDto mOrderDto = new OrderDto();

	private TextView Order_GoodsName;

	private MuInputEditText GoodsManager_GoodsWeight;

	private Button Order_Driver;

	private Button Order_Car;

	private Button Order_ensure;

	private Button Order_cancel;

	/**
	 * 选择司机。数据返回
	 */
	private final int REFRESH_DRIVER = 1000;

	/**
	 * 选择车辆。数据返回
	 */
	private final int REFRESH_CAR = 1001;

	/**
	 * 显示进度条
	 */
	private final int SHOW_PROGRESS = 1002;
	/**
	 * 关闭进度条
	 */
	private final int CLOSE_PROGRESS = 1003;

	private final int SHOW_TOAST = 1004;

	private ProgressAlertDialog progressDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.activity_place_an_goods_order); // 软件activity的布局
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,
				R.layout.deflaut_titlebar); // titlebar为自己标题栏的布局
		context = getApplicationContext();
		mGoodsDto = (GoodsDto) getIntent().getSerializableExtra("goodsInfo");
		initView();
		CommonUtils.addActivity(this);
	}

	@Override
	public void initView() {
		maintitle_back_iv = (ImageView) findViewById(R.id.maintitle_back_iv);
		maintitle_back_iv.setOnClickListener(this);

		defaulttitle_title_tv = (TextView) findViewById(R.id.defaulttitle_title_tv);
		defaulttitle_title_tv.setText(R.string.order_info_hint);

		Order_GoodsName = (TextView) findViewById(R.id.Order_GoodsName);
		Order_GoodsName.setText(mGoodsDto.getGoodsName());

		GoodsManager_GoodsWeight = (MuInputEditText) findViewById(R.id.GoodsManager_GoodsWeight);
		GoodsManager_GoodsWeight.setText(null == mGoodsDto.getCost() ? ""
				: mGoodsDto.getCost().toString());

		Order_Driver = (Button) findViewById(R.id.Order_Driver);
		Order_Driver.setOnClickListener(this);
		Order_Car = (Button) findViewById(R.id.Order_Car);
		Order_Car.setOnClickListener(this);

		Order_ensure = (Button) findViewById(R.id.Order_ensure);
		Order_ensure.setOnClickListener(this);
		Order_cancel = (Button) findViewById(R.id.Order_cancel);
		Order_cancel.setOnClickListener(this);
	}

	private Handler myHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
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
		}
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
			CommonUtils.finishActivity(PlaceAnGoodsOrderActivity.this);
			break;
		case R.id.Order_Driver:
			doSelectDriver();
			break;
		case R.id.Order_Car:
			doSelectCar();
			break;
		case R.id.Order_ensure:
			doPlaceAnOrder();
			break;
		case R.id.Order_cancel:
			CommonUtils.finishActivity(PlaceAnGoodsOrderActivity.this);
			break;

		default:
			break;
		}
	}

	/**
	 * 选择车辆
	 */
	private void doSelectCar() {
		Intent intent = new Intent(PlaceAnGoodsOrderActivity.this,
				SelectCarManagerActivity.class);
		startActivityForResult(intent, REFRESH_CAR);
	}

	/**
	 * 选择司机
	 */
	private void doSelectDriver() {
		Intent intent = new Intent(PlaceAnGoodsOrderActivity.this,
				SelectDriverManagerActivity.class);
		startActivityForResult(intent, REFRESH_DRIVER);
	}

	@Override
	public void onClickListener(View view) {

	}

	/**
	 * 下单
	 */
	private void doPlaceAnOrder() {
		String result = isCanPlaceOrder();
		if (result.equalsIgnoreCase("成功")) {
			myHandler.sendEmptyMessage(SHOW_PROGRESS);
			mOrderDto
					.setTransAmount(BigDecimal.valueOf(Double
							.parseDouble(GoodsManager_GoodsWeight.getText()
									.toString())));
			mOrderDto.setDriver(mDriverDto);
			mOrderDto.setCars(mCarsDto);
			mOrderDto.setGoods(mGoodsDto);
			mOrderDto.setCommand("CreateOrderByVehicle");

			PdaRequest<OrderDto> request = new PdaRequest<OrderDto>();
			request.setData(mOrderDto);
			PlaceAnOrderHandler dataHandler = new PlaceAnOrderHandler(context,
					request);
			dataHandler.setOnDataReceiveListener(this);
			dataHandler.startNetWork();
		} else {
			ToastUtil.show(context, result);
		}
	}

	@Override
	public void onDataReceive(DataHandler dataHandler, int resultCode,
			Object data, int type) {
		myHandler.sendEmptyMessage(CLOSE_PROGRESS);
		switch (resultCode) {
		case NetWork.PLACE_AN_ORDER_OK:
			doPlaceAnOrderSuccess(data);
			break;
		case NetWork.PLACE_AN_ORDER_ERROR:
			ToastUtil.show(context,
					getResources().getString(R.string.network_error_hint));
			break;

		default:
			break;
		}
	}

	/**
	 * 订单申请成功
	 * 
	 * @param data
	 */
	private void doPlaceAnOrderSuccess(Object data) {
		String dataString = null;
		try {
			dataString = new String((byte[]) data, "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			PdaResponse<String> response = ResultCodeJsonParser
					.parserResultCodeJson(dataString);
			if (response.isSuccess()) {
				ToastUtil.show(context, "已报价，请等待对方确认");
				Intent intent = new Intent(PlaceAnGoodsOrderActivity.this,
						MyOrderManagerActivity.class);
				intent.putExtra("isNomalGetIn", false);
				startActivity(intent);
				CommonUtils.finishAllActivity();
			} else {

				String result = response.getMessage();
				int messageCode = Integer.parseInt(result.substring(0,
						result.indexOf("#")));
				String message = "";
				message = result.substring(result.indexOf("#") + 1,
						result.length());
				Message msg = myHandler.obtainMessage();
				msg.what = SHOW_TOAST;
				msg.obj = message;
				myHandler.sendMessage(msg);

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode != RESULT_OK)
			return;
		switch (requestCode) {
		case REFRESH_DRIVER:
			mDriverDto = (DriverDto) data.getSerializableExtra("driverInfo");
			if (mDriverDto != null)
				Order_Driver.setText(mDriverDto.getDriverName());
			break;
		case REFRESH_CAR:
			mCarsDto = (CarsDto) data.getSerializableExtra("carInfo");
			if (mCarsDto != null)
				Order_Car.setText(mCarsDto.getVehicleNum());
			break;

		default:
			break;
		}
	}

	abstract class Filter {

		Filter next = null;

		public Filter getNext() {

			return next;

		}

		public void setNext(Filter next) {

			this.next = next;

		}

		public String doFilter(String goodsName, String driver, String car,
				String price) {

			if (next == null) {
				return "成功";
			} else
				return next.doFilter(goodsName, driver, car, price);
		}
	}

	class GoodsNameFilter extends Filter {
		@Override
		public String doFilter(String goodsName, String driver, String car,
				String price) {

			if (TextUtils.isEmpty(goodsName)) {
				return "请输入正确的货物名称";
			} else {
				return super.doFilter(goodsName, driver, car, price);
			}
		}
	}

	class DriverFilter extends Filter {
		@Override
		public String doFilter(String goodsName, String driver, String car,
				String price) {

			if (TextUtils.isEmpty(driver)) {
				return "请选择正确的司机";
			} else {
				return super.doFilter(goodsName, driver, car, price);
			}
		}
	}

	class CarFilter extends Filter {
		@Override
		public String doFilter(String goodsName, String driver, String car,
				String price) {

			if (TextUtils.isEmpty(car)) {
				return "请选择正确的车辆";
			} else {
				return super.doFilter(goodsName, driver, car, price);
			}
		}
	}

	class PriceFilter extends Filter {
		@Override
		public String doFilter(String goodsName, String driver, String car,
				String price) {

			if (TextUtils.isEmpty(price)) {
				return "请输入运费";
			} else {
				return super.doFilter(goodsName, driver, car, price);
			}
		}
	}

	private String isCanPlaceOrder() {
		String result = null;
		Filter goodsName = new GoodsNameFilter();
		Filter driver = new DriverFilter();
		Filter car = new CarFilter();
		Filter price = new PriceFilter();
		goodsName.setNext(driver);
		driver.setNext(car);
		car.setNext(price);
		result = goodsName.doFilter(Order_GoodsName.getText().toString(),
				Order_Driver.getText().toString(), Order_Car.getText()
						.toString(), GoodsManager_GoodsWeight.getText()
						.toString());

		return result;
	}

}
