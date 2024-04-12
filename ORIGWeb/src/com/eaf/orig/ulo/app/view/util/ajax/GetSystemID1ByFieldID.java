package com.eaf.orig.ulo.app.view.util.ajax;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.model.ResponseData;
import com.eaf.core.ulo.common.properties.CacheControl;
import com.eaf.j2ee.pattern.control.AjaxInf;
import com.eaf.j2ee.pattern.control.ResponseDataController;

public class GetSystemID1ByFieldID implements AjaxInf {
	private static transient Logger logger = Logger.getLogger(GetSystemID1ByFieldID.class);	
	@Override
	public ResponseData processAction(HttpServletRequest request){
		ResponseDataController responseData = new ResponseDataController(request,ResponseData.FunctionId.GET_SYSTEMID1_BY_FIELD_ID);
		try{
			String choiceNo = request.getParameter("choiceNo");
			String fieldId = request.getParameter("fieldID");
			logger.info("GetSystemID1ByFieldID  choiceNo:"+choiceNo);
			String returnVal = CacheControl.getName(fieldId, choiceNo, "SYSTEM_ID1");
			logger.info("returnVal :"+returnVal);
			return responseData.success(returnVal);
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			return responseData.error(e);
		}
	}
}
