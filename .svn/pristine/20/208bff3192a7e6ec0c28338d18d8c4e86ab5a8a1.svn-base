package com.seeyuan.logistics.activity;

import java.io.File;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
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
import com.seeyuan.logistics.datahandler.AddDriverHandler;
import com.seeyuan.logistics.datahandler.EditDriverHandler;
import com.seeyuan.logistics.entity.DriverDto;
import com.seeyuan.logistics.entity.ImageDto;
import com.seeyuan.logistics.entity.PdaRequest;
import com.seeyuan.logistics.entity.PdaResponse;
import com.seeyuan.logistics.jsonparser.ResultCodeJsonParser;
import com.seeyuan.logistics.util.CommonUtils;
import com.seeyuan.logistics.util.ToastUtil;

/**
 * 添加新司机
 * 
 * @author zhazhaobao
 * 
 */
public class AddNewDriverActivity extends BaseActivity implements
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
	 * 姓名
	 */
	private MuInputEditText add_new_driver_name;

	/**
	 * 电话号码
	 */
	private MuInputEditText add_new_driver_phone;
	/**
	 * 身份证号码
	 */
	private MuInputEditText add_new_driver_idcard_name;

	/**
	 * 身份证照片
	 */
	private RemoteImageView add_new_driver_idcard_photo;
	private String idcar_photo_tag = "N";
	/**
	 * 驾驶证照片
	 */
	private RemoteImageView add_new_driver_driver_license;
	private String driver_licence_tag = "N";
	/**
	 * 头像
	 */
	private RemoteImageView add_new_driver_header;

	private DriverDto driverDto;
	private ImageDto idcardImageDto, driverLenenceImageDto, headerImageDto,
			emplLicenseImageDto;

	private boolean isEditMode = false;

	/**
	 * 上传头像,系统相册
	 */
	private final int HEADER_REQUEST_CODE_PHOTOALBUM = 100;
	private final int HEADER_REQUEST_CODE_PHOTO = 101;
	private final int HEADER_REQUEST_CODE_PHOTOOK = 102;
	private final int HEADER_REQUEST_CODE_PICK = 103;

	/**
	 * 驾驶证照片,系统相册
	 */
	private final int DRIVER_LICENSE_REQUEST_CODE_PHOTOALBUM = 200;
	private final int DRIVER_LICENSE__REQUEST_CODE_PHOTO = 201;
	private final int DRIVER_LICENSE__REQUEST_CODE_PHOTOOK = 202;
	private final int DRIVER_LICENSE__REQUEST_CODE_PICK = 203;

	/**
	 * 身份证照片,系统相册
	 */
	private final int IDCARD_REQUEST_CODE_PHOTOALBUM = 300;
	private final int IDCARD_REQUEST_CODE_PHOTO = 301;
	private final int IDCARD_REQUEST_CODE_PHOTOOK = 302;
	private final int IDCARD_REQUEST_CODE_PICK = 303;

	/**
	 * 从业资格证,系统相册
	 */
	private final int EMPLLICENSE_REQUEST_CODE_PHOTOALBUM = 400;
	private final int EMPLLICENSE_REQUEST_CODE_PHOTO = 401;
	private final int EMPLLICENSE_REQUEST_CODE_PHOTOOK = 402;
	private final int EMPLLICENSE_REQUEST_CODE_PICK = 403;

	private SharedPreferences sPreferences;

	private Bitmap resultBitmap;

	/**
	 * 显示进度条
	 */
	private final int SHOW_PROGRESS = 1000;
	/**
	 * 关闭进度条
	 */
	private final int CLOSE_PROGRESS = 1001;
	private final int SHOW_TOAST = 1002;

	private ProgressAlertDialog progressDialog;

	/**
	 * 从业资格证
	 */
	private MuInputEditText add_new_driver_emplLicense;

	/**
	 * 从业资格证照片
	 */
	private RemoteImageView add_new_driver_emplLicenseImage;
	private String emplLicenseImage_tag = "N";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.activity_add_new_driver); // 软件activity的布局
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,
				R.layout.deflaut_titlebar); // titlebar为自己标题栏的布局
		context = getApplicationContext();
		sPreferences = getSharedPreferences(ConstantPool.LOGISTICS_PREFERENCES,
				Context.MODE_PRIVATE);
		// driverDto = new DriverDto();
		driverDto = (DriverDto) getIntent().getSerializableExtra("driverInfo");
		if (null == driverDto) {
			driverDto = new DriverDto();
			isEditMode = false;
		} else {
			isEditMode = true;
		}
		initView();
	}

	@Override
	public void initView() {
		maintitle_back_iv = (ImageView) findViewById(R.id.maintitle_back_iv);
		maintitle_back_iv.setOnClickListener(this);

		defaulttitle_title_tv = (TextView) findViewById(R.id.defaulttitle_title_tv);
		if (isEditMode) {
			defaulttitle_title_tv.setText(R.string.edit_driver_hint);
		} else {
			defaulttitle_title_tv.setText(R.string.add_new_driver_manager_hint);
		}

		add_new_driver_name = (MuInputEditText) findViewById(R.id.add_new_driver_name);
		add_new_driver_name
				.setText(TextUtils.isEmpty(driverDto.getDriverName()) ? ""
						: driverDto.getDriverName());
		add_new_driver_name.addTextChangedListener(textWatcherListener);

		add_new_driver_phone = (MuInputEditText) findViewById(R.id.add_new_driver_phone);
		add_new_driver_phone
				.setText(TextUtils.isEmpty(driverDto.getDriverTel()) ? ""
						: driverDto.getDriverTel());
		add_new_driver_phone.addTextChangedListener(textWatcherListener);

		add_new_driver_idcard_name = (MuInputEditText) findViewById(R.id.add_new_driver_idcard_name);
		add_new_driver_idcard_name.setText(TextUtils.isEmpty(driverDto
				.getIdNumber()) ? "" : driverDto.getIdNumber());
		add_new_driver_idcard_name.addTextChangedListener(textWatcherListener);

		add_new_driver_idcard_photo = (RemoteImageView) findViewById(R.id.add_new_driver_idcard_photo);

		add_new_driver_idcard_photo.setOnClickListener(this);

		add_new_driver_driver_license = (RemoteImageView) findViewById(R.id.add_new_driver_driver_license);
		add_new_driver_driver_license.setOnClickListener(this);
		add_new_driver_header = (RemoteImageView) findViewById(R.id.add_new_driver_header);
		add_new_driver_header.setOnClickListener(this);

		add_new_driver_idcard_photo.draw(
				null == driverDto.getIdPictureFront() ? "" : driverDto
						.getIdPictureFront().getHeaderImgURL(),
				ConstantPool.DEFAULT_ICON_PATH, false, false);
		idcar_photo_tag = null == driverDto.getIdPictureFront() ? "N" : "Y";
		add_new_driver_driver_license.draw(
				null == driverDto.getDriverLicense() ? "" : driverDto
						.getDriverLicense().getHeaderImgURL(),
				ConstantPool.DEFAULT_ICON_PATH, false, false);
		driver_licence_tag = null == driverDto.getDriverLicense() ? "N" : "Y";
		add_new_driver_header.draw(null == driverDto.getFace() ? "" : driverDto
				.getFace().getHeaderImgURL(), ConstantPool.DEFAULT_ICON_PATH,
				false, true);

		add_new_driver_emplLicense = (MuInputEditText) findViewById(R.id.add_new_driver_emplLicense);
		add_new_driver_emplLicense.setText(TextUtils.isEmpty(driverDto
				.getEmplLicense()) ? "" : driverDto.getEmplLicense());
		add_new_driver_emplLicenseImage = (RemoteImageView) findViewById(R.id.add_new_driver_emplLicenseImage);
		add_new_driver_emplLicenseImage.draw(null == driverDto
				.getEmpLLicenseImage() ? "" : driverDto.getEmpLLicenseImage()
				.getHeaderImgURL(), ConstantPool.DEFAULT_ICON_PATH, false,
				false);
		emplLicenseImage_tag = null == driverDto.getEmpLLicenseImage() ? "N"
				: "Y";
		add_new_driver_emplLicenseImage.setOnClickListener(this);

		// 图片imageDto
		idcardImageDto = null == driverDto.getIdPictureFront() ? new ImageDto()
				: driverDto.getIdPictureFront();
		driverLenenceImageDto = null == driverDto.getDriverLicense() ? new ImageDto()
				: driverDto.getDriverLicense();
		headerImageDto = null == driverDto.getFace() ? new ImageDto()
				: driverDto.getFace();
		emplLicenseImageDto = null == driverDto.getEmpLLicenseImage() ? new ImageDto()
				: driverDto.getEmpLLicenseImage();

	}

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
		case R.id.add_new_driver_comfirm:
			new Thread(new Runnable() {
				@Override
				public void run() {
					doAddNewDriverComfirm();
				}
			}).start();
			break;
		case R.id.add_new_driver_cancel:
			doAddNewDriverCancel();
			break;
		default:
			break;
		}

	}

	/**
	 * 取消。
	 */
	private void doAddNewDriverCancel() {
		CommonUtils.finishActivity(AddNewDriverActivity.this);
	}

	/**
	 * 添加新司机
	 */
	private void doAddNewDriverComfirm() {

		String result = isCanSubmitDriverSource();
		if (result.equalsIgnoreCase("成功")) {

			if (isEditMode) {
				editNewDriver();
			} else {
				addNewDriver();
			}
		} else {
			Message msg = myHandler.obtainMessage();
			msg.what = SHOW_TOAST;
			msg.obj = result;
			myHandler.sendMessage(msg);
		}

	}

	/**
	 * 添加新司机
	 */
	private void addNewDriver() {
		submitDirverInfo();
	}

	/**
	 * 修改新司机
	 */
	private void editNewDriver() {
		myHandler.sendEmptyMessage(SHOW_PROGRESS);
		driverDto.setDriverName(add_new_driver_name.getText().toString());
		driverDto.setDriverTel(add_new_driver_phone.getText().toString());
		driverDto.setIdNumber(add_new_driver_idcard_name.getText().toString());
		driverDto.setIdPictureFront(idcardImageDto);
		driverDto.setDriverLicense(driverLenenceImageDto);
		driverDto.setFace(headerImageDto);
		driverDto.setDriverType("U");

		driverDto.setEmplLicense(add_new_driver_emplLicense.getText()
				.toString());
		driverDto.setEmpLLicenseImage(emplLicenseImageDto);
		PdaRequest<DriverDto> request = new PdaRequest<DriverDto>();
		request.setData(driverDto);
		EditDriverHandler dataHandler = new EditDriverHandler(context, request);
		dataHandler.setOnDataReceiveListener(this);
		dataHandler.startNetWork();

	}

	private void submitDirverInfo() {
		myHandler.sendEmptyMessage(SHOW_PROGRESS);
		driverDto.setDriverName(add_new_driver_name.getText().toString());
		driverDto.setDriverTel(add_new_driver_phone.getText().toString());
		driverDto.setIdNumber(add_new_driver_idcard_name.getText().toString());
		driverDto.setIdPictureFront(idcardImageDto);
		driverDto.setDriverLicense(driverLenenceImageDto);
		driverDto.setFace(headerImageDto);
		driverDto.setDriverType("U");
		driverDto.setEmplLicense(add_new_driver_emplLicense.getText()
				.toString());
		driverDto.setEmpLLicenseImage(emplLicenseImageDto);
		PdaRequest<DriverDto> request = new PdaRequest<DriverDto>();
		request.setData(driverDto);
		AddDriverHandler dataHandler = new AddDriverHandler(context, request);
		dataHandler.setOnDataReceiveListener(this);
		dataHandler.startNetWork();
	}

	private String isCanSubmitDriverSource() {

		Filter name = new NameFilder();
		Filter phone = new PhoneFilder();
		Filter idcard = new IDcardFilder();
		Filter idcardPhone = new IDcardPhotoFilder();
		Filter driverlicence = new driverLicencePhotoFilter();
		Filter emplLicense = new emplLicenseFilter();
		Filter emplLicensePhoto = new emplLicensePhotoFilter();

		name.setNext(phone);
		phone.setNext(idcard);
		idcard.setNext(idcardPhone);
		idcardPhone.setNext(driverlicence);
		driverlicence.setNext(emplLicense);
		emplLicense.setNext(emplLicensePhoto);

		String result = name.doFilter(add_new_driver_name.getText().toString(),
				add_new_driver_phone.getText().toString(),
				add_new_driver_idcard_name.getText().toString(),
				idcar_photo_tag.toString(), driver_licence_tag.toString(),
				add_new_driver_emplLicense.getText().toString(),
				emplLicenseImage_tag.toString());
		return result;
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.maintitle_back_iv:
			CommonUtils.finishActivity(AddNewDriverActivity.this);
			break;
		case R.id.add_new_driver_idcard_photo:
			doIDcardPhoto();
			break;
		case R.id.add_new_driver_driver_license:
			doDriverLincese();
			break;
		case R.id.add_new_driver_header:
			doDriverHeader();
			break;
		case R.id.add_new_driver_emplLicenseImage:
			doEmplLicenseImage();
			break;

		default:
			break;
		}

	}

	/**
	 * 从业资格证
	 */
	private void doEmplLicenseImage() {
		showOptionDialog(EMPLLICENSE_REQUEST_CODE_PHOTOALBUM,
				EMPLLICENSE_REQUEST_CODE_PHOTO);
	}

	/**
	 * 头像
	 */
	private void doDriverHeader() {
		showOptionDialog(HEADER_REQUEST_CODE_PHOTOALBUM,
				HEADER_REQUEST_CODE_PHOTO);
	}

	/**
	 * 驾驶证照片
	 */
	private void doDriverLincese() {
		showOptionDialog(DRIVER_LICENSE_REQUEST_CODE_PHOTOALBUM,
				DRIVER_LICENSE__REQUEST_CODE_PHOTO);
	}

	/**
	 * 身份证照片
	 */
	private void doIDcardPhoto() {
		showOptionDialog(IDCARD_REQUEST_CODE_PHOTOALBUM,
				IDCARD_REQUEST_CODE_PHOTO);
	}

	private void showOptionDialog(final int photoCode, final int cameraCode) {
		final SelectPicPopupWindow dialog = new SelectPicPopupWindow(
				AddNewDriverActivity.this);
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
						AddNewDriverActivity.this);
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
				AddNewDriverActivity.this.findViewById(R.id.main),
				Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0); // 设置layout在PopupWindow中显示的位置
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

		}
	};

	private String filePath;

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if(resultCode != RESULT_OK)
			return;
		switch (requestCode) {
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
			resultBitmap = CommonUtils.toRoundBitmap(resultBitmap);
			add_new_driver_header.setImageBitmap(resultBitmap);

			headerImageDto.setImageSuffix("PNG");
			headerImageDto.setFile(CommonUtils.getBitmapByByte(resultBitmap));
			break;
		case HEADER_REQUEST_CODE_PICK:
			resultBitmap = BitmapFactory
					.decodeFile(ConstantPool.DEFAULT_ICON_PATH
							+ "image_diy_resultphoto.jpg");
			resultBitmap = CommonUtils.toRoundBitmap(resultBitmap);
			add_new_driver_header.setImageBitmap(resultBitmap);

			headerImageDto.setImageSuffix("PNG");
			headerImageDto.setFile(CommonUtils.getBitmapByByte(resultBitmap));
			break;

		case DRIVER_LICENSE_REQUEST_CODE_PHOTOALBUM:
			if (data != null) {
				startPhotoZoom(data.getData(),
						DRIVER_LICENSE__REQUEST_CODE_PHOTOOK);
			}
			break;
		case DRIVER_LICENSE__REQUEST_CODE_PHOTO:
			filePath = "file://" + ConstantPool.DEFAULT_ICON_PATH
					+ "image_diy_takephoto.jpg";
			if (filePath != null) {
				cropPhoto(filePath, DRIVER_LICENSE__REQUEST_CODE_PICK);
			}
			break;
		case DRIVER_LICENSE__REQUEST_CODE_PHOTOOK:
			resultBitmap = BitmapFactory
					.decodeFile(ConstantPool.DEFAULT_ICON_PATH
							+ "image_diy_resultphoto_temp.jpg");
			add_new_driver_driver_license.setImageBitmap(resultBitmap);
			driver_licence_tag = "OK";
			driverLenenceImageDto.setImageSuffix("PNG");
			driverLenenceImageDto.setFile(CommonUtils
					.getBitmapByByte(resultBitmap));
			break;
		case DRIVER_LICENSE__REQUEST_CODE_PICK:
			resultBitmap = BitmapFactory
					.decodeFile(ConstantPool.DEFAULT_ICON_PATH
							+ "image_diy_resultphoto.jpg");
			add_new_driver_driver_license.setImageBitmap(resultBitmap);
			driver_licence_tag = "OK";
			driverLenenceImageDto.setImageSuffix("PNG");
			driverLenenceImageDto.setFile(CommonUtils
					.getBitmapByByte(resultBitmap));
			break;

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
			add_new_driver_idcard_photo.setImageBitmap(resultBitmap);
			idcar_photo_tag = "Y";
			idcardImageDto.setImageSuffix("PNG");
			idcardImageDto.setFile(CommonUtils.getBitmapByByte(resultBitmap));
			break;
		case IDCARD_REQUEST_CODE_PICK:
			resultBitmap = BitmapFactory
					.decodeFile(ConstantPool.DEFAULT_ICON_PATH
							+ "image_diy_resultphoto.jpg");
			add_new_driver_idcard_photo.setImageBitmap(resultBitmap);
			idcar_photo_tag = "Y";
			idcardImageDto.setImageSuffix("PNG");
			idcardImageDto.setFile(CommonUtils.getBitmapByByte(resultBitmap));
			break;

		case EMPLLICENSE_REQUEST_CODE_PHOTOALBUM:
			if (data != null) {
				startPhotoZoom(data.getData(), EMPLLICENSE_REQUEST_CODE_PHOTOOK);
			}
			break;
		case EMPLLICENSE_REQUEST_CODE_PHOTO:
			filePath = "file://" + ConstantPool.DEFAULT_ICON_PATH
					+ "image_diy_takephoto.jpg";
			if (filePath != null) {
				cropPhoto(filePath, EMPLLICENSE_REQUEST_CODE_PICK);
			}
			break;
		case EMPLLICENSE_REQUEST_CODE_PHOTOOK:
			resultBitmap = BitmapFactory
					.decodeFile(ConstantPool.DEFAULT_ICON_PATH
							+ "image_diy_resultphoto_temp.jpg");
			add_new_driver_emplLicenseImage.setImageBitmap(resultBitmap);
			emplLicenseImage_tag = "Y";
			emplLicenseImageDto.setImageSuffix("PNG");
			emplLicenseImageDto.setFile(CommonUtils
					.getBitmapByByte(resultBitmap));
			break;
		case EMPLLICENSE_REQUEST_CODE_PICK:
			resultBitmap = BitmapFactory
					.decodeFile(ConstantPool.DEFAULT_ICON_PATH
							+ "image_diy_resultphoto.jpg");
			add_new_driver_emplLicenseImage.setImageBitmap(resultBitmap);
			emplLicenseImage_tag = "Y";
			emplLicenseImageDto.setImageSuffix("PNG");
			emplLicenseImageDto.setFile(CommonUtils
					.getBitmapByByte(resultBitmap));
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

		public String doFilter(String driverName, String driverPhone,
				String driverIDcard, String driverIDPhoto,
				String driverLicencePhoto, String emplLicense,
				String emplLicensePhoto) {

			if (next == null) {
				return "成功";
			} else
				return next.doFilter(driverName, driverPhone, driverIDcard,
						driverIDPhoto, driverLicencePhoto, emplLicense,
						emplLicensePhoto);

		}

	}

	class NameFilder extends Filter {

		public String doFilter(String driverName, String driverPhone,
				String driverIDcard, String driverIDPhoto,
				String driverLicencePhoto, String emplLicense,
				String emplLicensePhoto) {

			if (TextUtils.isEmpty(driverName)) {
				return "请输入正确的司机姓名";
			} else
				return super.doFilter(driverName, driverPhone, driverIDcard,
						driverIDPhoto, driverLicencePhoto, emplLicense,
						emplLicensePhoto);
		}
	}

	class PhoneFilder extends Filter {

		public String doFilter(String driverName, String driverPhone,
				String driverIDcard, String driverIDPhoto,
				String driverLicencePhoto, String emplLicense,
				String emplLicensePhoto) {

			if (TextUtils.isEmpty(driverPhone)
					|| !CommonUtils.isMobileNO(driverPhone)) {
				return "请输入正确的手机号码";
			} else
				return super.doFilter(driverName, driverPhone, driverIDcard,
						driverIDPhoto, driverLicencePhoto, emplLicense,
						emplLicensePhoto);
		}
	}

	class IDcardFilder extends Filter {

		public String doFilter(String driverName, String driverPhone,
				String driverIDcard, String driverIDPhoto,
				String driverLicencePhoto, String emplLicense,
				String emplLicensePhoto) {

			if (TextUtils.isEmpty(driverIDcard) || driverIDcard.length() < 18
					|| !CommonUtils.is18IDcard(driverIDcard)) {
				return "请输入正确的身份证号码";
			} else
				return super.doFilter(driverName, driverPhone, driverIDcard,
						driverIDPhoto, driverLicencePhoto, emplLicense,
						emplLicensePhoto);
		}
	}

	class IDcardPhotoFilder extends Filter {

		public String doFilter(String driverName, String driverPhone,
				String driverIDcard, String driverIDPhoto,
				String driverLicencePhoto, String emplLicense,
				String emplLicensePhoto) {

			if (driverIDPhoto.equalsIgnoreCase("N")) {
				return "请选择身份证照片";
			} else
				return super.doFilter(driverName, driverPhone, driverIDcard,
						driverIDPhoto, driverLicencePhoto, emplLicense,
						emplLicensePhoto);
		}
	}

	class driverLicencePhotoFilter extends Filter {

		public String doFilter(String driverName, String driverPhone,
				String driverIDcard, String driverIDPhoto,
				String driverLicencePhoto, String emplLicense,
				String emplLicensePhoto) {

			if (driverLicencePhoto.equalsIgnoreCase("N")) {
				return "请选择驾驶证照片";
			} else
				return super.doFilter(driverName, driverPhone, driverIDcard,
						driverIDPhoto, driverLicencePhoto, emplLicense,
						emplLicensePhoto);
		}
	}

	class emplLicenseFilter extends Filter {

		public String doFilter(String driverName, String driverPhone,
				String driverIDcard, String driverIDPhoto,
				String driverLicencePhoto, String emplLicense,
				String emplLicensePhoto) {

			if (TextUtils.isEmpty(emplLicense)) {
				return "请输入正确的从业资格证";
			} else
				return super.doFilter(driverName, driverPhone, driverIDcard,
						driverIDPhoto, driverLicencePhoto, emplLicense,
						emplLicensePhoto);
		}
	}

	class emplLicensePhotoFilter extends Filter {

		public String doFilter(String driverName, String driverPhone,
				String driverIDcard, String driverIDPhoto,
				String driverLicencePhoto, String emplLicense,
				String emplLicensePhoto) {

			if (emplLicensePhoto.equalsIgnoreCase("N")) {
				return "请选择从业资格证照片";
			} else
				return super.doFilter(driverName, driverPhone, driverIDcard,
						driverIDPhoto, driverLicencePhoto, emplLicense,
						emplLicensePhoto);
		}
	}

	@Override
	public void onDataReceive(DataHandler dataHandler, int resultCode,
			Object data, int type) {
		myHandler.sendEmptyMessage(CLOSE_PROGRESS);
		switch (resultCode) {
		case NetWork.ADD_DRIVER_OK:
			doAddDriverSuccess(data);
			break;
		case NetWork.ADD_DRIVER_ERROR:
			ToastUtil.show(context,
					getResources().getString(R.string.network_error_hint));
			break;
		case NetWork.EDIT_DRIVER_INFO_OK:
			doEditDriverSuccess(data);
			break;
		case NetWork.EDIT_DRIVER_INFO_ERROR:
			ToastUtil.show(context,
					getResources().getString(R.string.network_error_hint));
			break;

		default:
			break;
		}
	}

	private void doEditDriverSuccess(Object data) {
		String dataString = null;
		try {
			dataString = new String((byte[]) data, "UTF-8");
		} catch (Exception e1) {
			e1.printStackTrace();
		}

		try {
			PdaResponse<String> mData = ResultCodeJsonParser
					.parserResultCodeJson(dataString);
			if (mData.isSuccess()) {
				// 成功
				Message msg = myHandler.obtainMessage();
				msg.what = SHOW_TOAST;
				msg.obj = "修改司机成功";
				myHandler.sendMessage(msg);
				Intent intent = new Intent();
				setResult(RESULT_OK, intent);
				finish();
			} else {// 失败
				String result = mData.getMessage();
				int messageCode = Integer.parseInt(result.substring(0,
						result.indexOf("#")));
				String message = result.substring(result.indexOf("#") + 1,
						result.length());
				Message msg = myHandler.obtainMessage();
				msg.what = SHOW_TOAST;
				msg.obj = message;
				myHandler.sendMessage(msg);
			}

		} catch (Exception e) {
			ToastUtil.show(context, "修改司机信息失败，请重新获取");
			e.printStackTrace();
		}
	}

	private void doAddDriverSuccess(Object data) {
		String dataString = null;
		try {
			dataString = new String((byte[]) data, "UTF-8");
		} catch (Exception e1) {
			e1.printStackTrace();
		}

		try {
			PdaResponse<String> mData = ResultCodeJsonParser
					.parserResultCodeJson(dataString);
			if (mData.isSuccess()) {
				// 成功
				Message msg = myHandler.obtainMessage();
				msg.what = SHOW_TOAST;
				msg.obj = "添加司机成功";
				myHandler.sendMessage(msg);
				Intent intent = new Intent();
				setResult(RESULT_OK, intent);
				finish();
			} else {// 失败
				String result = mData.getMessage();
				int messageCode = Integer.parseInt(result.substring(0,
						result.indexOf("#")));
				String message = result.substring(result.indexOf("#") + 1,
						result.length());
				Message msg = myHandler.obtainMessage();
				msg.what = SHOW_TOAST;
				msg.obj = message;
				myHandler.sendMessage(msg);
			}

		} catch (Exception e) {
			ToastUtil.show(context, "添加司机信息失败，请重新获取");
			e.printStackTrace();
		}
	}

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
		intent.putExtra("outputX", 800);
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
