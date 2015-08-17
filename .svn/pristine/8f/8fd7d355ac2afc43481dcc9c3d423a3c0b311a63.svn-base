package com.seeyuan.logistics.activity;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.DownloadManager.Request;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AnimationUtils;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.seeyuan.logistics.R;
import com.seeyuan.logistics.adapter.CarManagerAdapter;
import com.seeyuan.logistics.adapter.DriverManagerAdapter;
import com.seeyuan.logistics.application.NetWork;
import com.seeyuan.logistics.customview.ProgressAlertDialog;
import com.seeyuan.logistics.datacenter.DataHandler;
import com.seeyuan.logistics.datacenter.OnDataReceiveListener;
import com.seeyuan.logistics.datahandler.DeleteDriverInfoHandler;
import com.seeyuan.logistics.datahandler.SearchCarManagerHandler;
import com.seeyuan.logistics.entity.CarManagerInfo;
import com.seeyuan.logistics.entity.CarManagerLineInfo;
import com.seeyuan.logistics.entity.CarsDto;
import com.seeyuan.logistics.entity.DriverDto;
import com.seeyuan.logistics.entity.PdaPagination;
import com.seeyuan.logistics.entity.PdaRequest;
import com.seeyuan.logistics.entity.PdaResponse;
import com.seeyuan.logistics.entity.RouteDto;
import com.seeyuan.logistics.jsonparser.CarSourceJsonParser;
import com.seeyuan.logistics.pullrefreshview.PullToRefreshBase;
import com.seeyuan.logistics.pullrefreshview.PullToRefreshBase.OnRefreshListener;
import com.seeyuan.logistics.pullrefreshview.PullToRefreshListView;
import com.seeyuan.logistics.util.CommonUtils;
import com.seeyuan.logistics.util.ToastUtil;

/**
 * 车辆管理
 * 
 * @author zhazhaobao
 * 
 */
