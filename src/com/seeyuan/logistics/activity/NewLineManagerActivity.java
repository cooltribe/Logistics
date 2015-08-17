package com.seeyuan.logistics.activity;

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
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;

import com.baidu.platform.comapi.map.a.l;
import com.seeyuan.logistics.R;
import com.seeyuan.logistics.application.NetWork;
import com.seeyuan.logistics.customview.MuInputEditText;
import com.seeyuan.logistics.customview.ProgressAlertDialog;
import com.seeyuan.logistics.customview.SingleSelectAlertDlialog;
import com.seeyuan.logistics.datacenter.DataHandler;
import com.seeyuan.logistics.datacenter.OnDataReceiveListener;
import com.seeyuan.logistics.datahandler.DeleteLineHandler;
import com.seeyuan.logistics.datahandler.PublishCarSourceHandler;
import com.seeyuan.logistics.datahandler.UpdateLineHandler;
import com.seeyuan.logistics.entity.CarsDto;
import com.seeyuan.logistics.entity.DriverDto;
import com.seeyuan.logistics.entity.DriverManagerInfo;
import com.seeyuan.logistics.entity.PdaRequest;
import com.seeyuan.logistics.entity.PdaResponse;
import com.seeyuan.logistics.entity.RouteDto;
import com.seeyuan.logistics.jsonparser.CarSourceJsonParser;
import com.seeyuan.logistics.jsonparser.ResultCodeJsonParser;
import com.seeyuan.logistics.util.CommonUtils;
import com.seeyuan.logistics.util.ToastUtil;

/**
 * 添加新线路
 * 
 * @author zhazhaobao
 * 
 */
/**
 * @author Administrator
 * 
 */
