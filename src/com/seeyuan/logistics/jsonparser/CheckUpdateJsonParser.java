package com.seeyuan.logistics.jsonparser;

import java.lang.reflect.Type;

import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.seeyuan.logistics.entity.PdaResponse;
import com.seeyuan.logistics.entity.PdaVersionInfoDto;
import com.seeyuan.logistics.util.GsonUtils;

public class CheckUpdateJsonParser {

	public static PdaResponse<PdaVersionInfoDto> parserCheckUpdateJson(String json) {
		PdaResponse<PdaVersionInfoDto> response = new PdaResponse<PdaVersionInfoDto>();
		try {
			response = new PdaResponse<PdaVersionInfoDto>();
			Type type = new TypeToken<PdaResponse<PdaVersionInfoDto>>() {
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
