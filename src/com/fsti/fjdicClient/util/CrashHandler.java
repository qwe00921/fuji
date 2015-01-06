package com.fsti.fjdicClient.util;

import java.lang.Thread.UncaughtExceptionHandler;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import com.fsti.fjdicClient.R;
import com.fsti.fjdicClient.ApplicationUtil;


import android.content.Context;
import android.os.Build;
import android.os.Looper;

/**
 * 处理全局异常
 * @author 金涛
 *
 */
public class CrashHandler implements UncaughtExceptionHandler {

	/** 系统默认的UncaughtException处理类 */
	private Thread.UncaughtExceptionHandler mDefaultHandler;
	/** CrashHandler实例 */
	private static CrashHandler INSTANCE;
	/** 程序的Context对象 */
	private static Context mContext;

	/** 保证只有一个CrashHandler实例 */
	private CrashHandler() {
	}

	/** 获取CrashHandler实例 ,单例模式 */
	public static CrashHandler getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new CrashHandler();			
		}
		return INSTANCE;
	}

	/**
	 * 
	 * 初始化,注册Context对象,
	 * 
	 * 获取系统默认的UncaughtException处理器,
	 * 
	 * 设置该CrashHandler为程序的默认处理器
	 * 
	 * 
	 * 
	 * @param ctx
	 */
	public void init(Context ctx) {
		mContext = ctx;
		mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
		Thread.setDefaultUncaughtExceptionHandler(this);
	}

	/**
	 * 
	 * 当UncaughtException发生时会转入该函数来处理
	 */
	@Override
	public void uncaughtException(Thread thread, Throwable ex) {
		final Throwable f_ex = ex;
		System.out.println("_________________handler_UncaughtException__________________");
		if (mDefaultHandler != null) {
			// 开启一个线程处理异常消息
			new Thread() {
				@Override
				public void run() {
					Looper.prepare();
					handleException(f_ex);
				}
			}.start();
			//主线程抛出异常，避免出现异常的循环捕获造成系统无响应
			mDefaultHandler.uncaughtException(thread, ex); 
		} 
	}
	
	/**
	 * 异常信息处理
	 * @param ex
	 */	 
	public void handleException(final Throwable ex){
		String exceptionContent = getExceptionContent(ex);
		postData(exceptionContent);		
	}

	/**
	 * 获取异常信息
	 * @param ex
	 * @return
	 */	 
	private String getExceptionContent(final Throwable ex) {
		StringBuffer exception_content  = new StringBuffer();
		if (ex != null) {			
			//主机号码
			//String telephone = ContactUtil.getHostTelephone(mContext);
			String telephone = PersistenceVarUtil.getApplicationStringValue(ApplicationUtil.myContext, "FILENAMEPHONEMOVESET", "accountId","");
			//String telephone = null;
			//主机型号
			String mobile_type = Build.MANUFACTURER + ":" + Build.MODEL;
			//时间
			Date date = new Date();
			String nowDateStr = DataUtil.formatDateToString(date, DataUtil.DATE_TIME_STYLE_All);
			//抛出异常的文件名
			String e_filename = null;
			//异常原因
			String e_cause = ex.toString();
			//异常堆栈
			String e_stack = null;
			//操作系统版本号
			String os_version = Build.VERSION.RELEASE+ ":" + Build.VERSION.SDK;			
			//异常消息
			String e_message = ex.getMessage();
						 
			// final String msg = ex.getLocalizedMessage();
			StringBuffer sb = new StringBuffer();
			final StackTraceElement[] stack = ex.getStackTrace();
			for(StackTraceElement entity: stack){
				if(e_filename == null){
					e_filename = entity.getFileName();
				}
				String s_fileName = entity.getFileName();
				String s_methodName = entity.getMethodName();
				String lineNumber = String.valueOf(entity.getLineNumber());
				String s_content = s_fileName + ":" + s_methodName + " -- " + lineNumber + ";";
System.out.println("s_content=" + s_content);				
				sb.append(s_content);				
			}			
			//e_stack = sb.substring(0, 4000);
			e_stack = sb.toString();			
			
			if(telephone != null && !telephone.equals("")){
				exception_content.append(telephone + "^");
			}
			else{
				exception_content.append("^");
			}
			
			if(mobile_type != null && !mobile_type.equals("")){
				exception_content.append(mobile_type + "^");
System.out.println("mobile_type=" + mobile_type);				
			}
			else{
				exception_content.append("^");
			}
			
			if(nowDateStr != null && !nowDateStr.equals("")){
				exception_content.append(nowDateStr + "^");
			}
			else{
				exception_content.append("^");
			}
			
			if(e_filename != null && !e_filename.equals("")){
				exception_content.append(e_filename + "^");
			}
			else{
				exception_content.append("^");
			}
			
			if(e_cause != null && !e_cause.equals("")){
				exception_content.append(e_cause + "^");
			}
			else{
				exception_content.append("^");
			}
			
			if(e_stack != null && !e_stack.equals("")){
				exception_content.append(e_stack + "^");
			}
			else{
				exception_content.append("^");
			}

			if(os_version != null && !os_version.equals("")){
				exception_content.append(os_version + "^");
System.out.println("os_version=" + os_version);				
			}
			else{
				exception_content.append("^");
			}
			
			if(e_message != null && !e_message.equals("")){
				exception_content.append(e_message);
			}			
System.out.println("exception_content=" + exception_content.toString());						
		}		
		return exception_content.toString();
	}	
	
	/**
	 * 发送数据
	 * @param content 发送内容
	 * @return
	 */
	private String postData(String content){	
System.out.println("postData-------------");		
		Map<String,Object> postParams = new LinkedHashMap<String,Object>();
		postParams.put("exception_content", content);
		String url = mContext.getString(R.string.debugCollect);
System.out.println("eUrl=" + url);		
		String returnStr = "";
		try {
			returnStr = HttpUtil.postData(url, postParams,"UTF-8");
System.out.println("post result=" + returnStr);			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return returnStr;
	}
	
}