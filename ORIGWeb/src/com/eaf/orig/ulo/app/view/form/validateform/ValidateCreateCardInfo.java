package com.eaf.orig.ulo.app.view.form.validateform;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.FormatUtil;
import com.eaf.core.ulo.common.display.ValidateFormHelper;
import com.eaf.core.ulo.common.display.ValidateFormInf;
import com.eaf.core.ulo.common.message.MessageErrorUtil;
import com.eaf.core.ulo.common.message.MessageUtil;
import com.eaf.core.ulo.common.properties.ListBoxControl;
import com.eaf.core.ulo.common.properties.SystemConfig;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.FormErrorUtil;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.ulo.formcontrol.view.form.EntityFormHandler;
import com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler;
import com.eaf.orig.ulo.model.app.ApplicationDataM;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.PersonalApplicationInfoDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;

public class ValidateCreateCardInfo extends ValidateFormHelper implements ValidateFormInf {
	private static transient Logger logger = Logger.getLogger(ValidateCreateCardInfo.class);
	String PRODUCT_CRADIT_CARD = SystemConstant.getConstant("PRODUCT_CRADIT_CARD");
	String PRODUCT_K_EXPRESS_CASH = SystemConstant.getConstant("PRODUCT_K_EXPRESS_CASH");
	String PRODUCT_K_PERSONAL_LOAN = SystemConstant.getConstant("PRODUCT_K_PERSONAL_LOAN");
	String PERSONAL_TYPE_APPLICANT = SystemConstant.getConstant("PERSONAL_TYPE_APPLICANT");
	String PERSONAL_TYPE_SUPPLEMENTARY = SystemConstant.getConstant("PERSONAL_TYPE_SUPPLEMENTARY");
	String APPLICATION_CARD_TYPE_BORROWER = SystemConstant.getConstant("APPLICATION_CARD_TYPE_BORROWER");
	
	@Override
	public String validateFlag(HttpServletRequest request, Object objectForm) {
		return ValidateFormInf.VALIDATE_YES;
	}

	@Override
	public HashMap<String,Object> validateForm(HttpServletRequest request, Object objectForm) {
		FormErrorUtil formError = new FormErrorUtil();
		String PRODUCTS_CARD_TYPE = request.getParameter("PRODUCTS_CARD_TYPE");
		String subformId = request.getParameter("subformId");
		String CARD_TYPE = request.getParameter("CARD_TYPE");
		String CARD_LEVEL = request.getParameter("CARD_LEVEL");
		logger.debug("PRODUCTS_CARD_TYPE >> " + PRODUCTS_CARD_TYPE);
		logger.debug("CARD_TYPE >> " + CARD_TYPE);
		logger.debug("CARD_LEVEL >> " + CARD_LEVEL);
		logger.debug("subformId >> " + subformId);
		
		ORIGFormHandler ORIGForm = (ORIGFormHandler) request.getSession().getAttribute("ORIGForm");
		ApplicationGroupDataM applicationGroup = ORIGForm.getObjectForm();
		
		logger.debug("All personal info size : " + applicationGroup.getPersonalInfos().size());
		PersonalInfoDataM personalInfo = applicationGroup.getPersonalInfo(PERSONAL_TYPE_APPLICANT);
		if (null == personalInfo) {
			personalInfo = applicationGroup.getPersonalInfo(PERSONAL_TYPE_SUPPLEMENTARY);
		}
		logger.debug("Personal Info " + personalInfo);
		if (null == personalInfo) {
			formError.error(MessageErrorUtil.getText(request, "ERROR_MUST_HAVE_APPLICANT"));
		}
		formError.mandatoryElement("PRODUCTS_CARD_TYPE", "PRODUCTS_CARD_INFORMATION_TYPE", PRODUCTS_CARD_TYPE, request);
		if (!Util.empty(PRODUCTS_CARD_TYPE)) {
			String msg = validateMaxApplication(request, PRODUCTS_CARD_TYPE, subformId);
			if (!Util.empty(msg)) {
				formError.error(msg);
			}else{
				String FIELD_ID_PRODUCT_TYPE = SystemConstant.getConstant("FIELD_ID_PRODUCT_TYPE");
				String PRODUCT_CODE = ListBoxControl.getName(FIELD_ID_PRODUCT_TYPE, "CHOICE_NO", PRODUCTS_CARD_TYPE, "SYSTEM_ID1");
				if (PRODUCT_K_EXPRESS_CASH.equals(PRODUCT_CODE)) {
					formError.mandatoryElement("CARD_TYPE", "CARD_TYPE", CARD_TYPE, request);
				} else if (PRODUCT_CRADIT_CARD.equals(PRODUCT_CODE)) {
					formError.mandatoryElement("CARD_TYPE", "CARD_TYPE", CARD_TYPE, request);
					formError.mandatoryElement("CARD_LEVEL", "CARD_LEVEL", CARD_LEVEL, request);
				}
			}
		}
		return formError.getFormError();
	}

