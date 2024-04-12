package com.eaf.orig.ulo.app.form.search;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.engine.SearchFormHandler;
import com.eaf.orig.ulo.app.view.webaction.InboxWebAction;

@SuppressWarnings("serial")
public class InboxSearchFormHandler extends SearchFormHandler implements Serializable{
	private static transient Logger logger = Logger.getLogger(InboxWebAction.class);
	public InboxSearchFormHandler(HttpServletRequest request){
		super(request);
	}
	@Override
	public void postSearchResult(ArrayList<HashMap<String, Object>> searchResult) {
//		logger.debug("do postSearchResult()..");
//		try{
//			if(!Util.empty(searchResult)){
//				for(HashMap<String, Object> Row : searchResult){
//					String applicationGroupId = (String)Row.get("APPLICATION_GROUP_ID");
//					logger.debug("applicationGroupId : " + applicationGroupId);
//					if (!Util.empty(applicationGroupId)) {
//						int lifeCycle = ORIGDAOFactory.getApplicationGroupDAO().getMaxLifeCycle(applicationGroupId);
//						Row.put("MAX_LIFE_CYCLE", lifeCycle);
//					}
//				}
//			}
//		}catch(Exception e){
//			logger.fatal("ERROR",e);
//		}
	}
}
