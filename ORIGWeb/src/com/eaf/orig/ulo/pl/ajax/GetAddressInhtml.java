package com.eaf.orig.ulo.pl.ajax;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.orig.shared.servlet.AjaxDisplayGenerateInf;
import com.eaf.orig.ulo.pl.app.dao.exception.PLOrigApplicationException;
import com.eaf.orig.ulo.pl.formcontrol.view.form.PLOrigFormHandler;
import com.eaf.orig.ulo.pl.model.app.PLAddressDataM;
import com.eaf.orig.ulo.pl.model.app.PLApplicationDataM;
import com.eaf.orig.ulo.pl.model.app.PLPersonalInfoDataM;

public class GetAddressInhtml implements AjaxDisplayGenerateInf {
	Logger log = Logger.getLogger(GetAddressInhtml.class);
	@Override
	public String getDisplayObject(HttpServletRequest request) throws PLOrigApplicationException{

		PLOrigFormHandler origForm = (PLOrigFormHandler) request.getSession().getAttribute(PLOrigFormHandler.PLORIGForm);
		PLApplicationDataM applicationM = origForm.getAppForm();
		PLPersonalInfoDataM personalM = applicationM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);

		PLAddressDataM addressDataM = personalM.getAddressDataM(request.getParameter("address_type"));
		
		String No = "01";
		String AddressType = addressDataM.getAddressType();
		String Address =request.getParameter("address_style");
		String Tel= request.getParameter("telephone1");
		String ext_Tel_1= request.getParameter("ext_tel_1");
		String currentAddressStatus = request.getParameter("address_status");
		String notice = request.getParameter("note");
		String xmlObj = "";
		
		String checkbox2="XXX";
		
		xmlObj=(checkbox2+"|"+No+"|"+AddressType+"|"+Address+"|"+Tel+"|"+ext_Tel_1+"|"+currentAddressStatus+"|"+notice);
		log.debug(">>>>>>>>>>>>>> XMLOBJ = "+xmlObj);
		
		return xmlObj;
	}

}
