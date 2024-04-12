package com.eaf.orig.ulo.pl.app.utility;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.shared.servlet.AjaxDisplayGenerateInf;
import com.eaf.orig.shared.util.OrigUtil;
import com.eaf.orig.shared.utility.ORIGUtility;
import com.eaf.orig.ulo.pl.app.dao.exception.PLOrigApplicationException;
import com.eaf.orig.ulo.pl.formcontrol.view.form.PLOrigFormHandler;
import com.eaf.orig.ulo.pl.model.app.PLApplicationDataM;
import com.eaf.orig.ulo.pl.model.app.PLPersonalInfoDataM;

public class PopupMandatory implements AjaxDisplayGenerateInf {

	Logger logger = Logger.getLogger(XrulesMandatory.class);
	@Override
	public String getDisplayObject(HttpServletRequest request) throws PLOrigApplicationException {
		logger.info("[Mandatory Application]");

		PLOrigFormHandler origForm = (PLOrigFormHandler) request.getSession().getAttribute(PLOrigFormHandler.PLORIGForm);
		
		try{	
			
			PLApplicationDataM applicationM = PLOrigFormHandler.getPLApplicationDataM(request);
			
			ORIGMandatoryErrorTool errorUtil = ORIGMandatoryErrorTool.getInstance();
			
			PLPersonalInfoDataM personalM = applicationM.getPLPersonalInfoDataM(OrigConstant.PERSONAL_TYPE_APPLICANT);
			
			String submitType = request.getParameter("submitType");
			String customer_type = personalM.getCustomerType();
	//		String ManField = request.getParameter("ManField");	
			String formID =  request.getParameter("formID");
			String mandatoryType = request.getParameter("mandatoryType");
	
			boolean isError = true;
							
	/**Create New Error Fields #SeptemWi*/
			origForm.DestoryErrorPopupFields();
			
	        if(ORIGUtility.isEmptyString(customer_type)) 
	        		customer_type = OrigConstant.CUSTOMER_TYPE_INDIVIDUAL;
	        
			logger.info("formID >> "+formID);	
			logger.info("Mandatory Type >> "+mandatoryType);
			logger.info("Customer Type >> "+customer_type);
	        
			if(OrigUtil.isEmptyString(mandatoryType))
					mandatoryType = String.valueOf(OrigConstant.Mandatory.MANDATORY_TYPE_SUMMIT);
			
			origForm.setSaveType(submitType);
			origForm.setMandatoryType(Integer.parseInt(mandatoryType));
	
			isError = errorUtil.getMandateErrorBySubForm(request, customer_type, formID);
	
			logger.debug("Has Error Field = "+isError);
			
		}catch(Exception e){
			logger.fatal("Exception ",e);
		}
		return origForm.GetErrorPopupFields();
	}
}
