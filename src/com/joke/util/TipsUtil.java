package com.joke.util;


import com.joke.R;
import com.joke.jokeinterface.ThuCallBack;
import com.joke.widget.CustomDialog;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnKeyListener;

import android.view.View;

import android.widget.Toast;


public class TipsUtil {

	private static AlertDialog alertdialog;
	private static ProgressDialog progressdialog;
	private static CustomDialog dialog;
	
	public static void alertCustomDialog(Context context,View layout){
		 CustomDialog.Builder builder=new CustomDialog.Builder(context);
		 
		 dialog = new CustomDialog(context,R.style.Dialog);
		 
		 builder.create(dialog, layout).show();
		 
	}
	
	public static void closeCustomDialog() {
		if (dialog != null) {
			dialog.dismiss();
			dialog.setOnKeyListener(null);
		}
	}
	
	/**
	 * 弹出只有确定按钮的dialog 没有Logo
	 * 
	 * @param context
	 *            上下文
	 * @param title
	 *            标题文字
	 * @param content
	 *            提示信息
	 * @param okLabel
	 *            提示按钮文字
	 */
	public static void alertDialogTipsNoDraw(Context context, String title,
			String content, String okLabel, final ThuCallBack okCallback) {
		alertdialog = new AlertDialog.Builder(context)
				.setTitle(title)
				.setIcon(android.R.drawable.ic_menu_info_details)
				.setMessage(content)
				.setPositiveButton(okLabel,
						new DialogInterface.OnClickListener() {
							public void onClick(
									DialogInterface dialoginterface, int i) {
								if (null != okCallback) {
									okCallback.excute(null);
								}
							}
						}).show();
	}

	/**
	 * 弹出只有确定按钮的dialog
	 * 
	 * @param context
	 *            上下文
	 * @param title
	 *            标题文字
	 * @param content
	 *            提示信息
	 * @param okLabel
	 *            提示按钮文字
	 */
	public static void alertDialogTips(Context context, String title,
			String content, String okLabel, final ThuCallBack okCallback) {
		alertdialog = new AlertDialog.Builder(context)
				.setTitle(title)
				.setIcon(R.drawable.ic_launcher)
				.setMessage(content)
				.setPositiveButton(okLabel,
						new DialogInterface.OnClickListener() {
							public void onClick(
									DialogInterface dialoginterface, int i) {
								if (null != okCallback) {
									okCallback.excute(null);
								}
							}
						}).show();
	}

