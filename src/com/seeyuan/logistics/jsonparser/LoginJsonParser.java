package com.seeyuan.logistics.jsonparser;

import java.lang.reflect.Type;

import org.json.JSONException;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.seeyuan.logistics.entity.MemberDto;
import com.seeyuan.logistics.entity.PdaResponse;
import com.seeyuan.logistics.util.GsonUtils;

public class LoginJsonParser {

	/**
	 * 解析json数据，得到具体的实体类
	 * 
	 * @param json
	 * @return
	 * @throws JSONException
	 */
	public static PdaResponse<MemberDto> parserLoginJson(String json)
			throws JSONException {
		PdaResponse<MemberDto> response = new PdaResponse<MemberDto>();
		try {
			Type type = new TypeToken<PdaResponse<MemberDto>>() {
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
