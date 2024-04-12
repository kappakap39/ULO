package com.eaf.orig.ulo.app.view.form.process;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.ProcessActionHelper;
import com.eaf.core.ulo.common.model.ProcessActionResponse;
import com.eaf.core.ulo.common.util.FormErrorUtil;
import com.eaf.im.rest.common.model.ServiceResponse;

public class ProcessActionVT extends ProcessActionHelper {
	private static transient Logger logger = Logger.getLogger(ProcessActionVT.class);
	@Override
	public HashMap<String, Object> validateAction(HttpServletRequest request,Object objectForm) {
		FormErrorUtil formError = new FormErrorUtil();
		return formError.getFormError();
	}
	@Override
	public Object preProcessAction() {
		return null;
	}
	@Override
	public Object processAction() {
	 	ProcessActionResponse processActionResponse = new ProcessActionResponse();
		try{
			processActionResponse.setResultCode(ServiceResponse.Status.SUCCESS);
		}catch(Exception e){
			logger.fatal("ERROR",e);
			processActionResponse.setResultCode(ServiceResponse.Status.SYSTEM_EXCEPTION);
			processActionResponse.setResultDesc(e.getLocalizedMessage());
		}
		return processActionResponse;
	}
	@Override
	public Object postProcessAction() {
		return null;
	}
}
