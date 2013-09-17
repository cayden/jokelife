/**
 * JokeInfo.java
 * 版权所有(C) 2013 
 * 创建:cuiran 2013-7-11 下午6:49:27
 */
package com.joke.net.dto;

import java.io.Serializable;

/**
 * TODO笑话对象
 * @author cuiran
 * @version 1.0.0
 */
public class JokeInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5185864172253189448L;
	private int id;
	private String subject;
	private String content;
	private String link;
	private String createDate;
	private String isread="0";
	private String type;
	
	

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getIsread() {
		return isread;
	}

	public void setIsread(String isread) {
		this.isread = isread;
	}

	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	

}