@SuppressLint("HandlerLeak")
public class CarManagerActivity extends BaseActivity implements
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

	private PullToRefreshListView car_manager_refresh_view;

	private ListView mListView;

	private List<CarsDto> mDataList;

	private CarManagerAdapter mAdapter;

	private Context context;

	/**
	 * 返回数据位置标示，标记列表的第几个元素
	 */
	private int position = -1;
	/**
	 * 添加新车辆
	 */
	private final int FORM_NEW_CAR_REFRESH = 100;

	/**
	 * 添加线路
	 */
	private final int FORM_NEW_LINE_REFRESH = 101;

	/**
	 * 找货
	 */
	private final int SEARCH_GOODS_REFRESH = 102;
	/**
	 * 修改线路
	 */
	private final int EDIT_LINE_REFRESH = 103;
	/**
	 * 删除线路
	 */
	private final int DELETE_LINE_REFRESH = 104;

	/**
	 * 修改线路
	 */
	private final int REEDIT_LINE_REFRESH = 105;

	/**
	 * 修改车辆信息
	 */
	private final int REEDIT_CAR_REFRESH = 106;

	private final int EDIT_LINE_CODE = 10000;

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

	private int selectCarPosition;

	/**
	 * 操作布局
	 */
	private LinearLayout car_manager_operation_layout;

	private List<Integer> selectedList = new ArrayList<Integer>();

	/**
	 * 显示进度条
	 */
	private final int SHOW_PROGRESS = 1000;
	/**
	 * 关闭进度条
	 */
	private final int CLOSE_PROGRESS = 1001;

	private ProgressAlertDialog progressDialog;

	/**
	 * 是否正常操作进入 判断界面的跳转效果
	 */
	private boolean isNomalGetIn = true;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.activity_car_manager); // 软件activity的布局
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,
				R.layout.deflaut_titlebar); // titlebar为自己标题栏的布局
		context = getApplicationContext();
		isNomalGetIn = getIntent().getBooleanExtra("isNomalGetIn", true);
		initView();
		initAdapter();
		new Thread(new Runnable() {

			@Override
			public void run() {
				doGetCarSource(pageNum);
			}
		}).start();
	}

	@Override
	public void initView() {
		maintitle_back_iv = (ImageView) findViewById(R.id.maintitle_back_iv);
		maintitle_back_iv.setOnClickListener(this);

		defaulttitle_title_tv = (TextView) findViewById(R.id.defaulttitle_title_tv);
		defaulttitle_title_tv.setText(R.string.car_manage_hint);

		// maintitle_comfirm_tv = (TextView)
		// findViewById(R.id.maintitle_comfirm_tv);
		// maintitle_comfirm_tv.setVisibility(View.VISIBLE);
		// maintitle_comfirm_tv.setText(getResources().getString(
		// R.string.edit_hint));
		// maintitle_comfirm_tv.setOnClickListener(this);

		car_manager_refresh_view = (PullToRefreshListView) findViewById(R.id.car_manager_refresh_view);
		car_manager_refresh_view.setOnRefreshListener(mRefreshListener);
		mListView = car_manager_refresh_view.getRefreshableView();

		car_manager_operation_layout = (LinearLayout) findViewById(R.id.car_manager_operation_layout);
	}

	private void initAdapter() {
		mDataList = new ArrayList<CarsDto>();

		mAdapter = new CarManagerAdapter(context, myHandler, mDataList);
		mListView.setAdapter(mAdapter);
	}

	private Handler myHandler = new Handler() {
		public void handleMessage(Message msg) {
			position = msg.arg2;
			switch (msg.what) {
			case R.id.item_car_manager_add_line:
				doAddLine(msg);
				break;
			case R.id.item_car_manager_search_goods:
				doSearchGoods(msg);
				break;
			case R.id.item_car_manager_line_edit:
				doEditLine(msg);
				break;
			case R.id.item_car_manager_line_delete:
				doDeleteLine(msg);
				break;
			case EDIT_LINE_CODE:
				doEditLine(msg);
				break;
			case R.id.item_car_manager_layout:
				doEditCar(msg);
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

	@Override
	public void onClickListener(View view) {
		switch (view.getId()) {
		case R.id.car_manager_new:
			doAddNewCar();
			break;
		case R.id.car_manager_delete:
			doCarManagerDelete();
			break;
		case R.id.car_manager_cancel:
			doCarManagerCancel();
			break;

		default:
			break;
		}
	}

	/**
	 * 找货
	 * 
	 * @param msg
	 */
	protected void doSearchGoods(Message msg) {
		Intent intent = new Intent(CarManagerActivity.this,
				SearchGoodsInfoActivity.class);
		intent.putExtra("carInfo", mDataList.get(msg.arg1));
		startActivity(intent);
	}

	/**
	 * 查看，编辑车辆
	 */
	protected void doEditCar(Message msg) {
		Intent intent = new Intent(CarManagerActivity.this,
				NewCarManagerActivity.class);
		intent.putExtra("isEditMode", true);
		intent.putExtra("carInfo", mDataList.get(msg.arg1));
		selectCarPosition = msg.arg1;
		startActivityForResult(intent, REEDIT_CAR_REFRESH);
	}

	/**
	 * 添加新线路
	 * 
	 * @param msg
	 */
	protected void doAddLine(Message msg) {
		Intent intent = new Intent(CarManagerActivity.this,
				NewLineManagerActivity.class);
		CarsDto info = mDataList.get(msg.arg1);
		intent.putExtra("lineTitle",
				getResources().getString(R.string.add_new_line_hint2));
		intent.putExtra("editModel", false);
		intent.putExtra("carManagerInfo", info);
		startActivityForResult(intent, FORM_NEW_LINE_REFRESH);
	}

	/**
	 * 删除专线
	 * 
	 * @param msg
	 */
	protected void doDeleteLine(Message msg) {
		int linePostion = msg.arg1;
		int position = msg.arg2;
		CarsDto info = mDataList.get(position);
		List<RouteDto> lineInfoList = info.getRoutes();
		lineInfoList.remove(linePostion);
		info.setRoutes(lineInfoList);
		mDataList.set(position, info);
		mAdapter = new CarManagerAdapter(context, myHandler, mDataList);
		mListView.setAdapter(mAdapter);
	}

	/**
	 * 修改线路
	 */
	protected void doEditLine(Message msg) {
		Intent intent = new Intent(CarManagerActivity.this,
				NewLineManagerActivity.class);
		CarsDto info = mDataList.get(msg.arg2);
		intent.putExtra("lineTitle",
				getResources().getString(R.string.line_hint));
		intent.putExtra("carManagerInfo", info);
		intent.putExtra("editModel", true);
		intent.putExtra("linePosition", msg.arg1);
		startActivityForResult(intent, FORM_NEW_LINE_REFRESH);
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.maintitle_back_iv:
			// CommonUtils.finishActivity(CarManagerActivity.this);
			back();
			break;
		case R.id.maintitle_comfirm_tv:
			if (car_manager_operation_layout.getVisibility() == View.VISIBLE) {
				hideOperation();
			} else {
				if (mDataList.size() == 1) {
					ToastUtil.show(context, "司机列表为空,请添加司机");
				} else {
					showOperation();
				}
			}
			break;

		default:
			break;
		}
	}

	private void back() {
		if (isNomalGetIn) {
			CommonUtils.finishActivity(this);
			return;
		}
		Intent intent = null;
		int memberType = Integer.parseInt(CommonUtils.getMemberType(context));
		switch (memberType) {
		case 2:// 个人车主
			intent = new Intent(this, PersonalInformationActivity.class);
			break;
		case 3:// 货主
		case 1:// 企业
			intent = new Intent(this, PersonalInformation2Activity.class);
			break;
		default:
			break;
		}
		startActivity(intent);
		CommonUtils.finishActivity(this);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (event.getKeyCode() == KeyEvent.KEYCODE_BACK
				&& event.getAction() == KeyEvent.ACTION_DOWN) {
			back();
			return false;
		}
		return super.onKeyDown(keyCode, event);
	}

	/**
	 * 显示菜单栏，进入编辑模式
	 */
	private void showOperation() {

		car_manager_operation_layout.setVisibility(View.VISIBLE);
		car_manager_operation_layout.setAnimation(AnimationUtils.loadAnimation(
				this, R.anim.down_in));
		// mDataList.remove(addNewDriver);
		selectedList.clear();
		int size = mDataList.size();
		for (int i = 0; i < size; i++) {
			mDataList.get(i).setEditMode(true);
		}
		mAdapter.notifyDataSetChanged();

	}

	/**
	 * 隐藏菜单栏,退出编辑模式
	 */
	private void hideOperation() {
		car_manager_operation_layout.setVisibility(View.GONE);
		car_manager_operation_layout.setAnimation(AnimationUtils.loadAnimation(
				this, R.anim.down_out));
		// mDataList.add(addNewDriver);
		int size = mDataList.size();
		for (int i = 0; i < size; i++) {
			mDataList.get(i).setEditMode(false);
		}
		mAdapter.notifyDataSetChanged();
	}

	/**
	 * 取消
	 */
	private void doCarManagerCancel() {
		hideOperation();
	}

	/**
	 * 删除车辆
	 */
	private void doCarManagerDelete() {
		selectedList = mAdapter.getSelectedList();
		selectedList = CommonUtils.BubbleSort(selectedList);
		PdaRequest<List<CarsDto>> carsInfo = new PdaRequest<List<CarsDto>>();
		List<CarsDto> list = new ArrayList<CarsDto>();
		for (int i = 0; i < selectedList.size(); i++) {
			list.add(mDataList.get(selectedList.get(i)));
		}
		carsInfo.setData(list);
		// DeleteDriverInfoHandler dataHandler = new DeleteDriverInfoHandler(
		// context, carsInfo);
		// dataHandler.setOnDataReceiveListener(this);
		// dataHandler.startNetWork();
	}

	/**
	 * 添加新车辆
	 */
	private void doAddNewCar() {
		Intent intent = new Intent(CarManagerActivity.this,
				NewCarManagerActivity.class);
		startActivityForResult(intent, FORM_NEW_CAR_REFRESH);
	}

	private OnRefreshListener mRefreshListener = new OnRefreshListener() {

		@Override
		public void onRefresh(int scrollState) {
			if (scrollState == PullToRefreshBase.STATE_OF_HEADER) {
				pageNum = 0;
				isGetMoreData = false;
				doGetCarSource(pageNum);
			} else if (scrollState == PullToRefreshBase.STATE_OF_FOOTER) {
				isGetMoreData = true;
				pageNum = pageNum + pageSize;
				if (pageNum < totalPage) {
					doGetCarSource(pageNum);
				} else {
					ToastUtil.show(context, "没有更多数据");
					car_manager_refresh_view.onRefreshComplete();
				}
			}
		}
	};

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == RESULT_OK) {
			CarsDto info = (CarsDto) data
					.getSerializableExtra("carManagerInfo");
			switch (requestCode) {
			case FORM_NEW_CAR_REFRESH:
				mDataList.add(info);
				mAdapter.notifyDataSetChanged();
				break;
			case FORM_NEW_LINE_REFRESH:
				mDataList.set(position, info);
				mAdapter = new CarManagerAdapter(context, myHandler, mDataList);
				mListView.setAdapter(mAdapter);
				break;
			case REEDIT_LINE_REFRESH:

				break;
			case REEDIT_CAR_REFRESH:
				boolean isDelete = data.getBooleanExtra("isDelete", false);
				if (isDelete) {
					mDataList.remove(selectCarPosition);
				} else {
					mDataList.set(selectCarPosition, info);
				}
				mAdapter.notifyDataSetChanged();
				break;

			default:
				break;
			}
		}
	};

	/**
	 * 搜索车辆信息
	 */
	private void doGetCarSource(int page) {
		myHandler.sendEmptyMessage(SHOW_PROGRESS);
		CarsDto carInfo = new CarsDto();
		PdaRequest<CarsDto> request = new PdaRequest<CarsDto>();
		request.setData(carInfo);
		PdaPagination pagination = new PdaPagination();
		pagination.setStartPos(page);
		pagination.setAmount(pageSize);
		request.setPagination(pagination);
		SearchCarManagerHandler dataHandler = new SearchCarManagerHandler(
				context, request);
		dataHandler.setOnDataReceiveListener(this);
		dataHandler.startNetWork();
	}

	@Override
	public void onDataReceive(DataHandler dataHandler, int resultCode,
			Object data, int type) {
		myHandler.sendEmptyMessage(CLOSE_PROGRESS);
		car_manager_refresh_view.onRefreshComplete();
		switch (resultCode) {
		case NetWork.SEARCH_CAR_OK:
			doGetCarSourceSuccss(data);
			break;
		case NetWork.SEARCH_CAR_ERROR:
			car_manager_refresh_view.onRefreshComplete();
			ToastUtil.show(context,
					getResources().getString(R.string.network_error_hint));
			break;

		default:
			break;
		}
	}

	private void doGetCarSourceSuccss(Object data) {
		String dataString = null;
		try {
			dataString = new String((byte[]) data, "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			PdaResponse<List<CarsDto>> mData = CarSourceJsonParser
					.parserSearchCarSourceJson(dataString);
			if (null == mData || null == mData.getData() || !mData.isSuccess()) {
				ToastUtil.show(context, "获取车辆信息失败，请重新获取");
				return;
			}
			// mDataList.remove(addNewDriver);
			if (!isGetMoreData) {
				mDataList.clear();
			} else {
				// mDataList.remove(addNewDriver);
			}
			for (CarsDto carsDto : mData.getData()) {
				mDataList.add(carsDto);
			}
			// mDataList.add(addNewDriver);
			totalPage = new Long(mData.getTotal()).intValue();
			mAdapter = new CarManagerAdapter(context, myHandler, mDataList);
			Log.i("size", mDataList.size() + "");
			mListView.setAdapter(mAdapter);
			// mAdapter.notifyDataSetChanged();
			car_manager_refresh_view.onRefreshComplete();
		} catch (Exception e) {
			ToastUtil.show(context, "获取车辆信息失败，请重新获取");
			e.printStackTrace();
			car_manager_refresh_view.onRefreshComplete();
		}
	}
}
