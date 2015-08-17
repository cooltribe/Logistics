package com.seeyuan.logistics.jsonparser;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.seeyuan.logistics.entity.GasStationInfo;

public class GasStationJsonParser {

	public static List<GasStationInfo> parserGasStation(String json)
			throws JSONException {
		List<GasStationInfo> gasStationInfosList = new ArrayList<GasStationInfo>();
		try {
			JSONObject result = new JSONObject(json);
			if (!result.getString("resultcode").equals("200")) {
				return null;
			}
			JSONArray array = new JSONArray(result.getString("data"));
			for (int i = 0; i < array.length(); i++) {
				GasStationInfo info = new GasStationInfo();
				JSONObject object = array.getJSONObject(i);
				info.setId(object.getString("id"));
				info.setName(object.getString("name"));
				info.setArea(object.getString("area"));
				info.setAreaname(object.getString("areaname"));
				info.setAddress(object.getString("address"));
				info.setBrandname(object.getString("brandname"));
				info.setType(object.getString("type"));
				info.setDiscount(object.getString("discount"));
				info.setExhaust(object.getString("exhaust"));
				info.setPosition(object.getString("position"));
				info.setLon(object.getString("lon"));
				info.setLat(object.getString("lat"));
				info.setPrice(object.getString("price"));
				info.setGastprice(object.getString("gastprice"));
				info.setFwlsmc(object.getString("fwlsmc"));
				info.setDistance(object.getString("distance"));
				gasStationInfosList.add(info);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return gasStationInfosList;
	}
}
