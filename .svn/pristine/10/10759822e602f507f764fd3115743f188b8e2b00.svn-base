package com.seeyuan.logistics.jsonparser;

import java.lang.reflect.Type;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.seeyuan.logistics.entity.GoodsDto;
import com.seeyuan.logistics.entity.PdaResponse;
import com.seeyuan.logistics.entity.RouteDto;
import com.seeyuan.logistics.util.GsonUtils;

public class CarJsonParser {

	public static PdaResponse<List<RouteDto>> parserSearchCarJson(String json) {
		PdaResponse<List<RouteDto>> response = new PdaResponse<List<RouteDto>>();
		try {
			Type type = new TypeToken<PdaResponse<List<RouteDto>>>() {
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
