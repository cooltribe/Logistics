package com.seeyuan.logistics.activity;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
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
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.seeyuan.logistics.R;
import com.seeyuan.logistics.adapter.OrderInfoAdapter;
import com.seeyuan.logistics.application.ConstantPool;
import com.seeyuan.logistics.application.NetWork;
import com.seeyuan.logistics.customview.EditOrderPriceAlertDlialog;
import com.seeyuan.logistics.customview.ImageAlertDlialog;
import com.seeyuan.logistics.customview.NormalTextAlertDlialog;
import com.seeyuan.logistics.customview.ProgressAlertDialog;
import com.seeyuan.logistics.customview.SelectPicPopupWindow;
import com.seeyuan.logistics.datacenter.DataHandler;
import com.seeyuan.logistics.datacenter.OnDataReceiveListener;
import com.seeyuan.logistics.datahandler.ChangeOrderStatusHandler;
import com.seeyuan.logistics.datahandler.EditOrderPriceHandler;
import com.seeyuan.logistics.datahandler.GetOrderInfoHandler;
import com.seeyuan.logistics.entity.ImageDto;
import com.seeyuan.logistics.entity.OrderDto;
import com.seeyuan.logistics.entity.PdaPagination;
import com.seeyuan.logistics.entity.PdaRequest;
import com.seeyuan.logistics.entity.PdaResponse;
import com.seeyuan.logistics.jsonparser.OrderInfoJsonParser;
import com.seeyuan.logistics.jsonparser.ResultCodeJsonParser;
import com.seeyuan.logistics.pullrefreshview.PullToRefreshBase;
import com.seeyuan.logistics.pullrefreshview.PullToRefreshBase.OnRefreshListener;
import com.seeyuan.logistics.pullrefreshview.PullToRefreshListView;
import com.seeyuan.logistics.util.CommonUtils;
import com.seeyuan.logistics.util.ToastUtil;

/**
 * 全部订单,仅供订单系统用户接入
 * 
 * @author zhazhaobao
 * 
 */
