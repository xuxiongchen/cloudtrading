package com.cloudtrading.warehouse.entity;

import com.cloudtrading.warehouse.entity.base.BaseEntity;
import com.huisa.common.reflection.annotations.huisadb_alias;

import lombok.Data;

/**
 * 建仓值
 * @author chenxu
 *
 */
@Data
@huisadb_alias("CT_WAREHOUSE")
public class Warehouse  extends BaseEntity{

	private java.lang.Integer DIRECTION; //1涨 2跌
	
	private java.lang.Integer CREATEPOSITION;//建仓时的大小
	
	private java.util.Date DATETIME; //建仓时机
	
	private java.lang.Long TIME;
	
	private java.lang.String DESCRIPTION;

	private java.lang.Long OLDTIME;
	
	
	
}
