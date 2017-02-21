package com.cloudtrading.pub;

public class Position {
	private int value;	
	private long valueTime;
	
	public Position(int value, long valueTime) {
		super();
		this.value = value;
		this.valueTime = valueTime;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public long getValueTime() {
		return valueTime;
	}

	public void setValueTime(long valueTime) {
		this.valueTime = valueTime;
	}
	
}
