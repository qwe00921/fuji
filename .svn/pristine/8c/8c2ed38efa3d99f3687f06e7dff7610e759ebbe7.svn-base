package com.fsti.fjdicClient.util.timecount;

import java.util.Date;

import com.fsti.fjdicClient.activity.groupBuy.GroupBuyDetailActivity;

import android.os.CountDownTimer;

public class Timedowncount {
	private int now_num=0;
	private static  CountDownTimer countdowntimer;
	public Timedowncount() {}
	public Timedowncount(final Date date1,final Date date2) {
		
	    int max_num=new Timecount().timetoseconds(date1)-new Timecount().timetoseconds(date2);
	    
	     now_num=max_num;
	     if(countdowntimer!=null){
				System.out.println("...............cancel..... success............");
			countdowntimer.cancel();
			}
	      countdowntimer =	new CountDownTimer(max_num, 1000) {
				@Override
				public void onFinish() {
					// done
					
					 now_num=now_num-1;				
			         new GroupBuyDetailActivity().tvupdata(now_num);
			         countdowntimer.cancel();
			         if(now_num!=0){
			        	 new Timedowncount( date1, date2) ;
			        	 }
					
					
				}

				@Override
				public void onTick(long arg0) {
					// 每1000毫秒回调的方法
					 now_num=now_num-1;				
	         new GroupBuyDetailActivity().tvupdata(now_num);
	         if(now_num==0){countdowntimer_cancle();}
				}

			}.start();

		}
	public void countdowntimer_cancle(){
		if(countdowntimer!=null){
			System.out.println("...............cancel..... success............");
		countdowntimer.cancel();
		}
	}
}
