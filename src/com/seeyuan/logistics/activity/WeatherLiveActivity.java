package com.seeyuan.logistics.activity;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.seeyuan.logistics.R;
import com.seeyuan.logistics.adapter.FutureWeatherAdapter;
import com.seeyuan.logistics.application.ConstantPool;
import com.seeyuan.logistics.application.NetWork;
import com.seeyuan.logistics.datacenter.DataHandler;
import com.seeyuan.logistics.datacenter.OnDataReceiveListener;
import com.seeyuan.logistics.datahandler.WeatherHandler;
import com.seeyuan.logistics.entity.FutureWeatherInfo;
import com.seeyuan.logistics.entity.WeatherInfo;
import com.seeyuan.logistics.jsonparser.WeatherJsonParser;
import com.seeyuan.logistics.util.CommonUtils;

/**
 * 实时天气
 * 
 * @author zhazhaobao
 */
public class WeatherLiveActivity extends BaseActivity implements
		OnClickListener, OnDataReceiveListener {

	/**
	 * 返回按钮
	 */
	private ImageView maintitle_back_iv;

	/**
	 * 标题title
	 */
	private TextView defaulttitle_title_tv;

	private Context context;

	private SharedPreferences sPreferences;

	/**
	 * 城市
	 */
	private TextView weather_city_name_tv;

	/**
	 * 天气
	 */
	private TextView weather_weather_tv;

	/**
	 * 温度
	 */
	private TextView weather_temperature_tv;

	/**
	 * 星期
	 */
	private TextView weather_day_tv;

	/**
	 * 最低温度
	 */
	private TextView weather_low_temperature_tv;
	/**
	 * 最高温度
	 */
	private TextView weather_high_temperature_tv;

	/**
	 * 风向
	 */
	private TextView weather_wind_tv;
	/**
	 * 紫外线强度
	 */
	private TextView weather_ultraviolet_tv;
	/**
	 * 旅行强度
	 */
	private TextView weather_travel_tv;
	/**
	 * 洗车指数
	 */
	private TextView weather_clean_tv;
	/**
	 * 晨练指数
	 */
	private TextView weather_exercise_tv;

	/**
	 * 未来几日天气
	 */
	private ListView weather_future_list;
	private FutureWeatherAdapter futureWeatherAdapter;
	private List<FutureWeatherInfo> futureWeatherList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.activity_weather_live);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,
				R.layout.deflaut_titlebar); // titlebar为自己标题栏的布局
		context = getApplicationContext();
		sPreferences = getSharedPreferences(ConstantPool.LOGISTICS_PREFERENCES,
				Context.MODE_PRIVATE);
		initView();
		getWeatherInfo();
	}

	@Override
	public void initView() {
		maintitle_back_iv = (ImageView) findViewById(R.id.maintitle_back_iv);
		maintitle_back_iv.setOnClickListener(this);

		defaulttitle_title_tv = (TextView) findViewById(R.id.defaulttitle_title_tv);
		defaulttitle_title_tv.setText(R.string.weather_live_hint);

		weather_city_name_tv = (TextView) findViewById(R.id.weather_city_name_tv);
		weather_weather_tv = (TextView) findViewById(R.id.weather_weather_tv);
		weather_temperature_tv = (TextView) findViewById(R.id.weather_temperature_tv);
		weather_day_tv = (TextView) findViewById(R.id.weather_day_tv);
		weather_low_temperature_tv = (TextView) findViewById(R.id.weather_low_temperature_tv);
		weather_high_temperature_tv = (TextView) findViewById(R.id.weather_high_temperature_tv);
		weather_wind_tv = (TextView) findViewById(R.id.weather_wind_tv);
		weather_ultraviolet_tv = (TextView) findViewById(R.id.weather_ultraviolet_tv);
		weather_travel_tv = (TextView) findViewById(R.id.weather_travel_tv);
		weather_clean_tv = (TextView) findViewById(R.id.weather_clean_tv);
		weather_exercise_tv = (TextView) findViewById(R.id.weather_exercise_tv);

		weather_future_list = (ListView) findViewById(R.id.weather_future_list);
		futureWeatherList = new ArrayList<FutureWeatherInfo>();

	}

	/**
	 * 获取天气信息
	 */
	private void getWeatherInfo() {
		WeatherHandler dataHandler = new WeatherHandler(context, "南京");
		// WeatherHandler dataHandler = new WeatherHandler(context,
		// sPreferences.getString("city", ""));
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
			CommonUtils.finishActivity(WeatherLiveActivity.this);
			break;

		default:
			break;
		}
	}

	@Override
	public void onDataReceive(DataHandler dataHandler, int resultCode,
			Object data, int type) {
		switch (resultCode) {
		case NetWork.WEATHER_SOURCE_OK:
			parserWeather(data);
			break;
		case NetWork.WEATHER_SOURCE_ERROR:
			Log.d("TAG", "失败");
			break;

		default:
			break;
		}

	}

	private void parserWeather(Object data) {
		String dataString = null;
		try {
			dataString = new String((byte[]) data, "UTF-8");
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		try {
			Log.d("TAG", "dataString = " + dataString);
			WeatherInfo info = WeatherJsonParser.parserWeather(dataString);
			refreshView(info);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void refreshView(WeatherInfo info) {
		if (info == null)
			return;
		weather_city_name_tv.setText(info.getCity());
		weather_weather_tv.setText(info.getWeather());
		weather_temperature_tv.setText(info.getTemp());
		weather_day_tv.setText(info.getWeek());
		String temperature = info.getTemperature();
		weather_high_temperature_tv.setText(temperature.substring(0,
				temperature.lastIndexOf("~")));
		weather_low_temperature_tv.setText(temperature.substring(
				temperature.indexOf("~") + 1, temperature.length()));
		weather_wind_tv.setText("风向:" + info.getWind());
		weather_ultraviolet_tv.setText("紫外线强度:" + info.getUv_index());
		weather_travel_tv.setText("旅行指数:" + info.getTravel_index());
		weather_clean_tv.setText("洗车指数:" + info.getWash_index());
		weather_exercise_tv.setText("晨练指数:" + info.getExercise_index());

		futureWeatherList = info.getFutureWeather();
		Log.d("TAG", "futureWeatherList = " + futureWeatherList.size());
		futureWeatherAdapter = new FutureWeatherAdapter(futureWeatherList,
				context);
		weather_future_list.setAdapter(futureWeatherAdapter);
//		weather_future_list.setEnabled(false);
		//listview滑动背景需要设置，如若不设置，滑动效果太丑
	}
}
