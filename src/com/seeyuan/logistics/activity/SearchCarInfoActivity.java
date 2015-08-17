package com.seeyuan.logistics.activity;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.ActionBar.LayoutParams;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources.NotFoundException;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
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

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.BMapManager;
import com.baidu.mapapi.map.GroundOverlay;
import com.baidu.mapapi.map.ItemizedOverlay;
import com.baidu.mapapi.map.LocationData;
import com.baidu.mapapi.map.MapController;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationOverlay;
import com.baidu.mapapi.map.OverlayItem;
import com.baidu.mapapi.map.PopupClickListener;
import com.baidu.mapapi.map.PopupOverlay;
import com.baidu.mapapi.search.MKSearch;
import com.baidu.platform.comapi.basestruct.GeoPoint;
import com.seeyuan.logistics.R;
import com.seeyuan.logistics.adapter.CarSourceListAdapter;
import com.seeyuan.logistics.adapter.GoodsSourceListAdapter;
import com.seeyuan.logistics.adapter.SearchPopWindowAdapter;
import com.seeyuan.logistics.application.ApplicationPool;
import com.seeyuan.logistics.application.ConstantPool;
import com.seeyuan.logistics.application.NetWork;
import com.seeyuan.logistics.customview.ProgressAlertDialog;
import com.seeyuan.logistics.datacenter.DataHandler;
import com.seeyuan.logistics.datacenter.OnDataReceiveListener;
import com.seeyuan.logistics.datahandler.SearchCarHandler;
import com.seeyuan.logistics.datahandler.SearchGoodsHandler;
import com.seeyuan.logistics.entity.CarSourceInfo;
import com.seeyuan.logistics.entity.GoodsDto;
import com.seeyuan.logistics.entity.PdaPagination;
import com.seeyuan.logistics.entity.PdaRequest;
import com.seeyuan.logistics.entity.PdaResponse;
import com.seeyuan.logistics.entity.RouteDto;
import com.seeyuan.logistics.jsonparser.CarJsonParser;
import com.seeyuan.logistics.jsonparser.SearchGoodsJsonParser;
import com.seeyuan.logistics.pullrefreshview.PullToRefreshBase;
import com.seeyuan.logistics.pullrefreshview.PullToRefreshBase.OnRefreshListener;
import com.seeyuan.logistics.pullrefreshview.PullToRefreshListView;
import com.seeyuan.logistics.util.BMapUtil;
import com.seeyuan.logistics.util.CommonUtils;
import com.seeyuan.logistics.util.ToastUtil;

/**
 * 搜索车源详细信息
 * 
 * @author Administrator
 * 
 */
