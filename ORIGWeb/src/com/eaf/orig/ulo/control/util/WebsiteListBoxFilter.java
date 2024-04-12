package com.eaf.orig.ulo.control.util;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.ListBoxFilterInf;
import com.eaf.core.ulo.common.engine.SQLQueryEngine;
import com.eaf.core.ulo.common.properties.CacheControl;
import com.eaf.core.ulo.common.properties.ListBoxControl;
import com.eaf.core.ulo.common.properties.SystemConstant;

public class WebsiteListBoxFilter implements ListBoxFilterInf {
	private static transient Logger logger = Logger.getLogger(WebsiteListBoxFilter.class);
	@Override
	public ArrayList<HashMap<String, Object>> filter(String configId,String cacheId, String typeId, HttpServletRequest request) {
		String WEBSITE_VERIFICATION_CACHE = SystemConstant.getConstant("WEBSITE_VERIFICATION_CACHE");
		String WEBSITE_CACHE = SystemConstant.getConstant("WEBSITE_CACHE");
		logger.debug("typeId>>"+typeId);

		ArrayList<HashMap<String,Object>> List = ListBoxControl.getListBox(cacheId,"ACTIVE");
		if(ListBoxControl.Type.ALL.equals(typeId)){
			return List;
		}
		String webID = CacheControl.getName(WEBSITE_CACHE, typeId, "WEBSITE_ID");
		ArrayList<HashMap<String,Object>> vVerificationCode = new ArrayList<>();
		if(null != List){
			for (HashMap<String, Object> verificationM : List){
				String verificationCode = SQLQueryEngine.display(verificationM, "CODE");
				String webVerifictionID = CacheControl.getName(WEBSITE_VERIFICATION_CACHE,webID+"_"+verificationCode);
				if(webVerifictionID != null && !webVerifictionID.isEmpty()) {
					vVerificationCode.add(verificationM);
				}
			}
		}
		return vVerificationCode;
	}
	@Override
	public void setFilterProperties(String configId, String cacheId, String typeId,HttpServletRequest request) {
		
	}
	@Override
	public void init(Object objectForm) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void setElementDisplayMode(String displayMode) {
		
	}
}
