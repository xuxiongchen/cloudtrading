package com.cloudtrading.collection.entity;

import com.cloudtrading.collection.entity.base.BaseEntity;
import com.huisa.common.reflection.annotations.huisadb_alias;

import lombok.Data;

@Data
@huisadb_alias("CT_GOLDCOPPER_DATA")
public class GoldCopperData extends BaseEntity{

	private java.lang.Integer VALUE;
	private java.lang.Long TIME;
	private java.lang.Long TIME_ERROR;
	
	
}
