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
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.TextView;

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
import com.seeyuan.logistics.datahandler.AccountInfoHandler;
import com.seeyuan.logistics.datahandler.RechargeHandler;
import com.seeyuan.logistics.entity.AccountDto;
import com.seeyuan.logistics.entity.AccountInDto;
import com.seeyuan.logistics.entity.CarLengthInfo;
import com.seeyuan.logistics.entity.ImageDto;
import com.seeyuan.logistics.entity.PayInfo;
import com.seeyuan.logistics.entity.PdaPagination;
import com.seeyuan.logistics.entity.PdaRequest;
import com.seeyuan.logistics.entity.PdaResponse;
import com.seeyuan.logistics.jsonparser.AccountInfoJsonParser;
import com.seeyuan.logistics.jsonparser.ResultCodeJsonParser;
import com.seeyuan.logistics.provider.DBOperate;
import com.seeyuan.logistics.util.CommonUtils;
import com.seeyuan.logistics.util.ToastUtil;

/**
 * 充值详情
 * 
 * @author Administrator
 * 
 */
public class PayDetailActivity extends BaseActivity implements OnClickListener,
		OnDataReceiveListener {

	private Context context;

	/**
	 * 返回按钮
	 */
	private ImageView maintitle_back_iv;

	/**
	 * 标题title
	 */
	private TextView defaulttitle_title_tv;

	private PayInfo payInfo;

	/**
	 * 充值金额
	 */
	private TextView pay_detail_money;

	/**
	 * 快速选择账号
	 */
	private TextView pay_detail_account;

	/**
	 * 账户类型
	 */
	private TextView pay_detail_type;

	/**
	 * 账户名称
	 */
	private TextView pay_detail_name;

	/**
	 * 账号号码
	 */
	private TextView pay_detail_number;

	/**
	 * 凭证图片
	 */
	private RemoteImageView voucher_photo;

	/**
	 * 凭证DTO
	 */
	private ImageDto voucher_photo_dto = new ImageDto();

	private String photoFlag;

	private final int REFRESH_ACCOUNT_TYPE = 1000;
	private final int REFRESH_SPEED_SELECT = 1001;
	private final int SHOW_PROGRESS = 1002;
	private final int CLOSE_PROGRESS = 1003;
	private final int SHOW_TOAST = 1004;

	private final int REQUEST_CODE_PHOTOALBUM = 500;
	private final int REQUEST_CODE_PHOTO = 501;
	private final int REQUEST_CODE_PHOTOOK = 502;
	private final int REQUEST_CODE_PICK = 503;

	private DBOperate dbOperate;

	private List<AccountDto> allAccountInfo;

	private ProgressAlertDialog progressDialog;

	private AccountInDto accountInDto;

	private AccountDto accountDto;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.activity_pay_detail); // 软件activity的布局
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,
				R.layout.deflaut_titlebar); // titlebar为自己标题栏的布局
		context = getApplicationContext();
		dbOperate = DBOperate.getInstance(context);
		payInfo = (PayInfo) getIntent().getSerializableExtra("payInfo");
		initView();
		getAccountInfo();
	}

	@Override
	protected void onStart() {
		super.onStart();
		allAccountInfo = dbOperate.getAllAccount();
	}

	/**
	 * 获取充值账户信息
	 */
	private void getAccountInfo() {
		myHandler.sendEmptyMessage(SHOW_PROGRESS);
		PdaRequest<String> request = new PdaRequest<String>();
		request.setData("");
		PdaPagination pagination = new PdaPagination();
		pagination.setNeedsPaginate(false);
		request.setPagination(pagination);
		AccountInfoHandler dataHandler = new AccountInfoHandler(context,
				request);
		dataHandler.setOnDataReceiveListener(this);
		dataHandler.startNetWork();
	}

	@Override
	public void onClickListener(View view) {
		switch (view.getId()) {
		case R.id.bt_save:
			String result = isCanRecharge();
			if (result.equalsIgnoreCase("成功")) {
				new Thread(new Runnable() {

					@Override
					public void run() {
						doRecharge();
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

	/**
	 * 充值
	 */
	private void doRecharge() {
		myHandler.sendEmptyMessage(SHOW_PROGRESS);
		accountInDto = new AccountInDto();
		accountInDto.setInAmount(BigDecimal.valueOf(Double
				.parseDouble(pay_detail_money.getText().toString())));
		voucher_photo_dto.setImageSuffix("PNG");
		voucher_photo_dto.setFile(CommonUtils.getBitmapByByte(resultBitmap));
		accountInDto.setVoucher(voucher_photo_dto);

		String accType = CommonUtils.getBankID(pay_detail_type.getText()
				.toString());
		String name = pay_detail_name.getText().toString();
		String accountNum = pay_detail_number.getText().toString();

		if (null == accountDto
				|| !accountDto.getAccType().equalsIgnoreCase(accType)
				|| !accountDto.getName().equalsIgnoreCase(name)
				|| !accountDto.getAccountNum().equalsIgnoreCase(accountNum)) {
			accountDto = new AccountDto();
		}
		accountDto.setAccType(accType);
		accountDto.setName(name);
		accountDto.setAccountNum(accountNum);

		accountInDto.setAccountDto(accountDto);

		PdaRequest<AccountInDto> request = new PdaRequest<AccountInDto>();
		request.setData(accountInDto);
		RechargeHandler dataHandler = new RechargeHandler(context, request);
		dataHandler.setOnDataReceiveListener(this);
		dataHandler.startNetWork();

	}

	private String isCanRecharge() {
		Filter money = new moneyFilder();
		Filter type = new typeFilder();
		Filter name = new nameFilder();
		Filter number = new numberFilder();
		Filter photo = new photoFilder();

		money.setNext(type);
		type.setNext(name);
		name.setNext(number);
		number.setNext(photo);

		String result = money.doFilter(pay_detail_money.getText().toString(),
				pay_detail_type.getText().toString(), pay_detail_name.getText()
						.toString(), pay_detail_number.getText().toString(),
				photoFlag);

		return result;
	}

	@Override
	public void initView() {
		maintitle_back_iv = (ImageView) findViewById(R.id.maintitle_back_iv);
		maintitle_back_iv.setOnClickListener(this);

		defaulttitle_title_tv = (TextView) findViewById(R.id.defaulttitle_title_tv);
		defaulttitle_title_tv.setText("充值详情");
		
		pay_detail_money = (TextView) findViewById(R.id.pay_detail_money);
		pay_detail_money.setText(payInfo.getAmount());
		
		pay_detail_account = (TextView) findViewById(R.id.pay_detail_account);
		pay_detail_account.setOnClickListener(this);

		pay_detail_type = (TextView) findViewById(R.id.pay_detail_type);
		pay_detail_type.setOnClickListener(this);

		pay_detail_name = (TextView) findViewById(R.id.pay_detail_name);

		pay_detail_number = (TextView) findViewById(R.id.pay_detail_number);

		voucher_photo = (RemoteImageView) findViewById(R.id.voucher_photo);
		voucher_photo.setOnClickListener(this);
	}

	private Handler myHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case REFRESH_ACCOUNT_TYPE:
				pay_detail_type.setText(msg.obj.toString());
				break;
			case REFRESH_SPEED_SELECT:
				refreshSpeedSelect((AccountDto) msg.obj);
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

	/**
	 * 快速选择，界面刷新
	 */
	private void refreshSpeedSelect(AccountDto accountInfo) {
		pay_detail_type.setText(CommonUtils.getBankName(accountInfo
				.getAccType()));
		pay_detail_name.setText(accountInfo.getName());
		pay_detail_number.setText(accountInfo.getAccountNum());
		accountDto = accountInfo;
	};

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.maintitle_back_iv:
			CommonUtils.finishActivity(this);
			break;
		case R.id.pay_detail_account:
			doSelectAccount();
			break;
		case R.id.pay_detail_type:
			doSelectType();
			break;
		case R.id.voucher_photo:
			doSelectVoucherPhoto();
			break;

		default:
			break;
		}
	}

	/**
	 * 选择凭证
	 */
	private void doSelectVoucherPhoto() {
		showOptionDialog(REQUEST_CODE_PHOTOALBUM, REQUEST_CODE_PHOTO);
	}

	/**
	 * 快速选择账户类型
	 */
	private void doSelectType() {

		List<CarLengthInfo> mDataList = new ArrayList<CarLengthInfo>();
		TypedArray typedArray = getResources().obtainTypedArray(
				R.array.account_type);
		int size = typedArray.length();
		for (int i = 0; i < size; i++) {
			CarLengthInfo indexInfo = new CarLengthInfo();
			indexInfo.setCar_Length(typedArray.getString(i));
			mDataList.add(indexInfo);
		}

		final SingleSelectAlertDlialog ad = new SingleSelectAlertDlialog(this);
		ad.setTitle("选择账户");
		ad.setListContentForCarLength(mDataList);
		ad.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View view, int arg2,
					long arg3) {
				if (null == view)
					return;
				ad.dismiss();
				Message message = myHandler.obtainMessage();
				message.what = REFRESH_ACCOUNT_TYPE;
				message.obj = ((TextView) view
						.findViewById(R.id.item_car_length)).getText();
				myHandler.sendMessage(message);

			}
		});
		typedArray.recycle();
	}

	/**
	 * 快速选择账号
	 */
	private void doSelectAccount() {

		List<CarLengthInfo> mDataList = new ArrayList<CarLengthInfo>();

		int size = allAccountInfo.size();
		if (size == 0) {
			ToastUtil.show(context, "没有快捷账号,请设置快捷账号");
			return;
		}
		for (int i = 0; i < size; i++) {
			CarLengthInfo indexInfo = new CarLengthInfo();
			indexInfo.setCar_Length(allAccountInfo.get(i).getName()
					+ ":"
					+ CommonUtils.getBankName(allAccountInfo.get(i)
							.getAccType()));
			mDataList.add(indexInfo);
		}

		final SingleSelectAlertDlialog ad = new SingleSelectAlertDlialog(this);
		ad.setTitle("账号信息");
		ad.setListContentForCarLength(mDataList);
		ad.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View view,
					int position, long id) {
				if (null == view)
					return;
				ad.dismiss();
				Message message = myHandler.obtainMessage();
				message.what = REFRESH_SPEED_SELECT;
				message.obj = allAccountInfo.get((int) id);
				myHandler.sendMessage(message);

			}
		});
	}

	private void showOptionDialog(final int photoCode, final int cameraCode) {
		final SelectPicPopupWindow dialog = new SelectPicPopupWindow(
				PayDetailActivity.this);
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
				CommonUtils
						.selectSystemPhone(photoCode, PayDetailActivity.this);
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
		dialog.showAtLocation(PayDetailActivity.this.findViewById(R.id.main),
				Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0); // 设置layout在PopupWindow中显示的位置
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
			voucher_photo.setImageBitmap(resultBitmap);
			photoFlag = "Y";
			break;
		case REQUEST_CODE_PICK:
			resultBitmap = BitmapFactory
					.decodeFile(ConstantPool.DEFAULT_ICON_PATH
							+ "image_diy_resultphoto.jpg");
			voucher_photo.setImageBitmap(resultBitmap);
			photoFlag = "Y";
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
		case NetWork.RECHARGE_OK:
			doRechargeSuccess(data);
			break;
		case NetWork.GET_ACCOUNT_INFO_OK:
			doGetAcountInfoSuccess(data);
			break;
		case NetWork.GET_ACCOUNT_INFO_ERROR:
		case NetWork.RECHARGE_ERROR:
			Message msg = myHandler.obtainMessage();
			msg.what = SHOW_TOAST;
			msg.obj = getResources().getString(R.string.network_error_hint);
			myHandler.sendMessage(msg);
			break;
		}
	}

	/**
	 * 获取账号信息成功
	 * 
	 * @param data
	 */
	private void doGetAcountInfoSuccess(Object data) {
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
				doRefreshList(response);
				return;
			}
		} catch (Exception e) {
			e.printStackTrace();
			message = getResources().getString(R.string.network_error_hint);
		}
		Message msg = myHandler.obtainMessage();
		msg.what = SHOW_TOAST;
		msg.obj = message;
		myHandler.sendMessage(msg);
	}

	/**
	 * 刷新列表
	 * 
	 * @param data
	 */
	private void doRefreshList(PdaResponse<List<AccountDto>> response) {
		allAccountInfo = response.getData();
		if (null == allAccountInfo || allAccountInfo.size() == 0)
			return;
		for (AccountDto accountInfo : allAccountInfo) {
			dbOperate.updateAccount(accountInfo);
		}
	}

	/**
	 * 充值成功
	 */
	private void doRechargeSuccess(Object data) {
		String dataString = null;
		try {
			dataString = new String((byte[]) data, "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
		String message = "";
		try {
			PdaResponse<String> response = ResultCodeJsonParser
					.parserResultCodeJson(dataString);
			if (!response.isSuccess()) {
				String result = response.getMessage();
				int messageCode = Integer.parseInt(result.substring(0,
						result.indexOf("#")));
				message = result.substring(result.indexOf("#") + 1,
						result.length());
			} else {
				Intent intent = new Intent();
				intent.putExtra("rechargeMoney", pay_detail_money.getText()
						.toString());
				setResult(RESULT_OK, intent);
				finish();
				return;
			}
		} catch (Exception e) {
			e.printStackTrace();
			message = "充值失败,请重新充值";
		}
		Message msg = myHandler.obtainMessage();
		msg.what = SHOW_TOAST;
		msg.obj = message;
		myHandler.sendMessage(msg);
	}

	abstract class Filter {

		Filter next = null;

		public Filter getNext() {

			return next;

		}

		public void setNext(Filter next) {

			this.next = next;

		}

		public String doFilter(String money, String type, String name,
				String number, String photo) {

			if (next == null) {
				return "成功";
			} else
				return next.doFilter(money, type, name, number, photo);

		}

	}

	class moneyFilder extends Filter {
		@Override
		public String doFilter(String money, String type, String name,
				String number, String photo) {

			if (TextUtils.isEmpty(money)) {
				return "请输入正确的充值金额";
			} else {
				return super.doFilter(money, type, name, number, photo);
			}
		}
	}

	class typeFilder extends Filter {
		@Override
		public String doFilter(String money, String type, String name,
				String number, String photo) {

			if (TextUtils.isEmpty(type)) {
				return "请选择正确的账户类型";
			} else {
				return super.doFilter(money, type, name, number, photo);
			}
		}
	}

	class nameFilder extends Filter {
		@Override
		public String doFilter(String money, String type, String name,
				String number, String photo) {

			if (TextUtils.isEmpty(name)) {
				return "请输入正确的账户姓名";
			} else {
				return super.doFilter(money, type, name, number, photo);
			}
		}
	}

	class numberFilder extends Filter {
		@Override
		public String doFilter(String money, String type, String name,
				String number, String photo) {

			if (TextUtils.isEmpty(number)) {
				return "请输入正确的账户号码";
			} else {
				return super.doFilter(money, type, name, number, photo);
			}
		}
	}

	class photoFilder extends Filter {
		@Override
		public String doFilter(String money, String type, String name,
				String number, String photo) {

			if (TextUtils.isEmpty(photo)) {
				return "请选择正确的凭证";
			} else {
				return super.doFilter(money, type, name, number, photo);
			}
		}
	}
}
