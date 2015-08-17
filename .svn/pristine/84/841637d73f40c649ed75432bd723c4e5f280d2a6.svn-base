package com.seeyuan.logistics.activity;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.mesada.nj.pubcontrols.controls.RemoteImageView;
import com.seeyuan.logistics.R;
import com.seeyuan.logistics.adapter.SettlementDetailManagerListAdapter;
import com.seeyuan.logistics.application.NetWork;
import com.seeyuan.logistics.customview.ProgressAlertDialog;
import com.seeyuan.logistics.datacenter.DataHandler;
import com.seeyuan.logistics.datacenter.OnDataReceiveListener;
import com.seeyuan.logistics.datahandler.GetAccountSettlementDetailHandler;
import com.seeyuan.logistics.entity.AccountSettleDto;
import com.seeyuan.logistics.entity.PdaPagination;
import com.seeyuan.logistics.entity.PdaRequest;
import com.seeyuan.logistics.entity.PdaResponse;
import com.seeyuan.logistics.entity.SettleAccountsDetailDto;
import com.seeyuan.logistics.jsonparser.AccountSettlementDetailJsonParser;
import com.seeyuan.logistics.util.CommonUtils;
import com.seeyuan.logistics.util.ToastUtil;

/**
 * 结算明细详情
 * 
 * @author zhazhaobao
 * 
 */
