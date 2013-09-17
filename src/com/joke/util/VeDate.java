package com.joke.util;

/** 
 日期类 
 * @date   
 * @version 1.0 
 */
import java.util.*;
import java.sql.Timestamp;
import java.text.*;
import java.util.Calendar;


public class VeDate
{
	/**
	 * 获取现在时间
	 * 
	 * @return 返回时间类型 yyyy-MM-dd HH:mm:ss
	 */
	public static Date getNowDate()
	{
		Date currentTime = new Date();
		
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateString = formatter.format(currentTime);
		ParsePosition pos = new ParsePosition(8);
		Date currentTime_2 = formatter.parse(dateString, pos);
		return currentTime_2;
	}

	/**
	 * 获取现在时间
	 * 
	 * @return返回短时间格式 yyyy-MM-dd
	 */
	public static Date getNowDateShort()
	{
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String dateString = formatter.format(currentTime);
		ParsePosition pos = new ParsePosition(8);
		Date currentTime_2 = formatter.parse(dateString, pos);
		return currentTime_2;
	}

	/**
	 * 获取现在时间
	 * 
	 * @return返回字符串格式 yyyy-MM-dd HH:mm:ss
	 */
	public static String getStringDate()
	{
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateString = formatter.format(currentTime);
		return dateString;
	}

	/**
	 * 获取现在时间
	 * 
	 * @return 返回短时间字符串格式yyyy-MM-dd
	 */
	public static String getStringDateShort()
	{
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String dateString = formatter.format(currentTime);
		return dateString;
	}

	/**
	 * 获取时间 小时:分;秒 HH:mm:ss
	 * 
	 * @return
	 */
	public static String getTimeShort()
	{
		SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
		Date currentTime = new Date();
		String dateString = formatter.format(currentTime);
		return dateString;
	}

	/**
	 * 将给定字符串格式的时间字符串转换为Timestamp格式时间
	 * 
	 * @param strDate
	 * @param formatStyle
	 * @return
	 */
	public static Timestamp strToTimestamp(String strDate, String formatStyle)
	{
		SimpleDateFormat formatter = new SimpleDateFormat(formatStyle);
		ParsePosition pos = new ParsePosition(0);
		Date strtodate = formatter.parse(strDate, pos);
		Timestamp timestamp = new Timestamp(strtodate.getTime());
		return timestamp;
	}

	/**
	 * 将Timestamp格式时间转换为给定字符串格式的时间字符串
	 * 
	 * @param timestamp
	 * @param formatStyle
	 * @return
	 */
	public static String timestampToStr(Timestamp timestamp, String formatStyle)
	{
		SimpleDateFormat formatter = new SimpleDateFormat(formatStyle);
		Date date = new Date(timestamp.getTime());
		return formatter.format(date);
	}

	/**
	 * 将长时间格式字符串转换为时间 yyyy-MM-dd HH:mm:ss
	 * 
	 * @param strDate
	 * @return
	 */
	public static Date strToDateLong(String strDate)
	{
		if("".equals(strDate)||null==strDate){
			return null;
		}
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		ParsePosition pos = new ParsePosition(0);
		Date strtodate = formatter.parse(strDate, pos);
		return strtodate;
	}
	
	/**
	 * 将长时间格式字符串转换为时间 yyyyMMddHHmmss
	 * 
	 * @param strDate
	 * @return
	 */
	public static String strToDateShort(String strDate)
	{
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		ParsePosition pos = new ParsePosition(0);
		Date strtodate = formatter.parse(strDate, pos);
		SimpleDateFormat formatter1 = new SimpleDateFormat("yyyyMMddHHmmss");
		String dateString = formatter1.format(strtodate);
		
		return dateString;
	}
	
	/**
	 * 将长时间格式字符串转换为时间 yyyyMMddHHmmss
	 * 
	 * @param strDate
	 * @return
	 */
	public static Date shorttrToDate(String strDate)
	{
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
		ParsePosition pos = new ParsePosition(0);
		Date strtodate = formatter.parse(strDate, pos);
		
		return strtodate;
	}
	
