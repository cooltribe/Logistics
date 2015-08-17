package com.seeyuan.logistics.jsonparser;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.baidu.location.f;
import com.seeyuan.logistics.entity.AroundCarInfo;

public class AroundCarJsonParser {

	public static List<AroundCarInfo> parserAroundCarInfoList(String json)
			throws JSONException {
		List<AroundCarInfo> mDataList = new ArrayList<AroundCarInfo>();
		try {
			JSONObject result = new JSONObject(json);
			if (result.getString("status").equals("N")) {
				Log.d("TAG", "请求失败");
				return null;
			}
			JSONArray array = result.getJSONArray("data_List");
			int size = array.length();
			for (int i = 0; i < size; i++) {
				JSONObject object = (JSONObject) array.opt(i);
				AroundCarInfo carInfo = new AroundCarInfo();
				carInfo.setUsrName(object.getString("user_name"));
				carInfo.setDistance(object.getString("distance"));
				carInfo.setAuthentication(object.getString("is_checked")
						.equalsIgnoreCase("Y") ? true : false);
				carInfo.setTime(object.getString("last_pub_date"));
				carInfo.setCarLength(object.getString("vehicle_length"));
				carInfo.setCarID(object.getString("vehicle_sign"));
				carInfo.setTarget(object.getString("terminal"));
				carInfo.setCarStyle(object.getString("vehicle_typename"));
				carInfo.setCarWeigth(object.getString("load_capacity"));
				carInfo.setStar(Integer.parseInt(object.getString("level_id")));
				carInfo.setPhoneNo(object.getString("mobile"));
				carInfo.setLongitude(Double.parseDouble(object
						.getString("longitude")));
				carInfo.setLatitude(Double.parseDouble(object
						.getString("latitude")));

				mDataList.add(carInfo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return mDataList;

	}

	public static AroundCarInfo parserAroundCarInfo(String json)
			throws JSONException {
		AroundCarInfo carInfo = new AroundCarInfo();
		try {
			JSONObject object = new JSONObject(json).getJSONObject("");
			// doSomething

		} catch (Exception e) {
			e.printStackTrace();
		}
		return carInfo;
	}

}
