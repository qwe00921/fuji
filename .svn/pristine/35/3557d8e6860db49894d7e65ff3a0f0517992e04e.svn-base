package com.fsti.fjdicClient.bean;
 import java.util.LinkedList;
import java.util.List;

import com.fsti.fjdicClient.activity.home.HomeMainActivity;
import com.fsti.fjdicClient.util.GlobalVarUtil;

 import android.app.Activity;
import android.app.Application;

public class ExitApplication extends Application {
 

 private List<Activity> activityList=new LinkedList<Activity>();
 private List<Activity> newlist=new LinkedList<Activity>();

 private static ExitApplication instance;

 private ExitApplication()
 {
 }
 //单例模式中获取唯一的ExitApplication 实例
 public static ExitApplication getInstance()
 {
 if(null == instance)
 {
 instance = new ExitApplication();
}
return instance;

}
 //添加Activity 到容器中
 public void addActivity(Activity activity)
 {
	 if(!activityList.contains(activity)){
		 System.out.println("=============activity++++++===="+activity);
		 activityList.add(activity);
	 }

 }
 //遍历所有Activity 并finish

 public void exit()
 {

 for(Activity activity:activityList)
 {
	 System.out.println("=============activity------===="+activity);
 activity.finish();
 newlist.add(activity);

 }
 delete();
 }
 private void delete(){
	 for(Activity activity:newlist){
		 activityList.remove(activity);
	 }
 }
 }
