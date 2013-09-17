/**
 * JokeTable.java
 * 版权所有(C) 2013 
 * 创建:cuiran 2013-8-12 下午1:39:07
 */
package com.joke.database;



import java.util.ArrayList;
import java.util.List;

import com.joke.net.dto.JokeBaseDto;
import com.joke.net.dto.JokeInfo;
import com.joke.util.LogsUtil;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

/**
 * TODO JokeTable
 * @author cuiran
 * @version 1.0.0
 */
public class JokeTable extends SQLiteBase {

	public static final String TAG="JokeTable";
	
	public static final String TableName = "joke_info";
	public static final String Id = "_id";
	public static final String Subject = "_subject";
	public static final String Content = "_content";
	public static final String Link = "_link";
	public static final String CreateDate = "_createDate";
	public static final String Isread = "_isread";
	public static final String Type = "_type";
	
	private static JokeTable _instance = null;
	private static Context context = null;
	
	public synchronized static JokeTable getInstance(Context _context) {
		if (null == _instance || null == context) {
			_instance = new JokeTable(_context);
		}
		return _instance;
	}
	
	/**
	 * @param context
	 */
	public JokeTable(Context context) {
		super(context);
		JokeTable.context=context;
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 *<b>function:</b>检查同一个标题是否存在
	 *@author cuiran
	 *@createDate 2013-8-12 下午1:59:42
	 *@param subject
	 *@return
	 */
	public boolean checkJokeInfoByContent(String subject){
		boolean flag=false;
		Cursor cursor=null;
		try {
		
			cursor = database.query(TableName,null, Subject+"="+subject.trim(),null,null,null,null);
			
			int count=cursor.getCount();
			if(count>0){
				flag=true;
			}else{
				flag=false;
			}
		}catch (Exception e) {
			// TODO: handle exception
			flag=false;
			LogsUtil.e("错误", "执行checkJokeInfoByContent出现错误"+e.getMessage());
		}finally{
			if(null != cursor){
				cursor.close();
				cursor = null;
			}
		}
		return flag;
	}
	
	/**
	 * 
	 *<b>function:</b>添加数据
	 *@author cuiran
	 *@createDate 2013-8-12 下午1:49:19
	 *@param info
	 *@return
	 */
	public boolean addJokeInfo(JokeInfo info){
		boolean flag=false;
		ContentValues values = new ContentValues();
		try{
			values.put(Subject, info.getSubject().trim());
			values.put(Content, info.getContent());
			values.put(Link, info.getLink());
			values.put(CreateDate, info.getCreateDate());
			values.put(Isread, info.getIsread());
			values.put(Type, info.getType());
			if(!checkJokeInfoByContent(info.getSubject())){
				database.insert(TableName, null, values);
				LogsUtil.i(TAG, "添加成功 "+info.getSubject());
			}
		
			values.clear();
		}catch (Exception e) {
			// TODO: handle exception
			LogsUtil.e(TAG, "addJokeInfo出现异常", e);
		}
		return flag;
	}
	
	/**
	 * 
	 *<b>function:</b>点击设置为已读
	 *@author cuiran
	 *@createDate 2013-8-12 下午2:02:47
	 *@param id
	 *@return
	 */
	public boolean updateJokeIsRead(String id){
		boolean flag=false;
		String isread="1";
		String updateSql="update  "+TableName+"  set "+Isread+" = '"+isread+"' " +
				" where "+Id+" = "+id;
		try {
			database.execSQL(updateSql);
			LogsUtil.i(TAG, "修改updateJokeIsRead成功,id="+id);
			flag=true;
		} catch (Exception e) {
			flag=false;
			LogsUtil.e("错误", "执行updateJokeIsRead出现错误"+e.getMessage());
		}
		
		return flag;
	}
	
	public int queryMaxId(String type){
		int maxId=0;
		Cursor cursor=null;
		String querySql="";
		try{
			querySql= " select max("+Id+") from "+TableName+" where  "+Type+"="+type;
			LogsUtil.i(TAG, "querySql="+querySql);
			cursor=database.rawQuery(querySql, null);
			if(cursor.moveToLast()){
				int IdIndex=cursor.getColumnIndex(Id);
				maxId=cursor.getInt(IdIndex);
			}
			
			
			
		}catch (Exception e) {
			// TODO: handle exception
			LogsUtil.e("错误", "执行queryMaxId出现错误"+e.getMessage());
		}finally{
			if(null != cursor){
				cursor.close();
				cursor = null;
			}
		}
		
		return maxId;
	}
	
	public String getString(Cursor cursor,String columName){
		int SubjectIndex=cursor.getColumnIndex(columName);
		return cursor.getString(SubjectIndex);
	}
	
	/**
	 * 
	 *<b>function:</b>查询笑话记录
	 *@author cuiran
	 *@createDate 2013-8-12 下午3:01:26
	 *@param id
	 *@param type
	 *@param isList
	 *@return
	 */
	public JokeBaseDto queryJokes(int id,String type,String isList){
		JokeBaseDto dto=new JokeBaseDto();
		Cursor cursor=null;
		
		try{
			if(id==0){
				id=queryMaxId(type);
			}
			
			String p="";
			if("1".equals(isList)){
				 p=" "+Id+" < "+id;
			}else{
				 p=" "+Id+" <="+id;
			}
			String querySql="select * from "+TableName+" ";
			querySql+=" where "+Type+"="+type+" and "+p+" order by "+Id+" desc ";
			LogsUtil.i(TAG, "querySql="+querySql);
			cursor=database.rawQuery(querySql, null);
			if(cursor==null){
				return dto;
			}
			if(cursor.getCount()<=0){
				return dto;
			}
			cursor.moveToFirst();
			List<JokeInfo> jokes=new ArrayList<JokeInfo>();
			for (cursor.moveToFirst(); !(cursor.isAfterLast()); cursor.moveToNext()) {
				JokeInfo info=new JokeInfo();
				int IdIndex=cursor.getColumnIndex(Id);
				int  jokeId=cursor.getInt(IdIndex);
				info.setId(jokeId);
				
				int SubjectIndex=cursor.getColumnIndex(Subject);
				info.setSubject(cursor.getString(SubjectIndex));
				
				int ContentIndex=cursor.getColumnIndex(Content);
				info.setContent(cursor.getString(ContentIndex));
				
				int LinkIndex=cursor.getColumnIndex(Link);
				info.setLink(cursor.getString(LinkIndex));
				info.setCreateDate(getString(cursor, CreateDate));
				info.setIsread(getString(cursor, Isread));
				info.setType(getString(cursor, Type));
				
				
				jokes.add(info);
			}
			
		}catch (Exception e) {
			// TODO: handle exception
			LogsUtil.e("错误", "执行queryJokes出现错误"+e.getMessage());
		}finally{
			if(null != cursor){
				cursor.close();
				cursor = null;
			}
		}
		
		
		return dto;
	}
	
	/* (non-Javadoc)
	 * @see com.joke.database.SQLiteBase#closeDatabase()
	 */
	@Override
	public void closeDatabase() {
		// TODO Auto-generated method stub

	}

}
