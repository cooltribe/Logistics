package com.seeyuan.logistics.map;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
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
import com.seeyuan.logistics.activity.ParkingLotActivity;
import com.seeyuan.logistics.application.ApplicationPool;
import com.seeyuan.logistics.entity.AroundCarInfo;
import com.seeyuan.logistics.entity.PoiInfoResult;
import com.seeyuan.logistics.util.BMapUtil;
import com.seeyuan.logistics.util.CommonUtils;

public class SearchGoodsMapActivity extends Activity implements OnClickListener {

	/**
	 * 返回按钮
	 */
	private ImageView maintitle_back_iv;

	/**
	 * 标题title
	 */
	private TextView defaulttitle_title_tv;
	private MapView mMapView;
	private EditText keyText;
	private View searchBtn;
	private ListView lv;
	private PoiInfoAdapter infoAdapter;

	private MapController mMapController;
	// 定位相关
	private LocationClient mLocClient;
	private LocationData locData = null;
	// 定位图层
	private MyLocationOverlay myLocationOverlay = null;
	public MySearchLocationListenner myListener = new MySearchLocationListenner();
	private MySearchListener mkSearchListener = new MySearchListener();
	MKSearch mSearch;
	ApplicationPool app;
	private ArrayList<GeoPoint> pointsList = new ArrayList<GeoPoint>();
	private String currentCity;
	private ArrayList<PoiInfoResult> poiInfoResults = new ArrayList<PoiInfoResult>();
	double targetLon;
	double targetLat;
	private AroundCarInfo aroundCarInfo;
	private MyOverlay mOverlay = null;
	private ArrayList<OverlayItem> mItems = null;
	private GroundOverlay mGroundOverlay;
	private View viewCache = null;
	private OverlayItem mCurItem = null;
	private TextView popupText = null;
	private PopupOverlay pop = null;
	private View popupInfo = null;
	// private View popupLeft = null;
	// private View popupRight = null;
	// private Button button = null;

	private boolean isAlwaysMoveToCurrentPosition = false;

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
		setContentView(R.layout.activity_map_searchgoods);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,
				R.layout.deflaut_titlebar); // titlebar为自己标题栏的布局
		initView();
		initMap();
		initOverlay();
	}

	private void initView() {
		maintitle_back_iv = (ImageView) findViewById(R.id.maintitle_back_iv);
		maintitle_back_iv.setOnClickListener(this);

		defaulttitle_title_tv = (TextView) findViewById(R.id.defaulttitle_title_tv);
		defaulttitle_title_tv.setText(R.string.tab_around_car);
		mMapView = (MapView) findViewById(R.id.searchMap);
		keyText = (EditText) findViewById(R.id.keySearch);
		searchBtn = findViewById(R.id.searchbtn);
		searchBtn.setOnClickListener(this);
		lv = (ListView) findViewById(R.id.addresslist);
		infoAdapter = new PoiInfoAdapter(SearchGoodsMapActivity.this,
				poiInfoResults);
		lv.setAdapter(infoAdapter);
	}

	private void initMap() {
		mMapView.setBuiltInZoomControls(true);
		mMapController = mMapView.getController();
		mMapController.setZoom(15);
		mMapController.enableClick(true);
		// 定位初始化
		mLocClient = new LocationClient(SearchGoodsMapActivity.this);
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
		// targetLat = getIntent().getExtras().getDouble("latitude");
		// targetLon = getIntent().getExtras().getDouble("longitude");
		aroundCarInfo = (AroundCarInfo) getIntent().getSerializableExtra(
				"aroundCarInfo");
		targetLat = aroundCarInfo.getLatitude();
		targetLon = aroundCarInfo.getLongitude();
		GeoPoint p1 = new GeoPoint((int) (targetLat * 1E6),
				(int) (targetLon * 1E6));
		OverlayItem item1 = new OverlayItem(p1, aroundCarInfo.getUsrName(), "");
		mSearch.reverseGeocode(p1);
		pointsList.add(p1);
		/**
		 * 设置overlay图标，如不设置，则使用创建ItemizedOverlay时的默认图标.
		 */
		item1.setMarker(getResources().getDrawable(R.drawable.icon_marka));
		/**
		 * 将item 添加到overlay中 注意： 同一个itme只能add一次
		 */
		mOverlay.addItem(item1);
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
		// BMapUtil.fitPoints(pointsList, mMapController);
	}

	@Override
	protected void onResume() {
		mMapView.onResume();
		// 注册定位监听
		mLocClient.registerLocationListener(myListener);
		mLocClient.requestLocation();
		mSearch.init(app.mBMapManager, mkSearchListener);
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
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.searchbtn:
			String key = keyText.getText().toString();
			if (!TextUtils.isEmpty(key)) {
				searchKey(key);
			} else {
				Toast.makeText(SearchGoodsMapActivity.this, "请填写地址",
						Toast.LENGTH_SHORT).show();
			}
			break;
		case R.id.maintitle_back_iv:
			CommonUtils.finishActivity(SearchGoodsMapActivity.this);
			break;
		}
	}

	private void searchKey(String key) {
		mSearch.poiSearchInCity(currentCity, key);
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
				BMapUtil.fitPoints(pointsList, mMapController);
			}

		}

		public void onReceivePoi(BDLocation poiLocation) {
			if (poiLocation == null) {
				return;
			}
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
				Toast.makeText(SearchGoodsMapActivity.this, "抱歉，未找到结果",
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
			infoAdapter.setData(poiInfoResults);
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

	/**
	 * 自定义百度地图关键字查询结果adapter
	 * 
	 * @author
	 * 
	 */
	class PoiInfoAdapter extends BaseAdapter {
		private Context mContext;
		private ArrayList<PoiInfoResult> infoResult;

		public PoiInfoAdapter(Context ctx, ArrayList<PoiInfoResult> result) {
			this.mContext = ctx;
			this.infoResult = result;
		}

		@Override
		public int getCount() {
			return infoResult.size();
		}

		@Override
		public Object getItem(int position) {
			return infoResult.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		public void setData(ArrayList<PoiInfoResult> data) {
			this.infoResult = data;
			notifyDataSetChanged();
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder;
			if (convertView == null) {
				convertView = LayoutInflater.from(mContext).inflate(
						R.layout.poi_search_item, null);
				holder = new ViewHolder();
				holder.name = (TextView) convertView
						.findViewById(R.id.poi_name);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}

			holder.name.setText(infoResult.get(position).name);
			return convertView;
		}

		class ViewHolder {
			TextView name;
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
			// Bitmap[] bitMaps = { BMapUtil.getBitmapFromView(popupLeft),
			// BMapUtil.getBitmapFromView(popupInfo),
			// BMapUtil.getBitmapFromView(popupRight) };
			pop.showPopup(popupInfo, item.getPoint(), 32);
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
}
