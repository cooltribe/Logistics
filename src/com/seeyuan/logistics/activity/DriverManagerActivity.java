package com.seeyuan.logistics.activity;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.seeyuan.logistics.R;
import com.seeyuan.logistics.adapter.DriverManagerAdapter;
import com.seeyuan.logistics.application.ConstantPool;
import com.seeyuan.logistics.application.NetWork;
import com.seeyuan.logistics.customview.ProgressAlertDialog;
import com.seeyuan.logistics.datacenter.DataHandler;
import com.seeyuan.logistics.datacenter.OnDataReceiveListener;
import com.seeyuan.logistics.datahandler.DeleteDriverInfoHandler;
import com.seeyuan.logistics.datahandler.GetDriverInfoHandler;
import com.seeyuan.logistics.entity.DriverDto;
import com.seeyuan.logistics.entity.PdaPagination;
import com.seeyuan.logistics.entity.PdaRequest;
import com.seeyuan.logistics.entity.PdaResponse;
import com.seeyuan.logistics.jsonparser.DriverInfoJsonParser;
import com.seeyuan.logistics.jsonparser.ResultCodeJsonParser;
import com.seeyuan.logistics.pullrefreshview.PullToRefreshBase;
import com.seeyuan.logistics.pullrefreshview.PullToRefreshBase.OnRefreshListener;
import com.seeyuan.logistics.pullrefreshview.PullToRefreshGridView;
import com.seeyuan.logistics.util.CommonUtils;
import com.seeyuan.logistics.util.ToastUtil;

/**
 * 司机管理
 * 
 * @author zhazhaobao
 * 
 */
