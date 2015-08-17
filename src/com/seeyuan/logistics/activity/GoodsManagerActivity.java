package com.seeyuan.logistics.activity;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.seeyuan.logistics.R;
import com.seeyuan.logistics.adapter.GoodsManagerAdapter;
import com.seeyuan.logistics.application.NetWork;
import com.seeyuan.logistics.customview.ProgressAlertDialog;
import com.seeyuan.logistics.datacenter.DataHandler;
import com.seeyuan.logistics.datacenter.OnDataReceiveListener;
import com.seeyuan.logistics.datahandler.GetGoodsInfoHandler;
import com.seeyuan.logistics.entity.GoodsDto;
import com.seeyuan.logistics.entity.PdaPagination;
import com.seeyuan.logistics.entity.PdaRequest;
import com.seeyuan.logistics.entity.PdaResponse;
import com.seeyuan.logistics.jsonparser.GoodsInfoJsonParser;
import com.seeyuan.logistics.pullrefreshview.PullToRefreshBase;
import com.seeyuan.logistics.pullrefreshview.PullToRefreshBase.OnRefreshListener;
import com.seeyuan.logistics.pullrefreshview.PullToRefreshListView;
import com.seeyuan.logistics.util.CommonUtils;
import com.seeyuan.logistics.util.ToastUtil;

/**
 * 货源管理
 * 
 * @author zhazhaobao
 * 
 */
