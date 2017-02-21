package com.cloudtrading.pub;

import com.cloudtrading.pub.Production.Period;

public class Message {
	private MessageType msgType;			//消息类型
	private Production production;			//产品类型
	private Period period;							//短、中、长期
	private Direction createDirection;		//建仓方向
	
	public Message(MessageType msgType, Production production, Period period,
			Direction createDirection) {
		super();
		this.msgType = msgType;
		this.production = production;
		this.period = period;
		this.createDirection = createDirection;
	}

	public enum MessageType{
		READY_CREATE_HOUSE,		//准备建仓
		CANCEL_READY,					//取消准备
		CREATE_HOUSE					//建仓
	}

	public MessageType getMsgType() {
		return msgType;
	}

	public void setMsgType(MessageType msgType) {
		this.msgType = msgType;
	}

	public Production getProduction() {
		return production;
	}

	public void setProduction(Production production) {
		this.production = production;
	}

	public Period getPeriod() {
		return period;
	}

	public void setPeriod(Period period) {
		this.period = period;
	}

	public Direction getCreateDirection() {
		return createDirection;
	}

	public void setCreateDirection(Direction createDirection) {
		this.createDirection = createDirection;
	}
}
