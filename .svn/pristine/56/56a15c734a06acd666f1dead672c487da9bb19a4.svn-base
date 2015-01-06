package com.fsti.fjdicClient.util;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/**
 * 持久化变量工具类
 * @author 金涛
 *
 */
public class PersistenceVarUtil {
		
	/**
	 * 获取页面级别的持久化数据
	 * @param key 键
	 * @param a activity
	 * @param defaultValue 默认值
	 * @return
	 */
	public static String getValue(String key,Activity a,String defaultValue){
		SharedPreferences pre = a.getPreferences(0);		
		String values = pre.getString(key, defaultValue);
		return values;
	}
	
	/**
	 * 设置页面级别的持久化数据
	 * @param key 键 
	 * @param value 值
	 * @param a activity
	 */
	public static void setValue(String key,String value,Activity a){
		SharedPreferences.Editor editor = a.getPreferences(0).edit();		
		editor.putString(key, value);
		editor.commit();
	}
	
	/**
	 * 获取页面级别的持久化数据
	 * @param key 键
	 * @param a activity
	 * @param defaultValue 默认值
	 * @return
	 */
	public static int getValue(String key,Activity a,int defaultValue){
		SharedPreferences pre = a.getPreferences(0);		
		int values = pre.getInt(key, defaultValue);
		return values;
	}
	
	/**
	 * 设置页面级别的持久化数据
	 * @param key 键 
	 * @param value 值
	 * @param a activity
	 */
	public static void setValue(String key,int value,Activity a){
		SharedPreferences.Editor editor = a.getPreferences(0).edit();		
		editor.putInt(key, value);
		editor.commit();
	}
	
	
	
	/**
	 * 获取应用程序级别的持久化数据_字符串
	 * @param c 上下文
	 * @param fileName 保存文件名
	 * @param key 键值
	 * @param defaultValue 默认值
	 * @return
	 */
	public static String getApplicationStringValue(Context c,String fileName,String key,String defaultValue){
		SharedPreferences prefs = c.getSharedPreferences(fileName, c.MODE_PRIVATE);
		String value = prefs.getString(key, defaultValue);
		return value;
	}
	
	/**
	 * 设置应用程序级别的持久话数据_字符串
	 * @param c 上下文
	 * @param fileName 保存文件名
	 * @param key 键值
	 * @param value 设置数据
	 */
	public static void setApplicationStringValue(Context c, String fileName,String key,String value){
		SharedPreferences prefs = c.getSharedPreferences(fileName, c.MODE_PRIVATE);
		Editor editor = prefs.edit();
		editor.putString(key, value);
		editor.commit();
	}
	
	/**
	 * 获取应用程序级别的持久化数据_整型
	 * @param c 上下文
	 * @param fileName 保存文件名
	 * @param key 键值
	 * @param defaultValue 默认值
	 * @return
	 */
	public static int getApplicationIntValue(Context c,String fileName,String key,int defaultValue){
		SharedPreferences prefs = c.getSharedPreferences(fileName, c.MODE_PRIVATE);
		int value = prefs.getInt(key, defaultValue);
		return value;
	}
	
	/**
	 * 设置应用程序级别的持久话数据_整型
	 * @param c 上下文
	 * @param fileName 保存文件名
	 * @param key 键值 
	 * @param value 设置数据
	 */
	public static void setApplicationIntValue(Context c, String fileName,String key,int value){
		SharedPreferences prefs = c.getSharedPreferences(fileName, c.MODE_PRIVATE);
		Editor editor = prefs.edit();
		editor.putInt(key, value);
		editor.commit();
	}				
	
	
	/**
	 * 获取应用程序级别的持久化数据_长整型
	 * @param c 上下文
	 * @param fileName 保存文件名
	 * @param key 键值
	 * @param defaultValue 默认值
	 * @return
	 */
	public static Long getApplicationLongValue(Context c,String fileName,String key,Long defaultValue){
		SharedPreferences prefs = c.getSharedPreferences(fileName, c.MODE_PRIVATE);
		Long value = prefs.getLong(key, defaultValue);
		return value;
	}
	
	/**
	 * 设置应用程序级别的持久话数据_长整型
	 * @param c 上下文
	 * @param fileName 保存文件名
	 * @param key 键值 
	 * @param value 设置数据
	 */
	public static void setApplicationLongValue(Context c, String fileName,String key,Long value){
		SharedPreferences prefs = c.getSharedPreferences(fileName, c.MODE_PRIVATE);
		Editor editor = prefs.edit();
		editor.putLong(key, value);
		editor.commit();
	}		
}
