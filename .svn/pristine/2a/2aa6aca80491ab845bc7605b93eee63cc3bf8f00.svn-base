package com.seeyuan.logistics.activity;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.mesada.nj.pubcontrols.utils.ToastUtil;
import com.seeyuan.logistics.R;
import com.seeyuan.logistics.adapter.SettlementManager2ListAdapter;
import com.seeyuan.logistics.adapter.SettlementManagerListAdapter;
import com.seeyuan.logistics.application.NetWork;
import com.seeyuan.logistics.customview.ProgressAlertDialog;
import com.seeyuan.logistics.datacenter.DataHandler;
import com.seeyuan.logistics.datacenter.OnDataReceiveListener;
import com.seeyuan.logistics.datahandler.GetAccountSettlementHandler;
import com.seeyuan.logistics.entity.AccountSettleDto;
import com.seeyuan.logistics.entity.OrderDto;
import com.seeyuan.logistics.entity.PdaPagination;
import com.seeyuan.logistics.entity.PdaRequest;
import com.seeyuan.logistics.entity.PdaResponse;
import com.seeyuan.logistics.jsonparser.AccountSettlementJsonParser;
import com.seeyuan.logistics.pullrefreshview.PullToRefreshBase;
import com.seeyuan.logistics.pullrefreshview.PullToRefreshBase.OnRefreshListener;
import com.seeyuan.logistics.pullrefreshview.PullToRefreshListView;
import com.seeyuan.logistics.util.CommonUtils;

/**
 * 结算管理
 * 
 * @author zhazhaobao
 * 
 */