public class CompleteOrderForOrderSystemActivity extends BaseActivity implements
		OnDataReceiveListener {

	private PullToRefreshListView all_order_listview;

	private ListView mListView;

	private OrderInfoAdapter mAdapter;

	private List<OrderDto> mDataList;

	private Context context;

	/**
	 * 需求每页元素个数
	 */
	private final int pageSize = 5;
	/**
	 * 页数
	 */
	private int pageNum = 0;

	/**
	 * 一共多少页
	 */
	private int totalPage = 0;

	/**
	 * 是否获取更多数据
	 */
	private boolean isGetMoreData = false;

	private final int GET_ORDER_INFO_SUCCESS = 1000;
	private final int GET_ORDER_INFO_ERROR = 1001;

	private EditOrderPriceAlertDlialog dialog;

	/**
	 * 显示进度条
	 */
	private final int SHOW_PROGRESS = 2000;
	/**
	 * 关闭进度条
	 */
	private final int CLOSE_PROGRESS = 2001;

	private ProgressAlertDialog progressDialog;

	private NormalTextAlertDlialog textDialog;

	private final int SHOW_TOAST = 3001;

	private String filePath;

	private Bitmap resultBitmap;

	private final int REQUEST_CODE_PHOTOALBUM = 4000;

	private final int REQUEST_CODE_PHOTOOK = 4001;

	private final int REQUEST_CODE_PHOTO = 4002;

	private final int REQUEST_CODE_PICK = 4003;

	private OrderDto currentSelectedOder;

	private ImageDto oderImageDto = new ImageDto();

	/**
	 * 判断是否签收，用于区别签收和发货状态对应的imageDto
	 */
	private boolean isSignoff = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_all_order);
		context = getApplicationContext();
		initView();
		initAdapter();
		new Thread(new Runnable() {

			@Override
			public void run() {
				getOrderInfo(pageNum);
			}
		}).start();
	}

	@Override
	public void initView() {
		all_order_listview = (PullToRefreshListView) findViewById(R.id.all_order_listview);
		all_order_listview.setOnRefreshListener(mOnrefreshListener);
		mListView = all_order_listview.getRefreshableView();
	}

	private void initAdapter() {
		mDataList = new ArrayList<OrderDto>();
		mAdapter = new OrderInfoAdapter(context, mDataList, myHandler);
		mListView.setAdapter(mAdapter);
		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent intent = new Intent(CompleteOrderForOrderSystemActivity.this,
						CheckOrderDetailActivity.class);
				intent.putExtra("orderInfo", mDataList.get((int) id));
				startActivity(intent);
			}
		});
	}

	private Handler myHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case GET_ORDER_INFO_SUCCESS:
				mAdapter.notifyDataSetChanged();
				all_order_listview.onRefreshComplete();
				break;
			case GET_ORDER_INFO_ERROR:
				ToastUtil.show(context, msg.obj.toString());
				all_order_listview.onRefreshComplete();
				break;
			case R.id.order_btn_left:
				// 只针对订单，确认完成
				currentSelectedOder = (OrderDto) msg.obj;
				doOrderComplete(currentSelectedOder);
				break;
			case R.id.order_btn_right:
				// 根据不同状态处理不同事件
				currentSelectedOder = (OrderDto) msg.obj;
				doOrderOptionStatus(currentSelectedOder);
				break;
			case R.id.order_btn_mid:
				currentSelectedOder = (OrderDto) msg.obj;
				doOrderTracking(currentSelectedOder);
				break;
			case R.id.order_goodslist:
				// 显示货源清单
				OrderDto goodsDto = (OrderDto) msg.obj;
				String goodsPath = null == goodsDto.getGoodsList() ? ""
						: goodsDto.getGoodsList().getHeaderImgURL();
				doShowGoodsList(goodsPath);
				break;
			case R.id.order_peceipt:
				// 显示回单
				OrderDto peceiptDto = (OrderDto) msg.obj;
				String peceiptPath = null == peceiptDto.getPeceipt() ? ""
						: peceiptDto.getPeceipt().getHeaderImgURL();
				doShowPeceipt(peceiptPath);
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

	/**
	 * 显示回单
	 */
	protected void doShowPeceipt(String imagePath) {
		ImageAlertDlialog dialog = new ImageAlertDlialog(this, imagePath, "回单");
		dialog.show();
	}

	/**
	 * 显示货源清单
	 */
	protected void doShowGoodsList(String imagePath) {
		ImageAlertDlialog dialog = new ImageAlertDlialog(this, imagePath,
				"货物清单");
		dialog.show();
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

	@Override
	public void onClickListener(View view) {

	}

	protected void doOrderOptionStatus(OrderDto order) {
		int memberType = Integer.parseInt(CommonUtils.getMemberType(context));
		switch (memberType) {
		case 2:// 个人车主
			doDriverStatus(order);
			break;
		case 3:// 物流企业
		case 1:// 货主
			doGoodsStatus(order);
		default:
			break;
		}
	}

	private void getOrderInfo(int page) {
		PdaRequest<OrderDto> request = new PdaRequest<OrderDto>();
		OrderDto order = new OrderDto();
		order.setAllOrderStatus("complete");
		request.setData(order);
		PdaPagination pagination = new PdaPagination();
		pagination.setStartPos(page);
		pagination.setAmount(pageSize);
		request.setPagination(pagination);
		GetOrderInfoHandler dataHandler = new GetOrderInfoHandler(context,
				request);
		dataHandler.setOnDataReceiveListener(this);
		dataHandler.startNetWork();
	}

	/**
	 * 个人车主，状态显示控制
	 * 
	 * @param orderStatus
	 */
	private void doDriverStatus(OrderDto orderDto) {
		int orderStatus = Integer.parseInt(orderDto.getOrderStatus());
		switch (orderStatus) {
		case 0:
			// result = "报价";
			doEditOrderPrice(orderDto);
			break;
		case 1:
			// result = "改价";
			doEditOrderPrice(orderDto);
			break;
		case 3:
			// result = "发货";
			isSignoff = false;
			// doSendOut(orderDto);
			showTextDialog("上传货物清单,确认发货");
			break;
		case 4:
		case 5:
		case 6:
			// result = "签收";
			// doSignOff(orderDto);
			isSignoff = true;
			showTextDialog("上传回单,确认签收");
			break;
		case 7:
		case 8:
		case 2:
		case 9:
			// result = "查看订单";
			doCheckOrderDetail(orderDto);
			break;
		default:
			break;
		}
	}

	private void showTextDialog(String content) {
		textDialog = new NormalTextAlertDlialog(this);
		textDialog.setContent(content);
		textDialog.setLeftButtonListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				showOptionDialog(REQUEST_CODE_PHOTOALBUM, REQUEST_CODE_PHOTO);
				textDialog.dismiss();
			}
		});
		textDialog.setRightButtonListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				textDialog.dismiss();
			}
		});
	}

	/**
	 * 企业，货主，状态显示控制
	 * 
	 * @param orderStatus
	 */
	private void doGoodsStatus(OrderDto orderDto) {
		int orderStatus = Integer.parseInt(orderDto.getOrderStatus());
		int payStatus = Integer.parseInt(orderDto.getPayStatus());
		switch (orderStatus) {
		case 0:
			// result = "报价";
			doEditOrderPrice(orderDto);
			break;
		case 2:
			// result = "改价";
			doEditOrderPrice(orderDto);
			break;
		case 3:
			// result = "去付款";
			doPayStatus(orderDto, payStatus);
			break;
		case 4:
		case 5:
		case 6:
			// result = "订单跟踪";
			// doOrderTracking(orderDto);
			// result = "查看订单";
			doCheckOrderDetail(orderDto);
			break;
		case 7:
			// result = "回单确认";
			doReceiptComplete(orderDto);
			break;
		case 8:
			// result = "查看订单";
			doCheckOrderDetail(orderDto);
			break;
		case 1:
		case 9:
			// result = "查看订单";
			doCheckOrderDetail(orderDto);
			break;
		default:
			break;
		}
	}

	/**
	 * 换算订单付款状态
	 * 
	 * @param payStatus
	 * @return
	 */
	private void doPayStatus(OrderDto orderDto, int payStatus) {
		// 0 未付款
		// 1 部分付款
		// 2 已付款
		// -1 申请退款
		// -2 已部分退款
		// -3 已退款
		switch (payStatus) {
		case 0:
		case 1:
			// result = "去付款";
			doPayment(orderDto);
			break;
		case 2:
			// result = "查看订单";
			doCheckOrderDetail(orderDto);
			break;
		case -1:
		case -2:
			// result = "申请退款";
			doRefund();
			break;
		case -3:
			// result = "查看订单";
			doCheckOrderDetail(orderDto);
			break;

		default:
			break;
		}
	}

	/**
	 * 退款
	 */
	private void doRefund() {
		ToastUtil.show(context, "敬请期待退款功能上线");
	}

	/**
	 * 确认订单完成
	 * 
	 * @param order
	 */
	protected void doOrderComplete(OrderDto orderDto) {
		myHandler.sendEmptyMessage(SHOW_PROGRESS);
		// 确认订单生成，价格确认，修改订单状态
		PdaRequest<OrderDto> request = new PdaRequest<OrderDto>();
		orderDto.setCommand("ConfirmationOrder");
		request.setData(orderDto);
		ChangeOrderStatusHandler dataHandler = new ChangeOrderStatusHandler(
				context, request);
		dataHandler.setOnDataReceiveListener(this);
		dataHandler.startNetWork();
	}

	/**
	 * 回单确认
	 */
	private void doReceiptComplete(OrderDto orderDto) {
		myHandler.sendEmptyMessage(SHOW_PROGRESS);
		// 回单确认，订单完成，修改订单最终状态
		PdaRequest<OrderDto> request = new PdaRequest<OrderDto>();
		orderDto.setCommand("ConfirmTransaction");
		request.setData(orderDto);
		ChangeOrderStatusHandler dataHandler = new ChangeOrderStatusHandler(
				context, request);
		dataHandler.setOnDataReceiveListener(this);
		dataHandler.startNetWork();

	}

	/**
	 * 订单跟踪
	 */
	private void doOrderTracking(OrderDto orderDto) {
		// 订单跟踪暂时不开发
		Intent intent = new Intent(CompleteOrderForOrderSystemActivity.this,
				OrderTrackingActivity.class);
		intent.putExtra("orderInfo", orderDto);
		startActivity(intent);
	}

	/**
	 * 查看订单详情
	 */
	private void doCheckOrderDetail(OrderDto orderDto) {
		Intent intent = new Intent(CompleteOrderForOrderSystemActivity.this,
				CheckOrderDetailActivity.class);
		intent.putExtra("orderInfo", orderDto);
		startActivity(intent);

	}

	/**
	 * 签收
	 */
	private void doSignOff(OrderDto orderDto) {

		// 签收，立刻上传回单，修改订单状态
		myHandler.sendEmptyMessage(SHOW_PROGRESS);
		PdaRequest<OrderDto> request = new PdaRequest<OrderDto>();
		orderDto.setCommand("ArrivalGoods");
		orderDto.setPeceipt(oderImageDto);
		request.setData(orderDto);
		ChangeOrderStatusHandler dataHandler = new ChangeOrderStatusHandler(
				context, request);
		dataHandler.setOnDataReceiveListener(this);
		dataHandler.startNetWork();
	}

	/**
	 * 发货
	 */
	private void doSendOut(OrderDto orderDto) {
		myHandler.sendEmptyMessage(SHOW_PROGRESS);
		// 发货确认，立刻上传货物清单，修改订单状态
		PdaRequest<OrderDto> request = new PdaRequest<OrderDto>();
		orderDto.setCommand("DeliverGoods");
		orderDto.setGoodsList(oderImageDto);
		request.setData(orderDto);
		ChangeOrderStatusHandler dataHandler = new ChangeOrderStatusHandler(
				context, request);
		dataHandler.setOnDataReceiveListener(this);
		dataHandler.startNetWork();
	}

	/**
	 * 修改订单价格
	 */
	private void doEditOrderPrice(final OrderDto orderDto) {
		dialog = new EditOrderPriceAlertDlialog(this);
		dialog.setTitle("订单价格");
		dialog.setEditContent(orderDto.getOrderAmount().toString());
		dialog.setEidtTextListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {

			}

			@Override
			public void afterTextChanged(Editable s) {
				orderDto.setTransAmount(TextUtils.isEmpty(dialog
						.getEditContent()) ? null : BigDecimal.valueOf(Double
						.parseDouble(dialog.getEditContent())));
			}
		});
		dialog.setLeftButtonListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				doEditOrderPriceInDialog(orderDto);
				dialog.dismiss();
			}
		});
		dialog.setRightButtonListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});

	}

	/**
	 * 去付款
	 * 
	 * @param orderDto
	 */
	private void doPayment(OrderDto orderDto) {
		Intent intent = new Intent(this, PaymentCalculateActivity.class);
		intent.putExtra("orderInfo", orderDto);
		startActivity(intent);
	}

	private void doEditOrderPriceInDialog(OrderDto orderDto) {
		myHandler.sendEmptyMessage(SHOW_PROGRESS);
		PdaRequest<OrderDto> request = new PdaRequest<OrderDto>();
		// 订单价格的修改需要UI配合添加，UI暂时未开发
		request.setData(orderDto);
		EditOrderPriceHandler dataHandler = new EditOrderPriceHandler(context,
				request);
		dataHandler.setOnDataReceiveListener(this);
		dataHandler.startNetWork();
	}

	OnRefreshListener mOnrefreshListener = new OnRefreshListener() {

		@Override
		public void onRefresh(int scrollState) {
			// TODO Auto-generated method stub
			if (scrollState == PullToRefreshBase.STATE_OF_HEADER) {
				pageNum = 0;
				isGetMoreData = false;
				getOrderInfo(pageNum);
			} else if (scrollState == PullToRefreshBase.STATE_OF_FOOTER) {
				isGetMoreData = true;
				pageNum = pageNum + pageSize;
				if (pageNum < totalPage) {
					getOrderInfo(pageNum);
				} else {
					ToastUtil.show(context, "没有更多数据");
					all_order_listview.onRefreshComplete();
				}
			}
		}
	};

	@Override
	public void onDataReceive(DataHandler dataHandler, int resultCode,
			Object data, int type) {
		myHandler.sendEmptyMessage(CLOSE_PROGRESS);
		switch (resultCode) {
		case NetWork.GET_ORDER_INFO_OK:
			doGetOrderSuccess(data);
			break;
		case NetWork.CHANGE_ORDER_STATUS_OK:
			doChangeOrderStatusSuccess(data);
			break;
		case NetWork.EDIT_ORDER_PRICE_OK:
			doEditOrderPriceSuccess(data);
			break;
		case NetWork.EDIT_ORDER_PRICE_ERROR:
		case NetWork.CHANGE_ORDER_STATUS_ERROR:
		case NetWork.GET_USERINFO_ERROR:
			all_order_listview.onRefreshComplete();
			ToastUtil.show(context,
					getResources().getString(R.string.network_error_hint));
			break;

		default:
			break;
		}

	}

	/**
	 * 改价成功
	 * 
	 * @param data
	 */
	private void doEditOrderPriceSuccess(Object data) {
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
			if (response.isSuccess()) {
				Message msg = myHandler.obtainMessage();
				msg.what = SHOW_TOAST;
				msg.obj = message;
				myHandler.sendMessage(msg);
				pageNum = 0;
				new Thread(new Runnable() {

					@Override
					public void run() {
						getOrderInfo(pageNum);
					}
				}).start();

			} else {
				// 登录失败
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
	 * 修改订单状态成功
	 * 
	 * @param data
	 */
	private void doChangeOrderStatusSuccess(Object data) {
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
			if (response.isSuccess()) {
				Message msg = myHandler.obtainMessage();
				msg.what = SHOW_TOAST;
				msg.obj = message;
				myHandler.sendMessage(msg);
				pageNum = 0;
				new Thread(new Runnable() {

					@Override
					public void run() {
						getOrderInfo(pageNum);
					}
				}).start();
			} else {
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
	 * 获取订单成功
	 * 
	 * @param data
	 */
	private void doGetOrderSuccess(Object data) {
		String dataString = null;
		try {
			dataString = new String((byte[]) data, "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			PdaResponse<List<OrderDto>> response = OrderInfoJsonParser
					.parserOrderInfoJson(dataString);
			if (response.isSuccess()) {
				// mDataList = response.getData();
				// myHandler.sendEmptyMessage(GET_ORDER_INFO_SUCCESS);

				if (null == response || null == response.getData()) {
					ToastUtil.show(context, "获取司机信息失败，请重新获取");
					return;
				}
				if (!isGetMoreData) {
					mDataList.clear();
				} else {
				}
				for (OrderDto orderDto : response.getData()) {
					mDataList.add(orderDto);
				}
				totalPage = new Long(response.getTotal()).intValue();
				mAdapter = new OrderInfoAdapter(context, mDataList, myHandler);
				mListView.setAdapter(mAdapter);
				all_order_listview.onRefreshComplete();

			} else {// 登录失败
				String result = response.getMessage();
				int messageCode = Integer.parseInt(result.substring(0,
						result.indexOf("#")));
				String message = result.substring(result.indexOf("#") + 1,
						result.length());
				Message msg = myHandler.obtainMessage();
				msg.what = GET_ORDER_INFO_ERROR;
				msg.obj = message;
				myHandler.sendMessage(msg);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void showOptionDialog(final int photoCode, final int cameraCode) {
		final SelectPicPopupWindow dialog = new SelectPicPopupWindow(
				CompleteOrderForOrderSystemActivity.this);
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
						CompleteOrderForOrderSystemActivity.this);
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
				CompleteOrderForOrderSystemActivity.this.findViewById(R.id.main),
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

			oderImageDto.setImageSuffix("PNG");
			oderImageDto.setFile(CommonUtils.getBitmapByByte(resultBitmap));
			if (isSignoff) {
				doSignOff(currentSelectedOder);
			} else {
				doSendOut(currentSelectedOder);
			}
			break;
		case REQUEST_CODE_PICK:

			resultBitmap = BitmapFactory
					.decodeFile(ConstantPool.DEFAULT_ICON_PATH
							+ "image_diy_resultphoto.jpg");

			oderImageDto.setImageSuffix("PNG");
			oderImageDto.setFile(CommonUtils.getBitmapByByte(resultBitmap));
			if (isSignoff) {
				doSignOff(currentSelectedOder);
			} else {
				doSendOut(currentSelectedOder);
			}
			break;
		}
	}
}