	/**
	 * 将时间格式 yyyyMMddHHmmss 转换为 HH:mm:ss
	 * 
	 * @param strDate
	 * @return
	 */
	public static String strToDateshort(String strDate)
	{
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
		ParsePosition pos = new ParsePosition(0);
		Date strtodate = formatter.parse(strDate, pos);
		SimpleDateFormat formatter1 = new SimpleDateFormat("MM-dd HH:mm");
		String dateString = formatter1.format(strtodate);
		
		return dateString;
	}
	
	/**
	 * 将时间格式 yyyyMMddHHmmss 转换为 HH:mm:ss
	 * 
	 * @param strDate
	 * @return
	 */
	public static String strToDateshort1(String strDate)
	{
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
		ParsePosition pos = new ParsePosition(0);
		Date strtodate = formatter.parse(strDate, pos);
		SimpleDateFormat formatter1 = new SimpleDateFormat("MM-dd HH:mm");
		String dateString = formatter1.format(strtodate);
		
		return dateString;
	}
	
	/**
	 * 将时间格式 yyyy-MM-dd HH:mm:ss 转换为MM-dd HH:mm
	 * 
	 * @param strDate
	 * @return
	 */
	public static String strToDatestr(String strDate)
	{
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		ParsePosition pos = new ParsePosition(0);
		Date strtodate = formatter.parse(strDate, pos);
		SimpleDateFormat formatter1 = new SimpleDateFormat("MM-dd HH:mm");
		String dateString = formatter1.format(strtodate);
		
		return dateString;
	}
	
	/**
	 * 将时间格式 HH:mm:ss 转换为Date
	 * 
	 * @param strDate
	 * @return
	 */
	public static Date shortStrToDate(String strDate)
	{
		
		SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
		ParsePosition pos = new ParsePosition(0);
		Date strtodate = formatter.parse(strDate, pos);
		Date newDate=new Date();
		
		strtodate.setYear(newDate.getYear());
		strtodate.setMonth(newDate.getMonth());
		strtodate.setDate(newDate.getDate());
		
		return strtodate;
	}
	
	/**
	 * 将时间格式 HH:mm:ss 转换为Date
	 * @param newDate 传入一个Date 获取同一天的
	 * @param strDate
	 * @return
	 */
	public static Date shortStrToDate(Date newDate, String strDate)
	{
		
		SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
		ParsePosition pos = new ParsePosition(0);
		Date strtodate = formatter.parse(strDate, pos);
	
		
		strtodate.setYear(newDate.getYear());
		strtodate.setMonth(newDate.getMonth());
		strtodate.setDate(newDate.getDate());
		
		return strtodate;
	}
	
	/**
	 * 将时间格式 HH:mm:ss 转换为次日的Date
	 * 
	 * @param strDate
	 * @return
	 */
	public static Date shortStrToDateTomorrow(Date newDate,String strDate)
	{
		
		SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
		ParsePosition pos = new ParsePosition(0);
		Date strtodate = formatter.parse(strDate, pos);
		
		strtodate.setYear(newDate.getYear());
		strtodate.setMonth(newDate.getMonth());
		strtodate.setDate(newDate.getDate()+1);
		
		return strtodate;
	}
	
	/**
	 * 将时间格式 HH:mm:ss 转换为前一日的Date
	 * 
	 * @param strDate
	 * @return
	 */
	public static Date shortStrToDateYesterDay(Date newDate,String strDate)
	{
		
		SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
		ParsePosition pos = new ParsePosition(0);
		Date strtodate = formatter.parse(strDate, pos);
	
		
		strtodate.setYear(newDate.getYear());
		strtodate.setMonth(newDate.getMonth());
		strtodate.setDate(newDate.getDate()-1);
		
		return strtodate;
	}

	/**
	 * 将长时间格式字符串转换为时间 yyyy/MM/dd HH:mm:ss
	 * 
	 * @param strDate
	 * @return
	 */
	public static Date strToDateLongPlus(String strDate)
	{
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		ParsePosition pos = new ParsePosition(0);
		Date strtodate = formatter.parse(strDate, pos);
	
		return strtodate;
	}

