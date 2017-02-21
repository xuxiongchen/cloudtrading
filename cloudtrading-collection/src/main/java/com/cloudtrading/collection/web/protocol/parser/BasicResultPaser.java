package com.cloudtrading.collection.web.protocol.parser;

import org.apache.commons.lang3.StringUtils;

import com.google.gson.JsonObject;

public class BasicResultPaser extends JsonUnitData {
	private int code;
	private String msg;

	public BasicResultPaser() {
	}

	public BasicResultPaser(int code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	@Override
	public JsonObject parser() {
		JsonObject responseData = new JsonObject();
		if (code != 0) {
			responseData.addProperty("code", code);
			if (StringUtils.isNotBlank(msg)) {
				responseData.addProperty("msg", msg);
			}
		}
		return responseData;
	}

}
