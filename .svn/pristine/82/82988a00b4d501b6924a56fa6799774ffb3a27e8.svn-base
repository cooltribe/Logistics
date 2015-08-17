package com.seeyuan.logistics.jsonparser;

import java.lang.reflect.Type;
import java.util.List;

import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.seeyuan.logistics.entity.PdaResponse;
import com.seeyuan.logistics.entity.SettleAccountsDetailDto;
import com.seeyuan.logistics.util.GsonUtils;

/**
 * 结算单明细信息，json解析
 * @author zhazhaobao
 *
 */
public class AccountSettlementDetailJsonParser {

	public static PdaResponse<List<SettleAccountsDetailDto>> parserAccountSettlementDetailDataJson(
			String json) {
		PdaResponse<List<SettleAccountsDetailDto>> response = new PdaResponse<List<SettleAccountsDetailDto>>();
		try {
			Type type = new TypeToken<PdaResponse<List<SettleAccountsDetailDto>>>() {
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
