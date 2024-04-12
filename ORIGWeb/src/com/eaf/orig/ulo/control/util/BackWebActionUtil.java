package com.eaf.orig.ulo.control.util;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.FormatUtil;
import com.eaf.core.ulo.common.engine.SearchFormHandler;
import com.eaf.core.ulo.common.engine.SearchQueryEngine;
import com.eaf.core.ulo.common.util.SessionControl;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.ulo.model.control.FlowControlDataM;

public class BackWebActionUtil {
	private static transient Logger logger = Logger.getLogger(BackWebActionUtil.class);
	public static String processBackWebAction(HttpServletRequest request){
		String paramBackAction = "";
		FlowControlDataM flowControl = (FlowControlDataM)request.getSession().getAttribute(SessionControl.FlowControl);
		String actionType = flowControl.getActionType();
		logger.debug("actionType >> "+actionType);
		if("Enquiry".equalsIgnoreCase(actionType)){
			try{
				SearchQueryEngine engine = new SearchQueryEngine();
					engine.refresh(request,false);
			}catch(Exception e){
				logger.fatal("ERROR",e);
			}
			paramBackAction = getActionURL(flowControl.getStoreAction());
		}else if("Inbox".equalsIgnoreCase(actionType)){
			paramBackAction = "action=Inbox";
		}else{
			SearchFormHandler searchForm = (SearchFormHandler)request.getSession().getAttribute("SearchForm");
			if(null != searchForm){
				try{
					SearchQueryEngine engine = new SearchQueryEngine();
						engine.refresh(request);
				}catch(Exception e){
					logger.fatal("ERROR",e);
				}
			}
			paramBackAction = getActionURL(flowControl.getStoreAction());
		}
		logger.debug("paramBackAction >> "+paramBackAction);
		return paramBackAction;
	}
	private static String getActionURL(HashMap<String,String> storeAction){
		String URL = "";
		if(!Util.empty(storeAction) && storeAction.size()>1){
			String AMPERSAND = "";
			for(String KEY :storeAction.keySet()){
				URL += AMPERSAND+FormatUtil.display(KEY)+"="+FormatUtil.display(storeAction.get(KEY));
				AMPERSAND = "&";
			}
		}
		return URL;
	}
}
