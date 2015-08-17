package com.seeyuan.logistics.jsonparser;

import java.lang.reflect.Type;
import java.util.List;

import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.seeyuan.logistics.entity.OrderTraceDto;
import com.seeyuan.logistics.entity.PdaResponse;
import com.seeyuan.logistics.util.GsonUtils;

public class OrderOperationJsonParser {

	public static PdaResponse<List<OrderTraceDto>> parserOrderOperationDataJson(
			String json) {
		PdaResponse<List<OrderTraceDto>> response = new PdaResponse<List<OrderTraceDto>>();
		try {
			Type type = new TypeToken<PdaResponse<List<OrderTraceDto>>>() {
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