public class SettlementActivity extends BaseActivity implements
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
	 * 订单结算
	 */
	private TextView maintitle_comfirm_tv;

	/**
	 * 结算订单refreshList
	 */
	private PullToRefreshListView settlement_listview;

	/**
	 * 可结算订单 refreshList
	 */
	private PullToRefreshListView settlement_listview2;

	/**
	 * 结算单List
	 */
	private ListView mListView;

	/**
	 * 可结算订单list
	 */
	private ListView mListView2;

	private SettlementManagerListAdapter mAdapter;

	private SettlementManager2ListAdapter mAdapter2;

	private Context context;

	/**
	 * 结算单list
	 */
	private List<AccountSettleDto> mDataList;

	/**
	 * 可结算list
	 */
	private List<OrderDto> mDataList2;

	private final int SHOW_PROGRESS = 1002;
	private final int CLOSE_PROGRESS = 1003;
	private final int SHOW_TOAST = 1004;

	private ProgressAlertDialog progressDialog;

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

	/**
	 * 需求每页元素个数
	 */
	private final int pageSize2 = 5;
	/**
	 * 页数
	 */
	private int pageNum2 = 0;

	/**
	 * 一共多少页
	 */
	private int totalPage2 = 0;

	/**
	 * 是否获取更多数据
	 */
	private boolean isGetMoreData2 = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.activity_settlement); // 软件activity的布局
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,
				R.layout.deflaut_titlebar); // titlebar为自己标题栏的布局
		context = getApplicationContext();
		initView();
		initDealManagerAdapter();
		myHandler.sendEmptyMessage(SHOW_PROGRESS);
		getSettlementInfo();
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.maintitle_back_iv:
			CommonUtils.finishActivity(this);
			break;
		case R.id.maintitle_comfirm_tv:
			doOrderSettlement();
			break;
		default:
			break;
		}
	}

	private Handler myHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case SHOW_PROGRESS:
				showProgress();
				break;
			case CLOSE_PROGRESS:
				settlement_listview.onRefreshComplete();
				settlement_listview2.onRefreshComplete();
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

	/**
	 * 获取结算信息
	 */
	private void getSettlementInfo() {
		PdaRequest<String> settleAccountDto = new PdaRequest<String>();
		settleAccountDto.setData("");
		PdaPagination pagination = new PdaPagination();
		pagination.setStartPos(0);
		pagination.setAmount(pageSize);
		settleAccountDto.setPagination(pagination);
		GetAccountSettlementHandler dataHandler = new GetAccountSettlementHandler(
				context, settleAccountDto);
		dataHandler.setOnDataReceiveListener(this);
		dataHandler.startNetWork();
	}

	/**
	 * 获取更多结算信息
	 */
	private void getMoreSettlementInfo(int page) {
		PdaRequest<String> settleAccountDto = new PdaRequest<String>();
		settleAccountDto.setData("");
		PdaPagination pagination = new PdaPagination();
		pagination.setStartPos(page);
		pagination.setAmount(pageSize);
		settleAccountDto.setPagination(pagination);
		GetAccountSettlementHandler dataHandler = new GetAccountSettlementHandler(
				context, settleAccountDto);
		dataHandler.setOnDataReceiveListener(this);
		dataHandler.startNetWork();
	}

	/**
	 * 订单结算
	 */
	private void doOrderSettlement() {

	}

	@Override
	public void initView() {
		maintitle_back_iv = (ImageView) findViewById(R.id.maintitle_back_iv);
		maintitle_back_iv.setOnClickListener(this);

		defaulttitle_title_tv = (TextView) findViewById(R.id.defaulttitle_title_tv);
		defaulttitle_title_tv.setText("结算管理");

		maintitle_comfirm_tv = (TextView) findViewById(R.id.maintitle_comfirm_tv);
		maintitle_comfirm_tv.setText("订单结算");
		maintitle_comfirm_tv.setVisibility(View.VISIBLE);
		maintitle_comfirm_tv.setOnClickListener(this);
	}

	/**
	 * 初始化结算订单
	 */
	private void initDealManagerAdapter() {

		settlement_listview = (PullToRefreshListView) findViewById(R.id.settlement_listview);
		mDataList = new ArrayList<AccountSettleDto>();
		mAdapter = new SettlementManagerListAdapter(context, mDataList);
		mListView = settlement_listview.getRefreshableView();
		settlement_listview.setOnRefreshListener(mOnrefreshListener);
		mListView.setAdapter(mAdapter);
		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent intent = new Intent(SettlementActivity.this,
						SettlementDetailActivity.class);
				intent.putExtra("accountSettleDto", mDataList.get((int) id));
				startActivity(intent);
			}
		});
		settlement_listview2 = (PullToRefreshListView) findViewById(R.id.settlement_listview2);
		mDataList2 = new ArrayList<OrderDto>();
		mAdapter2 = new SettlementManager2ListAdapter(context, mDataList2);
		mListView2 = settlement_listview2.getRefreshableView();
		settlement_listview2.setOnRefreshListener(mOnrefreshListener2);
		mListView2.setAdapter(mAdapter2);
		mListView2.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

			}
		});
	}

	OnRefreshListener mOnrefreshListener = new OnRefreshListener() {

		@Override
		public void onRefresh(int scrollState) {
			// TODO Auto-generated method stub
			if (scrollState == PullToRefreshBase.STATE_OF_HEADER) {
				pageNum = 0;
				isGetMoreData = false;
				getSettlementInfo();
			} else if (scrollState == PullToRefreshBase.STATE_OF_FOOTER) {
				isGetMoreData = true;
				pageNum = pageNum + pageSize;
				if (pageNum < totalPage) {
					getMoreSettlementInfo(pageNum);
				} else {
					ToastUtil.show(context, "没有更多数据");
					settlement_listview.onRefreshComplete();
				}
			}
		}
	};

	OnRefreshListener mOnrefreshListener2 = new OnRefreshListener() {

		@Override
		public void onRefresh(int scrollState) {
			// TODO Auto-generated method stub
			if (scrollState == PullToRefreshBase.STATE_OF_HEADER) {
				pageNum2 = 0;
				isGetMoreData2 = false;
			} else if (scrollState == PullToRefreshBase.STATE_OF_FOOTER) {
				isGetMoreData2 = true;
				pageNum2 = pageNum2 + pageSize2;
				if (pageNum2 < totalPage2) {
				} else {
					ToastUtil.show(context, "没有更多数据");
					settlement_listview2.onRefreshComplete();
				}
			}
		}
	};

	@Override
	public void onClickListener(View view) {

	}

	@Override
	public void onDataReceive(DataHandler dataHandler, int resultCode,
			Object data, int type) {
		myHandler.sendEmptyMessage(CLOSE_PROGRESS);
		switch (resultCode) {
		case NetWork.GET_ACCOUNT_SETTLEMENT_OK:
			doGetAccountSettlementSuccess(data);
			break;
		case NetWork.GET_ACCOUNT_SETTLEMENT_ERROR:
			ToastUtil.show(context, "获取账号信息失败,请重新操作");
			break;

		default:
			break;
		}
	}

	/**
	 * 获取结算单成功
	 * 
	 * @param data
	 */
	private void doGetAccountSettlementSuccess(Object data) {
		String dataString = null;
		try {
			dataString = new String((byte[]) data, "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
		String message = "";
		try {
			PdaResponse<List<AccountSettleDto>> response = AccountSettlementJsonParser
					.parserAccountSettlementDataJson(dataString);
			if (!response.isSuccess()) {
				String result = response.getMessage();
				int messageCode = Integer.parseInt(result.substring(0,
						result.indexOf("#")));
				message = result.substring(result.indexOf("#") + 1,
						result.length());
			} else {
				if (!isGetMoreData) {
					doRefreshList(response);
				} else {
					doRefreshListMore(response);
				}
				return;
			}
		} catch (Exception e) {
			e.printStackTrace();
			message = "获取账户信息失败,请重新获取";
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
	private void doRefreshList(PdaResponse<List<AccountSettleDto>> response) {
		if (null == response || response.getData().size() == 0)
			return;
		mDataList = response.getData();
		mAdapter = new SettlementManagerListAdapter(context, mDataList);
		mListView.setAdapter(mAdapter);
	}

	/**
	 * 刷新列表，更多资源
	 * 
	 * @param response
	 */
	private void doRefreshListMore(PdaResponse<List<AccountSettleDto>> response) {
		if (null == response || response.getData().size() == 0)
			return;
		for (AccountSettleDto accountDto : response.getData()) {
			mDataList.add(accountDto);
		}
		mDataList = response.getData();
		mAdapter.notifyDataSetChanged();
	}
}
