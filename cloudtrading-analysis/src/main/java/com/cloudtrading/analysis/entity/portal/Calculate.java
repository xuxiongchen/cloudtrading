package com.cloudtrading.analysis.entity.portal;

import java.util.Date;

import com.cloudtrading.pub.Direction;
import com.cloudtrading.pub.Position;
import com.cloudtrading.pub.Production;
import com.cloudtrading.pub.Production.Period;
import com.cloudtrading.warehouse.utils.DateFormatUtil;

//分析预测
public class Calculate {
		private Direction direction;		//预测的方向
		private boolean haveResult;	//预测是否有结果，考虑数据丢失造成部分预测结果未知
		private boolean isSuccess;		//预测结果
		private Position position;			//预测时的观察点
		private Position lastPosition;
		private Period period;				//预测的时期长短
		private Production production;	//产品类型
		private String remark;
		
		public Calculate(Direction direction,
				Position position,Position lastPosition, Period period, Production production) {
			super();
			this.direction = direction;
			this.isSuccess = false;
			this.position = position;
			this.lastPosition=lastPosition;
			this.period = period;
			this.production = production;
		}
		
		@Override
		public String toString() {
			float k=(float) (((position.getValue()-lastPosition.getValue())*10000.0)
					/((position.getValueTime()-lastPosition.getValueTime())*100));
			remark="k值="+k+"\t";
			return  remark+"在："+  position.getValue()+"点"
					+"买：" + direction.getName() 
					+ ", 结果："    + (haveResult?(isSuccess?"成功":"失败"):"未知") 
					+ ", 建仓时间："  +DateFormatUtil.formatSimpleDate(new Date(position.getValueTime() )) 
					+ ", 周期："    + period.getName() 
					+ ", 产品："    + production.getName() 
					+ ", 备注："    + remark;
		}

		public Direction getDirection() {
			return direction;
		}
		public void setDirection(Direction direction) {
			this.direction = direction;
		}
		public boolean isSuccess() {
			return isSuccess;
		}
		public void setSuccess(boolean isSuccess) {
			this.isSuccess = isSuccess;
			this.haveResult = true;
		}
		public Position getPosition() {
			return position;
		}
		public void setPosition(Position position) {
			this.position = position;
		}
		public Period getPeriod() {
			return period;
		}
		public void setPeriod(Period period) {
			this.period = period;
		}
		public Production getProduction() {
			return production;
		}
		public void setProduction(Production production) {
			this.production = production;
		}

		public String getRemark() {
			return remark;
		}

		public void setRemark(String remark) {
			this.remark = remark;
		}

		public boolean isHaveResult() {
			return haveResult;
		}

		public void setHaveResult(boolean haveResult) {
			this.haveResult = haveResult;
		}
}