	/**
	 * 将长时间格式时间转换为字符串 yyyy-MM-dd HH:mm:ss
	 * 
	 * @param dateDate
	 * @return
	 */
	public static String dateToStrLong(java.util.Date dateDate)
	{
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateString = formatter.format(dateDate);
		return dateString;
	}
	
	/**
	 * 将长时间格式时间转换为字符串 yyyy-MM-dd HH:mm:ss
	 * 
	 * @param dateDate
	 * @return
	 */
	public static String dateToStrSh(java.util.Date dateDate)
	{
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
		String dateString = formatter.format(dateDate);
		return dateString;
	}

	/**
	 * 将短时间格式时间转换为字符串 yyyy-MM-dd
	 * 
	 * @param dateDate
	 * @param k
	 * @return
	 */
	public static String dateToStr(java.util.Date dateDate)
	{
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String dateString = formatter.format(dateDate);
		return dateString;
	}

	/**
	 * 将短时间格式字符串转换为时间 yyyy-MM-dd
	 * 
	 * @param strDate
	 * @return
	 */
	public static Date strToDate(String strDate)
	{
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		ParsePosition pos = new ParsePosition(0);
		Date strtodate = formatter.parse(strDate, pos);
		return strtodate;
	}

	/**
	 * 将Timestamp时间格式时间转换为字符串 yyyy-MM-dd HH:mm:ss
	 * 
	 * @param dateDate
	 * @return
	 */
	public static String timestampToStrLong(Timestamp timestamp)
	{
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateString = formatter.format(timestamp);
		return dateString;
	}

	/**
	 * 将Timestamp时间格式时间转换为字符串 yyyy-MM-dd HH:mm:ss
	 * 
	 * @param dateDate
	 * @return
	 */
	public static Timestamp strLongToTimestamp(String strTimestamp)
	{
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		ParsePosition pos = new ParsePosition(0);
		Date date = formatter.parse(strTimestamp, pos);
		return new Timestamp(date.getTime());
	}

	/**
	 * 得到现在时间
	 * 
	 * @return
	 */
	public static Date getNow()
	{
		Date currentTime = new Date();
		return currentTime;
	}

	/**
	 * 提取一个月中的最后一天
	 * 
	 * @param day
	 * @return
	 */
	public static Date getLastDate(long day)
	{
		Date date = new Date();
		long date_3_hm = date.getTime() - 3600000 * 34 * day;
		Date date_3_hm_date = new Date(date_3_hm);
		return date_3_hm_date;
	}

	/**
	 * 得到现在时间
	 * 
	 * @return 字符串 yyyyMMdd HHmmss
	 */
	public static String getStringToday()
	{
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd HHmmss");
		String dateString = formatter.format(currentTime);
		return dateString;
	}

	/**
	 * 得到现在小时
	 */
	public static String getHour()
	{
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateString = formatter.format(currentTime);
		String hour;
		hour = dateString.substring(11, 13);
		return hour;
	}

	/**
	 * 得到现在分钟
	 * 
	 * @return
	 */
	public static String getTime()
	{
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateString = formatter.format(currentTime);
		String min;
		min = dateString.substring(14, 16);
		return min;
	}

	/**
	 * 根据用户传入的时间表示格式，返回当前时间的格式 如果是yyyyMMdd，注意字母y不能大写。
	 * 
	 * @param sformat
	 *            yyyyMMddhhmmss
	 * @return
	 */
	public static String getUserDate(String sformat)
	{
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat(sformat);
		String dateString = formatter.format(currentTime);
		return dateString;
	}

	/**
	 * 二个小时时间间的差值,必须保证二个时间都是"HH:MM"的格式，返回字符型的分钟
	 */
	public static String getTwoHour(String st1, String st2)
	{
		String[] kk = null;
		String[] jj = null;
		kk = st1.split(":");
		jj = st2.split(":");
		if (Integer.parseInt(kk[0]) < Integer.parseInt(jj[0])) return "0";
		else
		{
			double y = Double.parseDouble(kk[0]) + Double.parseDouble(kk[1]) / 60;
			double u = Double.parseDouble(jj[0]) + Double.parseDouble(jj[1]) / 60;
			if ((y - u) > 0) return y - u + "";
			else
				return "0";
		}
	}

