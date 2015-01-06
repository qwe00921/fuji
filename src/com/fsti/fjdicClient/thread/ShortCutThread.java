package com.fsti.fjdicClient.thread;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.fsti.fjdicClient.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.Intent.ShortcutIconResource;
import android.os.Handler;
import android.util.Log;





/**
 * 创建快捷方式线程
 * @author 金涛
 *
 */
public class ShortCutThread extends Thread {
	private Context myContext;
	
	public ShortCutThread(Context c){			
		this.myContext = c;
	}
	
	
	@Override
	public void run() {
		addShortCut();			
		super.run();
	}


	//添加快捷方式
	private void addShortCut(){
System.out.println("shortcut start!!!!");		
		Intent shortcut = new Intent("com.android.launcher.action.INSTALL_SHORTCUT");
		//快捷方式的名称
		shortcut.putExtra(Intent.EXTRA_SHORTCUT_NAME, "福桔装饰");
		shortcut.putExtra("duplicate", false); //不允许重复创建
		//--说明--经过多次试验，发现卸载时应用程序是根据创建intent时的action来判断是否删除桌面的快捷方式，当action为Intent.ACTION_MAIN时
		//卸载后系统会删除action为Intent.ACTION_MAIN的shortcut。如果action不为Intent.ACTION_MAIN,则系统不会自动删除shortcut.
		//下方注释的三行代码虽然可以打开首页，但卸载时shortcut不会自动被删除.推断系统可能是使用的是模糊匹配(intent-filter)action来删除生成的shortcut。		
	    Intent respondIntent = new Intent(Intent.ACTION_MAIN);
	    respondIntent.addCategory(Intent.CATEGORY_LAUNCHER);
	    respondIntent.setComponent(new ComponentName(myContext.getPackageName(),"com.fsti.communityClient.activity.home.HomeMainActivity"));
	    //respondIntent.putExtra("sms_notification", "sms_notification");
	    shortcut.putExtra(Intent.EXTRA_SHORTCUT_INTENT, respondIntent);   
		//快捷方式的图标
		ShortcutIconResource iconRes = Intent.ShortcutIconResource.fromContext(myContext, R.drawable.icon); 
		shortcut.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, iconRes);
		myContext.sendBroadcast(shortcut);	
System.out.println("shortcut end!!!!");			
	}	
}
