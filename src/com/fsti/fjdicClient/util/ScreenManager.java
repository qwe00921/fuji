package com.fsti.fjdicClient.util;

import java.util.Stack;   


import android.app.Activity;   
  
public class ScreenManager {
    private static Stack<Activity> activityStack;   
    private static ScreenManager instance;   
    public ScreenManager(){
    	activityStack = new Stack<Activity>();
    }
    public static ScreenManager getScreenManager(){   
        if(instance==null){   
            instance=new ScreenManager();   
        }   
        return instance;   
    }   
    public void popActivity(){   
    	System.out.println("before popActivity  activityStack.size()-----"+activityStack.size());
        Activity activity=activityStack.lastElement();   
        if(activity!=null){   
            activity.finish();   
            activity=null;   
        } 
        System.out.println("after popActivity  activityStack.size()-----"+activityStack.size());
    }   
    public void popActivity(Activity activity){   
        if(activity!=null){   
            activity.finish();   
            activityStack.remove(activity);   
            activity=null;   
        }   
    }   
    public Activity currentActivity(){//当前栈   
        Activity activity=activityStack.lastElement();   
        return activity;   
    }   
    public void pushActivity(Activity activity){//入栈   
        if(activityStack==null){   
            activityStack=new Stack<Activity>();   
        }   
        activityStack.add(activity);   
    }   
       
    public void popAllActivityExceptOne(Class cls){
        while(true){   
            Activity activity=currentActivity();   
            if(activity==null){   
                break;   
            }   
            if(activity.getClass().equals(cls) ){   
                break;   
            }   
            popActivity(activity);   
        }   
    }   
    public void popAllActivity(){//goodsListActivity中sort按钮使用，直接全部finish()   
    	System.out.println("activityStack.size()-----"+activityStack.size());
    	if(activityStack.size()!= 0 ){
    		for (int i = 0; i < activityStack.size(); i++) {
				activityStack.get(i).finish();
				System.out.println("popAllActivity 关闭activity");
			}
    		activityStack.removeAllElements();
    	}
    }   
    public int activityStackSize(){
    	return activityStack.size();
    }   
}  