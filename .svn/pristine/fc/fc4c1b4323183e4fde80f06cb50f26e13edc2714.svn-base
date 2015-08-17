package com.seeyuan.logistics.jsonparser;

import java.lang.reflect.Type;
import java.util.List;

import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.seeyuan.logistics.entity.OrderDto;
import com.seeyuan.logistics.entity.PdaResponse;
import com.seeyuan.logistics.util.GsonUtils;

public class OrderInfoJsonParser {

	public static PdaResponse<List<OrderDto>> parserOrderInfoJson(String json) {
		PdaResponse<List<OrderDto>> response = new PdaResponse<List<OrderDto>>();
		try {
			Type type = new TypeToken<PdaResponse<List<OrderDto>>>() {
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
