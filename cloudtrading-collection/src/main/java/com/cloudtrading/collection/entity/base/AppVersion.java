package com.cloudtrading.collection.entity.base;

import com.huisa.common.reflection.annotations.huisadb_alias;
import com.huisa.common.reflection.annotations.huisadb_ignore;

/*  */
@huisadb_alias("app_version")
public class AppVersion {
	@huisadb_ignore
	private java.lang.Integer id;//remark:id，自增;length:10; not null,default:null
	@huisadb_alias("app_type")
	private java.lang.Integer appType;//remark:app类型；值域：【1：导购端app、2：用户端app】;length:10; not null,default:null
	@huisadb_alias("sys_type")
	private java.lang.Integer sysType;//remark:系统类型；值域：【1:ios，2：android】;length:10; not null,default:null
	@huisadb_alias("version_code")
	private java.lang.Integer versionCode;//remark:版本code;length:10; not null,default:null
	@huisadb_alias("version_name")
	private java.lang.String versionName;//remark:版本名称;length:32; not null,default:null
	@huisadb_alias("update_log")
	private java.lang.String updateLog;//remark:更新内容（HTML格式）;length:65535
	@huisadb_alias("apk_url")
	private java.lang.String apkUrl;//remark:apk包下载地址,针对android系统;length:120
	@huisadb_alias("create_time")
	private java.util.Date createTime;//remark:创建时间;length:19
	@huisadb_ignore
	@huisadb_alias("update_time")
	private java.util.Date updateTime;//remark:更新时间;length:19; not null,default:CURRENT_TIMESTAMP

	public AppVersion() {
	}

	public void setId(java.lang.Integer id) {
		this.id = id;
	}

	public java.lang.Integer getId() {
		return id;
	}

	public void setAppType(java.lang.Integer appType) {
		this.appType = appType;
	}

	public java.lang.Integer getAppType() {
		return appType;
	}

	public void setSysType(java.lang.Integer sysType) {
		this.sysType = sysType;
	}

	public java.lang.Integer getSysType() {
		return sysType;
	}

	public void setVersionCode(java.lang.Integer versionCode) {
		this.versionCode = versionCode;
	}

	public java.lang.Integer getVersionCode() {
		return versionCode;
	}

	public void setVersionName(java.lang.String versionName) {
		this.versionName = versionName;
	}

	public java.lang.String getVersionName() {
		return versionName;
	}

	public void setUpdateLog(java.lang.String updateLog) {
		this.updateLog = updateLog;
	}

	public java.lang.String getUpdateLog() {
		return updateLog;
	}

	public void setApkUrl(java.lang.String apkUrl) {
		this.apkUrl = apkUrl;
	}

	public java.lang.String getApkUrl() {
		return apkUrl;
	}

	public void setCreateTime(java.util.Date createTime) {
		this.createTime = createTime;
	}

	public java.util.Date getCreateTime() {
		return createTime;
	}

	public void setUpdateTime(java.util.Date updateTime) {
		this.updateTime = updateTime;
	}

	public java.util.Date getUpdateTime() {
		return updateTime;
	}
}