	/**
	 * 得到二个日期间的间隔天数
	 */
	public static String getTwoDay(String sj1, String sj2)
	{
		SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy-MM-dd");
		long day = 0;
		try
		{
			java.util.Date date = myFormatter.parse(sj1);
			java.util.Date mydate = myFormatter.parse(sj2);
			day = (date.getTime() - mydate.getTime()) / (24 * 60 * 60 * 1000);
		} catch (Exception e)
		{
			return "";
		}
		return day + "";
	}
	
	/**
	 * 得到二个日期间的间隔小时
	 */
	public static String getTwoResHour(String sj1, String sj2)
	{
		SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		long day = 0;
		try
		{
			java.util.Date date = myFormatter.parse(sj1);
			java.util.Date mydate = myFormatter.parse(sj2);
			day = (date.getTime() - mydate.getTime()) / (60 * 60 * 1000);
		} catch (Exception e)
		{
			return "";
		}
		return day + "";
	}
	/**
	 * 得到二个日期间的间隔小时
	 */
	public static String getTwoResMinutes(String sj1, String sj2)
	{
		SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		long day = 0;
		try
		{
			java.util.Date date = myFormatter.parse(sj1);
			java.util.Date mydate = myFormatter.parse(sj2);
			day = (date.getTime() - mydate.getTime()) / (60 * 1000);
		} catch (Exception e)
		{
			return "";
		}
		return day + "";
	}

	/**
	 * 时间前推或后推分钟,其中JJ表示分钟.
	 */
	public static String getPreTime(String sj1, String jj)
	{
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String mydate1 = "";
		try
		{
			Date date1 = format.parse(sj1);
			long Time = (date1.getTime() / 1000) + Integer.parseInt(jj) * 60;
			date1.setTime(Time * 1000);
			mydate1 = format.format(date1);
		} catch (Exception e)
		{
		}
		return mydate1;
	}
	
	/**
	 * 时间前推或后推分钟,其中JJ表示分钟.
	 */
	public static Date getPreTimeMin(Date sj1, String jj)
	{
		Date date1 = sj1;
		try
		{
			long Time = (date1.getTime() / 1000) + Integer.parseInt(jj) * 60;
			date1.setTime(Time * 1000);
			
		} catch (Exception e)
		{
		}
		return date1;
	}

	/**
	 * 得到一个时间延后或前移几天的时间,nowdate为时间,delay为前移或后延的天数
	 */
	public static String getNextDay(String nowdate, String delay)
	{
		try
		{
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			String mdate = "";
			Date d = strToDate(nowdate);
			long myTime = (d.getTime() / 1000) + Integer.parseInt(delay) * 24 * 60 * 60;
			d.setTime(myTime * 1000);
			mdate = format.format(d);
			return mdate;
		} catch (Exception e)
		{
			return "";
		}
	}

	/**
	 * 判断是否润年
	 * 
	 * @param ddate
	 * @return
	 */
	public static boolean isLeapYear(String ddate)
	{

		/**
		 * 详细设计： 1.被400整除是闰年，否则： 2.不能被4整除则不是闰年 3.能被4整除同时不能被100整除则是闰年
		 * 3.能被4整除同时能被100整除则不是闰年
		 */
		Date d = strToDate(ddate);
		GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
		gc.setTime(d);
		int year = gc.get(Calendar.YEAR);
		if ((year % 400) == 0) return true;
		else if ((year % 4) == 0)
		{
			if ((year % 100) == 0) return false;
			else
				return true;
		} else
			return false;
	}

	/**
	 * 返回美国时间格式 26 Apr 2006
	 * 
	 * @param str
	 * @return
	 */
	public static String getEDate(String str)
	{
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		ParsePosition pos = new ParsePosition(0);
		Date strtodate = formatter.parse(str, pos);
		String j = strtodate.toString();
		String[] k = j.split(" ");
		return k[2] + k[1].toUpperCase() + k[5].substring(2, 4);
	}

