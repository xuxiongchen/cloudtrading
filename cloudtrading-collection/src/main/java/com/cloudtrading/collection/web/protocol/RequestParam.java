package com.cloudtrading.collection.web.protocol;

import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;

public class RequestParam {
	private Map<String, String[]> params = null;

	public RequestParam(Map<String, String[]> params) {
		this.params = params;
	}

	public String getParam(String name) {
		if ((params != null) && (params.get(name) != null)) {
			return params.get(name)[0];
		}
		return null;
	}

	public String[] getParamArray(String name) {
		if ((params != null) && (params.get(name) != null)) {
			return params.get(name);
		}
		return null;
	}

	public int getIntParam(String name) {
		if ((params != null) && (params.get(name) != null)) {
			if (StringUtils.isNumeric(params.get(name)[0])) {
				return Integer.parseInt(params.get(name)[0]);
			}
		}
		return 0;
	}

	public int[] getIntParamArray(String name) {
		int[] intparams = new int[0];
		String[] params = getParamArray(name);
		if ((params != null) && (params.length > 0)) {
			intparams = new int[params.length];
			for (int i = 0; i < params.length; i++) {
				intparams[i] = Integer.parseInt(params[i]);
			}
		}
		return intparams;
	}

	public long getLongParam(String name) {
		if ((params != null) && (params.get(name) != null)) {
			if (StringUtils.isNumeric(params.get(name)[0])) {
				return Long.parseLong(params.get(name)[0]);
			}
		}
		return 0;
	}

	public long[] getLongParamArray(String name) {
		long[] longparams = new long[0];
		String[] params = getParamArray(name);
		if ((params != null) && (params.length > 0)) {
			longparams = new long[params.length];
			for (int i = 0; i < params.length; i++) {
				longparams[i] = Long.parseLong(params[i]);
			}
		}
		return longparams;
	}

	@Override
	public String toString() {
		if (params == null || params.size() <= 0) {
			return "";
		} else {
			StringBuffer sb = new StringBuffer();
			for (Entry<String, String[]> entry : params.entrySet()) {
				sb.append(entry.getKey() + "=");
				sb.append(StringUtils.join(entry.getValue(), ",") + ";");
			}
			return sb.toString();
		}
	}
}
