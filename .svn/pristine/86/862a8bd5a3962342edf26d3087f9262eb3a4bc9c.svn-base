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
import com.seeyuan.logistics.adapter.SelectCarAdapter;
import com.seeyuan.logistics.application.NetWork;
import com.seeyuan.logistics.datacenter.DataHandler;
import com.seeyuan.logistics.datacenter.OnDataReceiveListener;
import com.seeyuan.logistics.datahandler.SearchCarManagerHandler;
import com.seeyuan.logistics.entity.CarsDto;
import com.seeyuan.logistics.entity.PdaPagination;
import com.seeyuan.logistics.entity.PdaRequest;
import com.seeyuan.logistics.entity.PdaResponse;
import com.seeyuan.logistics.jsonparser.CarSourceJsonParser;
import com.seeyuan.logistics.pullrefreshview.PullToRefreshBase;
import com.seeyuan.logistics.pullrefreshview.PullToRefreshBase.OnRefreshListener;
import com.seeyuan.logistics.pullrefreshview.PullToRefreshGridView;
import com.seeyuan.logistics.util.CommonUtils;
import com.seeyuan.logistics.util.ToastUtil;

/**
 * 选择车辆
 * 
 * @author zhazhaobao
 * 
 */
public class SelectCarManagerActivity extends BaseActivity implements
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

	private SelectCarAdapter mAdapter;

	private List<CarsDto> mDataList;

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
		getCarsInfo(pageNum);
	}

	@Override
	public void initView() {
		maintitle_back_iv = (ImageView) findViewById(R.id.maintitle_back_iv);
		maintitle_back_iv.setOnClickListener(this);

		defaulttitle_title_tv = (TextView) findViewById(R.id.defaulttitle_title_tv);
		defaulttitle_title_tv.setText("选择车辆");
	}

	private void initAdapter() {
		select_dirver_pull_refresh_grid = (PullToRefreshGridView) findViewById(R.id.select_dirver_pull_refresh_grid);
		select_dirver_pull_refresh_grid
				.setOnRefreshListener(mOnrefreshListener);
		mGridView = select_dirver_pull_refresh_grid.getRefreshableView();
		mGridView.setHorizontalSpacing(10);
		mGridView.setVerticalSpacing(10);
		mGridView.setSelector(new ColorDrawable(Color.TRANSPARENT));
		mDataList = new ArrayList<CarsDto>();

		mAdapter = new SelectCarAdapter(mDataList, context);
		mGridView.setAdapter(mAdapter);
		mGridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent intent = new Intent();
				intent.putExtra("carInfo", mDataList.get((int)id));
				setResult(RESULT_OK, intent);
				finish();
			}
		});
	}

	/**
	 * 获取车辆信息
	 */
	private void getCarsInfo(int page) {
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
	public void onClickListener(View view) {

	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.maintitle_back_iv:
			 CommonUtils.finishActivity(SelectCarManagerActivity.this);
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
				getCarsInfo(pageNum);
			} else if (scrollState == PullToRefreshBase.STATE_OF_FOOTER) {
				isGetMoreData = true;
				pageNum = pageNum + pageSize;
				if (pageNum < totalPage) {
					getCarsInfo(pageNum);
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
		case NetWork.SEARCH_CAR_OK:
			doGetCarSuccess(data);
			break;
		case NetWork.SEARCH_CAR_ERROR:
			select_dirver_pull_refresh_grid.onRefreshComplete();
			ToastUtil.show(context,
					getResources().getString(R.string.network_error_hint));
			break;

		default:
			break;
		}
	}

	/**
	 * 获取车辆信息成功
	 */
	private void doGetCarSuccess(Object data) {
		String result = null;
		try {
			result = new String((byte[]) data, "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			PdaResponse<List<CarsDto>> mData = CarSourceJsonParser
					.parserSearchCarSourceJson(result);
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
			for (CarsDto carsDto : mData.getData()) {
				mDataList.add(carsDto);
			}
			// mDataList.add(addNewDriver);
			totalPage = new Long(mData.getTotal()).intValue();
			mAdapter = new SelectCarAdapter(mDataList, context);
			mGridView.setAdapter(mAdapter);
			// mAdapter.notifyDataSetChanged();
			select_dirver_pull_refresh_grid.onRefreshComplete();
		} catch (Exception e) {
			ToastUtil.show(context, "获取司机信息失败，请重新获取");
			e.printStackTrace();
		}
	}

}
