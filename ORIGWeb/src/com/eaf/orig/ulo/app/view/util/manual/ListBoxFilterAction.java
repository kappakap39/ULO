package com.eaf.orig.ulo.app.view.util.manual;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

import com.eaf.core.ulo.common.display.ListBoxFilter;
import com.eaf.core.ulo.common.display.ListBoxFilterInf;
import com.eaf.core.ulo.common.engine.SQLQueryEngine;
import com.eaf.core.ulo.common.message.LabelUtil;
import com.eaf.core.ulo.common.model.ResponseData;
import com.eaf.core.ulo.common.performance.TraceController;
import com.eaf.core.ulo.common.properties.ListBoxControl;
import com.eaf.core.ulo.common.properties.ListboxJsonUtil;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.j2ee.pattern.control.AjaxInf;
import com.eaf.j2ee.pattern.control.ResponseDataController;

public class ListBoxFilterAction implements AjaxInf{
	private static transient Logger logger = Logger.getLogger(ListBoxFilterAction.class);	
	@Override
	public ResponseData processAction(HttpServletRequest request){
		ResponseDataController responseData = new ResponseDataController(request,ResponseData.FunctionId.LISTBOX_FILTER_ACTION);
		JSONArray jsonOption = null;
		String transactionId = (String)request.getSession().getAttribute("transactionId");
		TraceController trace = new TraceController(this.getClass().getName(),transactionId);
		try{
			String configId = request.getParameter("configId");
			String typeId = request.getParameter("typeId");		
			String cacheId = request.getParameter("cacheId");
			logger.debug("configId >> "+configId);
			logger.debug("cacheId >> "+cacheId);		
			String pleaseSelectMsg = LabelUtil.getText(request,"PLEASE_SELECT");
			String filter = ListBoxFilter.get(configId);
			if(Util.empty(typeId)){
				typeId = ListBoxControl.Type.ACTIVE;
			}
			typeId = ListBoxControl.getTypeListBox(typeId);
			logger.debug("filter >> "+filter);
			logger.debug("typeId >> "+typeId);
			trace.create(filter);
			if(Util.empty(filter)){
	//			if("DEFAULT".equals(typeId)){
	//				jsonOption = ListBoxControl.getJsonListBox(cacheId,"ACTIVE");
	//			}else{
	//				jsonOption = ListBoxControl.getJsonListBox(cacheId,typeId);
	//			}
				jsonOption = ListBoxControl.getJsonListBox(cacheId,typeId);
			}else{
				ListBoxFilterInf FilterInf  = null; 		        			 
			    try{			        		    	
			    	FilterInf = (ListBoxFilterInf)Class.forName(filter).newInstance();			        		    	
				}catch(Exception e){
					logger.fatal("ERROR ",e);
					return responseData.error(e);
				}
			    if(null != FilterInf){
			    	FilterInf.setFilterProperties(configId,cacheId,typeId,request);
			    	ArrayList<HashMap<String,Object>> listBoxs = FilterInf.filter(configId, cacheId, typeId, request);
			    	ListboxJsonUtil listboxJson = new ListboxJsonUtil();
			    	if(!Util.empty(listBoxs)){
			    		for (HashMap<String, Object> listBox : listBoxs) {
			    			String CODE = SQLQueryEngine.display(listBox,"CODE");
			    			String VALUE = SQLQueryEngine.display(listBox,"VALUE");
			    			listboxJson.put(CODE,VALUE);
						}
			    	}
			    	jsonOption = listboxJson.getJSONArray();
			    }
			}
			
			if(null == jsonOption){
				jsonOption = new JSONArray();
			}
			JSONObject json = new JSONObject();
				json.put("code","");
				json.put("value",pleaseSelectMsg);
			jsonOption.put(json);
			trace.end(filter);
			trace.trace();
			return responseData.success(jsonOption.toString());
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			return responseData.error(e);
		}
	}
}
