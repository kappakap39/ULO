package com.eaf.orig.ulo.app.view.util.manual;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.model.ResponseData;
import com.eaf.core.ulo.security.hash.Hasher;
import com.eaf.core.ulo.security.hash.HashingFactory;
import com.eaf.j2ee.pattern.control.AjaxInf;
import com.eaf.j2ee.pattern.control.ResponseDataController;

public class HasherAction implements AjaxInf {
	private static transient Logger logger = Logger.getLogger(HasherAction.class);	
	@Override
	public ResponseData processAction(HttpServletRequest request){
		ResponseDataController responseData = new ResponseDataController(request,ResponseData.FunctionId.HASH_CODE);
		String data = null;
		try {
			String VALUE = request.getParameter("HASHER");
			logger.debug("VALUE >> "+VALUE);
			Hasher hash = HashingFactory.getSHA256Hasher();
			data = hash.getHashCode(VALUE);
			return responseData.success(data);
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			return responseData.error(e);
		}
	}
}
