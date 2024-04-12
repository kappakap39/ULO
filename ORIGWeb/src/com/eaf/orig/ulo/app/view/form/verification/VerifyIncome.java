package com.eaf.orig.ulo.app.view.form.verification;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.ElementHelper;
import com.eaf.core.ulo.common.display.ElementInf;
import com.eaf.core.ulo.common.display.HtmlUtil;
import com.eaf.core.ulo.common.properties.CacheControl;
import com.eaf.core.ulo.common.properties.FormControl;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.FormEffects;
import com.eaf.core.ulo.common.util.FormErrorUtil;
import com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.VerificationResultDataM;

public class VerifyIncome extends ElementHelper implements ElementInf {
	private static transient Logger logger = Logger.getLogger(VerifyIncome.class);	
	private String VALIDATION_STATUS_REQUIRED =SystemConstant.getConstant("VALIDATION_STATUS_REQUIRED");
	private String subformId = "VERIFICATION_VALIDATION_SUBFROM";
	@Override
	public String getElement(HttpServletRequest request, Object objectElement){
		VerificationResultDataM verificationResult = (VerificationResultDataM)objectElement;
		if(null == verificationResult){
			verificationResult = new VerificationResultDataM();
		}
		ORIGFormHandler ORIGForm = (ORIGFormHandler)request.getSession().getAttribute(ORIGFormHandler.ORIGForm);
		ApplicationGroupDataM applicationGroup = ORIGForm.getObjectForm();
		FormEffects formEffect = new FormEffects(subformId,request);
		String roleId = FormControl.getFormRoleId(request);
		logger.debug("roleId : "+roleId);
		String verifyIncomeResultCode = VerifyUtil.VerifyResult.getVerifyIncomeResult(applicationGroup, roleId);
		String VerifyIncomeResultDesc = CacheControl.getName(SystemConstant.getConstant("FIELD_ID_DATA_VALIDATION_STATUS"), verifyIncomeResultCode,"DISPLAY_NAME");	
		verificationResult.setSummaryIncomeResultCode(verifyIncomeResultCode);
		verificationResult.setSummaryIncomeResult(VerifyIncomeResultDesc);
		String styleClass = CacheControl.getName(SystemConstant.getConstant("FIELD_ID_DATA_VALIDATION_STATUS"),verifyIncomeResultCode, "SYSTEM_ID1");
		logger.debug("verifyIncomeResultCode : "+verifyIncomeResultCode);
		logger.debug("VerifyIncomeResultDesc : "+VerifyIncomeResultDesc);
		StringBuilder htmlTag = new StringBuilder();
		 htmlTag.append("<div class='col-md-12'>");	
		 htmlTag.append("	<div class='form-group'>");	
		 htmlTag.append("		<div class='col-sm-5 col-md-4'>"+HtmlUtil.button("VERIFY_INCOME_BTN", "", "VERIFY_INCOME_BTN", "VERIFY_INCOME_BTN", "btn btn-default btn-block", HtmlUtil.elementTagId("VERIFY_INCOME_BTN"), formEffect)+"</div>");	
		 htmlTag.append(			HtmlUtil.textBox("VERIFY_INCOME_STATUS","", VerifyIncomeResultDesc ,styleClass,"",HtmlUtil.VIEW,HtmlUtil.elementTagId("VERIFY_INCOME_STATUS"),"col-sm-4 col-md-4", request));	
		 htmlTag.append("	</div>");
		 htmlTag.append("</div>");				
		return htmlTag.toString();
	}
	
	@Override
	public HashMap<String,Object> validateElement(HttpServletRequest request, Object objectElement) {	
		String subformId ="VERIFICATION_VALIDATION_SUBFROM";		
		FormErrorUtil formError = new FormErrorUtil();
		ORIGFormHandler ORIGForm = (ORIGFormHandler)request.getSession().getAttribute(ORIGFormHandler.ORIGForm);
		ApplicationGroupDataM applicationGroup = ORIGForm.getObjectForm();
		logger.debug("applicationGroup.getJobState : "+applicationGroup.getJobState());
		if(!SystemConstant.lookup("JOB_STATE_EXCEPTION_VERIFY_INCOME",applicationGroup.getJobState())){
			VerificationResultDataM verificationResult = (VerificationResultDataM)objectElement;
			if(null == verificationResult){
				verificationResult = new VerificationResultDataM();
			}
			String verifyIncomeResultCode = verificationResult.getSummaryIncomeResultCode();
			String ERROR_TYPE = VerifyUtil.validationVerification(applicationGroup);
			logger.debug("verifyIncomeResultCode : "+verifyIncomeResultCode);
			logger.debug("ERROR_TYPE : "+ERROR_TYPE);
			if(VALIDATION_STATUS_REQUIRED.equals(verifyIncomeResultCode) && VerifyUtil.ERROR_TYPE_REQUIRED_ALL_VERIFICATION.equals(ERROR_TYPE)){
				formError.mandatory(subformId, "VERIFY_INCOME_BTN","", request);
			}
		}
		return formError.getFormError();
	}
}
