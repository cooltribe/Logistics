package com.seeyuan.logistics.activity;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TimePicker;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.seeyuan.logistics.R;
import com.seeyuan.logistics.application.NetWork;
import com.seeyuan.logistics.customview.MuInputEditText;
import com.seeyuan.logistics.customview.ProgressAlertDialog;
import com.seeyuan.logistics.customview.SingleSelectAlertDlialog;
import com.seeyuan.logistics.datacenter.DataHandler;
import com.seeyuan.logistics.datacenter.OnDataReceiveListener;
import com.seeyuan.logistics.datahandler.PublishGoodsSourceHandler;
import com.seeyuan.logistics.entity.CarLengthInfo;
import com.seeyuan.logistics.entity.CarTypeInfo;
import com.seeyuan.logistics.entity.CarsDto;
import com.seeyuan.logistics.entity.GoodsDto;
import com.seeyuan.logistics.entity.PdaRequest;
import com.seeyuan.logistics.entity.PdaResponse;
import com.seeyuan.logistics.jsonparser.CarSourceJsonParser;
import com.seeyuan.logistics.jsonparser.ResultCodeJsonParser;
import com.seeyuan.logistics.util.CommonUtils;
import com.seeyuan.logistics.util.ToastUtil;

/**
 * 添加新货源
 * 
 * @author zhazhaobao
 * 
 */
