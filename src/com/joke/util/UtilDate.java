package com.joke.util;

import java.util.Date;
import java.util.Random;
import java.text.SimpleDateFormat;
import java.text.DateFormat;

public class UtilDate {
	public static final String dtLong = "yyyyMMddHHmmss";//14位
	
	public static final String dtOrderLong = "yyMMddHHmmss";//12
	
	public static final String dtTaxiLong = "yyMMddHHmmss";//12
	
	public static final String taxiLong = "yyMMdd";
	
	public static final String simple = "yyyy-MM-dd HH:mm:ss";

	public static final String dtShort = "yyyyMMdd";
	
	public static final String dtDir = "yyyyMM";

	public static String getOrderNum(int length) {
		Date date = new Date();
		DateFormat df = new SimpleDateFormat(dtOrderLong);
		int num = 1;
		double random = Math.random();
		if (random < 0.1) {
			random = random + 0.1;
		}
		for (int i = 0; i < length; i++) {
			num = num * 10;
		}
		return df.format(date) + (int) ((random * num));
	}
	
	public static String getFirstOrderNum(int length) {
		Date date = new Date();
		DateFormat df = new SimpleDateFormat(dtLong);
		int num = 1;
		double random = Math.random();
		if (random < 0.1) {
			random = random + 0.1;
		}
		for (int i = 0; i < length; i++) {
			num = num * 10;
		}
		return df.format(date) + (int) ((random * num));
	}

	public static String getTaxiOrderNum(int length) {
		Date date = new Date();
		DateFormat df = new SimpleDateFormat(taxiLong);
		int num = 1;
		double random = Math.random();
		if (random < 0.1) {
			random = random + 0.1;
		}
		for (int i = 0; i < length; i++) {
			num = num * 10;
		}
		return df.format(date) + (int) ((random * num));
	}
	
	
	public static int buildRandom(int length) {
		int num = 1;
		double random = Math.random();
		if (random < 0.1) {
			random = random + 0.1;
		}
		for (int i = 0; i < length; i++) {
			num = num * 10;
		}
		return (int) ((random * num));
	}
	/**
	 * 
	 *<b>function:</b>获取文件夹
	 *@author cuiran
	 *@createDate 2013-03-28 15:55:17
	 *@return
	 */
	public static String getDatedtDir() {
		Date date = new Date();
		DateFormat df = new SimpleDateFormat(dtDir);
		return df.format(date);
	}

	public static String getDateFormatter() {
		Date date = new Date();
		DateFormat df = new SimpleDateFormat(simple);
		return df.format(date);
	}

	public static String getDate() {
		Date date = new Date();
		DateFormat df = new SimpleDateFormat(dtShort);
		return df.format(date);
	}

	public static String getThree() {
		Random rad = new Random();
		return rad.nextInt(1000) + "";
	}
	public static void main(String args[]){
		String orderNum = UtilDate.getTaxiOrderNum(6);
		System.out.println(orderNum);
		System.out.println(UtilDate.getDate());
	}
}
