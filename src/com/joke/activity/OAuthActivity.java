/**
 * OAuthActivity.java
 * 版权所有(C) 2013 
 * 创建:cuiran 2013-7-17 上午9:50:22
 */
package com.joke.activity;

import com.baidu.sharesdk.BaiduShareException;
import com.baidu.sharesdk.BaiduSocialShare;
import com.baidu.sharesdk.ShareListener;
import com.baidu.sharesdk.SocialShareLogger;
import com.baidu.sharesdk.Utility;
import com.joke.R;
import com.joke.config.BaiduSocialShareConfig;


import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.TextView;

/**
 * TODO 第三方授权操作
 * @author cuiran
 * @version TODO
 */
public class OAuthActivity extends Activity {

	final Handler handler = new Handler(Looper.getMainLooper());
	 
	private BaiduSocialShare socialShare;
	
	private final static String appKey = BaiduSocialShareConfig.mbApiKey;
	private final static String wxAppId = BaiduSocialShareConfig.appID;
	private TextView sinaOauthView, qqOauthView, qZoneOauthView,
	kaixinOauthView,renrenOauthView;
	 @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.layout_more_oauth);
	        
	        initSocialShare();
	        
	        initView();
	       
	        updateAuthButtonState();
	 }

	 /**
	  * 
	  *<b>function:</b>更改界面显示状态
	  *@author cuiran
	  *@createDate 2013-7-17 上午10:02:26
	  */
	 private void updateAuthButtonState() {
	        sinaOauthView = (TextView) findViewById(R.id.sinaAuth);
	        if (socialShare.isAccessTokenValid(Utility.SHARE_TYPE_SINA_WEIBO)) {
	            sinaOauthView.setTextColor(Color.BLUE);
	            sinaOauthView.setText("已授权");
	        }

	        qqOauthView = (TextView) findViewById(R.id.qqAuth);
	        if (socialShare.isAccessTokenValid(Utility.SHARE_TYPE_QQ_WEIBO)) {
	            qqOauthView.setTextColor(Color.BLUE);
	            qqOauthView.setText("已授权");
	        }

	        qZoneOauthView = (TextView) findViewById(R.id.qzoneAuth);
	        if (socialShare.isAccessTokenValid(Utility.SHARE_TYPE_QZONE)) {
	            qZoneOauthView.setTextColor(Color.BLUE);
	            qZoneOauthView.setText("已授权");
	        }

	        kaixinOauthView = (TextView) findViewById(R.id.kaixinAuth);
	        if (socialShare.isAccessTokenValid(Utility.SHARE_TYPE_KAIXIN)) {
	            kaixinOauthView.setTextColor(Color.BLUE);
	            kaixinOauthView.setText("已授权");
	        }
	        
	        renrenOauthView = (TextView) findViewById(R.id.renrenAuth);
	        if (socialShare.isAccessTokenValid(Utility.SHARE_TYPE_RENREN)) {
	        	renrenOauthView.setTextColor(Color.BLUE);
	        	renrenOauthView.setText("已授权");
	        }
	        
	        
	        
	        
		}
	/**
	 *<b>function:</b>
	 *@author cuiran
	 *@createDate 2013-7-17 上午10:00:47
	 */
	private void initSocialShare() {
		// TODO Auto-generated method stub
		//实例化baidu社会化分享，传入appkey
		socialShare = BaiduSocialShare.getInstance(this, appKey);
		//设置支持微信平台 传入wxAppId
		socialShare.supportWeixin(wxAppId);

		//设置支持腾讯微博单点登录的appid
		socialShare.supportQQSso(BaiduSocialShareConfig.QQ_SSO_APP_KEY);
		
		//设置支持新浪微博单点登录的appid
        socialShare.supportWeiBoSso(BaiduSocialShareConfig.SINA_SSO_APP_KEY);

		
		
		SocialShareLogger.on();
	}

	/**
	 *<b>function:</b>初始化界面
	 *@author cuiran
	 *@createDate 2013-7-17 上午9:50:41
	 */
	private void initView() {
		// TODO Auto-generated method stub
		
	}
	
	//针对sinaWeibo进行认证授权  如已经授权，进行点击操作后，会调用本地清除保存的token方法
		public void auth_sinaweibo(View v) {
			if (!socialShare.isAccessTokenValid(Utility.SHARE_TYPE_SINA_WEIBO)) {
				socialShare.authorize(this, Utility.SHARE_TYPE_SINA_WEIBO,
						new ShareListener() {
							@Override
							public void onAuthComplete(Bundle values) {
								handler.post(new Runnable() {
									@Override
									public void run() {
										sinaOauthView = (TextView) 
												findViewById(R.id.sinaAuth);
										sinaOauthView.setTextColor(Color.BLUE);
										sinaOauthView.setText("已授权");
									}
								});
							}

							@Override
							public void onApiComplete(String responses) {

							}

							@Override
							public void onError(BaiduShareException e) {
								final String errorMsg = e.toString();
								handler.post(new Runnable() {
									@Override
									public void run() {
										Utility.showAlert(OAuthActivity.this,
												"错误提示", errorMsg);
									}
								});
							}

						});
			} else {
				socialShare.cleanAccessToken(Utility.SHARE_TYPE_SINA_WEIBO);
				sinaOauthView.setTextColor(Color.BLACK);
				sinaOauthView.setText("未授权");
			}

		}
		public void auth_qqweibo(View v) {
			if (!socialShare.isAccessTokenValid(Utility.SHARE_TYPE_QQ_WEIBO)) {
				socialShare.authorize(this, Utility.SHARE_TYPE_QQ_WEIBO,
						new ShareListener() {
							@Override
							public void onAuthComplete(Bundle values) {
								handler.post(new Runnable() {
									@Override
									public void run() {
										qqOauthView = (TextView) findViewById(R.id.qqAuth);
										qqOauthView.setTextColor(Color.BLUE);
										qqOauthView.setText("已授权");
									}
								});
							}

							@Override
							public void onApiComplete(String responses) {

							}

							@Override
							public void onError(BaiduShareException e) {
								final String errorMsg = e.toString();
								handler.post(new Runnable() {
									@Override
									public void run() {
										Utility.showAlert(OAuthActivity.this,
												"错误提示", errorMsg);
									}
								});
							}

						});
			} else {
				socialShare.cleanAccessToken(Utility.SHARE_TYPE_QQ_WEIBO);
				qqOauthView.setTextColor(Color.BLACK);
				qqOauthView.setText("未授权");

			}
		}
		//针对QQ空间进行认证授权  如已经授权，进行点击操作后，会调用本地清除保存的token方法
		public void auth_qzone(View v) {

			if (!socialShare.isAccessTokenValid(Utility.SHARE_TYPE_QZONE)) {

				socialShare.authorize(this, Utility.SHARE_TYPE_QZONE,
						new ShareListener() {
							@Override
							public void onAuthComplete(Bundle values) {
								handler.post(new Runnable() {
									@Override
									public void run() {
										qZoneOauthView = (TextView) findViewById(R.id.qzoneAuth);
										qZoneOauthView.setTextColor(Color.BLUE);
										qZoneOauthView.setText("已授权");
									}
								});
							}

							@Override
							public void onApiComplete(String responses) {

							}

							@Override
							public void onError(BaiduShareException e) {
								final String errorMsg = e.toString();
								handler.post(new Runnable() {
									@Override
									public void run() {
										Utility.showAlert(OAuthActivity.this,
												"错误提示", errorMsg);
									}
								});
							}

						});
			} else {
				socialShare.cleanAccessToken(Utility.SHARE_TYPE_QZONE);
				qZoneOauthView.setTextColor(Color.BLACK);
				qZoneOauthView.setText("未授权");
			}

		}
		
		
		//针对人人进行认证授权  如已经授权，进行点击操作后，会调用本地清除保存的token方法
		public void auth_renren(View v) {

			if (!socialShare.isAccessTokenValid(Utility.SHARE_TYPE_RENREN)) {

				socialShare.authorize(this, Utility.SHARE_TYPE_RENREN,
						new ShareListener() {
							@Override
							public void onAuthComplete(Bundle values) {
								// TODO Auto-generated method stub
								handler.post(new Runnable() {
									@Override
									public void run() {
										renrenOauthView = (TextView)findViewById(R.id.qzoneAuth);
										renrenOauthView.setTextColor(Color.BLUE);
										renrenOauthView.setText("已授权");
									}
								});
							}

							@Override
							public void onApiComplete(String responses) {

							}

							@Override
							public void onError(BaiduShareException e) {
								// TODO Auto-generated method stub
								final String errorMsg = e.toString();
								// TODO Auto-generated method stub
								handler.post(new Runnable() {
									@Override
									public void run() {
										Utility.showAlert(OAuthActivity.this,
												"错误提示", errorMsg);
									}
								});
							}

						});
			} else {
				socialShare.cleanAccessToken(Utility.SHARE_TYPE_RENREN);
				renrenOauthView.setTextColor(Color.BLACK);
				renrenOauthView.setText("未授权");
			}

		}
		
		
		
		
		
		
		
		
		
		//针对开心网进行认证授权  如已经授权，进行点击操作后，会调用本地清除保存的token方法
		public void auth_kaixin(View v) {
			if (!socialShare.isAccessTokenValid(Utility.SHARE_TYPE_KAIXIN)) {
				socialShare.authorize(this, Utility.SHARE_TYPE_KAIXIN,
						new ShareListener() {
							@Override
							public void onAuthComplete(Bundle values) {
								handler.post(new Runnable() {
									@Override
									public void run() {
										kaixinOauthView = (TextView) findViewById(R.id.kaixinAuth);
										kaixinOauthView.setTextColor(Color.BLUE);
										kaixinOauthView.setText("已授权");
									}
								});
							}

							@Override
							public void onApiComplete(String responses) {

							}

							@Override
							public void onError(BaiduShareException e) {
								final String errorMsg = e.toString();
								handler.post(new Runnable() {
									@Override
									public void run() {
										Utility.showAlert(OAuthActivity.this,
												"错误提示", errorMsg);
									}
								});
							}

						});
			} else {
				socialShare.cleanAccessToken(Utility.SHARE_TYPE_KAIXIN);
				kaixinOauthView.setTextColor(Color.BLACK);
				kaixinOauthView.setText("未授权");

			}
		}
		
		/**
		  * 
		  *<b>function:</b>返回
		  *@author cuiran
		  *@createDate 2013-8-12 上午11:07:30
		  *@param v
		  */
		 public void back(View v){
			 this.finish();
		 }
}
