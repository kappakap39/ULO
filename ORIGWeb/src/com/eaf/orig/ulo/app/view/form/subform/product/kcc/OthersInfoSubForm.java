package com.eaf.orig.ulo.app.view.form.subform.product.kcc;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.FormatUtil;
import com.eaf.core.ulo.common.properties.CacheControl;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.FormErrorUtil;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.shared.view.form.ORIGSubForm;
import com.eaf.orig.ulo.app.util.GenerateUnique;
import com.eaf.orig.ulo.app.view.util.dih.DIHProxy;
import com.eaf.orig.ulo.app.view.util.dih.model.DIHQueryResult;
import com.eaf.orig.ulo.constant.MConstant;
import com.eaf.orig.ulo.model.app.ApplicationDataM;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;
import com.eaf.orig.ulo.model.app.SpecialAdditionalServiceDataM;

@SuppressWarnings("serial")
public class OthersInfoSubForm extends ORIGSubForm{
	private static transient Logger logger = Logger.getLogger(OthersInfoSubForm.class);
	String PRODUCT_CRADIT_CARD = SystemConstant.getConstant("PRODUCT_CRADIT_CARD");
	String PERSONAL_TYPE_APPLICANT= SystemConstant.getConstant("PERSONAL_TYPE_APPLICANT");
	String PERSONAL_RELATION_APPLICATION_LEVEL = SystemConstant.getConstant("PERSONAL_RELATION_APPLICATION_LEVEL");
	String SPECIAL_ADDITIONAL_SERVICE_ATM = SystemConstant.getConstant("SPECIAL_ADDITIONAL_SERVICE_ATM");
	String INFO_IS_CORRECT = SystemConstant.getConstant("INFO_IS_CORRECT");
	String SPECIAL_ADDITIONAL_SERVICE_DUE_ALERT = SystemConstant.getConstant("SPECIAL_ADDITIONAL_SERVICE_DUE_ALERT");
	String APPLICATION_TEMPLATE_CC_V2 = SystemConstant.getConstant("APPLICATION_TEMPLATE_CC_V2");
	@Override
	public void setProperties(HttpServletRequest request, Object appForm) {
		DIHProxy dihService = new DIHProxy();
		ApplicationGroupDataM applicationGroup = ((ApplicationGroupDataM)appForm);	
		PersonalInfoDataM personalInfo = applicationGroup.getPersonalInfo(PERSONAL_TYPE_APPLICANT);
		String personalId = personalInfo.getPersonalId();
		
//		String TAG_SMART_DATA_PERSONAL = FormatUtil.getSmartDataEntryId(personalInfo.getPersonalType(),personalInfo.getSeq());
//		String TAG_SMART_DATA_PRODUCT = FormatUtil.getSmartDataEntryId(PRODUCT_CRADIT_CARD,TAG_SMART_DATA_PERSONAL);	
		
		String productElementId = FormatUtil.getProductElementId(personalInfo, PRODUCT_CRADIT_CARD);
		
		String SAVING_ACC_NO = FormatUtil.removeDash(request.getParameter("SAVING_ACC_NO_"+productElementId));
		String CURRENT_ACC_NO = FormatUtil.removeDash(request.getParameter("CURRENT_ACC_NO_"+productElementId));
		String COMPLETE_DATA_SAVING = request.getParameter("COMPLETE_DATA_SAVING_"+productElementId);
		String COMPLETE_DATA_CURRENT = request.getParameter("COMPLETE_DATA_CURRENT_"+productElementId);		
		if(Util.empty(COMPLETE_DATA_SAVING)) {
			COMPLETE_DATA_SAVING = INFO_IS_CORRECT;
		}
		if(Util.empty(COMPLETE_DATA_CURRENT)) {
			COMPLETE_DATA_CURRENT = INFO_IS_CORRECT;
		}
		DIHQueryResult<String> savingAccNameResult = dihService.getAccountName(SAVING_ACC_NO);
		String SAVING_ACC_NAME = savingAccNameResult.getResult();
		DIHQueryResult<String> currentAccNameResult = dihService.getAccountName(CURRENT_ACC_NO);
		String CURRENT_ACC_NAME = currentAccNameResult.getResult();
		
		DIHQueryResult<String> queryAccountNoBrNoResult = dihService.getAccountInfo(CURRENT_ACC_NO,"DOMC_BR_NO");
		String crnBranchCode = queryAccountNoBrNoResult.getResult();
		
		queryAccountNoBrNoResult = dihService.getAccountInfo(SAVING_ACC_NO,"DOMC_BR_NO");
		String svgBranchCode = queryAccountNoBrNoResult.getResult();
		
		logger.debug("productElementId >> "+productElementId);
		logger.debug("SAVING_ACC_NO >> "+SAVING_ACC_NO);
		logger.debug("SAVING_ACC_NAME >> "+SAVING_ACC_NAME);
		logger.debug("CURRENT_ACC_NO >> "+CURRENT_ACC_NO);
		logger.debug("CURRENT_ACC_NAME >> "+CURRENT_ACC_NAME);
		logger.debug("COMPLETE_DATA_SAVING >> "+COMPLETE_DATA_SAVING);
		logger.debug("COMPLETE_DATA_CURRENT >> "+COMPLETE_DATA_CURRENT);	
		
		SpecialAdditionalServiceDataM specialAdditionalService = applicationGroup.getSpecialAdditionalServiceLifeCycleByPersonalId(personalId
				, PRODUCT_CRADIT_CARD, SPECIAL_ADDITIONAL_SERVICE_ATM);
		if(Util.empty(specialAdditionalService)){
			specialAdditionalService = new SpecialAdditionalServiceDataM();
			String serviceId= GenerateUnique.generate(OrigConstant.SeqNames.ORIG_ADDITIONAL_SERVICE_PK);
			specialAdditionalService.init(serviceId);
			specialAdditionalService.setPersonalId(personalId);
			specialAdditionalService.setServiceType(SPECIAL_ADDITIONAL_SERVICE_ATM);
			applicationGroup.addSpecialAdditionalService(specialAdditionalService);
		}
		specialAdditionalService.setSavingAccNo(SAVING_ACC_NO);
		specialAdditionalService.setSavingAccName(SAVING_ACC_NAME);
		specialAdditionalService.setCurrentAccNo(CURRENT_ACC_NO);
		specialAdditionalService.setCurrentAccName(CURRENT_ACC_NAME);
		specialAdditionalService.setCompleteDataSaving(COMPLETE_DATA_SAVING);
		if(!Util.empty(crnBranchCode) && !Util.empty(svgBranchCode) && svgBranchCode.equals(crnBranchCode)){
			specialAdditionalService.setCompleteData(SPECIAL_ADDITIONAL_SERVICE_DUE_ALERT);	
		}else{
			specialAdditionalService.setCompleteData(COMPLETE_DATA_CURRENT);		
		}
		ArrayList<ApplicationDataM> applications = applicationGroup.filterApplicationLifeCycleByPersonalId(personalId,PRODUCT_CRADIT_CARD);
		if(!Util.empty(applications)){
			for(ApplicationDataM applicationItem :applications){
				applicationItem.addAdditionalServiceId(specialAdditionalService.getServiceId());
			}
		}
	}	

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public HashMap validateForm(HttpServletRequest request, Object appForm) {
		FormErrorUtil formError = new FormErrorUtil();
		String subformId = "KCC_OTHER_INFO_SUBFORM";
		logger.debug("subformId >> "+subformId);		
		ApplicationGroupDataM applicationGroup = ((ApplicationGroupDataM)appForm);	
		PersonalInfoDataM personalInfo = applicationGroup.getPersonalInfo(PERSONAL_TYPE_APPLICANT);
		String personalId = personalInfo.getPersonalId();
		SpecialAdditionalServiceDataM specialAdditionalService = applicationGroup.getSpecialAdditionalServiceLifeCycleByPersonalId(personalId
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
	@Override
	public String renderSubformFlag(HttpServletRequest request, Object objectForm) {
		logger.debug("Render subform other info");
		ApplicationGroupDataM applicationGroup = (ApplicationGroupDataM) objectForm;
		if (null == applicationGroup) {
			logger.warn("Render Subform Flag : Can't get ApplicationGroup ObjectForm : return detault");
			return super.renderSubformFlag(request, objectForm);
		}
		String applicationTemplate = applicationGroup.getApplicationTemplate();
		if (null == applicationTemplate) {
			logger.error("Render Subform Flag : Application HAVE NO TEMPLATE !!! : return detault");
			return super.renderSubformFlag(request, objectForm);
		}
		String CACHE_TEMPLATE = SystemConstant.getConstant("CACHE_TEMPLATE");
		String bunding = CacheControl.getName(CACHE_TEMPLATE, applicationTemplate, "BUNDING");
//		if(Util.empty(bunding)){
//			logger.debug("Show ! no bunding");
//			return MConstant.FLAG_Y;
//		}else{
//			logger.debug("Hide ! found bunding");
//			return MConstant.FLAG_N;
//		}
		
		if(APPLICATION_TEMPLATE_CC_V2.equals(applicationTemplate))
		{
			return MConstant.FLAG_N;
		}
		
		if (Util.empty(bunding) || (!Util.empty(bunding) && !"KL".equals(bunding))) {
			logger.debug("Show !");
			return MConstant.FLAG_Y;
		} else {
			logger.debug("Hide ! found KL bunding");
			return MConstant.FLAG_N;
		}
	}	
}
