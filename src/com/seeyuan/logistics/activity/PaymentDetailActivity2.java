package com.seeyuan.logistics.activity;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
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
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.baidu.location.f;
import com.mesada.nj.pubcontrols.controls.RemoteImageView;
import com.seeyuan.logistics.R;
import com.seeyuan.logistics.alipay.PayResult;
import com.seeyuan.logistics.alipay.PaySetting;
import com.seeyuan.logistics.alipay.SignUtils;
import com.seeyuan.logistics.application.ConstantPool;
import com.seeyuan.logistics.application.NetWork;
import com.seeyuan.logistics.customview.MuInputEditText;
import com.seeyuan.logistics.customview.ProgressAlertDialog;
import com.seeyuan.logistics.customview.SelectPicPopupWindow;
import com.seeyuan.logistics.customview.SingleSelectAlertDlialog;
import com.seeyuan.logistics.datacenter.DataHandler;
import com.seeyuan.logistics.datacenter.OnDataReceiveListener;
import com.seeyuan.logistics.datahandler.AccountInfoHandler;
import com.seeyuan.logistics.datahandler.GetRechargeAccountHandler;
import com.seeyuan.logistics.datahandler.SubmitPaymentHandler;
import com.seeyuan.logistics.entity.AccountDto;
import com.seeyuan.logistics.entity.AccountInDto;
import com.seeyuan.logistics.entity.CarLengthInfo;
import com.seeyuan.logistics.entity.ImageDto;
import com.seeyuan.logistics.entity.MemAccountDto;
import com.seeyuan.logistics.entity.OrderDto;
import com.seeyuan.logistics.entity.PaymentDto;
import com.seeyuan.logistics.entity.PdaPagination;
import com.seeyuan.logistics.entity.PdaRequest;
import com.seeyuan.logistics.entity.PdaResponse;
import com.seeyuan.logistics.jsonparser.AccountInfoJsonParser;
import com.seeyuan.logistics.jsonparser.RechargeAccountJsonParser;
import com.seeyuan.logistics.provider.DBOperate;
import com.seeyuan.logistics.util.CommonUtils;
import com.seeyuan.logistics.util.ToastUtil;

/**
 * 订单-支付详情
 * 
 * @author zhazhaobao
 * 
 */
