package com.seeyuan.logistics.activity;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

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
import com.seeyuan.logistics.datahandler.AddCarHandler;
import com.seeyuan.logistics.datahandler.DeleteCarHandler;
import com.seeyuan.logistics.datahandler.UpdateCarHandler;
import com.seeyuan.logistics.entity.CarLengthInfo;
import com.seeyuan.logistics.entity.CarsDto;
import com.seeyuan.logistics.entity.ImageDto;
import com.seeyuan.logistics.entity.PdaRequest;
import com.seeyuan.logistics.entity.PdaResponse;
import com.seeyuan.logistics.jsonparser.CarSourceJsonParser;
import com.seeyuan.logistics.jsonparser.ResultCodeJsonParser;
import com.seeyuan.logistics.util.CommonUtils;
import com.seeyuan.logistics.util.ToastUtil;

/**
 * 添加新车辆管理
 * 
 * @author zhazhaobao
 * 
 */
public class NewCarManagerActivity extends BaseActivity implements
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
	 * 删除
	 */
	private TextView maintitle_comfirm_tv;

	/**
	 * 车牌号
	 */
	private MuInputEditText new_car_manager_car_plate;

	/**
	 * 车型
	 */
	private TextView new_car_manager_car_type;

	/**
	 * 车重
	 */
	private MuInputEditText new_car_manager_car_weight;

	/**
	 * 车长
	 */
	private TextView new_car_manager_car_length;

	/**
	 * 发动机号
	 */
	private MuInputEditText new_car_manager_car_engine;

	/**
	 * 车架号
	 */
	private MuInputEditText new_car_manager_car_identifyingCadr;

	/**
	 * 附属装备
	 */
	private TextView new_car_manager_atta_device;

	/**
	 * 保单号
	 */
	private MuInputEditText new_car_manager_insurance;

	/**
	 * 容积
	 */
	private MuInputEditText new_car_manager_volume;

	/**
	 * 道路运输证号
	 */
	private MuInputEditText new_car_manager_transLicense;

	/**
	 * 道路运输证号表示
	 */
	private String transLicensePhotoSign = "";

	/**
	 * 车辆类型
	 */
	private final int REFRESH_CAR_TYPE = 1000;

	/**
	 * 车辆长度
	 */
	private final int REFRESH_CAR_LENGTH = 1001;

	/**
	 * 车重
	 */
	private final int REFRESH_CAR_WEIGHT = 1002;

	/**
	 * 附属装备
	 */
	private final int REFRESH_ATTA_DIRVICE = 1003;

	private Context context;

	private CarsDto carsDto;

	private boolean isEditMode = false;

	private Button add_new_car_comfirm;

	/**
	 * 车辆照片
	 */
	private RemoteImageView new_car_manager_car_photo;
	private ImageDto carImgDto;
	// 车辆照片
	private final int CAR_REQUEST_CODE_PHOTOALBUM = 400;
	private final int CAR_REQUEST_CODE_PHOTO = 401;
	private final int CAR_REQUEST_CODE_PHOTOOK = 402;
	private final int CAR_REQUEST_CODE_PICK = 403;

	/**
	 * 保险车辆图片
	 */
	private RemoteImageView new_car_manager_insurance_phtot;
	private ImageDto driverLenenceDto;
	// 保险车辆照片
	private final int INSURANCE_REQUEST_CODE_PHOTOALBUM = 100;
	private final int INSURANCE_REQUEST_CODE_PHOTO = 101;
	private final int INSURANCE_REQUEST_CODE_PHOTOOK = 102;
	private final int INSURANCE_REQUEST_CODE_PICK = 103;
	/**
	 * 道路运输证图片
	 */
	private RemoteImageView new_car_manager_transLicense_photo;
	private ImageDto insuranceImageDto;
	// 道路运输证照片
	private final int TRANSLICENSE_REQUEST_CODE_PHOTOALBUM = 200;
	private final int TRANSLICENSE_REQUEST_CODE_PHOTO = 201;
	private final int TRANSLICENSE_REQUEST_CODE_PHOTOOK = 202;
	private final int TRANSLICENSE_REQUEST_CODE_PICK = 203;
	/**
	 * 行驶证照片
	 */
	private RemoteImageView new_car_manager_driving_license;
	private ImageDto transLicenseImageDto;
	// 行驶证照片
	private final int DRIVERLICENSE_REQUEST_CODE_PHOTOALBUM = 300;
	private final int DRIVERLICENSE_REQUEST_CODE_PHOTO = 301;
	private final int DRIVERLICENSE_REQUEST_CODE_PHOTOOK = 302;
	private final int DRIVERLICENSE_REQUEST_CODE_PICK = 303;

	private SelectPicPopupWindow dialog;

	private String headerImgPath;

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
		setContentView(R.layout.activity_new_car_manager); // 软件activity的布局
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,
				R.layout.deflaut_titlebar); // titlebar为自己标题栏的布局
		headerImgPath = "file://" + ConstantPool.DEFAULT_ICON_PATH
				+ "image_diy_takephoto.jpg";
		context = getApplicationContext();
		isEditMode = getIntent().getBooleanExtra("isEditMode", false);
		initView();
	}

	@Override
	public void initView() {

		if (isEditMode) {
			carsDto = (CarsDto) getIntent().getSerializableExtra("carInfo");
		} else {
			carsDto = new CarsDto();
		}

		maintitle_back_iv = (ImageView) findViewById(R.id.maintitle_back_iv);
		maintitle_back_iv.setOnClickListener(this);

		defaulttitle_title_tv = (TextView) findViewById(R.id.defaulttitle_title_tv);

		maintitle_comfirm_tv = (TextView) findViewById(R.id.maintitle_comfirm_tv);
		maintitle_comfirm_tv.setText(getResources().getString(R.string.delete));
		maintitle_comfirm_tv.setOnClickListener(this);

		add_new_car_comfirm = (Button) findViewById(R.id.add_new_car_comfirm);
		if (isEditMode) {
			add_new_car_comfirm
					.setText(getResources().getString(R.string.edit));
			defaulttitle_title_tv.setText(R.string.edit_new_car_manage_hint);
			maintitle_comfirm_tv.setVisibility(View.VISIBLE);
		} else {
			defaulttitle_title_tv.setText(R.string.add_new_car_manage_hint);
		}
		new_car_manager_car_plate = (MuInputEditText) findViewById(R.id.new_car_manager_car_plate);
		new_car_manager_car_plate.setText(TextUtils.isEmpty(carsDto
				.getVehicleNum()) ? "" : carsDto.getVehicleNum());
		new_car_manager_car_type = (TextView) findViewById(R.id.new_car_manager_car_type);
		new_car_manager_car_type.setOnClickListener(this);
		new_car_manager_car_type
				.setText(TextUtils.isEmpty(carsDto.getType()) ? "" : carsDto
						.getType());
		new_car_manager_car_weight = (MuInputEditText) findViewById(R.id.new_car_manager_car_weight);
		new_car_manager_car_weight
				.setText(carsDto.getCapacity() == null
						|| carsDto.getCapacity().toString()
								.equalsIgnoreCase("null") ? "" : String
						.valueOf(carsDto.getCapacity()));
		// new_car_manager_car_weight.setOnClickListener(this);
		new_car_manager_car_length = (TextView) findViewById(R.id.new_car_manager_car_length);
		new_car_manager_car_length.setOnClickListener(this);
		new_car_manager_car_length.setText(TextUtils.isEmpty(carsDto
				.getLength()) ? "" : carsDto.getLength() + "米");
		new_car_manager_car_photo = (RemoteImageView) findViewById(R.id.new_car_manager_car_photo);
		new_car_manager_car_photo.draw(null == carsDto.getVehiclePhoto() ? ""
				: carsDto.getVehiclePhoto().getHeaderImgURL(),
				ConstantPool.DEFAULT_ICON_PATH, false, false);
		new_car_manager_car_photo.setOnClickListener(this);
		new_car_manager_car_engine = (MuInputEditText) findViewById(R.id.new_car_manager_car_engine);
		new_car_manager_car_engine.setText(TextUtils.isEmpty(carsDto
				.getEngineNo()) ? "" : carsDto.getEngineNo());
		new_car_manager_car_identifyingCadr = (MuInputEditText) findViewById(R.id.new_car_manager_car_identifyingCadr);
		new_car_manager_car_identifyingCadr.setText(TextUtils.isEmpty(carsDto
				.getVin()) ? "" : carsDto.getVin());
		new_car_manager_driving_license = (RemoteImageView) findViewById(R.id.new_car_manager_driving_license);
		new_car_manager_driving_license.draw(null == carsDto
				.getVehicleLicensePhoto() ? "" : carsDto
				.getVehicleLicensePhoto().getHeaderImgURL(),
				ConstantPool.DEFAULT_ICON_PATH, false, false);
		new_car_manager_driving_license.setOnClickListener(this);

		new_car_manager_atta_device = (TextView) findViewById(R.id.new_car_manager_atta_device);
		new_car_manager_atta_device.setText(TextUtils.isEmpty(carsDto
				.getAttaDevice()) ? "" : carsDto.getAttaDevice());
		new_car_manager_atta_device.setOnClickListener(this);
		new_car_manager_insurance = (MuInputEditText) findViewById(R.id.new_car_manager_insurance);
		new_car_manager_insurance.setText(TextUtils.isEmpty(carsDto
				.getInsurance()) ? "" : carsDto.getInsurance());
		new_car_manager_insurance_phtot = (RemoteImageView) findViewById(R.id.new_car_manager_insurance_phtot);
		new_car_manager_insurance_phtot.draw(null == carsDto
				.getInsuranceImage() ? "" : carsDto.getInsuranceImage()
				.getHeaderImgURL(), ConstantPool.DEFAULT_ICON_PATH, false,
				false);
		new_car_manager_insurance_phtot.setOnClickListener(this);
		new_car_manager_volume = (MuInputEditText) findViewById(R.id.new_car_manager_volume);
		new_car_manager_volume.setText(null == carsDto.getVolume() ? ""
				: carsDto.getVolume().toString());
		new_car_manager_transLicense = (MuInputEditText) findViewById(R.id.new_car_manager_transLicense);
		new_car_manager_transLicense.setText(TextUtils.isEmpty(carsDto
				.getTransLicense()) ? "" : carsDto.getTransLicense());
		new_car_manager_transLicense_photo = (RemoteImageView) findViewById(R.id.new_car_manager_transLicense_photo);
		new_car_manager_transLicense_photo.draw(null == carsDto
				.getTransLicenseImage() ? "" : carsDto.getTransLicenseImage()
				.getHeaderImgURL(), ConstantPool.DEFAULT_ICON_PATH, false,
				false);
		new_car_manager_transLicense_photo.setOnClickListener(this);

		carImgDto = null == carsDto.getVehiclePhoto() ? new ImageDto()
				: carsDto.getVehiclePhoto();
		driverLenenceDto = null == carsDto.getInsuranceImage() ? new ImageDto()
				: carsDto.getInsuranceImage();
		insuranceImageDto = null == carsDto.getTransLicenseImage() ? new ImageDto()
				: carsDto.getTransLicenseImage();
		transLicenseImageDto = null == carsDto.getVehicleLicensePhoto() ? new ImageDto()
				: carsDto.getVehicleLicensePhoto();

		transLicensePhotoSign = null == carsDto.getTransLicenseImage() ? ""
				: "Y";
	}

	private Handler myHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case REFRESH_CAR_TYPE:
				String typeString = msg.obj.toString();
				new_car_manager_car_type.setText(typeString);
				carsDto.setType(typeString);
				break;
			case REFRESH_CAR_LENGTH:
				String lengthString = msg.obj.toString();
				new_car_manager_car_length.setText(lengthString
						.equalsIgnoreCase("全部") ? msg.obj.toString()
						: lengthString + "米");
				carsDto.setLength(lengthString);
				break;
			case REFRESH_CAR_WEIGHT:
				String weightString = msg.obj.toString();
				new_car_manager_car_weight.setText(weightString);
				break;
			case REFRESH_ATTA_DIRVICE:
				new_car_manager_atta_device.setText(msg.obj.toString());
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
	public void onClickListener(View view) {
		switch (view.getId()) {
		case R.id.add_new_car_comfirm:
			new Thread(new Runnable() {

				@Override
				public void run() {
					if (isEditMode) {
						doEdit();
					} else {
						doComfirm();
					}
				}
			}).start();

			break;
		case R.id.add_new_car_cancel:
			doCancel();
			break;

		default:
			break;
		}

	}

	/**
	 * 取消
	 */
	private void doCancel() {
		CommonUtils.finishActivity(NewCarManagerActivity.this);
	}

	/**
	 * 确定，保存
	 */
	private void doComfirm() {
		String result = isCanComfirm();
		if (result.equalsIgnoreCase("成功")) {
			AddNewCar();
		} else {
			Message msg = myHandler.obtainMessage();
			msg.what = SHOW_TOAST;
			msg.obj = result;
			myHandler.sendMessage(msg);
		}
	}

	/**
	 * 确定，修改
	 */
	private void doEdit() {
		String result = isCanComfirm();
		if (result.equalsIgnoreCase("成功")) {
			EditCar();
		} else {
			Message msg = myHandler.obtainMessage();
			msg.what = SHOW_TOAST;
			msg.obj = result;
			myHandler.sendMessage(msg);
		}
	}

	private String isCanComfirm() {
		String result = null;
		Filter carPlateFilter = new CarPlateFilter();
		Filter carTypeFilter = new CarTypeFilter();
		Filter carWeightFilter = new CarWeightFilter();
		Filter carLengthFilter = new CarLengthFilter();
		Filter engineFilter = new EngineNOFilter();
		Filter vinFilter = new VinFilter();
		Filter transLicense = new TransLicenseFilter();
		Filter transLicensePhoto = new TransLicensePhotoFilter();

		carPlateFilter.setNext(carTypeFilter);
		carTypeFilter.setNext(carWeightFilter);
		carWeightFilter.setNext(carLengthFilter);
		carLengthFilter.setNext(engineFilter);
		engineFilter.setNext(vinFilter);
		vinFilter.setNext(transLicense);
		transLicense.setNext(transLicensePhoto);

		result = carPlateFilter.doFilter(new_car_manager_car_plate.getText()
				.toString(), new_car_manager_car_type.getText().toString(),
				new_car_manager_car_weight.getText().toString(),
				new_car_manager_car_length.getText().toString(),
				new_car_manager_car_engine.getText().toString(),
				new_car_manager_car_identifyingCadr.getText().toString(),
				new_car_manager_transLicense.getText().toString(),
				transLicensePhotoSign);

		return result;
	}

	/**
	 * 添加新车辆
	 */
	private void AddNewCar() {
		myHandler.sendEmptyMessage(SHOW_PROGRESS);
		carsDto.setVehicleNum(new_car_manager_car_plate.getText().toString());
		carsDto.setCapacity(BigDecimal.valueOf(Double
				.parseDouble(new_car_manager_car_weight.getText().toString())));
		carsDto.setEngineNo(new_car_manager_car_engine.getText().toString());
		carsDto.setVin(new_car_manager_car_identifyingCadr.getText().toString());
		carsDto.setAttaDevice(new_car_manager_atta_device.getText().toString());
		carsDto.setInsurance(new_car_manager_insurance.getText().toString());
		carsDto.setVolume(TextUtils.isEmpty(new_car_manager_volume.getText()
				.toString()) ? null : BigDecimal.valueOf(Double
				.parseDouble(new_car_manager_volume.getText().toString())));
		carsDto.setTransLicense(new_car_manager_transLicense.getText()
				.toString());
		carsDto.setVehiclePhoto(carImgDto);
		carsDto.setVehicleLicensePhoto(transLicenseImageDto);
		carsDto.setInsuranceImage(driverLenenceDto);
		carsDto.setTransLicenseImage(insuranceImageDto);
		PdaRequest<CarsDto> request = new PdaRequest<CarsDto>();
		request.setData(carsDto);
		AddCarHandler dataHandler = new AddCarHandler(context, request);
		dataHandler.setOnDataReceiveListener(this);
		dataHandler.startNetWork();
	}

	/**
	 * 修改车辆信息
	 */
	private void EditCar() {
		myHandler.sendEmptyMessage(SHOW_PROGRESS);
		carsDto.setVehicleNum(new_car_manager_car_plate.getText().toString());
		carsDto.setCapacity(BigDecimal.valueOf(Double
				.parseDouble(new_car_manager_car_weight.getText().toString())));
		carsDto.setEngineNo(new_car_manager_car_engine.getText().toString());
		carsDto.setVin(new_car_manager_car_identifyingCadr.getText().toString());

		carsDto.setVehiclePhoto(carImgDto);
		carsDto.setVehicleLicensePhoto(transLicenseImageDto);
		carsDto.setInsuranceImage(driverLenenceDto);
		carsDto.setTransLicenseImage(insuranceImageDto);
		PdaRequest<CarsDto> request = new PdaRequest<CarsDto>();
		request.setData(carsDto);
		UpdateCarHandler dataHandler = new UpdateCarHandler(context, request);
		dataHandler.setOnDataReceiveListener(this);
		dataHandler.startNetWork();
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.maintitle_back_iv:
			CommonUtils.finishActivity(NewCarManagerActivity.this);
			break;
		case R.id.new_car_manager_car_type:
			CommonUtils.selectCarInfo(NewCarManagerActivity.this, myHandler,
					REFRESH_CAR_TYPE, R.array.CarType_Str,
					R.string.PublishInfo_CarTypes_Hint);
			break;
		case R.id.new_car_manager_car_weight:
			CommonUtils.selectCarInfo(NewCarManagerActivity.this, myHandler,
					REFRESH_CAR_WEIGHT, R.array.Search_car_weight,
					R.string.PublishInfo_CarWeight_Hint);
			break;
		case R.id.new_car_manager_car_length:
			CommonUtils.selectCarInfo(NewCarManagerActivity.this, myHandler,
					REFRESH_CAR_LENGTH, R.array.Search_car_length,
					R.string.PublishInfo_Carlen_Hint);
			break;
		case R.id.maintitle_comfirm_tv:
			new Thread(new Runnable() {

				@Override
				public void run() {
					doDeleteCar();
				}
			}).start();
			break;
		case R.id.new_car_manager_car_photo:// 车辆照片
			doCarPhoto();
			break;
		case R.id.new_car_manager_insurance_phtot:// 保险车辆照片
			doInsurancePhoto();
			break;
		case R.id.new_car_manager_transLicense_photo:// 道路运输证照片
			doTranslicensePhoto();
			break;
		case R.id.new_car_manager_driving_license:// 行驶证照片
			doDrivingLicesePhoto();
			break;
		case R.id.new_car_manager_atta_device:
			doAttaDevice();
			break;

		default:
			break;
		}
	}

	/**
	 * 附属装备
	 */
	private void doAttaDevice() {

		List<CarLengthInfo> mDataList = new ArrayList<CarLengthInfo>();
		TypedArray typedArray = getResources().obtainTypedArray(
				R.array.att_device_type);
		int size = typedArray.length();
		for (int i = 0; i < size; i++) {
			CarLengthInfo indexInfo = new CarLengthInfo();
			indexInfo.setCar_Length(typedArray.getString(i));
			mDataList.add(indexInfo);
		}

		final SingleSelectAlertDlialog ad = new SingleSelectAlertDlialog(
				NewCarManagerActivity.this);
		ad.setTitle(getResources().getString(R.string.atta_device_hint));
		ad.setListContentForCarLength(mDataList);
		ad.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View view, int arg2,
					long arg3) {
				if (null == view)
					return;
				ad.dismiss();
				Message message = myHandler.obtainMessage();
				message.what = REFRESH_ATTA_DIRVICE;
				message.obj = ((TextView) view
						.findViewById(R.id.item_car_length)).getText();
				myHandler.sendMessage(message);

			}
		});
		typedArray.recycle();
	}

	/**
	 * 删除车辆
	 */
	private void doDeleteCar() {
		PdaRequest<List<CarsDto>> request = new PdaRequest<List<CarsDto>>();
		List<CarsDto> list = new ArrayList<CarsDto>();
		list.add(carsDto);
		request.setData(list);
		DeleteCarHandler dataHandler = new DeleteCarHandler(context, request);
		dataHandler.setOnDataReceiveListener(this);
		dataHandler.startNetWork();
	}

	@Override
	public void onDataReceive(DataHandler dataHandler, int resultCode,
			Object data, int type) {
		myHandler.sendEmptyMessage(CLOSE_PROGRESS);
		switch (resultCode) {
		case NetWork.ADD_CAR_OK:
			doAddCarSuccess(data);
			break;
		case NetWork.ADD_CAR_ERROR:
			ToastUtil.show(context,
					getResources().getString(R.string.network_error_hint));
			break;
		case NetWork.UPDATE_CAR_OK:
			doUpdateCarSuccess(data);
			break;
		case NetWork.UPDATE_CAR_ERROR:
			ToastUtil.show(context,
					getResources().getString(R.string.network_error_hint));
			break;
		case NetWork.DELETE_CAR_OK:
			doDeleteCarSuccess(data);
			break;
		case NetWork.DELETE_CAR_ERROR:
			ToastUtil.show(context,
					getResources().getString(R.string.network_error_hint));
			break;

		default:
			break;
		}
	}

	private void doDeleteCarSuccess(Object data) {

		String dataString = null;
		try {
			dataString = new String((byte[]) data, "UTF-8");
			Log.i("车辆删除", dataString);
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
				msg.obj = "删除车辆成功";
				myHandler.sendMessage(msg);
				Intent intent = new Intent();
				intent.putExtra("carManagerInfo", carsDto);
				intent.putExtra("isDelete", true);
				setResult(RESULT_OK, intent);
				finish();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void doUpdateCarSuccess(Object data) {
		String dataString = null;
		try {
			dataString = new String((byte[]) data, "UTF-8");
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
			if (!mData.isSuccess()) {
				msg.obj = message;
				myHandler.sendMessage(msg);
				return;
			} else {
				msg.obj = "更新车辆信息成功";
				myHandler.sendMessage(msg);
				Intent intent = new Intent();
				intent.putExtra("carManagerInfo", carsDto);
				setResult(RESULT_OK, intent);
				finish();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void doAddCarSuccess(Object data) {
		String dataString = null;
		try {
			dataString = new String((byte[]) data, "UTF-8");
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
			if (!mData.isSuccess()) {
				msg.obj = message;
				myHandler.sendMessage(msg);
				return;
			} else {
				msg.obj = "更新车辆信息成功";
				myHandler.sendMessage(msg);
				Intent intent = new Intent();
				intent.putExtra("carManagerInfo", carsDto);
				setResult(RESULT_OK, intent);
				finish();
			}
		} catch (Exception e) {
			e.printStackTrace();
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

		public String doFilter(String carPlate, String carType,
				String carWeight, String carLength, String engineNO,
				String vin, String transLicense, String transLicensePhoto) {

			if (next == null) {
				return "成功";
			} else
				return next.doFilter(carPlate, carType, carWeight, carLength,
						engineNO, vin, transLicense, transLicensePhoto);

		}

	}

	class CarPlateFilter extends Filter {

		public String doFilter(String carPlate, String carType,
				String carWeight, String carLength, String engineNO,
				String vin, String transLicense, String transLicensePhoto) {

			if (TextUtils.isEmpty(carPlate)) {
				return "请输入正确的车牌号";
			} else
				return super.doFilter(carPlate, carType, carWeight, carLength,
						engineNO, vin, transLicense, transLicensePhoto);
		}
	}

	class CarTypeFilter extends Filter {

		public String doFilter(String carPlate, String carType,
				String carWeight, String carLength, String engineNO,
				String vin, String transLicense, String transLicensePhoto) {

			if (TextUtils.isEmpty(carType)) {
				return "请选择正确的车型";
			} else
				return super.doFilter(carPlate, carType, carWeight, carLength,
						engineNO, vin, transLicense, transLicensePhoto);
		}
	}

	class CarWeightFilter extends Filter {

		public String doFilter(String carPlate, String carType,
				String carWeight, String carLength, String engineNO,
				String vin, String transLicense, String transLicensePhoto) {

			if (TextUtils.isEmpty(carWeight)) {
				return "请选择正确的车重";
			} else
				return super.doFilter(carPlate, carType, carWeight, carLength,
						engineNO, vin, transLicense, transLicensePhoto);
		}
	}

	class CarLengthFilter extends Filter {

		public String doFilter(String carPlate, String carType,
				String carWeight, String carLength, String engineNO,
				String vin, String transLicense, String transLicensePhoto) {

			if (TextUtils.isEmpty(carLength)) {
				return "请选择正确的车长";
			} else
				return super.doFilter(carPlate, carType, carWeight, carLength,
						engineNO, vin, transLicense, transLicensePhoto);
		}
	}

	class EngineNOFilter extends Filter {

		public String doFilter(String carPlate, String carType,
				String carWeight, String carLength, String engineNO,
				String vin, String transLicense, String transLicensePhoto) {

			if (TextUtils.isEmpty(engineNO)) {
				return "请选择正确的发动机号码";
			} else
				return super.doFilter(carPlate, carType, carWeight, carLength,
						engineNO, vin, transLicense, transLicensePhoto);
		}
	}

	class VinFilter extends Filter {

		public String doFilter(String carPlate, String carType,
				String carWeight, String carLength, String engineNO,
				String vin, String transLicense, String transLicensePhoto) {

			if (TextUtils.isEmpty(vin)) {
				return "请选择正确的车辆识别号";
			} else
				return super.doFilter(carPlate, carType, carWeight, carLength,
						engineNO, vin, transLicense, transLicensePhoto);
		}
	}

	class TransLicenseFilter extends Filter {

		public String doFilter(String carPlate, String carType,
				String carWeight, String carLength, String engineNO,
				String vin, String transLicense, String transLicensePhoto) {

			if (TextUtils.isEmpty(transLicense)) {
				return "请选择正确的道路运输证号";
			} else
				return super.doFilter(carPlate, carType, carWeight, carLength,
						engineNO, vin, transLicense, transLicensePhoto);
		}
	}

	class TransLicensePhotoFilter extends Filter {

		public String doFilter(String carPlate, String carType,
				String carWeight, String carLength, String engineNO,
				String vin, String transLicense, String transLicensePhoto) {

			if (TextUtils.isEmpty(transLicensePhoto)) {
				return "请选择正确的道路运输证图片";
			} else
				return super.doFilter(carPlate, carType, carWeight, carLength,
						engineNO, vin, transLicense, transLicensePhoto);
		}
	}

	/**
	 * 车辆照片
	 */
	private void doCarPhoto() {
		showOptionDialog(CAR_REQUEST_CODE_PHOTOALBUM, CAR_REQUEST_CODE_PHOTO);

	}

	/**
	 * 保险车辆照片
	 */
	private void doInsurancePhoto() {

		showOptionDialog(INSURANCE_REQUEST_CODE_PHOTOALBUM,
				INSURANCE_REQUEST_CODE_PHOTO);

	}

	/**
	 * 道路运输证照片
	 */
	private void doTranslicensePhoto() {

		showOptionDialog(TRANSLICENSE_REQUEST_CODE_PHOTOALBUM,
				TRANSLICENSE_REQUEST_CODE_PHOTO);

	}

	/**
	 * 行驶证照片
	 */
	private void doDrivingLicesePhoto() {

		showOptionDialog(DRIVERLICENSE_REQUEST_CODE_PHOTOALBUM,
				DRIVERLICENSE_REQUEST_CODE_PHOTO);

	}

	private void showOptionDialog(final int photoCode, final int cameraCode) {
		dialog = new SelectPicPopupWindow(NewCarManagerActivity.this);
		dialog.setFirstButtonContent(getResources().getString(
				R.string.take_photo_hint));
		dialog.setFirstButtonOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				CommonUtils.selectCameraPhone(cameraCode, headerImgPath,
						NewCarManagerActivity.this);
				dialog.dismiss();
			}
		});
		dialog.setSecendButtonContent(getResources().getString(
				R.string.get_system_photo_hint));
		dialog.setSecendButtonOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				CommonUtils.selectSystemPhone(photoCode,
						NewCarManagerActivity.this);
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
				NewCarManagerActivity.this.findViewById(R.id.main),
				Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0); // 设置layout在PopupWindow中显示的位置

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode != RESULT_OK)
			return;
		switch (requestCode) {
		case CAR_REQUEST_CODE_PHOTOALBUM:
			if (data != null) {
				startPhotoZoom(data.getData(), CAR_REQUEST_CODE_PHOTOOK);
			}
			break;
		case CAR_REQUEST_CODE_PHOTO:
			filePath = "file://" + ConstantPool.DEFAULT_ICON_PATH
					+ "image_diy_takephoto.jpg";
			if (filePath != null) {
				cropPhoto(filePath, CAR_REQUEST_CODE_PICK);
			}
			break;
		case CAR_REQUEST_CODE_PHOTOOK:
			resultBitmap = BitmapFactory
					.decodeFile(ConstantPool.DEFAULT_ICON_PATH
							+ "image_diy_resultphoto_temp.jpg");
			carImgDto.setImageSuffix("PNG");
			carImgDto.setFile(CommonUtils.getBitmapByByte(BitmapFactory
					.decodeFile(ConstantPool.DEFAULT_ICON_PATH
							+ "image_diy_resultphoto_temp.jpg")));
			new_car_manager_car_photo.setImageBitmap(resultBitmap);
			break;
		case CAR_REQUEST_CODE_PICK:
			resultBitmap = BitmapFactory
					.decodeFile(ConstantPool.DEFAULT_ICON_PATH
							+ "image_diy_resultphoto.jpg");
			carImgDto.setImageSuffix("PNG");
			carImgDto.setFile(CommonUtils.getBitmapByByte(BitmapFactory
					.decodeFile(ConstantPool.DEFAULT_ICON_PATH
							+ "image_diy_resultphoto_temp.jpg")));
			new_car_manager_car_photo.setImageBitmap(resultBitmap);
			break;

		case INSURANCE_REQUEST_CODE_PHOTOALBUM:
			if (data != null) {
				startPhotoZoom(data.getData(), INSURANCE_REQUEST_CODE_PHOTOOK);
			}
			break;
		case INSURANCE_REQUEST_CODE_PHOTO:
			filePath = "file://" + ConstantPool.DEFAULT_ICON_PATH
					+ "image_diy_takephoto.jpg";
			if (filePath != null) {
				cropPhoto(filePath, INSURANCE_REQUEST_CODE_PICK);
			}
			break;
		case INSURANCE_REQUEST_CODE_PHOTOOK:
			resultBitmap = BitmapFactory
					.decodeFile(ConstantPool.DEFAULT_ICON_PATH
							+ "image_diy_resultphoto_temp.jpg");
			driverLenenceDto.setImageSuffix("PNG");
			driverLenenceDto.setFile(CommonUtils.getBitmapByByte(BitmapFactory
					.decodeFile(ConstantPool.DEFAULT_ICON_PATH
							+ "image_diy_resultphoto_temp.jpg")));
			new_car_manager_insurance_phtot.setImageBitmap(resultBitmap);
			break;
		case INSURANCE_REQUEST_CODE_PICK:
			resultBitmap = BitmapFactory
					.decodeFile(ConstantPool.DEFAULT_ICON_PATH
							+ "image_diy_resultphoto.jpg");
			driverLenenceDto.setImageSuffix("PNG");
			driverLenenceDto.setFile(CommonUtils.getBitmapByByte(BitmapFactory
					.decodeFile(ConstantPool.DEFAULT_ICON_PATH
							+ "image_diy_resultphoto_temp.jpg")));
			new_car_manager_insurance_phtot.setImageBitmap(resultBitmap);
			break;

		case TRANSLICENSE_REQUEST_CODE_PHOTOALBUM:
			if (data != null) {
				startPhotoZoom(data.getData(),
						TRANSLICENSE_REQUEST_CODE_PHOTOOK);
			}
			break;
		case TRANSLICENSE_REQUEST_CODE_PHOTO:
			filePath = "file://" + ConstantPool.DEFAULT_ICON_PATH
					+ "image_diy_takephoto.jpg";
			if (filePath != null) {
				cropPhoto(filePath, TRANSLICENSE_REQUEST_CODE_PICK);
			}
			break;
		case TRANSLICENSE_REQUEST_CODE_PHOTOOK:
			resultBitmap = BitmapFactory
					.decodeFile(ConstantPool.DEFAULT_ICON_PATH
							+ "image_diy_resultphoto_temp.jpg");
			transLicensePhotoSign = "Y";
			insuranceImageDto.setImageSuffix("PNG");
			insuranceImageDto.setFile(CommonUtils.getBitmapByByte(BitmapFactory
					.decodeFile(ConstantPool.DEFAULT_ICON_PATH
							+ "image_diy_resultphoto_temp.jpg")));
			new_car_manager_transLicense_photo.setImageBitmap(resultBitmap);
			break;
		case TRANSLICENSE_REQUEST_CODE_PICK:
			resultBitmap = BitmapFactory
					.decodeFile(ConstantPool.DEFAULT_ICON_PATH
							+ "image_diy_resultphoto.jpg");
			transLicensePhotoSign = "Y";
			insuranceImageDto.setImageSuffix("PNG");
			insuranceImageDto.setFile(CommonUtils.getBitmapByByte(BitmapFactory
					.decodeFile(ConstantPool.DEFAULT_ICON_PATH
							+ "image_diy_resultphoto_temp.jpg")));
			new_car_manager_transLicense_photo.setImageBitmap(resultBitmap);
			break;

		case DRIVERLICENSE_REQUEST_CODE_PHOTOALBUM:
			if (data != null) {
				startPhotoZoom(data.getData(),
						DRIVERLICENSE_REQUEST_CODE_PHOTOOK);
			}
			break;
		case DRIVERLICENSE_REQUEST_CODE_PHOTO:
			filePath = "file://" + ConstantPool.DEFAULT_ICON_PATH
					+ "image_diy_takephoto.jpg";
			if (filePath != null) {
				cropPhoto(filePath, DRIVERLICENSE_REQUEST_CODE_PICK);
			}
			break;
		case DRIVERLICENSE_REQUEST_CODE_PHOTOOK:
			resultBitmap = BitmapFactory
					.decodeFile(ConstantPool.DEFAULT_ICON_PATH
							+ "image_diy_resultphoto_temp.jpg");
			transLicenseImageDto.setImageSuffix("PNG");
			transLicenseImageDto.setFile(CommonUtils
					.getBitmapByByte(BitmapFactory
							.decodeFile(ConstantPool.DEFAULT_ICON_PATH
									+ "image_diy_resultphoto_temp.jpg")));
			new_car_manager_driving_license.setImageBitmap(resultBitmap);
			break;
		case DRIVERLICENSE_REQUEST_CODE_PICK:
			resultBitmap = BitmapFactory
					.decodeFile(ConstantPool.DEFAULT_ICON_PATH
							+ "image_diy_resultphoto.jpg");
			transLicenseImageDto.setImageSuffix("PNG");
			transLicenseImageDto.setFile(CommonUtils
					.getBitmapByByte(BitmapFactory
							.decodeFile(ConstantPool.DEFAULT_ICON_PATH
									+ "image_diy_resultphoto_temp.jpg")));
			new_car_manager_driving_license.setImageBitmap(resultBitmap);
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
}
