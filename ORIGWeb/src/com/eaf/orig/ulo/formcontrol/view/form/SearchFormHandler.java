package com.eaf.orig.ulo.formcontrol.view.form;

import java.io.Serializable;
import java.util.HashMap;

import org.apache.log4j.Logger;
@SuppressWarnings("serial")
@Deprecated
public class SearchFormHandler implements Serializable,Cloneable{
	private static transient Logger logger = Logger.getLogger(SearchFormHandler.class);
	public SearchFormHandler(){
		super();
	}
	public static final String SearchForm = "SearchForm";
	private HashMap<String,Object> search;		
	public HashMap<String, Object> getSearch() {
		return search;
	}
	public void setSearch(HashMap<String, Object> search) {
		this.search = search;
	}
	public Object get(String FIELD_ID){
		if(null == search){
			search = new HashMap<String,Object>();
		}
		return search.get(FIELD_ID);
	}
}
