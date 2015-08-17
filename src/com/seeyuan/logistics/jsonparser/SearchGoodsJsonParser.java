package com.seeyuan.logistics.jsonparser;

import java.lang.reflect.Type;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.seeyuan.logistics.entity.GoodsDto;
import com.seeyuan.logistics.entity.PdaResponse;
import com.seeyuan.logistics.util.GsonUtils;

public class SearchGoodsJsonParser {

	public static PdaResponse<List<GoodsDto>> parserSearchGoodsJson(String json) {
		PdaResponse<List<GoodsDto>> response = new PdaResponse<List<GoodsDto>>();;
		try {
			Type type = new TypeToken<PdaResponse<List<GoodsDto>>>() {
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
