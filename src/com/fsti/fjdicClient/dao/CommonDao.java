package com.fsti.fjdicClient.dao;

import java.util.List;

import com.fsti.fjdicClient.ApplicationUtil;
import com.fsti.fjdicClient.util.DBHelper;

import android.content.ContentValues;
import android.database.Cursor;
import android.widget.Toast;



/**
 * 数据库相关公共操作类
 * @author 金涛
 *
 */
public class CommonDao {

	
	
	
//	private static String SQL_Delete_Table_HomePageActivityInfo = "drop table if exists " + Table_Name_HomePageActivityInfo;
	
	
	/**
	 * 创建表
	 * @param sql 建表语句
	 * @param tableName 表名
	 */
	public static void creatTable(String sql,String tableName){
		DBHelper dbHelper = null;
		try {
			dbHelper = new DBHelper(ApplicationUtil.myContext);
			dbHelper.open();
			if(dbHelper.isTableExist(tableName)){
				//Toast.makeText(ApplicationUtil.myContext, TABLE_USERS+"表已经存在!", Toast.LENGTH_SHORT).show();
			}else{
				//String sql = "CREATE TABLE users (_id integer primary key autoincrement, name text, password text)";  
				dbHelper.execSQL(sql);
				//Toast.makeText(myContext, TABLE_USERS+"表不存在，创建成功!", Toast.LENGTH_SHORT).show();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(dbHelper!=null){
				dbHelper.closeDb();
			}
		}
	}
	
	/**
	 * 删除表
	 * @param tableName 表名
	 */
	public static void deleteTable(String tableName){
		DBHelper dbHelper = null;
		
		try {
			dbHelper = new DBHelper(ApplicationUtil.myContext);
			dbHelper.open();
			if(dbHelper.isTableExist(tableName)){
				String deleteSql = "drop table if exists "+tableName;   
				dbHelper.execSQL(deleteSql);
				//Toast.makeText(myContext, TABLE_USERS+"表存在，删除成功!", Toast.LENGTH_SHORT).show();
			}else{
				//Toast.makeText(myContext, TABLE_USERS+"表不存在，不用删除!", Toast.LENGTH_SHORT).show();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(dbHelper!=null){
				dbHelper.closeDb();
			}
		}
	}
	
	
	/**
	 * 插入单条数据
	 * @param tableName 表名
	 * @param entity 插入数据
	 */
	public static void insert(String tableName,ContentValues entity){
		DBHelper dbHelper = null;
		try {
			dbHelper = new DBHelper(ApplicationUtil.myContext);
			dbHelper.open();
			if(dbHelper.isTableExist(tableName)){
				dbHelper.insert(tableName, entity);
//				Toast.makeText(myContext, "新增数据成功", Toast.LENGTH_SHORT).show();
//				myCursor.requery();
//				listView.invalidateViews();
//				name.setText("");
			}else{
				//Toast.makeText(myContext, TABLE_USERS+"表不存在，请先创建表!", Toast.LENGTH_SHORT).show();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(dbHelper!=null){
				dbHelper.closeDb();
			}
		}
	}
	
	/**
	 * 批量插入数据
	 * @param tableName 表名
	 * @param list 插入数据
	 */
	public static void insert(String tableName,List<ContentValues> list){
		DBHelper dbHelper = null;
		try {
			dbHelper = new DBHelper(ApplicationUtil.myContext);
			dbHelper.open();
			if(dbHelper.isTableExist(tableName)){
				dbHelper.insertList(tableName, list);
			}else{
				//Toast.makeText(myContext, TABLE_USERS+"表不存在，请先创建表!", Toast.LENGTH_SHORT).show();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(dbHelper!=null){
				dbHelper.closeDb();
			}
		}
	}
	/**
	 * 查找数据
	 * yyy
	 
	 */
	public static Cursor select(String sql){
		DBHelper dbHelper = null;
		Cursor cursor=null;
		try {
			dbHelper = new DBHelper(ApplicationUtil.myContext);
			dbHelper.open();
		cursor=	dbHelper.select(sql);
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(dbHelper!=null){
				//dbHelper.closeDb();
			}
		}
		
		return cursor;
	}
	
	
	/**
	 * 查找列表
	 * yyy
	 
	 */
	public static Cursor findList(String tableName, String[] columns,
			String whereClause, String[] whereArgs, String groupBy,
			String having, String orderBy){
		DBHelper dbHelper = null;
		Cursor cursor=null;
		try {
			dbHelper = new DBHelper(ApplicationUtil.myContext);
			dbHelper.open();
		cursor=	dbHelper.findList(tableName, columns, whereClause, whereArgs, groupBy, having, orderBy);
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(dbHelper!=null){
				//dbHelper.closeDb();
			}
		}
		
		return cursor;
	}
	
	/**
	 * 更新数据
	 * yyy
	 
	 */
	public static boolean update(String tableName, ContentValues initialValues,
			String whereClause) {
		DBHelper dbHelper = null;
		boolean  result = false;
		try {
			dbHelper = new DBHelper(ApplicationUtil.myContext);
			dbHelper.open();
			result=dbHelper.update(tableName, initialValues, whereClause);
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(dbHelper!=null){
				//dbHelper.closeDb();
			}
		}
		return result;
	}
	
	/**
	 * 删除数据
	 * yyy
	 
	 */
	public static boolean delete(String tableName, String deleteCondition) {
		DBHelper dbHelper = null;
		boolean  result = false;
		try {
			dbHelper = new DBHelper(ApplicationUtil.myContext);
			dbHelper.open();
			result=dbHelper.delete(tableName, deleteCondition);
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(dbHelper!=null){
				//dbHelper.closeDb();
			}
		}
		return result;
	}
	
	
	
	/**
	 * 删除表数据
	 * @param tableName 表名
	 */
	public static void deleteTableData(String tableName){
		DBHelper dbHelper = null;
		
		try {
			dbHelper = new DBHelper(ApplicationUtil.myContext);
			dbHelper.open();
			if(dbHelper.isTableExist(tableName)){
				String deleteSql = "delete from "+tableName;   
				dbHelper.execSQL(deleteSql);
				//Toast.makeText(myContext, TABLE_USERS+"表存在，删除成功!", Toast.LENGTH_SHORT).show();
			}else{
				//Toast.makeText(myContext, TABLE_USERS+"表不存在，不用删除!", Toast.LENGTH_SHORT).show();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(dbHelper!=null){
				dbHelper.closeDb();
			}
		}
	}
}
