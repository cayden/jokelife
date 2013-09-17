/**
 * JokeData.java
 * 版权所有(C) 2013 
 * 创建:cuiran 2013-7-12 下午4:20:45
 */
package com.joke.net.http;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.joke.net.dto.JokeBaseDto;
import com.joke.net.dto.JokeInfo;
import com.joke.util.Constants;
import com.joke.util.LogsUtil;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

/**
 * TODO
 * @author cuiran
 * @version TODO
 */
public class JokeData {
	private static final String TAG="JokeData";
	private static Handler loginHandler;
	private static Handler storyHandler;
	private static Handler sayHandler;
	@SuppressWarnings("unused")
	private static Context context;
	private static Document doc = null;
	private static String link;
	/**
	 * 
	 *<b>function:</b>获取笑话内容
	 *@author cuiran
	 *@createDate 2013-7-12 下午4:22:56
	 */
	public static void getJokeData(Context _context, Handler _handler){
		loginHandler = _handler;
		context = _context;
		
		Thread sendHandler = new Thread(new JokeDataHandler());
		sendHandler.start();
		  
	}
	/**
	 * 
	 *<b>function:</b>获取故事
	 *@author cuiran
	 *@createDate 2013-7-15 上午11:36:33
	 *@param _context
	 *@param _handler
	 */
	public static void getStoryData(Context _context, Handler _handler){
		storyHandler = _handler;
		context = _context;
		
		Thread sendHandler = new Thread(new StoryDataHandler());
		sendHandler.start();
	}
	
	/**
	 * 
	 *<b>function:</b>获取爆笑
	 *@author cuiran
	 *@createDate 2013-7-15 上午11:36:33
	 *@param _context
	 *@param _handler
	 */
	public static void getSayData(Context _context, Handler _handler){
		sayHandler = _handler;
		context = _context;
		
		Thread sendHandler = new Thread(new SayDataHandler());
		sendHandler.start();
	}
	
	static class  SayDataHandler implements Runnable {

		/* (non-Javadoc)
		 * @see java.lang.Runnable#run()
		 */
		@Override
		public void run() {
			// TODO Auto-generated method stub
			JokeBaseDto dto=new JokeBaseDto();
			List<JokeInfo> jokes=new ArrayList<JokeInfo>();
				try {
		            doc = Jsoup.connect(Constants.URL_SAY).get();
		       	 Elements   elements1= doc.getElementsByAttributeValue("class", "text_doublelist1");
				 System.out.println(elements1.size());
				 String html=elements1.get(0).html();
				 Document doc1=Jsoup.parse(html);
				 Elements elements= doc1.select("li");
				 for(Element element:elements ){
					 Elements links=element.select("a");
					 for(Element link : links) { 
						 String target=link.attr("target"); 
						JokeInfo info=new JokeInfo();
						 if("_blank".equals(target)){
							 String linkHref = link.attr("href"); 
							 String linkText = link.text(); 
							 info.setSubject(linkText);
							 info.setLink(linkHref);
							 jokes.add(info);
						 }
						
					 }
				 }
				 
			        dto.setJokes(jokes);
			        Message msg = new Message();
					msg.what = Constants.SUCCESS;
					Bundle bundle=new Bundle();
					bundle.putSerializable("dto", dto);
					msg.setData(bundle);
					sayHandler.sendMessage(msg);
					
		        } catch (IOException e) {
		            // TODO Auto-generated catch block
		           LogsUtil.e(TAG, "getJokeData出现异常", e);
		           Message msg = new Message();
					msg.what = Constants.ERROR;
					sayHandler.sendMessage(msg);
					return;
		       }
				
				
		}
		
	}
		
	
	/**
	 * 
	 *<b>function:</b>获取故事内容
	 *@author cuiran
	 *@createDate 2013-7-15 上午11:36:33
	 *@param _context
	 *@param _handler
	 */
	public static void getStoryContentData(String _link,Context _context, Handler _handler){
		storyHandler = _handler;
		context = _context;
		link=_link;
		Thread sendHandler = new Thread(new StoryContentDataHandler());
		sendHandler.start();
	}
	
	static class  StoryContentDataHandler implements Runnable {

