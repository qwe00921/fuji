package com.fsti.fjdicClient.util;

import java.util.List;

import com.fsti.fjdicClient.R;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper {
	static private DatabaseHelper mDbHelper;
	static private SQLiteDatabase mDb;

	private static String DATABASE_NAME = "CommunityClient";

	private static final int DATABASE_VERSION = 1;

	private final Context mCtx;

	private static class DatabaseHelper extends SQLiteOpenHelper {

		DatabaseHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {

		}
		
		/**
		* 执锟叫革拷锟斤拷 锟斤拷锟斤拷锟斤拷锟�锟斤拷执锟叫革拷锟铰诧拷锟斤拷
		* oldVersion 锟较版本锟斤拷
		* newVersio 锟铰版本锟斤拷
		*/
		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			
		}
	}
  
	public DBHelper(Context ctx) {
		this.mCtx = ctx;
	}

	public DBHelper open() throws SQLException {
		mDbHelper = new DatabaseHelper(mCtx);
		mDb = mDbHelper.getWritableDatabase();
		return this;
	}

	public void closeDb() {

		mDb.close();
		mDbHelper.close();
	}

	/**
	 * 锟斤拷锟斤拷锟斤拷锟�
	 * @param tableName 锟斤拷锟斤拷
	 * @param initialValues 锟街讹拷锟斤拷锟接χ礐ontentValues
	 * @return
	 */
	public long insert(String tableName, ContentValues initialValues) {

		return mDb.insert(tableName, null, initialValues);
	}

	/**
	 * 删锟斤拷锟斤拷锟�
	 * @param tableName 锟斤拷锟斤拷
	 * @param deleteCondition 删锟斤拷锟斤拷锟斤拷
	 * @return
	 */
	public boolean delete(String tableName, String deleteCondition) {
		return mDb.delete(tableName, deleteCondition, null) > 0;
	}

	/**
	 * 锟斤拷锟斤拷锟斤拷锟�
	 * @param tableName 锟斤拷锟斤拷
	 * @param initialValues 锟斤拷锟铰碉拷锟街讹拷锟斤拷锟斤拷锟斤拷锟�
	 * @param whereClause 锟斤拷锟斤拷锟斤拷锟�
	 * @return
	 */
	public boolean update(String tableName, ContentValues initialValues,
			String whereClause) {
		int returnValue = mDb.update(tableName, initialValues, whereClause,
				null);
		return returnValue > 0;
	}

	/**
	 * 锟斤拷询锟叫憋拷
	 * @param tableName 锟斤拷锟斤拷
	 * @param columns 锟斤拷询锟斤拷锟街讹拷
	 * @param whereClause 锟斤拷询锟斤拷锟斤拷
	 * @param whereArgs 锟斤拷询锟斤拷锟斤拷锟斤拷'?'锟芥换
	 * @param groupBy 锟斤拷锟斤拷
	 * @param having 锟斤拷锟斤拷锟缴秆�
	 * @param orderBy 锟斤拷锟斤拷
	 * @return
	 */
	public Cursor findList(String tableName, String[] columns,
			String whereClause, String[] whereArgs, String groupBy,
			String having, String orderBy) {

		return mDb.query(tableName, columns, whereClause, whereArgs, groupBy,
				having, orderBy);
	}
	
	/**
	 * 锟斤拷取锟斤拷锟�
	 * @param sql
	 * @return
	 */
	public Cursor select(String sql){
		return mDb.rawQuery(sql, null);
	}

	/**
	 * 执锟斤拷sql 锟斤拷锟斤拷sql 要执锟叫碉拷sql
	 * 
	 * */
	public void execSQL(String sql) {
		mDb.execSQL(sql);

	}

	/**
	 * 锟叫讹拷tableName锟斤拷锟角凤拷锟斤拷锟�
	 * @param tabName 锟斤拷锟斤拷
	 * @return
	 */
	public boolean isTableExist(String tableName) {
		boolean result = false;
		if (tableName == null) {
			return false;
		}

		try {
			Cursor cursor = null;
			String sql = "select count(1) as c from sqlite_master where type ='table' and name ='"
					+ tableName.trim() + "' ";
			cursor = mDb.rawQuery(sql, null);
			if (cursor.moveToNext()) {
				int count = cursor.getInt(0);
				if (count > 0) {
					result = true;
				}
			}

			cursor.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 锟叫讹拷tabName锟斤拷columnName锟角凤拷锟斤拷锟�
	 * @param tabName 锟斤拷锟斤拷
	 * @param columnName 锟街讹拷锟斤拷
	 * @return 
	 */
	public boolean isColumnExist(String tableName, String columnName) {
		boolean result = false;
		if (tableName == null) {
			return false;
		}

		try {
			Cursor cursor = null;
			String sql = "select count(1) as c from sqlite_master where type ='table' and name ='"
					+ tableName.trim()
					+ "' and sql like '%"
					+ columnName.trim() + "%'";
			cursor = mDb.rawQuery(sql, null);
			if (cursor.moveToNext()) {
				int count = cursor.getInt(0);
				if (count > 0) {
					result = true;
				}
			}

			cursor.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 锟斤拷锟斤拷锟斤拷锟斤拷
	 * @param tableName 锟斤拷锟斤拷
	 * @param list 锟叫憋拷锟斤拷锟�
	 */
	public void insertList(String tableName,List<ContentValues> list){
		//锟街讹拷锟斤拷锟矫匡拷始锟斤拷锟斤拷
		mDb.beginTransaction();
		for(ContentValues entity: list){
			mDb.insert(tableName, null, entity);
		}
		//锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷晒锟斤拷锟斤拷锟斤拷锟斤拷没锟斤拷远锟斤拷毓锟斤拷锟斤拷峤�
		mDb.setTransactionSuccessful();        
		//锟斤拷锟斤拷锟斤拷锟�
		mDb.endTransaction();       
	}
	
}