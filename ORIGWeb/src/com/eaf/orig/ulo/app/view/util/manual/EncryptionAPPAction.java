package com.eaf.orig.ulo.app.view.util.manual;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.model.ResponseData;
import com.eaf.core.ulo.flp.security.encryptor.FLPEncryptorFactory;
import com.eaf.j2ee.pattern.control.AjaxInf;
import com.eaf.j2ee.pattern.control.ResponseDataController;

public class EncryptionAPPAction implements AjaxInf {
	private static transient Logger logger = Logger.getLogger(EncryptionAPPAction.class);	
	@Override
	public ResponseData processAction(HttpServletRequest request){
		ResponseDataController responseData = new ResponseDataController(request,ResponseData.FunctionId.APP_ENCRYPT);
		String data = null;
		 try {
			String VALUE = request.getParameter("APP_ENCRYPTION");
			logger.debug("VALUE >> "+VALUE);
			data = FLPEncryptorFactory.getFLPEncryptor().encrypt(VALUE);
			return responseData.success(data);
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			return responseData.error(e);
		}
	}
}