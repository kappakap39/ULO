package com.eaf.orig.ulo.pl.ajax;

import java.util.Vector;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.shared.servlet.AjaxDisplayGenerateInf;
import com.eaf.orig.shared.utility.ORIGFormUtil;
import com.eaf.orig.ulo.pl.app.dao.exception.PLOrigApplicationException;
import com.eaf.orig.ulo.pl.app.utility.HTMLRenderUtil;
import com.eaf.orig.ulo.pl.app.utility.JsonObjectUtil;
import com.eaf.orig.ulo.pl.app.view.webaction.AddressUtil;
import com.eaf.orig.ulo.pl.formcontrol.view.form.PLOrigFormHandler;
import com.eaf.orig.ulo.pl.model.app.PLApplicationDataM;
import com.eaf.orig.ulo.pl.model.app.PLPersonalInfoDataM;

public class LoadVerifyCustomer implements AjaxDisplayGenerateInf{
	       
	Logger logger = Logger.getLogger(this.getClass());

	@Override
	public String getDisplayObject(HttpServletRequest request)throws PLOrigApplicationException {
		
		String personal = request.getParameter("vercus-personal-type");
		
		logger.debug("[getDisplayObject]..Personal Type "+personal);
		
		PLOrigFormHandler  formHandler = (PLOrigFormHandler) request.getSession().getAttribute(PLOrigFormHandler.PLORIGForm);	
		if(null == formHandler) formHandler = new PLOrigFormHandler();
		PLApplicationDataM applicationM = formHandler.getAppForm();
		if(null == applicationM) applicationM = new PLApplicationDataM();
		
		String formID = formHandler.getFormID();
		String searchType = (String) request.getSession().getAttribute("searchType");		
		UserDetailM userM =	(UserDetailM) request.getSession().getAttribute("ORIGUser");
		
		ORIGFormUtil formUtil = new ORIGFormUtil();		
		String displayMode = formUtil.getDisplayMode("VERIFICATION_SUBFORM", userM.getRoles(), applicationM.getJobState(), formID, searchType);				
		logger.debug("DisplayMode >> "+displayMode);
		
		PLPersonalInfoDataM personalM = null;
		
		if(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT.equals(personal)){
			personalM = applicationM.getPLPersonalInfoDataM(personal);
		}		
		if(null == personalM) personalM = new PLPersonalInfoDataM();
		
		AddressUtil addressUtil = new AddressUtil();
		
		Vector addressTypeVect = addressUtil.getAddressTypeCacheByPlAddressM(personalM.getAddressVect());
		
		JsonObjectUtil jObjectUtil = new JsonObjectUtil();		
		
		jObjectUtil.CreateJsonObject("div-vercus-name", HTMLRenderUtil.displayHTML(personalM.getThaiFirstName())+" "+HTMLRenderUtil.displayHTML(personalM.getThaiLastName()));
		
		jObjectUtil.CreateJsonObject("div-vercus-address-type", HTMLRenderUtil.displaySelectTagScriptAction_ORIG(addressTypeVect,"","vercus-address-type",displayMode," onchange=\"javascript:loadTelephone();\" "));
		
		return jObjectUtil.returnJson();
	}
}
