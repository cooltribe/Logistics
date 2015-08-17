package com.seeyuan.logistics.activity;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;

import android.content.Context;
import android.os.AsyncTask;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.seeyuan.logistics.R;
import com.seeyuan.logistics.adapter.TabAroundCarAdapter;
import com.seeyuan.logistics.application.NetWork;
import com.seeyuan.logistics.datacenter.DataHandler;
import com.seeyuan.logistics.datacenter.OnDataReceiveListener;
import com.seeyuan.logistics.datahandler.FindAroundCarHandler;
import com.seeyuan.logistics.datahandler.RefreshAroundCarHandler;
import com.seeyuan.logistics.entity.AroundCarInfo;
import com.seeyuan.logistics.entity.PersonalPositionInfo;
import com.seeyuan.logistics.jsonparser.AroundCarJsonParser;
import com.seeyuan.logistics.pullrefreshview.PullToRefreshBase;
import com.seeyuan.logistics.pullrefreshview.PullToRefreshBase.OnRefreshListener;
import com.seeyuan.logistics.pullrefreshview.PullToRefreshListView;
import com.seeyuan.logistics.util.ToastUtil;

public class TabAroundCarActivity extends LinearLayout implements
		View.OnClickListener, OnItemClickListener, OnDataReceiveListener {

	private List<AroundCarInfo> aroundCarInfoList = new ArrayList<AroundCarInfo>();

	private TabAroundCarAdapter mAdapter;

	private ListView mListView;

	private PullToRefreshListView mPullRefreshListView;// 下拉上拉自定义控件

	private Context context;

	/**
	 * 个人地理位置信息
	 */
	private PersonalPositionInfo positionInfo;

	public TabAroundCarActivity(Context context) {
		super(context);
		this.context = context;
		initView();
		initAdapter();
		// new aroundCarAsyncTask().execute();
		initAroundCarInfo();
	}

	public TabAroundCarActivity(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		initView();
		initAdapter();
		// new aroundCarAsyncTask().execute();
		initAroundCarInfo();
	}

	/**
	 * 初始化数据，访问服务器
	 */
	private void initAroundCarInfo() {
		positionInfo = new PersonalPositionInfo();
		positionInfo.setUserID("123");
		positionInfo.setLatitude("123");
		positionInfo.setLongitude("123");
		FindAroundCarHandler dataHandler = new FindAroundCarHandler(context,
				positionInfo);
		dataHandler.setOnDataReceiveListener(this);
		dataHandler.startNetWork();
	}

	/**
	 * 刷新最新附近车源信息
	 */
	private void refreshAroundCarInfo() {
		positionInfo = new PersonalPositionInfo();
		positionInfo.setUserID("123");
		positionInfo.setLatitude("123");
		positionInfo.setLongitude("123");
		RefreshAroundCarHandler dataHandler = new RefreshAroundCarHandler(
				context, positionInfo);
		dataHandler.setOnDataReceiveListener(this);
		dataHandler.startNetWork();
	}

	public void initView() {
		LayoutInflater inflater = LayoutInflater.from(context);
		View view = inflater.inflate(R.layout.activity_tab_around_car, this);
		mPullRefreshListView = (PullToRefreshListView) view
				.findViewById(R.id.tab_around_car_refreshview);
		mPullRefreshListView.setOnRefreshListener(mOnrefreshListener);
		// mPullRefreshListView.setVisibility(View.INVISIBLE);
	}

	/**
	 * 初始化适配器
	 */
	private void initAdapter() {

		// 测试数据
//		AroundCarInfo a = new AroundCarInfo();
//		a.setTarget("全国");
//		a.setUsrName("张先生");
//		a.setPhoneNo("1234567890");
//		a.setCarID("苏A123456");
//		a.setCarStyle("集装车");
//		a.setCarLength("17.5");
//		a.setCarWeigth("14");
//		a.setDistance("0.8");
//		a.setLatitude(116.405275);
//		a.setLongitude(39.915931);
//		a.setAuthentication(true);
//		a.setTime("2014.7.9");
//		a.setStar(3);
//
//		AroundCarInfo a1 = new AroundCarInfo();
//		a1.setTarget("全国");
//		a1.setUsrName("李先生");
//		a1.setPhoneNo("1234567890");
//		a1.setCarID("苏A123456");
//		a1.setCarStyle("板车");
//		a1.setCarLength("20.5");
//		a1.setCarWeigth("14");
//		a1.setDistance("0.8");
//		a1.setAuthentication(false);
//		a1.setTime("2014.7.9");
//		a1.setStar(2);
//
//		AroundCarInfo a2 = new AroundCarInfo();
//		a2.setTarget("全国");
//		a2.setUsrName("王先生");
//		a2.setPhoneNo("1234567890");
//		a2.setCarID("苏A123456");
//		a2.setCarStyle("集装车");
//		a2.setCarLength("30");
//		a2.setCarWeigth("20");
//		a2.setDistance("0.8");
//		a2.setAuthentication(true);
//		a2.setTime("2014.7.9");
//		a2.setStar(3);
//
//		aroundCarInfoList.add(a);
//		aroundCarInfoList.add(a1);
//		aroundCarInfoList.add(a2);

		mListView = mPullRefreshListView.getRefreshableView();
		mAdapter = new TabAroundCarAdapter(aroundCarInfoList, context);
		mListView.setAdapter(mAdapter);
		mListView.setOnItemClickListener(this);

	}

	public class aroundCarAsyncTask extends AsyncTask<Integer, Integer, String> {

		public aroundCarAsyncTask() {
			super();
		}

		@Override
		protected String doInBackground(Integer... arg0) {
			initAroundCarInfo();
			return null;
		}

	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View view, final int position,
			long id) {

	}

	@Override
	public void onClick(View view) {

		switch (view.getId()) {

		default:
			break;
		}
	}

	OnRefreshListener mOnrefreshListener = new OnRefreshListener() {

		@Override
		public void onRefresh(int scrollState) {
			// TODO Auto-generated method stub
			if (scrollState == PullToRefreshBase.STATE_OF_HEADER) {
				refreshAroundCarInfo();
			} else if (scrollState == PullToRefreshBase.STATE_OF_FOOTER) {
				mPullRefreshListView.onRefreshComplete();
			}
		}
	};

	@Override
	public void onDataReceive(DataHandler dataHandler, int resultCode,
			Object data, int type) {
		String dataString = null;
		mPullRefreshListView.onRefreshComplete();
		switch (resultCode) {
		case NetWork.FIND_AROUND_CAR_OK:
			getAroundCarInfoList(dataString, data);
			break;
		case NetWork.FIND_AROUND_CAR_ERROR:
			ToastUtil.show(context, "超时了");
			break;
		case NetWork.REFRESH_AROUND_CAR_OK:
			getNewAroundCarInfoList(dataString, data);

			break;
		case NetWork.REFRESH_AROUND_CAR_ERROR:
			ToastUtil.show(context, "超时了");
			break;

		}
	}

	/**
	 * 获取附近车源信息列表
	 * 
	 * @param dataString
	 * @param data
	 */
	private void getAroundCarInfoList(String dataString, Object data) {
		try {
			dataString = new String((byte[]) data, "UTF-8");
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		try {
			// aroundCarInfoList.clear();
			aroundCarInfoList = AroundCarJsonParser
					.parserAroundCarInfoList(dataString);
			mAdapter.refresh(aroundCarInfoList);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取最新的附近车源信息
	 * 
	 * @param dataString
	 * @param data
	 */
	private void getNewAroundCarInfoList(String dataString, Object data) {
		try {
			dataString = new String((byte[]) data, "UTF-8");
			Log.d("TAG", "dataString = " + dataString);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		try {
			aroundCarInfoList.clear();
			aroundCarInfoList = AroundCarJsonParser
					.parserAroundCarInfoList(dataString);
			mAdapter.refresh(aroundCarInfoList);
		} catch (JSONException e) {
			e.printStackTrace();
		}

	}
}
