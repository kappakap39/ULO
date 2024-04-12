package com.ava.dynamic.model.nav;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

public class NavigationItem {
	private Integer id;
	private String displayName;
	private String link;
	private String requestUrl;
	private String queryString;
	private boolean forceLink;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getDisplayName() {
		return displayName;
	}
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	public String getLink() {
		if(!forceLink){
			return requestUrl+"?"+queryString;
		}
		return link;
	}
	public void setLink(String link) {
		this.forceLink = true;
		this.link = link;
	}
	public String getRequestUrl() {
		return requestUrl;
	}
	public void setRequestUrl(String requestUrl) {
		this.requestUrl = requestUrl;
	}
	public String getQueryString() {
		return queryString;
	}
	public void setQueryString(String queryString) {
		this.queryString = queryString;
	}
	@Override
	public String toString() {
		ReflectionToStringBuilder builder = new ReflectionToStringBuilder(this);
		return builder.toString();
	}	
}