	/**
	 * 获取一个月的最后一天
	 * 
	 * @param dat
	 * @return
	 */
	public static String getEndDateOfMonth(String dat)
	{// yyyy-MM-dd
		String str = dat.substring(0, 8);
		String month = dat.substring(5, 7);
		int mon = Integer.parseInt(month);
		if (mon == 1 || mon == 3 || mon == 5 || mon == 7 || mon == 8 || mon == 10 || mon == 12)
		{
			str += "31";
		} else if (mon == 4 || mon == 6 || mon == 9 || mon == 11)
		{
			str += "30";
		} else
		{
			if (isLeapYear(dat))
			{
				str += "29";
			} else
			{
				str += "28";
			}
		}
		return str;
	}

	/**
	 * 判断二个时间是否在同一个周
	 * 
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static boolean isSameWeekDates(Date date1, Date date2)
	{
		Calendar cal1 = Calendar.getInstance();
		Calendar cal2 = Calendar.getInstance();
		cal1.setTime(date1);
		cal2.setTime(date2);
		int subYear = cal1.get(Calendar.YEAR) - cal2.get(Calendar.YEAR);
		if (0 == subYear)
		{
			if (cal1.get(Calendar.WEEK_OF_YEAR) == cal2.get(Calendar.WEEK_OF_YEAR)) return true;
		} else if (1 == subYear && 11 == cal2.get(Calendar.MONTH))
		{
			// 如果12月的最后一周横跨来年第一周的话则最后一周即算做来年的第一周
			if (cal1.get(Calendar.WEEK_OF_YEAR) == cal2.get(Calendar.WEEK_OF_YEAR)) return true;
		} else if (-1 == subYear && 11 == cal1.get(Calendar.MONTH))
		{
			if (cal1.get(Calendar.WEEK_OF_YEAR) == cal2.get(Calendar.WEEK_OF_YEAR)) return true;
		}
		return false;
	}

	/**
	 * 产生周序列,即得到当前时间所在的年度是第几周
	 * 
	 * @return
	 */
	public static String getSeqWeek()
	{
		Calendar c = Calendar.getInstance(Locale.CHINA);
		String week = Integer.toString(c.get(Calendar.WEEK_OF_YEAR));
		if (week.length() == 1) week = "0" + week;
		String year = Integer.toString(c.get(Calendar.YEAR));
		return year + week;
	}

