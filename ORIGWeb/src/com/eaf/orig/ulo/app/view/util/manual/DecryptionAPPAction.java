package com.eaf.orig.ulo.app.view.util.manual;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.model.ResponseData;
import com.eaf.core.ulo.flp.security.encryptor.FLPEncryptorFactory;
import com.eaf.j2ee.pattern.control.AjaxInf;
import com.eaf.j2ee.pattern.control.ResponseDataController;

public class DecryptionAPPAction implements AjaxInf {
	private static transient Logger logger = Logger.getLogger(DecryptionAPPAction.class);	
	@Override
	public ResponseData processAction(HttpServletRequest request){
		ResponseDataController responseData = new ResponseDataController(request,ResponseData.FunctionId.APP_DECRYPT);
		String data = "";
		 try {
			String VALUE = request.getParameter("APP_DECRYPTION");
			logger.debug("VALUE >> "+VALUE);
			data = FLPEncryptorFactory.getFLPEncryptor().decrypt(VALUE);
			return responseData.success(data);
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			return responseData.error(e);
		}
	}
}
