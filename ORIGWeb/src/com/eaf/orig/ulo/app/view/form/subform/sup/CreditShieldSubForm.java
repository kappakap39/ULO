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
import com.eaf.orig.ulo.model.app.PersonalApplicationInfoDataM;
import com.eaf.orig.ulo.model.app.SpecialAdditionalServiceDataM;

@SuppressWarnings("serial")
public class CreditShieldSubForm extends ORIGSubForm {
	private static transient Logger logger = Logger.getLogger(CreditShieldSubForm.class);
	String PRODUCT_CRADIT_CARD= SystemConstant.getConstant("PRODUCT_CRADIT_CARD");
	String SPECIAL_ADDITIONAL_SERVICE_CREDIT_SHIELD = SystemConstant.getConstant("SPECIAL_ADDITIONAL_SERVICE_CREDIT_SHIELD");
	String FLAG_YES = SystemConstant.getConstant("FLAG_YES");
	String APPLICATION_TYPE_ADD_SUP = SystemConstant.getConstant("APPLICATION_TYPE_ADD_SUP");
	@Override
	public void setProperties(HttpServletRequest request, Object appForm) {
		String CREDIT_SHIRLD_FLAG = request.getParameter("CREDIT_SHIRLD_FLAG_"+PRODUCT_CRADIT_CARD)	;
		logger.debug("CREDIT_SHIRLD_FLAG >>> "+CREDIT_SHIRLD_FLAG);
		PersonalApplicationInfoDataM personalApplicationInfo = (PersonalApplicationInfoDataM)appForm;
		String personalId = personalApplicationInfo.getPersonalId();	
		String applicationType = personalApplicationInfo.getApplicationType();
		logger.debug("personalId >> "+personalId);
		logger.debug("applicationType >> "+applicationType);
		ArrayList<ApplicationDataM> applications = personalApplicationInfo.filterApplicationRelationLifeCycle(personalId,PRODUCT_CRADIT_CARD);	
		SpecialAdditionalServiceDataM specialAdditionalService = null;
		if(APPLICATION_TYPE_ADD_SUP.equals(applicationType)){
			specialAdditionalService = personalApplicationInfo.getSpecialAdditionalServiceLifeCycleByPersonalId(personalId, SPECIAL_ADDITIONAL_SERVICE_CREDIT_SHIELD);
		}else{
			specialAdditionalService = personalApplicationInfo.getSpecialAdditionalServiceLifeCycleByPersonalId(personalId
					, PRODUCT_CRADIT_CARD, SPECIAL_ADDITIONAL_SERVICE_CREDIT_SHIELD);
		}	
		if(!Util.empty(CREDIT_SHIRLD_FLAG) && FLAG_YES.equals(CREDIT_SHIRLD_FLAG)){
			if(Util.empty(specialAdditionalService)) {
				specialAdditionalService = new SpecialAdditionalServiceDataM();
				String serviceId = GenerateUnique.generate(OrigConstant.SeqNames.ORIG_ADDITIONAL_SERVICE_PK);
				specialAdditionalService.init(serviceId);
				specialAdditionalService.setPersonalId(personalId);
				specialAdditionalService.setServiceType(SPECIAL_ADDITIONAL_SERVICE_CREDIT_SHIELD);
				personalApplicationInfo.addSpecialAdditionalService(specialAdditionalService);
			}
			if(!Util.empty(applications)){
				for(ApplicationDataM application :applications){
					application.addAdditionalServiceId(specialAdditionalService.getServiceId());					
				}
			}
			
		} else {
			if(!Util.empty(specialAdditionalService)) {
				logger.debug("remove specialAdditionalService");
				if(APPLICATION_TYPE_ADD_SUP.equals(applicationType)){
					personalApplicationInfo.removeSpecialAdditionalLifeCycleByPersonalId(personalId, SPECIAL_ADDITIONAL_SERVICE_CREDIT_SHIELD);
				}else{
					personalApplicationInfo.removeSpecialAdditionalLifeCycleByPersonalId(personalId, PRODUCT_CRADIT_CARD, SPECIAL_ADDITIONAL_SERVICE_CREDIT_SHIELD);
				}
				if(!Util.empty(applications)){
					for(ApplicationDataM application :applications){
						application.removeAdditionalServiceId(specialAdditionalService.getServiceId());
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
