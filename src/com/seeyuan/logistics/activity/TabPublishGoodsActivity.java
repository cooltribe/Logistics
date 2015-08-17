package com.seeyuan.logistics.activity;

import java.io.File;
import java.io.UnsupportedEncodingException;
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
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import com.mesada.nj.pubcontrols.controls.RemoteImageView;
import com.seeyuan.logistics.R;
import com.seeyuan.logistics.application.ConstantPool;
import com.seeyuan.logistics.application.NetWork;
import com.seeyuan.logistics.customview.MuInputEditText;
import com.seeyuan.logistics.customview.ProgressAlertDialog;
import com.seeyuan.logistics.customview.SelectPicPopupWindow;
import com.seeyuan.logistics.customview.SingleSelectAlertDlialog;
import com.seeyuan.logistics.datacenter.DataHandler;
import com.seeyuan.logistics.datacenter.OnDataReceiveListener;
import com.seeyuan.logistics.datahandler.PublishGoodsSourceHandler;
import com.seeyuan.logistics.entity.CarLengthInfo;
import com.seeyuan.logistics.entity.CarTypeInfo;
import com.seeyuan.logistics.entity.GoodsDto;
import com.seeyuan.logistics.entity.ImageDto;
import com.seeyuan.logistics.entity.PdaRequest;
import com.seeyuan.logistics.entity.PdaResponse;
import com.seeyuan.logistics.jsonparser.ResultCodeJsonParser;
import com.seeyuan.logistics.util.CommonUtils;
import com.seeyuan.logistics.util.ToastUtil;

/**
 * 发布货源
 * 
 * @author zhazhaobao
 * 
 */
