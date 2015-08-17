package com.seeyuan.logistics.activity;

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
import com.seeyuan.logistics.adapter.AccountInfoListAdapter;
import com.seeyuan.logistics.application.NetWork;
import com.seeyuan.logistics.customview.ProgressAlertDialog;
import com.seeyuan.logistics.datacenter.DataHandler;
import com.seeyuan.logistics.datacenter.OnDataReceiveListener;
import com.seeyuan.logistics.datahandler.AccountInfoHandler;
import com.seeyuan.logistics.entity.AccountDto;
import com.seeyuan.logistics.entity.PdaPagination;
import com.seeyuan.logistics.entity.PdaRequest;
import com.seeyuan.logistics.entity.PdaResponse;
import com.seeyuan.logistics.entity.RouteDto;
import com.seeyuan.logistics.jsonparser.AccountInfoJsonParser;
import com.seeyuan.logistics.provider.DBOperate;
import com.seeyuan.logistics.pullrefreshview.PullToRefreshBase;
import com.seeyuan.logistics.pullrefreshview.PullToRefreshListView;
import com.seeyuan.logistics.pullrefreshview.PullToRefreshBase.OnRefreshListener;
import com.seeyuan.logistics.util.CommonUtils;

/**
 * 账户设置
 * 
 * @author zhazhaobao
 * 
 */
