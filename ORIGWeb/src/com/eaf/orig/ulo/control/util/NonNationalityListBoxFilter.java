package com.eaf.orig.ulo.control.util;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.HtmlUtil;
import com.eaf.core.ulo.common.display.ListBoxFilterInf;
import com.eaf.core.ulo.common.engine.SQLQueryEngine;
import com.eaf.core.ulo.common.properties.CacheControl;
import com.eaf.core.ulo.common.properties.FormControl;
import com.eaf.core.ulo.common.properties.ListBoxControl;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.ulo.app.view.form.subform.product.manual.CardInfoUtil;
import com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.CardDataM;
import com.eaf.ulo.cache.constant.CacheConstant;
import com.eaf.ulo.cache.controller.CacheController;
import com.eaf.ulo.cache.inf.CachePropertiesInf;
import com.eaf.ulo.cache.util.CacheUtil;

public class NonNationalityListBoxFilter implements ListBoxFilterInf{
	private static transient Logger logger = Logger.getLogger(NonNationalityListBoxFilter.class);	
	String CACHE_CARD_PRODUCT_INFO = SystemConstant.getConstant("CACHE_CARD_PRODUCT_INFO");
	String CACHE_NAME_TEMPLATE_CARDTYPE = SystemConstant.getConstant("CACHE_NAME_TEMPLATE_CARDTYPE");
	String CACHE_BUSINESS_CLASS = SystemConstant.getConstant("CACHE_BUSINESS_CLASS");
	String CACHE_NATIONALITY = SystemConstant.getConstant("CACHE_NATIONALITY");
	private String displayMode = HtmlUtil.EDIT;
	@Override
	public ArrayList<HashMap<String, Object>> filter(String configId,String cacheId, String typeId, HttpServletRequest request) {		
		logger.debug("filter :: action");
		 // see in ORIG_METABCACHE Table attr is CACHENAME = Nationality 
		ArrayList<HashMap<String,Object>> List = ListBoxControl.getListBox(cacheId,"ACTIVE");
		ArrayList<HashMap<String, Object>> MYANMAR = CacheControl.search(CACHE_NATIONALITY, "CODE", "MM");
		ArrayList<HashMap<String, Object>> CAMBODIA = CacheControl.search(CACHE_NATIONALITY, "CODE", "KH");
		ArrayList<HashMap<String, Object>> LAO = CacheControl.search(CACHE_NATIONALITY, "CODE", "LA");
		ArrayList<HashMap<String, Object>> NOT_HAVE_NATION = CacheControl.search(CACHE_NATIONALITY, "CODE", "XX");
		// map in list 
		ArrayList<HashMap<String, Object>> nationality = new ArrayList<HashMap<String, Object>>();
		
		for(HashMap<String, Object> map : MYANMAR ){
			nationality.add(map);
		}
		for(HashMap<String, Object> map : CAMBODIA ){
			nationality.add(map);
		}
		for(HashMap<String, Object> map : LAO ){
			nationality.add(map);
		}
		
		return nationality ;
	}

	@Override
	public void setFilterProperties(String configId, String cacheId,String typeId, HttpServletRequest request) {
		
	}
	

	@Override
	public void init(Object objectForm) {
		
	}
	@Override
	public void setElementDisplayMode(String displayMode) {
		this.displayMode = displayMode;
	}
}
