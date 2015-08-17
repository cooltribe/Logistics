package com.seeyuan.logistics.activity;

import java.io.File;
import java.util.Calendar;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
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
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;

import com.mesada.nj.pubcontrols.controls.RemoteImageView;
import com.seeyuan.logistics.R;
import com.seeyuan.logistics.application.ConstantPool;
import com.seeyuan.logistics.application.NetWork;
import com.seeyuan.logistics.customview.MuInputEditText;
import com.seeyuan.logistics.customview.ProgressAlertDialog;
import com.seeyuan.logistics.customview.SelectPicPopupWindow;
import com.seeyuan.logistics.datacenter.DataHandler;
import com.seeyuan.logistics.datacenter.OnDataReceiveListener;
import com.seeyuan.logistics.datahandler.CertificationHandler;
import com.seeyuan.logistics.datahandler.GetCertificationInfoHandler;
import com.seeyuan.logistics.entity.ImageDto;
import com.seeyuan.logistics.entity.MemberAuthDto;
import com.seeyuan.logistics.entity.PdaRequest;
import com.seeyuan.logistics.entity.PdaResponse;
import com.seeyuan.logistics.jsonparser.GetCertificationJsonParser;
import com.seeyuan.logistics.jsonparser.ResultCodeJsonParser;
import com.seeyuan.logistics.util.CommonUtils;
import com.seeyuan.logistics.util.ToastUtil;

/**
 * 实名认证
 * 
 * @author zhazhaobao
 * 
 */
