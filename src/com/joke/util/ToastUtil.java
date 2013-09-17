/**
 * 
 */
package com.joke.util;

import android.content.Context;
import android.widget.Toast;

/*******
 * @project AMapV2Demos
 * @email chuan.yu@autonavi.com
 * @time 2013-3-20下午4:35:09
 *******/
public class ToastUtil {

	public static void show(Context context, String info) {
		Toast.makeText(context, info, Toast.LENGTH_LONG).show();
	}

	public static void show(Context context, int info) {
		Toast.makeText(context, info, Toast.LENGTH_LONG).show();
	}
	
	public static void showshort(Context context, String info) {
		Toast.makeText(context, info, Toast.LENGTH_SHORT).show();
	}

	public static void showshort(Context context, int info) {
		Toast.makeText(context, info, Toast.LENGTH_SHORT).show();
	}
}
