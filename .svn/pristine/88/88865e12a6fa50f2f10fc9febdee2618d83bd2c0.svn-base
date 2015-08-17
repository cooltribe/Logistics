package com.seeyuan.logistics.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.seeyuan.logistics.R;
import com.seeyuan.logistics.util.CommonUtils;

/**
 * 实用工具
 * 
 * @author zhazhaobao
 * 
 */
public class UtilityActivity extends BaseActivity implements OnClickListener {

	/**
	 * 返回按钮
	 */
	private ImageView maintitle_back_iv;

	/**
	 * 标题title
	 */
	private TextView defaulttitle_title_tv;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.activity_utility); // 软件activity的布局
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,
				R.layout.deflaut_titlebar); // titlebar为自己标题栏的布局
		initView();

	}

	@Override
	public void initView() {
		maintitle_back_iv = (ImageView) findViewById(R.id.maintitle_back_iv);
		maintitle_back_iv.setOnClickListener(this);

		defaulttitle_title_tv = (TextView) findViewById(R.id.defaulttitle_title_tv);
		defaulttitle_title_tv.setText(R.string.utiltiy_hint);
	}

	@Override
	public void onClickListener(View view) {
		switch (view.getId()) {
		case R.id.weather_live_But:
			doWeatherLiveBtn();
			break;
		case R.id.parking_lot_btn:
			doParkingLotBtn();
			break;
		case R.id.gas_station_btn:
			doGasStationBtn();
			break;

		default:
			break;
		}
	}

	private void doParkingLotBtn() {
		Intent intent = new Intent(UtilityActivity.this,
				ParkingLotActivity.class);
		startActivity(intent);
	}

	private void doGasStationBtn() {
		Intent intent = new Intent(UtilityActivity.this,
				GasStationActivity.class);
		startActivity(intent);
	}

	private void doWeatherLiveBtn() {
		Intent intent = new Intent(UtilityActivity.this,
				WeatherLiveActivity.class);
		startActivity(intent);
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.maintitle_back_iv:
			CommonUtils.finishActivity(UtilityActivity.this);
			break;

		default:
			break;
		}
	}

}