public class CertificationActivity extends BaseActivity implements
		OnClickListener, OnDataReceiveListener {
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
	 * 有效期 开始
	 */
	private TextView certification_idcard_validtime_from;
	/**
	 * 有效期 结束
	 */
	private TextView certification_idcard_validtime_to;

	/**
	 * 真实姓名
	 */
	private MuInputEditText certification_name;

	/**
	 * 身份证号码
	 */
	private MuInputEditText certification_idcard;

	/**
	 * 身份证，正面照
	 */
	private RemoteImageView certification_idcard_photo_front;
	private ImageDto frontDto = new ImageDto();
	private String frontDtoFlag = "";
	private ImageDto backDto = new ImageDto();
	private String backDtoFlag = "";

	/**
	 * 身份证，反面照
	 */
	private RemoteImageView certification_idcard_photo_behind;

	private MemberAuthDto mMemberAuthDto = new MemberAuthDto();

	// 身份证照，正面
	private final int IDCARDF_REQUEST_CODE_PHOTOALBUM = 500;
	private final int IDCARDF_REQUEST_CODE_PHOTO = 501;
	private final int IDCARDF_REQUEST_CODE_PHOTOOK = 502;
	private final int IDCARDF_REQUEST_CODE_PICK = 503;

	// 身份证照，反面
	private final int IDCARDB_REQUEST_CODE_PHOTOALBUM = 600;
	private final int IDCARDB_REQUEST_CODE_PHOTO = 601;
	private final int IDCARDB_REQUEST_CODE_PHOTOOK = 602;
	private final int IDCARDB_REQUEST_CODE_PICK = 603;

	private static final int SHOW_DATAPICK = 0;
	private static final int DATE_DIALOG_ID = 1;
	private static final int SHOW_TIMEPICK = 2;
	private static final int TIME_DIALOG_ID = 3;

	private int timeSelectID = -1;

	private int mYear;
	private int mMonth;
	private int mDay;
	private int mHour;
	private int mMinute;

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
	 * 认证提示
	 */
	private TextView certification_hint;

	/**
	 * 提交按钮
	 */
	private Button certification_ensure;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.activity_certification); // 软件activity的布局
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,
				R.layout.deflaut_titlebar); // titlebar为自己标题栏的布局

		context = getApplicationContext();
		initView();
		setDateTime();
		setTimeOfDay();

		getCertificationInfo();
	}

	@Override
	public void initView() {
		maintitle_back_iv = (ImageView) findViewById(R.id.maintitle_back_iv);
		maintitle_back_iv.setOnClickListener(this);

		defaulttitle_title_tv = (TextView) findViewById(R.id.defaulttitle_title_tv);
		defaulttitle_title_tv.setText(R.string.certification_hint);

		certification_idcard_validtime_from = (TextView) findViewById(R.id.certification_idcard_validtime_from);
		certification_idcard_validtime_from.setOnClickListener(this);
		certification_idcard_validtime_to = (TextView) findViewById(R.id.certification_idcard_validtime_to);
		certification_idcard_validtime_to.setOnClickListener(this);
		certification_name = (MuInputEditText) findViewById(R.id.certification_name);
		certification_idcard = (MuInputEditText) findViewById(R.id.certification_idcard);
		certification_idcard_photo_front = (RemoteImageView) findViewById(R.id.certification_idcard_photo_front);
		certification_idcard_photo_front.setOnClickListener(this);
		certification_idcard_photo_behind = (RemoteImageView) findViewById(R.id.certification_idcard_photo_behind);
		certification_idcard_photo_behind.setOnClickListener(this);

		certification_hint = (TextView) findViewById(R.id.certification_hint);

		certification_ensure = (Button) findViewById(R.id.certification_ensure);

	}

	private void showView(MemberAuthDto mMemberAuthDto) {
		if (mMemberAuthDto == null)
			return;
		certification_idcard_validtime_from.setText(null == mMemberAuthDto
				.getIdLimitFrom() ? "" : CommonUtils.parserData(mMemberAuthDto
				.getIdLimitFrom()));
		certification_idcard_validtime_to.setText(null == mMemberAuthDto
				.getIdLimitTo() ? "" : CommonUtils.parserData(mMemberAuthDto
				.getIdLimitTo()));
		certification_name.setText(TextUtils.isEmpty(mMemberAuthDto
				.getRealName()) ? "" : mMemberAuthDto.getRealName());
		certification_idcard.setText(TextUtils.isEmpty(mMemberAuthDto
				.getIdNumber().toString()) ? "" : mMemberAuthDto.getIdNumber());
		certification_idcard_photo_front.draw(null == mMemberAuthDto
				.getIdPictureFront() ? "" : mMemberAuthDto.getIdPictureFront()
				.getHeaderImgURL(), ConstantPool.DEFAULT_ICON_PATH, false,
				false);
		certification_idcard_photo_behind.draw(null == mMemberAuthDto
				.getIdPictrueBack() ? "" : mMemberAuthDto.getIdPictrueBack()
				.getHeaderImgURL(), ConstantPool.DEFAULT_ICON_PATH, false,
				false);
		frontDtoFlag = null == mMemberAuthDto.getIdPictureFront() ? "" : "Y";
		backDtoFlag = null == mMemberAuthDto.getIdPictrueBack() ? "" : "Y";
	}

	private void doAllItemEnable() {
		certification_idcard_validtime_from.setEnabled(false);
		certification_idcard_validtime_from.setEnabled(false);
		certification_idcard_validtime_to.setEnabled(false);
		certification_name.setEnabled(false);
		certification_name.setCanTouch(false);
		certification_idcard.setEnabled(false);
		certification_idcard.setCanTouch(false);
		certification_idcard_photo_front.setEnabled(false);
		certification_idcard_photo_behind.setEnabled(false);
		certification_ensure.setVisibility(View.GONE);

	}

	private void doAllItemAble() {
		certification_idcard_validtime_from.setEnabled(true);
		certification_idcard_validtime_from.setEnabled(true);
		certification_idcard_validtime_to.setEnabled(true);
		certification_name.setEnabled(true);
		certification_idcard.setEnabled(true);
		certification_name.setCanTouch(true);
		certification_idcard.setCanTouch(true);
		certification_idcard_photo_front.setEnabled(true);
		certification_idcard_photo_behind.setEnabled(true);
		certification_ensure.setVisibility(View.VISIBLE);

	}

	private Handler myHandler = new Handler() {
		public void handleMessage(Message msg) {
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
			case 406:
			case 408:
				ToastUtil.show(context, msg.obj.toString());
				doAllItemAble();
				break;
			case 407:
			case 409:
				ToastUtil.show(context, msg.obj.toString());
				doAllItemEnable();
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
		case R.id.certification_ensure:

			String result = isCanSubmit();
			if (result.equalsIgnoreCase("成功")) {
				new Thread(new Runnable() {

					@Override
					public void run() {
						doEnsure();
					}
				}).start();
			} else {
				ToastUtil.show(context, result);
			}
			break;

		default:
			break;
		}
	}

	private String isCanSubmit() {
		Filter realName = new RealNameFilter();
		Filter idcard = new IDcardFilter();
		Filter from = new From();
		Filter to = new To();
		Filter front = new Front();
		Filter black = new Black();

		realName.setNext(idcard);
		idcard.setNext(from);
		from.setNext(to);
		to.setNext(front);
		front.setNext(black);

		String result = realName.doFilter(certification_name.getText()
				.toString(), certification_idcard.getText().toString(),
				certification_idcard_validtime_from.getText().toString(),
				certification_idcard_validtime_to.getText().toString(),
				frontDtoFlag, backDtoFlag);

		return result;
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
	private void updateTimeDisplay(int id) {
		switch (id) {
		case R.id.certification_idcard_validtime_from:
			certification_idcard_validtime_from.setText(new StringBuilder()
					.append(mYear)
					.append("-")
					.append((mMonth + 1) < 10 ? "0" + (mMonth + 1)
							: (mMonth + 1)).append("-")
					.append((mDay < 10) ? "0" + mDay : mDay).append("   ")
					.append(mHour).append(":")
					.append((mMinute < 10) ? "0" + mMinute : mMinute));
			break;
		case R.id.certification_idcard_validtime_to:
			certification_idcard_validtime_to.setText(new StringBuilder()
					.append(mYear)
					.append("-")
					.append((mMonth + 1) < 10 ? "0" + (mMonth + 1)
							: (mMonth + 1)).append("-")
					.append((mDay < 10) ? "0" + mDay : mDay).append("   ")
					.append(mHour).append(":")
					.append((mMinute < 10) ? "0" + mMinute : mMinute));
			break;

		default:
			break;
		}
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

			// updateTimeDisplay();
		}
	};

	/**
	 * 更新日期显示
	 */
	private void updateDateDisplay(int id) {
		switch (id) {
		case R.id.certification_idcard_validtime_from:
			certification_idcard_validtime_from.setText(new StringBuilder()
					.append(mYear)
					.append("-")
					.append((mMonth + 1) < 10 ? "0" + (mMonth + 1)
							: (mMonth + 1)).append("-")
					.append((mDay < 10) ? "0" + mDay : mDay));
			break;
		case R.id.certification_idcard_validtime_to:
			certification_idcard_validtime_to.setText(new StringBuilder()
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
	 * 时间选择
	 */
	private void doSelectTimeData() {
		Message msg = new Message();
		msg.what = SHOW_TIMEPICK;
		dateandtimeHandler.sendMessage(msg);
	}

	/**
	 * 提交实名认证
	 */
	private void doEnsure() {
		if (CommonUtils.compare_date(certification_idcard_validtime_from
				.getText().toString(), certification_idcard_validtime_to
				.getText().toString())) {
			Message msg = myHandler.obtainMessage();
			msg.what = SHOW_TOAST;
			msg.obj = "身份证有效期限截止日期应该大于开始日期";
			myHandler.sendMessage(msg);
			return;
		}

		myHandler.sendEmptyMessage(SHOW_PROGRESS);
		mMemberAuthDto.setRealName(certification_name.getText().toString());
		mMemberAuthDto.setIdNumber(certification_idcard.getText().toString());
		mMemberAuthDto.setIdLimitFrom(CommonUtils
				.parserData(certification_idcard_validtime_from.getText()
						.toString()));
		mMemberAuthDto.setIdLimitTo(CommonUtils
				.parserData(certification_idcard_validtime_to.getText()
						.toString()));
		mMemberAuthDto.setIdPictureFront(frontDto);
		mMemberAuthDto.setIdPictrueBack(backDto);

		PdaRequest<MemberAuthDto> request = new PdaRequest<MemberAuthDto>();
		request.setData(mMemberAuthDto);
		CertificationHandler dataHandler = new CertificationHandler(context,
				request);
		dataHandler.setOnDataReceiveListener(this);
		dataHandler.startNetWork();
	}

	/**
	 * 获取实名认证信息
	 */
	private void getCertificationInfo() {
		myHandler.sendEmptyMessage(SHOW_PROGRESS);
		PdaRequest<String> request = new PdaRequest<String>();
		request.setData("");
		GetCertificationInfoHandler dataHandler = new GetCertificationInfoHandler(
				context, request);
		dataHandler.setOnDataReceiveListener(this);
		dataHandler.startNetWork();
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.maintitle_back_iv:
			CommonUtils.finishActivity(CertificationActivity.this);
			break;
		case R.id.certification_idcard_validtime_from:
			doSelectDayData(view.getId());
			break;
		case R.id.certification_idcard_validtime_to:
			doSelectDayData(view.getId());
			break;
		case R.id.certification_idcard_photo_front:
			showOptionDialog(IDCARDF_REQUEST_CODE_PHOTOALBUM,
					IDCARDF_REQUEST_CODE_PHOTO);
			break;
		case R.id.certification_idcard_photo_behind:
			showOptionDialog(IDCARDB_REQUEST_CODE_PHOTOALBUM,
					IDCARDB_REQUEST_CODE_PHOTO);
			break;

		default:
			break;
		}
	}

	private void showOptionDialog(final int photoCode, final int cameraCode) {
		final SelectPicPopupWindow dialog = new SelectPicPopupWindow(
				CertificationActivity.this);
		dialog.setFirstButtonContent(getResources().getString(
				R.string.take_photo_hint));
		dialog.setFirstButtonOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// CommonUtils.selectCameraPhone(cameraCode, resultPath,
				// AddNewDriverActivity.this);
				takePhoto(cameraCode);
				dialog.dismiss();
			}
		});
		dialog.setSecendButtonContent(getResources().getString(
				R.string.get_system_photo_hint));
		dialog.setSecendButtonOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				CommonUtils.selectSystemPhone(photoCode,
						CertificationActivity.this);
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
				CertificationActivity.this.findViewById(R.id.main),
				Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0); // 设置layout在PopupWindow中显示的位置
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

	@Override
	public void onDataReceive(DataHandler dataHandler, int resultCode,
			Object data, int type) {
		myHandler.sendEmptyMessage(CLOSE_PROGRESS);
		switch (resultCode) {
		case NetWork.GET_CERTIFICATION_INFO_OK:
			doGetCertificationInfoSuccess(data);
			break;
		case NetWork.CERTIFICATION_OK:
			doCertificationSuccess(data);
			break;
		case NetWork.GET_CERTIFICATION_INFO_ERROR:
		case NetWork.CERTIFICATION_ERROR:
			ToastUtil.show(context,
					getResources().getString(R.string.network_error_hint));
			break;

		default:
			break;
		}

	}

	/**
	 * 实名认证成功
	 * 
	 * @param data
	 */
	private void doCertificationSuccess(Object data) {
		String dataString = null;
		try {
			dataString = new String((byte[]) data, "UTF-8");
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		try {
			PdaResponse<String> mData = ResultCodeJsonParser
					.parserResultCodeJson(dataString);
			String result = mData.getMessage();
			String message = result.substring(result.indexOf("#") + 1,
					result.length());
			if (mData.isSuccess()) {
				Message msg = myHandler.obtainMessage();
				msg.what = SHOW_TOAST;
				msg.obj = "认证已经提交,请等待审核";
				myHandler.sendMessage(msg);
				doAllItemEnable();
				certification_hint.setText(message);
			} else {
				// 失败
				Message msg = myHandler.obtainMessage();
				msg.what = SHOW_TOAST;
				msg.obj = message;
				myHandler.sendMessage(msg);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取实名认证信息
	 * 
	 * @param data
	 */
	private void doGetCertificationInfoSuccess(Object data) {
		String dataString = null;
		try {
			dataString = new String((byte[]) data, "UTF-8");
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		try {
			PdaResponse<MemberAuthDto> mData = GetCertificationJsonParser
					.parserCertificationCarJson(dataString);
			if (mData.isSuccess()) {
				String result = mData.getMessage();
				String message = result.substring(result.indexOf("#") + 1,
						result.length());
				int messageCode = Integer.parseInt(result.substring(0,
						result.indexOf("#")));
				if (null != mData.getData()) {

					certification_hint.setText(message);
					mMemberAuthDto = mData.getData();
					showView(mData.getData());
				}
				Message msg = myHandler.obtainMessage();
				msg.what = messageCode;
				msg.obj = message;
				myHandler.sendMessage(msg);
			} else {
				// 失败
				String result = mData.getMessage();
				String message = result.substring(result.indexOf("#") + 1,
						result.length());
				Message msg = myHandler.obtainMessage();
				msg.what = SHOW_TOAST;
				msg.obj = message;
				myHandler.sendMessage(msg);
				doAllItemAble();
			}
		} catch (Exception e) {
			e.printStackTrace();
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
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode != RESULT_OK)
			return;
		switch (requestCode) {
		case IDCARDF_REQUEST_CODE_PHOTOALBUM:
			if (data != null) {
				startPhotoZoom(data.getData(), IDCARDF_REQUEST_CODE_PHOTOOK);
			}
			break;
		case IDCARDF_REQUEST_CODE_PHOTO:
			filePath = "file://" + ConstantPool.DEFAULT_ICON_PATH
					+ "image_diy_takephoto.jpg";
			if (filePath != null) {
				cropPhoto(filePath, IDCARDF_REQUEST_CODE_PICK);
			}
			break;
		case IDCARDF_REQUEST_CODE_PHOTOOK:
			resultBitmap = BitmapFactory
					.decodeFile(ConstantPool.DEFAULT_ICON_PATH
							+ "image_diy_resultphoto_temp.jpg");
			frontDto.setImageSuffix("PNG");
			frontDto.setFile(CommonUtils.getBitmapByByte(resultBitmap));
			frontDtoFlag = "Y";
			certification_idcard_photo_front.setImageBitmap(resultBitmap);
			break;
		case IDCARDF_REQUEST_CODE_PICK:
			resultBitmap = BitmapFactory
					.decodeFile(ConstantPool.DEFAULT_ICON_PATH
							+ "image_diy_resultphoto.jpg");
			frontDto.setImageSuffix("PNG");
			frontDtoFlag = "Y";
			frontDto.setFile(CommonUtils.getBitmapByByte(resultBitmap));
			certification_idcard_photo_front.setImageBitmap(resultBitmap);
			break;

		case IDCARDB_REQUEST_CODE_PHOTOALBUM:
			if (data != null) {
				startPhotoZoom(data.getData(), IDCARDB_REQUEST_CODE_PHOTOOK);
			}
			break;
		case IDCARDB_REQUEST_CODE_PHOTO:
			filePath = "file://" + ConstantPool.DEFAULT_ICON_PATH
					+ "image_diy_takephoto.jpg";
			if (filePath != null) {
				cropPhoto(filePath, IDCARDB_REQUEST_CODE_PICK);
			}
			break;
		case IDCARDB_REQUEST_CODE_PHOTOOK:
			resultBitmap = BitmapFactory
					.decodeFile(ConstantPool.DEFAULT_ICON_PATH
							+ "image_diy_resultphoto_temp.jpg");
			backDto.setImageSuffix("PNG");
			backDtoFlag = "Y";
			backDto.setFile(CommonUtils.getBitmapByByte(resultBitmap));
			certification_idcard_photo_behind.setImageBitmap(resultBitmap);
			break;
		case IDCARDB_REQUEST_CODE_PICK:
			resultBitmap = BitmapFactory
					.decodeFile(ConstantPool.DEFAULT_ICON_PATH
							+ "image_diy_resultphoto.jpg");
			backDto.setImageSuffix("PNG");
			backDtoFlag = "Y";
			backDto.setFile(CommonUtils.getBitmapByByte(resultBitmap));
			certification_idcard_photo_behind.setImageBitmap(resultBitmap);
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

		public String doFilter(String realName, String idcard,
				String idcardFrom, String idcardTo, String front, String back) {

			if (next == null) {
				return "成功";
			} else
				return next.doFilter(realName, idcard, idcardFrom, idcardTo,
						front, back);

		}

	}

	class RealNameFilter extends Filter {
		@Override
		public String doFilter(String realName, String idcard,
				String idcardFrom, String idcardTo, String front, String back) {

			if (TextUtils.isEmpty(realName)) {
				return "请输入真实的姓名";
			} else {
				return super.doFilter(realName, idcard, idcardFrom, idcardTo,
						front, back);
			}
		}
	}

	class IDcardFilter extends Filter {
		@Override
		public String doFilter(String realName, String idcard,
				String idcardFrom, String idcardTo, String front, String back) {

			if (!CommonUtils.is18IDcard(idcard)) {
				return "请输入正确的身份证号码";
			} else {
				return super.doFilter(realName, idcard, idcardFrom, idcardTo,
						front, back);
			}
		}
	}

	class From extends Filter {
		@Override
		public String doFilter(String realName, String idcard,
				String idcardFrom, String idcardTo, String front, String back) {

			if (TextUtils.isEmpty(idcardFrom)) {
				return "请输入正确的身份证有效期";
			} else {
				return super.doFilter(realName, idcard, idcardFrom, idcardTo,
						front, back);
			}
		}
	}

	class To extends Filter {
		@Override
		public String doFilter(String realName, String idcard,
				String idcardFrom, String idcardTo, String front, String back) {

			if (TextUtils.isEmpty(idcardTo)) {
				return "请输入正确的身份证有效期";
			} else {
				return super.doFilter(realName, idcard, idcardFrom, idcardTo,
						front, back);
			}
		}
	}

	class Front extends Filter {
		@Override
		public String doFilter(String realName, String idcard,
				String idcardFrom, String idcardTo, String front, String back) {

			if (TextUtils.isEmpty(front)) {
				return "请选择正确的身份证正面照";
			} else {
				return super.doFilter(realName, idcard, idcardFrom, idcardTo,
						front, back);
			}
		}
	}

	class Black extends Filter {
		@Override
		public String doFilter(String realName, String idcard,
				String idcardFrom, String idcardTo, String front, String back) {

			if (TextUtils.isEmpty(back)) {
				return "请选择正确的身份证反面照";
			} else {
				return super.doFilter(realName, idcard, idcardFrom, idcardTo,
						front, back);
			}
		}
	}
}
