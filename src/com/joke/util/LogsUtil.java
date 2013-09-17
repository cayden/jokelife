/**
 * LogsUtil.java
 * 版权所有(C) 2013 
 * 创建:cuiran 2013-4-17 上午11:42:15
 */
package com.joke.util;



import android.util.Log;

/**
 * TODO 日志使用类
 * @author cuiran
 * @version TODO
 */
public class LogsUtil {
	private static boolean flag=true;
//	private static Logger logger = Logger.getLogger(LogsUtil.class);  
	public static void d(String tag,String msg){
		if(flag){
			Log.d(tag, msg);
		}
		
	}

	public static void d(String tag,String msg,Throwable tr){
		if(flag){
			Log.d(tag, msg,tr);
		}
		
	}
	
	public static void i(String tag,String msg){
		if(flag){
			Log.i(tag, msg);
//			logger.debug(tag+"-"+msg);	       
		}
		
	}

	public static void i(String tag,String msg,Throwable tr){
		if(flag){
			Log.i(tag, msg,tr);
		}
		
	}
	
	public static void e(String tag,String msg){
		if(flag){
			Log.e(tag, msg);
//			logger.error(tag+"-"+msg);	    
		}
		
	}
	public static void e(String tag,Exception e){
		if(flag){
			if(null!=e){
				Log.e(tag, e.getMessage());
			}
		
//			logger.error(tag+"-"+msg);	    
		}
		
	}

	public static void e(String tag,String msg,Throwable tr){
		if(flag){
			Log.e(tag, msg,tr);
		}
		
	}
}
