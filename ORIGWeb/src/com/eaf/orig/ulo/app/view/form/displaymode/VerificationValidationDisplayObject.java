package com.eaf.orig.ulo.app.view.form.displaymode;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.FormDisplayObjectHelper;
import com.eaf.core.ulo.common.display.HtmlUtil;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.SessionControl;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.ulo.app.view.form.verification.VerifyUtil;
import com.eaf.orig.ulo.control.util.PersonalInfoUtil;
import com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;
import com.eaf.orig.ulo.model.app.VerificationResultDataM;
import com.eaf.orig.ulo.model.control.FlowControlDataM;

public class VerificationValidationDisplayObject extends FormDisplayObjectHelper{
	private static transient Logger logger = Logger.getLogger(VerificationValidationDisplayObject.class);
	String VALIDATION_STATUS_WAIVED = SystemConstant.getConstant("VALIDATION_STATUS_WAIVED");
	String VERIFY_PRIVILEGE_BTN = "VERIFY_PRIVILEGE_BTN";
	String VERIFY_INCOME_BTN = "VERIFY_INCOME_BTN";
	String VERIFY_WEBSITE_BTN = "VERIFY_WEBSITE_BTN";
	String VERIFY_HR_BTN = "VERIFY_HR_BTN";
	String VERIFY_CUSTOMER_BTN = "VERIFY_CUSTOMER_BTN";
	
	@Override
	public String getObjectFormMode(String objectId, String objectType, Object objectElement) {
		String DISPLAY_MODE = HtmlUtil.EDIT;
		try{
			FlowControlDataM flowControl = (FlowControlDataM) request.getSession().getAttribute(SessionControl.FlowControl);
			String actionType = flowControl.getActionType();
			ApplicationGroupDataM applicationGroup = ORIGFormHandler.getObjectForm(request);
			PersonalInfoDataM personalInfo = PersonalInfoUtil.getApplicationTypePersonalInfo(applicationGroup);
			if(Util.empty(personalInfo)){
				personalInfo = new PersonalInfoDataM();
			}
			VerificationResultDataM verificationResult = personalInfo.getVerificationResult();
			if(Util.empty(verificationResult)){
				verificationResult = new VerificationResultDataM();
			}			
			String RESULT_CODE = getResultCode(verificationResult, applicationGroup);
			logger.debug("objectId>>>>>>>>>>>>>"+objectId);
			logger.debug("RESULT_CODE>>>>>>>>>>>>>"+RESULT_CODE);
			if(SystemConstant.lookup("ACTION_TYPE_ENQUIRY", actionType)){
				if(VALIDATION_STATUS_WAIVED.equals(RESULT_CODE)){
					DISPLAY_MODE = HtmlUtil.VIEW;
				}else if(Util.empty(RESULT_CODE)){
					DISPLAY_MODE = HtmlUtil.VIEW;
				}
			}else{
				if(null!=RESULT_CODE && RESULT_CODE.equals(SystemConstant.getConstant("VALIDATION_STATUS_WAIVED"))){
					DISPLAY_MODE= HtmlUtil.VIEW;
				}
				
				if(VERIFY_INCOME_BTN.equals(objectId)){
					if(SystemConstant.lookup("JOB_STATE_EXCEPTION_VERIFY_INCOME",applicationGroup.getJobState())){
						DISPLAY_MODE =  HtmlUtil.VIEW;
					}
				}
			}
		}catch(Exception e){
			logger.fatal("ERROR ",e);
		}
		logger.debug("DISPLAY_MODE>>>>>>>>>>>>>"+DISPLAY_MODE);
		return DISPLAY_MODE;
	}
	
	public String getResultCode(VerificationResultDataM verificationResult,ApplicationGroupDataM applicationGroup){
		String resultCode = "";
		try{		
			logger.debug("objectId >> "+objectId);
			if(VERIFY_PRIVILEGE_BTN.equals(objectId)){
				resultCode = verificationResult.getVerPrivilegeResultCode();
			}else if(VERIFY_INCOME_BTN.equals(objectId)){
				resultCode = verificationResult.getSummaryIncomeResultCode();
			}else if(VERIFY_WEBSITE_BTN.equals(objectId)){
				resultCode = verificationResult.getVerWebResultCode();
			}else if(VERIFY_HR_BTN.equals(objectId)){
				resultCode = verificationResult.getVerHrResultCode();
			}else if(VERIFY_CUSTOMER_BTN.equals(objectId)){
				resultCode = VerifyUtil.VerifyResult.getVerifyCustomerResult(applicationGroup);
			}
		}catch(Exception e){
			logger.fatal("ERROR ",e);
		}
		logger.debug("resultCode >> "+resultCode);
		return resultCode;
	}
}
