package com.seeyuan.logistics.activity;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.ActionBar.LayoutParams;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources.NotFoundException;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.TextView;

import com.seeyuan.logistics.R;
import com.seeyuan.logistics.adapter.GoodsSourceListAdapter;
import com.seeyuan.logistics.adapter.SearchPopWindowAdapter;
import com.seeyuan.logistics.application.ApplicationPool;
import com.seeyuan.logistics.application.ConstantPool;
import com.seeyuan.logistics.application.NetWork;
import com.seeyuan.logistics.customview.ProgressAlertDialog;
import com.seeyuan.logistics.datacenter.DataHandler;
import com.seeyuan.logistics.datacenter.OnDataReceiveListener;
import com.seeyuan.logistics.datahandler.SearchGoodsHandler;
import com.seeyuan.logistics.entity.GoodsDto;
import com.seeyuan.logistics.entity.PdaPagination;
import com.seeyuan.logistics.entity.PdaRequest;
import com.seeyuan.logistics.entity.PdaResponse;
import com.seeyuan.logistics.jsonparser.SearchGoodsJsonParser;
import com.seeyuan.logistics.pullrefreshview.PullToRefreshBase;
import com.seeyuan.logistics.pullrefreshview.PullToRefreshBase.OnRefreshListener;
import com.seeyuan.logistics.pullrefreshview.PullToRefreshListView;
import com.seeyuan.logistics.util.CommonUtils;
import com.seeyuan.logistics.util.ToastUtil;

/**
 * 搜索货源信息详情
 * 
 * @author Administrator
 * 
 */
