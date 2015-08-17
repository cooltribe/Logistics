package com.seeyuan.logistics.activity;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
import com.baidu.mapapi.search.MKAddrInfo;
import com.baidu.mapapi.search.MKBusLineResult;
import com.baidu.mapapi.search.MKDrivingRouteResult;
import com.baidu.mapapi.search.MKPoiInfo;
import com.baidu.mapapi.search.MKPoiResult;
import com.baidu.mapapi.search.MKSearch;
import com.baidu.mapapi.search.MKSearchListener;
import com.baidu.mapapi.search.MKShareUrlResult;
import com.baidu.mapapi.search.MKSuggestionResult;
import com.baidu.mapapi.search.MKTransitRouteResult;
import com.baidu.mapapi.search.MKWalkingRouteResult;
import com.baidu.platform.comapi.basestruct.GeoPoint;
import com.seeyuan.logistics.R;
import com.seeyuan.logistics.application.ApplicationPool;
import com.seeyuan.logistics.application.NetWork;
import com.seeyuan.logistics.datacenter.DataHandler;
import com.seeyuan.logistics.datacenter.OnDataReceiveListener;
import com.seeyuan.logistics.datahandler.GasStationHandler;
import com.seeyuan.logistics.entity.GasStationInfo;
import com.seeyuan.logistics.entity.PoiInfoResult;
import com.seeyuan.logistics.jsonparser.GasStationJsonParser;
import com.seeyuan.logistics.util.BMapUtil;
import com.seeyuan.logistics.util.CommonUtils;
import com.seeyuan.logistics.util.ToastUtil;

/**
 * 加油站
 * 
 * @author zhazhaobao
 * 
 */
