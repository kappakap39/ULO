package com.eaf.orig.ulo.app.view.form.subform.sup;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.shared.view.form.ORIGSubForm;
import com.eaf.orig.ulo.app.util.GenerateUnique;
import com.eaf.orig.ulo.model.app.ApplicationDataM;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.PersonalApplicationInfoDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;
import com.eaf.orig.ulo.model.app.SpecialAdditionalServiceDataM;

@SuppressWarnings("serial")
public class AdditionalServiceSubForm extends ORIGSubForm{
	private static transient Logger logger = Logger.getLogger(AdditionalServiceSubForm.class);
	String PRODUCT_CRADIT_CARD = SystemConstant.getConstant("PRODUCT_CRADIT_CARD");
	String SPECIAL_ADDITIONAL_SERVICE_SPENDING_ALERT = SystemConstant.getConstant("SPECIAL_ADDITIONAL_SERVICE_SPENDING_ALERT");
	String SPECIAL_ADDITIONAL_SERVICE_DUE_ALERT = SystemConstant.getConstant("SPECIAL_ADDITIONAL_SERVICE_DUE_ALERT");
	String FLAG_YES = SystemConstant.getConstant("FLAG_YES");
	String APPLICATION_TYPE_ADD_SUP = SystemConstant.getConstant("APPLICATION_TYPE_ADD_SUP");
	String PERSONAL_TYPE_SUPPLEMENTARY = SystemConstant.getConstant("PERSONAL_TYPE_SUPPLEMENTARY");
	
