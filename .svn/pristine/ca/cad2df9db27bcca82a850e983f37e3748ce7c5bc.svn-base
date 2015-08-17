package com.seeyuan.logistics.jsonparser;

import java.lang.reflect.Type;
import java.util.List;

import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.seeyuan.logistics.entity.AccountSettleDto;
import com.seeyuan.logistics.entity.PdaResponse;
import com.seeyuan.logistics.util.GsonUtils;

/**
 * 结算单信息，json解析
 * @author zhazhaobao
 *
 */
public class AccountSettlementJsonParser {

	public static PdaResponse<List<AccountSettleDto>> parserAccountSettlementDataJson(
			String json) {
		PdaResponse<List<AccountSettleDto>> response = new PdaResponse<List<AccountSettleDto>>();
		try {
			Type type = new TypeToken<PdaResponse<List<AccountSettleDto>>>() {
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