public class SettlementDetailActivity extends BaseActivity implements
		OnClickListener, OnDataReceiveListener {

	private Context context;

	/**
	 * 返回按钮
	 */
	private ImageView maintitle_back_iv;

	/**
	 * 标题title
	 */
	private TextView defaulttitle_title_tv;

	/**
	 * 折算后金额
	 */
	private TextView settlement_detail_money;
	/**
	 * 服务费
	 */
	private TextView settlement_detail_service_charge;
	/**
	 * 实际折算金额
	 */
	private TextView settlement_detail_net_amount;

	/**
	 * 结算凭证
	 */
	private RemoteImageView settlement_detail_phone;

	/**
	 * 账户类型
	 */
	private TextView settlement_detail_account_type;

	/**
	 * 账户姓名
	 */
	private TextView settlement_detail_account_name;
	/**
	 * 账户号码
	 */
	private TextView settlement_detail_account_number;

	private AccountSettleDto accountSettleDto;

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

	private final int SHOW_PROGRESS = 1002;
	private final int CLOSE_PROGRESS = 1003;
	private final int SHOW_TOAST = 1004;

	private ProgressAlertDialog progressDialog;

	private List<SettleAccountsDetailDto> mDataList;

	private SettlementDetailManagerListAdapter mAdapter;

	private ListView mListView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.activity_settlement_detail); // 软件activity的布局
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,
				R.layout.deflaut_titlebar); // titlebar为自己标题栏的布局
		context = getApplicationContext();
		accountSettleDto = (AccountSettleDto) getIntent().getSerializableExtra(
				"accountSettleDto");
		initView();
		initAdapter();
		getSettlementDetail();
	}

	@Override
	public void initView() {
		maintitle_back_iv = (ImageView) findViewById(R.id.maintitle_back_iv);
		maintitle_back_iv.setOnClickListener(this);

		defaulttitle_title_tv = (TextView) findViewById(R.id.defaulttitle_title_tv);
		defaulttitle_title_tv.setText("结算明细");

		settlement_detail_money = (TextView) findViewById(R.id.settlement_detail_money);
		settlement_detail_money.setText(null == accountSettleDto
				.getAftSettAmount() ? "" : accountSettleDto.getAftSettAmount()
				.toString());

		settlement_detail_service_charge = (TextView) findViewById(R.id.settlement_detail_service_charge);
		settlement_detail_service_charge.setText(null == accountSettleDto
				.getTakeoffAmount() ? "" : accountSettleDto.getTakeoffAmount()
				.toString());

		settlement_detail_net_amount = (TextView) findViewById(R.id.settlement_detail_net_amount);
		settlement_detail_net_amount.setText(null == accountSettleDto
				.getSettAmount() ? "" : accountSettleDto.getSettAmount()
				.toString());

		settlement_detail_phone = (RemoteImageView) findViewById(R.id.settlement_detail_phone);
		// settlement_detail_phone.draw(null == accountSettleDto
		// .getIdPictureFront() ? "" : mMemberAuthDto.getIdPictureFront()
		// .getHeaderImgURL(), ConstantPool.DEFAULT_ICON_PATH, false,
		// false);

		settlement_detail_account_type = (TextView) findViewById(R.id.settlement_detail_account_type);
		settlement_detail_account_type.setText(TextUtils
				.isEmpty(accountSettleDto.getAccType()) ? "" : accountSettleDto
				.getAccType());

		settlement_detail_account_name = (TextView) findViewById(R.id.settlement_detail_account_name);
		settlement_detail_account_name.setText(TextUtils
				.isEmpty(accountSettleDto.getName()) ? "" : accountSettleDto
				.getName());

		settlement_detail_account_number = (TextView) findViewById(R.id.settlement_detail_account_number);
		settlement_detail_account_number.setText(TextUtils
				.isEmpty(accountSettleDto.getAccountNum()) ? ""
				: accountSettleDto.getAccountNum());

	}

	private void initAdapter() {

		mDataList = new ArrayList<SettleAccountsDetailDto>();
		mAdapter = new SettlementDetailManagerListAdapter(context, mDataList);
		mListView = (ListView) findViewById(R.id.settlement_detail_list);
		mListView.setAdapter(mAdapter);
	}

	/**
	 * 获取结算订单信息
	 */
	private void getSettlementDetail() {
		PdaRequest<SettleAccountsDetailDto> request = new PdaRequest<SettleAccountsDetailDto>();
		SettleAccountsDetailDto settleAccountsDetailDto = new SettleAccountsDetailDto();
		settleAccountsDetailDto.setRefId(accountSettleDto.getId());
		request.setData(settleAccountsDetailDto);
		GetAccountSettlementDetailHandler dataHandler = new GetAccountSettlementDetailHandler(
				context, request);
		PdaPagination pagination = new PdaPagination();
		pagination.setStartPos(0);
		pagination.setAmount(pageSize);
		request.setPagination(pagination);
		dataHandler.setOnDataReceiveListener(this);
		dataHandler.startNetWork();
	}

	/**
	 * 获取更多结算订单信息
	 */
	private void getMoreSettlementDetail(int page) {
		PdaRequest<SettleAccountsDetailDto> request = new PdaRequest<SettleAccountsDetailDto>();
		GetAccountSettlementDetailHandler dataHandler = new GetAccountSettlementDetailHandler(
				context, request);
		PdaPagination pagination = new PdaPagination();
		pagination.setStartPos(page);
		pagination.setAmount(pageSize);
		request.setPagination(pagination);
		dataHandler.setOnDataReceiveListener(this);
		dataHandler.startNetWork();
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
		case R.id.settlement_detail_more_data:
			doGetMoreSettlmentDetail();
			break;
		default:
			break;
		}

	}

	/**
	 * 获取更多结算订单操作信息
	 */
	private void doGetMoreSettlmentDetail() {
		isGetMoreData = true;
		if (mDataList.size() == 0) {
			pageNum = 0;
			getMoreSettlementDetail(pageNum);
		} else {
			pageNum = pageNum + pageSize;
			if (pageNum < totalPage) {
				getMoreSettlementDetail(pageNum);
			} else {
				ToastUtil.show(context, "没有更多数据");
			}
		}
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
	public void onDataReceive(DataHandler dataHandler, int resultCode,
			Object data, int type) {
		myHandler.sendEmptyMessage(CLOSE_PROGRESS);
		switch (resultCode) {
		case NetWork.GET_ACCOUNT_SETTLEMENT_OK:
			doGetAccountSettlementDetailSuccess(data);
			break;
		case NetWork.GET_ACCOUNT_SETTLEMENT_ERROR:
			ToastUtil.show(context, "获取账号信息失败,请重新操作");
			break;

		default:
			break;
		}
	}

	/**
	 * 获取结算订单明细成功
	 * 
	 * @param data
	 */
	private void doGetAccountSettlementDetailSuccess(Object data) {
		String dataString = null;
		try {
			dataString = new String((byte[]) data, "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
		String message = "";
		try {
			PdaResponse<List<SettleAccountsDetailDto>> response = AccountSettlementDetailJsonParser
					.parserAccountSettlementDetailDataJson(dataString);
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
	private void doRefreshList(
			PdaResponse<List<SettleAccountsDetailDto>> response) {
		if (null == response || response.getData().size() == 0)
			return;
		mDataList = response.getData();
		mAdapter = new SettlementDetailManagerListAdapter(context, mDataList);
		mListView.setAdapter(mAdapter);
	}

	/**
	 * 刷新列表，更多资源
	 * 
	 * @param response
	 */
	private void doRefreshListMore(
			PdaResponse<List<SettleAccountsDetailDto>> response) {
		if (null == response || response.getData().size() == 0)
			return;
		for (SettleAccountsDetailDto accountDto : response.getData()) {
			mDataList.add(accountDto);
		}
		mDataList = response.getData();
		mAdapter.notifyDataSetChanged();
	}

}
