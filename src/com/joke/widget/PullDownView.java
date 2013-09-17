package com.joke.widget;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import com.joke.R;
import com.joke.widget.ScrollOverListView.OnScrollOverListener;


import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


/**
 * 下拉刷新控件</br>
 * 真正实现下拉刷新的是这个控件，
 * ScrollOverListView只是提供触摸的事件等
 * @author Solo Email:kjsoloho@gmail.com
 */
public class PullDownView extends LinearLayout implements OnScrollOverListener, OnScrollListener {
	private static final String TAG = "PullDownView";
	private static final boolean DEBUG = false;
	
	private static final int AUTO_INCREMENTAL = 10;		// 自增量，用于回弹
	
	private static final int WHAT_SET_HEADER_HEIGHT = 1;// Handler what 设置高度
	
	private static SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd HH:mm");
	
	private View mHeaderView;
	private LayoutParams mHeaderViewParams;	
	private TextView mHeaderViewDateView;
	private TextView mHeaderTextView;
	private ImageView mHeaderArrowView;
	private View mHeaderLoadingView;
	private View mFooterView;
	private TextView mFooterTextView;
	private View mFooterLoadingView;
	private ScrollOverListView mListView;
	
	private OnPullDownListener mOnPullDownListener;
	private RotateAnimation mRotateOTo180Animation;
	private RotateAnimation mRotate180To0Animation;
	
	private Context mContext;
	@SuppressWarnings("unused")
	private Field overScrollModeField;
	
	private int mMoveDeviation;				// 移动误差
	private int mHeaderIncremental;			// 头部文件自增量增量
	private int mDefaultHeaderViewHeight;	// 头部文件原本的高度
	private int mStartIndex;				// 当前List可显示第一项
	//private int mEndIndex;					// 当前List可显示最后一项
	
	private float mMotionDownLastY;	// 按下时候的Y轴坐标
	
	private boolean mEnablePullDown;		// 是否可以下拉
	private boolean mIsPullUpDone;			// 是否回推完成
	private boolean mEnableLoadMore;			// 是否启用更多
	private boolean mEnableAutoFetchMore;	// 是否允许自动获取更多
	private boolean mIsNoMoreData;			// 没有更多的数据了
	private boolean mIsDidLoad;				// 是否加载了数据
	
	// 头部文件的状态
	private static final int HEADER_VIEW_STATE_IDLE = 0;			// 空闲
	private static final int HEADER_VIEW_STATE_NOT_OVER_HEIGHT = 1;	// 没有超过默认高度
	private static final int HEADER_VIEW_STATE_OVER_HEIGHT = 2;		// 超过默认高度
	private int mHeaderViewState = HEADER_VIEW_STATE_IDLE;
	
	private static final int STATE_NONE = 0;
	private static final int STATE_REFRESHING = 1;		// 刷新中
	private static final int STATE_LOADING_MORE = 2;	// 加载更多中
	private static final int STATE_DRAGING = 4;			// 拖动中
	private static final int STATE_MOTION_DOWN = 8;		// 按下
	private int state = STATE_NONE;

