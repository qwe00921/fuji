package com.fsti.fjdicClient.util.timecount;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import android.os.CountDownTimer;
/**
 * 时间处理
 * @author yyy
 *
 */
public class Timecount {
	
	private int  i_year ;
	private int  i_month;
	private int  i_day ;
	private int  i_hour ;
	private int  i_minute ;
	private int  i_second ;
    private int zone;
	
  
	
	
	//获取年
	public int get_year(Date date){
		get_time(date);
		return i_year;
	}
	//获取月
	public int get_month(Date date){
		get_time(date);
		return i_month;
	}
	//获取日
	public int get_day(Date date){
		get_time(date);
		return i_day;
	}
	//获取小时
	public int get_hour(Date date){
		get_time(date);
		return i_hour;
	}
	//获取分钟
	public int get_minute(Date date){
		get_time(date);
		return i_minute;
	}
	//获取秒
	public int get_second(Date date){
		get_time(date);
		return i_second;
	}
	
	

	//把时间转化为秒
	public int timetoseconds(Date date) {
    
		
		get_time(date);
	
		int day_num =count_day_difference(i_year, i_month, i_day);
		int hour_num=day_num*24+i_hour;
		int minute_num = hour_num*60+i_minute;
		int second_num=minute_num*60+i_second ;
		second_num = second_num-zone/1000;//减去时差影响
		
		return second_num;
	}
	
	
	//转化时间格式
	public String timeformat(Date date) {
		  
		//Date date = new Date(System.currentTimeMillis());
		SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		String s_time = dateformat.format(date);
		
         return s_time;
	};
	//获得各段时间值
	private void get_time(Date date){
		String s_time = timeformat(date);
		String s_year = s_time.substring(0, 4);
		String s_month = s_time.substring(5, 7);
		String s_day = s_time.substring(8, 10);
		String s_hour = s_time.substring(11, 13);
		String s_minute = s_time.substring(14, 16);
		String s_second = s_time.substring(17, 19);
		 zone = TimeZone.getDefault().getRawOffset();
	
		  i_year = new Integer(s_year);
		  i_month = new Integer(s_month);
		  i_day = new Integer(s_day);
		  i_hour = new Integer(s_hour);
		  i_minute = new Integer(s_minute);
		  i_second = new Integer(s_second);
		  
	}
	
	
	
	
	//计算天数差(和1970.1.1比)
	private int count_day_difference(int year,int month,int day){
		int num=0;
		//获取1970年到该年前一年的天数
		for(int q=1970;q<year;q++){	
			num=num+is_leap_year(q)+365;
		}
		//获取该年1月到该时间前一月的天数
		for(int q=1;q<month;q++)
		{
			num=num+get_month_num(year, q);
			
		}
		
		num=num+day-1;
		
		return num;
	}
	
	 //是否是闰年
	private int is_leap_year(int year) {
		
	if((year % 400 == 0)|(year % 4 == 0)&(year % 100 != 0)){
    	 
    	 return 1;
     }
     return 0;
	}
	
	//获取莫个月的天数
	private  int get_month_num(int year ,int month){
		switch(month){
		case 2 :
			return 28+is_leap_year(year);
			//break;
		case 1:
		case 3:
		case 5:
		case 7:
		case 8:
		case 10:
		case 12:
			return 31;
		   //break;
		case 4:
		case 6:
		case 9:
		case 11:
			return 30;
			//break;
		}
		return 0;
	}

}
