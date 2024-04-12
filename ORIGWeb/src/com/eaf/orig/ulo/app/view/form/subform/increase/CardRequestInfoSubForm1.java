package com.eaf.orig.ulo.app.view.form.subform.increase;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.ElementInf;
import com.eaf.core.ulo.common.model.AuditDataM;
import com.eaf.core.ulo.common.model.FieldElement;
import com.eaf.core.ulo.common.properties.ImplementControl;
import com.eaf.core.ulo.common.util.AuditFormUtil;
import com.eaf.core.ulo.common.util.FormDisplayValueUtil;
import com.eaf.core.ulo.common.util.FormErrorUtil;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.shared.view.form.ORIGSubForm;
import com.eaf.orig.ulo.constant.MConstant;
import com.eaf.orig.ulo.model.app.ApplicationDataM;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;

public class CardRequestInfoSubForm1 extends ORIGSubForm{
	private static transient Logger logger = Logger.getLogger(CardRequestInfoSubForm1.class);
	private String subformId = "INCREASE_CARD_REQUEST_INFO_SUBFROM_1";
	
	@Override
	public void setProperties(HttpServletRequest request, Object appForm) {
		ApplicationGroupDataM applicationGroup = ((ApplicationGroupDataM)appForm);
		ArrayList<ApplicationDataM> applications = applicationGroup.filterApplicationLifeCycle();
		
		
		if(!Util.empty(applications)){
			for(ApplicationDataM applicationM : applications){
				String PRODUCT = applicationM.getProduct();
				if(!Util.empty(PRODUCT)){
					ArrayList<ElementInf> elements = ImplementControl.getElements("LIST_CARD_REQUEST_INCREASE_"+PRODUCT);
					if(!Util.empty(elements)){
						for(ElementInf element : elements){
							if(MConstant.FLAG_Y.equals(element.renderElementFlag(request, applicationGroup))){
								element.processElement(request, applicationGroup);
							}
						}
					}
				}
			}
		}
		
		if(!Util.empty(applications)){
			for(ApplicationDataM applicationM : applications){
				String PRODUCT = applicationM.getProduct();
				if(!Util.empty(PRODUCT)){
					ArrayList<ElementInf> elements = ImplementControl.getElements("CARD_REQUEST_INCREASE_"+PRODUCT);
					if(!Util.empty(elements)){
						for(ElementInf element : elements){
							if(MConstant.FLAG_Y.equals(element.renderElementFlag(request, applicationGroup))){
								element.processElement(request, applicationGroup);
							}
						}
					}
				}
			}
		}		
	}
	
	@Override
	public HashMap validateForm(HttpServletRequest request, Object appForm) {
//		ApplicationGroupDataM applicationGroup = (ApplicationGroupDataM)appForm;
		FormErrorUtil formError = new FormErrorUtil();		
//		ArrayList<ApplicationDataM> applications = applicationGroup.filterApplicationLifeCycle();
//		if (applications.size() == 0) {
//			logger.debug("No applications in APPLICATION_TYPE_INCREASE");
//			formError.error(MessageErrorUtil.getText(request,"ERROR_NO_APPLICATIONS"));
//		}
		formError.mandatory(getSubFormID(),"VALIDATE_INCREASE_CARD_REQUEST",appForm,request);
		return formError.getFormError();
	}
	
	@Override
	public boolean validateSubForm(HttpServletRequest request) {
		return false;
	}
	
	@Override
	public void displayValueForm(HttpServletRequest request, Object objectForm) {
		logger.debug("subformId(displayValueForm) : "+subformId);
		ApplicationGroupDataM applicationGroup = (ApplicationGroupDataM)objectForm;
		FormDisplayValueUtil formValue = new FormDisplayValueUtil(formId,formLevel,roleId,subformId,applicationGroup,request);
		formValue.clearValue("LIST_CARD",applicationGroup);
	}

	@Override
	public ArrayList<FieldElement> elementForm(HttpServletRequest request,Object objectForm) {
		ArrayList<FieldElement> fieldElements = new ArrayList<FieldElement>();
		try {
			ApplicationGroupDataM applicationGroup = ((ApplicationGroupDataM)objectForm);	;
			ArrayList<ApplicationDataM> applications = applicationGroup.filterApplicationLifeCycle();
			
			if(!Util.empty(applications)){
				for(ApplicationDataM applicationM : applications){
					String PRODUCT = applicationM.getProduct();
					if(!Util.empty(PRODUCT)){
						ArrayList<ElementInf> elements = ImplementControl.getElements("LIST_CARD_REQUEST_INCREASE_"+PRODUCT);
						if(!Util.empty(elements)){
							for(ElementInf element : elements){
								if(MConstant.FLAG_Y.equals(element.renderElementFlag(request, applicationGroup))){
									ArrayList<FieldElement> fieldElementList =  element.elementForm(request, applicationM);
									if(!Util.empty(fieldElementList)){
										fieldElements.addAll(fieldElementList);
									}
								}
							}
						}
					}
				}
			}
			
			if(!Util.empty(applications)){
				for(ApplicationDataM applicationM : applications){
					String PRODUCT = applicationM.getProduct();
					if(!Util.empty(PRODUCT)){
						ArrayList<ElementInf> elements = ImplementControl.getElements("CARD_REQUEST_INCREASE_"+PRODUCT);
						if(!Util.empty(elements)){
							for(ElementInf element : elements){
								if(MConstant.FLAG_Y.equals(element.renderElementFlag(request, applicationGroup))){
									ArrayList<FieldElement> fieldElementList =  element.elementForm(request, applicationM);
									if(!Util.empty(fieldElementList)){
										fieldElements.addAll(fieldElementList);
									}
								}
							}
						}
					}
				}
			}
			
		} catch (Exception e) {
			logger.fatal("ERROR",e);
		}
		return fieldElements;
	}
	
	@Override
	public ArrayList<AuditDataM> auditForm(HttpServletRequest request, Object objectForm, Object lastObjectForm) {
		logger.debug("CardRequestInfoSubForm1.auditForm");
		ApplicationGroupDataM applicationGroup = ((ApplicationGroupDataM)objectForm);
		ApplicationGroupDataM lastapplicationGroup = ((ApplicationGroupDataM)lastObjectForm);
		ApplicationDataM application = applicationGroup.filterApplicationItemLifeCycle();
		ApplicationDataM lastApplication = lastapplicationGroup.filterApplicationItemLifeCycle();
		
		AuditFormUtil auditFormUtil = new AuditFormUtil();
		auditFormUtil.auditForm(subformId, "VALIDATE_INCREASE_CARD_REQUEST", applicationGroup,lastapplicationGroup, request);
		auditFormUtil.auditForm(subformId, "SP_SIGN_OFF_DATE", application,lastApplication, request);
		auditFormUtil.auditForm(subformId, "SP_SIGN_OFF_AUTHORIZED_BY", application,lastApplication, request);
		
		return auditFormUtil.getAuditForm();
		
	}
	
}
