package com.eaf.orig.ulo.app.view.util.ajax;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.model.ResponseData;
import com.eaf.j2ee.pattern.control.AjaxInf;
import com.eaf.j2ee.pattern.control.ResponseDataController;
import com.eaf.j2ee.pattern.util.JSONUtil;
import com.eaf.orig.ulo.pl.app.utility.ServiceReqRespTool;
 
public class GetQr2No implements AjaxInf  {
	private static transient Logger logger = Logger.getLogger(GetQr2No.class);	
	@Override
	public ResponseData processAction(HttpServletRequest request){
		ResponseDataController responseData = new ResponseDataController(request,ResponseData.FunctionId.GET_QR2);		
		try{
			ServiceReqRespTool serviceReqResp  = new ServiceReqRespTool();
			String QR2_RUNNING_NO = serviceReqResp.GenerateReqResNo();
			JSONUtil json = new JSONUtil();	
			json.put("QR2_RUNNING_NO",QR2_RUNNING_NO);
			return responseData.success(json.getJSON());
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			return responseData.error(e);
		}
	}
}
