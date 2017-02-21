package com.cloudtrading.warehouse.entity;

import com.cloudtrading.warehouse.entity.base.BaseEntity;
import com.huisa.common.reflection.annotations.huisadb_alias;

import lombok.Data;

@Data
@huisadb_alias("CT_RESULT")
public class Result extends BaseEntity{

	
	private java.util.Date DATE;
	
	private java.lang.Integer IS_SUCESS;
	
	private java.lang.Integer POSITION;
	
	private java.lang.Integer CREATE_POSITION;
	
	private java.lang.String  DESCRIPTION;

	
}
