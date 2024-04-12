package com.eaf.orig.ulo.app.view.form.subform.product.kcc;

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
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;
import com.eaf.orig.ulo.model.app.SpecialAdditionalServiceDataM;

@SuppressWarnings("serial")
public class CreditShieldSubForm extends ORIGSubForm {
	private static transient Logger logger = Logger.getLogger(CreditShieldSubForm.class);
	String PRODUCT_CRADIT_CARD = SystemConstant.getConstant("PRODUCT_CRADIT_CARD");
	String PERSONAL_TYPE_APPLICANT= SystemConstant.getConstant("PERSONAL_TYPE_APPLICANT");
	String SPECIAL_ADDITIONAL_SERVICE_CREDIT_SHIELD = SystemConstant.getConstant("SPECIAL_ADDITIONAL_SERVICE_CREDIT_SHIELD");
	String FLAG_YES = SystemConstant.getConstant("FLAG_YES");
	@Override
	public void setProperties(HttpServletRequest request, Object appForm) {
		logger.debug("CREDIT_SHIRLD_FLAG_"+PRODUCT_CRADIT_CARD);
		String CREDIT_SHIRLD_FLAG = request.getParameter("CREDIT_SHIRLD_FLAG_"+PRODUCT_CRADIT_CARD)	;
		logger.debug("CREDIT_SHIRLD_FLAG >> "+CREDIT_SHIRLD_FLAG);
		ApplicationGroupDataM applicationGroup = (ApplicationGroupDataM)appForm;
		PersonalInfoDataM personalInfo = applicationGroup.getPersonalInfo(PERSONAL_TYPE_APPLICANT);
		String personalId = personalInfo.getPersonalId();
		ArrayList<ApplicationDataM> applications = applicationGroup.filterApplicationLifeCycleByPersonalId(personalId,PRODUCT_CRADIT_CARD);
		SpecialAdditionalServiceDataM specialAdditionalService = applicationGroup.getSpecialAdditionalServiceLifeCycleByPersonalId(personalId
				, PRODUCT_CRADIT_CARD, SPECIAL_ADDITIONAL_SERVICE_CREDIT_SHIELD);		
		if(!Util.empty(CREDIT_SHIRLD_FLAG) && FLAG_YES.equals(CREDIT_SHIRLD_FLAG)){
			logger.debug("add specialAdditionalService");
			if(Util.empty(specialAdditionalService)) {
				specialAdditionalService = new SpecialAdditionalServiceDataM();
				String serviceId = GenerateUnique.generate(OrigConstant.SeqNames.ORIG_ADDITIONAL_SERVICE_PK);
				specialAdditionalService.init(serviceId);
				specialAdditionalService.setPersonalId(personalId);
				applicationGroup.addSpecialAdditionalService(specialAdditionalService);
				specialAdditionalService.setServiceType(SPECIAL_ADDITIONAL_SERVICE_CREDIT_SHIELD);
			}
			if(!Util.empty(applications)){
				for(ApplicationDataM applicationItem :applications){
					applicationItem.addAdditionalServiceId(specialAdditionalService.getServiceId());
				}
			}			
		}else{
			if(!Util.empty(specialAdditionalService)) {
				logger.debug("remove specialAdditionalService");
				applicationGroup.removeSpecialAdditionalLifeCycleByPersonalId(personalId,PRODUCT_CRADIT_CARD,SPECIAL_ADDITIONAL_SERVICE_CREDIT_SHIELD);
				if(!Util.empty(applications)){
					for(ApplicationDataM applicationItem :applications){
						applicationItem.removeAdditionalServiceId(specialAdditionalService.getServiceId());
					}
				}
			}
		}
	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public HashMap validateForm(HttpServletRequest request, Object appForm) {
		return null;
	}
	@Override
	public boolean validateSubForm(HttpServletRequest request) {
		return false;
	}
}