	public PullDownView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initHeaderViewAndFooterViewAndListView(context);
	}

	public PullDownView(Context context) {
		super(context);
		initHeaderViewAndFooterViewAndListView(context);
	}
	
	/*
	 * ==================================
	 * Public method
	 * 外部使用，具体就是用这几个就可以了
	 * 
	 * ==================================
	 */
	
	/**
	 * 刷新事件接口
	 * @author Solo Email:kjsoloho@gmail.com
	 */
	public interface OnPullDownListener {
		void onRefresh();
		void onLoadMore();
	}
	
	/**
	 * 通知加载完了数据，要放在Adapter.notifyDataSetChanged后面
	 * 当你加载完数据的时候，调用这个notifyDidLoad()
	 * 才会隐藏头部，并初始化数据等
	 */
	public void notifyDidDataLoad(boolean isNoMoreData) {
		mIsDidLoad = true;
		mIsNoMoreData = isNoMoreData;
		mFooterView.setVisibility(View.VISIBLE);
		updateFooter();
		mListView.setFooterDividersEnabled(true);
		
		mHeaderViewParams.height = 0;
		mHeaderView.setLayoutParams(mHeaderViewParams);
		updateHeader();
		
		doListViewIdleActionOnDataDidLoad();
	}
	
	/**
	 * 通知已经刷新完了，要放在Adapter.notifyDataSetChanged后面
	 * 当你执行完刷新任务之后，调用这个notifyDidRefresh()
	 * 才会隐藏掉头部文件等操作
	 */
	public void notifyDidRefresh(boolean isNoMoreData) {
		mIsNoMoreData = isNoMoreData;
		updateFooter();
		
		state &= ~STATE_REFRESHING;
		mHeaderViewState = HEADER_VIEW_STATE_IDLE;
		setHeaderHeight(0);
		updateHeader();
		
		doListViewIdleActionOnDataDidLoad();
	}
	
	/**
	 * 通知已经获取完更多了，要放在Adapter.notifyDataSetChanged后面
	 * 当你执行完更多任务之后，调用这个notyfyDidMore()
	 * 才会隐藏加载圈等操作
	 */
	public void notifyDidLoadMore(boolean isNoMoreData) {
		mIsNoMoreData = isNoMoreData;
		state &= ~STATE_LOADING_MORE;
		updateFooter();
	}

	/**
	 * 设置监听器
	 * @param listener
	 */
	public void setOnPullDownListener(OnPullDownListener listener){
		mOnPullDownListener = listener;
	}

	/**
	 * 获取内嵌的listview
	 * @return ScrollOverListView
	 */
	public ScrollOverListView getListView(){
		return mListView;
	}

	/**
	 * 是否开启自动获取更多</br>
	 * 自动获取更多，并在触摸到达底部的时候自动刷新
	 * @param index 倒数第几个触发
	 */
	public void enableAutoFetchMore(boolean enable, int index){
		if(!mEnableLoadMore) return;
		mEnableAutoFetchMore = enable;
		if(enable){
			mListView.setBottomPosition(index);
		}else{
			updateFooter();
		}
	}
	
	/**
	 * 是否启用加载更多</br>
	 * 为false 在底部不显示加载更多</br>
	 * 只能在初始化的时候调用 
	 */
	public void enableLoadMore(boolean enable) {
		mEnableLoadMore = enable;
		if (!enable) {
			// TODO 暂时使用这种错开的方法
			// 更好的办法是写到属性里面
			mUIHandler.post(new Runnable() {
				
				@Override
				public void run() {
					removeFooter();
				}
			});
		}
	}
	
	/**
	 * 是否启用下拉刷新
	 */
	public void enablePullDown(boolean enable) {
		mEnablePullDown = enable;
	}
	
	/*
	 * ==================================
	 * Private method
	 * 具体实现下拉刷新等操作
	 * 
	 * ==================================
	 */
	
	/**
	 * 初始化界面
	 */
	private void initHeaderViewAndFooterViewAndListView(Context context){
		setOrientation(LinearLayout.VERTICAL);
		
		mContext = context;
		
		/*
		 * 自定义头部文件
		 * 放在这里是因为考虑到很多界面都需要使用
		 * 如果要修改，和它相关的设置都要更改
		 */
		mEnablePullDown = true;
		mHeaderView = LayoutInflater.from(context).inflate(R.layout.pulldown_header, null);
		mHeaderViewParams = new LayoutParams(android.view.ViewGroup.LayoutParams.FILL_PARENT, android.view.ViewGroup.LayoutParams.WRAP_CONTENT);
		mHeaderIncremental = mDefaultHeaderViewHeight;
		addView(mHeaderView, 0, mHeaderViewParams);
		
		mDefaultHeaderViewHeight = getResources().getDimensionPixelSize(R.dimen.pulldown_headerview_height);
		mMoveDeviation = getResources().getDimensionPixelSize(R.dimen.pulldown_move_deviation);
		mHeaderTextView = (TextView) mHeaderView.findViewById(R.id.pulldown_header_text);
		mHeaderArrowView = (ImageView) mHeaderView.findViewById(R.id.pulldown_header_arrow);
		mHeaderViewDateView = (TextView) mHeaderView.findViewById(R.id.pulldown_header_date);
		mHeaderLoadingView = mHeaderView.findViewById(R.id.pulldown_header_loading);
		
		// 注意，图片旋转之后，再执行旋转，坐标会重新开始计算
		mRotateOTo180Animation = new RotateAnimation(0, 180, 
				Animation.RELATIVE_TO_SELF, 0.5f, 
				Animation.RELATIVE_TO_SELF, 0.5f);
		mRotateOTo180Animation.setDuration(250);
		mRotateOTo180Animation.setFillAfter(true);
		
		mRotate180To0Animation = new RotateAnimation(180, 0, 
				Animation.RELATIVE_TO_SELF, 0.5f, 
				Animation.RELATIVE_TO_SELF, 0.5f);
		mRotate180To0Animation.setDuration(250);
		mRotate180To0Animation.setFillAfter(true);
		
		
		/*
		 * 自定义脚部文件
		 */
		mEnableLoadMore = true;
		mFooterView = LayoutInflater.from(mContext).inflate(R.layout.pulldown_footer, null);
		mFooterView.setVisibility(View.GONE);
		mFooterTextView = (TextView) mFooterView.findViewById(R.id.pulldown_footer_text);
		mFooterLoadingView = mFooterView.findViewById(R.id.pulldown_footer_loading);
		mFooterView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (mIsNoMoreData || !mIsDidLoad || (state & STATE_LOADING_MORE) == STATE_LOADING_MORE) return;
				ScrollOverListView listView = mListView;
				if (listView.getCount() - listView.getHeaderViewsCount() - listView.getFooterViewsCount() > 0) {
					state |= STATE_LOADING_MORE;
					updateFooter();
					mOnPullDownListener.onLoadMore();
				}
			}
		});
		
		
		/*
		 * ScrollOverListView 同样是考虑到基本都要使用，所以放在这里
		 * 同时因为，需要它的监听事件
		 */
