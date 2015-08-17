package com.seeyuan.logistics.activity;

import java.io.File;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

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
import android.view.View.OnClickListener;
import android.view.Window;
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
import com.seeyuan.logistics.datahandler.DeleteGoodsHandler;
import com.seeyuan.logistics.datahandler.EditGoodsInfoHandler;
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
 * 货源管理。货源详情
 * 
 * @author zhazhaobao
 * 
 */
public class GoodsManagerDetailActivity extends BaseActivity implements
		OnClickListener, OnDataReceiveListener {

	/**
	 * 返回按钮
	 */
	private ImageView maintitle_back_iv;

	/**
	 * 标题title
	 */
	private TextView defaulttitle_title_tv;

	private TextView maintitle_comfirm_tv;

	/**
	 * 货物名称
	 */
	private MuInputEditText GoodsManager_GoodsName;

	/**
	 * 货物类型
	 */
	private Button GoodsManager_GoodsType;

	/**
	 * 货物重量
	 */
	private MuInputEditText GoodsManager_GoodsWeight;

	/**
	 * 货物体积
	 */
	private MuInputEditText GoodsManager_GoodsBulk;

	/**
	 * 车辆类型
	 */
	private Button GoodsManager_CasType;

	/**
	 * 车辆长度
	 */
	private Button GoodsManager_CasLength;

	/**
	 * 包装
	 */
	private Button GoodsManager_packaging;

	/**
	 * 运输价格
	 */
	private MuInputEditText GoodsManager_TransportPrices;

	/**
	 * 始发地
	 */
	private Button GoodsManager_Origin;

	/**
	 * 目的地
	 */
	private Button GoodsManager_Destination;

	/**
	 * 装货时间 checkbox
	 */
	private CheckBox GoodsManager_shipment_time;

	private LinearLayout GoodsManager_shipment_time_layout;

	private Button GoodsManager_shipment_time_start;

	private Button GoodsManager_shipment_time_end;

	/**
	 * 收货时间 checkbox
	 */
	private CheckBox GoodsManager_receipt_time;

	private LinearLayout GoodsManager_receipt_time_layout;

	private Button GoodsManager_receipt_time_start;

	private Button GoodsManager_receipt_time_end;

	/**
	 * 有效期限
	 */
	private Button GoodsManager_valid_time;

	/**
	 * 联系人
	 */
	private MuInputEditText GoodsManager_Contact;

	/**
	 * 手机号码
	 */
	private MuInputEditText GoodsManager_phone;

	/**
	 * 固定电话
	 */
	private MuInputEditText GoodsManager_telephone;

	/**
	 * 公司名称
	 */
	private MuInputEditText GoodsManager_company;

	/**
	 * 备注
	 */
	private MuInputEditText GoodsManager_remark;

	/**
	 * 支付方式
	 */
	private RadioButton radio_pay1, radio_pay2, radio_pay3;

	/**
	 * 是否索要发票
	 */
	private RadioButton radio_invoice1, radio_invoice2;

	private MuInputEditText GoodsManager_invoice_header;

	private RelativeLayout GoodsManager_invoice_header_layout;

	private GoodsDto mGoodsDto;

	private Context context;

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

	private Button editButton;

	private ProgressAlertDialog progressDialog;

	/**
	 * 货源照片
	 */
	private RemoteImageView PublishGoods_goods_photo;

	private final int REQUEST_CODE_PHOTOALBUM = 500;
	private final int REQUEST_CODE_PHOTO = 501;
	private final int REQUEST_CODE_PHOTOOK = 502;
	private final int REQUEST_CODE_PICK = 503;

	/**
	 * 是否回程
	 */
	private CheckBox PublishGoods_back_cb;

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
	private MuInputEditText PublishGoods_Origin_detail;

	/**
	 * 目的地详址
	 */
	private MuInputEditText PublishGoods_Destination_detail;

	/**
	 * 显示进度条
	 */
	private final int SHOW_PROGRESS = 3000;
	/**
	 * 关闭进度条
	 */
	private final int CLOSE_PROGRESS = 3001;

	private final int SHOW_TOAST = 3002;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.activity_goods_manager_detail); // 软件activity的布局
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,
				R.layout.deflaut_titlebar); // titlebar为自己标题栏的布局
		mGoodsDto = (GoodsDto) getIntent().getSerializableExtra("goodsInfo");
		context = getApplicationContext();
		headerImgPath = "file://" + ConstantPool.DEFAULT_ICON_PATH
				+ "image_diy_takephoto.jpg";
		initView();
		setDateTime();
		setTimeOfDay();
	}

	@Override
	public void initView() {
		maintitle_back_iv = (ImageView) findViewById(R.id.maintitle_back_iv);
		maintitle_back_iv.setOnClickListener(this);

		defaulttitle_title_tv = (TextView) findViewById(R.id.defaulttitle_title_tv);
		defaulttitle_title_tv.setText(R.string.goods_manage_detail_hint);

		maintitle_comfirm_tv = (TextView) findViewById(R.id.maintitle_comfirm_tv);
		maintitle_comfirm_tv.setText(R.string.delete);
		maintitle_comfirm_tv.setVisibility(View.VISIBLE);
		maintitle_comfirm_tv.setOnClickListener(this);

		GoodsManager_GoodsName = (MuInputEditText) findViewById(R.id.PublishGoods_GoodsName);
		GoodsManager_GoodsName.setText(TextUtils.isEmpty(mGoodsDto
				.getGoodsName()) ? "" : mGoodsDto.getGoodsName());
		GoodsManager_GoodsType = (Button) findViewById(R.id.PublishGoods_GoodsType);
		GoodsManager_GoodsType.setText(TextUtils.isEmpty(mGoodsDto
				.getGoodsType()) ? "" : mGoodsDto.getGoodsType());
		GoodsManager_GoodsWeight = (MuInputEditText) findViewById(R.id.PublishGoods_GoodsWeight);
		GoodsManager_GoodsWeight
				.setText(mGoodsDto.getGoodsWeight() == null ? "" : mGoodsDto
						.getGoodsWeight().toString());
		GoodsManager_GoodsBulk = (MuInputEditText) findViewById(R.id.PublishGoods_GoodsBulk);
		GoodsManager_GoodsBulk.setText(null == mGoodsDto.getGoodsVolume() ? ""
				: mGoodsDto.getGoodsVolume().toString());
		GoodsManager_CasType = (Button) findViewById(R.id.PublishGoods_CasType);
		GoodsManager_CasType
				.setText(TextUtils.isEmpty(mGoodsDto.getVehType()) ? ""
						: mGoodsDto.getVehType());
		GoodsManager_CasLength = (Button) findViewById(R.id.PublishGoods_CasLength);
		GoodsManager_CasLength.setText(TextUtils.isEmpty(mGoodsDto
				.getVehLegth()) ? "" : mGoodsDto.getVehLegth());
		GoodsManager_packaging = (Button) findViewById(R.id.PublishGoods_packaging);
		GoodsManager_packaging.setText(TextUtils.isEmpty(mGoodsDto
				.getPackages()) ? "" : mGoodsDto.getPackages());
		GoodsManager_TransportPrices = (MuInputEditText) findViewById(R.id.PublishGoods_TransportPrices);
		GoodsManager_TransportPrices.setText(TextUtils.isEmpty(mGoodsDto
				.getDisMode()) ? "" : mGoodsDto.getDisMode());
		GoodsManager_Origin = (Button) findViewById(R.id.PublishGoods_Origin);
		GoodsManager_Origin
				.setText(TextUtils.isEmpty(mGoodsDto.getSetout()) ? ""
						: mGoodsDto.getSetout());
		GoodsManager_Destination = (Button) findViewById(R.id.PublishGoods_Destination);
		GoodsManager_Destination.setText(TextUtils.isEmpty(mGoodsDto
				.getDestination()) ? "" : mGoodsDto.getDestination());
		GoodsManager_shipment_time = (CheckBox) findViewById(R.id.PublishGoods_shipment_time);
		GoodsManager_shipment_time
				.setOnCheckedChangeListener(new OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(CompoundButton buttonView,
							boolean isChecked) {
						if (isChecked) {
							GoodsManager_shipment_time_layout
									.setVisibility(View.GONE);
						} else {
							GoodsManager_shipment_time_layout
									.setVisibility(View.VISIBLE);
						}
					}
				});
		GoodsManager_shipment_time_layout = (LinearLayout) findViewById(R.id.PublishGoods_shipment_time_layout);
		GoodsManager_shipment_time_start = (Button) findViewById(R.id.PublishGoods_shipment_time_start);
		GoodsManager_shipment_time_end = (Button) findViewById(R.id.PublishGoods_shipment_time_end);

		GoodsManager_receipt_time = (CheckBox) findViewById(R.id.PublishGoods_receipt_time);
		GoodsManager_receipt_time
				.setOnCheckedChangeListener(new OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(CompoundButton buttonView,
							boolean isChecked) {
						if (isChecked) {
							GoodsManager_receipt_time_layout
									.setVisibility(View.GONE);
						} else {
							GoodsManager_receipt_time_layout
									.setVisibility(View.VISIBLE);
						}
					}
				});
		GoodsManager_receipt_time_layout = (LinearLayout) findViewById(R.id.PublishGoods_receipt_time_layout);
		GoodsManager_receipt_time_start = (Button) findViewById(R.id.PublishGoods_receipt_time_start);
		GoodsManager_receipt_time_end = (Button) findViewById(R.id.PublishGoods_receipt_time_end);
		GoodsManager_valid_time = (Button) findViewById(R.id.PublishGoods_valid_time);
		GoodsManager_valid_time.setText(TextUtils.isEmpty(CommonUtils
				.parserData(mGoodsDto.getValidDeadline())) ? "" : CommonUtils
				.parserData(mGoodsDto.getValidDeadline()));
		GoodsManager_Contact = (MuInputEditText) findViewById(R.id.PublishGoods_Contact);
		GoodsManager_Contact.setText(TextUtils.isEmpty(mGoodsDto
				.getContactName()) ? "" : mGoodsDto.getContactName());
		GoodsManager_phone = (MuInputEditText) findViewById(R.id.PublishGoods_phone);
		GoodsManager_phone
				.setText(TextUtils.isEmpty(mGoodsDto.getmPhone()) ? ""
						: mGoodsDto.getmPhone());
		GoodsManager_telephone = (MuInputEditText) findViewById(R.id.PublishGoods_telephone);
		GoodsManager_telephone
				.setText(TextUtils.isEmpty(mGoodsDto.getfPhone()) ? ""
						: mGoodsDto.getfPhone());
		GoodsManager_company = (MuInputEditText) findViewById(R.id.PublishGoods_company);
		GoodsManager_company.setText(TextUtils.isEmpty(mGoodsDto
				.getCompanyName()) ? "" : mGoodsDto.getCompanyName());
		GoodsManager_remark = (MuInputEditText) findViewById(R.id.PublishGoods_remark);
		GoodsManager_remark
				.setText(TextUtils.isEmpty(mGoodsDto.getRemark()) ? ""
						: mGoodsDto.getRemark());
		radio_pay1 = (RadioButton) findViewById(R.id.radio_pay1);
		radio_pay2 = (RadioButton) findViewById(R.id.radio_pay2);
		radio_pay3 = (RadioButton) findViewById(R.id.radio_pay3);
		radio_invoice1 = (RadioButton) findViewById(R.id.radio_invoice1);
		radio_invoice1
				.setOnCheckedChangeListener(new OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(CompoundButton buttonView,
							boolean isChecked) {
						if (isChecked) {
							GoodsManager_invoice_header_layout
									.setVisibility(View.VISIBLE);
						}
					}
				});
		radio_invoice2 = (RadioButton) findViewById(R.id.radio_invoice2);
		radio_invoice2
				.setOnCheckedChangeListener(new OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(CompoundButton buttonView,
							boolean isChecked) {
						if (isChecked) {
							GoodsManager_invoice_header_layout
									.setVisibility(View.GONE);
						}
					}
				});

		GoodsManager_invoice_header_layout = (RelativeLayout) findViewById(R.id.PublishGoods_invoice_header_layout);

		GoodsManager_invoice_header = (MuInputEditText) findViewById(R.id.PublishGoods_invoice_header);
		getPayType(mGoodsDto.getPayType());
		getInvoice(mGoodsDto.getIsNeedInvoice());

		editButton = (Button) findViewById(R.id.PublishGoods_ensure);
		editButton.setText("修改");

		PublishGoods_goods_photo = (RemoteImageView) findViewById(R.id.PublishGoods_goods_photo);
		PublishGoods_goods_photo.draw(null == mGoodsDto.getGoodsPicture() ? ""
				: mGoodsDto.getGoodsPicture().getHeaderImgURL(),
				ConstantPool.DEFAULT_ICON_PATH, false, false);
		PublishGoods_goods_photo.setOnClickListener(this);

		radio_attention1 = (RadioButton) findViewById(R.id.radio_attention1);
		radio_attention2 = (RadioButton) findViewById(R.id.radio_attention2);
		radio_attention3 = (RadioButton) findViewById(R.id.radio_attention3);
		radio_attention4 = (RadioButton) findViewById(R.id.radio_attention4);

		radio_transport1 = (RadioButton) findViewById(R.id.radio_transport1);
		radio_transport2 = (RadioButton) findViewById(R.id.radio_transport2);

		PublishGoods_Origin_detail = (MuInputEditText) findViewById(R.id.PublishGoods_Origin_detail);
		PublishGoods_Origin_detail.setText(TextUtils.isEmpty(mGoodsDto
				.getSetoutDetail()) ? "" : mGoodsDto.getSetoutDetail());
		PublishGoods_Destination_detail = (MuInputEditText) findViewById(R.id.PublishGoods_Destination_detail);
		PublishGoods_Destination_detail.setText(TextUtils.isEmpty(mGoodsDto
				.getDestinationDetail()) ? "" : mGoodsDto
				.getDestinationDetail());

		getNotice(mGoodsDto.getNotice());
		getDisMode(mGoodsDto.getDisMode());

		PublishGoods_back_cb = (CheckBox) findViewById(R.id.PublishGoods_back_cb);
		PublishGoods_back_cb.setChecked(mGoodsDto.getIsBackPre()
				.equalsIgnoreCase("Y") ? true : false);
	}

	/**
	 * 注意事项
	 * 
	 */
	private void getNotice(String notice) {
		if (TextUtils.isEmpty(notice))
			return;
		if (notice.equalsIgnoreCase(radio_attention1.getText().toString())) {
			radio_attention1.setChecked(true);
		} else if (notice.equalsIgnoreCase(radio_attention2.getText()
				.toString())) {
			radio_attention2.setChecked(true);
		} else if (notice.equalsIgnoreCase(radio_attention3.getText()
				.toString())) {
			radio_attention3.setChecked(true);
		} else if (notice.equalsIgnoreCase(radio_attention4.getText()
				.toString())) {
			radio_attention4.setChecked(true);
		}
	}

	/**
	 * 配送方式
	 * 
	 * @return
	 */
	private void getDisMode(String disMode) {
		if (TextUtils.isEmpty(disMode))
			return;
		if (disMode.equalsIgnoreCase(radio_transport1.getText().toString())) {
			radio_transport1.setChecked(true);
		} else if (disMode.equalsIgnoreCase(radio_transport2.getText()
				.toString())) {
			radio_transport2.setChecked(true);
		}
	}

	private Handler myHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case REFRESH_GOODS_TYPE:
				GoodsManager_GoodsType.setText(msg.obj.toString());
				break;
			case REFRESH_TRANSPORT_MODE:
				// PublishGoods_TransportMode.setText(msg.obj.toString());
				break;
			case REFRESH_CAR_TYPE:
				GoodsManager_CasType.setText(msg.obj.toString());
				break;
			case REFRESH_CAR_LENGTH:
				GoodsManager_CasLength.setText(msg.obj.toString() + "米");
				break;
			case REFRESH_CAR_PACKAGE:
				GoodsManager_packaging.setText(msg.obj.toString());
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

	private void getPayType(String type) {
		if (TextUtils.isEmpty(type))
			return;
		if (type.equalsIgnoreCase("3")) {
			radio_pay3.setChecked(true);
		} else if (type.equalsIgnoreCase("2")) {
			radio_pay2.setChecked(true);
		} else if (type.equalsIgnoreCase("1")) {
			radio_pay1.setChecked(true);
		}
	}

	private void getInvoice(String invoice) {
		if (TextUtils.isEmpty(invoice))
			return;
		if (invoice.equalsIgnoreCase("Y")) {
			radio_invoice1.setChecked(true);
		} else if (invoice.equalsIgnoreCase("N")) {
			radio_invoice2.setChecked(true);
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
						doEditGoods();
					}
				}).start();
			} else {
				ToastUtil.show(context, result);
			}
			break;
		case R.id.PublishGoods_cancel:
			CommonUtils.finishActivity(GoodsManagerDetailActivity.this);
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
				GoodsManagerDetailActivity.this);
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

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.maintitle_back_iv:
			CommonUtils.finishActivity(GoodsManagerDetailActivity.this);
			break;
		case R.id.maintitle_comfirm_tv:
			new Thread(new Runnable() {

				@Override
				public void run() {
					doDeleteGoods();
				}
			}).start();
			break;
		case R.id.PublishGoods_goods_photo:
			showOptionDialog(REQUEST_CODE_PHOTOALBUM, REQUEST_CODE_PHOTO);
			break;

		default:
			break;
		}
	}

	private void showOptionDialog(final int photoCode, final int cameraCode) {
		dialog = new SelectPicPopupWindow(GoodsManagerDetailActivity.this);
		dialog.setFirstButtonContent(getResources().getString(
				R.string.take_photo_hint));
		dialog.setFirstButtonOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				CommonUtils.selectCameraPhone(cameraCode, headerImgPath,
						GoodsManagerDetailActivity.this);
				dialog.dismiss();
			}
		});
		dialog.setSecendButtonContent(getResources().getString(
				R.string.get_system_photo_hint));
		dialog.setSecendButtonOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				CommonUtils.selectSystemPhone(photoCode,
						GoodsManagerDetailActivity.this);
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
				GoodsManagerDetailActivity.this.findViewById(R.id.main),
				Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0); // 设置layout在PopupWindow中显示的位置

	}

	/**
	 * 删除货源
	 */
	private void doDeleteGoods() {
		myHandler.sendEmptyMessage(SHOW_PROGRESS);
		PdaRequest<List<GoodsDto>> request = new PdaRequest<List<GoodsDto>>();
		List<GoodsDto> list = new ArrayList<GoodsDto>();
		list.add(mGoodsDto);
		request.setData(list);
		DeleteGoodsHandler dataHandler = new DeleteGoodsHandler(context,
				request);
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

		String result = goodsName.doFilter(GoodsManager_GoodsName.getText()
				.toString(), GoodsManager_GoodsType.getText().toString(),
				GoodsManager_GoodsWeight.getText().toString(),
				GoodsManager_CasType.getText().toString(),
				GoodsManager_GoodsBulk.getText().toString(),
				GoodsManager_CasLength.getText().toString(),
				GoodsManager_TransportPrices.getText().toString(),
				GoodsManager_Origin.getText().toString(),
				GoodsManager_Destination.getText().toString(),
				GoodsManager_phone.getText().toString(),
				GoodsManager_invoice_header.getText().toString());

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
				GoodsManagerDetailActivity.this);
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
		Intent intent = new Intent(GoodsManagerDetailActivity.this,
				SearchCityActivity.class);
		startActivityForResult(intent, GOODS_ORIGIN_RETURN);
	}

	/**
	 * 执行目的地
	 */
	private void doGoodsDestination() {
		Intent intent = new Intent(GoodsManagerDetailActivity.this,
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
				GoodsManagerDetailActivity.this);
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
				GoodsManagerDetailActivity.this);
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
				GoodsManagerDetailActivity.this);
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
			GoodsManager_shipment_time_start.setText(new StringBuilder()
					.append(mYear)
					.append("-")
					.append((mMonth + 1) < 10 ? "0" + (mMonth + 1)
							: (mMonth + 1)).append("-")
					.append((mDay < 10) ? "0" + mDay : mDay));
			break;
		case R.id.PublishGoods_shipment_time_end:
			GoodsManager_shipment_time_end.setText(new StringBuilder()
					.append(mYear)
					.append("-")
					.append((mMonth + 1) < 10 ? "0" + (mMonth + 1)
							: (mMonth + 1)).append("-")
					.append((mDay < 10) ? "0" + mDay : mDay));
			break;
		case R.id.PublishGoods_receipt_time_start:
			GoodsManager_receipt_time_start.setText(new StringBuilder()
					.append(mYear)
					.append("-")
					.append((mMonth + 1) < 10 ? "0" + (mMonth + 1)
							: (mMonth + 1)).append("-")
					.append((mDay < 10) ? "0" + mDay : mDay));
			break;
		case R.id.PublishGoods_receipt_time_end:
			GoodsManager_receipt_time_end.setText(new StringBuilder()
					.append(mYear)
					.append("-")
					.append((mMonth + 1) < 10 ? "0" + (mMonth + 1)
							: (mMonth + 1)).append("-")
					.append((mDay < 10) ? "0" + mDay : mDay));
			break;
		case R.id.PublishGoods_valid_time:
			GoodsManager_valid_time.setText(new StringBuilder()
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
	 * 修改货源
	 */
	private void doEditGoods() {
		myHandler.sendEmptyMessage(SHOW_PROGRESS);
		mGoodsDto.setGoodsName(GoodsManager_GoodsName.getText().toString());
		mGoodsDto.setGoodsType(GoodsManager_GoodsType.getText().toString());
		mGoodsDto.setGoodsWeight(BigDecimal.valueOf(Double
				.parseDouble(GoodsManager_GoodsWeight.getText().toString())));
		mGoodsDto.setGoodsVolume(Double.valueOf(GoodsManager_GoodsBulk
				.getText().toString()));
		mGoodsDto.setVehType(GoodsManager_CasType.getText().toString());
		mGoodsDto.setVehLegth(GoodsManager_CasLength.getText().toString());
		mGoodsDto.setPackages(GoodsManager_packaging.getText().toString());
		mGoodsDto
				.setCost(BigDecimal.valueOf(Double
						.parseDouble(GoodsManager_TransportPrices.getText()
								.toString())));
		mGoodsDto.setSetout(GoodsManager_Origin.getText().toString());
		mGoodsDto.setDestination(GoodsManager_Destination.getText().toString());
		// 装货日期，收货日期
		if (!GoodsManager_shipment_time.isChecked()) {
			try {
				mGoodsDto.setDeliveryDateF(new SimpleDateFormat("yyyy-MM-dd")
						.parse(GoodsManager_shipment_time_start.getText()
								.toString()));
				mGoodsDto.setDeliveryDateT(new SimpleDateFormat("yyyy-MM-dd")
						.parse(GoodsManager_shipment_time_end.getText()
								.toString()));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		} else {
			mGoodsDto.setDeliveryDateF(null);
			mGoodsDto.setDeliveryDateT(null);
		}

		if (!GoodsManager_receipt_time.isChecked()) {
			try {
				mGoodsDto.setReceiveDateF(new SimpleDateFormat("yyyy-MM-dd")
						.parse(GoodsManager_receipt_time_start.getText()
								.toString()));
				mGoodsDto.setReceiveDateT(new SimpleDateFormat("yyyy-MM-dd")
						.parse(GoodsManager_receipt_time_end.getText()
								.toString()));
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			mGoodsDto.setReceiveDateF(null);
			mGoodsDto.setReceiveDateT(null);
		}

		mGoodsDto.setValidDeadline(CommonUtils
				.parserTimestamp(GoodsManager_valid_time.getText().toString()));
		mGoodsDto.setContactName(GoodsManager_Contact.getText().toString());
		mGoodsDto.setmPhone(GoodsManager_phone.getText().toString());
		mGoodsDto.setfPhone(GoodsManager_telephone.getText().toString());
		mGoodsDto.setCompanyName(GoodsManager_company.getText().toString());
		mGoodsDto.setRemark(GoodsManager_remark.getText().toString());
		mGoodsDto.setPayType(getPayType());

		mGoodsDto.setIsNeedInvoice(getInvoice());
		mGoodsDto.setInvoiceTitle(GoodsManager_invoice_header.getText()
				.toString());

		mGoodsDto.setIsBackPre(PublishGoods_back_cb.isChecked() ? "Y" : "N");
		ImageDto imageDto = new ImageDto();
		imageDto.setImageSuffix("PNG");
		imageDto.setFile(null == resultBitmap ? null : CommonUtils
				.getBitmapByByte(resultBitmap));
		mGoodsDto.setGoodsPicture(imageDto);
		mGoodsDto.setSetoutDetail(PublishGoods_Origin_detail.getText()
				.toString());
		mGoodsDto.setDestinationDetail(PublishGoods_Destination_detail
				.getText().toString());
		mGoodsDto.setNotice(getNotice());
		mGoodsDto.setDisMode(getDisMode());

		PdaRequest<GoodsDto> request = new PdaRequest<GoodsDto>();
		request.setData(mGoodsDto);
		EditGoodsInfoHandler dataHandler = new EditGoodsInfoHandler(context,
				request);
		dataHandler.setOnDataReceiveListener(this);
		dataHandler.startNetWork();
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

	@Override
	public void onDataReceive(DataHandler dataHandler, int resultCode,
			Object data, int type) {
		myHandler.sendEmptyMessage(CLOSE_PROGRESS);
		switch (resultCode) {
		case NetWork.EDIT_GOODS_INFO_OK:
			doEditGoodsSuccess(data);
			break;
		case NetWork.DELETE_GOODS_OK:
			doDeleteGoodsSuccess(data);
			break;
		case NetWork.DELETE_GOODS_ERROR:
		case NetWork.EDIT_GOODS_INFO_ERROR:
			ToastUtil.show(context,
					getResources().getString(R.string.network_error_hint));
			break;

		default:
			break;
		}
	}

	/**
	 * 删除货源成功
	 * 
	 * @param data
	 */
	private void doDeleteGoodsSuccess(Object data) {
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
			String message = result.substring(result.indexOf("#") + 1,
					result.length());
			if (!response.isSuccess()) {
				ToastUtil.show(context, "删除货源信息失败，请重新删除");
				Message msg = myHandler.obtainMessage();
				msg.what = SHOW_TOAST;
				msg.obj = message;
				myHandler.sendMessage(msg);
				return;
			}
			Message msg = myHandler.obtainMessage();
			msg.what = SHOW_TOAST;
			msg.obj = "删除货源信息成功";
			myHandler.sendMessage(msg);
			Intent intent = new Intent();
			intent.putExtra("goodsInfo", mGoodsDto);
			setResult(RESULT_OK, intent);
			finish();
		} catch (Exception e) {
			ToastUtil.show(context, "删除货源信息失败，请重新删除");
			e.printStackTrace();
		}
	}

	/**
	 * 修改货源成功
	 * 
	 * @param data
	 */
	private void doEditGoodsSuccess(Object data) {
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
			String message = result.substring(result.indexOf("#") + 1,
					result.length());
			if (!response.isSuccess()) {
				Message msg = myHandler.obtainMessage();
				msg.what = SHOW_TOAST;
				msg.obj = message;
				myHandler.sendMessage(msg);
				return;
			}
			Message msg = myHandler.obtainMessage();
			msg.what = SHOW_TOAST;
			msg.obj = "修改货源信息成功";
			myHandler.sendMessage(msg);
			Intent intent = new Intent();
			intent.putExtra("goodsInfo", mGoodsDto);
			intent.putExtra("isEdit", true);
			setResult(RESULT_OK, intent);
			finish();
		} catch (Exception e) {
			ToastUtil.show(context, "修改货源信息失败，请重新修改");
			e.printStackTrace();
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode != RESULT_OK)
			return;
		switch (requestCode) {
		case GOODS_ORIGIN_RETURN:
			GoodsManager_Origin.setText(data.getStringExtra("place"));
			break;
		case GOODS_DESTINATION_RETURN:
			GoodsManager_Destination.setText(data.getStringExtra("place"));
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
			PublishGoods_goods_photo.setImageBitmap(resultBitmap);
			break;
		case REQUEST_CODE_PICK:
			resultBitmap = BitmapFactory
					.decodeFile(ConstantPool.DEFAULT_ICON_PATH
							+ "image_diy_resultphoto.jpg");
			PublishGoods_goods_photo.setImageBitmap(resultBitmap);
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
}
