package com.seeyuan.logistics.jsonparser;

import java.lang.reflect.Type;
import java.util.List;

import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.seeyuan.logistics.entity.DriverDto;
import com.seeyuan.logistics.entity.PdaResponse;
import com.seeyuan.logistics.util.GsonUtils;

public class DriverInfoJsonParser {
	public static PdaResponse<List<DriverDto>> parserDriverInfoJson(String json) {
		PdaResponse<List<DriverDto>> response = new PdaResponse<List<DriverDto>>();
		try {
			Type type = new TypeToken<PdaResponse<List<DriverDto>>>() {
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