@SuppressLint("HandlerLeak")
public class TabPublishGoodsActivity extends BaseActivity implements
		OnClickListener, OnDataReceiveListener {

	/**
	 * 货物名称
	 */
	private MuInputEditText publishGoods_GoodsName;

	/**
	 * 货物类型
	 */
	private Button publishGoods_GoodsType;

	/**
	 * 货物重量
	 */
	private MuInputEditText publishGoods_GoodsWeight;

	/**
	 * 货物体积
	 */
	private MuInputEditText publishGoods_GoodsBulk;

	/**
	 * 车辆类型
	 */
	private Button publishGoods_CasType;

	/**
	 * 车辆长度
	 */
	private Button publishGoods_CasLength;

	/**
	 * 包装
	 */
	private Button publishGoods_packaging;

	/**
	 * 运输价格
	 */
	private MuInputEditText publishGoods_TransportPrices;

	/**
	 * 始发地
	 */
	private Button publishGoods_Origin;

	/**
	 * 目的地
	 */
	private Button publishGoods_Destination;

	/**
	 * 装货时间
	 */
	private CheckBox publishGoods_shipment_time;

	/**
	 * 装货时间，布局
	 */
	private LinearLayout publishGoods_shipment_time_layout;

	private Button publishGoods_shipment_time_start;

	private Button publishGoods_shipment_time_end;

	/**
	 * 收货时间
	 */
	private CheckBox publishGoods_receipt_time;

	/**
	 * 收货时间，布局
	 */
	private LinearLayout publishGoods_receipt_time_layout;

	private Button publishGoods_receipt_time_start;

	private Button publishGoods_receipt_time_end;

	/**
	 * 有效时间
	 */
	private Button publishGoods_valid_time;

	/**
	 * 有效期限 布局
	 */
	private LinearLayout publishGoods_valid_time_layout;

	/**
	 * 有效期限，长期
	 */
	private CheckBox publishGoods_valid_time_checkbox;

	/**
	 * 联系人
	 */
	private MuInputEditText publishGoods_Contact;

	/**
	 * 手机号码
	 */
	private MuInputEditText publishGoods_phone;

	/**
	 * 固定号码
	 */
	private MuInputEditText publishGoods_telephone;

	/**
	 * 公司名称
	 */
	private MuInputEditText publishGoods_company;

	/**
	 * 备注
	 */
	private MuInputEditText publishGoods_remark;

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
	private RelativeLayout publishGoods_invoice_header_layout;

	private MuInputEditText publishGoods_invoice_header;

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
	 * 包装
	 */
	private final int REFRESH_CAR_PACKAGE = 1004;

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

	private String currentTime;
	private String selectTime;

	/**
	 * 显示进度条
	 */
	private final int SHOW_PROGRESS = 3000;
	/**
	 * 关闭进度条
	 */
	private final int CLOSE_PROGRESS = 3001;

	private final int SHOW_TOAST = 3002;

	private ProgressAlertDialog progressDialog;

	/**
	 * 货源照片
	 */
	private RemoteImageView publishGoods_goods_photo;

	private final int REQUEST_CODE_PHOTOALBUM = 500;
	private final int REQUEST_CODE_PHOTO = 501;
	private final int REQUEST_CODE_PHOTOOK = 502;
	private final int REQUEST_CODE_PICK = 503;

	/**
	 * 是否回程
	 */
	private CheckBox publishGoods_back_cb;

	private SelectPicPopupWindow dialog;

	private String headerImgPath;

	/**
	 * 注意事项
	 */
	private RadioButton radio_attention1, radio_attention2, radio_attention3,
			radio_attention4;

	/**
	 * 配送方式
	 */
	private RadioButton radio_transport1, radio_transport2;

	/**
	 * 配送方式，提示
	 */
	private TextView transport_type_hint;

	/**
	 * 始发地详址
	 */
	private MuInputEditText publishGoods_Origin_detail;

	/**
	 * 目的地详址
	 */
	private MuInputEditText publishGoods_Destination_detail;
	
	/**
	 * 指定车主布局
	 */
	private RelativeLayout car_own_layout;
	
	/**
	 * 选择车主按钮
	 */
	private Button publishGoods_car_own_select;

	/**
	 * 是否选择车主：是
	 */
	private RadioButton radio_car_own1;

	/**
	 * 是否选择车主：否
	 */
	private RadioButton radio_car_own2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		switch (getIntent().getExtras().getInt("tag")) {
		case 1:
			setContentView(R.layout.activity_tab_publish_goods); // 软件activity的布局
			break;
		case 2:
			requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
			setContentView(R.layout.activity_tab_publish_goods); // 软件activity的布局
			getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,
					R.layout.deflaut_titlebar); // titlebar为自己标题栏的布局
			initTitle();
			break;
		}
		
		context = getApplicationContext();
		headerImgPath = "file://" + ConstantPool.DEFAULT_ICON_PATH
				+ "image_diy_takephoto.jpg";
		initView();
		setDateTime();
		setTimeOfDay();
	}
	private void initTitle(){
		ImageView back = (ImageView) findViewById(R.id.maintitle_back_iv);
		TextView title = (TextView) findViewById(R.id.defaulttitle_title_tv);
		title.setText("发布货源");
		back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
	}
	public void initView() {
		publishGoods_GoodsName = (MuInputEditText) findViewById(R.id.PublishGoods_GoodsName);
		publishGoods_GoodsType = (Button) findViewById(R.id.PublishGoods_GoodsType);
		publishGoods_GoodsWeight = (MuInputEditText) findViewById(R.id.PublishGoods_GoodsWeight);
		publishGoods_GoodsBulk = (MuInputEditText) findViewById(R.id.PublishGoods_GoodsBulk);

		publishGoods_CasType = (Button) findViewById(R.id.PublishGoods_CasType);
		publishGoods_CasLength = (Button) findViewById(R.id.PublishGoods_CasLength);
		publishGoods_packaging = (Button) findViewById(R.id.PublishGoods_packaging);

		publishGoods_shipment_time_layout = (LinearLayout) findViewById(R.id.PublishGoods_shipment_time_layout);
		publishGoods_receipt_time_layout = (LinearLayout) findViewById(R.id.PublishGoods_receipt_time_layout);

		publishGoods_TransportPrices = (MuInputEditText) findViewById(R.id.PublishGoods_TransportPrices);

		publishGoods_Origin = (Button) findViewById(R.id.PublishGoods_Origin);
		publishGoods_Destination = (Button) findViewById(R.id.PublishGoods_Destination);
		publishGoods_shipment_time = (CheckBox) findViewById(R.id.PublishGoods_shipment_time);
		publishGoods_shipment_time
				.setOnCheckedChangeListener(new OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(CompoundButton buttonView,
							boolean isChecked) {
						if (isChecked) {
							publishGoods_shipment_time_layout
									.setVisibility(View.GONE);
						} else {
							publishGoods_shipment_time_layout
									.setVisibility(View.VISIBLE);
						}
					}
				});
		publishGoods_receipt_time = (CheckBox) findViewById(R.id.PublishGoods_receipt_time);
		publishGoods_receipt_time
				.setOnCheckedChangeListener(new OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(CompoundButton buttonView,
							boolean isChecked) {

						if (isChecked) {
							publishGoods_receipt_time_layout
									.setVisibility(View.GONE);
						} else {
							publishGoods_receipt_time_layout
									.setVisibility(View.VISIBLE);
						}
					}
				});
		publishGoods_valid_time = (Button) findViewById(R.id.PublishGoods_valid_time);
		publishGoods_valid_time_layout = (LinearLayout) findViewById(R.id.PublishGoods_valid_time_layout);
		publishGoods_valid_time_checkbox = (CheckBox) findViewById(R.id.PublishGoods_valid_time_checkbox);
		publishGoods_valid_time_checkbox
				.setOnCheckedChangeListener(new OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(CompoundButton buttonView,
							boolean isChecked) {
						if (isChecked) {
							publishGoods_valid_time_layout
									.setVisibility(View.GONE);
						} else {
							publishGoods_valid_time_layout
									.setVisibility(View.VISIBLE);
						}
					}
				});
		publishGoods_Contact = (MuInputEditText) findViewById(R.id.PublishGoods_Contact);
		publishGoods_phone = (MuInputEditText) findViewById(R.id.PublishGoods_phone);

		publishGoods_telephone = (MuInputEditText) findViewById(R.id.PublishGoods_telephone);
		publishGoods_company = (MuInputEditText) findViewById(R.id.PublishGoods_company);
		publishGoods_remark = (MuInputEditText) findViewById(R.id.PublishGoods_remark);

		radio_pay1 = (RadioButton) findViewById(R.id.radio_pay1);
		radio_pay1.setChecked(true);
		radio_pay2 = (RadioButton) findViewById(R.id.radio_pay2);
		radio_pay3 = (RadioButton) findViewById(R.id.radio_pay3);

		publishGoods_shipment_time_start = (Button) findViewById(R.id.PublishGoods_shipment_time_start);
		publishGoods_shipment_time_end = (Button) findViewById(R.id.PublishGoods_shipment_time_end);
		publishGoods_receipt_time_start = (Button) findViewById(R.id.PublishGoods_receipt_time_start);
		publishGoods_receipt_time_end = (Button) findViewById(R.id.PublishGoods_receipt_time_end);

		publishGoods_invoice_header_layout = (RelativeLayout) findViewById(R.id.PublishGoods_invoice_header_layout);
		publishGoods_invoice_header = (MuInputEditText) findViewById(R.id.PublishGoods_invoice_header);
		radio_invoice1 = (RadioButton) findViewById(R.id.radio_invoice1);
		radio_invoice1
				.setOnCheckedChangeListener(new OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(CompoundButton buttonView,
							boolean isChecked) {
						if (isChecked) {
							publishGoods_invoice_header_layout
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
							publishGoods_invoice_header_layout
									.setVisibility(View.GONE);
						}
					}
				});

		publishGoods_back_cb = (CheckBox) findViewById(R.id.PublishGoods_back_cb);
		publishGoods_goods_photo = (RemoteImageView) findViewById(R.id.PublishGoods_goods_photo);
		publishGoods_goods_photo.setOnClickListener(this);

		radio_attention1 = (RadioButton) findViewById(R.id.radio_attention1);
		radio_attention2 = (RadioButton) findViewById(R.id.radio_attention2);
		radio_attention3 = (RadioButton) findViewById(R.id.radio_attention3);
		radio_attention4 = (RadioButton) findViewById(R.id.radio_attention4);

		radio_transport1 = (RadioButton) findViewById(R.id.radio_transport1);
		radio_transport2 = (RadioButton) findViewById(R.id.radio_transport2);

		// transport_type_hint =
		// (TextView)findViewById(R.id.transport_type_hint);

		publishGoods_Origin_detail = (MuInputEditText) findViewById(R.id.PublishGoods_Origin_detail);
		publishGoods_Destination_detail = (MuInputEditText) findViewById(R.id.PublishGoods_Destination_detail);
		car_own_layout = (RelativeLayout) findViewById(R.id.car_own_layout);
		publishGoods_car_own_select = (Button) findViewById(R.id.PublishGoods_car_own_select);
		radio_car_own1 = (RadioButton) findViewById(R.id.radio_car_own1);
		radio_car_own1
		.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				if (isChecked) {
					car_own_layout
							.setVisibility(View.VISIBLE);
				}
			}
		});
		
		radio_car_own2 = (RadioButton) findViewById(R.id.radio_car_own2);
		radio_car_own2.setChecked(true);
		radio_car_own2
				.setOnCheckedChangeListener(new OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(CompoundButton buttonView,
							boolean isChecked) {
						if (isChecked) {
							car_own_layout
									.setVisibility(View.GONE);
						}
					}
				});
	}

	private Handler myHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case REFRESH_GOODS_TYPE:
				publishGoods_GoodsType.setText(msg.obj.toString());
				break;
			case REFRESH_TRANSPORT_MODE:
				// PublishGoods_TransportMode.setText(msg.obj.toString());
				break;
			case REFRESH_CAR_TYPE:
				publishGoods_CasType.setText(msg.obj.toString());
				break;
			case REFRESH_CAR_LENGTH:
				publishGoods_CasLength.setText(msg.obj.toString() + "米");
				break;
			case REFRESH_CAR_PACKAGE:
				publishGoods_packaging.setText(msg.obj.toString());
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
		case R.id.PublishGoods_goods_photo:
			showOptionDialog(REQUEST_CODE_PHOTOALBUM, REQUEST_CODE_PHOTO);
			break;

		default:
			break;
		}
	}

	private void showOptionDialog(final int photoCode, final int cameraCode) {
		dialog = new SelectPicPopupWindow(TabPublishGoodsActivity.this);
		dialog.setFirstButtonContent(getResources().getString(
				R.string.take_photo_hint));
		dialog.setFirstButtonOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				CommonUtils.selectCameraPhone(cameraCode, headerImgPath,
						TabPublishGoodsActivity.this);
				dialog.dismiss();
			}
		});
		dialog.setSecendButtonContent(getResources().getString(
				R.string.get_system_photo_hint));
		dialog.setSecendButtonOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				CommonUtils.selectSystemPhone(photoCode,
						TabPublishGoodsActivity.this);
				dialog.dismiss();
			}
		});
		dialog.setThirdButtonContent(getResources().getString(R.string.cancel));
		dialog.setThirdButtonOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});
		// 显示窗口
		dialog.showAtLocation(
				TabPublishGoodsActivity.this.findViewById(R.id.main),
				Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0); // 设置layout在PopupWindow中显示的位置

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
			doSearchPackage();
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
			//指定车主选择
		case R.id.PublishGoods_car_own_select:
			doSelectCarOwn();
			break;
		default:
			break;
		}

	}

	/**
	 * 指定车主选择
	 */
	private void doSelectCarOwn(){
		Intent intent = new Intent(this, SelectCarOwnActivity.class);
		startActivityForResult(intent, 5);
	}
	private void doSearchPackage() {

		List<CarLengthInfo> mDataList = new ArrayList<CarLengthInfo>();
		TypedArray typedArray = getResources().obtainTypedArray(
				R.array.package_type);
		int size = typedArray.length();
		for (int i = 0; i < size; i++) {
			CarLengthInfo indexInfo = new CarLengthInfo();
			indexInfo.setCar_Length(typedArray.getString(i));
			mDataList.add(indexInfo);
		}

		final SingleSelectAlertDlialog ad = new SingleSelectAlertDlialog(
				TabPublishGoodsActivity.this);
		ad.setTitle("包装类型");
		ad.setListContentForCarLength(mDataList);
		ad.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View view, int arg2,
					long arg3) {
				if (null == view)
					return;
				ad.dismiss();
				Message message = myHandler.obtainMessage();
				message.what = REFRESH_CAR_PACKAGE;
				message.obj = ((TextView) view
						.findViewById(R.id.item_car_length)).getText();
				myHandler.sendMessage(message);

			}
		});
		typedArray.recycle();
	}

	/**
	 * 发布货源
	 */
	private void doSubmitPublishGoods() {
		myHandler.sendEmptyMessage(SHOW_PROGRESS);
		if (radio_transport2.isChecked()) {// 判断运输方法
			if (TextUtils.isEmpty(publishGoods_Origin_detail.getText()
					.toString())) {
				myHandler.sendEmptyMessage(CLOSE_PROGRESS);
				Message msg = myHandler.obtainMessage();
				msg.what = SHOW_TOAST;
				msg.obj = "请输入正确的始发地详址";
				myHandler.sendMessage(msg);
				return;
			}
			if (TextUtils.isEmpty(publishGoods_Destination_detail.getText()
					.toString())) {
				myHandler.sendEmptyMessage(CLOSE_PROGRESS);
				Message msg = myHandler.obtainMessage();
				msg.what = SHOW_TOAST;
				msg.obj = "请输入正确的目的地详址";
				myHandler.sendMessage(msg);
				return;
			}
		}
		// if (PublishGoods_valid_time_checkbox.isChecked()) {
		// if (TextUtils.isEmpty(PublishGoods_valid_time.getText().toString()))
		// {
		//
		// myHandler.sendEmptyMessage(CLOSE_PROGRESS);
		// Message msg = myHandler.obtainMessage();
		// msg.what = SHOW_TOAST;
		// msg.obj = "请选择正确的有效期限";
		// myHandler.sendMessage(msg);
		// return;
		//
		// }
		// }

		mGoodsDto.setGoodsName(publishGoods_GoodsName.getText().toString());
		mGoodsDto.setGoodsType(publishGoods_GoodsType.getText().toString());
		mGoodsDto.setGoodsWeight(BigDecimal.valueOf(Double
				.parseDouble(publishGoods_GoodsWeight.getText().toString())));
		mGoodsDto.setGoodsVolume(Double.valueOf(publishGoods_GoodsBulk
				.getText().toString()));
		mGoodsDto.setVehType(publishGoods_CasType.getText().toString());
		mGoodsDto.setVehLegth(publishGoods_CasLength.getText().toString());
		mGoodsDto.setPackages(publishGoods_packaging.getText().toString());
		mGoodsDto.setCost(TextUtils.isEmpty(publishGoods_TransportPrices
				.getText().toString()) ? null
				: BigDecimal.valueOf(Double
						.parseDouble(publishGoods_TransportPrices.getText()
								.toString())));
		mGoodsDto.setSetout(publishGoods_Origin.getText().toString());
		mGoodsDto.setDestination(publishGoods_Destination.getText().toString());
		// 装货日期，收货日期
		if (!publishGoods_shipment_time.isChecked()) {
			try {
				mGoodsDto.setDeliveryDateF(new SimpleDateFormat("yyyy-MM-dd")
						.parse(publishGoods_shipment_time_start.getText()
								.toString()));
				mGoodsDto.setDeliveryDateT(new SimpleDateFormat("yyyy-MM-dd")
						.parse(publishGoods_shipment_time_end.getText()
								.toString()));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		} else {
			mGoodsDto.setDeliveryDateF(null);
			mGoodsDto.setDeliveryDateT(null);
		}

		if (!publishGoods_receipt_time.isChecked()) {
			try {
				mGoodsDto.setReceiveDateF(new SimpleDateFormat("yyyy-MM-dd")
						.parse(publishGoods_receipt_time_start.getText()
								.toString()));
				mGoodsDto.setReceiveDateT(new SimpleDateFormat("yyyy-MM-dd")
						.parse(publishGoods_receipt_time_end.getText()
								.toString()));
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			mGoodsDto.setReceiveDateF(null);
			mGoodsDto.setReceiveDateT(null);
		}

		// mGoodsDto.setValidDeadline(TextUtils.isEmpty(PublishGoods_valid_time
		// .getText().toString()) ? null : CommonUtils
		// .parserData(PublishGoods_valid_time.getText().toString()));
		mGoodsDto
				.setValidDeadline(publishGoods_valid_time_checkbox.isChecked() ? null
						: CommonUtils.parserData(publishGoods_valid_time
								.getText().toString()));
		mGoodsDto.setContactName(publishGoods_Contact.getText().toString());
		mGoodsDto.setmPhone(publishGoods_phone.getText().toString());
		mGoodsDto.setfPhone(publishGoods_telephone.getText().toString());
		mGoodsDto.setCompanyName(publishGoods_company.getText().toString());
		mGoodsDto.setRemark(publishGoods_remark.getText().toString());
		mGoodsDto.setPayType(getPayType());

		mGoodsDto.setIsNeedInvoice(getInvoice());
		mGoodsDto.setInvoiceTitle(publishGoods_invoice_header.getText()
				.toString());
		mGoodsDto.setIsBackPre(publishGoods_back_cb.isChecked() ? "Y" : "N");
		ImageDto imageDto = new ImageDto();
		imageDto.setImageSuffix("PNG");
		imageDto.setFile(null == resultBitmap ? null : CommonUtils
				.getBitmapByByte(resultBitmap));
		mGoodsDto.setGoodsPicture(imageDto);
		mGoodsDto.setSetoutDetail(publishGoods_Origin_detail.getText()
				.toString());
		mGoodsDto.setDestinationDetail(publishGoods_Destination_detail
				.getText().toString());
		mGoodsDto.setNotice(getNotice());
		mGoodsDto.setDisMode(getDisMode());

		PdaRequest<GoodsDto> request = new PdaRequest<GoodsDto>();
		request.setData(mGoodsDto);
		PublishGoodsSourceHandler dataHandler = new PublishGoodsSourceHandler(
				context, request);
		dataHandler.setOnDataReceiveListener(this);
		dataHandler.startNetWork();
	}

	/**
	 * 支付方式
	 * 
	 * @return
	 */
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

	/**
	 * 注意事项
	 * 
	 * @return
	 */
	private String getNotice() {
		if (radio_attention1.isChecked()) {
			return radio_attention1.getText().toString();
		} else if (radio_attention2.isChecked()) {
			return radio_attention2.getText().toString();
		} else if (radio_attention3.isChecked()) {
			return radio_attention3.getText().toString();
		} else if (radio_attention4.isChecked()) {
			return radio_attention4.getText().toString();
		}
		return "";
	}

	private String getDisMode() {
		if (radio_transport1.isChecked()) {
			return radio_transport1.getText().toString();
		} else if (radio_transport2.isChecked()) {
			return radio_transport2.getText().toString();
		}
		return "";
	}

	private String getInvoice() {
		if (radio_invoice1.isChecked()) {
			return "Y";
		} else if (radio_invoice2.isChecked()) {
			return "N";
		}
		return "";
	}

	private String isCanSubmitGoodsSource() {
		Filter goodsName = new goodsNameFilder();
		Filter goodsType = new goodsTypeFilder();
		Filter goodsWeight = new goodsWeightFilder();
		Filter carType = new carTypeFilder();
		Filter carBulk = new carBulkFilder();
		Filter carLength = new carLengthFilder();
		Filter targetFrom = new targetFromFilder();
		Filter targetTo = new targetToFilder();
		Filter uesrPhone = new userPhoneFilder();
		Filter invoiceHeader = new InvoiceHeaderFilder();

		goodsName.setNext(goodsType);
		goodsType.setNext(goodsWeight);
		goodsWeight.setNext(carType);
		carType.setNext(carBulk);
		carBulk.setNext(carLength);
		carLength.setNext(targetFrom);
		targetFrom.setNext(targetTo);
		targetTo.setNext(uesrPhone);

		if (radio_invoice1.isChecked()) {
			uesrPhone.setNext(invoiceHeader);
		}
		String result = goodsName.doFilter(publishGoods_GoodsName.getText()
				.toString(), publishGoods_GoodsType.getText().toString(),
				publishGoods_GoodsWeight.getText().toString(),
				publishGoods_CasType.getText().toString(),
				publishGoods_GoodsBulk.getText().toString(),
				publishGoods_CasLength.getText().toString(),
				publishGoods_Origin.getText().toString(),
				publishGoods_Destination.getText().toString(),
				publishGoods_phone.getText().toString(),
				publishGoods_invoice_header.getText().toString());

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
				String carLength, String targetFrom, String targetTo,
				String uesrPhone, String invoiceHeader) {

			if (next == null) {
				return "成功";
			} else
				return next.doFilter(goodsName, goodsType, goodsWeight,
						carType, carBulk, carLength, targetFrom, targetTo,
						uesrPhone, invoiceHeader);

		}

	}

	class goodsNameFilder extends Filter {
		@Override
		public String doFilter(String goodsName, String goodsType,
				String goodsWeight, String carType, String carBulk,
				String carLength, String targetFrom, String targetTo,
				String uesrPhone, String invoiceHeader) {

			if (TextUtils.isEmpty(goodsName)) {
				return "请输入正确的货物名称";
			} else {
				return super.doFilter(goodsName, goodsType, goodsWeight,
						carType, carBulk, carLength, targetFrom, targetTo,
						uesrPhone, invoiceHeader);
			}
		}
	}

	class goodsTypeFilder extends Filter {
		@Override
		public String doFilter(String goodsName, String goodsType,
				String goodsWeight, String carType, String carBulk,
				String carLength, String targetFrom, String targetTo,
				String uesrPhone, String invoiceHeader) {

			if (TextUtils.isEmpty(goodsType)) {
				return "请输入正确的货物类型";
			} else {
				return super.doFilter(goodsName, goodsType, goodsWeight,
						carType, carBulk, carLength, targetFrom, targetTo,
						uesrPhone, invoiceHeader);
			}
		}
	}

	class goodsWeightFilder extends Filter {
		@Override
		public String doFilter(String goodsName, String goodsType,
				String goodsWeight, String carType, String carBulk,
				String carLength, String targetFrom, String targetTo,
				String uesrPhone, String invoiceHeader) {

			if (TextUtils.isEmpty(goodsWeight)) {
				return "请输入正确的货物重量";
			} else {
				return super.doFilter(goodsName, goodsType, goodsWeight,
						carType, carBulk, carLength, targetFrom, targetTo,
						uesrPhone, invoiceHeader);
			}
		}
	}

	class carTypeFilder extends Filter {
		@Override
		public String doFilter(String goodsName, String goodsType,
				String goodsWeight, String carType, String carBulk,
				String carLength, String targetFrom, String targetTo,
				String uesrPhone, String invoiceHeader) {

			if (TextUtils.isEmpty(carType)) {
				return "请输入正确的车辆类型";
			} else {
				return super.doFilter(goodsName, goodsType, goodsWeight,
						carType, carBulk, carLength, targetFrom, targetTo,
						uesrPhone, invoiceHeader);
			}
		}
	}

	class carBulkFilder extends Filter {
		@Override
		public String doFilter(String goodsName, String goodsType,
				String goodsWeight, String carType, String carBulk,
				String carLength, String targetFrom, String targetTo,
				String uesrPhone, String invoiceHeader) {

			if (TextUtils.isEmpty(carBulk)) {
				return "请输入正确的货物体积";
			} else {
				return super.doFilter(goodsName, goodsType, goodsWeight,
						carType, carBulk, carLength, targetFrom, targetTo,
						uesrPhone, invoiceHeader);
			}
		}
	}

	class carLengthFilder extends Filter {
		@Override
		public String doFilter(String goodsName, String goodsType,
				String goodsWeight, String carType, String carBulk,
				String carLength, String targetFrom, String targetTo,
				String uesrPhone, String invoiceHeader) {

			if (TextUtils.isEmpty(carLength)) {
				return "请输入正确的车辆长度";
			} else {
				return super.doFilter(goodsName, goodsType, goodsWeight,
						carType, carBulk, carLength, targetFrom, targetTo,
						uesrPhone, invoiceHeader);
			}
		}
	}

	class targetFromFilder extends Filter {
		@Override
		public String doFilter(String goodsName, String goodsType,
				String goodsWeight, String carType, String carBulk,
				String carLength, String targetFrom, String targetTo,
				String uesrPhone, String invoiceHeader) {

			if (TextUtils.isEmpty(targetFrom)) {
				return "请输入正确的始发地";
			} else {
				return super.doFilter(goodsName, goodsType, goodsWeight,
						carType, carBulk, carLength, targetFrom, targetTo,
						uesrPhone, invoiceHeader);
			}
		}
	}

	class targetToFilder extends Filter {
		@Override
		public String doFilter(String goodsName, String goodsType,
				String goodsWeight, String carType, String carBulk,
				String carLength, String targetFrom, String targetTo,
				String uesrPhone, String invoiceHeader) {

			if (TextUtils.isEmpty(targetTo)) {
				return "请输入正确的目的地";
			} else {
				return super.doFilter(goodsName, goodsType, goodsWeight,
						carType, carBulk, carLength, targetFrom, targetTo,
						uesrPhone, invoiceHeader);
			}
		}
	}

	class InvoiceHeaderFilder extends Filter {
		@Override
		public String doFilter(String goodsName, String goodsType,
				String goodsWeight, String carType, String carBulk,
				String carLength, String targetFrom, String targetTo,
				String uesrPhone, String invoiceHeader) {

			if (TextUtils.isEmpty(invoiceHeader)) {
				return "请输入正确的发票抬头";
			} else {
				return super.doFilter(goodsName, goodsType, goodsWeight,
						carType, carBulk, carLength, targetFrom, targetTo,
						uesrPhone, invoiceHeader);
			}
		}
	}

	// 装货时间

	// 收货时间

	class userPhoneFilder extends Filter {
		@Override
		public String doFilter(String goodsName, String goodsType,
				String goodsWeight, String carType, String carBulk,
				String carLength, String targetFrom, String targetTo,
				String uesrPhone, String invoiceHeader) {

			if (TextUtils.isEmpty(uesrPhone)
					|| !CommonUtils.isMobileNO(uesrPhone)) {
				return "请输入正确的手机号码";
			} else {
				return super.doFilter(goodsName, goodsType, goodsWeight,
						carType, carBulk, carLength, targetFrom, targetTo,
						uesrPhone, invoiceHeader);
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
				TabPublishGoodsActivity.this);
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
				message.obj = mDataList.get(position);
				myHandler.sendMessage(message);

			}
		});
		typedArray.recycle();
	}

	/**
	 * 执行始发地
	 */
	private void doGoodsOrigin() {
		Intent intent = new Intent(TabPublishGoodsActivity.this,
				SearchCityActivity.class);
		startActivityForResult(intent, GOODS_ORIGIN_RETURN);
	}

	/**
	 * 执行目的地
	 */
	private void doGoodsDestination() {
		Intent intent = new Intent(TabPublishGoodsActivity.this,
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
				TabPublishGoodsActivity.this);
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
				TabPublishGoodsActivity.this);
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
				TabPublishGoodsActivity.this);
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
				TabPublishGoodsActivity.this);
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
			publishGoods_Origin.setText(data.getStringExtra("place"));
			break;
		case GOODS_DESTINATION_RETURN:
			publishGoods_Destination.setText(data.getStringExtra("place"));
			break;
		case REQUEST_CODE_PHOTOALBUM:
			if (data != null) {
				startPhotoZoom(data.getData(), REQUEST_CODE_PHOTOOK);
			}
			break;
		case REQUEST_CODE_PHOTO:
			filePath = "file://" + ConstantPool.DEFAULT_ICON_PATH
					+ "image_diy_takephoto.jpg";
			if (filePath != null) {
				cropPhoto(filePath, REQUEST_CODE_PICK);
			}
			break;
		case REQUEST_CODE_PHOTOOK:
			resultBitmap = BitmapFactory
					.decodeFile(ConstantPool.DEFAULT_ICON_PATH
							+ "image_diy_resultphoto_temp.jpg");
			publishGoods_goods_photo.setImageBitmap(resultBitmap);
			break;
		case REQUEST_CODE_PICK:
			resultBitmap = BitmapFactory
					.decodeFile(ConstantPool.DEFAULT_ICON_PATH
							+ "image_diy_resultphoto.jpg");
			publishGoods_goods_photo.setImageBitmap(resultBitmap);
			break;

		default:
			break;
		}
	}

	private String filePath;
	private Bitmap resultBitmap;

	/**
	 * 
	 * 裁剪图片方法实现
	 * 
	 * 
	 * 
	 * @param uri
	 */
	public void startPhotoZoom(Uri uri, int photoook) {
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, "image/*");
		intent.putExtra("crop", "true");
		intent.putExtra("aspectX", 5);
		intent.putExtra("aspectY", 3);
		intent.putExtra("outputX", 480);
		intent.putExtra("outputY", 480);
		intent.putExtra("scale", true);

		File tempFile = new File(ConstantPool.DEFAULT_ICON_PATH
				+ "image_diy_resultphoto_temp.jpg");
		intent.putExtra("output", Uri.fromFile(tempFile));
		intent.putExtra("outputFormat", "JPEG");// 返回格式
		try {
			startActivityForResult(intent, photoook);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void cropPhoto(String filePath, int pick) {
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(Uri.parse(filePath), "image/*");
		intent.putExtra("crop", "true");
		intent.putExtra("aspectX", 5);
		intent.putExtra("aspectY", 3);
		intent.putExtra("outputX", 480);
		intent.putExtra("outputY", 480);
		intent.putExtra("scale", true);

		File tempFile = new File(ConstantPool.DEFAULT_ICON_PATH
				+ "image_diy_resultphoto.jpg");
		intent.putExtra("output", Uri.fromFile(tempFile));
		intent.putExtra("outputFormat", "JPEG");// 返回格式
		try {
			startActivityForResult(intent, pick);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void takePhoto(int photo) {
		Intent intent = new Intent();
		intent.setAction("android.media.action.IMAGE_CAPTURE");
		Bundle bundle = new Bundle();

		String path = ConstantPool.DEFAULT_ICON_PATH;
		if (path != null) {
			filePath = "file://" + path + "image_diy_takephoto.jpg";
			Log.v("filePath", filePath);
			Uri uri = Uri.parse(filePath);
			bundle.putParcelable(MediaStore.EXTRA_OUTPUT, uri);
			intent.putExtras(bundle);
			try {
				startActivityForResult(intent, photo);
			} catch (Exception e) {
				ToastUtil.show(
						this,
						getResources().getString(
								R.string.msg_send_nophoto_prompt));
			}
		}
	}

	@Override
	public void onDataReceive(DataHandler dataHandler, int resultCode,
			Object data, int type) {
		myHandler.sendEmptyMessage(CLOSE_PROGRESS);
		try {
			String dataString = new String((byte[]) data, "UTF-8");
			Log.i("发布货源", dataString);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
			PdaResponse<String> response = ResultCodeJsonParser
					.parserResultCodeJson(dataString);
			String result = response.getMessage();
			int messageCode = Integer.parseInt(result.substring(0,
					result.indexOf("#")));
			String message = "";
			message = result
					.substring(result.indexOf("#") + 1, result.length());
			if (!response.isSuccess()) {
				Message msg = myHandler.obtainMessage();
				msg.what = SHOW_TOAST;
				msg.obj = message;
				myHandler.sendMessage(msg);
				return;
			} else {
				ToastUtil.show(context, "发布车源成功");
				Intent intent = new Intent(TabPublishGoodsActivity.this,
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

		currentTime = new StringBuilder().append(mYear).append("-")
				.append((mMonth + 1) < 10 ? "0" + (mMonth + 1) : (mMonth + 1))
				.append("-").append((mDay < 10) ? "0" + mDay : mDay).toString();
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

		selectTime = new StringBuilder().append(mYear).append("-")
				.append((mMonth + 1) < 10 ? "0" + (mMonth + 1) : (mMonth + 1))
				.append("-").append((mDay < 10) ? "0" + mDay : mDay).toString();
		if (CommonUtils.compare_date(currentTime, selectTime)) {
			ToastUtil.show(context, "请选择正确的时间");
		} else {

			switch (id) {
			case R.id.PublishGoods_shipment_time_start:
				publishGoods_shipment_time_start.setText(selectTime);
				break;
			case R.id.PublishGoods_shipment_time_end:
				publishGoods_shipment_time_end.setText(selectTime);
				break;
			case R.id.PublishGoods_receipt_time_start:
				publishGoods_receipt_time_start.setText(selectTime);
				break;
			case R.id.PublishGoods_receipt_time_end:
				publishGoods_receipt_time_end.setText(selectTime);
				break;
			case R.id.PublishGoods_valid_time:
				publishGoods_valid_time.setText(selectTime);
				break;

			default:
				break;
			}
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
