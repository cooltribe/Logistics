package com.seeyuan.logistics.jsonparser;

import java.lang.reflect.Type;

import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.seeyuan.logistics.entity.PdaResponse;
import com.seeyuan.logistics.entity.StatisticInfoDto;
import com.seeyuan.logistics.util.GsonUtils;

public class CurrentOrderMessageJsonParser {

	public static PdaResponse<StatisticInfoDto> parserCurrentOrderMessageJson(String json) {
		PdaResponse<StatisticInfoDto> response = new PdaResponse<StatisticInfoDto>();
		try {
			Type type = new TypeToken<PdaResponse<StatisticInfoDto>>() {
			}.getType();
			response = GsonUtils.createCommonBuilder().create()
					.fromJson(json, type);
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
			return response;
		}
		return response;
	}

}
