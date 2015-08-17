package com.seeyuan.logistics.jsonparser;

import java.lang.reflect.Type;
import java.util.List;

import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.seeyuan.logistics.entity.AccountLogDto;
import com.seeyuan.logistics.entity.PdaResponse;
import com.seeyuan.logistics.util.GsonUtils;

public class GetDealManagerJsonParser {

	public static PdaResponse<List<AccountLogDto>> parserDealManagerJson(String json) {
		PdaResponse<List<AccountLogDto>> response = new PdaResponse<List<AccountLogDto>>();
		try {
			Type type = new TypeToken<PdaResponse<List<AccountLogDto>>>() {
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
