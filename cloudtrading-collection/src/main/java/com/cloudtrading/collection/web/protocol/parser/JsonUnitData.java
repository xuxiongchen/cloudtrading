package com.cloudtrading.collection.web.protocol.parser;

import com.google.gson.JsonObject;
import com.huisa.common.exception.ServiceException;

public abstract class JsonUnitData {
	public String getResponseData() throws ServiceException {
		JsonObject responseData = parser();
		if (!responseData.has("code")) {
			responseData.addProperty("code", 1);
		}
		return responseData.toString();
	}

	public abstract JsonObject parser() throws ServiceException;
}
