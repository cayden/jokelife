package com.joke.activity;





import java.util.ArrayList;


import java.util.List;

import com.baidu.mobstat.StatService;
import com.baidu.sharesdk.BaiduShareException;
import com.baidu.sharesdk.BaiduSocialShare;
import com.baidu.sharesdk.ShareContent;
import com.baidu.sharesdk.ShareListener;
import com.baidu.sharesdk.SocialShareLogger;
import com.baidu.sharesdk.Utility;
import com.baidu.sharesdk.ui.BaiduSocialShareUserInterface;
import com.joke.R;
import com.joke.adapter.JokeAdapter;
import com.joke.adapter.StoryAdapter;
import com.joke.config.BaiduSocialShareConfig;
import com.joke.net.dto.JokeBaseDto;
import com.joke.net.dto.JokeInfo;
import com.joke.net.http.JokeData;
import com.joke.util.Constants;
import com.joke.util.LogsUtil;
import com.joke.util.TipsUtil;
import com.joke.widget.PullDownView;
import com.joke.widget.PullDownView.OnPullDownListener;
import com.joke.widget.ScrollOverListView;






import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableRow;

public class MainActivity extends Activity implements
CompoundButton.OnCheckedChangeListener,OnClickListener {
	private static final String TAG="MainActivity";
	public static MainActivity instance = null;
	private ViewPager mTabPager;
	private ImageView mTab1, mTab2, mTab3,mTab4;
	private View view1, view2, view3,view4;
	
	/**
	 * 笑话
	 */
	private PullDownView pullDownView1;
	private ScrollOverListView listView1;
	private JokeAdapter jokeAdapter;
	private List<JokeInfo> data;
	private TableRow share_tr;;
	/**
	 * 故事
	 */
	private PullDownView pullDownView2;
	private ScrollOverListView listView2;
	private StoryAdapter storyAdapter;
	private List<JokeInfo> data2;
	
	
	/**
	 * 爆笑
	 */
	private PullDownView pullDownView3;
	private ScrollOverListView listView3;
	private List<JokeInfo> data3;
	private StoryAdapter sayAdapter;
	
	final Handler handler = new Handler(Looper.getMainLooper());

	private final static String appKey = BaiduSocialShareConfig.mbApiKey;
	private final static String wxAppId = BaiduSocialShareConfig.appID;
	private BaiduSocialShare socialShare;
	private BaiduSocialShareUserInterface socialShareUi;
	
	
	private JokeInfo selectInfo;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//全屏
//		getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
//		getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        setContentView(R.layout.activity_main);
        
        initSocialShare();
        
        initView();
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

		//获取社会化分享UI的实例对象   当自定义UI时使用
		socialShareUi = socialShare.getSocialShareUserInterfaceInstance();
		
		SocialShareLogger.on();
	}
    

	/**
	 *<b>function:</b>
	 *@author cuiran
	 *@createDate 2013-7-11 下午1:00:10
	 */
	private void initView() {
		// TODO Auto-generated method stub
		getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		instance = this;
		mTabPager = (ViewPager) findViewById(R.id.tabpager);
		
		mTabPager.setOnPageChangeListener(new MyOnPageChangeListener());
		
		
		mTab1 = (ImageView) findViewById(R.id.img_address);
		mTab2 = (ImageView) findViewById(R.id.img_friends);
		mTab3 = (ImageView) findViewById(R.id.img_settings);
		mTab4 = (ImageView) findViewById(R.id.img_more);

		mTab1.setOnClickListener(new MyOnClickListener(0));
		mTab2.setOnClickListener(new MyOnClickListener(1));
		mTab3.setOnClickListener(new MyOnClickListener(2));
		mTab4.setOnClickListener(new MyOnClickListener(3));
		
		LinearLayout linearLayout1 = (LinearLayout) findViewById(R.id.show1);
		LinearLayout linearLayout2 = (LinearLayout) findViewById(R.id.show2);
		LinearLayout linearLayout3 = (LinearLayout) findViewById(R.id.show3);
		LinearLayout linearLayout4 = (LinearLayout) findViewById(R.id.show4);

		linearLayout1.setOnClickListener(new MyOnClickListener(0));
		linearLayout2.setOnClickListener(new MyOnClickListener(1));
		linearLayout3.setOnClickListener(new MyOnClickListener(2));
		linearLayout4.setOnClickListener(new MyOnClickListener(3));
		
		/*************************************************************************/
		LayoutInflater mLi = LayoutInflater.from(this);
		view1 = mLi.inflate(R.layout.layout_joke, null);
		view2 = mLi.inflate(R.layout.layout_story, null);
		view3 = mLi.inflate(R.layout.layout_say, null);
		view4 = mLi.inflate(R.layout.layout_more, null);
		
	
		initMainView();
		
		final ArrayList<View> views = new ArrayList<View>();
		views.add(view1);
		views.add(view2);
		views.add(view3);
		views.add(view4);
		
		PagerAdapter mPagerAdapter = new PagerAdapter() {

			@Override
			public boolean isViewFromObject(View arg0, Object arg1) {
				return arg0 == arg1;
			}

			@Override
			public int getCount() {
				return views.size();
			}

			@Override
			public void destroyItem(View container, int position, Object object) {
				((ViewPager) container).removeView(views.get(position));
			}

			@Override
			public Object instantiateItem(View container, int position) {
				((ViewPager) container).addView(views.get(position));
				return views.get(position);
			}
		};

		mTabPager.setAdapter(mPagerAdapter);
	}
	/**
	 * 
	 *<b>function:</b>初始化1的界面
	 *@author cuiran
	 *@createDate 2013-7-12 下午5:47:04
	 */
	public void initMainView(){
		
		pullDownView1=(PullDownView)view1.findViewById(R.id.listview);
		pullDownView1.enableAutoFetchMore(true, 0);
		pullDownView1.enableLoadMore(false);
		listView1=pullDownView1.getListView();
		data=new ArrayList<JokeInfo>();
		jokeAdapter=new JokeAdapter(data,this);
		listView1.setAdapter(jokeAdapter);
		
		listView1.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				// TODO Auto-generated method stub
				selectInfo=data.get(position);
				
				LayoutInflater inflater = (LayoutInflater)MainActivity.this
						.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				View layout = inflater.inflate(R.layout.layout_list_menu, null);
				TipsUtil.alertCustomDialog(MainActivity.this, layout);
				share_tr=(TableRow)layout.findViewById(R.id.share_tr);
				
				share_tr.setOnClickListener(MainActivity.this);
				
				return true;
			}
			
			
		});
		
		listView1.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				// TODO Auto-generated method stub
				selectInfo=data.get(position);
				LayoutInflater inflater = (LayoutInflater)MainActivity.this
						.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				View layout = inflater.inflate(R.layout.layout_list_menu, null);
				TipsUtil.alertCustomDialog(MainActivity.this, layout);
				share_tr=(TableRow)layout.findViewById(R.id.share_tr);
				
				share_tr.setOnClickListener(MainActivity.this);
				
			}
			
		});
		
		pullDownView2=(PullDownView)view2.findViewById(R.id.listview1);
		pullDownView2.enableAutoFetchMore(true, 0);
		pullDownView2.enableLoadMore(false);
		listView2=pullDownView2.getListView();
		data2=new ArrayList<JokeInfo>();
		storyAdapter=new StoryAdapter(data2, this);
		listView2.setAdapter(storyAdapter);
		listView2.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {
				// TODO Auto-generated method stub
				 JokeInfo info=data2.get(position);
				 Intent intent=new Intent(MainActivity.this,StroyContentActivity.class);
				 Bundle bundle=new Bundle();
				 bundle.putSerializable("info", info);
				 intent.putExtras(bundle);
				 startActivity(intent);
			}
			
			
		});
		/**
		 * 爆笑
		 */
		pullDownView3=(PullDownView)view3.findViewById(R.id.listview2);
		pullDownView3.enableAutoFetchMore(true, 0);
		pullDownView3.enableLoadMore(false);
		
		listView3=pullDownView3.getListView();
		data3=new ArrayList<JokeInfo>();
		sayAdapter=new StoryAdapter(data3, this);
		listView3.setAdapter(sayAdapter);
		
		listView3.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {
				// TODO Auto-generated method stub
				 JokeInfo info=data3.get(position);
				 Intent intent=new Intent(MainActivity.this,StroyContentActivity.class);
				 Bundle bundle=new Bundle();
				 bundle.putSerializable("info", info);
				 intent.putExtras(bundle);
				 startActivity(intent);
			}
			
			
		});
		/**
		 * 更多界面
		 */
		pulldown();
		
		showdata();
		
	}
	/**
	 *<b>function:</b>刷新数据
	 *@author cuiran
	 *@createDate 2013-8-21 下午12:52:10
	 */
	private void pulldown() {
		// TODO Auto-generated method stub
		pullDownView1.setOnPullDownListener(new OnPullDownListener() {
			
			@Override
			public void onRefresh() {
				// TODO Auto-generated method stub
				pullDownView1.notifyDidRefresh(false);
				TipsUtil.alertShortToastTips(MainActivity.this, "当前已是最新");
			}
			@Override
			public void onLoadMore() {
				// TODO Auto-generated method stub
				
			}
		});
		
		pullDownView2.setOnPullDownListener(new OnPullDownListener() {
			
			@Override
			public void onRefresh() {
				// TODO Auto-generated method stub
				pullDownView2.notifyDidRefresh(false);
				TipsUtil.alertShortToastTips(MainActivity.this, "当前已是最新");
			}
			@Override
			public void onLoadMore() {
				// TODO Auto-generated method stub
				
			}
		});
	
		pullDownView3.setOnPullDownListener(new OnPullDownListener() {
		
		@Override
		public void onRefresh() {
			// TODO Auto-generated method stub
			pullDownView3.notifyDidRefresh(false);
			TipsUtil.alertShortToastTips(MainActivity.this, "当前已是最新");
		}
		@Override
		public void onLoadMore() {
			// TODO Auto-generated method stub
			
		}
	});
		
	}
	
	@SuppressWarnings("unused")
	private void getNewString(final Handler handler) {
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				
				
			}
		}).start();
	}
	


	/**
	 *<b>function:</b>取出数据
	 *@author cuiran
	 *@createDate 2013-7-12 下午5:54:10
	 */
	private void showdata() {
		// TODO Auto-generated method stub
		JokeData.getJokeData(this, jokeHandler);
		
		JokeData.getStoryData(this, storyHandler);
		
		JokeData.getSayData(this, sayHandler);
	}
	
	private Handler jokeHandler =new Handler(){
		 
		 
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			pullDownView1.notifyDidDataLoad(false);
		
			switch (msg.what) {
			case Constants.WORKERROR:
				TipsUtil.closeProgressDialog();
				TipsUtil.alertShortToastTips(MainActivity.this, 
						R.string.tip_msg_neterror);
				break;
			
			case Constants.SUCCESS:
				TipsUtil.closeProgressDialog();
				try{
					Bundle bundle=msg.getData();
					JokeBaseDto dto=(JokeBaseDto)bundle.getSerializable("dto");
					List<JokeInfo> jokes=dto.getJokes();
					LogsUtil.i(TAG, ""+jokes.size());
					data.addAll(jokes);
					jokeAdapter.notifyDataSetChanged();
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

	 private Handler storyHandler =new Handler(){
		 
		 
			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				pullDownView2.notifyDidDataLoad(false);
			
				switch (msg.what) {
				case Constants.WORKERROR:
					TipsUtil.closeProgressDialog();
					TipsUtil.alertShortToastTips(MainActivity.this, 
							R.string.tip_msg_neterror);
					break;
				
				case Constants.SUCCESS:
					TipsUtil.closeProgressDialog();
					try{
						Bundle bundle=msg.getData();
						JokeBaseDto dto=(JokeBaseDto)bundle.getSerializable("dto");
						List<JokeInfo> jokes=dto.getJokes();
						LogsUtil.i(TAG, ""+jokes.size());
						data2.addAll(jokes);
						storyAdapter.notifyDataSetChanged();
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
	
		 private Handler sayHandler =new Handler(){
			 
			 
				@Override
				public void handleMessage(Message msg) {
					// TODO Auto-generated method stub
					pullDownView3.notifyDidDataLoad(false);
				
					switch (msg.what) {
					case Constants.WORKERROR:
						TipsUtil.closeProgressDialog();
						TipsUtil.alertShortToastTips(MainActivity.this, 
								R.string.tip_msg_neterror);
						break;
					
					case Constants.SUCCESS:
						TipsUtil.closeProgressDialog();
						try{
							Bundle bundle=msg.getData();
							JokeBaseDto dto=(JokeBaseDto)bundle.getSerializable("dto");
							List<JokeInfo> jokes=dto.getJokes();
							LogsUtil.i(TAG, ""+jokes.size());
							data3.addAll(jokes);
							sayAdapter.notifyDataSetChanged();
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
		
	public class MyOnClickListener implements View.OnClickListener {
		private int index = 0;

		public MyOnClickListener(int i) {
			index = i;
		}

		public void onClick(View v) {
			LogsUtil.i(TAG, "点击："+index);
			mTabPager.setCurrentItem(index);
		}
	};
	
	public class MyOnPageChangeListener implements OnPageChangeListener {

		public void onPageSelected(int arg0) {
			LogsUtil.i(TAG, "点击onPageSelected："+arg0);
			switch (arg0) {
			case 0:
				mTab1.setImageDrawable(getResources().getDrawable(
						R.drawable.tab_address_pressed));
				mTab2.setImageDrawable(getResources().getDrawable(
						R.drawable.tab_find_frd_normal));
				mTab3.setImageDrawable(getResources().getDrawable(
						R.drawable.tab_weixin_normal));
				mTab4.setImageDrawable(getResources().getDrawable(
						R.drawable.tab_settings_normal));
				break;
			case 1:
				mTab2.setImageDrawable(getResources().getDrawable(
						R.drawable.tab_find_frd_pressed));
				mTab1.setImageDrawable(getResources().getDrawable(
						R.drawable.tab_address_normal));
				mTab3.setImageDrawable(getResources().getDrawable(
						R.drawable.tab_weixin_normal));

				mTab4.setImageDrawable(getResources().getDrawable(
						R.drawable.tab_settings_normal));
				break;
			case 2:
				mTab3.setImageDrawable(getResources().getDrawable(
						R.drawable.tab_weixin_pressed));
				mTab1.setImageDrawable(getResources().getDrawable(
						R.drawable.tab_address_normal));
				mTab2.setImageDrawable(getResources().getDrawable(
						R.drawable.tab_find_frd_normal));
				mTab4.setImageDrawable(getResources().getDrawable(
						R.drawable.tab_settings_normal));
				break;
			case 3:
				mTab4.setImageDrawable(getResources().getDrawable(
						R.drawable.tab_settings_pressed));
				mTab1.setImageDrawable(getResources().getDrawable(
						R.drawable.tab_address_normal));
				mTab2.setImageDrawable(getResources().getDrawable(
						R.drawable.tab_find_frd_normal));
				mTab3.setImageDrawable(getResources().getDrawable(
						R.drawable.tab_weixin_normal));
				break;
			}

		}

		public void onPageScrolled(int arg0, float arg1, int arg2) {
		}

		public void onPageScrollStateChanged(int arg0) {
		}
	}


	/* (non-Javadoc)
	 * @see android.widget.CompoundButton.OnCheckedChangeListener#onCheckedChanged(android.widget.CompoundButton, boolean)
	 */
	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		// TODO Auto-generated method stub
		
	}
	/**
	 * 
	 *<b>function:</b>跳转至我的家园
	 *@author cuiran
	 *@createDate 2013-7-15 下午2:37:13
	 *@param v
	 */
	public void share_mine(View v) {
		Intent intent = new Intent(this, MineActivity.class);
		
		startActivity(intent);
	}
	/**
	 * 
	 *<b>function:</b>跳转读哲理下载
	 *@author cuiran
	 *@createDate 2013-8-12 上午11:15:46
	 *@param v
	 */
	public void share_zheli(View v){
		 Intent it = new Intent(Intent.ACTION_VIEW, Uri.parse(Constants.APP_DUZHELI_URL));  
		 it.setClassName("com.android.browser", "com.android.browser.BrowserActivity");  
		 startActivity(it);  

	}
	/**
	 * 
	 *<b>function:</b>跳转至授权界面
	 *@author cuiran
	 *@createDate 2013-7-17 上午9:51:57
	 *@param v
	 */
	public void share_oauth(View v){
		Intent intent = new Intent(this, OAuthActivity.class);
		
		startActivity(intent);
	}



	/* (non-Javadoc)
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()){
		
		case R.id.share_tr:
			TipsUtil.closeCustomDialog();
			page_share(selectInfo);
			
			break;
		}
	}
	
	public void page_share(JokeInfo info) {
		 ShareContent pageContent=new ShareContent();
		 pageContent.setTitle(info.getSubject());
		 pageContent.setContent(Html.fromHtml(info.getContent()).toString());
		 pageContent.setUrl(Constants.TAOBAO_PC_URL);
		socialShareUi.showShareMenu(this, pageContent, Utility.SHARE_BOX_STYLE,
				new ShareListener() {
					@Override
					public void onAuthComplete(Bundle values) {
					}

					@Override
					public void onApiComplete(String responses) {
					    final String msg = responses;
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
//                                Utility.showAlert(MainActivity.this, msg);
                            	LogsUtil.i(TAG, "msg="+msg);
                            	 Utility.showAlert(MainActivity.this,"分享成功");
//                            	TipsUtil.alertShortToastTips(MainActivity.this, "分享成功");
                            }
                        });
					}

					@Override
					public void onError(BaiduShareException e) {
						LogsUtil.e(TAG, "page_share出现异常", e);
					}

				});
	}
	
	public void onResume() {
		
		super.onResume();
		LogsUtil.i(TAG, "onResume");
		StatWrapper.onResume(this);
	}

	public void onPause() {
		
		super.onPause();
		LogsUtil.i(TAG, "onPause");
		StatWrapper.onPause(this);
	}
}

class StatWrapper {
	public static void onResume(Context context) {

		/**
		 * 此处调用基本统计代码
		 */
		StatService.onResume(context);
	}

	public static void onPause(Context context) {

		/**
		 * 此处调用基本统计代码
		 */
		StatService.onPause(context);
	}
}