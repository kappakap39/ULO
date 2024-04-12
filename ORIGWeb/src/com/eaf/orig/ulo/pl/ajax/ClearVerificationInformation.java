package com.eaf.orig.ulo.pl.ajax;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.shared.servlet.AjaxDisplayGenerateInf;
import com.eaf.orig.ulo.pl.app.dao.exception.PLOrigApplicationException;
import com.eaf.orig.ulo.pl.app.utility.JsonObjectUtil;
import com.eaf.orig.ulo.pl.formcontrol.view.form.PLOrigFormHandler;
import com.eaf.orig.ulo.pl.model.app.PLApplicationDataM;

public class ClearVerificationInformation extends AjaxDisplayServlet implements	AjaxDisplayGenerateInf {
	Logger logger = Logger.getLogger(ClearDataInformation.class);	
	@Override
	public String getDisplayObject(HttpServletRequest request) throws PLOrigApplicationException {
		JsonObjectUtil jsonObj = new JsonObjectUtil();
		try{	
			UserDetailM userM = (UserDetailM)request.getSession().getAttribute("ORIGUser");	
			PLApplicationDataM appM = PLOrigFormHandler.getPLApplicationDataM(request);
			ClearDataInformation clearPLVer = new ClearDataInformation();
				clearPLVer.ClearPLXRulesVerificationResultDataM(jsonObj, appM, userM);			
		}catch(Exception e){
			logger.fatal("Error ",e);
		}		
		return jsonObj.returnJson();
	}
}