@SuppressLint("NewApi")
public class PaymentDetailActivity2 extends BaseActivity implements
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

	/**
	 * 余额
	 */
	private TextView pay_detail_balance;

	/**
	 * 是否使用余额
	 */
	private CheckBox payment_detail_balance_ck;

	/**
	 * 充值金额
	 */
	private MuInputEditText pay_detail_money;

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

	private MemAccountDto memAccountDto;

	private PaymentDto paymentDto = new PaymentDto();

	private String payMoney;

	private OrderDto orderDto;

	private AccountDto accountDto;
	
	private String filePath;
	private Bitmap resultBitmap;

	private LinearLayout user_balance;
	/**
	 * 屏幕款
	 */
	private int screenWidth;
	
	private static final int SDK_PAY_FLAG = 1;

	private static final int SDK_CHECK_FLAG = 2;
	
	@Override 
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.activity_payment_detail2); // 软件activity的布局
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,
				R.layout.deflaut_titlebar); // titlebar为自己标题栏的布局
		context = getApplicationContext();
		dbOperate = DBOperate.getInstance(context);
		payMoney = getIntent().getStringExtra("payMoney");
		orderDto = (OrderDto) getIntent().getSerializableExtra("orderInfo");
		initView();
		getAccountInfo();
		getRechargeAccount();
	}

	@Override
	protected void onStart() {
		super.onStart();
		allAccountInfo = dbOperate.getAllAccount();
	}

	@Override
	public void initView() {
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		screenWidth = dm.widthPixels;
		maintitle_back_iv = (ImageView) findViewById(R.id.maintitle_back_iv);
		maintitle_back_iv.setOnClickListener(this);

		defaulttitle_title_tv = (TextView) findViewById(R.id.defaulttitle_title_tv);
		defaulttitle_title_tv.setText("付款详情");

		pay_detail_balance = (TextView) findViewById(R.id.pay_detail_balance);

		payment_detail_balance_ck = (CheckBox) findViewById(R.id.payment_detail_balance_ck);
		payment_detail_balance_ck.setChecked(true);
//		payment_detail_balance_ck.setOnClickListener(this);
		
		
		pay_detail_money = (MuInputEditText) findViewById(R.id.pay_detail_money);
		pay_detail_money.setText(payMoney);
		
		user_balance = (LinearLayout) findViewById(R.id.user_balance);
		payment_detail_balance_ck.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
				if (isChecked) {
					little(user_balance);
//					user_balance.setVisibility(View.GONE);
				} else {
//					user_balance.setVisibility(View.VISIBLE);
					big(user_balance);
				}
			}
		});
		pay_detail_account = (TextView) findViewById(R.id.pay_detail_account);
		pay_detail_account.setOnClickListener(this);

		pay_detail_type = (TextView) findViewById(R.id.pay_detail_type);
		pay_detail_type.setOnClickListener(this);

		pay_detail_name = (TextView) findViewById(R.id.pay_detail_name);

		pay_detail_number = (TextView) findViewById(R.id.pay_detail_number);

		voucher_photo = (RemoteImageView) findViewById(R.id.voucher_photo);
		voucher_photo.setOnClickListener(this);
	}

	private void little(final LinearLayout layout){
		ObjectAnimator animator = ObjectAnimator.ofFloat(layout, "scaleY", 1f, 0f);
		animator.setDuration(3000);
		animator.addListener(new AnimatorListenerAdapter() {
			@Override
			public void onAnimationEnd(Animator animation) {
				// TODO Auto-generated method stub
				super.onAnimationEnd(animation);
				layout.setVisibility(View.GONE);
			}
		});
		animator.start();
	}
	private void big(final LinearLayout layout){
		ObjectAnimator animator = ObjectAnimator.ofFloat(layout, "scaleY", 0f, 1f);
		animator.setDuration(3000);
		animator.addListener(new AnimatorListenerAdapter() {
			@Override
			public void onAnimationStart(Animator animation) {
				// TODO Auto-generated method stub
				super.onAnimationStart(animation);
				layout.setVisibility(View.VISIBLE);
			}
		});
		animator.start();
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
				dismissProgress();
				ToastUtil.show(context, msg.obj.toString());
				break;
			case SDK_PAY_FLAG: {
				PayResult payResult = new PayResult((String) msg.obj);

				// 支付宝返回此次支付结果及加签，建议对支付宝签名信息拿签约时支付宝提供的公钥做验签
				String resultInfo = payResult.getResult();

				String resultStatus = payResult.getResultStatus();

				// 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
				if (TextUtils.equals(resultStatus, "9000")) {
					Toast.makeText(PaymentDetailActivity2.this, "支付成功", Toast.LENGTH_SHORT).show();
					// startActivity(new Intent(PayAlipay.this, MyOrder.class));
					finish();
					if ( !PaymentCalculateActivity.instance.isFinishing()) {
						PaymentCalculateActivity.instance.finish();
					}
				} else {
					// 判断resultStatus 为非“9000”则代表可能支付失败
					// “8000”代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
					if (TextUtils.equals(resultStatus, "8000")) {
						Toast.makeText(PaymentDetailActivity2.this, "支付结果确认中", Toast.LENGTH_SHORT).show();

					} else {
						// 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
						Log.i("支付结果", resultStatus + "," + resultInfo);
						Toast.makeText(PaymentDetailActivity2.this, "支付失败", Toast.LENGTH_SHORT).show();

					}
				}
				break;
			}
			case SDK_CHECK_FLAG: {
				Toast.makeText(PaymentDetailActivity2.this, "检查结果为：" + msg.obj, Toast.LENGTH_SHORT).show();
				break;
			}
			default:
				break;
			}
		}
	};

	

	
	/**
	 * call alipay sdk pay. 调用SDK支付
	 * 
	 */
	public void pay() {
		// 订单
		String orderInfo = getOrderInfo("货物运费", "使用支付宝付款", payMoney, orderDto.getOrderNo() + "_" + CommonUtils.getUUID(context));

		// 对订单做RSA 签名
		String sign = sign(orderInfo);
		try {
			// 仅需对sign 做URL编码
			Log.i("sign", sign);
			sign = URLEncoder.encode(sign, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		// 完整的符合支付宝参数规范的订单信息
		final String payInfo = orderInfo + "&sign=\"" + sign + "\"&" + getSignType();
		Log.i("完整的支付信息：", payInfo);
		Runnable payRunnable = new Runnable() {

			@Override
			public void run() {
				// 构造PayTask 对象
				PayTask alipay = new PayTask(PaymentDetailActivity2.this);
				// 调用支付接口，获取支付结果
				String result = alipay.pay(payInfo);

				Message msg = new Message();
				msg.what = SDK_PAY_FLAG;
				msg.obj = result;
				myHandler.sendMessage(msg);
			}
		};

		// 必须异步调用
		Thread payThread = new Thread(payRunnable);
		payThread.start();
	}
	
	/**
	 * create the order info. 创建订单信息
	 * @param subject  商品名称
	 * @param body
	 * @param price   商品价格
	 * @param orderSn  商品订单
	 * @return
	 */
	public String getOrderInfo(String subject, String body, String price, String orderSn) {
		// 签约合作者身份ID
		String orderInfo = "partner=" + "\"" + PaySetting.PARTNER + "\"";

		// 签约卖家支付宝账号
		orderInfo += "&seller_id=" + "\"" + PaySetting.SELLER + "\"";

		// 商户网站唯一订单号
		orderInfo += "&out_trade_no=" + "\"" + orderSn + "\"";

		// 商品名称
		orderInfo += "&subject=" + "\"" + subject + "\"";

		// 商品详情
		orderInfo += "&body=" + "\"" + body + "\"";

		// 商品金额
		orderInfo += "&total_fee=" + "\"" + price + "\"";

		// 服务器异步通知页面路径
		orderInfo += "&notify_url=" + "\"" + NetWork.RETURN_URL + "payment/payReturn_alipayAjaxReturnOrder.action" + "\"";

		// 服务接口名称， 固定值
		orderInfo += "&service=\"mobile.securitypay.pay\"";

		// 支付类型， 固定值
		orderInfo += "&payment_type=\"1\"";

		// 参数编码， 固定值
		orderInfo += "&_input_charset=\"utf-8\"";

		// 设置未付款交易的超时时间
		// 默认30分钟，一旦超时，该笔交易就会自动被关闭。
		// 取值范围：1m～15d。
		// m-分钟，h-小时，d-天，1c-当天（无论交易何时创建，都在0点关闭）。
		// 该参数数值不接受小数点，如1.5h，可转换为90m。
		orderInfo += "&it_b_pay=\"30m\"";

		// extern_token为经过快登授权获取到的alipay_open_id,带上此参数用户将使用授权的账户进行支付
		// orderInfo += "&extern_token=" + "\"" + extern_token + "\"";

		// 支付宝处理完请求后，当前页面跳转到商户指定页面的路径，可空
		orderInfo += "&return_url=\"m.alipay.com\"";

		// 调用银行卡支付，需配置此参数，参与签名， 固定值 （需要签约《无线银行卡快捷支付》才能使用）
		// orderInfo += "&paymethod=\"expressGateway\"";

		return orderInfo;
	}
	
	/**
	 * sign the order info. 对订单信息进行签名
	 * 
	 * @param content
	 *            待签名订单信息
	 */
	public String sign(String content) {
		return SignUtils.sign(content, PaySetting.RSA_PRIVATE);
	}

	/**
	 * get the sign type we use. 获取签名方式
	 * 
	 */
	public String getSignType() {
		return "sign_type=\"RSA\"";
	}
	
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
	public void onClickListener(View view) {
		switch (view.getId()) {
		case R.id.bt_save:
			myHandler.sendEmptyMessage(SHOW_PROGRESS);
			new Thread(new Runnable() {
				@Override
				public void run() {
					String result = isCanRecharge();
					if (result.equalsIgnoreCase("成功")) {
						
						Log.i("类型", pay_detail_type.getText().toString());
						if ( ! payment_detail_balance_ck.isChecked() && pay_detail_type.getText().toString().trim().equals("支付宝") ) {//调用支付宝
							dismissProgress();
							pay();
							
						} else {
							
							doSubmitPayment();
						}
					} else {
						Message msg = myHandler.obtainMessage();
						msg.what = SHOW_TOAST;
						msg.obj = result;
						myHandler.sendMessage(msg);
					}
				}
			}).start();
			break;

		default:
			break;
		}
	}

	/**
	 * 提交 付款
	 */
	private void doSubmitPayment() {
		PdaRequest<PaymentDto> request = new PdaRequest<PaymentDto>();
		paymentDto.setInAmount(BigDecimal.valueOf(Double
				.valueOf(pay_detail_money.getText().toString())));
		paymentDto.setPayAccount(BigDecimal.valueOf(Double.valueOf(payMoney)));
		paymentDto.setAccBalence(payment_detail_balance_ck.isChecked() ? true
				: false);
//		if ( ! payment_detail_balance_ck.isChecked()) {
			
			String accType = CommonUtils.getBankID(pay_detail_type.getText()
					.toString());
			String name = pay_detail_name.getText().toString();
			String accountNum = pay_detail_number.getText().toString();
			if (null == accountDto
					|| !accountDto.getAccType().equalsIgnoreCase(accType)
					|| !accountDto.getName().equalsIgnoreCase(name)
					|| !accountDto.getAccountNum().equalsIgnoreCase(accountNum))
				accountDto = new AccountDto();
			accountDto.setAccType(accType);
			accountDto.setName(name);
			accountDto.setAccountNum(accountNum);
			paymentDto.setAccountDto(accountDto);
//		} 

		paymentDto.setVoucher(voucher_photo_dto);
		paymentDto.setOrderId(orderDto.getOrderId());

		request.setData(paymentDto);
		Log.i("订单ID", orderDto.getOrderId());
		SubmitPaymentHandler dataHandler = new SubmitPaymentHandler(context,
				request);
		dataHandler.setOnDataReceiveListener(PaymentDetailActivity2.this);
		dataHandler.startNetWork();
	}

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
		case R.id.payment_detail_balance_ck:
			if (payment_detail_balance_ck.isChecked()) {
				findViewById(R.id.user_balance).setVisibility(View.GONE);
			} else {
				findViewById(R.id.user_balance).setVisibility(View.VISIBLE);
			}
			break;
		default:
			break;
		}
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
		String result = null ;
//		if (payment_detail_balance_ck.isChecked()) {
//			if (TextUtils.isEmpty(pay_detail_money.getText().toString())) {
//				Toast.makeText(context, "请输入正确的充值金额", Toast.LENGTH_LONG).show();
//			} else {
//				result = "成功";
//			}
//		} else {
			
			result = money.doFilter(pay_detail_money.getText().toString(),
					pay_detail_type.getText().toString(), pay_detail_name.getText()
					.toString(), pay_detail_number.getText().toString(),
					photoFlag);
//		}

		return result;
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
				PaymentDetailActivity2.this);
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
						PaymentDetailActivity2.this);
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
				PaymentDetailActivity2.this.findViewById(R.id.main),
				Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0); // 设置layout在PopupWindow中显示的位置
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
			voucher_photo_dto.setImageSuffix("PNG");
			voucher_photo_dto
					.setFile(CommonUtils.getBitmapByByte(resultBitmap));
			break;
		case REQUEST_CODE_PICK:
			resultBitmap = BitmapFactory
					.decodeFile(ConstantPool.DEFAULT_ICON_PATH
							+ "image_diy_resultphoto.jpg");
			voucher_photo.setImageBitmap(resultBitmap);
			photoFlag = "Y";
			voucher_photo_dto.setImageSuffix("PNG");
			voucher_photo_dto
					.setFile(CommonUtils.getBitmapByByte(resultBitmap));
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

	/**
	 * 获取充值账号信息(余额)
	 */
	private void getRechargeAccount() {
		myHandler.sendEmptyMessage(SHOW_PROGRESS);
		PdaRequest<String> request = new PdaRequest<String>();
		request.setData("");
		GetRechargeAccountHandler dataHandler = new GetRechargeAccountHandler(
				context, request);
		dataHandler.setOnDataReceiveListener(this);
		dataHandler.startNetWork();
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
	public void onDataReceive(DataHandler dataHandler, int resultCode,
			Object data, int type) {
		myHandler.sendEmptyMessage(CLOSE_PROGRESS);
		try {
			String dataString = new String((byte[]) data, "UTF-8");
			Log.i("支付结果", dataString);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		switch (resultCode) {
		case NetWork.GET_ACCOUNT_INFO_OK:
			doGetAcountInfoSuccess(data);
			break;
		case NetWork.SUBMIT_PAYMENT_OK:
			doSubmitPaymentSuccess(data);
			break;
		case NetWork.GET_RECHARGE_ACCOUNT_OK:
			doGetRechargeAccountSuccess(data);
			break;
		case NetWork.GET_RECHARGE_ACCOUNT_ERROR:
		case NetWork.GET_ACCOUNT_INFO_ERROR:
		case NetWork.SUBMIT_PAYMENT_ERROR:
			doNetworkError();
			break;

		default:
			break;
		}
	}

	/**
	 * 获取充值账号信息成功
	 * 
	 * @param data
	 */
	private void doGetRechargeAccountSuccess(Object data) {
		String dataString = null;
		try {
			dataString = new String((byte[]) data, "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
		String message = "";
		try {
			PdaResponse<MemAccountDto> response = RechargeAccountJsonParser
					.parserRechargeAccountJson(dataString);
			if (!response.isSuccess()) {
				String result = response.getMessage();
				int messageCode = Integer.parseInt(result.substring(0,
						result.indexOf("#")));
				message = result.substring(result.indexOf("#") + 1,
						result.length());
			} else {
				memAccountDto = response.getData();
				showRechargeView(memAccountDto);
				return;
			}
		} catch (Exception e) {
			e.printStackTrace();
			message = "获取账号信息失败,请重新获取";
		}
		Message msg = myHandler.obtainMessage();
		msg.what = SHOW_TOAST;
		msg.obj = message;
		myHandler.sendMessage(msg);
	}

	/**
	 * 刷新充值账号信息
	 */
	private void showRechargeView(MemAccountDto memAccountDto) {
		if (null == memAccountDto)
			return;
		pay_detail_balance.setText(null == memAccountDto.getBalance() ? ""
				: memAccountDto.getBalance().toString());
		double price = Double.parseDouble(payMoney.toString());
		double yuE = Double.parseDouble(null == memAccountDto.getBalance() ? ""
				: memAccountDto.getBalance().toString());
		if (price <= yuE) {
			user_balance.setVisibility(View.GONE);
		} else {
			user_balance.setVisibility(View.VISIBLE);
		}
		
	}

	/**
	 * 支付成功
	 * 
	 * @param data
	 */
	private void doSubmitPaymentSuccess(Object data) {
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
//				message = result.substring(result.indexOf("#") + 1,
//						result.length());
//				Toast.makeText(context, message, Toast.LENGTH_LONG).show();
				Intent intent = new Intent(PaymentDetailActivity2.this,
						MyOrderManagerActivity.class);
				intent.putExtra("isNomalGetIn", false);
				startActivity(intent);
				finish();
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
	 * 异常
	 */
	private void doNetworkError() {
		Message msg = myHandler.obtainMessage();
		msg.what = SHOW_TOAST;
		msg.obj = getResources().getString(R.string.network_error_hint);
		myHandler.sendMessage(msg);
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
	 * 刷新列表，更多资源
	 * 
	 * @param response
	 */
	private void doRefreshListMore(PdaResponse<List<AccountDto>> response) {
		if (null == response || response.getData().size() == 0)
			return;
		for (AccountDto accountDto : response.getData()) {
			allAccountInfo.add(accountDto);
			dbOperate.updateAccount(accountDto);
		}
	}

}