//		mListView = new ScrollOverListView(context,null,R.style.Activity_ListView_Specfication);
		mListView = new ScrollOverListView(context);
		mListView.setSelector(getResources().getDrawable(
                R.drawable.specfication_listview_style)); 
		mListView.setFooterDividersEnabled(false);
		mListView.setId(android.R.id.list);
		mListView.addFooterView(mFooterView);
		mListView.setOnScrollOverListener(this);
		mListView.setOnScrollListener(this);
		
		// 因为2.3之后的某些ListView控件自己实现了pull阴影动画效果
		// 所以我们在这里屏蔽他们
		try {
			Method method = AbsListView.class.getDeclaredMethod("setOverScrollMode", int.class);
			method.setAccessible(true);
			method.invoke(mListView, 2);//View.OVER_SCROLL_NEVER
		} catch (Exception e) {
			e.printStackTrace();
		}
		addView(mListView, android.view.ViewGroup.LayoutParams.FILL_PARENT, android.view.ViewGroup.LayoutParams.FILL_PARENT);

		// set ListView animation
		/*
		AnimationSet set = new AnimationSet(true);

        Animation animation = new AlphaAnimation(0.0f, 1.0f);
        animation.setDuration(50);
        set.addAnimation(animation);

        animation = new TranslateAnimation(
            Animation.RELATIVE_TO_SELF, 0.0f,Animation.RELATIVE_TO_SELF, 0.0f,
            Animation.RELATIVE_TO_SELF, -1.0f,Animation.RELATIVE_TO_SELF, 0.0f
        );
        animation.setDuration(100);
        set.addAnimation(animation);

        LayoutAnimationController controller = new LayoutAnimationController(set, 0.5f);
        mListView.setLayoutAnimation(controller);
		*/
		
		// 空的listener
		mOnPullDownListener = new OnPullDownListener() {
			@Override
			public void onRefresh() {}
			@Override
			public void onLoadMore() {}
		};
	}
	
	/**
	 * 去掉底部文件
	 */
	private void removeFooter(){
		if(mListView.getFooterViewsCount() > 0 && mListView != null && mFooterView != null){
			mListView.removeFooterView(mFooterView);
		}
	}
	
	/**
	 * 更新头部文件</br>
	 * 如果刷新中显示Loading，
	 * 如果拖动中，显示拖动效果，
	 * 否则显示更新时间。
	 */
	private void updateHeader() {
		if ((state & STATE_REFRESHING) == STATE_REFRESHING) {
			mHeaderArrowView.clearAnimation();
			mHeaderArrowView.setVisibility(View.GONE);
			mHeaderLoadingView.setVisibility(View.VISIBLE);
			mHeaderTextView.setText("正在刷新中...");
		} else if ((state & STATE_DRAGING) == STATE_DRAGING) {
			if(mHeaderViewParams.height >= mDefaultHeaderViewHeight){
				if(mHeaderViewState == HEADER_VIEW_STATE_OVER_HEIGHT) return;
				mHeaderArrowView.setVisibility(View.VISIBLE);
				mHeaderLoadingView.setVisibility(View.GONE);
				mHeaderViewDateView.setVisibility(View.VISIBLE);
				mHeaderViewState = HEADER_VIEW_STATE_OVER_HEIGHT;
				mHeaderTextView.setText("松开可以刷新");
				mHeaderArrowView.startAnimation(mRotateOTo180Animation);
			}else{
				if(mHeaderViewState == HEADER_VIEW_STATE_NOT_OVER_HEIGHT
						|| mHeaderViewState == HEADER_VIEW_STATE_IDLE) return;
				mHeaderArrowView.setVisibility(View.VISIBLE);
				mHeaderLoadingView.setVisibility(View.GONE);
				mHeaderViewDateView.setVisibility(View.VISIBLE);
				mHeaderViewState = HEADER_VIEW_STATE_NOT_OVER_HEIGHT;
				mHeaderTextView.setText("下拉可以刷新");
				mHeaderArrowView.startAnimation(mRotate180To0Animation);
			}
		} else {
			mHeaderLoadingView.setVisibility(View.GONE);
			mHeaderViewDateView.setVisibility(View.VISIBLE);
			mHeaderArrowView.setVisibility(View.VISIBLE);
			mHeaderTextView.setText("下拉可以刷新");
			mHeaderViewDateView.setText("更新于：" + dateFormat.format(new Date(System.currentTimeMillis())));
		}
	}
	
	/** 
	 * 更新脚部文件</br>
	 * 正常显示"更多"，
	 * 如果没有更多的数据就显示"已加载完全部"，
	 * 如果加载中就显示"加载中..."。
	 */
	private void updateFooter() {
		if (!mEnableLoadMore) return;
		
		if (mIsNoMoreData) {
			mFooterTextView.setText("已加载完全部");
			mFooterLoadingView.setVisibility(View.GONE);
		} else if ((state & STATE_LOADING_MORE) == STATE_LOADING_MORE) {
			mFooterTextView.setText("加载更多中...");
			mFooterLoadingView.setVisibility(View.VISIBLE);
		} else {
			mFooterTextView.setText("更多");
			mFooterLoadingView.setVisibility(View.GONE);
		}
	}
	
	private void setHeaderHeight(final int height){
		mHeaderIncremental = height;
		mHeaderViewParams.height = height;
		mHeaderView.setLayoutParams(mHeaderViewParams);
	}

	/**
	 * 自动隐藏动画
	 */
	class HideHeaderViewTask extends TimerTask{
		@Override
		public void run() {
			if((state & STATE_MOTION_DOWN) == STATE_MOTION_DOWN) {
				cancel();
				return;
			}
			mHeaderIncremental -= AUTO_INCREMENTAL;
			if(mHeaderIncremental > 0){
				mUIHandler.sendEmptyMessage(WHAT_SET_HEADER_HEIGHT);
			}else{
				mHeaderIncremental = 0;
				mUIHandler.sendEmptyMessage(WHAT_SET_HEADER_HEIGHT);
				cancel();
			}
		}
	}
	
	/**
	 * 自动显示动画
	 */
	class ShowHeaderViewTask extends TimerTask{

		@Override
		public void run() {
			if((state & STATE_MOTION_DOWN) == STATE_MOTION_DOWN) {
				cancel();
				return;
			}
			mHeaderIncremental -= AUTO_INCREMENTAL;
			if(mHeaderIncremental > mDefaultHeaderViewHeight){
				mUIHandler.sendEmptyMessage(WHAT_SET_HEADER_HEIGHT);
			}else{
				mHeaderIncremental = mDefaultHeaderViewHeight;
				mUIHandler.sendEmptyMessage(WHAT_SET_HEADER_HEIGHT);
				if (mIsDidLoad && (state & STATE_REFRESHING) != STATE_REFRESHING) {
					state |= STATE_REFRESHING;
					mUIHandler.post(new Runnable() {
						
						@Override
						public void run() {
							// 要清除掉动画，否则无法隐藏
							updateHeader();
							mHeaderArrowView.clearAnimation();
							mHeaderArrowView.setVisibility(View.INVISIBLE);
							mHeaderLoadingView.setVisibility(View.VISIBLE);
							mOnPullDownListener.onRefresh();
						}
					});
				}
				cancel();
			}
		}
	}


	private Handler mUIHandler = new Handler(){

		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
				case WHAT_SET_HEADER_HEIGHT :{
					setHeaderHeight(mHeaderIncremental);
					return;
				}
			}
		}
		
	};
	
	/**
	 * 当刷新完数据时，手动调用onIdle事件
	 */
	private void doListViewIdleActionOnDataDidLoad(){
		new Handler().postDelayed(new Runnable() {
			
			@Override
			public void run() {
				if(mOnListViewIdleListener != null){
					// 不需要考虑FooterView
					final int firstVisiblePosition = mListView.getFirstVisiblePosition();
					final int childCount = mListView.getChildCount(); 
					//Log.d(TAG, "[doListViewIdleActionOnDataDidLoad] firstVisiblePosition:" + firstVisiblePosition + " childCount:" + childCount);
					mOnListViewIdleListener.onIdle(firstVisiblePosition, childCount);
				}
			}
		}, 0);
	}
	
	/**
	 * 条目是否填满整个屏幕
	 */
	@SuppressWarnings("unused")
	private boolean isFillScreenItem(){
		final int firstVisiblePosition = mListView.getFirstVisiblePosition();
		final int lastVisiblePosition = mListView.getLastVisiblePosition() - mListView.getFooterViewsCount();
		final int visibleItemCount = lastVisiblePosition - firstVisiblePosition + 1;
		final int totalItemCount = mListView.getCount() - mListView.getFooterViewsCount();
		
		if(visibleItemCount < totalItemCount) return true;
		return false;
	}
	
	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		if (ev.getAction() == MotionEvent.ACTION_DOWN) {
			state |= STATE_MOTION_DOWN;
			mIsPullUpDone = false;
			mMotionDownLastY = ev.getRawY();
			Log.d(TAG, "pulldownview.onIntercept:" + mMotionDownLastY);
		}
		return super.onInterceptTouchEvent(ev);
	}
	
	/*
	 * ==================================
	 * 实现 OnScrollOverListener接口
	 * 
	 * 
	 * ==================================
	 */

	@Override
	public boolean onListViewTopAndPullDown(MotionEvent event, int delta) {
		// 因为某些ListView控件自己已经添加了下拉效果
		// 所以我们必须返回true，控制ListView不会滚动，达到屏蔽的效果
		if ((state & STATE_REFRESHING) == STATE_REFRESHING || !mEnablePullDown || !mIsDidLoad) {
			return true;
		}
		
		if (mListView.getCount() - mListView.getFooterViewsCount() == 0) {
			return true;
		}
		
		// 如果开始按下到滑动距离不超过误差值，则不滑动
		if(mHeaderViewParams.height <= 0){
			final int absMotionY = (int) Math.abs(event.getRawY() - mMotionDownLastY);
			if(absMotionY < mMoveDeviation) {
				return true;
			}
		}
		
		int absDelta = Math.abs(delta);
		final int i = (int) Math.ceil((double)absDelta / 2);
		mHeaderIncremental += i;
		if(mHeaderIncremental >= 0){ // && mIncremental <= mMaxHeight
			setHeaderHeight(mHeaderIncremental);
			updateHeader();
		}
		return true;
	}

	@Override
	public boolean onListViewBottomAndPullUp(MotionEvent event, int delta) {
		if((state & STATE_LOADING_MORE) == STATE_LOADING_MORE
				|| !mIsDidLoad || !mEnableAutoFetchMore || mIsNoMoreData) return false;
		ScrollOverListView listView = mListView;
		if (listView.getCount() - listView.getHeaderViewsCount() - listView.getFooterViewsCount() > 0) {
			state |= STATE_LOADING_MORE;
			updateFooter();
			mOnPullDownListener.onLoadMore();
		}
		return true;
	}

	@Override
	public boolean onMotionDown(MotionEvent ev) {
		return false;
	}

	@Override
	public boolean onMotionMove(MotionEvent ev, int delta) {
		state |= STATE_DRAGING;
		//当头部文件回推消失的时候，不允许滚动
		if(mIsPullUpDone) return true;
		
		// onTopDown在顶部，并向上回推和onTopUp相对
		if(mHeaderViewParams.height > 0 && delta < 0){
			final int absDelta = Math.abs(delta);
			final int i = (int) Math.ceil((double)absDelta / 2);
			
			mHeaderIncremental -= i;
			if(mHeaderIncremental > 0){
				setHeaderHeight(mHeaderIncremental);
				updateHeader();
			}else{
				mHeaderViewState = HEADER_VIEW_STATE_IDLE;
				mHeaderIncremental = 0;
				setHeaderHeight(mHeaderIncremental);
				mIsPullUpDone = true;
			}
			return true;
		}
		return false;
	}

	@Override
	public boolean onMotionUp(MotionEvent ev) {
		state &= ~STATE_DRAGING;
		state &= ~STATE_MOTION_DOWN;
		// 避免和点击事件冲突
		if(mHeaderViewParams.height > 0 || mIsPullUpDone){
			
			// 判断头文件拉动的距离与设定的高度，小了就隐藏，多了就固定高度
			int x = mHeaderIncremental - mDefaultHeaderViewHeight;
			Timer timer = new Timer(true);
			if(x < 0){
				timer.scheduleAtFixedRate(new HideHeaderViewTask(), 0, 10);
			}else{
				timer.scheduleAtFixedRate(new ShowHeaderViewTask(), 0, 10);
			}
			return true;
		}
		return false;//TODO
	}
	
	/*
	 * =====================================
	 * 监听ListView停止事件
	 * =====================================
	 */
	
	private OnListViewIdleListener mOnListViewIdleListener;
	
	public interface OnListViewIdleListener {
		void onIdle(int startIndex, int count);
	}
	
	public void setOnListViewIdleListener(OnListViewIdleListener listener){
		if(mListView != null){
			mOnListViewIdleListener = listener;
		}
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		if(scrollState == SCROLL_STATE_IDLE){
			if (DEBUG) Log.d(TAG, "IDLE");
			if(mOnListViewIdleListener != null){
				int count = mListView.getChildCount();
				final int childEndIndex = mStartIndex + mListView.getChildCount() -1;
				final int listEndIndex = mListView.getCount() -1;
				if(childEndIndex == listEndIndex){
					count -= mListView.getFooterViewsCount();
				}
				mOnListViewIdleListener.onIdle(mStartIndex, count);
			}
		}
		switch (scrollState) {
		case SCROLL_STATE_FLING:
			if (DEBUG) Log.d(TAG, "FLING");
			break;
		case SCROLL_STATE_TOUCH_SCROLL:
			if (DEBUG) Log.d(TAG, "SCROLL");
		default:
			break;
		}
	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
		if (DEBUG) Log.d(TAG, "onScroll");
		mStartIndex = firstVisibleItem;
		
		// 因为三星I91002.3版本如果设置over_scroll_never会出现回弹问题，所以在这里处理一下
		// 更新12-9-9 2:08：晕死，测试三星2.3.3版本如果反射使用这些属性会报错。
		/*
		final ScrollOverListView localListView = this.mListView;		
		final boolean hasItem = localListView.getCount() > 0;

		try {
			if (overScrollModeField == null) return;
			final Integer mode = (Integer) overScrollModeField.get(localListView);
			
			if (firstVisibleItem <= 0 && hasItem) {
				if (mode != View.OVER_SCROLL_NEVER) {
					if (DEBUG) Log.w(TAG, "set over scroll never");
					overScrollModeField.set(localListView, View.OVER_SCROLL_NEVER);
				}
			} else if (firstVisibleItem + visibleItemCount >= totalItemCount && hasItem) {
				if (mode != View.OVER_SCROLL_ALWAYS) {
					if (DEBUG) Log.w(TAG, "set over scroll always");
					overScrollModeField.set(localListView, View.OVER_SCROLL_ALWAYS);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		*/
	}

}
