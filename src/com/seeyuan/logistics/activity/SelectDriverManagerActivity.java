package com.seeyuan.logistics.activity;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.seeyuan.logistics.R;
import com.seeyuan.logistics.adapter.DriverManagerAdapter;
import com.seeyuan.logistics.application.NetWork;
import com.seeyuan.logistics.datacenter.DataHandler;
import com.seeyuan.logistics.datacenter.OnDataReceiveListener;
import com.seeyuan.logistics.datahandler.GetDriverInfoHandler;
import com.seeyuan.logistics.entity.DriverDto;
import com.seeyuan.logistics.entity.DriverManagerInfo;
import com.seeyuan.logistics.entity.PdaPagination;
import com.seeyuan.logistics.entity.PdaRequest;
import com.seeyuan.logistics.entity.PdaResponse;
import com.seeyuan.logistics.jsonparser.DriverInfoJsonParser;
import com.seeyuan.logistics.pullrefreshview.PullToRefreshBase;
import com.seeyuan.logistics.pullrefreshview.PullToRefreshBase.OnRefreshListener;
import com.seeyuan.logistics.pullrefreshview.PullToRefreshGridView;
import com.seeyuan.logistics.util.CommonUtils;
import com.seeyuan.logistics.util.ToastUtil;

/**
 * 选择司机
 * 
 * @author zhazhaobao
 * 
 */
public class SelectDriverManagerActivity extends BaseActivity implements
		OnClickListener, OnDataReceiveListener {
	/**
	 * 返回按钮
	 */
	private ImageView maintitle_back_iv;

	/**
	 * 标题title
	 */
	private TextView defaulttitle_title_tv;

	private PullToRefreshGridView select_dirver_pull_refresh_grid;

	private GridView mGridView;

	private DriverManagerAdapter mAdapter;

	private List<DriverDto> mDataList;

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

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.activity_select_driver); // 软件activity的布局
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,
				R.layout.deflaut_titlebar); // titlebar为自己标题栏的布局
		context = getApplicationContext();
		initView();
		initAdapter();
		getDriverInfo(pageNum);
	}

	@Override
	public void initView() {
		maintitle_back_iv = (ImageView) findViewById(R.id.maintitle_back_iv);
		maintitle_back_iv.setOnClickListener(this);

		defaulttitle_title_tv = (TextView) findViewById(R.id.defaulttitle_title_tv);
		defaulttitle_title_tv.setText(getResources().getString(
				R.string.select_driver_hint));
	}

	private void initAdapter() {
		select_dirver_pull_refresh_grid = (PullToRefreshGridView) findViewById(R.id.select_dirver_pull_refresh_grid);
		select_dirver_pull_refresh_grid
				.setOnRefreshListener(mOnrefreshListener);
		mGridView = select_dirver_pull_refresh_grid.getRefreshableView();
		mGridView.setHorizontalSpacing(10);
		mGridView.setVerticalSpacing(10);
		mGridView.setSelector(new ColorDrawable(Color.TRANSPARENT));
		mDataList = new ArrayList<DriverDto>();

		mAdapter = new DriverManagerAdapter(mDataList, context);
		mGridView.setAdapter(mAdapter);
		mGridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent intent = new Intent();
				intent.putExtra("driverInfo", mDataList.get((int)id));
				setResult(RESULT_OK, intent);
				finish();
			}
		});
	}

	/**
	 * 获取司机信息
	 */
	private void getDriverInfo(int page) {
		PdaRequest<DriverDto> driverInfo = new PdaRequest<DriverDto>();
		PdaPagination pagination = new PdaPagination();
		pagination.setStartPos(page);
		pagination.setAmount(pageSize);
		// driverInfo.setUuId(sPreferences.getString("uuId", "0"));
		driverInfo.setUuId("36f51a72-7953-4b09-9811-52c56d00906c");
		driverInfo.setData(new DriverDto());
		driverInfo.setPagination(pagination);
		GetDriverInfoHandler dataHandler = new GetDriverInfoHandler(context,
				driverInfo);
		dataHandler.setOnDataReceiveListener(this);
		dataHandler.startNetWork();
	}

	@Override
	public void onClickListener(View view) {

	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.maintitle_back_iv:
			CommonUtils.finishActivity(SelectDriverManagerActivity.this);
			break;
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
					select_dirver_pull_refresh_grid.onRefreshComplete();
				}
			}
		}
	};

	@Override
	public void onDataReceive(DataHandler dataHandler, int resultCode,
			Object data, int type) {
		switch (resultCode) {
		case NetWork.GET_DRIVER_INFO_OK:
			doGetDriverSuccess(data);
			break;
		case NetWork.GET_DRIVER_INFO_ERROR:
			select_dirver_pull_refresh_grid.onRefreshComplete();
			ToastUtil.show(context,
					getResources().getString(R.string.network_error_hint));
			break;

		default:
			break;
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
			// mDataList.remove(addNewDriver);
			if (!isGetMoreData) {
				mDataList.clear();
			} else {
				// mDataList.remove(addNewDriver);
			}
			for (DriverDto driverDto : mData.getData()) {
				mDataList.add(driverDto);
			}
			// mDataList.add(addNewDriver);
			totalPage = new Long(mData.getTotal()).intValue();
			mAdapter = new DriverManagerAdapter(mDataList, context);
			mGridView.setAdapter(mAdapter);
			// mAdapter.notifyDataSetChanged();
			select_dirver_pull_refresh_grid.onRefreshComplete();
		} catch (Exception e) {
			ToastUtil.show(context, "获取司机信息失败，请重新获取");
			e.printStackTrace();
		}
	}

}
