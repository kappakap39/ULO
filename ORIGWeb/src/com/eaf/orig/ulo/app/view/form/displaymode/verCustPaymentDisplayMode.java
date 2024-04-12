package com.eaf.orig.ulo.app.view.form.displaymode;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.FormDisplayModeHelper;
import com.eaf.core.ulo.common.display.HtmlUtil;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.PaymentMethodDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;

public class verCustPaymentDisplayMode extends FormDisplayModeHelper{
	private static transient Logger logger = Logger.getLogger(verCustPaymentDisplayMode.class);
	String PERSONAL_TYPE = SystemConstant.getConstant("PERSONAL_TYPE_APPLICANT");	
	String PRODUCT_K_EXPRESS_CASH = SystemConstant.getConstant("PRODUCT_K_EXPRESS_CASH");
	String PAYMENT_METHOD_DEPOSIT_ACCOUNT = SystemConstant.getConstant("PAYMENT_METHOD_DEPOSIT_ACCOUNT");
	
	@Override
	public String displayMode(Object objectElement, HttpServletRequest request) {
		String subformId = request.getParameter("subFormId");
		logger.debug(subformId+" displayMode");
		logger.debug("verCustPaymentDisplayMode displayMode>>");
		ApplicationGroupDataM applicationGroup = ORIGFormHandler.getObjectForm(request);
		if(null == applicationGroup){
			applicationGroup = new ApplicationGroupDataM();
		}

		String displayMode = HtmlUtil.VIEW;
		PersonalInfoDataM personalInfo = applicationGroup.getPersonalInfo(PERSONAL_TYPE);
		String personalId = personalInfo.getPersonalId();
		PaymentMethodDataM paymentMethod = applicationGroup.getPaymentMethodLifeCycleByPersonalId(personalId,PRODUCT_K_EXPRESS_CASH); 
		logger.debug("verCustPaymentDisplayMode paymentMethod>> "+paymentMethod);		
		if(!Util.empty(paymentMethod)){
			logger.debug("verCustPaymentDisplayMode paymentMethod>> "+paymentMethod.getPaymentMethod());	
			if(PAYMENT_METHOD_DEPOSIT_ACCOUNT.equals(paymentMethod.getPaymentMethod())) {
				displayMode = HtmlUtil.EDIT;
			}
			
		}
		return displayMode;
	}
}
