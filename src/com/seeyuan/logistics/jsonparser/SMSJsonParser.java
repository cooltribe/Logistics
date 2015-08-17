package com.seeyuan.logistics.jsonparser;

import java.lang.reflect.Type;

import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.seeyuan.logistics.entity.PdaResponse;
import com.seeyuan.logistics.entity.SmsInfoDto;
import com.seeyuan.logistics.util.GsonUtils;

public class SMSJsonParser {

	public static PdaResponse<SmsInfoDto> parserSMSJson(String json) {
		PdaResponse<SmsInfoDto> response = new PdaResponse<SmsInfoDto>();
		try {
			Type type = new TypeToken<PdaResponse<SmsInfoDto>>() {
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
