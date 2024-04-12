package com.eaf.orig.ulo.app.view.form.subform.normal;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.ElementHelper;
import com.eaf.core.ulo.common.display.ElementInf;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.FormErrorUtil;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.ulo.constant.MConstant;
import com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler;
import com.eaf.orig.ulo.model.app.AddressDataM;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;

public class VatRegistrationElement  extends ElementHelper{
	private static transient Logger logger = Logger.getLogger(VatRegistrationElement.class);
	String ADDRESS_TYPE_VAT = SystemConstant.getConstant("ADDRESS_TYPE_VAT");
	@Override
	public String getElement(HttpServletRequest request, Object objectElement) {
		return "/orig/ulo/subform/normal/VatRegistrationElementSubForm.jsp";
	}

	@Override
	public String getDisplayType() {
		return ElementInf.DISPLAY_JSP;
	}
	
	@Override
	public String processElement(HttpServletRequest request,Object objectElement) {
		String suffixV = ADDRESS_TYPE_VAT;
		try {
			AddressDataM  addressV = (AddressDataM)objectElement;		
			if(Util.empty(addressV)){
				addressV = new AddressDataM();
			}
			String VAT_REGIST_FLAG = request.getParameter("VAT_REGIST_FLAG_"+suffixV);
			String ESTABLISHMENT_ADDR_FLAG = request.getParameter("ESTABLISHMENT_ADDR_FLAG_"+suffixV);
			String ESTABLISHMENT_ADDR_FLAG_txt = request.getParameter("ESTABLISHMENT_ADDR_FLAG_txt_"+suffixV);
			String ADDRESS1_V = request.getParameter("ADDRESS1_"+suffixV);
			String PROVINCE_V = request.getParameter("PROVINCE_"+suffixV);
			String ZIPCODE_V = request.getParameter("ZIPCODE_"+suffixV);
			String VAT_CODE_V = request.getParameter("VAT_CODE_"+suffixV);
			
			logger.debug("VAT_REGIST_FLAG >>"+VAT_REGIST_FLAG);
			logger.debug("ESTABLISHMENT_ADDR_FLAG >>"+ESTABLISHMENT_ADDR_FLAG);
			logger.debug("ESTABLISHMENT_ADDR_FLAG_txt >>"+ESTABLISHMENT_ADDR_FLAG_txt);
			logger.debug("V_ADDRESS1 >>"+ADDRESS1_V);
			logger.debug("V_PROVINCE >>"+PROVINCE_V);
			logger.debug("V_ZIPCODE >>"+ZIPCODE_V);
			logger.debug("VAT_CODE_V >>"+VAT_CODE_V);
			
			addressV.setVatRegistration(VAT_REGIST_FLAG);
			addressV.setBranchType(ESTABLISHMENT_ADDR_FLAG);
			addressV.setBranchName(ESTABLISHMENT_ADDR_FLAG_txt);
			addressV.setAddress1(ADDRESS1_V);
			addressV.setProvinceDesc(PROVINCE_V);
			addressV.setZipcode(ZIPCODE_V);	
			addressV.setVatCode(VAT_CODE_V);
		} catch (Exception e) {
			logger.fatal("ERROR",e);
		}
		 
		return null;
	}
	
	@Override
	public HashMap<String,Object> validateElement(HttpServletRequest request, Object objectElement) {	
		String subformId ="NORMAL_ADDRESS_SUBFORM_3";
		logger.debug("subformId >> "+subformId);
		FormErrorUtil formError = new FormErrorUtil();
		try {
			AddressDataM  addressV = (AddressDataM)objectElement;	
			formError.mandatory(subformId,"VAT_REGIST_FLAG",addressV.getVatRegistration(),request);
//			formError.mandatory(subformId,"ESTABLISHMENT_ADDR_FLAG",addressV.getes,request);
			formError.mandatory(subformId,"ADDRESS1",addressV.getAddress1(),request);
			formError.mandatory(subformId,"PROVINCE",addressV.getProvinceDesc(),request);
			formError.mandatory(subformId,"ZIPCODE",addressV.getZipcode(),request);
//			formError.mandatory(subformId,"VAT_CODE",addressV.getVat,request);
		} catch (Exception e) {
			logger.fatal("ERROR",e);
		}
		return formError.getFormError();
	}
	@Override
	public String renderElementFlag(HttpServletRequest request,Object objectElement) {
		String APPLICATION_TEMPLATE_THAIBEV = SystemConstant.getConstant("APPLICATION_TEMPLATE_THAIBEV");		
		ApplicationGroupDataM applicationGroup = ORIGFormHandler.getObjectForm(request);			
		logger.debug(">>>APPLICATION_TEMPLATE>>>"+applicationGroup.getApplicationTemplate());
		if(APPLICATION_TEMPLATE_THAIBEV.equals(applicationGroup.getApplicationTemplate())){
			return MConstant.FLAG_Y;
		}
		 return MConstant.FLAG_N;
	}
}
