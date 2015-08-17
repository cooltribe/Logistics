package com.seeyuan.logistics.activity;

import java.io.File;

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
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RatingBar;
import android.widget.TextView;

import com.mesada.nj.pubcontrols.controls.RemoteImageView;
import com.seeyuan.logistics.R;
import com.seeyuan.logistics.application.ConstantPool;
import com.seeyuan.logistics.application.NetWork;
import com.seeyuan.logistics.customview.MuInputEditText;
import com.seeyuan.logistics.customview.ProgressAlertDialog;
import com.seeyuan.logistics.customview.SelectPicPopupWindow;
import com.seeyuan.logistics.datacenter.DataHandler;
import com.seeyuan.logistics.datacenter.OnDataReceiveListener;
import com.seeyuan.logistics.datahandler.UpdateUserInfoHandler;
import com.seeyuan.logistics.entity.ImageDto;
import com.seeyuan.logistics.entity.MemberDto;
import com.seeyuan.logistics.entity.PdaRequest;
import com.seeyuan.logistics.entity.PdaResponse;
import com.seeyuan.logistics.entity.UserInfo;
import com.seeyuan.logistics.jsonparser.ResultCodeJsonParser;
import com.seeyuan.logistics.util.CommonUtils;
import com.seeyuan.logistics.util.ToastUtil;

/**
 * 个人详细资料
 * 
 * @author zhazhaobao
 * 
 */
