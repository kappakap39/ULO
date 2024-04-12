package com.eaf.orig.ulo.app.view.form.process;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.FormatUtil;
import com.eaf.core.ulo.common.display.ProcessActionHelper;
import com.eaf.core.ulo.common.display.ValidateFormInf;
import com.eaf.core.ulo.common.message.MessageErrorUtil;
import com.eaf.core.ulo.common.model.ProcessActionResponse;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.DateValidateUtil;
import com.eaf.core.ulo.common.util.FormErrorUtil;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.im.rest.common.model.ServiceResponse;
import com.eaf.orig.ulo.app.view.util.ajax.ValidateSendToFu;
import com.eaf.orig.ulo.control.util.ApplicationUtil;
import com.eaf.orig.ulo.control.util.DocumentCheckListUtil;
import com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.PersonalApplicationInfoDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;

public class ProcessActionIA extends ProcessActionHelper{
	private static transient Logger logger = Logger.getLogger(ProcessActionIA.class);
	String BUTTON_ACTION_SUBMIT = SystemConstant.getConstant("BUTTON_ACTION_SUBMIT");
	String BUTTON_ACTION_SEND_TO_FU = SystemConstant.getConstant("BUTTON_ACTION_SEND_TO_FU");
	String PERSONAL_TYPE_APPLICANT = SystemConstant.getConstant("PERSONAL_TYPE_APPLICANT");
	String PERSONAL_TYPE_SUPPLEMENTARY= SystemConstant.getConstant("PERSONAL_TYPE_SUPPLEMENTARY");
	String PERSONAL_ERROR_DIH_FAILED = SystemConstant.getConstant("PERSONAL_ERROR_DIH_FAILED");
	String PERSONAL_ERROR_CIS_DUPLICATE = SystemConstant.getConstant("PERSONAL_ERROR_CIS_DUPLICATE");
	String APPLICATION_TYPE_INCREASE = SystemConstant.getConstant("APPLICATION_TYPE_INCREASE");
	String APPLICATION_TEMPLATE_CC_BUNDLE_SME = SystemConstant.getConstant("APPLICATION_TEMPLATE_CC_BUNDLE_SME");
	String APPLICATION_TEMPLATE_KEC_BUNDLE_SME = SystemConstant.getConstant("APPLICATION_TEMPLATE_KEC_BUNDLE_SME");
	String APPLICATION_TEMPLATE_CC_KEC_BUNDLE_HL = SystemConstant.getConstant("APPLICATION_TEMPLATE_CC_KEC_BUNDLE_HL");
	
