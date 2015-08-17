package com.seeyuan.logistics.activity;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.mesada.nj.pubcontrols.utils.ToastUtil;
import com.seeyuan.logistics.R;
import com.seeyuan.logistics.adapter.DealManagerListAdapter;
import com.seeyuan.logistics.adapter.SettlementManagerListAdapter;
import com.seeyuan.logistics.application.NetWork;
import com.seeyuan.logistics.customview.ProgressAlertDialog;
import com.seeyuan.logistics.datacenter.DataHandler;
import com.seeyuan.logistics.datacenter.OnDataReceiveListener;
import com.seeyuan.logistics.datahandler.GetAccountSettlementHandler;
import com.seeyuan.logistics.datahandler.GetDealManagerHandler;
import com.seeyuan.logistics.entity.AccountLogDto;
import com.seeyuan.logistics.entity.AccountSettleDto;
import com.seeyuan.logistics.entity.PdaPagination;
import com.seeyuan.logistics.entity.PdaRequest;
import com.seeyuan.logistics.entity.PdaResponse;
import com.seeyuan.logistics.jsonparser.GetDealManagerJsonParser;
import com.seeyuan.logistics.pullrefreshview.PullToRefreshBase;
import com.seeyuan.logistics.pullrefreshview.PullToRefreshListView;
import com.seeyuan.logistics.pullrefreshview.PullToRefreshBase.OnRefreshListener;
import com.seeyuan.logistics.util.CommonUtils;

/**
 * 交易管理
 * 
 * @author zhazhaobao
 * 
 */
public class DealManagerActivity extends BaseActivity implements
		OnClickListener, OnDataReceiveListener {

	/**
	 * 返回按钮
	 */
	private ImageView maintitle_back_iv;

	/**
	 * 标题title
	 */
	private TextView defaulttitle_title_tv;

	private DealManagerListAdapter mAdapter;

	private ListView mListView;

	private List<AccountLogDto> mDataList;

	private PullToRefreshListView deal_listview;

	private Context context;

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

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.activity_deal_manager); // 软件activity的布局
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,
				R.layout.deflaut_titlebar); // titlebar为自己标题栏的布局
		context = getApplicationContext();
		initView();
		initAdapter();
		getDealManager();
	}

	@Override
	public void initView() {
		maintitle_back_iv = (ImageView) findViewById(R.id.maintitle_back_iv);
		maintitle_back_iv.setOnClickListener(this);

		defaulttitle_title_tv = (TextView) findViewById(R.id.defaulttitle_title_tv);
		defaulttitle_title_tv.setText("交易管理");
	}

	private void initAdapter() {

		deal_listview = (PullToRefreshListView) findViewById(R.id.deal_listview);
		mDataList = new ArrayList<AccountLogDto>();
		mAdapter = new DealManagerListAdapter(context, mDataList);
		deal_listview.setOnRefreshListener(mOnrefreshListener);
		mListView = deal_listview.getRefreshableView();
		mListView.setAdapter(mAdapter);
	}

	OnRefreshListener mOnrefreshListener = new OnRefreshListener() {

		@Override
		public void onRefresh(int scrollState) {
			// TODO Auto-generated method stub
			if (scrollState == PullToRefreshBase.STATE_OF_HEADER) {
				pageNum = 0;
				isGetMoreData = false;
				getDealManager();
			} else if (scrollState == PullToRefreshBase.STATE_OF_FOOTER) {
				isGetMoreData = true;
				pageNum = pageNum + pageSize;
				if (pageNum < totalPage) {
					getMoreDealManager(pageNum);
				} else {
					ToastUtil.show(context, "没有更多数据");
					deal_listview.onRefreshComplete();
				}
			}
		}
	};

	private Handler myHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case SHOW_PROGRESS:
				showProgress();
				break;
			case CLOSE_PROGRESS:
				deal_listview.onRefreshComplete();
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
	 * 获取交易管理日志
	 */
	private void getDealManager() {
		PdaRequest<String> dealManagerDto = new PdaRequest<String>();
		dealManagerDto.setData("");
		PdaPagination pagination = new PdaPagination();
		pagination.setStartPos(0);
		pagination.setAmount(pageSize);
		dealManagerDto.setPagination(pagination);
		GetDealManagerHandler dataHandler = new GetDealManagerHandler(
				context, dealManagerDto);
		dataHandler.setOnDataReceiveListener(this);
		dataHandler.startNetWork();
	}

	/**
	 * 获取更多交易管理日志
	 */
	private void getMoreDealManager(int page) {
		PdaRequest<String> dealManagerDto = new PdaRequest<String>();
		dealManagerDto.setData("");
		PdaPagination pagination = new PdaPagination();
		pagination.setStartPos(page);
		pagination.setAmount(pageSize);
		dealManagerDto.setPagination(pagination);
		GetDealManagerHandler dataHandler = new GetDealManagerHandler(
				context, dealManagerDto);
		dataHandler.setOnDataReceiveListener(this);
		dataHandler.startNetWork();
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.maintitle_back_iv:
			CommonUtils.finishActivity(this);
			break;
		default:
			break;
		}
	}

	@Override
	public void onClickListener(View view) {

	}

	@Override
	public void onDataReceive(DataHandler dataHandler, int resultCode,
			Object data, int type) {
		myHandler.sendEmptyMessage(CLOSE_PROGRESS);
		switch (resultCode) {
		case NetWork.GET_DEAL_MANAGER_OK:
			doGetDealManagerSuccess(data);
			break;
		case NetWork.GET_DEAL_MANAGER_ERROR:
			deal_listview.onRefreshComplete();
			ToastUtil.show(context, "获取交易信息失败,请重新操作");
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
	private void doGetDealManagerSuccess(Object data) {
		String dataString = null;
		try {
			dataString = new String((byte[]) data, "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
		String message = "";
		try {
			PdaResponse<List<AccountLogDto>> response = GetDealManagerJsonParser
					.parserDealManagerJson(dataString);
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
	private void doRefreshList(PdaResponse<List<AccountLogDto>> response) {
		mDataList = response.getData();
		if (null == mDataList || mDataList.size() == 0)
			return;
		mDataList = response.getData();
		mAdapter = new DealManagerListAdapter(context, mDataList);
		mListView.setAdapter(mAdapter);
	}

	/**
	 * 刷新列表，更多资源
	 * 
	 * @param response
	 */
	private void doRefreshListMore(PdaResponse<List<AccountLogDto>> response) {
		if (null == response || response.getData().size() == 0)
			return;
		for (AccountLogDto accountDto : response.getData()) {
			mDataList.add(accountDto);
		}
		mDataList = response.getData();
		mAdapter.notifyDataSetChanged();
	}
}
