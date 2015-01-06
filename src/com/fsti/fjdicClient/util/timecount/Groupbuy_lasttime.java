package com.fsti.fjdicClient.util.timecount;


public class Groupbuy_lasttime {
	
	//通过相差的秒数计算：天数，小时，分钟，秒
	
	public int get_day_num(int seconds){
		
		return seconds/(60*60*24);
	}
	public int get_hour_num(int seconds){
		
		return (seconds/(60*60))%24;
	}
	public int get_minute_num(int seconds){
		return (seconds/60)%60;
	}
	public int get_second_num(int seconds){
		return seconds%60;
	}
	
}
