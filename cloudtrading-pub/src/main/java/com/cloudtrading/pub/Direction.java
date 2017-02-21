package com.cloudtrading.pub;

public enum Direction {
	INSCREASE("涨", 1),
	DESCREASE("跌",2);
	
	private String name;
	private int index;
	private Direction(String name, int index) {
		this.name = name;
		this.index = index;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
}