		/* (non-Javadoc)
		 * @see java.lang.Runnable#run()
		 */
		@Override
		public void run() {
			// TODO Auto-generated method stub
			
			 JokeInfo info=new JokeInfo();
				try {
		            doc = Jsoup.connect(Constants.URL+link).get();
		            Elements   elements1= doc.getElementsByAttributeValue("id", "endtext");
		            info.setContent(elements1.get(0).html());
			        Message msg = new Message();
					msg.what = Constants.SUCCESS;
					Bundle bundle=new Bundle();
					bundle.putSerializable("dto", info);
					msg.setData(bundle);
					storyHandler.sendMessage(msg);
					
		        } catch (IOException e) {
		            // TODO Auto-generated catch block
		           LogsUtil.e(TAG, "getJokeData出现异常", e);
		           Message msg = new Message();
					msg.what = Constants.ERROR;
					storyHandler.sendMessage(msg);
					return;
		       }
				
				
		}
		
	}
			
	
	
	static class  StoryDataHandler implements Runnable {

		/* (non-Javadoc)
		 * @see java.lang.Runnable#run()
		 */
		@Override
		public void run() {
			// TODO Auto-generated method stub
			JokeBaseDto dto=new JokeBaseDto();
			List<JokeInfo> jokes=new ArrayList<JokeInfo>();
				try {
		            doc = Jsoup.connect(Constants.URL_STORY).get();
		       	 Elements   elements1= doc.getElementsByAttributeValue("class", "text_doublelist1");
				 System.out.println(elements1.size());
				 String html=elements1.get(0).html();
				 Document doc1=Jsoup.parse(html);
				 Elements elements= doc1.select("li");
				 for(Element element:elements ){
					 Elements links=element.select("a");
					 for(Element link : links) { 
						 String target=link.attr("target"); 
						JokeInfo info=new JokeInfo();
						 if("_blank".equals(target)){
							 String linkHref = link.attr("href"); 
							 String linkText = link.text(); 
							 info.setSubject(linkText);
							 info.setLink(linkHref);
							 jokes.add(info);
						 }
						
					 }
				 }
				 
			        dto.setJokes(jokes);
			        Message msg = new Message();
					msg.what = Constants.SUCCESS;
					Bundle bundle=new Bundle();
					bundle.putSerializable("dto", dto);
					msg.setData(bundle);
					storyHandler.sendMessage(msg);
					
		        } catch (IOException e) {
		            // TODO Auto-generated catch block
		           LogsUtil.e(TAG, "getJokeData出现异常", e);
		           Message msg = new Message();
					msg.what = Constants.ERROR;
					storyHandler.sendMessage(msg);
					return;
		       }
				
				
		}
		
	}
			
	
	
	static class  JokeDataHandler implements Runnable {

		/* (non-Javadoc)
		 * @see java.lang.Runnable#run()
		 */
		@Override
		public void run() {
			// TODO Auto-generated method stub
			JokeBaseDto dto=new JokeBaseDto();
			List<JokeInfo> jokes=new ArrayList<JokeInfo>();
				try {
		            doc = Jsoup.connect(Constants.URL_JOKE).get();
		            /**
			         * 获取标题
			         * 热门标签
					按题目显示分页...
			         */
		            
			        Elements elements= doc.select("h3");
			        List<String> subjects=new ArrayList<String>();
				    for(Element element:elements){
				    	 String s1=element.text();
				    	 if(s1.contains("热门标签")){
				    		 
				    	 }else if(s1.contains("按题目显示分页...")){
				    		 
				    	 }else{
				    		 subjects.add(s1);
				    	 }
				    }
				    LogsUtil.i(TAG, "subjects.size="+subjects.size());
				    List<String> contents=new ArrayList<String>();
				    Elements   elements1= doc.getElementsByAttributeValue("id", "endtext");
			     
			        for(Element element:elements1){
				    	
				    	  contents.add(element.html());
				    }
			        
			        LogsUtil.i(TAG, "contents.size="+contents.size());
			        for(int i=0;i<contents.size();i++){
			        	JokeInfo info=new JokeInfo();
			        	info.setSubject(subjects.get(i));
			        	info.setContent(contents.get(i));
			        	jokes.add(info);
			        }
			        
			        dto.setJokes(jokes);
			        Message msg = new Message();
					msg.what = Constants.SUCCESS;
					Bundle bundle=new Bundle();
					bundle.putSerializable("dto", dto);
					msg.setData(bundle);
					loginHandler.sendMessage(msg);
					
		        } catch (IOException e) {
		            // TODO Auto-generated catch block
		           LogsUtil.e(TAG, "getJokeData出现异常", e);
		           Message msg = new Message();
					msg.what = Constants.ERROR;
					loginHandler.sendMessage(msg);
					return;
		       }
				
				
		}
		
	}
			
}