	public String validateMaxApplication(HttpServletRequest request, String PRODUCTS_CARD_TYPE, String formId) {
		String MSG = MessageUtil.getText(request, "MSG_MAX_CARD");
		boolean flag = false;
		
		// Give Product Type from SYSTEM_ID1 instead
		
		String FIELD_ID_PRODUCT_TYPE = SystemConstant.getConstant("FIELD_ID_PRODUCT_TYPE");
		PRODUCTS_CARD_TYPE = ListBoxControl.getName(FIELD_ID_PRODUCT_TYPE,"CHOICE_NO",PRODUCTS_CARD_TYPE,"SYSTEM_ID1");
		
		logger.debug("PRODUCTS_CARD_TYPE>>"+PRODUCTS_CARD_TYPE);
//		String APPLICATION_CARD_TYPE_BORROWER_LIMIT = SystemConstant.getConstant("APPLICATION_CARD_TYPE_BORROWER_LIMIT");// cc
//		String APPLICATION_CARD_TYPE_SUPPLEMANTARY_LIMIT = SystemConstant.getConstant("APPLICATION_CARD_TYPE_SUPPLEMANTARY_LIMIT");
//		String APPLICATION_CARD_TYPE_ALL = SystemConstant.getConstant("APPLICATION_CARD_TYPE_ALL");
//		String PERSONAL_TYPE_SUPPLEMENTARY = SystemConstant.getConstant("PERSONAL_TYPE_SUPPLEMENTARY");
//		String PERSONAL_RELATION_APPLICATION_LEVEL = SystemConstant.getConstant("PERSONAL_RELATION_APPLICATION_LEVEL");
//		int MAX_PRODUCT_CC = Integer.parseInt(SystemConfig.getGeneralParam("MAX_PRODUCT_CC"));
		int MAX_PRODUCT_CC_SUP = Integer.parseInt(SystemConfig.getGeneralParam("MAX_PRODUCT_CC_SUP"));

		if ("SUP_PRODUCT_FORM".equals(formId)) {
			logger.debug("CREATE_CC_SUPCARD_INFO  .... ");
			EntityFormHandler entityForm = (EntityFormHandler) request.getSession().getAttribute("EntityForm");
			PersonalApplicationInfoDataM personalAppInfo = (PersonalApplicationInfoDataM) entityForm.getObjectForm();
//			if (null == personalAppInfo) {
//				logger.fatal("PersonalApplicationInfoDataM is NULL");
//				// TODO: What should I do next?
//			}
			
			ArrayList<ApplicationDataM> supApplications = personalAppInfo.getApplications();
			logger.debug("supApplications size >> " + supApplications.size());
			if (supApplications.size() < MAX_PRODUCT_CC_SUP) {
				flag = true;
			}
		} else {
			ORIGFormHandler ORIGForm = (ORIGFormHandler) request.getSession().getAttribute(ORIGFormHandler.ORIGForm);
			ApplicationGroupDataM applicationGroup = ORIGForm.getObjectForm();
			ArrayList<ApplicationDataM> applications  = new ArrayList<ApplicationDataM>();
			if(PRODUCT_K_PERSONAL_LOAN.equals(PRODUCTS_CARD_TYPE)){
				applications = applicationGroup.filterApplicationLifeCycle(PRODUCTS_CARD_TYPE);
			}else{
				applications = applicationGroup.filterApplicationLifeCycle(PRODUCTS_CARD_TYPE, APPLICATION_CARD_TYPE_BORROWER);
			}
			String conditionGeneralParam = SystemConfig.getGeneralParam("MAX_PRODUCT_" + PRODUCTS_CARD_TYPE);
			logger.debug("General Param [" + "MAX_PRODUCT_" + PRODUCTS_CARD_TYPE + "] : " + conditionGeneralParam);
			logger.debug("App size : " + applications.size());
			if ("".equals(conditionGeneralParam)) {
				MSG = "General Param 'MAX_PRODUCT_" + PRODUCTS_CARD_TYPE + "' is not found !! ";
				logger.error(MSG);
				return MSG;
			}
			if (applications.size() < FormatUtil.getInt(conditionGeneralParam)) {
				flag = true;
			}
		}

		if (flag) {
			return null;
		}
		logger.debug("MSG >>>> " + MSG);
		return MSG;
		
	}

}
