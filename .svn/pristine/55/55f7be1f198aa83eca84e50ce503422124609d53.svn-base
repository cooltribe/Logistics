package com.seeyuan.logistics.jsonparser;

import java.lang.reflect.Type;
import java.util.List;

import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.seeyuan.logistics.entity.CarsDto;
import com.seeyuan.logistics.entity.PdaResponse;
import com.seeyuan.logistics.util.GsonUtils;

public class CarSourceJsonParser {

	public static PdaResponse<List<CarsDto>> parserSearchCarSourceJson(String json) {
		PdaResponse<List<CarsDto>> response = new PdaResponse<List<CarsDto>>();
		try {
			Type type = new TypeToken<PdaResponse<List<CarsDto>>>() {
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