@SuppressLint("UseValueOf")
public class DriverManagerActivity extends BaseActivity implements
		OnClickListener, OnItemClickListener, OnDataReceiveListener {
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
	 * 主GridView
	 */
	private PullToRefreshGridView dirver_pull_refresh_grid;

	private GridView mGridView;

	private DriverManagerAdapter mAdapter;

	private List<DriverDto> mDataList;

	/**
	 * 操作布局
	 */
	private LinearLayout driver_manager_operation_layout;

	private Context context;

	// 默认添加新建资料项
	private DriverDto addNewDriver;

	private List<Integer> selectedList = new ArrayList<Integer>();

	/**
	 * 添加新司机，数据返回
	 */
	private final int ADD_NEW_DRIVER_REFRESH = 1000;

	/**
	 * 编辑司机，数据返回
	 */
	private final int EDIT_DRIVER_REFRESH = 1001;

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

	private SharedPreferences sPreferences;

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

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.activity_driver_manager); // 软件activity的布局
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,
				R.layout.deflaut_titlebar); // titlebar为自己标题栏的布局
		context = getApplicationContext();
		sPreferences = getSharedPreferences(ConstantPool.LOGISTICS_PREFERENCES,
				Context.MODE_PRIVATE);
		initView();
		initAdapter();
		new Thread(new Runnable() {

			@Override
			public void run() {
				getDriverInfo(pageNum);
			}
		}).start();
	}
	
	
	@Override
	public void initView() {
		maintitle_back_iv = (ImageView) findViewById(R.id.maintitle_back_iv);
		maintitle_back_iv.setOnClickListener(this);

		defaulttitle_title_tv = (TextView) findViewById(R.id.defaulttitle_title_tv);
		defaulttitle_title_tv.setText(R.string.driver_manage_hint);

		maintitle_comfirm_tv = (TextView) findViewById(R.id.maintitle_comfirm_tv);
		maintitle_comfirm_tv.setVisibility(View.VISIBLE);
		maintitle_comfirm_tv.setText(R.string.edit_hint);
		maintitle_comfirm_tv.setOnClickListener(this);

		driver_manager_operation_layout = (LinearLayout) findViewById(R.id.driver_manager_operation_layout);

	}

	private void initAdapter() {
		dirver_pull_refresh_grid = (PullToRefreshGridView) findViewById(R.id.dirver_pull_refresh_grid);
		dirver_pull_refresh_grid.setOnRefreshListener(mOnrefreshListener);
		mGridView = dirver_pull_refresh_grid.getRefreshableView();
		mGridView.setHorizontalSpacing(10);
		mGridView.setVerticalSpacing(10);
		mGridView.setSelector(new ColorDrawable(Color.TRANSPARENT));
		mDataList = new ArrayList<DriverDto>();
		// 默认添加新建资料项
		addNewDriver = new DriverDto();
		addNewDriver.setAddNewDriver(true);
		mDataList.add(addNewDriver);// 默认添加,加至最后
		mAdapter = new DriverManagerAdapter(mDataList, context);
		mGridView.setAdapter(mAdapter);
		mGridView.setOnItemClickListener(this);

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
		case R.id.dirver_manager_delete:
			doDirverManagerDelete();
			break;
		case R.id.dirver_manager_cancel:
			doDirverManagerCancel();
			break;

		default:
			break;
		}

	}

	/**
	 * 取消
	 */
	private void doDirverManagerCancel() {
		hideOperation();
	}

	/**
	 * 删除司机
	 */
	private void doDirverManagerDelete() {
		selectedList = mAdapter.getSelectedList();
		selectedList = CommonUtils.BubbleSort(selectedList);
		PdaRequest<List<DriverDto>> driverInfo = new PdaRequest<List<DriverDto>>();
		List<DriverDto> list = new ArrayList<DriverDto>();
		for (int i = 0; i < selectedList.size(); i++) {
			list.add(mDataList.get(selectedList.get(i)));
		}
		driverInfo.setData(list);
		DeleteDriverInfoHandler dataHandler = new DeleteDriverInfoHandler(
				context, driverInfo);
		dataHandler.setOnDataReceiveListener(this);
		dataHandler.startNetWork();
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.maintitle_back_iv:
			CommonUtils.finishActivity(DriverManagerActivity.this);
			break;
		case R.id.maintitle_comfirm_tv:
			if (driver_manager_operation_layout.getVisibility() == View.VISIBLE) {
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

	/**
	 * 显示菜单栏，进入编辑模式
	 */
	private void showOperation() {

		driver_manager_operation_layout.setVisibility(View.VISIBLE);
		driver_manager_operation_layout.setAnimation(AnimationUtils
				.loadAnimation(this, R.anim.down_in));
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
		driver_manager_operation_layout.setVisibility(View.GONE);
		driver_manager_operation_layout.setAnimation(AnimationUtils
				.loadAnimation(this, R.anim.down_out));
		// mDataList.add(addNewDriver);
		int size = mDataList.size();
		for (int i = 0; i < size; i++) {
			mDataList.get(i).setEditMode(false);
		}
		mAdapter.notifyDataSetChanged();
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		if (position == mDataList.size() - 1) {
			addNewDriver(position);
		} else {
			if (driver_manager_operation_layout.getVisibility() == View.VISIBLE) {
				selectDriverForDelete(position);
			} else {
				selectDriverForEdit(mDataList.get(position));
			}
		}
	}

	/**
	 * 添加新司机
	 */
	private void addNewDriver(int position) {
		Intent intent = new Intent(DriverManagerActivity.this,
				AddNewDriverActivity.class);
		startActivityForResult(intent, ADD_NEW_DRIVER_REFRESH);
	}

	/**
	 * 选择司机,修改
	 */
	private void selectDriverForEdit(DriverDto driverDto) {
		Intent intent = new Intent(DriverManagerActivity.this,
				AddNewDriverActivity.class);
		intent.putExtra("driverInfo", driverDto);
		startActivityForResult(intent, EDIT_DRIVER_REFRESH);
	}

	/**
	 * 选择司机，删除
	 */
	private void selectDriverForDelete(int position) {
		mDataList.get(position).setClicked(
				mDataList.get(position).isClicked() ? false : true);
		mAdapter.notifyDataSetChanged();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode != RESULT_OK)
			return;
		switch (requestCode) {
		case ADD_NEW_DRIVER_REFRESH:
		case EDIT_DRIVER_REFRESH:
			// DriverDto driverDto = (DriverDto) data
			// .getSerializableExtra("driverInfo");
			// if (driverDto != null) {
			// mDataList.remove(addNewDriver);
			// mDataList.add(driverDto);
			// mDataList.remove(addNewDriver);
			// mAdapter.notifyDataSetChanged();
			// }
			pageNum = 0;
			new Thread(new Runnable() {

				@Override
				public void run() {
					getDriverInfo(pageNum);
				}
			}).start();
			break;

		default:
			break;
		}
	}


	/**
	 * 获取司机信息
	 */
	private void getDriverInfo(int page) {
		myHandler.sendEmptyMessage(SHOW_PROGRESS);
		PdaRequest<DriverDto> driverInfo = new PdaRequest<DriverDto>();
		PdaPagination pagination = new PdaPagination();
		pagination.setStartPos(page);
		pagination.setAmount(pageSize);
		driverInfo.setData(new DriverDto());
		driverInfo.setPagination(pagination);
		GetDriverInfoHandler dataHandler = new GetDriverInfoHandler(context,
				driverInfo);
		dataHandler.setOnDataReceiveListener(this);
		dataHandler.startNetWork();
	}

	@Override
	public void onDataReceive(DataHandler dataHandler, int resultCode,
			Object data, int type) {
		myHandler.sendEmptyMessage(CLOSE_PROGRESS);
		switch (resultCode) {
		case NetWork.GET_DRIVER_INFO_OK:
			doGetDriverSuccess(data);
			break;
		case NetWork.DELETE_DRIVER_INFO_OK:
			doDeleteDriverSuccess(data);
			break;
		case NetWork.DELETE_DRIVER_INFO_ERROR:
		case NetWork.GET_DRIVER_INFO_ERROR:
			dirver_pull_refresh_grid.onRefreshComplete();
			ToastUtil.show(context,
					getResources().getString(R.string.network_busy));
			break;

		default:
			break;
		}
	}

	private void doDeleteDriverSuccess(Object data) {
		String dataString = null;
		try {
			dataString = new String((byte[]) data, "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			PdaResponse<String> response = ResultCodeJsonParser
					.parserResultCodeJson(dataString);

			if (response.isSuccess()) {
				// 成功
				Message msg = myHandler.obtainMessage();
				msg.what = SHOW_TOAST;
				msg.obj = "删除司机成功";
				myHandler.sendMessage(msg);
				for (int i = 0; i < selectedList.size(); i++) {
					mDataList.remove(selectedList.get(i) - i);
				}
				mAdapter.notifyDataSetChanged();
				hideOperation();

			} else {// 失败
				String result = response.getMessage();
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
			e.printStackTrace();
			ToastUtil.show(context, "删除司机失败,请稍后重新删除");
		}

	}

	/**
	 * 获取司机信息成功
	 */
	private void doGetDriverSuccess(Object data) {
		String result = null;
		try {
			result = new String((byte[]) data, "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			PdaResponse<List<DriverDto>> mData = DriverInfoJsonParser
					.parserDriverInfoJson(result);
			if (null == mData || null == mData.getData()) {
				ToastUtil.show(context, "获取司机信息失败，请重新获取");
				return;
			}
			mDataList.remove(addNewDriver);
			if (!isGetMoreData) {
				mDataList.clear();
			}
			for (DriverDto driverDto : mData.getData()) {
				mDataList.add(driverDto);
			}
			mDataList.add(addNewDriver);
			totalPage = new Long(mData.getTotal()).intValue();
			mAdapter = new DriverManagerAdapter(mDataList, context);
			mGridView.setAdapter(mAdapter);
			// mAdapter.notifyDataSetChanged();
			dirver_pull_refresh_grid.onRefreshComplete();
		} catch (Exception e) {
			ToastUtil.show(context, "获取司机信息失败，请重新获取");
			e.printStackTrace();
		}
	}

	OnRefreshListener mOnrefreshListener = new OnRefreshListener() {

		@Override
		public void onRefresh(int scrollState) {
			// TODO Auto-generated method stub
			if (scrollState == PullToRefreshBase.STATE_OF_HEADER) {
				pageNum = 0;
				isGetMoreData = false;
				getDriverInfo(pageNum);
			} else if (scrollState == PullToRefreshBase.STATE_OF_FOOTER) {
				isGetMoreData = true;
				pageNum = pageNum + pageSize;
				if (pageNum < totalPage) {
					getDriverInfo(pageNum);
				} else {
					ToastUtil.show(context, "没有更多数据");
					dirver_pull_refresh_grid.onRefreshComplete();
				}
			}
		}
	};
}