@SuppressLint("NewApi")
public class SearchCarInfoActivity extends BaseActivity implements
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
	 * 确定按钮。
	 */
	private ImageView maintitle_comfirm_iv;

	/**
	 * 车型搜索
	 */
	private CheckBox CarSourceList_CarType;

	/**
	 * 车长搜索
	 */
	private CheckBox CarSourceList_CarLength;

	private Context context;

	private PullToRefreshListView carSourceList_List;

	private List<RouteDto> mDataList;

	private CarSourceListAdapter mAdapter;

	private ListView mListView;

	private MapController mMapController;
	// 定位相关
	private LocationClient mLocClient;
	private LocationData locData = null;
	// 定位图层
	private MyLocationOverlay myLocationOverlay = null;
	public MySearchLocationListenner myListener = new MySearchLocationListenner();
	private MKSearch mSearch;
	private MapView mMapView;
	private boolean isAlwaysMoveToCurrentPosition = false;
	private ApplicationPool app;
	private MyOverlay mOverlay = null;
	private ArrayList<GeoPoint> pointsList = new ArrayList<GeoPoint>();
	private OverlayItem mCurItem = null;
	private ArrayList<OverlayItem> mItems = null;
	private GroundOverlay mGroundOverlay;
	private View viewCache = null;
	private View popupInfo = null;
	// private View popupLeft = null;
	// private View popupRight = null;
	// private Button button = null;
	private TextView popupText = null;
	private PopupOverlay pop = null;

	private RouteDto routeDto;

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
		setContentView(R.layout.activity_search_car_info); // 软件activity的布局
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,
				R.layout.deflaut_titlebar); // titlebar为自己标题栏的布局
		/**
		 * 使用地图sdk前需先初始化BMapManager. BMapManager是全局的，可为多个MapView共用，它需要地图模块创建前创建，
		 * 并在地图地图模块销毁后销毁，只要还有地图模块在使用，BMapManager就不应该销毁
		 */
		app = (ApplicationPool) this.getApplication();

		if (app.mBMapManager == null) {
			app.mBMapManager = new BMapManager(getApplicationContext());
			/**
			 * 如果BMapManager没有初始化则初始化BMapManager
			 */
			app.mBMapManager.init(new ApplicationPool.MyGeneralListener());
		}
		sPreferences = getSharedPreferences(ConstantPool.LOGISTICS_PREFERENCES,
				Context.MODE_PRIVATE);
		context = getApplicationContext();
		routeDto = (RouteDto) getIntent().getSerializableExtra("routeDto");
		if (routeDto == null) {
			routeDto = new RouteDto();
		}
		initView();
		initAdapter();
		initMap();
		// initOverlay();
		myHandler.sendEmptyMessage(SHOW_PROGRESS);
		doGetNewestCarInfoList();
		CommonUtils.addActivity(this);
	}

	@Override
	public void initView() {
		maintitle_back_iv = (ImageView) findViewById(R.id.maintitle_back_iv);
		maintitle_back_iv.setOnClickListener(this);

		defaulttitle_title_tv = (TextView) findViewById(R.id.defaulttitle_title_tv);
		defaulttitle_title_tv.setText(String.format(
				getResources().getString(R.string.CarSourceList_Title), 0));

		CarSourceList_CarType = (CheckBox) findViewById(R.id.CarSourceList_CarType);
		CarSourceList_CarType
				.setText(TextUtils.isEmpty(routeDto.getVehType()) ? getResources()
						.getString(R.string.SearchCar_CarType_Hint) : routeDto
						.getVehType());
		routeDto.setVehType((null == routeDto.getVehType()) ||routeDto.getVehType().equalsIgnoreCase("全部") ? ""
				: routeDto.getVehType());
		CarSourceList_CarType.setOnClickListener(this);

		CarSourceList_CarLength = (CheckBox) findViewById(R.id.CarSourceList_CarLength);
		CarSourceList_CarLength.setText(TextUtils.isEmpty(routeDto
				.getVehLegth()) ? getResources().getString(
				R.string.SearchCar_CarLength_Hint) : routeDto.getVehLegth()
				+ "米");
		routeDto.setVehLegth(null == routeDto.getVehLegth() || routeDto.getVehLegth().equalsIgnoreCase("全部") ? ""
				: routeDto.getVehLegth());
		CarSourceList_CarLength.setOnClickListener(this);
		maintitle_comfirm_iv = (ImageView) findViewById(R.id.maintitle_comfirm_iv);
		maintitle_comfirm_iv.setVisibility(View.VISIBLE);
		maintitle_comfirm_iv
				.setBackgroundResource(R.drawable.default_title_map);
		maintitle_comfirm_iv.setOnClickListener(this);

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
			if (!SearchCarInfoActivity.this.isFinishing()) {
				finish();
			}
			break;
		case R.id.CarSourceList_CarType:
			doCarType();
			break;
		case R.id.CarSourceList_CarLength:
			doCarLength();
			break;
		case R.id.maintitle_comfirm_iv:
			doComfirm();
			break;
		default:
			break;
		}
	}

	@Override
	public void onClickListener(View view) {

	}

	/**
	 * 地图，列表切换
	 */
	private void doComfirm() {

		switch (mMapView.getVisibility()) {
		case View.VISIBLE:
			mMapView.setVisibility(View.GONE);
			carSourceList_List.setVisibility(View.VISIBLE);
			break;
		case View.GONE:
			mMapView.setVisibility(View.VISIBLE);
			carSourceList_List.setVisibility(View.GONE);
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
		typedArray.recycle();
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
				CarSourceList_CarLength.setChecked(false);
			}
		});
		popupWindow.showAsDropDown(CarSourceList_CarLength, 2, 5);
		listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				String lengthString = mDataList.get((int)id).toString();
				CarSourceList_CarLength.setText(lengthString
						.equalsIgnoreCase("全部") ? mDataList.get((int)id)
						.toString() : lengthString + "米");
				routeDto.setVehLegth(lengthString.equalsIgnoreCase("全部") ? ""
						: mDataList.get((int)id).toString());
				popupWindow.dismiss();
				doGetNewestCarInfoList();
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
		typedArray.recycle();
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
				CarSourceList_CarType.setChecked(false);
			}
		});
		popupWindow.showAsDropDown(CarSourceList_CarType, 2, 5);
		listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				String typeString = mDataList.get((int)id).toString();
				CarSourceList_CarType.setText(typeString);
				routeDto.setVehType(typeString.equalsIgnoreCase("全部") ? ""
						: typeString);
				popupWindow.dismiss();
				doGetNewestCarInfoList();
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
		window.setWidth(CarSourceList_CarType.getWidth());
		window.setHeight(ApplicationPool.screenHeight >> 1);

		// 设置PopupWindow外部区域是否可触摸
		window.setFocusable(true); // 设置PopupWindow可获得焦点
		window.setTouchable(true); // 设置PopupWindow可触摸
		window.setOutsideTouchable(false); // 设置非PopupWindow区域可触摸
		return window;
	}

	private void initAdapter() {
		mDataList = new ArrayList<RouteDto>();
		carSourceList_List = (PullToRefreshListView) findViewById(R.id.carSourceList_List);
		carSourceList_List.setOnRefreshListener(mOnrefreshListener);
		mListView = carSourceList_List.getRefreshableView();
		mAdapter = new CarSourceListAdapter(context, mDataList);
		mListView.setAdapter(mAdapter);
		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent intent = new Intent(SearchCarInfoActivity.this,
						CarSourceDetailActivity.class);
				intent.putExtra("carSourceInfo", mDataList.get((int)id));
				startActivity(intent);
			}
		});

		defaulttitle_title_tv.setText(String.format(
				getResources().getString(R.string.CarSourceList_Title),
				mDataList.size()));
	}

	/**
	 * 获取最新数据
	 */
	private void doGetNewestCarInfoList() {
		PdaRequest<RouteDto> goodsInfo = new PdaRequest<RouteDto>();
		PdaPagination pagination = new PdaPagination();
		pagination.setStartPos(0);
		pagination.setAmount(pageSize);
		goodsInfo.setUuId(sPreferences.getString("uuId", "0"));
		goodsInfo.setData(routeDto);
		goodsInfo.setPagination(pagination);
		SearchCarHandler dataHandler = new SearchCarHandler(context, goodsInfo);
		dataHandler.setOnDataReceiveListener(this);
		dataHandler.startNetWork();
	}

	private void initMap() {
		mMapView = (MapView) findViewById(R.id.car_source_list_map);
		mMapView.setBuiltInZoomControls(true);
		mMapController = mMapView.getController();
		mMapController.setZoom(15);
		mMapController.enableClick(true);
		// 定位初始化
		mLocClient = new LocationClient(SearchCarInfoActivity.this);
		locData = new LocationData();

		LocationClientOption option = new LocationClientOption();
		option.setOpenGps(true);// 打开gps
		option.setCoorType("bd09ll"); // 设置坐标类型
		option.setScanSpan(5000);
		mLocClient.setLocOption(option);
		mLocClient.start();

		// 初始化搜索模块，注册事件监听
		mSearch = new MKSearch();
		// 定位图层初始化
		myLocationOverlay = new MyLocationOverlay(mMapView);
		// 设置定位数据
		myLocationOverlay.setData(locData);
		// 添加定位图层
		mMapView.getOverlays().add(myLocationOverlay);
		myLocationOverlay.enableCompass();

		mMapView.refresh();
	}

	private void initOverlay() {
		/**
		 * 创建自定义overlay
		 */
		mOverlay = new MyOverlay(getResources().getDrawable(
				R.drawable.icon_marka), mMapView);
		/**
		 * 准备overlay 数据
		 */

		int friendsSize = mDataList.size();
		for (int i = 0; i < friendsSize; i++) {
			double lat = mDataList.get(i).getCarLatitude();
			double lon = mDataList.get(i).getCarLongilation();
			GeoPoint point = new GeoPoint((int) (lat * 1E6), (int) (lon * 1E6));
			pointsList.add(point);
			OverlayItem item = new OverlayItem(point, mDataList.get(i)
					.getUserName(), "");
			item.setMarker(getResources().getDrawable(R.drawable.icon_marka));
			mSearch.reverseGeocode(point);
			mOverlay.addItem(item);
		}
		// targetLat = getIntent().getExtras().getDouble("latitude");
		// targetLon = getIntent().getExtras().getDouble("longitude");
		// GeoPoint p1 = new GeoPoint((int) (targetLat * 1E6),
		// (int) (targetLon * 1E6));
		// OverlayItem item1 = new OverlayItem(p1, "覆盖物1", "");
		// mSearch.reverseGeocode(p1);
		/**
		 * 设置overlay图标，如不设置，则使用创建ItemizedOverlay时的默认图标.
		 */
		// item1.setMarker(getResources().getDrawable(R.drawable.icon_marka));
		/**
		 * 将item 添加到overlay中 注意： 同一个itme只能add一次
		 */
		// mOverlay.addItem(item1);
		/**
		 * 保存所有item，以便overlay在reset后重新添加
		 */
		mItems = new ArrayList<OverlayItem>();
		mItems.addAll(mOverlay.getAllItem());

		// 初始化 ground 图层
		mGroundOverlay = new GroundOverlay(mMapView);

		/**
		 * 将overlay 添加至MapView中
		 */
		mMapView.getOverlays().add(mOverlay);
		mMapView.getOverlays().add(mGroundOverlay);
		/**
		 * 刷新地图
		 */
		mMapView.refresh();
		/**
		 * 向地图添加自定义View.
		 */
		viewCache = getLayoutInflater().inflate(R.layout.bd_custom_text_view,
				null);
		popupInfo = (View) viewCache.findViewById(R.id.popinfo);
		// popupLeft = (View) viewCache.findViewById(R.id.popleft);
		// popupRight = (View) viewCache.findViewById(R.id.popright);
		popupText = (TextView) viewCache.findViewById(R.id.textcache);

		// button = new Button(this);
		// button.setBackgroundResource(R.drawable.popup);

		/**
		 * 创建一个popupoverlay
		 */
		PopupClickListener popListener = new PopupClickListener() {
			@Override
			public void onClickedPopup(int index) {
				if (index == 0) {
					// 更新item位置
					pop.hidePop();
					GeoPoint p = new GeoPoint(mCurItem.getPoint()
							.getLatitudeE6() + 5000, mCurItem.getPoint()
							.getLongitudeE6() + 5000);
					mCurItem.setGeoPoint(p);
					mOverlay.updateItem(mCurItem);
					mMapView.refresh();
				} else if (index == 2) {
					// 更新图标
					mCurItem.setMarker(getResources().getDrawable(
							R.drawable.nav_turn_via_1));
					mOverlay.updateItem(mCurItem);
					mMapView.refresh();
				}
			}
		};
		pop = new PopupOverlay(mMapView, popListener);

		BMapUtil.fitPoints(pointsList, mMapController);
	}

	OnRefreshListener mOnrefreshListener = new OnRefreshListener() {

		@Override
		public void onRefresh(int scrollState) {
			// TODO Auto-generated method stub
			if (scrollState == PullToRefreshBase.STATE_OF_HEADER) {
				pageNum = 0;
				isGetMoreData = false;
				doGetNewestCarInfoList();
			} else if (scrollState == PullToRefreshBase.STATE_OF_FOOTER) {
				isGetMoreData = true;
				pageNum = pageNum + pageSize;
				if (pageNum < totalPage) {
					doGetMoreCarInfo(pageNum);
				} else {
					ToastUtil.show(context, "没有更多数据");
					carSourceList_List.onRefreshComplete();
				}
			}
		}
	};

	@Override
	public void onDataReceive(DataHandler dataHandler, int resultCode,
			Object data, int type) {
		switch (resultCode) {
		case NetWork.SEACH_CAR_SOURCE_OK:
			myHandler.sendEmptyMessage(CLOSE_PROGRESS);
			if (!isGetMoreData) {
				doSearchGoodsSuccess(data);
			} else {
				doGetmoreDataSuccess(data);
			}
			break;
		case NetWork.SEACH_CAR_SOURCE_ERROR:
			myHandler.sendEmptyMessage(CLOSE_PROGRESS);
			carSourceList_List.onRefreshComplete();
			ToastUtil.show(context, "搜索车源失败,请重新搜索");
			break;

		default:
			break;
		}
	}

	/**
	 * 定位SDK监听函数
	 */
	public class MySearchLocationListenner implements BDLocationListener {

		@Override
		public void onReceiveLocation(BDLocation location) {
			if (location == null)
				return;
			locData.latitude = location.getLatitude();
			locData.longitude = location.getLongitude();
			// 如果不显示定位精度圈，将accuracy赋值为0即可
			// locData.accuracy = location.getRadius();
			locData.accuracy = 0;
			locData.direction = location.getDerect();
			// 更新定位数据
			myLocationOverlay.setData(locData);
			// 更新图层数据执行刷新后生效
			mMapView.refresh();
			// 是手动触发请求或首次定位时，移动到定位点
			GeoPoint loc_GeoPoint = new GeoPoint(
					(int) (locData.latitude * 1e6),
					(int) (locData.longitude * 1e6));

			if (!isAlwaysMoveToCurrentPosition) {
				isAlwaysMoveToCurrentPosition = true;
				mSearch.reverseGeocode(loc_GeoPoint);
				// 移动地图到定位点
				mMapController.animateTo(loc_GeoPoint);
			}

		}

		public void onReceivePoi(BDLocation poiLocation) {
			if (poiLocation == null) {
				return;
			}
		}
	}

	@Override
	protected void onDestroy() {
		// 退出时销毁定位
		if (mLocClient != null)
			mLocClient.stop();
		mMapView.destroy();
		mSearch.destory();
		super.onDestroy();
	}

	@Override
	protected void onResume() {
		mMapView.onResume();
		// 注册定位监听
		mLocClient.registerLocationListener(myListener);
		mLocClient.requestLocation();
		super.onResume();
	}

	@Override
	protected void onPause() {
		mMapView.onPause();
		mLocClient.unRegisterLocationListener(myListener);
		super.onPause();
	}

	public class MyOverlay extends ItemizedOverlay {

		public MyOverlay(Drawable defaultMarker, MapView mapView) {
			super(defaultMarker, mapView);
		}

		@Override
		public boolean onTap(int index) {
			OverlayItem item = getItem(index);
			mCurItem = item;
			popupText.setText(getItem(index).getTitle());
			// Bitmap[] bitMaps = { BMapUtil.getBitmapFromView(popupLeft),
			// BMapUtil.getBitmapFromView(popupInfo),
			// BMapUtil.getBitmapFromView(popupRight) };
			pop.showPopup(popupInfo, item.getPoint(), 45);
			return true;
		}

		@Override
		public boolean onTap(GeoPoint pt, MapView mMapView) {
			if (pop != null) {
				pop.hidePop();
				// mMapView.removeView(button);
			}
			return false;
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
		if (TextUtils.isEmpty(dataString))
			return;
		try {
			PdaResponse<List<RouteDto>> mData = CarJsonParser
					.parserSearchCarJson(dataString);
			if (null == mData || null == mData.getData()) {
				ToastUtil.show(context, "搜索车源失败,请重新搜索");
				return;
			}
			mDataList = mData.getData();
			mAdapter = new CarSourceListAdapter(context, mDataList);
			mListView.setAdapter(mAdapter);
			totalPage = new Long(mData.getTotal()).intValue();
			defaulttitle_title_tv
					.setText(String.format(
							getResources().getString(
									R.string.CarSourceList_Title),
							mDataList.size()));
			carSourceList_List.onRefreshComplete();
		} catch (NotFoundException e) {
			e.printStackTrace();
			ToastUtil.show(context, "搜索车源失败,请重新搜索");
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
			PdaResponse<List<RouteDto>> mData = CarJsonParser
					.parserSearchCarJson(dataString);
			if (null == mData || null == mData.getData()) {
				ToastUtil.show(context, "搜索车源失败,请重新搜索");
				return;
			}
			for (RouteDto routeDto : mData.getData()) {
				mDataList.add(routeDto);
			}
			mAdapter.notifyDataSetChanged();
			defaulttitle_title_tv
					.setText(String.format(
							getResources().getString(
									R.string.CarSourceList_Title),
							mDataList.size()));
			carSourceList_List.onRefreshComplete();
		} catch (NotFoundException e) {
			e.printStackTrace();
			ToastUtil.show(context, "搜索车源失败,请重新搜索");
		}

	}

	/**
	 * 再次获取
	 */
	private void doGetMoreCarInfo(int page) {
		PdaRequest<RouteDto> routeDto = new PdaRequest<RouteDto>();
		PdaPagination pagination = new PdaPagination();
		pagination.setStartPos(page);
		pagination.setAmount(pageSize);
		routeDto.setUuId(sPreferences.getString("uuId", "0"));
		routeDto.setData(this.routeDto);
		routeDto.setPagination(pagination);
		SearchCarHandler dataHandler = new SearchCarHandler(context, routeDto);
		dataHandler.setOnDataReceiveListener(this);
		dataHandler.startNetWork();
	}
}
