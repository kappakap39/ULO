package com.eaf.orig.ulo.app.view.form.verifyhr;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import org.apache.commons.beanutils.BeanComparator;
import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.properties.CacheControl;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.j2ee.pattern.control.FormAction;
import com.eaf.j2ee.pattern.control.FormHelper;
import com.eaf.orig.ulo.constant.MConstant;
import com.eaf.orig.ulo.control.util.PersonalInfoUtil;
import com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.HRVerificationDataM;
import com.eaf.orig.ulo.model.app.IdentifyQuestionDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;
import com.eaf.orig.ulo.model.app.VerificationResultDataM;


public class VerifyhrForm  extends FormHelper implements FormAction {
	private static transient Logger logger = Logger.getLogger(VerifyhrForm.class);
	String FIELD_ID_DATA_VALIDATION_STATUS = SystemConstant.getConstant("FIELD_ID_DATA_VALIDATION_STATUS");
	String VALIDATION_STATUS_WAIT_FOR_NEXT_CONTACT = SystemConstant.getConstant("VALIDATION_STATUS_WAIT_FOR_NEXT_CONTACT");	
	String VALIDATION_STATUS_COMPLETED = SystemConstant.getConstant("VALIDATION_STATUS_COMPLETED");
	String VALIDATION_STATUS_COMPLETED_NOT_PASS = SystemConstant.getConstant("VALIDATION_STATUS_COMPLETED_NOT_PASS");
	String VER_HR_RESULT_TRUE = SystemConstant.getConstant("VER_HR_RESULT_TRUE");
	String VER_HR_RESULT_FALSE = SystemConstant.getConstant("VER_HR_RESULT_FALSE");
	String VER_HR_RESULT_RESIGN = SystemConstant.getConstant("VER_HR_RESULT_RESIGN");
	String VER_HR_RESULT_HR_NOT_DATA = SystemConstant.getConstant("VER_HR_RESULT_HR_NOT_DATA");
	String VER_HR_RESULT_QUESTION = SystemConstant.getConstant("VER_HR_RESULT_QUESTION");
	@Override
	public String processForm() {
		PersonalInfoDataM personalInfo = (PersonalInfoDataM)objectForm;	
		processVerifyHRResult(personalInfo);
//		#rawi:comment change logic update personal to ORIGForm session
//		int PERSONAL_SEQ = personalInfo.getSeq();
//		if(null != applicationGroup.getPersonalInfo(PERSONAL_SEQ)){
//			personalInfos.set(PERSONAL_SEQ-1,personalInfo);
//		}else{
//			personalInfos.add(personalInfo);
//		}
		ORIGFormHandler ORIGForm = (ORIGFormHandler)request.getSession().getAttribute("ORIGForm");
		ApplicationGroupDataM applicationGroup = ORIGForm.getObjectForm();
		ArrayList<PersonalInfoDataM> personalInfos = applicationGroup.getPersonalInfos();
		String personalId = personalInfo.getPersonalId();
		if(!Util.empty(personalId)){		
			int personalIndex = -1;
			try{
				personalIndex = applicationGroup.getPersonalIndex(personalId);
			} catch (Exception e) {
				logger.fatal("ERROR ", e);
			}
			logger.debug("personalId >> "+personalId);
			logger.debug("personalIndex >> "+personalIndex);
			if(personalIndex == -1){
				personalInfos.add(personalInfo);
			}else{
				personalInfos.set(personalIndex,personalInfo);
			}
		}
		return null;	
	}
	private void processVerifyHRResult(PersonalInfoDataM personalInfo){
		VerificationResultDataM verificationResult = personalInfo.getVerificationResult();		
		IdentifyQuestionDataM  identifyQuestion	= verificationResult.getIndentifyQuesitionNo(VER_HR_RESULT_QUESTION);
		String verHrResult = !Util.empty(identifyQuestion)?identifyQuestion.getCustomerAnswer():"";
		logger.debug("verHrResult >>>>>> "+verHrResult);
		if(!Util.empty(verificationResult)){
			ArrayList<HRVerificationDataM>	hrVerifications = verificationResult.getHrVerifications();
			if(!Util.empty(hrVerifications)){
				Comparator<HRVerificationDataM> comp = new BeanComparator("createDate");
				Collections.sort(hrVerifications,comp);
				if(!Util.empty(hrVerifications)){
					HRVerificationDataM hrVerification =	hrVerifications.get(hrVerifications.size()-1);
					 if(MConstant.FLAG_Y.equals(hrVerification.getPhoneVerStatus())){
						 if(VER_HR_RESULT_TRUE.equals(verHrResult)){
							 logger.debug("VER_HR_RESULT_TRUE >>> "+VER_HR_RESULT_TRUE);
							verificationResult.setVerHrResultCode(VALIDATION_STATUS_COMPLETED);
							verificationResult.setVerHrResult(CacheControl.getName(FIELD_ID_DATA_VALIDATION_STATUS, VALIDATION_STATUS_COMPLETED, "DISPLAY_NAME")); 
						 }else if(VER_HR_RESULT_FALSE.equals(verHrResult)){
							verificationResult.setVerHrResultCode(VALIDATION_STATUS_COMPLETED_NOT_PASS);
							verificationResult.setVerHrResult(CacheControl.getName(FIELD_ID_DATA_VALIDATION_STATUS, VALIDATION_STATUS_COMPLETED_NOT_PASS, "DISPLAY_NAME")); 
						 }else if(VER_HR_RESULT_RESIGN.equals(verHrResult)){
							verificationResult.setVerHrResultCode(VALIDATION_STATUS_COMPLETED_NOT_PASS);
							verificationResult.setVerHrResult(CacheControl.getName(FIELD_ID_DATA_VALIDATION_STATUS, VALIDATION_STATUS_COMPLETED_NOT_PASS, "DISPLAY_NAME")); 
						 }else if(VER_HR_RESULT_HR_NOT_DATA.equals(verHrResult)){
							verificationResult.setVerHrResultCode(VALIDATION_STATUS_COMPLETED_NOT_PASS);
							verificationResult.setVerHrResult(CacheControl.getName(FIELD_ID_DATA_VALIDATION_STATUS, VALIDATION_STATUS_COMPLETED_NOT_PASS, "DISPLAY_NAME"));
						 }
					 }else if(MConstant.FLAG_N.equals(hrVerification.getPhoneVerStatus())){
						 verificationResult.setVerHrResultCode(VALIDATION_STATUS_WAIT_FOR_NEXT_CONTACT);
						 verificationResult.setVerHrResult(CacheControl.getName(FIELD_ID_DATA_VALIDATION_STATUS, VALIDATION_STATUS_WAIT_FOR_NEXT_CONTACT, "DISPLAY_NAME"));
					 }
				}				 
			}
		}
	}
	@Override
	public Object getObjectForm() {
		ORIGFormHandler ORIGForm = (ORIGFormHandler)request.getSession().getAttribute("ORIGForm");
		ApplicationGroupDataM applicationGroup = ORIGForm.getObjectForm();
		PersonalInfoDataM personalInfo = PersonalInfoUtil.getApplicationTypePersonalInfo(applicationGroup);
		logger.debug(">>>personalInfo.getPersonalType>>>"+personalInfo.getPersonalType());
		logger.debug(">>>personalInfo.getPersonalId>>>"+personalInfo.getPersonalId());
		if(Util.empty(personalInfo)){
			personalInfo = new PersonalInfoDataM();
		}
		return personalInfo; 
	}
}
