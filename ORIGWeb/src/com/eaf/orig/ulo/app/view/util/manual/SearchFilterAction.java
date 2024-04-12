package com.eaf.orig.ulo.app.view.util.manual;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.SearchFilter;
import com.eaf.core.ulo.common.display.SearchFilterInf;
import com.eaf.core.ulo.common.model.ResponseData;
import com.eaf.core.ulo.common.performance.TraceController;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.j2ee.pattern.control.AjaxInf;
import com.eaf.j2ee.pattern.control.ResponseDataController;

public class SearchFilterAction implements AjaxInf{
	private static transient Logger logger = Logger.getLogger(SearchFilterAction.class);
	String FLAG_YES = SystemConstant.getConstant("FLAG_YES");	
	@Override
	public ResponseData processAction(HttpServletRequest request){
		ResponseDataController responseData = new ResponseDataController(request,ResponseData.FunctionId.SEARCH_FILTER_ACTION);
		String jsonOption = "";
		String transactionId = (String)request.getSession().getAttribute("transactionId");
		TraceController trace = new TraceController(this.getClass().getName(),transactionId);
		try{
			String searchConfigId = request.getParameter("searchConfigId");
			String searchValueFlag = request.getParameter("searchValueFlag");
			trace.create(searchConfigId);
			String className = SearchFilter.get(searchConfigId);
			if(!Util.empty(className)){
				SearchFilterInf SearchFilterInf = (SearchFilterInf)Class.forName(className).newInstance();
				if(null != SearchFilterInf){
					if(!FLAG_YES.equals(searchValueFlag)){
						jsonOption = SearchFilterInf.searchResult(request);
					}else{
						String searchValue = request.getParameter("searchValue");
						logger.debug("searchValue >>"+searchValue);
						jsonOption = SearchFilterInf.getValue(searchValue).toString();
					}
				}
			}
			trace.end(searchConfigId);
			trace.trace();
			return responseData.success(jsonOption);
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			return responseData.error(e);
		}
	}
}
