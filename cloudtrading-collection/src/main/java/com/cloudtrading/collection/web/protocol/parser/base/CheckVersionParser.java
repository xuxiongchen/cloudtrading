package com.cloudtrading.collection.web.protocol.parser.base;

import com.cloudtrading.collection.common.AppContext;
import com.cloudtrading.collection.entity.base.AppVersion;
import com.cloudtrading.collection.web.protocol.parser.JsonUnitData;
import com.google.gson.JsonObject;
import com.huisa.common.exception.ServiceException;
import com.huisa.common.util.DateCoverd;

public class CheckVersionParser extends JsonUnitData {
	private AppVersion maxAppVersion;
	private int versionCode;

	public CheckVersionParser(AppVersion maxAppVersion, int versionCode) {
		this.maxAppVersion = maxAppVersion;
		this.versionCode = versionCode;
	}

	@Override
	public JsonObject parser() throws ServiceException {
		JsonObject responseData = new JsonObject();
		if (maxAppVersion != null && maxAppVersion.getVersionCode() != null
				&& maxAppVersion.getVersionCode() > 0) {
			if (versionCode >= maxAppVersion.getVersionCode()) {
				responseData.addProperty("has_new", 0);
			} else {
				responseData.addProperty("has_new", 1);
				responseData.addProperty("version_code",
						maxAppVersion.getVersionCode());
				responseData.addProperty("version_name",
						maxAppVersion.getVersionName());
				responseData.addProperty("update_log",
						maxAppVersion.getUpdateLog());
				responseData.addProperty("apk_url", AppContext.getFileUrl(maxAppVersion.getApkUrl()));
				if (maxAppVersion.getCreateTime() != null) {
					responseData.addProperty("publish_time", DateCoverd
							.toString(maxAppVersion.getCreateTime(),
									DateCoverd.FORMAT_YYYY_MM_DD_HH_MM));
				}
			}
		} else {
			responseData.addProperty("has_new", 0);
		}
		return responseData;
	}

}