@SuppressLint("HandlerLeak")
public class AddNewGoodsManagerActivity extends BaseActivity implements
		OnClickListener, OnDataReceiveListener {

	/**
	 * 返回按钮
	 */
	private ImageView maintitle_back_iv;

	/**
	 * 标题title
	 */
	private TextView defaulttitle_title_tv;

	/**
	 * 货物名称
	 */
	private MuInputEditText PublishGoods_GoodsName;

	/**
	 * 货物类型
	 */
	private Button PublishGoods_GoodsType;

	/**
	 * 货物重量
	 */
	private MuInputEditText PublishGoods_GoodsWeight;

	/**
	 * 货物体积
	 */
	private MuInputEditText PublishGoods_GoodsBulk;

	/**
	 * 车辆类型
	 */
	private Button PublishGoods_CasType;

	/**
	 * 车辆长度
	 */
	private Button PublishGoods_CasLength;

	/**
	 * 包装
	 */
	private Button PublishGoods_packaging;

	/**
	 * 运输价格
	 */
	private MuInputEditText PublishGoods_TransportPrices;

	/**
	 * 始发地
	 */
	private Button PublishGoods_Origin;

	/**
	 * 目的地
	 */
	private Button PublishGoods_Destination;

	/**
	 * 装货时间
	 */
	private CheckBox PublishGoods_shipment_time;

	/**
	 * 装货时间，布局
	 */
	private LinearLayout PublishGoods_shipment_time_layout;

	private Button PublishGoods_shipment_time_start;

	private Button PublishGoods_shipment_time_end;

	/**
	 * 收货时间
	 */
	private CheckBox PublishGoods_receipt_time;

	/**
	 * 收货时间，布局
	 */
	private LinearLayout PublishGoods_receipt_time_layout;

	private Button PublishGoods_receipt_time_start;

	private Button PublishGoods_receipt_time_end;

	/**
	 * 有效时间
	 */
	private Button PublishGoods_valid_time;

	/**
	 * 联系人
	 */
	private MuInputEditText PublishGoods_Contact;

	/**
	 * 手机号码
	 */
	private MuInputEditText PublishGoods_phone;

	/**
	 * 固定号码
	 */
	private MuInputEditText PublishGoods_telephone;

	/**
	 * 公司名称
	 */
	private MuInputEditText PublishGoods_company;

	/**
	 * 备注
	 */
	private MuInputEditText PublishGoods_remark;

	/**
	 * 收货方支付
	 */
	private RadioButton radio_pay1;

	/**
	 * 发货方支付
	 */
	private RadioButton radio_pay2;

	/**
	 * 平台担保支付
	 */
	private RadioButton radio_pay3;

	/**
	 * 是否需要发票。是
	 */
	private RadioButton radio_invoice1;

	/**
	 * 是否需要发票。否
	 */
	private RadioButton radio_invoice2;

	/**
	 * 发票抬头
	 */
	private RelativeLayout PublishGoods_invoice_header_layout;

	private MuInputEditText PublishGoods_invoice_header;

	/**
	 * 刷新货物类型
	 */
	private final int REFRESH_GOODS_TYPE = 1000;

	/**
	 * 刷新运输方式
	 */
	private final int REFRESH_TRANSPORT_MODE = 1001;

	/**
	 * 刷新车辆类型
	 */
	private final int REFRESH_CAR_TYPE = 1002;

	/**
	 * 刷新车辆长度
	 */
	private final int REFRESH_CAR_LENGTH = 1003;

	/**
	 * 始发地，数据返回
	 */
	private final int GOODS_ORIGIN_RETURN = 2000;

	/**
	 * 目的地，数据返回
	 */
	private final int GOODS_DESTINATION_RETURN = 2001;

	/**
	 * 刷新有效时间
	 */
	private final int REFRESH_VAILD_TIME = 2002;

	private Context context;

	private GoodsDto mGoodsDto = new GoodsDto();

	private static final int SHOW_DATAPICK = 0;
	private static final int DATE_DIALOG_ID = 1;
	private static final int SHOW_TIMEPICK = 2;
	private static final int TIME_DIALOG_ID = 3;

	private int mYear;
	private int mMonth;
	private int mDay;
	private int mHour;
	private int mMinute;

	private int timeSelectID = -1;

	/**
	 * 显示进度条
	 */
	private final int SHOW_PROGRESS = 2000;
	/**
	 * 关闭进度条
	 */
	private final int CLOSE_PROGRESS = 2001;

	private final int SHOW_TOAST = 3002;

	private ProgressAlertDialog progressDialog;

	private RadioButton radio_transport1;

	private RadioButton radio_transport2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.activity_add_new_goods_manager); // 软件activity的布局
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,
				R.layout.deflaut_titlebar); // titlebar为自己标题栏的布局
		context = getApplicationContext();
		initView();
		setDateTime();
		setTimeOfDay();
	}

	@Override
	public void initView() {

		maintitle_back_iv = (ImageView) findViewById(R.id.maintitle_back_iv);
		maintitle_back_iv.setOnClickListener(this);

		defaulttitle_title_tv = (TextView) findViewById(R.id.defaulttitle_title_tv);
		defaulttitle_title_tv.setText(R.string.add_goods_manage_hint);

		PublishGoods_GoodsName = (MuInputEditText) findViewById(R.id.PublishGoods_GoodsName);
		PublishGoods_GoodsType = (Button) findViewById(R.id.PublishGoods_GoodsType);
		PublishGoods_GoodsWeight = (MuInputEditText) findViewById(R.id.PublishGoods_GoodsWeight);
		PublishGoods_GoodsBulk = (MuInputEditText) findViewById(R.id.PublishGoods_GoodsBulk);

		PublishGoods_CasType = (Button) findViewById(R.id.PublishGoods_CasType);
		PublishGoods_CasLength = (Button) findViewById(R.id.PublishGoods_CasLength);
		PublishGoods_packaging = (Button) findViewById(R.id.PublishGoods_packaging);

		PublishGoods_shipment_time_layout = (LinearLayout) findViewById(R.id.PublishGoods_shipment_time_layout);
		PublishGoods_receipt_time_layout = (LinearLayout) findViewById(R.id.PublishGoods_receipt_time_layout);

		PublishGoods_TransportPrices = (MuInputEditText) findViewById(R.id.PublishGoods_TransportPrices);
		PublishGoods_Origin = (Button) findViewById(R.id.PublishGoods_Origin);
		PublishGoods_Destination = (Button) findViewById(R.id.PublishGoods_Destination);
		PublishGoods_shipment_time = (CheckBox) findViewById(R.id.PublishGoods_shipment_time);
		PublishGoods_shipment_time
				.setOnCheckedChangeListener(new OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(CompoundButton buttonView,
							boolean isChecked) {
						if (isChecked) {
							PublishGoods_shipment_time_layout
									.setVisibility(View.GONE);
						} else {
							PublishGoods_shipment_time_layout
									.setVisibility(View.VISIBLE);
						}
					}
				});
		PublishGoods_receipt_time = (CheckBox) findViewById(R.id.PublishGoods_receipt_time);
		PublishGoods_receipt_time
				.setOnCheckedChangeListener(new OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(CompoundButton buttonView,
							boolean isChecked) {

						if (isChecked) {
							PublishGoods_receipt_time_layout
									.setVisibility(View.GONE);
						} else {
							PublishGoods_receipt_time_layout
									.setVisibility(View.VISIBLE);
						}

					}
				});
		PublishGoods_valid_time = (Button) findViewById(R.id.PublishGoods_valid_time);
		PublishGoods_Contact = (MuInputEditText) findViewById(R.id.PublishGoods_Contact);
		PublishGoods_phone = (MuInputEditText) findViewById(R.id.PublishGoods_phone);

		PublishGoods_telephone = (MuInputEditText) findViewById(R.id.PublishGoods_telephone);
		PublishGoods_company = (MuInputEditText) findViewById(R.id.PublishGoods_company);
		PublishGoods_remark = (MuInputEditText) findViewById(R.id.PublishGoods_remark);

		radio_pay1 = (RadioButton) findViewById(R.id.radio_pay1);
		radio_pay1.setChecked(true);
		radio_pay2 = (RadioButton) findViewById(R.id.radio_pay2);
		radio_pay3 = (RadioButton) findViewById(R.id.radio_pay3);

		PublishGoods_shipment_time_start = (Button) findViewById(R.id.PublishGoods_shipment_time_start);
		PublishGoods_shipment_time_end = (Button) findViewById(R.id.PublishGoods_shipment_time_end);
		PublishGoods_receipt_time_start = (Button) findViewById(R.id.PublishGoods_receipt_time_start);
		PublishGoods_receipt_time_end = (Button) findViewById(R.id.PublishGoods_receipt_time_end);

		PublishGoods_invoice_header_layout = (RelativeLayout) findViewById(R.id.PublishGoods_invoice_header_layout);
		PublishGoods_invoice_header = (MuInputEditText) findViewById(R.id.PublishGoods_invoice_header);
		radio_invoice1 = (RadioButton) findViewById(R.id.radio_invoice1);
		radio_invoice1
				.setOnCheckedChangeListener(new OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(CompoundButton buttonView,
							boolean isChecked) {
						if (isChecked) {
							PublishGoods_invoice_header_layout
									.setVisibility(View.VISIBLE);
						}
					}
				});
		radio_invoice2 = (RadioButton) findViewById(R.id.radio_invoice2);
		radio_invoice2.setChecked(true);
		radio_invoice2
				.setOnCheckedChangeListener(new OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(CompoundButton buttonView,
							boolean isChecked) {
						if (isChecked) {
							PublishGoods_invoice_header_layout
									.setVisibility(View.GONE);
						}
					}
				});
		
		radio_transport1 = (RadioButton) findViewById(R.id.radio_transport1);
		radio_transport2 = (RadioButton) findViewById(R.id.radio_transport2);

	}
	private String getDisMode() {
		if (radio_transport1.isChecked()) {
			return radio_transport1.getText().toString();
		} else if (radio_transport2.isChecked()) {
			return radio_transport2.getText().toString();
		}
		return "";
	}
	private Handler myHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case REFRESH_GOODS_TYPE:
				PublishGoods_GoodsType.setText(msg.obj.toString());
				break;
			case REFRESH_TRANSPORT_MODE:
				// PublishGoods_TransportMode.setText(msg.obj.toString());
				break;
			case REFRESH_CAR_TYPE:
				PublishGoods_CasType.setText(msg.obj.toString());
				break;
			case REFRESH_CAR_LENGTH:
				PublishGoods_CasLength.setText(msg.obj.toString() + "米");
				break;
			case REFRESH_VAILD_TIME:
				// PublishInfo_EffectiveDate.setText(msg.obj.toString() + "天");
				break;
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
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.maintitle_back_iv:
			CommonUtils.finishActivity(AddNewGoodsManagerActivity.this);
			break;

		default:
			break;
		}
	}

	@Override
	public void onClickListener(View view) {

		switch (view.getId()) {
		case R.id.PublishGoods_GoodsType:
			doGoodsType();
			break;
		case R.id.PublishGoods_CasType:
			doSearchCarType();
			break;
		case R.id.PublishGoods_CasLength:
			doSearchCarLength();
			break;
		case R.id.PublishGoods_packaging:

			break;
		case R.id.PublishGoods_Origin:
			doGoodsOrigin();
			break;
		case R.id.PublishGoods_Destination:
			doGoodsDestination();
			break;
		case R.id.PublishGoods_valid_time:
			// doVaildTime();
			doSelectDayData(view.getId());
			break;
		case R.id.PublishGoods_ensure:
			String result = isCanSubmitGoodsSource();
			if (result.equalsIgnoreCase("成功")) {
				new Thread(new Runnable() {

					@Override
					public void run() {
						doSubmitPublishGoods();
					}
				}).start();
			} else {
				ToastUtil.show(context, result);
			}
			break;
		case R.id.PublishGoods_cancel:
			CommonUtils.finishActivity(getParent());
			break;
		case R.id.PublishGoods_shipment_time_start:
			doSelectDayData(view.getId());
			break;
		case R.id.PublishGoods_shipment_time_end:
			doSelectDayData(view.getId());
			break;
		case R.id.PublishGoods_receipt_time_start:
			doSelectDayData(view.getId());
			break;
		case R.id.PublishGoods_receipt_time_end:
			doSelectDayData(view.getId());
			break;

		default:
			break;
		}

	}

	/**
	 * 发布货源
	 */
	private void doSubmitPublishGoods() {
		myHandler.sendEmptyMessage(SHOW_PROGRESS);
		mGoodsDto.setGoodsName(PublishGoods_GoodsName.getText().toString());
		mGoodsDto.setGoodsType(PublishGoods_GoodsType.getText().toString());
		mGoodsDto.setGoodsWeight(BigDecimal.valueOf(Double
				.parseDouble(PublishGoods_GoodsWeight.getText().toString())));
		mGoodsDto.setGoodsVolume(Double.valueOf(PublishGoods_GoodsBulk
				.getText().toString()));
		mGoodsDto.setVehType(PublishGoods_CasType.getText().toString());
		mGoodsDto.setVehLegth(PublishGoods_CasLength.getText().toString());
		mGoodsDto.setPackages(PublishGoods_packaging.getText().toString());
		mGoodsDto
				.setCost(BigDecimal.valueOf(Double
						.parseDouble(PublishGoods_TransportPrices.getText()
								.toString())));
		mGoodsDto.setSetout(PublishGoods_Origin.getText().toString());
		mGoodsDto.setDestination(PublishGoods_Destination.getText().toString());
		// 装货日期，收货日期
		if (!PublishGoods_shipment_time.isChecked()) {
			try {
				mGoodsDto.setDeliveryDateF(new SimpleDateFormat("yyyy-MM-dd")
						.parse(PublishGoods_shipment_time_start.getText()
								.toString()));
				mGoodsDto.setDeliveryDateT(new SimpleDateFormat("yyyy-MM-dd")
						.parse(PublishGoods_shipment_time_end.getText()
								.toString()));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		} else {
			mGoodsDto.setDeliveryDateF(null);
			mGoodsDto.setDeliveryDateT(null);
		}

		if (!PublishGoods_receipt_time.isChecked()) {
			try {
				mGoodsDto.setReceiveDateF(new SimpleDateFormat("yyyy-MM-dd")
						.parse(PublishGoods_receipt_time_start.getText()
								.toString()));
				mGoodsDto.setReceiveDateT(new SimpleDateFormat("yyyy-MM-dd")
						.parse(PublishGoods_receipt_time_end.getText()
								.toString()));
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			mGoodsDto.setReceiveDateF(null);
			mGoodsDto.setReceiveDateT(null);
		}

		mGoodsDto.setValidDeadline(CommonUtils
				.parserTimestamp(PublishGoods_valid_time.getText().toString()));
		mGoodsDto.setContactName(PublishGoods_Contact.getText().toString());
		mGoodsDto.setmPhone(PublishGoods_phone.getText().toString());
		mGoodsDto.setfPhone(PublishGoods_telephone.getText().toString());
		mGoodsDto.setCompanyName(PublishGoods_company.getText().toString());
		mGoodsDto.setRemark(PublishGoods_remark.getText().toString());
		mGoodsDto.setPayType(getPayType());

		mGoodsDto.setIsNeedInvoice(getInvoice());
		mGoodsDto.setInvoiceTitle(PublishGoods_invoice_header.getText()
				.toString());
		mGoodsDto.setDisMode(getDisMode());
		
		PdaRequest<GoodsDto> request = new PdaRequest<GoodsDto>();
		request.setData(mGoodsDto);
		PublishGoodsSourceHandler dataHandler = new PublishGoodsSourceHandler(
				context, request);
		dataHandler.setOnDataReceiveListener(this);
		dataHandler.startNetWork();
	}

	private String getPayType() {
		if (radio_pay1.isChecked()) {
			return "3";
		} else if (radio_pay2.isChecked()) {
			return "2";
		} else if (radio_pay3.isChecked()) {
			return "1";
		}
		return "0";
	}

	private String getInvoice() {
		if (radio_invoice1.isChecked()) {
			return "Y";
		} else if (radio_invoice2.isChecked()) {
			return "N";
		}
		return "0";
	}

	private String isCanSubmitGoodsSource() {
		Filter goodsName = new goodsNameFilder();
		Filter goodsType = new goodsTypeFilder();
		Filter goodsWeight = new goodsWeightFilder();
		Filter carType = new carTypeFilder();
		Filter carBulk = new carBulkFilder();
		Filter carLength = new carLengthFilder();
		Filter transportPrice = new transportPriceFilder();
		Filter targetFrom = new targetFromFilder();
		Filter targetTo = new targetToFilder();
		Filter uesrPhone = new userPhoneFilder();
		Filter invoiceHeader = new InvoiceHeaderFilder();

		goodsName.setNext(goodsType);
		goodsType.setNext(goodsWeight);
		goodsWeight.setNext(carType);
		carType.setNext(carBulk);
		carBulk.setNext(carLength);
		carLength.setNext(transportPrice);
		transportPrice.setNext(targetFrom);
		targetFrom.setNext(targetTo);
		targetTo.setNext(uesrPhone);

		if (radio_invoice1.isChecked()) {
			uesrPhone.setNext(invoiceHeader);
		}

		String result = goodsName.doFilter(PublishGoods_GoodsName.getText()
				.toString(), PublishGoods_GoodsType.getText().toString(),
				PublishGoods_GoodsWeight.getText().toString(),
				PublishGoods_CasType.getText().toString(),
				PublishGoods_GoodsBulk.getText().toString(),
				PublishGoods_CasType.getText().toString(),
				PublishGoods_CasLength.getText().toString(),
				PublishGoods_Origin.getText().toString(),
				PublishGoods_Destination.getText().toString(),
				PublishGoods_phone.getText().toString(),
				PublishGoods_invoice_header.getText().toString());

		return result;
	}

	abstract class Filter {

		Filter next = null;

		public Filter getNext() {

			return next;

		}

		public void setNext(Filter next) {

			this.next = next;

		}

		public String doFilter(String goodsName, String goodsType,
				String goodsWeight, String carType, String carBulk,
				String carLength, String transportPrice, String targetFrom,
				String targetTo, String uesrPhone, String invoiceHeader) {

			if (next == null) {
				return "成功";
			} else
				return next.doFilter(goodsName, goodsType, goodsWeight,
						carType, carBulk, carLength, transportPrice,
						targetFrom, targetTo, uesrPhone, invoiceHeader);

		}

	}

	class goodsNameFilder extends Filter {
		@Override
		public String doFilter(String goodsName, String goodsType,
				String goodsWeight, String carType, String carBulk,
				String carLength, String transportPrice, String targetFrom,
				String targetTo, String uesrPhone, String invoiceHeader) {

			if (TextUtils.isEmpty(goodsName)) {
				return "请输入正确的货物名称";
			} else {
				return super.doFilter(goodsName, goodsType, goodsWeight,
						carType, carBulk, carLength, transportPrice,
						targetFrom, targetTo, uesrPhone, invoiceHeader);
			}
		}
	}

	class goodsTypeFilder extends Filter {
		@Override
		public String doFilter(String goodsName, String goodsType,
				String goodsWeight, String carType, String carBulk,
				String carLength, String transportPrice, String targetFrom,
				String targetTo, String uesrPhone, String invoiceHeader) {

			if (TextUtils.isEmpty(goodsType)) {
				return "请输入正确的货物类型";
			} else {
				return super.doFilter(goodsName, goodsType, goodsWeight,
						carType, carBulk, carLength, transportPrice,
						targetFrom, targetTo, uesrPhone, invoiceHeader);
			}
		}
	}

	class goodsWeightFilder extends Filter {
		@Override
		public String doFilter(String goodsName, String goodsType,
				String goodsWeight, String carType, String carBulk,
				String carLength, String transportPrice, String targetFrom,
				String targetTo, String uesrPhone, String invoiceHeader) {

			if (TextUtils.isEmpty(goodsWeight)) {
				return "请输入正确的货物重量";
			} else {
				return super.doFilter(goodsName, goodsType, goodsWeight,
						carType, carBulk, carLength, transportPrice,
						targetFrom, targetTo, uesrPhone, invoiceHeader);
			}
		}
	}

	class carTypeFilder extends Filter {
		@Override
		public String doFilter(String goodsName, String goodsType,
				String goodsWeight, String carType, String carBulk,
				String carLength, String transportPrice, String targetFrom,
				String targetTo, String uesrPhone, String invoiceHeader) {

			if (TextUtils.isEmpty(carType)) {
				return "请输入正确的车辆类型";
			} else {
				return super.doFilter(goodsName, goodsType, goodsWeight,
						carType, carBulk, carLength, transportPrice,
						targetFrom, targetTo, uesrPhone, invoiceHeader);
			}
		}
	}

	class carBulkFilder extends Filter {
		@Override
		public String doFilter(String goodsName, String goodsType,
				String goodsWeight, String carType, String carBulk,
				String carLength, String transportPrice, String targetFrom,
				String targetTo, String uesrPhone, String invoiceHeader) {

			if (TextUtils.isEmpty(carBulk)) {
				return "请输入正确的货物体积";
			} else {
				return super.doFilter(goodsName, goodsType, goodsWeight,
						carType, carBulk, carLength, transportPrice,
						targetFrom, targetTo, uesrPhone, invoiceHeader);
			}
		}
	}

	class carLengthFilder extends Filter {
		@Override
		public String doFilter(String goodsName, String goodsType,
				String goodsWeight, String carType, String carBulk,
				String carLength, String transportPrice, String targetFrom,
				String targetTo, String uesrPhone, String invoiceHeader) {

			if (TextUtils.isEmpty(carLength)) {
				return "请输入正确的车辆长度";
			} else {
				return super.doFilter(goodsName, goodsType, goodsWeight,
						carType, carBulk, carLength, transportPrice,
						targetFrom, targetTo, uesrPhone, invoiceHeader);
			}
		}
	}

	class transportPriceFilder extends Filter {
		@Override
		public String doFilter(String goodsName, String goodsType,
				String goodsWeight, String carType, String carBulk,
				String carLength, String transportPrice, String targetFrom,
				String targetTo, String uesrPhone, String invoiceHeader) {

			if (TextUtils.isEmpty(transportPrice)) {
				return "请输入正确的运输价格";
			} else {
				return super.doFilter(goodsName, goodsType, goodsWeight,
						carType, carBulk, carLength, transportPrice,
						targetFrom, targetTo, uesrPhone, invoiceHeader);
			}
		}
	}

	class targetFromFilder extends Filter {
		@Override
		public String doFilter(String goodsName, String goodsType,
				String goodsWeight, String carType, String carBulk,
				String carLength, String transportPrice, String targetFrom,
				String targetTo, String uesrPhone, String invoiceHeader) {

			if (TextUtils.isEmpty(targetFrom)) {
				return "请输入正确的始发地";
			} else {
				return super.doFilter(goodsName, goodsType, goodsWeight,
						carType, carBulk, carLength, transportPrice,
						targetFrom, targetTo, uesrPhone, invoiceHeader);
			}
		}
	}

	class targetToFilder extends Filter {
		@Override
		public String doFilter(String goodsName, String goodsType,
				String goodsWeight, String carType, String carBulk,
				String carLength, String transportPrice, String targetFrom,
				String targetTo, String uesrPhone, String invoiceHeader) {

			if (TextUtils.isEmpty(targetTo)) {
				return "请输入正确的目的地";
			} else {
				return super.doFilter(goodsName, goodsType, goodsWeight,
						carType, carBulk, carLength, transportPrice,
						targetFrom, targetTo, uesrPhone, invoiceHeader);
			}
		}
	}

	class InvoiceHeaderFilder extends Filter {
		@Override
		public String doFilter(String goodsName, String goodsType,
				String goodsWeight, String carType, String carBulk,
				String carLength, String transportPrice, String targetFrom,
				String targetTo, String uesrPhone, String invoiceHeader) {

			if (TextUtils.isEmpty(invoiceHeader)) {
				return "请输入正确的发票抬头";
			} else {
				return super.doFilter(goodsName, goodsType, goodsWeight,
						carType, carBulk, carLength, transportPrice,
						targetFrom, targetTo, uesrPhone, invoiceHeader);
			}
		}
	}

	// 装货时间

	// 收货时间

	class userPhoneFilder extends Filter {
		@Override
		public String doFilter(String goodsName, String goodsType,
				String goodsWeight, String carType, String carBulk,
				String carLength, String transportPrice, String targetFrom,
				String targetTo, String uesrPhone, String invoiceHeader) {

			if (TextUtils.isEmpty(uesrPhone)) {
				return "请输入正确的手机号码";
			} else {
				return super.doFilter(goodsName, goodsType, goodsWeight,
						carType, carBulk, carLength, transportPrice,
						targetFrom, targetTo, uesrPhone, invoiceHeader);
			}
		}
	}

	// 支付方式
	/**
	 * 有效时间
	 */
	private void doVaildTime() {

		final List<String> mDataList = new ArrayList<String>();
		TypedArray typedArray = getResources().obtainTypedArray(
				R.array.Valid_time);
		int size = typedArray.length();
		for (int i = 0; i < size; i++) {
			mDataList.add(typedArray.getString(i));
		}

		final SingleSelectAlertDlialog ad = new SingleSelectAlertDlialog(
				AddNewGoodsManagerActivity.this);
		ad.setTitle(getResources().getString(
				R.string.PublishInfo_EffectiveDateHint2));
		ad.setListContentForNormalText(mDataList);
		ad.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View view,
					int position, long arg3) {
				if (null == view)
					return;
				ad.dismiss();
				Message message = myHandler.obtainMessage();
				message.what = REFRESH_VAILD_TIME;
				message.obj = mDataList.get((int) arg3);
				myHandler.sendMessage(message);

			}
		});
		typedArray.recycle();
	}

	/**
	 * 执行始发地
	 */
	private void doGoodsOrigin() {
		Intent intent = new Intent(AddNewGoodsManagerActivity.this,
				SearchCityActivity.class);
		startActivityForResult(intent, GOODS_ORIGIN_RETURN);
	}

	/**
	 * 执行目的地
	 */
	private void doGoodsDestination() {
		Intent intent = new Intent(AddNewGoodsManagerActivity.this,
				SearchCityActivity.class);
		startActivityForResult(intent, GOODS_DESTINATION_RETURN);
	}

	/**
	 * 车型选择
	 */
	private void doSearchCarType() {

		List<CarTypeInfo> mDataList = new ArrayList<CarTypeInfo>();
		TypedArray typedArray = getResources().obtainTypedArray(
				R.array.CarType_Str);
		int size = typedArray.length();
		for (int i = 0; i < size; i++) {
			CarTypeInfo indexInfo = new CarTypeInfo();
			indexInfo.setCar_type(typedArray.getString(i));
			mDataList.add(indexInfo);
		}

		final SingleSelectAlertDlialog ad = new SingleSelectAlertDlialog(
				AddNewGoodsManagerActivity.this);
		ad.setTitle(getResources()
				.getString(R.string.PublishInfo_CarTypes_Hint));
		ad.setListContentForCarType(mDataList);
		ad.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View view, int arg2,
					long arg3) {
				if (null == view)
					return;
				ad.dismiss();
				Message message = myHandler.obtainMessage();
				message.what = REFRESH_CAR_TYPE;
				message.obj = ((TextView) view.findViewById(R.id.item_car_type))
						.getText();
				myHandler.sendMessage(message);

			}
		});
		// ad.setPositiveButton("确定", new OnClickListener() {
		//
		// @Override
		// public void onClick(View v) {
		// ad.dismiss();
		// Toast.makeText(HaveCarActivity.this, "被点到确定", Toast.LENGTH_LONG)
		// .show();
		//
		// }
		// });

		// ad.setNegativeButton("取消", new OnClickListener() {
		//
		// @Override
		// public void onClick(View v) {
		// ad.dismiss();
		// Toast.makeText(HaveCarActivity.this, "被点到取消", Toast.LENGTH_LONG)
		// .show();
		// }
		// });
		typedArray.recycle();
	}

	/**
	 * 车长选择
	 */
	private void doSearchCarLength() {

		List<CarLengthInfo> mDataList = new ArrayList<CarLengthInfo>();
		TypedArray typedArray = getResources().obtainTypedArray(
				R.array.Search_car_length);
		int size = typedArray.length();
		for (int i = 0; i < size; i++) {
			CarLengthInfo indexInfo = new CarLengthInfo();
			indexInfo.setCar_Length(typedArray.getString(i));
			mDataList.add(indexInfo);
		}

		final SingleSelectAlertDlialog ad = new SingleSelectAlertDlialog(
				AddNewGoodsManagerActivity.this);
		ad.setTitle(getResources().getString(R.string.PublishInfo_Carlen_Hint));
		ad.setListContentForCarLength(mDataList);
		ad.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View view, int arg2,
					long arg3) {
				if (null == view)
					return;
				ad.dismiss();
				Message message = myHandler.obtainMessage();
				message.what = REFRESH_CAR_LENGTH;
				message.obj = ((TextView) view
						.findViewById(R.id.item_car_length)).getText();
				myHandler.sendMessage(message);

			}
		});
		typedArray.recycle();
	}

	/**
	 * 货物类型
	 */
	private void doGoodsType() {

		List<CarLengthInfo> mDataList = new ArrayList<CarLengthInfo>();
		TypedArray typedArray = getResources().obtainTypedArray(
				R.array.Goods_Type_Name);
		int size = typedArray.length();
		for (int i = 0; i < size; i++) {
			CarLengthInfo indexInfo = new CarLengthInfo();
			indexInfo.setCar_Length(typedArray.getString(i));
			mDataList.add(indexInfo);
		}

		final SingleSelectAlertDlialog ad = new SingleSelectAlertDlialog(
				AddNewGoodsManagerActivity.this);
		ad.setTitle(getResources().getString(
				R.string.PublishInfo_GoodsType_Hint));
		ad.setListContentForCarLength(mDataList);
		ad.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View view, int arg2,
					long arg3) {
				if (null == view)
					return;
				ad.dismiss();
				Message message = myHandler.obtainMessage();
				message.what = REFRESH_GOODS_TYPE;
				message.obj = ((TextView) view
						.findViewById(R.id.item_car_length)).getText();
				myHandler.sendMessage(message);

			}
		});
		typedArray.recycle();
	}

	/**
	 * 运输方式
	 */
	private void doTransportMode() {

		List<CarLengthInfo> mDataList = new ArrayList<CarLengthInfo>();
		TypedArray typedArray = getResources().obtainTypedArray(
				R.array.Transport_Mode);
		int size = typedArray.length();
		for (int i = 0; i < size; i++) {
			CarLengthInfo indexInfo = new CarLengthInfo();
			indexInfo.setCar_Length(typedArray.getString(i));
			mDataList.add(indexInfo);
		}

		final SingleSelectAlertDlialog ad = new SingleSelectAlertDlialog(
				AddNewGoodsManagerActivity.this);
		ad.setTitle(getResources().getString(
				R.string.PublishInfo_TransportMode_Hint));
		ad.setListContentForCarLength(mDataList);
		ad.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View view, int arg2,
					long arg3) {
				if (null == view)
					return;
				ad.dismiss();
				Message message = myHandler.obtainMessage();
				message.what = REFRESH_TRANSPORT_MODE;
				message.obj = ((TextView) view
						.findViewById(R.id.item_car_length)).getText();
				myHandler.sendMessage(message);

			}
		});
		typedArray.recycle();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode != RESULT_OK)
			return;
		switch (requestCode) {
		case GOODS_ORIGIN_RETURN:
			PublishGoods_Origin.setText(data.getStringExtra("place"));
			break;
		case GOODS_DESTINATION_RETURN:
			PublishGoods_Destination.setText(data.getStringExtra("place"));
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
		case NetWork.PUBLISH_GOODS_SOURCE_OK:
			doPublishGoodsSuccess(data);
			break;
		case NetWork.PUBLISH_GOODS_SOURCE_ERROR:
			ToastUtil.show(context,
					getResources().getString(R.string.network_error_hint));
			break;
		default:
			break;
		}
	}

	private void doPublishGoodsSuccess(Object data) {
		String dataString = null;
		try {
			dataString = new String((byte[]) data, "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			PdaResponse<String> mData = ResultCodeJsonParser
					.parserResultCodeJson(dataString);
			if (!mData.isSuccess()) {
				String result = mData.getMessage();
				int messageCode = Integer.parseInt(result.substring(0,
						result.indexOf("#")));
				String message = "";
				message = result.substring(result.indexOf("#") + 1,
						result.length());
				Message msg = myHandler.obtainMessage();
				msg.what = SHOW_TOAST;
				msg.obj = message;
				myHandler.sendMessage(msg);
			} else {
				ToastUtil.show(context, "发布货源成功");
				Intent intent = new Intent(AddNewGoodsManagerActivity.this,
						GoodsManagerActivity.class);
				intent.putExtra("isNomalGetIn", false);
				startActivity(intent);
				CommonUtils.finishActivity(this);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 日期选择
	 */
	private void doSelectDayData(int timeSelectID) {
		Message msg = new Message();
		msg.what = SHOW_DATAPICK;
		dateandtimeHandler.sendMessage(msg);
		this.timeSelectID = timeSelectID;
	}

	/**
	 * 处理日期和时间控件的Handler
	 */
	Handler dateandtimeHandler = new Handler() {

		@SuppressWarnings("deprecation")
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case SHOW_DATAPICK:
				showDialog(DATE_DIALOG_ID);
				break;
			case SHOW_TIMEPICK:
				showDialog(TIME_DIALOG_ID);
				break;
			}
		}

	};

	/**
	 * 设置时间
	 */
	private void setTimeOfDay() {
		final Calendar c = Calendar.getInstance();
		mHour = c.get(Calendar.HOUR_OF_DAY);
		mMinute = c.get(Calendar.MINUTE);
		// updateTimeDisplay();
	}

	/**
	 * 设置日期
	 */
	private void setDateTime() {
		final Calendar c = Calendar.getInstance();

		mYear = c.get(Calendar.YEAR);
		mMonth = c.get(Calendar.MONTH);
		mDay = c.get(Calendar.DAY_OF_MONTH);

		// updateDateDisplay();
	}

	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case DATE_DIALOG_ID:
			return new DatePickerDialog(this, mDateSetListener, mYear, mMonth,
					mDay);
		case TIME_DIALOG_ID:
			return new TimePickerDialog(this, mTimeSetListener, mHour, mMinute,
					true);
		}

		return null;
	}

	@Override
	protected void onPrepareDialog(int id, Dialog dialog) {
		switch (id) {
		case DATE_DIALOG_ID:
			((DatePickerDialog) dialog).updateDate(mYear, mMonth, mDay);
			break;
		case TIME_DIALOG_ID:
			((TimePickerDialog) dialog).updateTime(mHour, mMinute);
			break;
		}
	}

	/**
	 * 日期控件的事件
	 */
	private DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {

		public void onDateSet(DatePicker view, int year, int monthOfYear,
				int dayOfMonth) {
			mYear = year;
			mMonth = monthOfYear;
			mDay = dayOfMonth;

			updateDateDisplay(timeSelectID);
		}
	};
	/**
	 * 时间控件事件
	 */
	private TimePickerDialog.OnTimeSetListener mTimeSetListener = new TimePickerDialog.OnTimeSetListener() {

		@Override
		public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
			mHour = hourOfDay;
			mMinute = minute;

			// updateTimeDisplay();
		}
	};

	/**
	 * 更新日期显示
	 */
	private void updateDateDisplay(int id) {
		switch (id) {
		case R.id.PublishGoods_shipment_time_start:
			PublishGoods_shipment_time_start.setText(new StringBuilder()
					.append(mYear)
					.append("-")
					.append((mMonth + 1) < 10 ? "0" + (mMonth + 1)
							: (mMonth + 1)).append("-")
					.append((mDay < 10) ? "0" + mDay : mDay));
			break;
		case R.id.PublishGoods_shipment_time_end:
			PublishGoods_shipment_time_end.setText(new StringBuilder()
					.append(mYear)
					.append("-")
					.append((mMonth + 1) < 10 ? "0" + (mMonth + 1)
							: (mMonth + 1)).append("-")
					.append((mDay < 10) ? "0" + mDay : mDay));
			break;
		case R.id.PublishGoods_receipt_time_start:
			PublishGoods_receipt_time_start.setText(new StringBuilder()
					.append(mYear)
					.append("-")
					.append((mMonth + 1) < 10 ? "0" + (mMonth + 1)
							: (mMonth + 1)).append("-")
					.append((mDay < 10) ? "0" + mDay : mDay));
			break;
		case R.id.PublishGoods_receipt_time_end:
			PublishGoods_receipt_time_end.setText(new StringBuilder()
					.append(mYear)
					.append("-")
					.append((mMonth + 1) < 10 ? "0" + (mMonth + 1)
							: (mMonth + 1)).append("-")
					.append((mDay < 10) ? "0" + mDay : mDay));
			break;
		case R.id.PublishGoods_valid_time:
			PublishGoods_valid_time.setText(new StringBuilder()
					.append(mYear)
					.append("-")
					.append((mMonth + 1) < 10 ? "0" + (mMonth + 1)
							: (mMonth + 1)).append("-")
					.append((mDay < 10) ? "0" + mDay : mDay));
			break;

		default:
			break;
		}

	}

	/**
	 * 更新时间显示
	 */
	// private void updateTimeDisplay() {
	// PublishCar_vaild_time.setText(new StringBuilder().append(mYear)
	// .append("-")
	// .append((mMonth + 1) < 10 ? "0" + (mMonth + 1) : (mMonth + 1))
	// .append("-").append((mDay < 10) ? "0" + mDay : mDay)
	// .append("   ").append(mHour).append(":")
	// .append((mMinute < 10) ? "0" + mMinute : mMinute));
	// }

	/**
	 * 时间选择
	 */
	private void doSelectTimeData() {
		Message msg = new Message();
		msg.what = SHOW_TIMEPICK;
		dateandtimeHandler.sendMessage(msg);
	}
}
