package com.cloudtrading.pub;

import java.util.Map;

public class Production {
		private String platform;	//平台
		private String name;		//名字
		private int index;				//产品序号
		private Map<Period, Integer> period;		//短、中、长期止盈止损值
		
		//投资的期限
		public enum Period {
			SHORT_PERIOD("短期", 1),			
			MIDDLE_PERIOD("中期", 2)	,		
			LONG_PERIOD	("长期", 3);	
			
			private String name;
			private int index;
			private Period(String name, int index){
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
		
		public Production(String platform, String name, int index,
				Map<Period, Integer> period) {
			super();
			this.platform = platform;
			this.name = name;
			this.index = index;
			this.period = period;
		}
		
		public String getPlatform() {
			return platform;
		}
		public void setPlatform(String platform) {
			this.platform = platform;
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
		public Map<Period, Integer> getPeriod() {
			return period;
		}
		public void setPeriod(Map<Period, Integer> period) {
			this.period = period;
		}
}
