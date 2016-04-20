package net.ddns.hnmirror.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Token implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6559823584999105138L;
	private int tokenId;
	private int numberOfStoriesInDb = 0;
	private String searchString;
	private String searchBy;
	private int page;
	String request;
	String response = null;
	List<Story> stories = new ArrayList<Story>();

	public Token(int tokenId, String request) {
		this.tokenId = tokenId;
		this.request = request;
	}

	public void setNumberOfStoriesInDb(int numberOfStoriesInDb) {
		this.numberOfStoriesInDb = numberOfStoriesInDb;
	}

	public int getNumberOfStoriesInDb() {
		return numberOfStoriesInDb;
	}

	public void setTokenId(int tokeId) {
		this.tokenId = tokeId;
	}

	public int getTokenId() {
		return tokenId;
	}

	public void setRequest(String request) {
		this.request = request;
	}

	public String getRequest() {
		return request;
	}

	public void setResponse(String response) {
		this.response = response;
	}

	public String getResponse() {
		return response;
	}

	public void setStoriesArray(List<Story> stories) {
		this.stories = stories;
	}

	public List<Story> getStoriesArray() {
		return stories;
	}

	public void setSearchString(String searchString) {
		this.searchString = searchString;
	}

	public String getSearchString() {
		return searchString;
	}
	
	public void setSearchBy(String searchBy) {
		this.searchBy = searchBy;
	}
	
	public String getSearchBy() {
		return searchBy;
	}
	
	public void setPage(int page) {
		this.page = page;
	}
	
	public int getPage() {
		return page;
	}
}
