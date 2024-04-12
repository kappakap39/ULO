package com.eaf.orig.ulo.app.view.util.manual;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.model.ResponseData;
import com.eaf.j2ee.pattern.control.AjaxInf;
import com.eaf.j2ee.pattern.control.ResponseDataController;
import com.eaf.ulo.cache.controller.RefreshCacheController;

public class RefreshCacheUser implements AjaxInf{
	private static transient Logger logger = Logger.getLogger(RefreshOrigCache.class);	
	@Override
	public ResponseData processAction(HttpServletRequest request){
		ResponseDataController responseData = new ResponseDataController(request,ResponseData.FunctionId.REFRESH_CACHE);
		String data = null;
		try{
	//		CacheController.refreshCacheAll();
			String serverName = request.getParameter("serverName");
			logger.debug("serverName : "+serverName);
			RefreshCacheController.execute("User");
			data = "Success Refresh Orig Cache..";
			return responseData.success(data);
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			return responseData.error(e);
		}
	}
}
