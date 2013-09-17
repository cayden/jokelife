/**
 * StroyContentActivity.java
 * 版权所有(C) 2013 
 * 创建:cuiran 2013-7-15 下午12:28:39
 */
package com.joke.activity;


import com.joke.R;
import com.joke.net.dto.JokeInfo;
import com.joke.net.http.JokeData;
import com.joke.util.Constants;
import com.joke.util.LogsUtil;
import com.joke.util.TipsUtil;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.webkit.WebView;
import android.widget.TextView;

/**
 * TODO加载故事正文
 * @author cuiran
 * @version TODO
 */
public class StroyContentActivity extends Activity {
	private TextView subject_tv,subject;
	private WebView content_wv;
	
	private JokeInfo info;
	private static final String TAG="StroyContentActivity";
	 @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.layout_story_content);
	        initView();
	        
	        initData();
	 }
	/**
	 *<b>function:</b>初始化数据
	 *@author cuiran
	 *@createDate 2013-7-15 下午12:31:05
	 */
	private void initData() {
		// TODO Auto-generated method stub
		Bundle bundle=this.getIntent().getExtras();
		info=(JokeInfo)bundle.getSerializable("info");
		subject_tv.setText(info.getSubject());
		subject.setText(info.getSubject());
		TipsUtil.showProgress(this, "正在加载...", "");
		JokeData.getStoryContentData(info.getLink(), this, contentHandler);
	}
	
	
	/**
	 *<b>function:</b>初始化界面
	 *@author cuiran
	 *@createDate 2013-7-15 下午12:30:01
	 */
	private void initView() {
		// TODO Auto-generated method stub
		subject_tv=(TextView)findViewById(R.id.story_subject);
		subject=(TextView)findViewById(R.id.subject);
		content_wv=(WebView)findViewById(R.id.webView);
		content_wv.getSettings().setJavaScriptEnabled(true); 
		
	}
	
	private Handler contentHandler =new Handler(){
		 
		 
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			
			switch (msg.what) {
			case Constants.WORKERROR:
				TipsUtil.closeProgressDialog();
				TipsUtil.alertShortToastTips(StroyContentActivity.this, 
						R.string.tip_msg_neterror);
				break;
			
			case Constants.SUCCESS:
				TipsUtil.closeProgressDialog();
				try{
					Bundle bundle=msg.getData();
					JokeInfo jInfo=(JokeInfo)bundle.getSerializable("dto");
					content_wv.loadDataWithBaseURL(null, jInfo.getContent(), "text/html", "utf-8", null);
				}catch (Exception e) {
					// TODO: handle exception
					LogsUtil.e(TAG, "返回数据异常", e);
				}
				
				break;
			case Constants.ERROR:
				TipsUtil.closeProgressDialog();
				
				
				break;
			}
		}
		
		 
	 };
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
