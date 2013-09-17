/**
 * JokeBaseDto.java
 * 版权所有(C) 2013 
 * 创建:cuiran 2013-7-12 下午5:40:54
 */
package com.joke.net.dto;

import java.io.Serializable;
import java.util.List;

/**
 * TODO
 * @author cuiran
 * @version TODO
 */
public class JokeBaseDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6593576087060439215L;

	private List<JokeInfo> jokes;

	public List<JokeInfo> getJokes() {
		return jokes;
	}

	public void setJokes(List<JokeInfo> jokes) {
		this.jokes = jokes;
	}
	
}
