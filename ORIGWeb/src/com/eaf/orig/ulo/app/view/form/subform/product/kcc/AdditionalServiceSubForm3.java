package com.eaf.orig.ulo.app.view.form.subform.product.kcc;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.properties.SystemConfig;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.shared.view.form.ORIGSubForm;
import com.eaf.orig.ulo.app.util.GenerateUnique;
import com.eaf.orig.ulo.constant.MConstant;
import com.eaf.orig.ulo.model.app.ApplicationDataM;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;
import com.eaf.orig.ulo.model.app.SpecialAdditionalServiceDataM;

@SuppressWarnings("serial")
public class AdditionalServiceSubForm3 extends ORIGSubForm{
	private static transient Logger logger = Logger.getLogger(AdditionalServiceSubForm3.class);
	String PERSONAL_TYPE_APPLICANT = SystemConstant.getConstant("PERSONAL_TYPE_APPLICANT");
	String PRODUCT_CRADIT_CARD =  SystemConstant.getConstant("PRODUCT_CRADIT_CARD");
	String SPECIAL_ADDITIONAL_SERVICE_SPENDING_ALERT = SystemConstant.getConstant("SPECIAL_ADDITIONAL_SERVICE_SPENDING_ALERT");
	String SPECIAL_ADDITIONAL_SERVICE_DUE_ALERT = SystemConstant.getConstant("SPECIAL_ADDITIONAL_SERVICE_DUE_ALERT");
	String SPECIAL_ADDITIONAL_SERVICE_POSTAL = SystemConstant.getConstant("SPECIAL_ADDITIONAL_SERVICE_POSTAL");
	String GEN_PARAM_CC_PREMIER = SystemConstant.getConstant("GEN_PARAM_CC_PREMIER");
	String FLAG_YES = SystemConstant.getConstant("FLAG_YES");
	//ADDITIONAL_SERVICE_PREMIER
	@Override
	public void setProperties(HttpServletRequest request, Object appForm) {		
		ApplicationGroupDataM applicationGroup = ((ApplicationGroupDataM)appForm);
		PersonalInfoDataM personalInfo  = applicationGroup.getPersonalInfo(PERSONAL_TYPE_APPLICANT);
		String personalId = personalInfo.getPersonalId();
		logger.debug("personalId >> "+personalId);
		ArrayList<ApplicationDataM> applications = applicationGroup.filterApplicationLifeCycleByPersonalId(personalId,PRODUCT_CRADIT_CARD);
		String SERVICE_TYPE_SPENDING_ALERT = request.getParameter("SERVICE_TYPE_SPENDING_ALERT_"+PRODUCT_CRADIT_CARD);
		String SERVICE_TYPE_DUE_ALERT = request.getParameter("SERVICE_TYPE_DUE_ALERT_"+PRODUCT_CRADIT_CARD);
		String SERVICE_TYPE_POSTAL_FLAG = request.getParameter("SERVICE_TYPE_POSTAL_FLAG_"+PRODUCT_CRADIT_CARD);		
		
		logger.debug("SERVICE_TYPE_SPENDING_ALERT >> "+SERVICE_TYPE_SPENDING_ALERT);		
		logger.debug("SERVICE_TYPE_DUE_ALERT >> "+SERVICE_TYPE_DUE_ALERT);
		logger.debug("SERVICE_TYPE_POSTAL_FLAG >> "+SERVICE_TYPE_POSTAL_FLAG);
		
		// Spending Alert Service
		SpecialAdditionalServiceDataM spendingAlertService = applicationGroup.getSpecialAdditionalServiceLifeCycleByPersonalId(personalId
				,PRODUCT_CRADIT_CARD,SPECIAL_ADDITIONAL_SERVICE_SPENDING_ALERT);
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
			if(!Util.empty(dueAlertService) ){
				applicationGroup.removeSpecialAdditionalLifeCycleByPersonalId(personalId,PRODUCT_CRADIT_CARD,SPECIAL_ADDITIONAL_SERVICE_DUE_ALERT);
				logger.debug("remove specialAdditionalService :"+SPECIAL_ADDITIONAL_SERVICE_DUE_ALERT);
				if(!Util.empty(applications)){
					for(ApplicationDataM application : applications){
						application.removeAdditionalServiceId(dueAlertService.getServiceId());
					}
				}
			}
		}
		
		// Email Service
		SpecialAdditionalServiceDataM postalService = applicationGroup.getSpecialAdditionalServiceLifeCycleByPersonalId(personalId
				, PRODUCT_CRADIT_CARD, SERVICE_TYPE_POSTAL_FLAG);
		if(FLAG_YES.equals(SERVICE_TYPE_POSTAL_FLAG)){
			if(Util.empty(postalService) ){
				postalService = new SpecialAdditionalServiceDataM();
				String serviceId= GenerateUnique.generate(OrigConstant.SeqNames.ORIG_ADDITIONAL_SERVICE_PK);
				postalService.init(serviceId);
				postalService.setPersonalId(personalId);
				postalService.setServiceType(SPECIAL_ADDITIONAL_SERVICE_POSTAL);
				applicationGroup.addSpecialAdditionalService(postalService);
				logger.debug("add specialAdditionalService :"+SPECIAL_ADDITIONAL_SERVICE_POSTAL);
			}
			if(!Util.empty(applications)){
				for(ApplicationDataM application : applications){
					application.addAdditionalServiceId(postalService.getServiceId());
				}
			}
		}else{
			if(!Util.empty(postalService) ){
				applicationGroup.removeSpecialAdditionalLifeCycleByPersonalId(personalId, PRODUCT_CRADIT_CARD,SPECIAL_ADDITIONAL_SERVICE_POSTAL);
				logger.debug("remove specialAdditionalService :"+SPECIAL_ADDITIONAL_SERVICE_POSTAL);
				if(!Util.empty(applications)){
					for(ApplicationDataM application : applications){
						application.removeAdditionalServiceId(postalService.getServiceId());
					}
				}
			}
		}
	}
	
	@Override
	public String renderSubformFlag(HttpServletRequest request,Object objectForm) {
		String rederFLag = MConstant.FLAG.NO;
		ApplicationGroupDataM applicationGroup = ((ApplicationGroupDataM)objectForm);
		if(SystemConfig.getGeneralParam(GEN_PARAM_CC_PREMIER).contains(applicationGroup.getApplicationTemplate())){
			rederFLag= MConstant.FLAG.YES;
		}
		logger.debug("applicationGroup.getApplicationTemplate() >> "+applicationGroup.getApplicationTemplate());
		logger.debug("rederFLag >> "+rederFLag);
		return rederFLag;
	}

	@Override
	public HashMap validateForm(HttpServletRequest request, Object appForm) {
		return null;
	}
	@Override
	public boolean validateSubForm(HttpServletRequest request) {
		return false;
	}
}
