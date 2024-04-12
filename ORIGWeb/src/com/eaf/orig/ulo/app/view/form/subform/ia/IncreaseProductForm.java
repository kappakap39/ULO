package com.eaf.orig.ulo.app.view.form.subform.ia;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.ElementInf;
import com.eaf.core.ulo.common.message.MessageErrorUtil;
import com.eaf.core.ulo.common.model.AuditDataM;
import com.eaf.core.ulo.common.model.FieldElement;
import com.eaf.core.ulo.common.properties.ImplementControl;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.AuditFormUtil;
import com.eaf.core.ulo.common.util.FormErrorUtil;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.shared.view.form.ORIGSubForm;
import com.eaf.orig.ulo.constant.MConstant;
import com.eaf.orig.ulo.control.util.PersonalInfoUtil;
import com.eaf.orig.ulo.model.app.ApplicationDataM;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;

public class IncreaseProductForm extends ORIGSubForm {
	private static final long serialVersionUID = 1L;
	private static transient Logger logger = Logger.getLogger(IncreaseProductForm.class);
	private final String APPLICATION_TYPE_ADD_SUP = SystemConstant.getConstant("APPLICATION_TYPE_ADD_SUP");
	private final String APPLICATION_CARD_TYPE_BORROWER = SystemConstant.getConstant("APPLICATION_CARD_TYPE_BORROWER");
	private final String PERSONAL_TYPE_APPLICANT = SystemConstant.getConstant("PERSONAL_TYPE_APPLICANT");
	
	private final String IA_PRODUCT_FORM = SystemConstant.getConstant("IA_PRODUCT_FORM");
	@Override
	public void setProperties(HttpServletRequest request, Object appForm) {
		logger.debug("Call ProductForm .... ");
	}

	@SuppressWarnings( { "unchecked", "rawtypes" })
	@Override
	public HashMap validateForm(HttpServletRequest request, Object appForm) {
		String ERROR_NO_APPLICATIONS = MessageErrorUtil.getText(request, "ERROR_NO_APPLICATIONS");
		FormErrorUtil formError = new FormErrorUtil();
		logger.debug("IA Increase PRODUCT validateForm .....");
		// TODO: Validation application products
		int applicationSize = 0;
		String applicationType = "";
	
		ApplicationGroupDataM applicationGroup = (ApplicationGroupDataM) appForm;
		if(!Util.empty(applicationGroup)){
			ArrayList<ApplicationDataM> applications = applicationGroup.filterApplicationLifeCycle();
			applicationType = applicationGroup.getApplicationType();
			if (Util.empty(applications) && !Util.empty(applicationGroup.getPersonalInfos(PERSONAL_TYPE_APPLICANT))) {
					formError.mandatory(IA_PRODUCT_FORM, "APPLICATION_ITEM","PRODUCTS_CARD_TYPE","","", request);
			}
		}
		if (applicationSize == 0 && ( !Util.empty(applicationType) && !applicationType.equals(APPLICATION_TYPE_ADD_SUP))) {
			ArrayList<PersonalInfoDataM> personals = applicationGroup.getPersonalInfos();
			boolean PersonalTypeMain = false;
			if(!Util.empty(personals)){
				for(PersonalInfoDataM personalInfo:personals){
		 			String PERSONAL_TYPE = personalInfo.getPersonalType();
		 			if(!Util.empty(PERSONAL_TYPE) && PERSONAL_TYPE.equals(APPLICATION_CARD_TYPE_BORROWER)){
		 				PersonalTypeMain = true;
		 			}
				}
			}
			if(PersonalTypeMain){
				//330.1 FUT
				formError.error("PRODUCTS_CARD_TYPE", ERROR_NO_APPLICATIONS);
			}
		}
		
		return formError.getFormError();
	}

	@Override
	public boolean validateSubForm(HttpServletRequest request) {
		return false;
	}
	
	@Override
	public ArrayList<FieldElement> elementForm(HttpServletRequest request,Object objectForm) {
		ArrayList<FieldElement>  fieldElementList = new ArrayList<FieldElement>();
		ApplicationGroupDataM applicationGroup = (ApplicationGroupDataM) objectForm;
		ArrayList<String> products =   applicationGroup.getProducts();
		PersonalInfoDataM personalInfo  = PersonalInfoUtil.getApplicationTypePersonalInfo(applicationGroup);
		try {
			if(!Util.empty(products)){
				for(String product:products){
					logger.debug("product>>>"+product);
					ElementInf element = ImplementControl.getElement("PRODUCT_DISPLAY", product);
					ArrayList<FieldElement>  fieldElements = element.elementForm(request, personalInfo);
					if(!Util.empty(fieldElements)){
						fieldElementList.addAll(fieldElements);
					}
				}
			}

		} catch (Exception e) {
			logger.fatal("ERROR",e);
		}
		return fieldElementList;
	}
	@Override
	public ArrayList<AuditDataM> auditForm(HttpServletRequest request, Object objectForm, Object lastObjectForm) {
		ArrayList<AuditDataM> audits = new ArrayList<>();
		
		AuditFormUtil auditFormUtil = new AuditFormUtil();
		String subformId = getSubFormID();
		auditFormUtil.auditForm(subformId, "CARD_TYPE", objectForm,lastObjectForm, request);
		audits.addAll(auditFormUtil.getAuditForm());
		
		return audits;
	}
}