@SuppressLint({ "NewApi", "UseValueOf" })
public class SearchGoodsInfoActivity extends BaseActivity implements
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
	 * 车型搜索
	 */
	private CheckBox GoodsSourceList_CarType;

	/**
	 * 车长搜索
	 */
	private CheckBox GoodsSourceList_CarLength;

	private Context context;

	private PullToRefreshListView goodsSourceList_List;

	private List<GoodsDto> mDataList;

	private ListView mListView;

	private GoodsSourceListAdapter mAdapter;

	private GoodsDto goodsDto;

	private SharedPreferences sPreferences;

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
	 * 显示进度条
	 */
	private final int SHOW_PROGRESS = 1000;
	/**
	 * 关闭进度条
	 */
	private final int CLOSE_PROGRESS = 1001;

	private ProgressAlertDialog progressDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.activity_search_goods_info); // 软件activity的布局
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,
				R.layout.deflaut_titlebar); // titlebar为自己标题栏的布局
		context = getApplicationContext();
		sPreferences = getSharedPreferences(ConstantPool.LOGISTICS_PREFERENCES,
				Context.MODE_PRIVATE);
		goodsDto = (GoodsDto) getIntent().getSerializableExtra("goodsDto");
		if (goodsDto == null) {
			goodsDto = new GoodsDto();
		}
		initView();
		initAdapter();
		doGetNewestGoodsInfoList();
		CommonUtils.addActivity(this);
	}

	@Override
	public void onClickListener(View view) {

	}

	@Override
	public void initView() {
		maintitle_back_iv = (ImageView) findViewById(R.id.maintitle_back_iv);
		maintitle_back_iv.setOnClickListener(this);

		defaulttitle_title_tv = (TextView) findViewById(R.id.defaulttitle_title_tv);
		defaulttitle_title_tv.setText(String.format(
				getResources().getString(R.string.GoodsSourceList_Title), 0));

		GoodsSourceList_CarType = (CheckBox) findViewById(R.id.GoodsSourceList_CarType);
		GoodsSourceList_CarType
				.setText(TextUtils.isEmpty(goodsDto.getVehType()) ? getResources()
						.getString(R.string.SearchCar_CarType_Hint) : goodsDto
						.getVehType());
		goodsDto.setVehType(TextUtils.isEmpty(goodsDto.getVehType()) ? ""
				: goodsDto.getVehType().equalsIgnoreCase("全部") ? "" : goodsDto
						.getVehType());

		GoodsSourceList_CarType.setOnClickListener(this);

		GoodsSourceList_CarLength = (CheckBox) findViewById(R.id.GoodsSourceList_CarLength);
		GoodsSourceList_CarLength.setText(TextUtils.isEmpty(goodsDto
				.getVehLegth()) ? getResources().getString(
				R.string.SearchCar_CarLength_Hint) : goodsDto.getVehLegth() + "米");
		GoodsSourceList_CarLength.setOnClickListener(this);
		goodsDto.setVehLegth(TextUtils.isEmpty(goodsDto.getVehLegth()) ? ""
				: goodsDto.getVehLegth().equalsIgnoreCase("全部") ? "" : goodsDto
						.getVehLegth());

	}

	private void initAdapter() {
		mDataList = new ArrayList<GoodsDto>();
		goodsSourceList_List = (PullToRefreshListView) findViewById(R.id.goodsSourceList_List);
		goodsSourceList_List.setOnRefreshListener(mOnrefreshListener);
		mListView = goodsSourceList_List.getRefreshableView();
		mAdapter = new GoodsSourceListAdapter(context, mDataList);
		mListView.setAdapter(mAdapter);
		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent intent = new Intent(SearchGoodsInfoActivity.this,
						GoodsSourceDetailActivity.class);
				intent.putExtra("GoodsSourceInfo", mDataList.get((int) id));
				startActivity(intent);
			}
		});

		defaulttitle_title_tv.setText(String.format(
				getResources().getString(R.string.GoodsSourceList_Title),
				mDataList.size()));
	}

	private Handler myHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case SHOW_PROGRESS:
				showProgress();
				break;
			case CLOSE_PROGRESS:
				goodsSourceList_List.onRefreshComplete();
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
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.maintitle_back_iv:
			if (!SearchGoodsInfoActivity.this.isFinishing()) {
				finish();
			}
			break;
		case R.id.GoodsSourceList_CarType:
			doCarType();
			break;
		case R.id.GoodsSourceList_CarLength:
			doCarLength();
			break;
		default:
			break;
		}
	}

	private void doCarLength() {
		isGetMoreData = false;
		final List<String> mDataList = new ArrayList<String>();
		TypedArray typedArray = getResources().obtainTypedArray(
				R.array.Search_car_length);
		int size = typedArray.length();
		for (int i = 0; i < size; i++) {
			mDataList.add(typedArray.getString(i).toString());
		}
		ListView listview = new ListView(this);
		SearchPopWindowAdapter adapter = new SearchPopWindowAdapter(mDataList,
				context);
		listview.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,
				LayoutParams.WRAP_CONTENT));
		// listview.setAdapter(new SearchPopWindowAdapter(mDataList, context));
		listview.setAdapter(adapter);

		// 新建一个popwindow，并显示里面的内容
		final PopupWindow popupWindow = makePopupWindow(context, listview);
		// int[] xy = new int[2];
		// GoodsSourceList_CarLength.getLocationOnScreen(xy);
		// popupWindow.showAtLocation(GoodsSourceList_CarLength, Gravity.RIGHT
		// | Gravity.TOP, xy[0] / 2,
		// xy[1] + GoodsSourceList_CarLength.getWidth());
		// popwindow与按钮之间的相对位置
		popupWindow.setOnDismissListener(new OnDismissListener() {

			@Override
			public void onDismiss() {
				GoodsSourceList_CarLength.setChecked(false);
			}
		});
		popupWindow.showAsDropDown(GoodsSourceList_CarLength, 2, 5);
		listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				String lengthString = mDataList.get((int)id).toString();
				GoodsSourceList_CarLength.setText(lengthString
						.equalsIgnoreCase("全部") ? mDataList.get((int)id)
						.toString() : lengthString + "米");
				goodsDto.setVehLegth(lengthString.equalsIgnoreCase("全部(单位.米)") ? ""
						: lengthString);
				pageNum = 0;
				popupWindow.dismiss();
				doGetNewestGoodsInfoList();
			}
		});
	}

	private void doCarType() {
		isGetMoreData = false;
		final List<String> mDataList = new ArrayList<String>();
		TypedArray typedArray = getResources().obtainTypedArray(
				R.array.CarType_Str);
		int size = typedArray.length();
		for (int i = 0; i < size; i++) {
			mDataList.add(typedArray.getString(i).toString());
		}
		ListView listview = new ListView(this);
		SearchPopWindowAdapter adapter = new SearchPopWindowAdapter(mDataList,
				context);
		listview.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,
				LayoutParams.WRAP_CONTENT));
		// listview.setAdapter(new SearchPopWindowAdapter(mDataList, context));
		listview.setAdapter(adapter);
		// 新建一个popwindow，并显示里面的内容
		final PopupWindow popupWindow = makePopupWindow(context, listview);
		// int[] xy = new int[2];
		// GoodsSourceList_CarType.getLocationOnScreen(xy);
		// popupWindow.showAtLocation(GoodsSourceList_CarType, Gravity.RIGHT
		// | Gravity.TOP, xy[0] / 2,
		// xy[1] + GoodsSourceList_CarType.getWidth());
		// popwindow与按钮之间的相对位置
		popupWindow.setOnDismissListener(new OnDismissListener() {

			@Override
			public void onDismiss() {
				GoodsSourceList_CarType.setChecked(false);
			}
		});
		popupWindow.showAsDropDown(GoodsSourceList_CarType, 2, 5);
		listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				String typeString = mDataList.get((int)id).toString();
				GoodsSourceList_CarType.setText(typeString);
				popupWindow.dismiss();
				goodsDto.setVehType(typeString.equalsIgnoreCase("全部") ? ""
						: typeString);
				pageNum = 0;
				doGetNewestGoodsInfoList();
			}
		});
	}

	// 创建一个包含自定义view的PopupWindow
	@SuppressWarnings("deprecation")
	private PopupWindow makePopupWindow(Context context, ListView listview) {
		PopupWindow window;
		window = new PopupWindow(context);
		LinearLayout linearLayout = new LinearLayout(this);
		linearLayout.addView(listview);
		linearLayout.setOrientation(LinearLayout.VERTICAL);

		window.setContentView(linearLayout); // 选择布局方式
		// 设置popwindow的背景图片
		// window.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_pop_pressed));
		// 设置popwindow的高和宽
		window.setWidth(GoodsSourceList_CarType.getWidth());
		window.setHeight(ApplicationPool.screenHeight >> 1);

		// 设置PopupWindow外部区域是否可触摸
		window.setFocusable(true); // 设置PopupWindow可获得焦点
		window.setTouchable(true); // 设置PopupWindow可触摸
		window.setOutsideTouchable(false); // 设置非PopupWindow区域可触摸
		return window;
	}

	/**
	 * 获取最新数据
	 */
	private void doGetNewestGoodsInfoList() {
		myHandler.sendEmptyMessage(SHOW_PROGRESS);
		PdaRequest<GoodsDto> goodsInfo = new PdaRequest<GoodsDto>();
		PdaPagination pagination = new PdaPagination();
		pagination.setStartPos(0);
		pagination.setAmount(pageSize);
		goodsInfo.setData(goodsDto);
		goodsInfo.setPagination(pagination);
		SearchGoodsHandler dataHandler = new SearchGoodsHandler(context,
				goodsInfo);
		dataHandler.setOnDataReceiveListener(this);
		dataHandler.startNetWork();
	}

	/**
	 * 再次获取
	 */
	private void doGetMoreGoodsInfo(int page) {
		PdaRequest<GoodsDto> goodsInfo = new PdaRequest<GoodsDto>();
		PdaPagination pagination = new PdaPagination();
		pagination.setStartPos(page);
		pagination.setAmount(pageSize);
		goodsInfo.setUuId(sPreferences.getString("uuId", "0"));
		goodsInfo.setData(goodsDto);
		goodsInfo.setPagination(pagination);
		SearchGoodsHandler dataHandler = new SearchGoodsHandler(context,
				goodsInfo);
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
				doGetNewestGoodsInfoList();
			} else if (scrollState == PullToRefreshBase.STATE_OF_FOOTER) {
				isGetMoreData = true;
				pageNum = pageNum + pageSize;
				Log.d("TAG", "pageNum = " + pageNum);
				if (pageNum < totalPage) {
					doGetMoreGoodsInfo(pageNum);
				} else {
					ToastUtil.show(context, "没有更多数据");
					goodsSourceList_List.onRefreshComplete();
				}
			}
		}
	};

	@Override
	public void onDataReceive(DataHandler dataHandler, int resultCode,
			Object data, int type) {
		try {
			String dataString = new String((byte[]) data, "UTF-8");
			Log.i("搜索", dataString.toString());
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		switch (resultCode) {
		case NetWork.SEACH_GOODS_OK:
			myHandler.sendEmptyMessage(CLOSE_PROGRESS);
			if (!isGetMoreData) {
				doSearchGoodsSuccess(data);
			} else {
				doGetmoreDataSuccess(data);
			}
			break;
		case NetWork.SEACH_GOODS_ERROR:
			myHandler.sendEmptyMessage(CLOSE_PROGRESS);
			ToastUtil.show(context, "搜索货源失败,请重新搜索");
			goodsSourceList_List.onRefreshComplete();
			break;

		default:
			break;
		}
	}

	/**
	 * 获取更多数据
	 * 
	 * @param data
	 */
	private void doGetmoreDataSuccess(Object data) {

		String dataString = null;
		try {
			dataString = new String((byte[]) data, "UTF-8");
		} catch (Exception e) {
		}
		try {
			PdaResponse<List<GoodsDto>> mData = SearchGoodsJsonParser
					.parserSearchGoodsJson(dataString);
			if (null == mData || null == mData.getData()) {
				ToastUtil.show(context, "搜索货源失败,请重新搜索");
				return;
			}
			for (GoodsDto goodsDto : mData.getData()) {
				mDataList.add(goodsDto);
			}
			mAdapter.notifyDataSetChanged();
			defaulttitle_title_tv.setText(String.format(getResources()
					.getString(R.string.GoodsSourceList_Title), mDataList
					.size()));
			goodsSourceList_List.onRefreshComplete();
		} catch (NotFoundException e) {
			e.printStackTrace();
			ToastUtil.show(context, "搜索货源失败,请重新搜索");
		}

	}

	/**
	 * 获取数据成功
	 * 
	 * @param data
	 */
	private void doSearchGoodsSuccess(Object data) {
		String dataString = null;
		try {
			dataString = new String((byte[]) data, "UTF-8");
		} catch (Exception e) {
		}
		try {
			PdaResponse<List<GoodsDto>> mData = SearchGoodsJsonParser
					.parserSearchGoodsJson(dataString);
			if (null == mData || null == mData.getData()) {
				ToastUtil.show(context, "搜索货源失败,请重新搜索");
				return;
			}
			mDataList = mData.getData();
			mAdapter = new GoodsSourceListAdapter(context, mDataList);
			mListView.setAdapter(mAdapter);
			totalPage = new Long(mData.getTotal()).intValue();
			defaulttitle_title_tv.setText(String.format(getResources()
					.getString(R.string.GoodsSourceList_Title), mDataList
					.size()));
			goodsSourceList_List.onRefreshComplete();
		} catch (NotFoundException e) {
			e.printStackTrace();
			ToastUtil.show(context, "搜索货源失败,请重新搜索");
		}
	}
}