public class PersonalOwnerInformationActivity extends BaseActivity implements
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
	 * 身份证照片
	 */
	private RemoteImageView IdCard_photo;

	/**
	 * 头像
	 */
	private RemoteImageView iv_photo;

	private final int HEADER_REQUEST_CODE_PHOTOALBUM = 500;
	private final int HEADER_REQUEST_CODE_PHOTO = 501;
	private final int HEADER_REQUEST_CODE_PHOTOOK = 502;
	private final int HEADER_REQUEST_CODE_PICK = 503;

	/**
	 * 身份证,系统相册
	 */
	private final int IDCARD_REQUEST_CODE_PHOTOALBUM = 100;
	private final int IDCARD_REQUEST_CODE_PHOTO = 101;
	private final int IDCARD_REQUEST_CODE_PHOTOOK = 102;
	private final int IDCARD_REQUEST_CODE_PICK = 103;

	/**
	 * 行驶证照片
	 */
	private RemoteImageView iv_driving_license_photo;

	private final int DRIVING_REQUEST_CODE_PHOTOALBUM = 200;
	private final int DRIVING_REQUEST_CODE_PHOTO = 201;
	private final int DRIVING_REQUEST_CODE_PHOTOOK = 202;
	private final int DRIVING_REQUEST_CODE_PICK = 203;

	/**
	 * 驾驶证照片
	 */
	private RemoteImageView iv_driver_license_photo;

	private final int DRIVER_REQUEST_CODE_PHOTOALBUM = 300;
	private final int DRIVER_REQUEST_CODE_PHOTO = 301;
	private final int DRIVER_REQUEST_CODE_PHOTOOK = 302;
	private final int DRIVER_REQUEST_CODE_PICK = 303;

	/**
	 * 车辆照片
	 */
	private RemoteImageView iv_vehicle_photo;

	private final int VEHICLE_REQUEST_CODE_PHOTOALBUM = 400;
	private final int VEHICLE_REQUEST_CODE_PHOTO = 401;
	private final int VEHICLE_REQUEST_CODE_PHOTOOK = 402;
	private final int VEHICLE_REQUEST_CODE_PICK = 403;

	private SelectPicPopupWindow dialog;

	private UserInfo mUserInfo;

	/**
	 * 用户类型
	 */
	private TextView tv_information;

	/**
	 * 星级
	 */
	private RatingBar ratingBar;

	/**
	 * 账号
	 */
	private TextView user_type;

	/**
	 * 手机号码
	 */
	private TextView user_telphone;

	/**
	 * 真实姓名
	 */
	// private MuInputEditText ed_true_name;

	// private MuInputEditText Id_card_number;

	// private MuInputEditText ed_id_card_address;

	private final int SHOW_TOAST = 1000;

	/**
	 * 性别，男
	 */
	private RadioButton radio_man;
	/**
	 * 性别，女
	 */
	private RadioButton radio_female;

	/**
	 * 邮箱地址
	 */
	private MuInputEditText email_number;

	/**
	 * QQ
	 */
	private MuInputEditText ed_qq_address;

	/**
	 * 联系地址
	 */
	private MuInputEditText ed_insure_addr;

	private MemberDto memberDto = new MemberDto();

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
		setContentView(R.layout.activity_personal_owner_information_v1);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,
				R.layout.deflaut_titlebar); // titlebar为自己标题栏的布局
		context = getApplicationContext();
		mUserInfo = (UserInfo) getIntent().getSerializableExtra("userInfo");
		initView();
	}

	@Override
	public void initView() {
		maintitle_back_iv = (ImageView) findViewById(R.id.maintitle_back_iv);
		maintitle_back_iv.setOnClickListener(this);

		defaulttitle_title_tv = (TextView) findViewById(R.id.defaulttitle_title_tv);
		defaulttitle_title_tv.setText(R.string.tab_personal_owner_information);

		iv_photo = (RemoteImageView) findViewById(R.id.iv_photo);
		iv_photo.draw(
				TextUtils.isEmpty(mUserInfo.getFACE()) ? "" : mUserInfo
						.getFACE(), ConstantPool.DEFAULT_ICON_PATH, false, true);
		iv_photo.setOnClickListener(this);

		// IdCard_photo = (RemoteImageView) findViewById(R.id.IdCard_photo);
		// IdCard_photo.draw(TextUtils.isEmpty(mUserInfo.getFACE()) ? ""
		// : mUserInfo.getFACE(), ConstantPool.DEFAULT_ICON_PATH, false);
		// IdCard_photo.setOnClickListener(this);

		// iv_driving_license_photo = (RemoteImageView)
		// findViewById(R.id.iv_driving_license_photo);
		// iv_driving_license_photo.setOnClickListener(this);
		//
		// iv_driver_license_photo = (RemoteImageView)
		// findViewById(R.id.iv_driver_license_photo);
		// iv_driver_license_photo.setOnClickListener(this);
		//
		// iv_vehicle_photo = (RemoteImageView)
		// findViewById(R.id.iv_vehicle_photo);
		// iv_vehicle_photo.setOnClickListener(this);

		tv_information = (TextView) findViewById(R.id.tv_information);
		tv_information.setText(TextUtils.isEmpty(CommonUtils
				.getUserType(mUserInfo.getMemberType())) ? "" : CommonUtils
				.getUserType(mUserInfo.getMemberType()));

		ratingBar = (RatingBar) findViewById(R.id.ratingBar);
		user_type = (TextView) findViewById(R.id.user_type);
		user_type.setText(TextUtils.isEmpty(mUserInfo.getUSER_NAME()) ? ""
				: mUserInfo.getUSER_NAME());

		user_telphone = (TextView) findViewById(R.id.user_telphone);
		user_telphone.setText(TextUtils.isEmpty(mUserInfo.getMOBILE()) ? ""
				: mUserInfo.getMOBILE());

		// ed_true_name = (MuInputEditText) findViewById(R.id.ed_true_name);

		// Id_card_number = (MuInputEditText) findViewById(R.id.Id_card_number);

		// ed_id_card_address = (MuInputEditText)
		// findViewById(R.id.ed_id_card_address);

		email_number = (MuInputEditText) findViewById(R.id.email_number);
		email_number.setText(TextUtils.isEmpty(mUserInfo.getEMAIL()) ? ""
				: mUserInfo.getEMAIL());

		ed_qq_address = (MuInputEditText) findViewById(R.id.ed_qq_address);
		ed_qq_address.setText(TextUtils.isEmpty(mUserInfo.getQQ()) ? ""
				: mUserInfo.getQQ());

		ed_insure_addr = (MuInputEditText) findViewById(R.id.ed_insure_addr);
		ed_insure_addr.setText(TextUtils.isEmpty(mUserInfo.getADDRESS()) ? ""
				: mUserInfo.getADDRESS());

		radio_man = (RadioButton) findViewById(R.id.radio_man);
		radio_female = (RadioButton) findViewById(R.id.radio_female);
		setSexRadio(TextUtils.isEmpty(mUserInfo.getSEX()) ? -1 : Integer
				.parseInt(mUserInfo.getSEX()));
	}

	/**
	 * 设置性别按钮
	 */
	private void setSexRadio(int sex) {
		if (sex == -1)
			return;
		switch (sex) {
		case 0: // 女
			radio_female.setChecked(true);
			break;
		case 1:// 男
			radio_man.setChecked(true);
			break;

		default:
			break;
		}
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
			// if (null != dialog && dialog.isDialogVisible()) {
			// dialog.dismiss();
			// } else {
			CommonUtils.finishActivity(PersonalOwnerInformationActivity.this);
			// }
			break;
		case R.id.IdCard_photo:
			doIDCardPhone();
			break;
		case R.id.iv_driving_license_photo:
			doDrivingLicesePhoto();
			break;
		case R.id.iv_driver_license_photo:
			doDriverLicensePhoto();
			break;
		case R.id.iv_vehicle_photo:
			doVehiclePhoto();
			break;
		case R.id.iv_photo:
			doHeaderPhoto();
			break;
		default:
			break;
		}
	}

	/**
	 * 头像
	 */
	private void doHeaderPhoto() {
		showOptionDialog(HEADER_REQUEST_CODE_PHOTOALBUM,
				HEADER_REQUEST_CODE_PHOTO);

	}

	private void doVehiclePhoto() {

		showOptionDialog(VEHICLE_REQUEST_CODE_PHOTOALBUM,
				VEHICLE_REQUEST_CODE_PHOTO);

	}

	private void doDriverLicensePhoto() {

		showOptionDialog(DRIVER_REQUEST_CODE_PHOTOALBUM,
				DRIVER_REQUEST_CODE_PHOTO);

	}

	private void doDrivingLicesePhoto() {

		showOptionDialog(DRIVING_REQUEST_CODE_PHOTOALBUM,
				DRIVING_REQUEST_CODE_PHOTO);

	}

	/**
	 * 头像
	 */
	private void doIDCardPhone() {
		showOptionDialog(IDCARD_REQUEST_CODE_PHOTOALBUM,
				IDCARD_REQUEST_CODE_PHOTO);
	}

	@Override
	public void onClickListener(View view) {
		switch (view.getId()) {
		case R.id.bt_save:
			new Thread(new Runnable() {

				@Override
				public void run() {
					doSaveInformation();
				}
			}).start();
			break;

		default:
			break;
		}
	}

	/**
	 * 保存个人信息
	 */
	private void doSaveInformation() {
		if (!TextUtils.isEmpty(email_number.getText().toString())
				&& !CommonUtils.isEmail(email_number.getText().toString())) {
			Message msg = myHandler.obtainMessage();
			msg.what = SHOW_TOAST;
			msg.obj = "请输入正确的邮箱地址";
			myHandler.sendMessage(msg);
			return;
		}
		myHandler.sendEmptyMessage(SHOW_PROGRESS);
		ImageDto faceImage = new ImageDto();
		faceImage.setImageSuffix("PNG");
		// faceImage.setFile(CommonUtils.getBitmapByByte(resultBitmap));
		faceImage.setFile(CommonUtils.getBitmapByByte(uploadBitmap));
		memberDto.setFace(faceImage);
		memberDto.setMobile(user_telphone.getText().toString());
		// 真实姓名
		// memberDto.set
		memberDto.setSex(radio_man.isChecked() ? "1" : "0");
		memberDto.setEmail(email_number.getText().toString());
		memberDto.setQq(ed_qq_address.getText().toString());
		memberDto.setAddress(ed_insure_addr.getText().toString());

		// 同步数据
		mUserInfo.setSEX(radio_man.isChecked() ? "1" : "0");
		mUserInfo.setEMAIL(email_number.getText().toString());
		mUserInfo.setQQ(ed_qq_address.getText().toString());
		mUserInfo.setADDRESS(ed_insure_addr.getText().toString());
		mUserInfo.setFACE_LOCATION_URL(ConstantPool.DEFAULT_ICON_PATH
				+ "image_diy_resultphoto_temp.jpg");

		PdaRequest<MemberDto> request = new PdaRequest<MemberDto>();
		request.setData(memberDto);
		UpdateUserInfoHandler dataHandler = new UpdateUserInfoHandler(context,
				request);
		dataHandler.setOnDataReceiveListener(this);
		dataHandler.startNetWork();
	}

	private void showOptionDialog(final int photoCode, final int cameraCode) {
		final SelectPicPopupWindow dialog = new SelectPicPopupWindow(
				PersonalOwnerInformationActivity.this);
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
						PersonalOwnerInformationActivity.this);
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
				PersonalOwnerInformationActivity.this.findViewById(R.id.main),
				Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0); // 设置layout在PopupWindow中显示的位置
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode != RESULT_OK)
			return;
		switch (requestCode) {
		case IDCARD_REQUEST_CODE_PHOTOALBUM:
			if (data != null) {
				startPhotoZoom(data.getData(), IDCARD_REQUEST_CODE_PHOTOOK);
			}
			break;
		case IDCARD_REQUEST_CODE_PHOTO:
			filePath = "file://" + ConstantPool.DEFAULT_ICON_PATH
					+ "image_diy_takephoto.jpg";
			if (filePath != null) {
				cropPhoto(filePath, IDCARD_REQUEST_CODE_PICK);
			}
			break;
		case IDCARD_REQUEST_CODE_PHOTOOK:
			resultBitmap = BitmapFactory
					.decodeFile(ConstantPool.DEFAULT_ICON_PATH
							+ "image_diy_resultphoto_temp.jpg");
			IdCard_photo.setImageBitmap(resultBitmap);
			break;
		case IDCARD_REQUEST_CODE_PICK:
			resultBitmap = BitmapFactory
					.decodeFile(ConstantPool.DEFAULT_ICON_PATH
							+ "image_diy_resultphoto.jpg");
			IdCard_photo.setImageBitmap(resultBitmap);
			break;
		case DRIVING_REQUEST_CODE_PHOTOALBUM:
			if (data != null) {
				startPhotoZoom(data.getData(), DRIVING_REQUEST_CODE_PHOTOOK);
			}
			break;
		case DRIVING_REQUEST_CODE_PHOTO:
			filePath = "file://" + ConstantPool.DEFAULT_ICON_PATH
					+ "image_diy_takephoto.jpg";
			if (filePath != null) {
				cropPhoto(filePath, DRIVING_REQUEST_CODE_PICK);
			}
			break;
		case DRIVING_REQUEST_CODE_PHOTOOK:
			resultBitmap = BitmapFactory
					.decodeFile(ConstantPool.DEFAULT_ICON_PATH
							+ "image_diy_resultphoto_temp.jpg");
			iv_driving_license_photo.setImageBitmap(resultBitmap);
			break;
		case DRIVING_REQUEST_CODE_PICK:
			resultBitmap = BitmapFactory
					.decodeFile(ConstantPool.DEFAULT_ICON_PATH
							+ "image_diy_resultphoto.jpg");
			iv_driving_license_photo.setImageBitmap(resultBitmap);
			break;
		case DRIVER_REQUEST_CODE_PHOTOALBUM:
			if (data != null) {
				startPhotoZoom(data.getData(), DRIVER_REQUEST_CODE_PHOTOOK);
			}
			break;
		case DRIVER_REQUEST_CODE_PHOTO:
			filePath = "file://" + ConstantPool.DEFAULT_ICON_PATH
					+ "image_diy_takephoto.jpg";
			if (filePath != null) {
				cropPhoto(filePath, DRIVING_REQUEST_CODE_PICK);
			}
			break;
		case DRIVER_REQUEST_CODE_PHOTOOK:
			resultBitmap = BitmapFactory
					.decodeFile(ConstantPool.DEFAULT_ICON_PATH
							+ "image_diy_resultphoto_temp.jpg");
			iv_driver_license_photo.setImageBitmap(resultBitmap);
			break;
		case DRIVER_REQUEST_CODE_PICK:
			resultBitmap = BitmapFactory
					.decodeFile(ConstantPool.DEFAULT_ICON_PATH
							+ "image_diy_resultphoto.jpg");
			iv_driver_license_photo.setImageBitmap(resultBitmap);
			break;
		case VEHICLE_REQUEST_CODE_PHOTOALBUM:
			if (data != null) {
				startPhotoZoom(data.getData(), VEHICLE_REQUEST_CODE_PHOTOOK);
			}
			break;
		case VEHICLE_REQUEST_CODE_PHOTO:
			filePath = "file://" + ConstantPool.DEFAULT_ICON_PATH
					+ "image_diy_takephoto.jpg";
			if (filePath != null) {
				cropPhoto(filePath, VEHICLE_REQUEST_CODE_PICK);
			}
			break;
		case VEHICLE_REQUEST_CODE_PHOTOOK:
			resultBitmap = BitmapFactory
					.decodeFile(ConstantPool.DEFAULT_ICON_PATH
							+ "image_diy_resultphoto_temp.jpg");
			iv_vehicle_photo.setImageBitmap(resultBitmap);
			break;
		case VEHICLE_REQUEST_CODE_PICK:
			resultBitmap = BitmapFactory
					.decodeFile(ConstantPool.DEFAULT_ICON_PATH
							+ "image_diy_resultphoto.jpg");
			iv_vehicle_photo.setImageBitmap(resultBitmap);
			break;

		case HEADER_REQUEST_CODE_PHOTOALBUM:
			if (data != null) {
				startPhotoZoom(data.getData(), HEADER_REQUEST_CODE_PHOTOOK);
			}
			break;
		case HEADER_REQUEST_CODE_PHOTO:
			filePath = "file://" + ConstantPool.DEFAULT_ICON_PATH
					+ "image_diy_takephoto.jpg";
			if (filePath != null) {
				cropPhoto(filePath, HEADER_REQUEST_CODE_PICK);
			}
			break;
		case HEADER_REQUEST_CODE_PHOTOOK:
			resultBitmap = BitmapFactory
					.decodeFile(ConstantPool.DEFAULT_ICON_PATH
							+ "image_diy_resultphoto_temp.jpg");
			uploadBitmap = resultBitmap;
			resultBitmap = CommonUtils.toRoundBitmap(resultBitmap);
			iv_photo.setImageBitmap(resultBitmap);
			break;
		case HEADER_REQUEST_CODE_PICK:
			resultBitmap = BitmapFactory
					.decodeFile(ConstantPool.DEFAULT_ICON_PATH
							+ "image_diy_resultphoto.jpg");
			uploadBitmap = resultBitmap;
			resultBitmap = CommonUtils.toRoundBitmap(resultBitmap);
			iv_photo.setImageBitmap(resultBitmap);
			break;

		default:
			break;
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) { // 按下的如果是BACK，同时没有重复
			// if (null != dialog && dialog.isDialogVisible()) {
			// dialog.dismiss();
			// } else {
			CommonUtils.finishActivity(PersonalOwnerInformationActivity.this);
			// }
			return true;
		}

		return super.onKeyDown(keyCode, event);
	}

	private String filePath;
	private Bitmap resultBitmap;
	/**
	 * 上传头像。正方形头像
	 */
	private Bitmap uploadBitmap;

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
		switch (resultCode) {
		case NetWork.UPDATE_USERINFO_OK:
			doUpdateSuccess(data);
			break;
		case NetWork.UPDATE_USERINFO_ERROR:
			ToastUtil.show(context,
					getResources().getString(R.string.network_error_hint));
			break;

		default:
			break;
		}
	}

	/**
	 * 保存用户信息成功
	 * 
	 * @param data
	 */
	private void doUpdateSuccess(Object data) {
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
				Intent intent = new Intent();
				intent.putExtra("userInfo", mUserInfo);
				setResult(RESULT_OK, intent);
				CommonUtils.finishActivity(this);
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
			ToastUtil.show(context,
					getResources().getString(R.string.network_error_hint));
		}
	}
}