@SuppressLint("HandlerLeak")
public class NewLineManagerActivity extends BaseActivity implements
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

	private CarsDto carManagerInfo;
	private RouteDto carManagerLineInfo;

	/**
	 * 车牌号
	 */
	private TextView new_line_manager_car_plate;
	/**
	 * 司机
	 */
	private TextView new_line_manager_driver;
	/**
	 * 始发地
	 */
	private TextView new_line_manager_from;
	/**
	 * 目的地
	 */
	private TextView new_line_manager_to;

	/**
	 * 路线状态
	 */
	private TextView new_line_manager_type;

	/**
	 * 有效期限
	 */
	private TextView new_line_manager_valid_time;

	/**
	 * 始发地，数据返回
	 */
	private final int START_PLACE_RETURN = 100;
	/**
	 * 目的地，数据返回
	 */
	private final int END_PLACE_RETURN = 101;

	/**
	 * 路线状态，数据返回
	 */
	private final int LINE_TYPE_RETURN = 102;

	/**
	 * 有效期限，数据返回
	 */
	private final int REFRESH_VAILD_TIME = 103;

	/**
	 * 路线状态，数据刷新
	 */
	private final int REFRESH_LINE_TYPE = 104;

	/**
	 * 选择司机，数据返回
	 */
	private final int REFRESH_DRIVER = 105;

	private Context context;

	private int linePosition;

	/**
	 * 是否 修改，并非重新添加
	 */
	private boolean isReedit = false;

	private static final int SHOW_DATAPICK = 0;
	private static final int DATE_DIALOG_ID = 1;
	private static final int SHOW_TIMEPICK = 2;
	private static final int TIME_DIALOG_ID = 3;
	/**
	 * 途经地，数据返回
	 */
	private final int REFRESH_THROUGH_TO = 1005;
	

	private int mYear;
	private int mMonth;
	private int mDay;
	private int mHour;
	private int mMinute;

	private DriverDto driverDto = new DriverDto();

	/**
	 * 单价:元/吨
	 */
	private MuInputEditText publish_car_pricet;

	/**
	 * 单价:元/方
	 */
	private MuInputEditText publish_car_pricem;

	/**
	 * 途经地
	 */
	private Button PublishCar_through_to;

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

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.activity_new_line_manager); // 软件activity的布局
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,
				R.layout.deflaut_titlebar); // titlebar为自己标题栏的布局
		carManagerInfo = (CarsDto) getIntent().getSerializableExtra(
				"carManagerInfo");
		isReedit = getIntent().getBooleanExtra("editModel", false);
		linePosition = getIntent().getIntExtra("linePosition", -1);

		if (!isReedit) {
			carManagerLineInfo = new RouteDto();
		} else {
			carManagerLineInfo = carManagerInfo.getRoutes().get(linePosition);
			driverDto.setDriverId(carManagerLineInfo.getDriverId());
			driverDto.setDriverName(carManagerLineInfo.getDriverName());
		}
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
		defaulttitle_title_tv.setText(getIntent().getStringExtra("lineTitle"));

		maintitle_comfirm_tv = (TextView) findViewById(R.id.maintitle_comfirm_tv);
		maintitle_comfirm_tv.setText(getResources().getString(R.string.delete));
		maintitle_comfirm_tv.setVisibility(isReedit ? View.VISIBLE : View.GONE);
		maintitle_comfirm_tv.setOnClickListener(this);

		new_line_manager_car_plate = (TextView) findViewById(R.id.new_line_manager_car_plate);
		new_line_manager_car_plate.setText(TextUtils.isEmpty(carManagerInfo
				.getVehicleNum()) ? "" : carManagerInfo.getVehicleNum());

		new_line_manager_driver = (TextView) findViewById(R.id.new_line_manager_driver);
		new_line_manager_driver.setText(null == carManagerLineInfo
				.getDriverName() ? "" : TextUtils.isEmpty(carManagerLineInfo
				.getDriverName()) ? "" : carManagerLineInfo.getDriverName());
		new_line_manager_driver.setOnClickListener(this);

		new_line_manager_from = (TextView) findViewById(R.id.new_line_manager_from);
		new_line_manager_from.setText(TextUtils.isEmpty(carManagerLineInfo
				.getSetout()) ? "" : carManagerLineInfo.getSetout());
		new_line_manager_from.setOnClickListener(this);

		new_line_manager_to = (TextView) findViewById(R.id.new_line_manager_to);
		new_line_manager_to.setText(TextUtils.isEmpty(carManagerLineInfo
				.getDestination()) ? "" : carManagerLineInfo.getDestination());
		new_line_manager_to.setOnClickListener(this);

		new_line_manager_type = (TextView) findViewById(R.id.new_line_manager_type);
		new_line_manager_type.setText(TextUtils.isEmpty(carManagerLineInfo
				.getType()) ? "" : CommonUtils
				.parserLineType(carManagerLineInfo.getType()));
		new_line_manager_type.setOnClickListener(this);

		new_line_manager_valid_time = (TextView) findViewById(R.id.new_line_manager_valid_time);
		new_line_manager_valid_time.setText(null == carManagerLineInfo
				.getValidDeadline() ? "" : CommonUtils
				.parserTimestamp(carManagerLineInfo.getValidDeadline()));
		new_line_manager_valid_time.setOnClickListener(this);
		PublishCar_through_to = (Button) findViewById(R.id.PublishCar_through_to);
		PublishCar_through_to.setText(TextUtils.isEmpty(carManagerLineInfo
				.getPath()) ? "" : carManagerLineInfo.getPath());
		PublishCar_through_to.setOnClickListener(this);
		publish_car_pricet = (MuInputEditText) findViewById(R.id.publish_car_pricet);
		publish_car_pricet.setText(null == carManagerLineInfo.getPriceT() ? ""
				: "" + carManagerLineInfo.getPriceT());
		publish_car_pricem = (MuInputEditText) findViewById(R.id.publish_car_pricem);
		publish_car_pricem.setText(null == carManagerLineInfo.getPriceM() ? ""
				: "" + carManagerLineInfo.getPriceM());
	}

	/**
	 * 途经地
	 */
	private void doThroughTo() {
		Intent intent = new Intent(NewLineManagerActivity.this,
				SearchCityActivity.class);
		intent.putExtra("isCanMultipleChoice", true);
		intent.putExtra("mulitpleChoiceMaxNum", 7);
		startActivityForResult(intent, REFRESH_THROUGH_TO);
	}

	private Handler myHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case REFRESH_VAILD_TIME:
				new_line_manager_valid_time.setText(msg.obj.toString() + "天");
				// carManagerLineInfo.setValidTime(msg.obj.toString() + "天");
				break;
			case REFRESH_LINE_TYPE:
				new_line_manager_type.setText(msg.obj.toString());
				// carManagerLineInfo.setLineType(msg.obj.toString());
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
	 * 更新时间显示
	 */
	private void updateTimeDisplay() {
		new_line_manager_valid_time.setText(new StringBuilder().append(mYear)
				.append("-")
				.append((mMonth + 1) < 10 ? "0" + (mMonth + 1) : (mMonth + 1))
				.append("-").append((mDay < 10) ? "0" + mDay : mDay)
				.append("   ").append(mHour).append(":")
				.append((mMinute < 10) ? "0" + mMinute : mMinute));
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

			updateDateDisplay();
			// doSelectTimeData();
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

			updateTimeDisplay();
		}
	};

	/**
	 * 更新日期显示
	 */
	private void updateDateDisplay() {
		new_line_manager_valid_time.setText(new StringBuilder().append(mYear)
				.append("-")
				.append((mMonth + 1) < 10 ? "0" + (mMonth + 1) : (mMonth + 1))
				.append("-").append((mDay < 10) ? "0" + mDay : mDay)
		/*
		 * .append("   ").append(mHour).append(":") .append((mMinute < 10) ? "0"
		 * + mMinute : mMinute)
		 */);
		// carManagerLineInfo.setValidTime(new_line_manager_valid_time.getText()
		// .toString());
	}

	/**
	 * 时间选择
	 */
	private void doSelectTimeData() {
		Message msg = new Message();
		msg.what = SHOW_TIMEPICK;
		dateandtimeHandler.sendMessage(msg);
	}

	@Override
	public void onClickListener(View view) {
		switch (view.getId()) {
		case R.id.new_line_manager_delete:
			doAddNewCar();
			break;
		case R.id.new_line_manager_cancel:
			CommonUtils.finishActivity(NewLineManagerActivity.this);
			break;

		default:
			break;
		}
	}

	private void doAddNewCar() {
		String result = isAddNewCar();
		if (result.equalsIgnoreCase("成功")) {
			new Thread(new Runnable() {

				@Override
				public void run() {
					if (isReedit) {
						doModifyPublishCar();
					} else {
						doSubmitPublishCar();
					}
				}
			}).start();
		} else {
			ToastUtil.show(context, result);
		}
	}

	/**
	 * 提交，发布车源
	 */
	/**
	 * 修改车源
	 */
	private void doModifyPublishCar() {
		myHandler.sendEmptyMessage(SHOW_PROGRESS);
		carManagerLineInfo.setVehicleNum(new_line_manager_car_plate.getText()
				.toString());
		carManagerLineInfo.setDriverId(driverDto.getDriverId());
		carManagerLineInfo.setDriverName(driverDto.getDriverName());
		carManagerLineInfo
				.setSetout(new_line_manager_from.getText().toString());
		carManagerLineInfo.setDestination(new_line_manager_to.getText()
				.toString());
		carManagerLineInfo.setType(CommonUtils
				.getLineTypeID(new_line_manager_type.getText().toString()));
		carManagerLineInfo.setValidDeadline(CommonUtils
				.parserTimestamp(new_line_manager_valid_time.getText()
						.toString()));
		carManagerLineInfo.setPath(PublishCar_through_to.getText().toString());
		carManagerLineInfo.setPriceM(TextUtils.isEmpty(publish_car_pricem
				.getText().toString()) ? 0 : Integer
				.parseInt(publish_car_pricem.getText().toString()));
		carManagerLineInfo.setPriceT(TextUtils.isEmpty(publish_car_pricet
				.getText().toString()) ? 0 : Integer
				.parseInt(publish_car_pricet.getText().toString()));
		PdaRequest<RouteDto> request = new PdaRequest<RouteDto>();
		request.setData(carManagerLineInfo);
		UpdateLineHandler dataHandler = new UpdateLineHandler(context, request);
		dataHandler.setOnDataReceiveListener(this);
		dataHandler.startNetWork();
	}
	
	/**
	 * 修改车源
	 */
	private void doSubmitPublishCar() {
		myHandler.sendEmptyMessage(SHOW_PROGRESS);
		carManagerLineInfo.setVehicleNum(new_line_manager_car_plate.getText()
				.toString());
		carManagerLineInfo.setDriverId(driverDto.getDriverId());
		carManagerLineInfo.setDriverName(driverDto.getDriverName());
		carManagerLineInfo
				.setSetout(new_line_manager_from.getText().toString());
		carManagerLineInfo.setDestination(new_line_manager_to.getText()
				.toString());
		carManagerLineInfo.setType(CommonUtils
				.getLineTypeID(new_line_manager_type.getText().toString()));
		carManagerLineInfo.setValidDeadline(CommonUtils
				.parserTimestamp(new_line_manager_valid_time.getText()
						.toString()));
		carManagerLineInfo.setPath(PublishCar_through_to.getText().toString());
		carManagerLineInfo.setPriceM(TextUtils.isEmpty(publish_car_pricem
				.getText().toString()) ? 0 : Integer
				.parseInt(publish_car_pricem.getText().toString()));
		carManagerLineInfo.setPriceT(TextUtils.isEmpty(publish_car_pricet
				.getText().toString()) ? 0 : Integer
				.parseInt(publish_car_pricet.getText().toString()));
		PdaRequest<RouteDto> request = new PdaRequest<RouteDto>();
		request.setData(carManagerLineInfo);
		PublishCarSourceHandler dataHandler = new PublishCarSourceHandler(
				context, request);
		dataHandler.setOnDataReceiveListener(this);
		dataHandler.startNetWork();
	}

	/**
	 * 添加新车辆
	 */
	private String isAddNewCar() {
		Filter carPlate = new carPlateFilder();
		Filter driver = new driverFilder();
		Filter from = new fromFilder();
		Filter to = new toFilder();
		Filter lineType = new lineTypeFilder();
		Filter validTime = new validTimeFilder();

		carPlate.setNext(driver);
		driver.setNext(from);
		from.setNext(to);
		to.setNext(lineType);
		lineType.setNext(validTime);

		String result = carPlate.doFilter(new_line_manager_car_plate.getText()
				.toString(), new_line_manager_driver.getText().toString(),
				new_line_manager_from.getText().toString(), new_line_manager_to
						.getText().toString(), new_line_manager_type.getText()
						.toString(), new_line_manager_valid_time.getText()
						.toString());
		return result;
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.maintitle_back_iv:
			CommonUtils.finishActivity(NewLineManagerActivity.this);
			break;
		case R.id.maintitle_comfirm_tv:
			new Thread(new Runnable() {

				@Override
				public void run() {
					doDeleteLine();
				}
			}).start();
			break;
		case R.id.new_line_manager_from:
			Intent startIntent = new Intent(NewLineManagerActivity.this,
					SearchCityActivity.class);
			startActivityForResult(startIntent, START_PLACE_RETURN);
			break;
		case R.id.new_line_manager_to:
			Intent endIntent = new Intent(NewLineManagerActivity.this,
					SearchCityActivity.class);
			startActivityForResult(endIntent, END_PLACE_RETURN);
			break;
		case R.id.new_line_manager_valid_time:
			// doSelectVaildTime();
			doSelectDayData();
			break;
		case R.id.new_line_manager_type:
			doLineType();
			break;
		case R.id.new_line_manager_driver:
			doDriver();
			break;

		case R.id.PublishCar_through_to:
			doThroughTo();
			break;
		}
	}

	/**
	 * 删除线路信息
	 */
	private void doDeleteLine() {
		myHandler.sendEmptyMessage(SHOW_PROGRESS);
		PdaRequest<RouteDto> request = new PdaRequest<RouteDto>();
		request.setData(carManagerLineInfo);
		DeleteLineHandler dataHandler = new DeleteLineHandler(context, request);
		dataHandler.setOnDataReceiveListener(this);
		dataHandler.startNetWork();
	}

	/**
	 * 选择司机
	 */
	private void doDriver() {
		Intent intent = new Intent(NewLineManagerActivity.this,
				SelectDriverManagerActivity.class);
		startActivityForResult(intent, REFRESH_DRIVER);
	}

	/**
	 * 路线状态
	 */
	private void doLineType() {

		final List<String> mDataList = new ArrayList<String>();
		TypedArray typedArray = getResources().obtainTypedArray(
				R.array.line_type);
		int size = typedArray.length();
		for (int i = 0; i < size; i++) {
			mDataList.add(typedArray.getString(i));
		}

		final SingleSelectAlertDlialog ad = new SingleSelectAlertDlialog(
				NewLineManagerActivity.this);
		ad.setTitle(getResources().getString(R.string.line_type_hint));
		ad.setListContentForNormalText(mDataList);
		ad.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View view,
					int position, long arg3) {
				if (null == view)
					return;
				ad.dismiss();
				Message message = myHandler.obtainMessage();
				message.what = REFRESH_LINE_TYPE;
				message.obj = mDataList.get(position);
				myHandler.sendMessage(message);

			}
		});
		typedArray.recycle();

	}

	/**
	 * 日期选择
	 */
	private void doSelectDayData() {
		Message msg = new Message();
		msg.what = SHOW_DATAPICK;
		dateandtimeHandler.sendMessage(msg);
	}

	/**
	 * 选择有效日期
	 */
	private void doSelectVaildTime() {

		final List<String> mDataList = new ArrayList<String>();
		TypedArray typedArray = getResources().obtainTypedArray(
				R.array.Valid_time);
		int size = typedArray.length();
		for (int i = 0; i < size; i++) {
			mDataList.add(typedArray.getString(i));
		}

		final SingleSelectAlertDlialog ad = new SingleSelectAlertDlialog(
				NewLineManagerActivity.this);
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

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode != RESULT_OK)
			return;
		switch (requestCode) {
		case START_PLACE_RETURN:
			new_line_manager_from.setText(data.getStringExtra("place"));
			carManagerLineInfo.setSetout(data.getStringExtra("place"));
			break;
		case END_PLACE_RETURN:
			new_line_manager_to.setText(data.getStringExtra("place"));
			carManagerLineInfo.setDestination(data.getStringExtra("place"));
			break;
		case REFRESH_DRIVER:
			driverDto = (DriverDto) data.getSerializableExtra("driverInfo");
			new_line_manager_driver.setText(driverDto.getDriverName());
			// carManagerLineInfo.setDriver(info);

			break;

		case REFRESH_THROUGH_TO:
			PublishCar_through_to.setText(data.getStringExtra("place"));
			carManagerLineInfo.setPath(data.getStringExtra("place"));
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

		public String doFilter(String carPlate, String driver, String from,
				String to, String lineType, String validTime) {

			if (next == null) {
				return "成功";
			} else
				return next.doFilter(carPlate, driver, from, to, lineType,
						validTime);

		}

	}

	class carPlateFilder extends Filter {
		@Override
		public String doFilter(String carPlate, String driver, String from,
				String to, String lineType, String validTime) {
			if (TextUtils.isEmpty(carPlate)) {
				return "请输入正确的车牌";
			} else
				return super.doFilter(carPlate, driver, from, to, lineType,
						validTime);
		}
	}

	class driverFilder extends Filter {
		@Override
		public String doFilter(String carPlate, String driver, String from,
				String to, String lineType, String validTime) {
			if (TextUtils.isEmpty(driver)) {
				return "请输入正确的司机";
			} else
				return super.doFilter(carPlate, driver, from, to, lineType,
						validTime);
		}
	}

	class fromFilder extends Filter {
		@Override
		public String doFilter(String carPlate, String driver, String from,
				String to, String lineType, String validTime) {
			if (TextUtils.isEmpty(from)) {
				return "请输入正确的始发地";
			} else
				return super.doFilter(carPlate, driver, from, to, lineType,
						validTime);
		}
	}

	class toFilder extends Filter {
		@Override
		public String doFilter(String carPlate, String driver, String from,
				String to, String lineType, String validTime) {
			if (TextUtils.isEmpty(to)) {
				return "请输入正确的目的地";
			} else
				return super.doFilter(carPlate, driver, from, to, lineType,
						validTime);
		}
	}

	class lineTypeFilder extends Filter {
		@Override
		public String doFilter(String carPlate, String driver, String from,
				String to, String lineType, String validTime) {
			if (TextUtils.isEmpty(lineType)) {
				return "请输入正确的路线状态";
			} else
				return super.doFilter(carPlate, driver, from, to, lineType,
						validTime);
		}
	}

	class validTimeFilder extends Filter {
		@Override
		public String doFilter(String carPlate, String driver, String from,
				String to, String lineType, String validTime) {
			if (TextUtils.isEmpty(validTime)) {
				return "请输入正确的有效期限";
			} else
				return super.doFilter(carPlate, driver, from, to, lineType,
						validTime);
		}
	}

	private void doPublishCarSuccess(Object data) {
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
			message = result.substring(result.indexOf("#") + 1,
					result.length());
			if (!response.isSuccess()) {
				Message msg = myHandler.obtainMessage();
				msg.what = SHOW_TOAST;
				msg.obj = message;
				myHandler.sendMessage(msg);
				return;
			} else {
				ToastUtil.show(context, "发布路线成功");
				Intent intent = new Intent(this,
						CarManagerActivity.class);
				intent.putExtra("isNomalGetIn", false);
				startActivity(intent);
				CommonUtils.finishActivity(this);
			
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void onDataReceive(DataHandler dataHandler, int resultCode,
			Object data, int type) {
		myHandler.sendEmptyMessage(CLOSE_PROGRESS);
		switch (resultCode) {
		case NetWork.UPDATA_LINE_OK:
			doUpdateLineSuccess(data);
			break;
		case NetWork.DELETE_LINE_OK:
			doDeleteLineSuccess(data);
			break;
		case NetWork.DELETE_LINE_ERROR:
		case NetWork.UPDATA_LINE_ERROR:
			ToastUtil.show(context,
					getResources().getString(R.string.network_error_hint));
			break;

		case NetWork.PUBLISH_CAR_SOURCE_OK:
			doPublishCarSuccess(data);
			break;
		case NetWork.PUBLISH_CAR_SOURCE_ERROR:
			ToastUtil.show(context,
					getResources().getString(R.string.network_error_hint));
			break;
		}
	}

	/**
	 * 删除线路成功
	 * 
	 * @param data
	 */
	private void doDeleteLineSuccess(Object data) {
		String dataString = null;
		try {
			dataString = new String((byte[]) data, "UTF-8");
//			Log.i("删除结果", dataString);
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			PdaResponse<String> mData = ResultCodeJsonParser
					.parserResultCodeJson(dataString);
			String result = mData.getMessage();
			int messageCode = Integer.parseInt(result.substring(0,
					result.indexOf("#")));
			String message = result.substring(result.indexOf("#") + 1,
					result.length());
			Message msg = myHandler.obtainMessage();
			msg.what = SHOW_TOAST;
			if (null == mData || !mData.isSuccess()) {
				msg.obj = message;
				myHandler.sendMessage(msg);
				return;
			} else {
				List<RouteDto> content = carManagerInfo.getRoutes();
				content.remove(linePosition);
				carManagerInfo.setRoutes(content);
				Intent intent = new Intent();
				intent.putExtra("carManagerInfo", carManagerInfo);
				setResult(RESULT_OK, intent);
				finish();
			}
		} catch (Exception e) {
			e.printStackTrace();
			ToastUtil.show(context, "删除线路失败，请重新删除");
		}
	}

	private void doUpdateLineSuccess(Object data) {
		String dataString = null;
		try {
			dataString = new String((byte[]) data, "UTF-8");
//			Log.i("添加信息", dataString);
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			PdaResponse<String> mData = ResultCodeJsonParser
					.parserResultCodeJson(dataString);
			String result = mData.getMessage();
			int messageCode = Integer.parseInt(result.substring(0,
					result.indexOf("#")));
			String message = result.substring(result.indexOf("#") + 1,
					result.length());
			Message msg = myHandler.obtainMessage();
			msg.what = SHOW_TOAST;
			if (null == mData || !mData.isSuccess()) {
				msg.obj = message;
				myHandler.sendMessage(msg);
				return;
			} else {
				ToastUtil.show(context, "修改货源成功");
				List<RouteDto> content = carManagerInfo.getRoutes();
				if (isReedit) {
					content.set(linePosition, carManagerLineInfo);
				} else {
					content.add(carManagerLineInfo);
				}
				carManagerInfo.setRoutes(content);
				Intent intent = new Intent();
				intent.putExtra("carManagerInfo", carManagerInfo);
				setResult(RESULT_OK, intent);
				finish();
			}
		} catch (Exception e) {
			ToastUtil.show(context, "修改货源失败，请重新修改");
			e.printStackTrace();
		}
	}
}