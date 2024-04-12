package com.eaf.orig.ulo.app.view.util.manual;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.model.ResponseData;
import com.eaf.core.ulo.security.encryptor.Encryptor;
import com.eaf.core.ulo.security.encryptor.EncryptorFactory;
import com.eaf.j2ee.pattern.control.AjaxInf;
import com.eaf.j2ee.pattern.control.ResponseDataController;

public class DecryptionDIHAction implements AjaxInf {
	private static transient Logger logger = Logger.getLogger(DecryptionDIHAction.class);	
	@Override
	public ResponseData processAction(HttpServletRequest request){
		ResponseDataController responseData = new ResponseDataController(request,ResponseData.FunctionId.DIH_DECRYPT);
		String data = "";
		 try {
			String VALUE = request.getParameter("DIH_DECRYPTION");
			logger.debug("VALUE >> "+VALUE);
			Encryptor enc = EncryptorFactory.getDIHEncryptor();
			data = enc.decrypt(VALUE);
			return responseData.success(data);
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			return responseData.error(e);
		}
	}
}