	/**
	 * 弹出确定和取消两个按钮的dialog
	 * 
	 * @param context
	 *            上下文
	 * @param title
	 *            标题文字
	 * @param content
	 *            提示信息
	 * @param okLabel
	 *            提示按钮文字
	 */
	public static void alertDialogTips(Context context, String title,
			String content, String okLabel, String cancelLabel,
			final ThuCallBack okCallback, final ThuCallBack cancelCallback) {
		alertdialog = new AlertDialog.Builder(context)
				.setTitle(title)
				.setIcon(android.R.drawable.ic_menu_info_details)
				.setMessage(content)
				.setPositiveButton(okLabel,
						new DialogInterface.OnClickListener() {
							public void onClick(
									DialogInterface dialoginterface, int i) {
								if (null != okCallback) {
									okCallback.excute(null);
								}
							}
						})
				.setNegativeButton(cancelLabel,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {
								if(null != cancelCallback){
									cancelCallback.excute(null);
								}
							}
						}).show();
	}
	
	/**
	 * 弹出确定和取消两个按钮的dialog，并加上监听键盘返回事件
	 * 
	 * @param context
	 *            上下文
	 * @param title
	 *            标题文字
	 * @param content
	 *            提示信息
	 * @param okLabel
	 *            提示按钮文字
	 */
	public static void alertDialogTips(Context context, String title,
			String content, String okLabel, String cancelLabel,
			final ThuCallBack okCallback, final ThuCallBack cancelCallback,final OnKeyListener onkeylistener) {
		alertdialog = new AlertDialog.Builder(context)
				.setTitle(title)
				.setIcon(android.R.drawable.ic_menu_info_details)
				.setMessage(content)
				.setPositiveButton(okLabel,
						new DialogInterface.OnClickListener() {
							public void onClick(
									DialogInterface dialoginterface, int i) {
								if (null != okCallback) {
									okCallback.excute(null);
								}
							}
						})
				.setNegativeButton(cancelLabel,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {
								if(null != cancelCallback){
									cancelCallback.excute(null);
								}
							}
						}).show();
		alertdialog.setOnKeyListener(onkeylistener);
	}
	
	/**
	 * 弹出自定义View窗口的dialog
	 * 
	 * @param context
	 *            上下文
	 * @param title
	 *            标题文字
	 * @param content
	 *            提示信息
	 * @param okLabel
	 *            提示按钮文字
	 * @param okCallback
	 *            回调函数
	 * @param onkeylistener
	 *            监听键盘事件       
	 */
	public static void alertViewDialog(Context context, String title,
			View view, String okLabel, final ThuCallBack okCallback,final OnKeyListener onkeylistener) {
		    alertdialog=new AlertDialog.Builder(context)
										.setTitle(title)
										.setIcon(android.R.drawable.ic_menu_info_details)
										.setInverseBackgroundForced(true)
										.setView(view)
										.setPositiveButton(okLabel,
														new DialogInterface.OnClickListener() {
															public void onClick(
																	DialogInterface dialoginterface, int i) {
																if (null != okCallback) {
																	okCallback.excute(null);
																}
															}
														}).show();
			alertdialog.setOnKeyListener(onkeylistener);
	}
	
	/**
	 * 弹出自定义View窗口的dialog
	 * 
	 * @param context
	 *            上下文
	 * @param title
	 *            标题文字
	 * @param content
	 *            提示信息
	 * @param okLabel
	 *            提示按钮文字
	 * @param okCallback
	 *            回调函数
	 * @param onkeylistener
	 *            监听键盘事件       
	 */
	public static void alertViewDialogNoIcon(Context context, String title,
			View view, String okLabel, final ThuCallBack okCallback,final OnKeyListener onkeylistener) {
		    alertdialog=new AlertDialog.Builder(context)
										.setTitle(title)
										.setInverseBackgroundForced(true)
										.setView(view)
										.setPositiveButton(okLabel,
														new DialogInterface.OnClickListener() {
															public void onClick(
																	DialogInterface dialoginterface, int i) {
																if (null != okCallback) {
																	okCallback.excute(null);
																}
															}
														}).show();
			alertdialog.setOnKeyListener(onkeylistener);
	}
	
	/**
	 * 弹出自定义View窗口的dialog
	 * 
	 * @param context
	 *            上下文
	 * @param title
	 *            标题文字
	 * @param content
	 *            提示信息
	 * @param okLabel
	 *            提示按钮文字
	 * @param okCallback
	 *            回调函数
	 * @param onkeylistener
	 *            监听键盘事件       
	 */
	public static void alertViewDialogNoIconNoLis(Context context, String title,
			View view) {
		    alertdialog=new AlertDialog.Builder(context)
										.setTitle(title)
										.setInverseBackgroundForced(true)
										.setView(view)
										.show();
			
	}

	/**
	 * 弹出自定义View窗口的dialog
	 * 
	 * @param context
	 *            上下文
	 * @param title
	 *            标题文字
	 * @param content
	 *            提示信息
	 * @param okLabel
	 *            提示按钮文字
	 * @param okCallback
	 *            回调函数
	 * @param onkeylistener
	 *            监听键盘事件       
	 */
	public static void alertViewDialogNoIconNoLisNoTitle(Context context, String title,
			View view) {
		    alertdialog=new AlertDialog.Builder(context)
										.setInverseBackgroundForced(false)
										.setView(view)
										.show();
			
	}

	/**
	 * 关闭AlertDialog
	 */
	public static void closeAlertDialog() {
		if (alertdialog != null) {
			alertdialog.dismiss();
			alertdialog.setOnKeyListener(null);
		}
	}

	/**
	 * 弹出ProgressDialog
	 * 
	 * @param context
	 *            上下文
	 * @param msg
	 *            提示信息
	 * @param title
	 *            可以为空
	 */
	public static void showProgress(Context context, String msg, String title) {
		progressdialog = new ProgressDialog(context);
		progressdialog.setTitle(title);
		progressdialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		progressdialog.setMessage(msg);
		progressdialog.setIndeterminate(true);
//		progressdialog.setIndeterminateDrawable(context.getResources().getDrawable(R.color.zise));
		progressdialog.setCancelable(true);
		
		progressdialog.show();
	}
    private static Toast toast=null;
	/**
	 * 关闭ProgressDialog
	 */
	public static void closeProgressDialog() {
		if (progressdialog != null) {
			progressdialog.dismiss();
		}
	}

	public static void alertToastTips(Context context, String tips) {
//		Toast.makeText(context, tips, Toast.LENGTH_LONG).show();
		toast = Toast.makeText(context, tips,  Toast.LENGTH_LONG); 
		toast.show();
	}
	
	public static void alertShortToastTips(Context context,String tips){
//		Toast.makeText(context, tips, Toast.LENGTH_SHORT).show();
		toast = Toast.makeText(context, tips,  Toast.LENGTH_SHORT); 
		toast.show();
	}
	
	public static void alertShortToastTips(Context context,int id){
//		Toast.makeText(context, tips, Toast.LENGTH_SHORT).show();
		String tips=context.getResources().getString(id);
		toast = Toast.makeText(context, tips,  Toast.LENGTH_SHORT); 
		toast.show();
	}
	
	public static void aletShortToastTipsOffset(Context context,String tips,int gravity,int offX,int offY){
		toast = Toast.makeText(context, tips,  Toast.LENGTH_SHORT); 
		toast.setGravity(gravity, offX, offY);//Gravity.BOTTOM | Gravity.CENTER
		toast.show();
		
	}
	public static void closeToastTip(){
		if(toast!=null){
		  toast.cancel();
		}
	}
	

	
}
