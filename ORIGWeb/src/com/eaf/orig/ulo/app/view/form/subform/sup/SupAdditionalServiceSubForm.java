package com.eaf.orig.ulo.app.view.form.subform.sup;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.properties.FormControl;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.ulo.app.util.GenerateUnique;
import com.eaf.orig.ulo.constant.MConstant;
import com.eaf.orig.ulo.model.app.ApplicationDataM;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.PersonalApplicationInfoDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;
import com.eaf.orig.ulo.model.app.SpecialAdditionalServiceDataM;

public class SupAdditionalServiceSubForm extends AdditionalServiceSubForm {
	private static transient Logger logger = Logger.getLogger(SupAdditionalServiceSubForm.class);
	private String SUP_CARD_SEPARATELY_FORM = SystemConstant.getConstant("SUP_CARD_SEPARATELY_FORM");
//	String PERSONAL_TYPE_SUPPLEMENTARY = SystemConstant.getConstant("PERSONAL_TYPE_SUPPLEMENTARY");
//	String PRODUCT_CRADIT_CARD = SystemConstant.getConstant("PRODUCT_CRADIT_CARD");
//	String SPECIAL_ADDITIONAL_SERVICE_SPENDING_ALERT = SystemConstant.getConstant("SPECIAL_ADDITIONAL_SERVICE_SPENDING_ALERT");
//	String SPECIAL_ADDITIONAL_SERVICE_DUE_ALERT = SystemConstant.getConstant("SPECIAL_ADDITIONAL_SERVICE_DUE_ALERT");
//	String FLAG_YES = SystemConstant.getConstant("FLAG_YES");
//	String APPLICATION_TYPE_ADD_SUP = SystemConstant.getConstant("APPLICATION_TYPE_ADD_SUP");
//	
//	@Override
//	public void setProperties(HttpServletRequest request, Object appForm) {
//		String SERVICE_SPENDING_ALERT_FLAG = request.getParameter("SERVICE_TYPE_SPENDING_ALERT_"+PRODUCT_CRADIT_CARD);
//		String SERVICE_DUE_ALERT_FLAG = request.getParameter("SERVICE_TYPE_DUE_ALERT_"+PRODUCT_CRADIT_CARD);		
//		logger.debug("SERVICE_SPENDING_ALERT_FLAG >>> "+SERVICE_SPENDING_ALERT_FLAG);
//		logger.debug("SERVICE_DUE_ALERT_FLAG >>> "+SERVICE_DUE_ALERT_FLAG);	
//		ApplicationGroupDataM applicationGroup = ((ApplicationGroupDataM)appForm);
//		PersonalInfoDataM personalInfo  = applicationGroup.getPersonalInfo(PERSONAL_TYPE_SUPPLEMENTARY);
//		String personalId = personalInfo.getPersonalId();
//		logger.debug("personalId >> "+personalId);
//		ArrayList<ApplicationDataM> applications = applicationGroup.filterApplicationLifeCycleByPersonalId(personalId,PRODUCT_CRADIT_CARD);	
//		
//		// Spending Alert Service
//				SpecialAdditionalServiceDataM spendingAlertService = applicationGroup.getSpecialAdditionalServiceLifeCycleByPersonalId(personalId,PRODUCT_CRADIT_CARD,SPECIAL_ADDITIONAL_SERVICE_SPENDING_ALERT);
//				
//				if(FLAG_YES.equals(SERVICE_SPENDING_ALERT_FLAG)){
//					if(Util.empty(spendingAlertService) ){
//						spendingAlertService = new SpecialAdditionalServiceDataM();
//						String serviceId = GenerateUnique.generate(OrigConstant.SeqNames.ORIG_ADDITIONAL_SERVICE_PK);
//						spendingAlertService.init(serviceId);
//						spendingAlertService.setPersonalId(personalId);
//						spendingAlertService.setServiceType(SPECIAL_ADDITIONAL_SERVICE_SPENDING_ALERT);
//						applicationGroup.addSpecialAdditionalService(spendingAlertService);
//						logger.debug("add specialAdditionalService :"+SPECIAL_ADDITIONAL_SERVICE_SPENDING_ALERT);
//					}
//					if(!Util.empty(applications)){
//						for(ApplicationDataM application : applications){
//							logger.debug("spendingAlertService.getServiceId() >>"+spendingAlertService.getServiceId());
//							application.addAdditionalServiceId(spendingAlertService.getServiceId());
//						}
//					}
//				}else{
//					if(!Util.empty(spendingAlertService) ){
//						applicationGroup.removeSpecialAdditionalLifeCycleByPersonalId(personalId, PRODUCT_CRADIT_CARD,SPECIAL_ADDITIONAL_SERVICE_SPENDING_ALERT);
//						logger.debug("remove specialAdditionalService :"+SPECIAL_ADDITIONAL_SERVICE_SPENDING_ALERT);
//						if(!Util.empty(applications)){
//							for(ApplicationDataM application : applications){
//								application.removeAdditionalServiceId(spendingAlertService.getServiceId());
//							}
//						}
//					}
//				}
//				
//				// Due Alert service
//						SpecialAdditionalServiceDataM dueAlertService = applicationGroup.getSpecialAdditionalServiceLifeCycleByPersonalId(personalId
//								, PRODUCT_CRADIT_CARD, SPECIAL_ADDITIONAL_SERVICE_DUE_ALERT);
//						if(FLAG_YES.equals(SERVICE_DUE_ALERT_FLAG)){
//							if(Util.empty(dueAlertService) ){
//								dueAlertService = new SpecialAdditionalServiceDataM();
//								String serviceId= GenerateUnique.generate(OrigConstant.SeqNames.ORIG_ADDITIONAL_SERVICE_PK);
//								dueAlertService.init(serviceId);
//								dueAlertService.setPersonalId(personalId);
//								dueAlertService.setServiceType(SPECIAL_ADDITIONAL_SERVICE_DUE_ALERT);
//								applicationGroup.addSpecialAdditionalService(dueAlertService);
//								logger.debug("add specialAdditionalService :"+SPECIAL_ADDITIONAL_SERVICE_DUE_ALERT);
//							}
//							if(!Util.empty(applications)){
//								for(ApplicationDataM application : applications){
//									application.addAdditionalServiceId(dueAlertService.getServiceId());
//								}
//							}
//						}else{
//							if(!Util.empty(dueAlertService)){
//								applicationGroup.removeSpecialAdditionalLifeCycleByPersonalId(personalId,PRODUCT_CRADIT_CARD,SPECIAL_ADDITIONAL_SERVICE_DUE_ALERT);
//								logger.debug("remove specialAdditionalService :"+SPECIAL_ADDITIONAL_SERVICE_DUE_ALERT);
//								if(!Util.empty(applications)){
//									for(ApplicationDataM application : applications){
//										application.removeAdditionalServiceId(dueAlertService.getServiceId());
//									}
//								}
//							}
//						}
//	}
	
	@Override
	public String renderSubformFlag(HttpServletRequest request, Object objectForm) {
		String formId = FormControl.getOrigFormId(request);
		logger.debug("formId >> "+formId);
		logger.debug("SUP_CARD_SEPARATELY_FORM >> "+SUP_CARD_SEPARATELY_FORM);
		if(Util.empty(formId) || SUP_CARD_SEPARATELY_FORM.equals(formId)){
			return MConstant.FLAG_Y;
		}
		return MConstant.FLAG_N;
	}
}
