package com.seeyuan.logistics.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.IBinder;
import android.util.Log;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.BMapManager;
import com.baidu.mapapi.search.MKAddrInfo;
import com.baidu.mapapi.search.MKBusLineResult;
import com.baidu.mapapi.search.MKDrivingRouteResult;
import com.baidu.mapapi.search.MKPoiResult;
import com.baidu.mapapi.search.MKSearch;
import com.baidu.mapapi.search.MKSearchListener;
import com.baidu.mapapi.search.MKShareUrlResult;
import com.baidu.mapapi.search.MKSuggestionResult;
import com.baidu.mapapi.search.MKTransitRouteResult;
import com.baidu.mapapi.search.MKWalkingRouteResult;
import com.baidu.platform.comapi.basestruct.GeoPoint;
import com.seeyuan.logistics.application.ApplicationPool;
import com.seeyuan.logistics.application.ConstantPool;
import com.seeyuan.logistics.application.NetWork;
import com.seeyuan.logistics.datacenter.DataHandler;
import com.seeyuan.logistics.datacenter.OnDataReceiveListener;
import com.seeyuan.logistics.datahandler.SubmitPersonalPositionHandler;
import com.seeyuan.logistics.entity.MemberDto;
import com.seeyuan.logistics.entity.PdaRequest;

public class BDLocationService extends Service implements OnDataReceiveListener {

	private ApplicationPool app;
	private Context context;
	private SharedPreferences sPreferences;
	private LocationClient mLocClient;
	private DataLocationListenner myListener = new DataLocationListenner();
	private double latitude = 0;
	private double longitude = 0;
	private MySearchListener mkSearchListener = new MySearchListener();
	private MKSearch mSearch;

	/**
	 * 省
	 */
	private String province;

	/**
	 * 市
	 */
	private String city;

	/**
	 * 区域
	 */
	private String district;

	/**
	 * 详细地址
	 */
	private String street;

	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		Log.d("TAG", "启动service");
		context = getApplicationContext();
		sPreferences = getSharedPreferences(ConstantPool.LOGISTICS_PREFERENCES,
				Context.MODE_PRIVATE);
		initMap();
	}

	@Override
	public void onDestroy() {
		Log.d("TAG", "关闭service");
		if (mLocClient != null) {
			mLocClient.stop();
		}
		mSearch.destory();
		super.onDestroy();
	}

	@Override
	public void onStart(Intent intent, int startId) {
		super.onStart(intent, startId);
	}

	private void initMap() {
		app = (ApplicationPool) this.getApplication();
		if (app.mBMapManager == null) {
			app.mBMapManager = new BMapManager(getApplicationContext());
			app.mBMapManager.init(new ApplicationPool.MyGeneralListener());
		}
		// 初始化搜索模块，注册事件监听
		mSearch = new MKSearch();
		// 定位初始化
		mLocClient = new LocationClient(getApplicationContext());
		LocationClientOption option = new LocationClientOption();
		option.setOpenGps(true);// 打开gps
		option.setCoorType("bd09ll"); // 设置坐标类型
		option.setScanSpan(600000);// 10MIN计算一次
		mLocClient.setLocOption(option);
		mLocClient.start();
		mLocClient.registerLocationListener(myListener);
		mSearch.init(app.mBMapManager, mkSearchListener);
	}

	/**
	 * 定位SDK监听函数
	 */
	public class DataLocationListenner implements BDLocationListener {

		@Override
		public void onReceiveLocation(BDLocation location) {
			if (location == null)
				return;
			latitude = location.getLatitude();
			longitude = location.getLongitude();
			// 保存经纬度
			Editor editor = sPreferences.edit();
			editor.putString("longitude", String.valueOf(longitude));// 经度
			editor.putString("latitude", String.valueOf(latitude));// 维度
			editor.commit();
			GeoPoint loc_GeoPoint = new GeoPoint((int) (latitude * 1e6),
					(int) (longitude * 1e6));
			mSearch.reverseGeocode(loc_GeoPoint);

		}

		public void onReceivePoi(BDLocation poiLocation) {
			if (poiLocation == null) {
				return;
			}
		}
	}

	/**
	 * 提交位置信息(包括绑定和未绑定车辆的所有用户)
	 */
	private void submitPositionInfo(PdaRequest<MemberDto> info) {
		SubmitPersonalPositionHandler positionHandler = new SubmitPersonalPositionHandler(
				context, info);
		positionHandler.setOnDataReceiveListener(this);
		positionHandler.startNetWork();
	}

	@Override
	public void onDataReceive(DataHandler dataHandler, int resultCode,
			Object data, int type) {
		String dataString = null;
		switch (resultCode) {
		case NetWork.SUBMIT_PERSONAL_POSITION_OK:
			try {
				dataString = new String((byte[]) data, "UTF-8");
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			break;
		case NetWork.SUBMIT_PERSONAL_POSITION_ERROR:

			break;

		default:
			break;
		}
	}

	/** 自定义搜索结果通知接口 */
	class MySearchListener implements MKSearchListener {

		@Override
		public void onGetAddrResult(MKAddrInfo info, int arg1) {
			if (info == null)
				return;
			district = info.addressComponents.district;
			province = info.addressComponents.province;
			city = info.addressComponents.city;
			street = info.addressComponents.street;

			Editor editor = sPreferences.edit();
			editor.putString("longitude", String.valueOf(longitude));// 经度
			editor.putString("latitude", String.valueOf(latitude));// 维度
			editor.putString("province", province);// 省
			editor.putString("city", city);// 市
			editor.putString("district", district);// 区域
			editor.putString("street", street);// 详细地址
			editor.commit();
			MemberDto menberDto = new MemberDto();
			menberDto.setUuId(sPreferences.getString("uuId", ""));
			menberDto.setLast_lat(latitude);
			menberDto.setLast_lng(longitude);
			menberDto.setLast_location(info.strAddr);
			PdaRequest<MemberDto> request = new PdaRequest<MemberDto>();
			request.setData(menberDto);
			submitPositionInfo(request);
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
				return;
			}
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
}