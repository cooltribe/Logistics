package com.seeyuan.logistics.jsonparser;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.pm.FeatureInfo;
import android.util.Log;

import com.seeyuan.logistics.entity.FutureWeatherInfo;
import com.seeyuan.logistics.entity.WeatherInfo;

public class WeatherJsonParser {

	public static WeatherInfo parserWeather(String json) throws JSONException {
		WeatherInfo info = new WeatherInfo();
		List<FutureWeatherInfo> futureWeatherInfoList = new ArrayList<FutureWeatherInfo>();
		try {
			JSONObject result = new JSONObject(json);
			if (!result.getString("resultcode").equals("200")) {
				return null;
			}
			JSONObject resultContent = new JSONObject(
					result.getString("result"));
			JSONObject currentTimeContent = new JSONObject(
					resultContent.getString("sk"));
			info.setTemp(currentTimeContent.getString("temp"));
			info.setWind_direction(currentTimeContent
					.getString("wind_direction"));
			info.setWind_strength(currentTimeContent.getString("wind_strength"));
			info.setHumidity(currentTimeContent.getString("humidity"));
			info.setTime(currentTimeContent.getString("time"));

			JSONObject todayContent = new JSONObject(
					resultContent.getString("today"));
			info.setCity(todayContent.getString("city"));
			info.setDate_y(todayContent.getString("date_y"));
			info.setWeek(todayContent.getString("week"));
			info.setTemperature(todayContent.getString("temperature"));
			info.setWeather(todayContent.getString("weather"));
			info.setWind(todayContent.getString("wind"));
			info.setUv_index(todayContent.getString("uv_index"));
			info.setWash_index(todayContent.getString("wash_index"));
			info.setTravel_index(todayContent.getString("travel_index"));
			info.setComfort_index(todayContent.getString("comfort_index"));
			info.setDressing_index(todayContent.getString("dressing_index"));
			info.setDressing_advice(todayContent.getString("dressing_advice"));
			info.setExercise_index(todayContent.getString("exercise_index"));
			info.setDrying_index(todayContent.getString("drying_index"));

			JSONArray futureContent = new JSONArray(
					resultContent.getString("future"));
			for (int i = 0; i < futureContent.length(); i++) {
				JSONObject object = futureContent.getJSONObject(i);
				FutureWeatherInfo futureWeatherInfo = new FutureWeatherInfo();
				futureWeatherInfo.setData(object.getString("date"));
				futureWeatherInfo.setTemperature(object
						.getString("temperature"));
				futureWeatherInfo.setWeather(object.getString("weather"));
				futureWeatherInfo.setWind(object.getString("wind"));
				futureWeatherInfo.setWeek(object.getString("week"));
				futureWeatherInfoList.add(futureWeatherInfo);
			}
			info.setFutureWeather(futureWeatherInfoList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return info;

	}

}
