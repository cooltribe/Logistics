package com.seeyuan.logistics.jsonparser;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.baidu.location.f;
import com.seeyuan.logistics.entity.AroundCarInfo;
import com.seeyuan.logistics.entity.AroundFriends;

public class AroundFriendsJsonParser {

	public static List<AroundFriends> parserAroundFriendsInfoList(String json)
			throws JSONException {
		List<AroundFriends> mDataList = new ArrayList<AroundFriends>();
		try {
			JSONObject result = new JSONObject(json);
			if (result.getString("status").equals("N")) {
				Log.d("TAG", "请求失败");
				return null;
			}
			JSONArray array = result.getJSONArray("memberList");
			int size = array.length();
			for (int i = 0; i < size; i++) {
				JSONObject object = (JSONObject) array.opt(i);
				AroundFriends friendsInfo = new AroundFriends();
				friendsInfo.setUserName(object.getString("user_name"));
				friendsInfo.setDescription(object.getString("remark"));
				friendsInfo.setAuthentication(object.getString("is_cheked")
						.equalsIgnoreCase("Y") ? true : false);
				friendsInfo.setHeadImageURL(object.getString("face"));
				friendsInfo.setLatitude(object.getString("last_lat"));
				friendsInfo.setLongitude(object.getString("last_lng"));
				mDataList.add(friendsInfo);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

		return mDataList;

	}

	public static AroundFriends parserAroundFriendsInfo(String json)
			throws JSONException {
		AroundFriends friendsInfo = new AroundFriends();
		try {
			JSONObject object = new JSONObject(json).getJSONObject("");
			// doSomething

		} catch (Exception e) {
			e.printStackTrace();
		}
		return friendsInfo;
	}

}
