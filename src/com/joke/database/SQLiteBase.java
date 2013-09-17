/**
 * SQLiteBase.java
 * 版权所有(C) 2013 
 * 创建:cuiran 2013-7-11 下午6:20:19
 */
package com.joke.database;


import com.joke.util.LogsUtil;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * TODO数据库
 * @author cuiran
 * @version TODO
 */
public abstract class SQLiteBase extends SQLiteOpenHelper {

public static final String TAG="SQLiteBase";
	
	public static String DATABASENAME = "jokelife.db";
	
	protected SQLiteDatabase database;
	
	public SQLiteBase(Context context) {
		
		super(context, DATABASENAME, null, 1);
		
		database = this.getWritableDatabase();
	}
	

	/* (non-Javadoc)
	 * @see android.database.sqlite.SQLiteOpenHelper#onCreate(android.database.sqlite.SQLiteDatabase)
	 */
	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		try{
			db.beginTransaction(); 
			/**
			 * 群组列表
			 */
			String jokeSql ="create table if not exists "+JokeTable.TableName+ " (_id"
					+ " integer primary key autoincrement not null," 
					+JokeTable.Subject+" varchar ,"
					+JokeTable.Content+" varchar ,"
					+JokeTable.Link+" varchar ,"
					+JokeTable.CreateDate+" varchar ,"
					+JokeTable.Isread+" varchar ,"
					+JokeTable.Type+" varchar )";
			
			
			db.execSQL(jokeSql);
			
			db.setTransactionSuccessful();
			
			db.endTransaction();
			LogsUtil.i(TAG, "创建成功"+JokeTable.TableName);
		}catch (Exception e) {
			// TODO: handle exception
			LogsUtil.e("错误", "执行创建表时出现错误"+e.getMessage());
		}
		
		
		
	}

	 /**
     * 每一次数据库版本号发生变动时触发此方法
     * 比如如果想往数据库中再插入一些表、字段或者其他信息时通过修改数据库版本号来触发此方法
     */
	/* (non-Javadoc)
	 * @see android.database.sqlite.SQLiteOpenHelper#onUpgrade(android.database.sqlite.SQLiteDatabase, int, int)
	 */
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		
		LogsUtil.i(TAG, "onUpgrade 修改table ");
		
	}
	
	/**
	 * 执行关闭数据库的操作
	 */
	public abstract void closeDatabase();

}