	@Override
	public void setProperties(HttpServletRequest request, Object appForm) {
//		String SERVICE_SPENDING_ALERT_FLAG = request.getParameter("SERVICE_SPENDING_ALERT_FLAG_"+PRODUCT_CRADIT_CARD);
//		String SERVICE_DUE_ALERT_FLAG = request.getParameter("SERVICE_DUE_ALERT_FLAG_"+PRODUCT_CRADIT_CARD);		
		String SERVICE_SPENDING_ALERT_FLAG = request.getParameter("SERVICE_TYPE_SPENDING_ALERT_"+PRODUCT_CRADIT_CARD);
		String SERVICE_DUE_ALERT_FLAG = request.getParameter("SERVICE_TYPE_DUE_ALERT_"+PRODUCT_CRADIT_CARD);	
		logger.debug("SERVICE_SPENDING_ALERT_FLAG >>> "+SERVICE_SPENDING_ALERT_FLAG);
		logger.debug("SERVICE_DUE_ALERT_FLAG >>> "+SERVICE_DUE_ALERT_FLAG);		
		PersonalApplicationInfoDataM personalApplicationInfo = null;
		SpecialAdditionalServiceDataM spendingAlert = null;
		ArrayList<ApplicationDataM> applications = null;
		String applicationType = null;
		String personalId = null;
		ApplicationGroupDataM applicationGroup = null;
		SpecialAdditionalServiceDataM dueAlert = null;
		if(appForm instanceof PersonalApplicationInfoDataM){
		personalApplicationInfo = (PersonalApplicationInfoDataM)appForm;
		personalId = personalApplicationInfo.getPersonalId();	
			applicationType = personalApplicationInfo.getApplicationType();
		logger.debug("personalId >> "+personalId);
		logger.debug("applicationType >> "+applicationType);
			applications = personalApplicationInfo.filterApplicationRelationLifeCycle(personalId,PRODUCT_CRADIT_CARD);	
			
			if(APPLICATION_TYPE_ADD_SUP.equals(applicationType)){
			spendingAlert =	personalApplicationInfo.getSpecialAdditionalServiceLifeCycleByPersonalId(personalId,SPECIAL_ADDITIONAL_SERVICE_SPENDING_ALERT);	
			}else{
			spendingAlert =	personalApplicationInfo.getSpecialAdditionalServiceLifeCycleByPersonalId(personalId,PRODUCT_CRADIT_CARD,SPECIAL_ADDITIONAL_SERVICE_SPENDING_ALERT);	
			}
			
			if(APPLICATION_TYPE_ADD_SUP.equals(applicationType)){
				dueAlert = personalApplicationInfo.getSpecialAdditionalServiceLifeCycleByPersonalId(personalId, SPECIAL_ADDITIONAL_SERVICE_DUE_ALERT);
			}else{
				dueAlert = personalApplicationInfo.getSpecialAdditionalServiceLifeCycleByPersonalId(personalId
					, PRODUCT_CRADIT_CARD, SPECIAL_ADDITIONAL_SERVICE_DUE_ALERT);
			}
		}else if(appForm instanceof ApplicationGroupDataM){
			applicationGroup = ((ApplicationGroupDataM)appForm);
			PersonalInfoDataM personalInfo  = applicationGroup.getPersonalInfo(PERSONAL_TYPE_SUPPLEMENTARY);
			personalId = personalInfo.getPersonalId();
			logger.debug("personalId >> "+personalId);
			applications = applicationGroup.filterApplicationLifeCycleByPersonalId(personalId,PRODUCT_CRADIT_CARD);	
			spendingAlert = applicationGroup.getSpecialAdditionalServiceLifeCycleByPersonalId(personalId,PRODUCT_CRADIT_CARD,SPECIAL_ADDITIONAL_SERVICE_SPENDING_ALERT);
			dueAlert = applicationGroup.getSpecialAdditionalServiceLifeCycleByPersonalId(personalId
					, PRODUCT_CRADIT_CARD, SPECIAL_ADDITIONAL_SERVICE_DUE_ALERT);
		}
		
		if(!Util.empty(SERVICE_SPENDING_ALERT_FLAG) && FLAG_YES.equals(SERVICE_SPENDING_ALERT_FLAG)){
			if(Util.empty(spendingAlert)) {
				spendingAlert = new SpecialAdditionalServiceDataM();
				String serviceId = GenerateUnique.generate(OrigConstant.SeqNames.ORIG_ADDITIONAL_SERVICE_PK);
				spendingAlert.init(serviceId);
				spendingAlert.setPersonalId(personalId);
				if(appForm instanceof PersonalApplicationInfoDataM){
					personalApplicationInfo.addSpecialAdditionalService(spendingAlert);
				}else if(appForm instanceof ApplicationGroupDataM){
					applicationGroup.addSpecialAdditionalService(spendingAlert);
				}
				spendingAlert.setServiceType(SPECIAL_ADDITIONAL_SERVICE_SPENDING_ALERT);
			}
			if(!Util.empty(applications)){
				for(ApplicationDataM application :applications){
					application.addAdditionalServiceId(spendingAlert.getServiceId());					
				}
			}			
		}else{
			if(!Util.empty(spendingAlert)) {
				logger.debug("remove specialAdditionalService");
				if(appForm instanceof PersonalApplicationInfoDataM){
					if(APPLICATION_TYPE_ADD_SUP.equals(applicationType)){
						personalApplicationInfo.removeSpecialAdditionalLifeCycleByPersonalId(personalId, SPECIAL_ADDITIONAL_SERVICE_SPENDING_ALERT);
					}else{
						personalApplicationInfo.removeSpecialAdditionalLifeCycleByPersonalId(personalId, PRODUCT_CRADIT_CARD, SPECIAL_ADDITIONAL_SERVICE_SPENDING_ALERT);
					}
				
				}else if(appForm instanceof ApplicationGroupDataM){
					applicationGroup.removeSpecialAdditionalLifeCycleByPersonalId(personalId, PRODUCT_CRADIT_CARD,SPECIAL_ADDITIONAL_SERVICE_SPENDING_ALERT);
				}
				if(!Util.empty(applications)){
					for(ApplicationDataM application :applications){
						application.removeAdditionalServiceId(spendingAlert.getServiceId());
					}
				}
			}
		}

		
	
		if(!Util.empty(SERVICE_DUE_ALERT_FLAG) && FLAG_YES.equals(SERVICE_DUE_ALERT_FLAG)){
			if(Util.empty(dueAlert)) {
				dueAlert = new SpecialAdditionalServiceDataM();
				String serviceId= GenerateUnique.generate(OrigConstant.SeqNames.ORIG_ADDITIONAL_SERVICE_PK);
				dueAlert.init(serviceId);
				dueAlert.setPersonalId(personalId);
				if(appForm instanceof PersonalApplicationInfoDataM){
					personalApplicationInfo.addSpecialAdditionalService(dueAlert);
				}else if(appForm instanceof ApplicationGroupDataM){
					applicationGroup.addSpecialAdditionalService(dueAlert);
				}
				
				dueAlert.setServiceType(SPECIAL_ADDITIONAL_SERVICE_DUE_ALERT);
			}
			if(!Util.empty(applications)){
				for(ApplicationDataM application :applications){
					application.addAdditionalServiceId(dueAlert.getServiceId());					
				}
			}			
		} else {
			if(!Util.empty(dueAlert)) {
				logger.debug("remove specialAdditionalService");
				if(appForm instanceof PersonalApplicationInfoDataM){
					if(APPLICATION_TYPE_ADD_SUP.equals(applicationType)){
						personalApplicationInfo.removeSpecialAdditionalLifeCycleByPersonalId(personalId, SPECIAL_ADDITIONAL_SERVICE_DUE_ALERT);
					}else{
						personalApplicationInfo.removeSpecialAdditionalLifeCycleByPersonalId(personalId, PRODUCT_CRADIT_CARD, SPECIAL_ADDITIONAL_SERVICE_DUE_ALERT);
					}
				}else if(appForm instanceof ApplicationGroupDataM){
						applicationGroup.removeSpecialAdditionalLifeCycleByPersonalId(personalId,PRODUCT_CRADIT_CARD,SPECIAL_ADDITIONAL_SERVICE_DUE_ALERT);
				}
				if(!Util.empty(applications)){
					for(ApplicationDataM application :applications){
						application.removeAdditionalServiceId(dueAlert.getServiceId());
					}
				}
			}
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public HashMap validateForm(HttpServletRequest request, Object appForm) {
		return null;
	}
	@Override
	public boolean validateSubForm(HttpServletRequest request) {
		return false;
	}
}
