package com.fsti.fjdicClient.activity;


import java.io.Serializable;

import com.fsti.fjdicClient.util.asyncUtil.AsyncTaskUtils;
import com.fsti.fjdicClient.util.asyncUtil.CallEarliest;
import com.fsti.fjdicClient.util.asyncUtil.Callable;
import com.fsti.fjdicClient.util.asyncUtil.Callback;
import com.fsti.fjdicClient.util.asyncUtil.ProgressCallable;
import com.umeng.analytics.MobclickAgent;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Toast;

/**
 * 通用的activity类
 * @author shaocj
 *
 */

public abstract class BaseActivity  extends Activity{
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		MobclickAgent.onResume(this);
	}


	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		MobclickAgent.onPause(this);
	}


	protected Context myContext;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);// 设置不能横屏
		//初始化上下文
		myContext = getBaseContext();
		//init();
		MobclickAgent.onError(this);
	}
	
	/**
	 * 初始化页面
	 */
	public void init(){
		initValue();
		bindEvent();
	}
	/**
	 * 禁用搜索功能
	 */
	@Override
	public void startSearch(String initialQuery, boolean selectInitialQuery,
			Bundle appSearchData, boolean globalSearch) {
		// TODO Auto-generated method stub
	}
	
	/**
	 * 初始化页面参数  比如 textview.setText("hello")
	 */
	public abstract void initValue();
	
	/**
	 * 绑定事件
	 */
	public abstract void bindEvent();
	     
	/**
	 * 返回键处理
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		return super.onKeyDown(keyCode, event);
		
	}

	
//	@Override
//	public boolean dispatchKeyEvent(KeyEvent event) {
//		if (event.getKeyCode() == KeyEvent.KEYCODE_BACK
//				&& event.getRepeatCount() == 0 && event.getAction() == 0 && ApplicationUtil.MAINACTIVITY != null) {
//			AlertDialog.Builder builder = new AlertDialog.Builder(this);
//			builder.setMessage("确定要退出吗?");
//			builder.setTitle("提示");
//			builder.setPositiveButton("确认",
//					new android.content.DialogInterface.OnClickListener() {
//						@Override
//						public void onClick(DialogInterface dialog, int which) {
//							dialog.dismiss();
//							ApplicationUtil.MAINACTIVITY.finish();
//							ApplicationUtil.popWindowCloseMainActivity = null;
//						}
//
//					});
//			builder.setNegativeButton("取消",
//					new android.content.DialogInterface.OnClickListener() {
//						@Override
//						public void onClick(DialogInterface dialog, int which) {							
//							dialog.dismiss();
//						}
//					});
//			builder.create().show();
//			return false;
//		}
//		return super.dispatchKeyEvent(event);
//	}

	
    /** 
     * 封装的asynctask方法，此方法没有进度框. 
     *  
     * @param callEarliest 运行于主线程，最先执行此方法. 
     * @param callable 运行于异步线程,第二执行此方法. 
     * @param callback 运行于主线程,最后执行此方法. 
     */  
    public <T> void doAsync(final CallEarliest<Object> callEarliest,  
            final Callable<Object> callable, final Callback<String> callback) {  
        AsyncTaskUtils.doAsync(callEarliest, callable, callback);  
    }  
  
    /** 
     * 封装的asynctask方法，此方法拥有进度对话框，并支持定义样式. 
     * @param pContext  上下文 
     * @param styleID   对话框样式 ProgressDialog.STYLE_HORIZONTAL|ProgressDialog.STYLE_SPINNER 
     * @param pTitle    标题 
     * @param pMessage  内容 
     * @param pCallEarliest  运行于主线程，最先执行此方法. 
     * @param progressCallable 运行于异步线程,用于传递对话框进度. 
     * @param pCallback  运行于主线程,最后执行此方法. 
     */  
    public <T> void doProgressAsync(final Context pContext, final int styleID,  
            final String pTitleResID, final String pMessageResID,  
            final CallEarliest<T> pCallEarliest, final ProgressCallable<T> pCallable,  
            final Callback<T> pCallback) {  
  
        AsyncTaskUtils.doProgressAsync(pContext, styleID, pTitleResID,  
                pMessageResID, pCallEarliest, pCallable, pCallback);  
    }  
      
      
    /** 
     * 封装的asynctask方法，此方法拥有进度对话框，并支持定义样式. 
     * @param pContext  上下文 
     * @param styleID   对话框样式 ProgressDialog.STYLE_HORIZONTAL|ProgressDialog.STYLE_SPINNER 
     * @param pTitle    标题,资源id 
     * @param pMessage  内容,资源id 
     * @param pCallEarliest  运行于主线程，最先执行此方法. 
     * @param progressCallable 运行于异步线程,用于传递对话框进度. 
     * @param pCallback  运行于主线程,最后执行此方法. 
     */  
    public <T> void doProgressAsync(final Context pContext, final int styleID,  
            final int pTitleResID, final int pMessageResID,  
            final CallEarliest<T> pCallEarliest, final ProgressCallable<T> pCallable,  
            final Callback<T> pCallback) {  
  
        AsyncTaskUtils.doProgressAsync(pContext, styleID, pTitleResID,  
                pMessageResID, pCallEarliest, pCallable, pCallback);  
    }  
}