@SuppressLint("HandlerLeak")
public class GoodsManagerActivity extends BaseActivity implements
		OnClickListener, OnDataReceiveListener {
	/**
	 * 返回按钮
	 */
	private ImageView maintitle_back_iv;

	/**
	 * 标题title
	 */
	private TextView defaulttitle_title_tv;

	private PullToRefreshListView goods_manager_refresh_view;

	private ListView mListView;

	private List<GoodsDto> mDataList;

	private GoodsManagerAdapter mAdapter;

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

	/**
	 * 修改货源数据返回
	 */
	private final int REFRESH_EDIT_GOODS = 1000;

	/**
	 * 当前选中的item的id
	 */
	private int currentPostion = -1;

	/**
	 * 显示进度条
	 */
	private final int SHOW_PROGRESS = 2000;
	/**
	 * 关闭进度条
	 */
	private final int CLOSE_PROGRESS = 2001;

	private ProgressAlertDialog progressDialog;

	/**
	 * 是否正常操作进入 判断界面的跳转效果
	 */
	private boolean isNomalGetIn = true;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.activity_goods_manager); // 软件activity的布局
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,
				R.layout.deflaut_titlebar); // titlebar为自己标题栏的布局
		context = getApplicationContext();
		isNomalGetIn = getIntent().getBooleanExtra("isNomalGetIn", true);
		initView();
		initAdapter();
		new Thread(new Runnable() {

			@Override
			public void run() {
				getGoodsInfo(pageNum);
			}
		}).start();
	}

	@Override
	public void initView() {
		maintitle_back_iv = (ImageView) findViewById(R.id.maintitle_back_iv);
		maintitle_back_iv.setOnClickListener(this);

		defaulttitle_title_tv = (TextView) findViewById(R.id.defaulttitle_title_tv);
		defaulttitle_title_tv.setText(R.string.goods_manage_hint);

		goods_manager_refresh_view = (PullToRefreshListView) findViewById(R.id.goods_manager_refresh_view);
		goods_manager_refresh_view.setOnRefreshListener(mOnrefreshListener);
		mListView = goods_manager_refresh_view.getRefreshableView();

	}

	protected void doItemSelect(Message msg) {
		Intent intent = new Intent(GoodsManagerActivity.this,
				GoodsManagerDetailActivity.class);
		intent.putExtra("goodsInfo", mDataList.get(msg.arg1));
		startActivityForResult(intent, REFRESH_EDIT_GOODS);
	}

	private void initAdapter() {
		mDataList = new ArrayList<GoodsDto>();

		mAdapter = new GoodsManagerAdapter(context, myHandler, mDataList);
		mListView.setAdapter(mAdapter);
	}

	private Handler myHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case R.id.item_goods_manager_search_car:
				doSearchCar();
				break;
			case R.id.item_goods_manager_line_edit:

				break;
			case R.id.item_goods_manager_line_retry:

				break;
			case R.id.item_goods_manager_line_delete:

				break;
			case R.id.item_goods_manager_goods_layout:
				currentPostion = msg.arg1;
				doItemSelect(msg);
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
		case R.id.goods_manager_new:
			doNewGoods();
			break;

		default:
			break;
		}

	}

	protected void doSearchCar() {

		Intent intent = new Intent(GoodsManagerActivity.this,
				SearchCarInfoActivity.class);
		startActivity(intent);

	}

	private void doNewGoods() {
		Intent intent = new Intent(GoodsManagerActivity.this,
				TabPublishGoodsActivity.class);
		//AddNewGoodsManagerActivity
		intent.putExtra("tag", 2);
		startActivity(intent);
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.maintitle_back_iv:
			// CommonUtils.finishActivity(GoodsManagerActivity.this);
			back();
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

	// @Override
	// public boolean dispatchKeyEvent(KeyEvent event) {
	// if (event.getKeyCode() == KeyEvent.KEYCODE_BACK
	// && event.getAction() == KeyEvent.ACTION_DOWN) {
	// back();
	// return false;
	// }
	// return super.dispatchKeyEvent(event);
	// }

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
	 * 获取货源信息
	 */
	private void getGoodsInfo(int page) {
		myHandler.sendEmptyMessage(SHOW_PROGRESS);
		PdaRequest<GoodsDto> request = new PdaRequest<GoodsDto>();
		PdaPagination pagination = new PdaPagination();
		pagination.setStartPos(page);
		pagination.setAmount(pageSize);
		request.setPagination(pagination);
		request.setData(new GoodsDto());
		GetGoodsInfoHandler dataHandler = new GetGoodsInfoHandler(context,
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
				getGoodsInfo(pageNum);
			} else if (scrollState == PullToRefreshBase.STATE_OF_FOOTER) {
				isGetMoreData = true;
				pageNum = pageNum + pageSize;
				if (pageNum < totalPage) {
					getGoodsInfo(pageNum);
				} else {
					ToastUtil.show(context, "没有更多数据");
					goods_manager_refresh_view.onRefreshComplete();
				}
			}
		}
	};

	@Override
	public void onDataReceive(DataHandler dataHandler, int resultCode,
			Object data, int type) {
		myHandler.sendEmptyMessage(CLOSE_PROGRESS);
		goods_manager_refresh_view.onRefreshComplete();
		switch (resultCode) {
		case NetWork.GET_GOODS_INFO_OK:
			doGetGoodsSuccess(data);
			break;
		case NetWork.GET_GOODS_INFO_ERROR:
			goods_manager_refresh_view.onRefreshComplete();
			ToastUtil.show(context,
					getResources().getString(R.string.network_error_hint));
			break;

		default:
			break;
		}
	}

	/**
	 * 获取货源信息成功
	 * 
	 * @param data
	 */
	private void doGetGoodsSuccess(Object data) {
		String dataString = null;
		try {
			dataString = new String((byte[]) data, "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			PdaResponse<List<GoodsDto>> mData = GoodsInfoJsonParser
					.parserGoodsInfoJson(dataString);
			if (null == mData || null == mData.getData() || !mData.isSuccess()) {
				ToastUtil.show(context, "获取货源信息失败，请重新获取");
				return;
			}
			// mDataList.remove(addNewDriver);
			if (!isGetMoreData) {
				mDataList.clear();
			} else {
				// mDataList.remove(addNewDriver);
			}
			for (GoodsDto goodsDto : mData.getData()) {
				mDataList.add(goodsDto);
			}
			// mDataList.add(addNewDriver);
			totalPage = new Long(mData.getTotal()).intValue();
			mAdapter = new GoodsManagerAdapter(context, myHandler, mDataList);
			mListView.setAdapter(mAdapter);
			// mAdapter.notifyDataSetChanged();
			goods_manager_refresh_view.onRefreshComplete();
		} catch (Exception e) {
			ToastUtil.show(context, "获取货源信息失败，请重新获取");
			e.printStackTrace();
			goods_manager_refresh_view.onRefreshComplete();
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode != RESULT_OK)
			return;
		switch (requestCode) {
		case REFRESH_EDIT_GOODS:
			boolean isEdit = data.getBooleanExtra("isEdit", false);
			GoodsDto goodsDto = (GoodsDto) data
					.getSerializableExtra("goodsInfo");
			if (isEdit) {
				mDataList.set(currentPostion, goodsDto);
				mAdapter.notifyDataSetChanged();
			} else {
				mDataList.remove(currentPostion);
				mAdapter.notifyDataSetChanged();
			}
			break;

		default:
			break;
		}
	}
}