public class GasStationActivity extends BaseActivity implements
		OnClickListener, OnDataReceiveListener {

	/**
	 * 返回按钮
	 */
	private ImageView maintitle_back_iv;

	/**
	 * 标题title
	 */
	private TextView defaulttitle_title_tv;

	private ImageView searchGasStation;

	private MapController mMapController;

	private MapView mMapView;
	// 定位相关
	private LocationClient mLocClient;
	private LocationData locData = null;
	// 定位图层
	private MyLocationOverlay myLocationOverlay = null;
	public MySearchLocationListenner myListener = new MySearchLocationListenner();
	MKSearch mSearch;
	ApplicationPool app;

	private MyOverlay mOverlay = null;
	private ArrayList<OverlayItem> mItems = null;
	private GroundOverlay mGroundOverlay;
	private View viewCache = null;
	private OverlayItem mCurItem = null;
	private TextView popupText = null;
	private PopupOverlay pop = null;
	private ArrayList<GeoPoint> pointsList = new ArrayList<GeoPoint>();
	private View popupInfo = null;
	// private View popupLeft = null;
	// private View popupRight = null;
	// private Button button = null;

	private boolean isAlwaysMoveToCurrentPosition = false;
	private boolean isInitOverlayOnce = false;

	private Context context;
	private String currentCity;
	private GeoPoint currentPoint;
	private ArrayList<PoiInfoResult> poiInfoResults = new ArrayList<PoiInfoResult>();
	private MySearchListener mkSearchListener = new MySearchListener();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
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
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.activity_gas_station); // 软件activity的布局
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,
				R.layout.deflaut_titlebar); // titlebar为自己标题栏的布局
		context = getApplicationContext();
		initView();
		initMap();
		// initOverlay();
	}

	@Override
	public void initView() {
		maintitle_back_iv = (ImageView) findViewById(R.id.maintitle_back_iv);
		maintitle_back_iv.setOnClickListener(this);

		defaulttitle_title_tv = (TextView) findViewById(R.id.defaulttitle_title_tv);
		defaulttitle_title_tv.setText(R.string.gas_station_hint);

		searchGasStation = (ImageView) findViewById(R.id.maintitle_comfirm_iv);
		searchGasStation.setBackgroundResource(R.drawable.icon_4_n);
		// searchGasStation.setVisibility(View.VISIBLE);
		searchGasStation.setOnClickListener(this);

		mMapView = (MapView) findViewById(R.id.map_gas_station);

	}

	private void initMap() {
		mMapController = mMapView.getController();
		mMapController.setZoom(15);
		mMapController.enableClick(true);
		// 定位初始化
		mLocClient = new LocationClient(GasStationActivity.this);
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
		int size = poiInfoResults.size();
		for (int i = 0; i < size; i++) {
			GeoPoint geoPoint = poiInfoResults.get(i).pt;
			pointsList.add(geoPoint);
			OverlayItem item = new OverlayItem(geoPoint,
					poiInfoResults.get(i).address, "");
			item.setMarker(getResources().getDrawable(R.drawable.icon_marka));
			mSearch.reverseGeocode(geoPoint);
			mOverlay.addItem(item);
		}
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

	/**
	 * 获取周边加油站信息
	 */
	private void doGetGasStationInfo() {
		GasStationHandler dataHandler = new GasStationHandler(context, null);
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
			CommonUtils.finishActivity(GasStationActivity.this);
			break;
		case R.id.maintitle_comfirm_iv:
			doSearchGasStation();
			break;

		default:
			break;
		}
	}

	private void doSearchGasStation() {
		if (TextUtils.isEmpty(currentCity)) {
			ToastUtil.show(context, "地图正在加载,请稍后再试");
		} else {
			// mSearch.poiSearchInCity(currentCity, "加油站");
			mSearch.poiSearchNearBy("加油站", currentPoint, 3000);
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
				pointsList.add(loc_GeoPoint);
			}

		}

		public void onReceivePoi(BDLocation poiLocation) {
			if (poiLocation == null) {
				return;
			}
		}
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
			Bitmap[] bitMaps = { BMapUtil.getBitmapFromView(popupInfo) };
			// pop.showPopup(bitMaps, item.getPoint(), 45);
			pop.showPopup(popupInfo, item.getPoint(), popupInfo.getHeight() / 2);
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

	@Override
	protected void onResume() {
		mMapView.onResume();
		// 注册定位监听
		mLocClient.registerLocationListener(myListener);
		mLocClient.requestLocation();
		mSearch.init(app.mBMapManager, mkSearchListener);
		// doGetGasStationInfo();

		super.onResume();
	}

	@Override
	protected void onPause() {
		mMapView.onPause();
		mLocClient.unRegisterLocationListener(myListener);
		super.onPause();
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
	public void onDataReceive(DataHandler dataHandler, int resultCode,
			Object data, int type) {
		switch (resultCode) {
		case NetWork.GAS_STATION_SOURCE_OK:
			doParserGasStation(data);
			break;
		case NetWork.GAS_STATION_SOURCE_ERROR:

			break;

		default:
			break;
		}
	}

	private void doParserGasStation(Object data) {
		String dataString = null;
		try {
			dataString = new String((byte[]) data, "UTF-8");
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		Log.d("TAG", "dataString = " + dataString);
		try {
			List<GasStationInfo> infoList = (List<GasStationInfo>) GasStationJsonParser
					.parserGasStation(dataString);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	/** 自定义搜索结果通知接口 */
	class MySearchListener implements MKSearchListener {

		@Override
		public void onGetAddrResult(MKAddrInfo info, int arg1) {
			if (info == null)
				return;
			currentCity = null;
			currentCity = info.addressComponents.city;
			currentPoint = info.geoPt;
			if (!isInitOverlayOnce) {
				isInitOverlayOnce = true;
				mSearch.poiSearchNearBy("加油站", currentPoint, 3000);
			}
		}

		@Override
		public void onGetBusDetailResult(MKBusLineResult arg0, int arg1) {

		}

		@Override
		public void onGetDrivingRouteResult(MKDrivingRouteResult arg0, int arg1) {

		}

		@Override
		public void onGetPoiDetailSearchResult(int arg0, int arg1) {

		}

		@Override
		public void onGetPoiResult(MKPoiResult result, int type, int error) {
			// 错误号可参考MKEvent中的定义
			if (error != 0 || result == null) {
				Toast.makeText(GasStationActivity.this, "抱歉，未找到结果",
						Toast.LENGTH_LONG).show();
				return;
			}
			poiInfoResults.clear();
			for (MKPoiInfo info : result.getAllPoi()) {
				if (info.pt != null) {
					PoiInfoResult infoResult = new PoiInfoResult();
					infoResult.name = info.name;
					infoResult.address = info.address;
					infoResult.pt = info.pt;
					infoResult.city = info.city;
					poiInfoResults.add(infoResult);
				}
			}
			initOverlay();
		}

		@Override
		public void onGetShareUrlResult(MKShareUrlResult arg0, int arg1,
				int arg2) {

		}

		@Override
		public void onGetSuggestionResult(MKSuggestionResult arg0, int arg1) {

		}

		@Override
		public void onGetTransitRouteResult(MKTransitRouteResult arg0, int arg1) {

		}

		@Override
		public void onGetWalkingRouteResult(MKWalkingRouteResult arg0, int arg1) {

		}

	}

	class MyCount extends CountDownTimer {

		public MyCount(long millisInFuture, long countDownInterval) {

			super(millisInFuture, countDownInterval);

		}

		@Override
		public void onFinish() {

		}

		@Override
		public void onTick(long millisUntilFinished) {

		}

	}

}
