package com.eaf.orig.ulo.app.view.form.subform.product;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.shared.view.form.ORIGSubForm;
import com.eaf.orig.ulo.app.util.GenerateUnique;
import com.eaf.orig.ulo.app.view.form.subform.product.kcc.AdditionalServiceSubForm1;
import com.eaf.orig.ulo.constant.MConstant;
import com.eaf.orig.ulo.model.app.ApplicationDataM;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;
import com.eaf.orig.ulo.model.app.SpecialAdditionalServiceDataM;

@SuppressWarnings("serial")
public class SupplementaryServiceSupForm extends ORIGSubForm{
	private static transient Logger logger = Logger.getLogger(AdditionalServiceSubForm1.class);
	String PERSONAL_TYPE_SUPPLEMENTARY = SystemConstant.getConstant("PERSONAL_TYPE_SUPPLEMENTARY");
	String PRODUCT_CRADIT_CARD =  SystemConstant.getConstant("PRODUCT_CRADIT_CARD");
	String SPECIAL_ADDITIONAL_SERVICE_SPENDING_ALERT = SystemConstant.getConstant("SPECIAL_ADDITIONAL_SERVICE_SPENDING_ALERT");
	String SPECIAL_ADDITIONAL_SERVICE_DUE_ALERT = SystemConstant.getConstant("SPECIAL_ADDITIONAL_SERVICE_DUE_ALERT");
	String SPECIAL_ADDITIONAL_SERVICE_EMAIL = SystemConstant.getConstant("SPECIAL_ADDITIONAL_SERVICE_EMAIL");
	String GEN_PARAM_CC_INFINITE = SystemConstant.getConstant("GEN_PARAM_CC_INFINITE");
	String FLAG_YES = SystemConstant.getConstant("FLAG_YES");
	@Override
	public void setProperties(HttpServletRequest request, Object appForm) {
		ApplicationGroupDataM applicationGroup = ((ApplicationGroupDataM)appForm);
		PersonalInfoDataM personalInfo  = applicationGroup.getPersonalInfo(PERSONAL_TYPE_SUPPLEMENTARY);
		String personalId = personalInfo.getPersonalId();
		logger.debug("personalId >> "+personalId);
		ArrayList<ApplicationDataM> applications = applicationGroup.filterApplicationLifeCycleByPersonalId(personalId,PRODUCT_CRADIT_CARD);
											
		String SERVICE_TYPE_SPENDING_ALERT = request.getParameter("SERVICE_TYPE_SPENDING_ALERT_"+PRODUCT_CRADIT_CARD);
		String SERVICE_TYPE_DUE_ALERT = request.getParameter("SERVICE_TYPE_DUE_ALERT_"+PRODUCT_CRADIT_CARD);		
		
		logger.debug("SERVICE_TYPE_SPENDING_ALERT >> "+SERVICE_TYPE_SPENDING_ALERT);		
		logger.debug("SERVICE_TYPE_DUE_ALERT >> "+SERVICE_TYPE_DUE_ALERT);
		
		// Spending Alert Service
		SpecialAdditionalServiceDataM spendingAlertService = applicationGroup.getSpecialAdditionalServiceLifeCycleByPersonalId(personalId,PRODUCT_CRADIT_CARD,SPECIAL_ADDITIONAL_SERVICE_SPENDING_ALERT);
		
		if(FLAG_YES.equals(SERVICE_TYPE_SPENDING_ALERT)){
			if(Util.empty(spendingAlertService) ){
				spendingAlertService = new SpecialAdditionalServiceDataM();
				String serviceId = GenerateUnique.generate(OrigConstant.SeqNames.ORIG_ADDITIONAL_SERVICE_PK);
				spendingAlertService.init(serviceId);
				spendingAlertService.setPersonalId(personalId);
				spendingAlertService.setServiceType(SPECIAL_ADDITIONAL_SERVICE_SPENDING_ALERT);
				applicationGroup.addSpecialAdditionalService(spendingAlertService);
				logger.debug("add specialAdditionalService :"+SPECIAL_ADDITIONAL_SERVICE_SPENDING_ALERT);
			}
			if(!Util.empty(applications)){
				for(ApplicationDataM application : applications){
					logger.debug("spendingAlertService.getServiceId() >>"+spendingAlertService.getServiceId());
					application.addAdditionalServiceId(spendingAlertService.getServiceId());
				}
			}
		}else{
			if(!Util.empty(spendingAlertService) ){
				applicationGroup.removeSpecialAdditionalLifeCycleByPersonalId(personalId, PRODUCT_CRADIT_CARD,SPECIAL_ADDITIONAL_SERVICE_SPENDING_ALERT);
				logger.debug("remove specialAdditionalService :"+SPECIAL_ADDITIONAL_SERVICE_SPENDING_ALERT);
				if(!Util.empty(applications)){
					for(ApplicationDataM application : applications){
						application.removeAdditionalServiceId(spendingAlertService.getServiceId());
					}
				}
			}
		}
		
		// Due Alert service
				SpecialAdditionalServiceDataM dueAlertService = applicationGroup.getSpecialAdditionalServiceLifeCycleByPersonalId(personalId
						, PRODUCT_CRADIT_CARD, SPECIAL_ADDITIONAL_SERVICE_DUE_ALERT);
				if(FLAG_YES.equals(SERVICE_TYPE_DUE_ALERT)){
					if(Util.empty(dueAlertService) ){
						dueAlertService = new SpecialAdditionalServiceDataM();
						String serviceId= GenerateUnique.generate(OrigConstant.SeqNames.ORIG_ADDITIONAL_SERVICE_PK);
						dueAlertService.init(serviceId);
						dueAlertService.setPersonalId(personalId);
						dueAlertService.setServiceType(SPECIAL_ADDITIONAL_SERVICE_DUE_ALERT);
						applicationGroup.addSpecialAdditionalService(dueAlertService);
						logger.debug("add specialAdditionalService :"+SPECIAL_ADDITIONAL_SERVICE_DUE_ALERT);
					}
					if(!Util.empty(applications)){
						for(ApplicationDataM application : applications){
							application.addAdditionalServiceId(dueAlertService.getServiceId());
						}
					}
				}else{
					if(!Util.empty(dueAlertService)){
						applicationGroup.removeSpecialAdditionalLifeCycleByPersonalId(personalId,PRODUCT_CRADIT_CARD,SPECIAL_ADDITIONAL_SERVICE_DUE_ALERT);
						logger.debug("remove specialAdditionalService :"+SPECIAL_ADDITIONAL_SERVICE_DUE_ALERT);
						if(!Util.empty(applications)){
							for(ApplicationDataM application : applications){
								application.removeAdditionalServiceId(dueAlertService.getServiceId());
							}
						}
					}
				}
		
		
	}

	@Override
	public HashMap<String, Object> validateForm(HttpServletRequest request,
			Object appForm) {

		return null;
	}

	@Override
	public boolean validateSubForm(HttpServletRequest request) {

		return false;
	}
	
}
