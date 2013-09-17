/**
 * MineActivity.java
 * 版权所有(C) 2013 
 * 创建:cuiran 2013-7-15 下午2:36:51
 */
package com.joke.activity;

import com.joke.R;
import com.joke.util.Constants;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * TODO
 * @author cuiran
 * @version TODO
 */
public class MineActivity extends Activity {
	
	 @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.layout_city);
	        initView();
	       
	 }

	/**
	 *<b>function:</b>
	 *@author cuiran
	 *@createDate 2013-7-15 下午2:37:02
	 */
	private void initView() {
		// TODO Auto-generated method stub
		WebView webView=(WebView)findViewById(R.id.webView);
		webView.getSettings().setJavaScriptEnabled(true); 
		webView.getSettings().setJavaScriptEnabled(true); // 页面缩放设置 
		webView.getSettings().setBuiltInZoomControls(true); // 使页面获得焦点 
		webView.getSettings().setDefaultTextEncodingName("UTF-8");
		webView.requestFocus(); 
		webView.loadUrl(Constants.TAOBAO_URL);
		
		webView.setWebViewClient(new WebViewClient(){

			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				// TODO Auto-generated method stub
				view.loadUrl(url);
				return super.shouldOverrideUrlLoading(view, url);
			}
			
		});
		
	}
	
	/** 
	  *<b>function:</b>返回
	  *@author cuiran
	  *@createDate 2013-8-12 上午11:07:30
	  *@param v
	  */
	 public void back(View v){
		 this.finish();
	 }
}
