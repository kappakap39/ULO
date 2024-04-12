
package com.eaf.orig.ulo.app.view.form.subform.sup;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.FormatUtil;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.FormErrorUtil;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.shared.view.form.ORIGSubForm;
import com.eaf.orig.ulo.app.util.GenerateUnique;
import com.eaf.orig.ulo.app.view.util.dih.DIHProxy;
import com.eaf.orig.ulo.app.view.util.dih.model.DIHQueryResult;
import com.eaf.orig.ulo.model.app.ApplicationDataM;
import com.eaf.orig.ulo.model.app.PersonalApplicationInfoDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;
import com.eaf.orig.ulo.model.app.SpecialAdditionalServiceDataM;

@SuppressWarnings("serial")
public class OtherInfoSubForm extends ORIGSubForm{
	private static transient Logger logger = Logger.getLogger(OtherInfoSubForm.class);
	String NORMAL_APPLICATION_FORM = SystemConstant.getConstant("NORMAL_APPLICATION_FORM");
	String SUP_CARD_SEPARATELY_FORM = SystemConstant.getConstant("SUP_CARD_SEPARATELY_FORM");
	String PRODUCT_CRADIT_CARD = SystemConstant.getConstant("PRODUCT_CRADIT_CARD");
	String SPECIAL_ADDITIONAL_SERVICE_ATM = SystemConstant.getConstant("SPECIAL_ADDITIONAL_SERVICE_ATM");
	String INFO_IS_CORRECT = SystemConstant.getConstant("INFO_IS_CORRECT");
	String APPLICATION_TYPE_ADD_SUP = SystemConstant.getConstant("APPLICATION_TYPE_ADD_SUP");
	@Override
	public void setProperties(HttpServletRequest request, Object appForm) {		
		PersonalApplicationInfoDataM personalApplicationInfo = (PersonalApplicationInfoDataM)appForm;
		PersonalInfoDataM personalSup =	personalApplicationInfo.getPersonalInfo();
		String personalId = personalApplicationInfo.getPersonalId();	
		String applicationType = personalApplicationInfo.getApplicationType();
		logger.debug("personalId >> "+personalId);
		logger.debug("applicationType >> "+applicationType);
//		String TAG_SMART_DATA_PERSONAL = FormatUtil.getSmartDataEntryId(personalSup.getPersonalType(),personalSup.getSeq());
//		String TAG_SMART_DATA_PRODUCT = FormatUtil.getSmartDataEntryId(PRODUCT_CRADIT_CARD,TAG_SMART_DATA_PERSONAL);
		
		String productElementId = FormatUtil.getProductElementId(personalSup, PRODUCT_CRADIT_CARD);
		
		DIHProxy dihService = new DIHProxy();
		
		String savingAccNo = FormatUtil.removeDash(request.getParameter("SAVING_ACC_NO_"+productElementId));
		String COMPLETE_DATA_SAVING = request.getParameter("COMPLETE_DATA_SAVING_"+productElementId);
		String currentAccNo = FormatUtil.removeDash(request.getParameter("CURRENT_ACC_NO_"+productElementId));
		String COMPLETE_DATA_CURRENT = request.getParameter("COMPLETE_DATA_CURRENT_"+productElementId);
		
		DIHQueryResult<String> savingAccNameResult = dihService.getAccountName(savingAccNo);
		String savingAccName = savingAccNameResult.getResult();
		DIHQueryResult<String> currentAccNameResult = dihService.getAccountName(currentAccNo);
		String currentAccName = currentAccNameResult.getResult();
		
		if(Util.empty(COMPLETE_DATA_SAVING)) {
			COMPLETE_DATA_SAVING = INFO_IS_CORRECT;
		}
		if(Util.empty(COMPLETE_DATA_CURRENT)) {
			COMPLETE_DATA_CURRENT = INFO_IS_CORRECT;
		}
//		logger.debug("TAG_SMART_DATA_PERSONAL >> "+TAG_SMART_DATA_PRODUCT);
		logger.debug("savingAccNo :"+savingAccNo);
		logger.debug("savingAccName :"+savingAccName);
		logger.debug("COMPLETE_DATA_SAVING :"+COMPLETE_DATA_SAVING);
		logger.debug("currentAccNo :"+currentAccNo);
		logger.debug("currentAccName :"+currentAccName);
		logger.debug("COMPLETE_DATA_CURRENT :"+COMPLETE_DATA_CURRENT);
		
		SpecialAdditionalServiceDataM specialAdditionalService = null;
		if(APPLICATION_TYPE_ADD_SUP.equals(applicationType)){
			specialAdditionalService = personalApplicationInfo.getSpecialAdditionalServiceLifeCycleByPersonalId(personalId, SPECIAL_ADDITIONAL_SERVICE_ATM);
		}else{
			specialAdditionalService = personalApplicationInfo.getSpecialAdditionalServiceLifeCycleByPersonalId(personalId
				, PRODUCT_CRADIT_CARD, SPECIAL_ADDITIONAL_SERVICE_ATM);
		}
		if(Util.empty(specialAdditionalService)){
			specialAdditionalService = new SpecialAdditionalServiceDataM();
			String serviceId= GenerateUnique.generate(OrigConstant.SeqNames.ORIG_ADDITIONAL_SERVICE_PK);
			specialAdditionalService.init(serviceId);
			specialAdditionalService.setPersonalId(personalId);
			specialAdditionalService.setServiceType(SPECIAL_ADDITIONAL_SERVICE_ATM);
			personalApplicationInfo.addSpecialAdditionalService(specialAdditionalService);
		}
		specialAdditionalService.setSavingAccNo(savingAccNo);
		specialAdditionalService.setSavingAccName(savingAccName);
		specialAdditionalService.setCurrentAccNo(currentAccNo);
		specialAdditionalService.setCurrentAccName(currentAccName);
		specialAdditionalService.setCompleteDataSaving(COMPLETE_DATA_SAVING);
		specialAdditionalService.setCompleteData(COMPLETE_DATA_CURRENT);
		
		ArrayList<ApplicationDataM> applications = personalApplicationInfo.filterApplicationLifeCycleByPersonalId(personalId,PRODUCT_CRADIT_CARD);
		if(!Util.empty(applications)){
			for(ApplicationDataM application :applications){
				application.addAdditionalServiceId(specialAdditionalService.getServiceId());
			}
		}
	}	

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public HashMap validateForm(HttpServletRequest request, Object appForm) {
		FormErrorUtil formError = new FormErrorUtil();
		String subformId = "KCC_OTHER_INFO_SUBFORM";
		logger.debug("subformId >> "+subformId);		
		PersonalApplicationInfoDataM personalApplicationInfo = (PersonalApplicationInfoDataM)appForm;
		String personalId = personalApplicationInfo.getPersonalId();
		SpecialAdditionalServiceDataM specialAdditionalService = personalApplicationInfo.getSpecialAdditionalServiceLifeCycleByPersonalId(personalId
				, PRODUCT_CRADIT_CARD, SPECIAL_ADDITIONAL_SERVICE_ATM);
		if(null == specialAdditionalService){
			specialAdditionalService = new SpecialAdditionalServiceDataM();
		}		
		formError.mandatory(subformId,"SAVING_ACC_NO",specialAdditionalService.getSavingAccNo(),request);
		formError.mandatory(subformId,"ACC_NAME",specialAdditionalService.getSavingAccName(),request);
		formError.mandatory(subformId,"CURRENT_ACC_NO",specialAdditionalService.getCurrentAccNo(),request);
		formError.mandatory(subformId,"ACC_NAME",specialAdditionalService.getCurrentAccName(),request);		
		return formError.getFormError();
	}
	@Override
	public boolean validateSubForm(HttpServletRequest request) {
		return false;
	}
}
