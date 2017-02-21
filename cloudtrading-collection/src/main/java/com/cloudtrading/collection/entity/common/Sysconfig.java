package com.cloudtrading.collection.entity.common;

import com.huisa.common.reflection.annotations.huisadb_alias;
import com.huisa.common.reflection.annotations.huisadb_ignore;

/*  */
@huisadb_alias("sysconfig")
public class Sysconfig {
	@huisadb_ignore
	private java.lang.Integer id;//remark:id;length:10; not null,default:null
	private java.lang.String servercode;//remark:服务器标识(公共标识:com);length:24; not null,default:null
	private java.lang.String ckey;//remark:键;length:64; not null,default:null
	private java.lang.String cvalue;//remark:值;length:256
	private java.lang.String remarks;//remark:备注;length:64
	private java.util.Date createtime;//remark:创建时间;length:19; not null,default:0000-00-00 00:00:00
	@huisadb_ignore
	private java.util.Date updatetime;//remark:更新时间;length:19; not null,default:CURRENT_TIMESTAMP

	public Sysconfig() {
	}

	public void setId(java.lang.Integer id) {
		this.id = id;
	}

	public java.lang.Integer getId() {
		return id;
	}

	public void setServercode(java.lang.String servercode) {
		this.servercode = servercode;
	}

	public java.lang.String getServercode() {
		return servercode;
	}

	public void setCkey(java.lang.String ckey) {
		this.ckey = ckey;
	}

	public java.lang.String getCkey() {
		return ckey;
	}

	public void setCvalue(java.lang.String cvalue) {
		this.cvalue = cvalue;
	}

	public java.lang.String getCvalue() {
		return cvalue;
	}

	public void setRemarks(java.lang.String remarks) {
		this.remarks = remarks;
	}

	public java.lang.String getRemarks() {
		return remarks;
	}

	public void setCreatetime(java.util.Date createtime) {
		this.createtime = createtime;
	}

	public java.util.Date getCreatetime() {
		return createtime;
	}

	public void setUpdatetime(java.util.Date updatetime) {
		this.updatetime = updatetime;
	}

	public java.util.Date getUpdatetime() {
		return updatetime;
	}
}