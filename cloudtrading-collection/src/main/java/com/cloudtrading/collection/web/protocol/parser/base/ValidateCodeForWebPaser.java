package com.cloudtrading.collection.web.protocol.parser.base;

import com.cloudtrading.collection.web.protocol.parser.JsonUnitData;
import com.google.gson.JsonObject;
import com.huisa.common.exception.ServiceException;

public class ValidateCodeForWebPaser extends JsonUnitData {
	private String validateCode;

	public ValidateCodeForWebPaser(String validateCode) {
		this.validateCode = validateCode;
	}

	@Override
	public JsonObject parser() throws ServiceException {
		JsonObject responseData = new JsonObject();
		responseData.addProperty("validate_code", validateCode);
		return responseData;
	}

}