public class AccountSettingActivity extends BaseActivity implements
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
	 * 添加新账号
	 */
	private TextView maintitle_comfirm_tv;

	/**
	 * 刷新账号，根据新添加来判断
	 */
	private final int REFRESH_ADD_NEW_ACCOUNT = 1000;

	/**
	 * 刷新账号，根据删除，或者修改来判断
	 */
	private final int REFRESH_ACCOUNT = 1001;

	private AccountDto accountInfo;

	private PullToRefreshListView refreshListView;

	private ListView mListView;

	private AccountInfoListAdapter mAdapter;

	private List<AccountDto> mDataList;

	private Context context;

	/**
	 * 当前选中的账号ID
	 */
	private int selectAccountNO = -1;

	private DBOperate dbOperate;

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
		setContentView(R.layout.activity_account_setting); // 软件activity的布局
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,
				R.layout.deflaut_titlebar); // titlebar为自己标题栏的布局
		context = getApplicationContext();
		dbOperate = DBOperate.getInstance(context);
		initView();
		
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		initAdapter();
		myHandler.sendEmptyMessage(SHOW_PROGRESS);
		getAccountInfo();
	}
	@Override
	public void initView() {
		maintitle_back_iv = (ImageView) findViewById(R.id.maintitle_back_iv);
		maintitle_back_iv.setOnClickListener(this);

		defaulttitle_title_tv = (TextView) findViewById(R.id.defaulttitle_title_tv);
		defaulttitle_title_tv.setText("账户设置");

		maintitle_comfirm_tv = (TextView) findViewById(R.id.maintitle_comfirm_tv);
		maintitle_comfirm_tv.setVisibility(View.VISIBLE);
		maintitle_comfirm_tv.setText("新增");
		maintitle_comfirm_tv.setOnClickListener(this);
	}

	private void initAdapter() {
		refreshListView = (PullToRefreshListView) findViewById(R.id.account_listview);
		mListView = refreshListView.getRefreshableView();
		refreshListView.setOnRefreshListener(mOnrefreshListener);
		mDataList = dbOperate.getAllAccount();
		mAdapter = new AccountInfoListAdapter(context, mDataList);
		mListView.setAdapter(mAdapter);
		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				selectAccountNO = (int) id;
				Intent intent = new Intent(AccountSettingActivity.this,
						AddNewAccountActivity.class);
				intent.putExtra("isDelete", true);
				intent.putExtra("accountInfo", mDataList.get((int) id));
				startActivityForResult(intent, REFRESH_ACCOUNT);
				// startActivity(intent);
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
				getAccountInfo();
			} else if (scrollState == PullToRefreshBase.STATE_OF_FOOTER) {
				isGetMoreData = true;
				pageNum = pageNum + pageSize;
				if (pageNum < totalPage) {
					getMoreAccountInfo(pageNum);
				} else {
					ToastUtil.show(context, "没有更多数据");
					refreshListView.onRefreshComplete();
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
				refreshListView.onRefreshComplete();
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
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.maintitle_back_iv:
			CommonUtils.finishActivity(this);
			break;
		case R.id.maintitle_comfirm_tv:
			doComfirm();
			break;
		default:
			break;
		}
	}

	/**
	 * 点击新增按钮
	 */
	private void doComfirm() {
		Intent intent = new Intent(AccountSettingActivity.this,
				AddNewAccountActivity.class);
		startActivityForResult(intent, REFRESH_ADD_NEW_ACCOUNT);
		// startActivity(intent);
	}

	@Override
	public void onClickListener(View view) {

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode != RESULT_OK)
			return;
		boolean isDeleteAccount = data
				.getBooleanExtra("isDeleteAccount", false);
		accountInfo = (AccountDto) data.getSerializableExtra("accountInfo");
		switch (requestCode) {
		case REFRESH_ADD_NEW_ACCOUNT:
			ToastUtil.show(context, "新增账号成功");
			dbOperate.insertAccount(accountInfo);
			refreshView(accountInfo);
			break;
		case REFRESH_ACCOUNT:
			refreshView(accountInfo, isDeleteAccount);
			break;

		default:
			break;
		}
	}

	private void refreshView(AccountDto accountInfo) {
		if (accountInfo.getIsDefault().equalsIgnoreCase("Y")) {
			List<AccountDto> data = mDataList;
			mDataList.clear();
			for (AccountDto item : data) {
				item.setIsDefault("N");
				mDataList.add(item);
			}
		}
		mDataList.add(accountInfo);
		mAdapter.notifyDataSetChanged();
	}

	private void refreshView(AccountDto accountInfo, boolean isDeleteAccount) {
		if (isDeleteAccount) {
			ToastUtil.show(context, "删除账号成功");
			mDataList.remove(selectAccountNO);
			// dbOperate.deleteAccountByAccountID(accountInfo.getAccountID());
			dbOperate.deleteAccountByAccountID(accountInfo.getId());
			mAdapter.notifyDataSetChanged();
		} else {
			ToastUtil.show(context, "更新账号成功");
			mDataList.set(selectAccountNO, accountInfo);
			dbOperate.updateAccount(accountInfo);
			if (accountInfo.getIsDefault().equalsIgnoreCase("Y")) {
				dbOperate.changeAccountDefaultType(accountInfo);
			}
			// dbOperate.deleteAccountByAccountID(accountInfo.getId());
			// dbOperate.insertAccount(accountInfo);
			mAdapter.notifyDataSetChanged();
		}
	}

	/**
	 * 获取充值账号信息
	 */
	private void getAccountInfo() {
		PdaRequest<String> request = new PdaRequest<String>();
		request.setData("");
		PdaPagination pagination = new PdaPagination();
		pagination.setStartPos(0);
		pagination.setAmount(pageSize);
		request.setPagination(pagination);
		AccountInfoHandler dataHandler = new AccountInfoHandler(context,
				request);
		dataHandler.setOnDataReceiveListener(this);
		dataHandler.startNetWork();
	}

	/**
	 * 获取充值账号信息
	 */
	private void getMoreAccountInfo(int page) {
		PdaRequest<String> request = new PdaRequest<String>();
		request.setData("");
		PdaPagination pagination = new PdaPagination();
		pagination.setStartPos(page);
		pagination.setAmount(pageSize);
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
		switch (resultCode) {
		case NetWork.GET_ACCOUNT_INFO_OK:
			doGetAcountInfoSuccess(data);
			break;
		case NetWork.GET_ACCOUNT_INFO_ERROR:
			ToastUtil.show(context, "获取账号信息失败,请重新操作");
			refreshListView.onRefreshComplete();
			break;

		default:
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
	private void doRefreshList(PdaResponse<List<AccountDto>> response) {
		mDataList = response.getData();
		if (null == mDataList || mDataList.size() == 0)
			return;
		mAdapter = new AccountInfoListAdapter(context, mDataList);
		for (AccountDto accountInfo : mDataList) {
			dbOperate.updateAccount(accountInfo);
		}
		mListView.setAdapter(mAdapter);
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
			mDataList.add(accountDto);
			dbOperate.updateAccount(accountDto);
		}
		mDataList = response.getData();
		mAdapter.notifyDataSetChanged();
	}
}