	@Override
	public HashMap<String, Object> validateAction(HttpServletRequest request,Object objectForm) {
		FormErrorUtil formError = new FormErrorUtil();
		String buttonAction = getButtonAction();
		logger.debug("buttonAction : "+buttonAction);
		try{
			if(BUTTON_ACTION_SUBMIT.equals(buttonAction)){
				ApplicationGroupDataM applicationGroup = (ApplicationGroupDataM)objectForm;
				ArrayList<PersonalInfoDataM> usingPersonalInfos = applicationGroup.getUsingPersonalInfo();
				boolean isReasonApplicantDocFollowUp = DocumentCheckListUtil.isReasonApplicantDocumentFollowUp(applicationGroup);
				boolean isReasonSupplementatyDocFollowUp = DocumentCheckListUtil.isReasonSupplementaryDocumentFollowUp(applicationGroup);
				if(isReasonApplicantDocFollowUp || isReasonSupplementatyDocFollowUp){					
					formError.error(MessageErrorUtil.getText("ERROR_DOCUMENT_COMPLETE_FLAG"));
				}
				logger.debug("usingPersonalInfos : "+usingPersonalInfos);
				if(!Util.empty(usingPersonalInfos)){
//					if(PersonalInfoUtil.isDuplicateCis(usingPersonalInfos)){
//						formError.error(MessageErrorUtil.getText("ERROR_CIS_MORE_THAN_1_ROW"));
//					}
					// DEFECT 5375
					for(PersonalInfoDataM personalInfo : usingPersonalInfos){
						String applicationType = applicationGroup.getApplicationType();
						String PERSONAL_ERROR = personalInfo.getPersonalError();
						String personaltype = personalInfo.getPersonalType();
						Date sysDate = new Date(System.currentTimeMillis());
						if(!Util.empty(PERSONAL_ERROR)){
							if(PERSONAL_ERROR_DIH_FAILED.equals(PERSONAL_ERROR)){
								if(APPLICATION_TYPE_INCREASE.equals(applicationType)){
									if(PERSONAL_TYPE_APPLICANT.equals(personaltype)){
										formError.error("ID_NO",MessageErrorUtil.getText("ERROR_DIH_CONNECTION_FAILED_APPLICANT"));
									}
								}else{
									if(PERSONAL_TYPE_APPLICANT.equals(personaltype)){
										formError.error(MessageErrorUtil.getText("ERROR_DIH_CONNECTION_FAILED_APPLICANT"));
									}else if(PERSONAL_TYPE_SUPPLEMENTARY.equals(personaltype)){
										formError.error(MessageErrorUtil.getText("ERROR_DIH_CONNECTION_FAILED_SUPPLEMENTARY")+personalInfo.getThName());
									}
								}
							}else if(PERSONAL_ERROR_CIS_DUPLICATE.equals(PERSONAL_ERROR)){
								formError.error(MessageErrorUtil.getText("ERROR_CIS_MORE_THAN_1_ROW"));
							}
							
						}
						//For CR0362 validate ncb subform
						if(!Util.empty(personalInfo.getConsentDate()) && (ApplicationUtil.getDiffDay(sysDate,personalInfo.getConsentDate()) > 90)){
							formError.error("DATE_CONSENT", MessageErrorUtil.getText(request,"ERROR_CONSENT_DATE_90"));
						}
						
						String applicationTemplate = applicationGroup.getApplicationTemplate();
						String idNoConsent = personalInfo.getIdNoConsent();
						String birthDateConsent = FormatUtil.toString(personalInfo.getBirthDateConsent());
						boolean isValidateAdditional = true;
						if(APPLICATION_TEMPLATE_CC_BUNDLE_SME.equals(applicationTemplate)
								||APPLICATION_TEMPLATE_KEC_BUNDLE_SME.equals(applicationTemplate)
								||APPLICATION_TEMPLATE_CC_KEC_BUNDLE_HL.equals(applicationTemplate)){
							isValidateAdditional = false;
						}
						else if("ADD".equals(applicationType)){
							isValidateAdditional = false;
						}
						else if(personalInfo!=null&&!PERSONAL_TYPE_APPLICANT.equals(personalInfo.getPersonalType()) && !"INC".equals(applicationType)){
							isValidateAdditional = false;
						}
						if(isValidateAdditional) {
							if(Util.empty(idNoConsent)) {
								formError.error("ID_NO_CONSENT", MessageErrorUtil.getText(request, "ERROR_ID_NO_PICTURE_IS_BLANK"));
							} else if(!idNoConsent.equals(personalInfo.getIdno())) {
								formError.error("ID_NO_CONSENT", MessageErrorUtil.getText(request, "ERROR_ID_NO_AND_ID_NO_CONSENT_NOT_MATCH"));
							}
							
							if(Util.empty(personalInfo.getConsentDate())) {
								formError.error("DATE_CONSENT", MessageErrorUtil.getText(request, "ERROR_DATE_CONSENT_IS_BLANK"));
							}
							
							if(Util.empty(birthDateConsent)) {
								formError.error("TH_BIRTH_DATE_CONSENT", MessageErrorUtil.getText(request, "ERROR_BIRTH_DATE_CONSENT_IS_BLANK"));
							} else if(!birthDateConsent.equals(FormatUtil.toString(personalInfo.getBirthDate()))) {
								formError.error("TH_BIRTH_DATE_CONSENT", MessageErrorUtil.getText(request, "ERROR_BIRTH_DATE_CONSENT_NOT_MATCH"));
							}
							
							if(Util.empty(personalInfo.getPlaceConsent())) {
								formError.error("PLACE_CONSENT", MessageErrorUtil.getText(request, "ERROR_PLACE_CONSENT_IS_BLANK"));
							}
							if(Util.empty(personalInfo.getWitnessConsent())) {
								formError.error("WITNESS_CONSENT", MessageErrorUtil.getText(request, "ERROR_WITNESS_CONSENT_IS_BLANK"));
							}
							if("N".equals(personalInfo.getPlaceConsent()) || "N".equals(personalInfo.getWitnessConsent())){					
								formError.error(MessageErrorUtil.getText("ERROR_PLACE_WITNESS_CONSENT"));
							}
						}
					}
					
					
				}
			}else if(BUTTON_ACTION_SEND_TO_FU.equals(buttonAction)){
				ApplicationGroupDataM applicationGroup = (ApplicationGroupDataM)objectForm;
				ValidateSendToFu validateSendToFU = new ValidateSendToFu();
				String reasonErrorMsg = validateSendToFU.validateReasonCheckList(request, applicationGroup);
				if(!Util.empty(reasonErrorMsg)){
					formError.error(reasonErrorMsg);
				}
			}
		}catch(Exception e){
			logger.fatal("ERROR",e);
		}
		return formError.getFormError();
	}
	@Override
	public Object preProcessAction() {
		return null;
	}
	@Override
	public Object processAction() {
	 	ProcessActionResponse processActionResponse = new ProcessActionResponse();
		try{
			processActionResponse.setResultCode(ServiceResponse.Status.SUCCESS);
		}catch(Exception e){
			logger.fatal("ERROR",e);
			processActionResponse.setResultCode(ServiceResponse.Status.SYSTEM_EXCEPTION);
			processActionResponse.setResultDesc(e.getLocalizedMessage());
		}
		return processActionResponse;
	}
	@Override
	public Object postProcessAction() {
		return null;
	}
}
