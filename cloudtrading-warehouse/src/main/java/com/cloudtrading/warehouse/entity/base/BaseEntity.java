package com.cloudtrading.warehouse.entity.base;


import com.huisa.common.reflection.annotations.huisadb_ignore;

import lombok.Data;

@Data
public class BaseEntity {
	@huisadb_ignore
	private java.lang.Integer ID;
	private java.util.Date CREATE_TIME;
	@huisadb_ignore
	private java.util.Date UPDATE_TIME;
}
