package com.seeyuan.logistics.jsonparser;

import java.lang.reflect.Type;
import java.util.List;

import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.seeyuan.logistics.entity.OrderDto;
import com.seeyuan.logistics.entity.PdaResponse;
import com.seeyuan.logistics.util.GsonUtils;

public class OrderDetialInfoJsonParser {

	public static PdaResponse<OrderDto> parserOrderDetailInfoJson(String json) {
		PdaResponse<OrderDto> response = new PdaResponse<OrderDto>();
		try {
			Type type = new TypeToken<PdaResponse<OrderDto>>() {
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