	/**
	 * 获得一个日期所在的周的星期几的日期，如要找出2002年2月3日所在周的星期一是几号
	 * 
	 * @param sdate
	 * @param num
	 * @return
	 */
	public static String getWeek(String sdate, String num)
	{
		// 再转换为时间
		Date dd = VeDate.strToDate(sdate);
		Calendar c = Calendar.getInstance();
		c.setTime(dd);
		if (num.equals("1")) // 返回星期一所在的日期
		c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		else if (num.equals("2")) // 返回星期二所在的日期
		c.set(Calendar.DAY_OF_WEEK, Calendar.TUESDAY);
		else if (num.equals("3")) // 返回星期三所在的日期
		c.set(Calendar.DAY_OF_WEEK, Calendar.WEDNESDAY);
		else if (num.equals("4")) // 返回星期四所在的日期
		c.set(Calendar.DAY_OF_WEEK, Calendar.THURSDAY);
		else if (num.equals("5")) // 返回星期五所在的日期
		c.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);
		else if (num.equals("6")) // 返回星期六所在的日期
		c.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
		else if (num.equals("0")) // 返回星期日所在的日期
		c.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
		return new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
	}

	/**
	 * 根据一个日期，返回是星期几的字符串
	 * 
	 * @param sdate
	 * @return
	 */
	public static String getWeek(String sdate)
	{
		// 再转换为时间
		Date date = VeDate.strToDate(sdate);
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		// int hour=c.get(Calendar.DAY_OF_WEEK);
		// hour中存的就是星期几了，其范围 1~7
		// 1=星期日 7=星期六，其他类推
		return new SimpleDateFormat("EEEE").format(c.getTime());
	}

	public static String getWeekStr(String sdate)
	{
		String str = "";
		str = VeDate.getWeek(sdate);
		if ("1".equals(str))
		{
			str = "星期日";
		} else if ("2".equals(str))
		{
			str = "星期一";
		} else if ("3".equals(str))
		{
			str = "星期二";
		} else if ("4".equals(str))
		{
			str = "星期三";
		} else if ("5".equals(str))
		{
			str = "星期四";
		} else if ("6".equals(str))
		{
			str = "星期五";
		} else if ("7".equals(str))
		{
			str = "星期六";
		}
		return str;
	}

	/**
	 * 两个时间之间的天数
	 * 
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static long getDays(String date1, String date2)
	{
		if (date1 == null || date1.equals("")) return 0;
		if (date2 == null || date2.equals("")) return 0;
		// 转换为标准时间
		SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy-MM-dd");
		java.util.Date date = null;
		java.util.Date mydate = null;
		try
		{
			date = myFormatter.parse(date1);
			mydate = myFormatter.parse(date2);
		} catch (Exception e)
		{
		}
		long day = (date.getTime() - mydate.getTime()) / (24 * 60 * 60 * 1000);
		return day;
	}

	/**
	 * 形成如下的日历 ， 根据传入的一个时间返回一个结构 星期日 星期一 星期二 星期三 星期四 星期五 星期六 下面是当月的各个时间
	 * 此函数返回该日历第一行星期日所在的日期
	 * 
	 * @param sdate
	 * @return
	 */
	public static String getNowMonth(String sdate)
	{
		// 取该时间所在月的一号
		sdate = sdate.substring(0, 8) + "01";

		// 得到这个月的1号是星期几
		Date date = VeDate.strToDate(sdate);
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		int u = c.get(Calendar.DAY_OF_WEEK);
		String newday = VeDate.getNextDay(sdate, (1 - u) + "");
		return newday;
	}

	/**
	 * 取得数据库主键 生成格式为yyyymmddhhmmss+k位随机数
	 * 
	 * @param k
	 *            表示是取几位随机数，可以自己定
	 */

	public static String getNo(int k)
	{

		return getUserDate("yyyyMMddhhmmss") + getRandom(k);
	}

	/**
	 * 返回一个随机数
	 * 
	 * @param i
	 * @return
	 */
	public static String getRandom(int i)
	{
		Random jjj = new Random();
		// int suiJiShu = jjj.nextInt(9);
		if (i == 0) return "";
		String jj = "";
		for (int k = 0; k < i; k++)
		{
			jj = jj + jjj.nextInt(9);
		}
		return jj;
	}

	/**
	 * 
	 * @param args
	 */
	public static boolean RightDate(String date)
	{

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		;
		if (date == null) return false;
		if (date.length() > 10)
		{
			sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		} else
		{
			sdf = new SimpleDateFormat("yyyy-MM-dd");
		}
		try
		{
			sdf.parse(date);
		} catch (ParseException pe)
		{
			return false;
		}
		return true;
	}
	/**
	 * 
	 *  Timestamp转化为String:
	 * @param time Timestamp
	 * @return str yyyy-MM-dd HH:mm:ss
	 */
	public static String timestampToStr(Timestamp time){
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String str = df.format(time);    
		return str;
	}
	
	
	/**
	 * 
	 *  Timestamp转化为Date:
	 * @param time Timestamp
	 * @return str yyyy-MM-dd HH:mm:ss
	 */
	public static Date timestampTodate(Timestamp time){
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String str = df.format(time);    
		Date now=strToDateLong(str);
		return now;
	}
	/**
	 * 
	 *  Timestamp转化为String:
	 * @param time Timestamp
	 * @return str yyyy-MM-dd HH:mm:ss
	 */
	public static String timestampToShortStrM(Timestamp time){
		SimpleDateFormat df = new SimpleDateFormat("MM-dd HH:mm");
		String str = df.format(time);    
		return str;
	}
	
	/**
	 * 
	 *  Timestamp转化为String:
	 * @param time Timestamp
	 * @return str yyyy-MM-dd HH:mm:ss
	 */
	public static String timestampToShortStr(Timestamp time){
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String str = df.format(time);    
		return str;
	}
	public static Timestamp  timestampAddNum(Timestamp time,Integer num){
		Timestamp temp=null;
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String str = df.format(time); 
		Date now=strToDateLong(str);
		now.setMonth(now.getMonth()+num);
		now.setDate(now.getDate()-1);
		String str1=dateToStrLong(now);
		temp=strLongToTimestamp(str1);
		
		return temp;
	}
	/**
	 * 
	 * Timestamp 基础之上延长 小时
	 * @param time
	 * @param num
	 * @return
	 */
	public static Timestamp  timestampAddHourNum(Timestamp time,Integer num){
		Timestamp temp=null;
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String str = df.format(time); 
		Date now=strToDateLong(str);
		now.setHours(now.getHours()+num);
		String str1=dateToStrLong(now);
		temp=strLongToTimestamp(str1);
		
		return temp;
	}
	
	public static Date  timeDateAddHourNum(String time,Integer num){
		
		Date now=strToDateLong(time);
		now.setHours(now.getHours()+num);
		String str1=dateToStrLong(now);
		
		
		return now;
	}
	public static Date  timeDateAdMinNum(String time,Integer num){
		
		Date now=strToDateLong(time);
		now.setMinutes(now.getMinutes()+num);
		String str1=dateToStrLong(now);
		
		
		return now;
	}
	/**
	 * 判断用车时间是否在date1和date2之间
	 * TODO
	 * @param bytime
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static boolean isDateIn(String bytime,String s1,String e1){
		boolean flag=false;
		Date byTime=VeDate.strToDateLong(bytime);
		byTime.setSeconds(0);
		Date sDate1=VeDate.shortStrToDate(byTime,s1);
		Date eDate1=VeDate.shortStrToDate(byTime,e1);
		if(byTime.after(sDate1)&&byTime.before(eDate1)){
			flag=true;
		}else if(byTime.equals(sDate1)||byTime.equals(eDate1)){
			flag=true;
		}else{
			flag=false;
		}
		
		
		return flag;
		
	}
	
	public static void main(String args[]){
//	 Date now=new Date();
//	 System.out.println(now.toLocaleString());
//	 
//	 System.out.println(now);
//	 
//	 
//	 now.setMonth(now.getMonth()+18);
//	 System.out.println(now.toLocaleString());
//	 System.out.println(VeDate.strToDatestr("2012-06-05 16:52:54"));
//	 
//	 System.out.println(VeDate.getPreTimeMin(new Date(), "-30").toLocaleString());
//	 
//	 System.out.println(VeDate.strToDateshort("20120724175839"));
//	 
//	 
//	 System.out.println(VeDate.strLongToTimestamp("2012-09-01 12:00:00"));
//	 
//	 System.out.println(VeDate.timestampAddNum(VeDate.strLongToTimestamp("2012-02-28 12:00:00"),12));
//	 
//	 System.out.println(VeDate.shortStrToDate("07:00:00").toLocaleString());
////	 System.out.println(VeDate.shortStrToDateYesterDay("07:00:00").toLocaleString());
//	 
//	 System.out.println(VeDate.shortStrToDate("23:59:59").toLocaleString());
//	 
//	 System.out.println(VeDate.strToDateShort("2012-07-20 16:52:54"));
//	 System.out.println(VeDate.getNextDay(VeDate.getStringDateShort(), "-30"));
//	 System.out.println(VeDate.getStringDateShort());
//	 System.out.println(new Timestamp(System.currentTimeMillis()));
////	 System.out.println(VeDate.shortStrToDate("23:59:59").getHours());
//	 
	 Date date1=new Date("Wed, 8 May 2013 13:41:37 +0800");
	 System.out.println(date1.toLocaleString());
	}
}
