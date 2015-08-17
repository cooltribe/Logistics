package com.seeyuan.logistics.activity;

import java.util.ArrayList;
import java.util.List;

import android.R.bool;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.baidu.platform.comapi.map.a.p;
import com.seeyuan.logistics.R;
import com.seeyuan.logistics.adapter.CityTypeAdapter;
import com.seeyuan.logistics.adapter.PrivinceAdapter;
import com.seeyuan.logistics.util.ToastUtil;

public class SearchCityActivity extends BaseActivity implements OnClickListener {

	/**
	 * 省级列表
	 */
	private ListView province_listview;

	/**
	 * 市级列表
	 */
	private ListView city_listview;

	/**
	 * 省级 adapter
	 */
	private PrivinceAdapter provinceAdapter;

	/**
	 * 市级adapter
	 */
	private CityTypeAdapter cityAdapter;

	/**
	 * 省级列表内容
	 */
	private List<String> provinceDataList;

	/**
	 * 城市列表标记
	 */
	private List<String> cityMarker;

	/**
	 * 城市列表内容
	 */
	// private List<String> cityDataList;

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
	 * 确定按钮
	 */
	private ImageView maintitle_comfirm_iv;

	/**
	 * 是否可以多选
	 */
	private boolean isCanMultipleChoice = false;

	/**
	 * 多选最大个数个数
	 */
	private int mulitpleChoiceMaxNum = 0;

	private final int cityItemData[] = { R.array.beijin_province_item,
			R.array.tianjin_province_item, R.array.heibei_province_item,
			R.array.shanxi1_province_item, R.array.neimenggu_province_item,
			R.array.liaoning_province_item, R.array.jilin_province_item,
			R.array.heilongjiang_province_item, R.array.shanghai_province_item,
			R.array.jiangsu_province_item, R.array.zhejiang_province_item,
			R.array.anhui_province_item, R.array.fujian_province_item,
			R.array.jiangxi_province_item, R.array.shandong_province_item,
			R.array.henan_province_item, R.array.hubei_province_item,
			R.array.hunan_province_item, R.array.guangdong_province_item,
			R.array.guangxi_province_item, R.array.hainan_province_item,
			R.array.chongqing_province_item, R.array.sichuan_province_item,
			R.array.guizhou_province_item, R.array.yunnan_province_item,
			R.array.xizang_province_item, R.array.shanxi2_province_item,
			R.array.gansu_province_item, R.array.qinghai_province_item,
			R.array.linxia_province_item, R.array.xinjiang_province_item,
			R.array.hongkong_province_item, R.array.aomen_province_item

	};

	/**
	 * 省级列表状态view
	 */
	private View oldView;

	
	//------------------------>
	//
	//切换其他省的时候如何保存之前省的选中状态，保存剩余可选择的市的个数,保存全部选择的市的名字
	//<-----------------------
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.activity_select_city); // 软件activity的布局
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,
				R.layout.deflaut_titlebar); // titlebar为自己标题栏的布局

		context = getApplicationContext();
		initView();
		initProvinceAdapter();
	}

	@Override
	public void onClickListener(View view) {

	}

	@Override
	public void initView() {
		maintitle_back_iv = (ImageView) findViewById(R.id.maintitle_back_iv);
		maintitle_back_iv.setOnClickListener(this);

		defaulttitle_title_tv = (TextView) findViewById(R.id.defaulttitle_title_tv);
		defaulttitle_title_tv.setText(R.string.select_city_hint);

		province_listview = (ListView) findViewById(R.id.province_listview);
		city_listview = (ListView) findViewById(R.id.city_listview);

		isCanMultipleChoice = getIntent().getBooleanExtra(
				"isCanMultipleChoice", false);
		mulitpleChoiceMaxNum = getIntent().getIntExtra("mulitpleChoiceMaxNum",
				0);

		maintitle_comfirm_iv = (ImageView) findViewById(R.id.maintitle_comfirm_iv);
		maintitle_comfirm_iv.setOnClickListener(this);
		if (isCanMultipleChoice)
			maintitle_comfirm_iv.setVisibility(View.VISIBLE);
	}

	/**
	 * 初始化省
	 */
	private void initProvinceAdapter() {
		provinceDataList = new ArrayList<String>();
		cityMarker = new ArrayList<String>();
		// cityDataList = new ArrayList<String>();
		TypedArray typedArray = getResources().obtainTypedArray(
				R.array.province_item);
		TypedArray cityArray = getResources().obtainTypedArray(
				R.array.city_item);
		int size = typedArray.length();
		for (int i = 0; i < size; i++) {
			provinceDataList.add(typedArray.getString(i));
		}
		int citySize = cityArray.length();
		for (int i = 0; i < citySize; i++) {
			cityMarker.add(cityArray.getString(i));
		}
		provinceAdapter = new PrivinceAdapter(context, provinceDataList);
		// cityAdapter = new CityTypeAdapter(context, cityDataList);
		province_listview.setAdapter(provinceAdapter);
		province_listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				if (oldView != null)
					oldView.setBackgroundResource(R.color.Background);
				view.setBackgroundResource(R.color.search_city_button_down);
				oldView = view;
				initCityAdapter(provinceDataList.get((int)id).toString(),
						cityItemData[(int)id]);
			}
		});
		typedArray.recycle();
		cityArray.recycle();
	}

	/**
	 * 初始化城市
	 * 
	 * @param cityMark
	 *            城市标签
	 */
	private void initCityAdapter(final String privince, int cityMark) {
		TypedArray cityArray = getResources().obtainTypedArray(cityMark);
		final List<String> cityDataList = new ArrayList<String>();
		int size = cityArray.length();
		for (int i = 0; i < size; i++) {
			cityDataList.add(cityArray.getString(i).toString() +"市");
		}
		cityAdapter = new CityTypeAdapter(context, this, cityDataList);
		cityAdapter.setCurrentPlace(privince);
		cityAdapter.setCanMultipleChoice(isCanMultipleChoice);
		cityAdapter.setMulitpleChoiceMaxNum(mulitpleChoiceMaxNum);
		city_listview.setAdapter(cityAdapter);
		// city_listview.setOnItemClickListener(new OnItemClickListener() {
		//
		// @Override
		// public void onItemClick(AdapterView<?> parent, View view,
		// int position, long id) {
		// String place;
		// if (cityDataList.get(position).toString()
		// .equalsIgnoreCase(privince)) {
		// place = cityDataList.get(position).toString();
		// } else {
		// place = privince + cityDataList.get(position).toString();
		// }
		// doItemOnClicked(place);
		// }
		// });
		cityArray.recycle();
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.maintitle_back_iv:
			if (!SearchCityActivity.this.isFinishing()) {
				finish();
			}
			break;
		case R.id.maintitle_comfirm_iv:
			doComfirm();
			break;
		default:
			break;
		}
	}

	/**
	 * 执行确定
	 */
	private void doComfirm() {
		Intent intent = new Intent();
		List<String> currentPlaceList = new ArrayList<String>();
		String place = "";
		int listSize;
		if (cityAdapter != null)
			currentPlaceList = cityAdapter.getSelectedCityList();
		listSize = currentPlaceList.size();
		place = currentPlaceList.get(0);
		if (listSize != 1) {
			for (int i = 1; i < listSize; i++) {
				place = place + "," + currentPlaceList.get(i);
			}
		}
		intent.putExtra("place", place);
		setResult(RESULT_OK, intent);
		finish();
	}
}
