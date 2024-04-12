package com.eaf.orig.ulo.app.view.form.verification;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.ElementHelper;
import com.eaf.core.ulo.common.display.ElementInf;
import com.eaf.core.ulo.common.display.HtmlUtil;
import com.eaf.core.ulo.common.properties.CacheControl;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.FormEffects;
import com.eaf.core.ulo.common.util.FormErrorUtil;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.VerificationResultDataM;

public class VerifyWebsite extends ElementHelper implements ElementInf{
	private static transient Logger logger = Logger.getLogger(VerifyWebsite.class);
	private	String subformId ="VERIFICATION_VALIDATION_SUBFROM";
	private	String VALIDATION_STATUS_REQUIRED = SystemConstant.getConstant("VALIDATION_STATUS_REQUIRED");
	@Override
	public String getElement(HttpServletRequest request, Object objectElement) {	
		VerificationResultDataM verificationResult = (VerificationResultDataM)objectElement;
		if(null == verificationResult){
			verificationResult = new VerificationResultDataM();
		}
		String resultCode= verificationResult.getVerWebResultCode();
		String styleClass=CacheControl.getName(SystemConstant.getConstant("FIELD_ID_DATA_VALIDATION_STATUS"),resultCode, "SYSTEM_ID1");
		FormEffects formEffect = new FormEffects(subformId, request);
		StringBuilder htmlTag = new StringBuilder();
		htmlTag.append("<div class='col-md-12'>");
		htmlTag.append("	<div class='form-group'>");
		htmlTag.append("		<div class='col-sm-5 col-md-4'>"+HtmlUtil.button("VERIFY_WEBSITE_BTN", "", "VERIFY_WEBSITE_BTN", "VERIFY_WEBSITE_BTN", "btn btn-default btn-block", HtmlUtil.elementTagId("VERIFY_WEBSITE_BTN"), formEffect)+"</div>");
		htmlTag.append(				HtmlUtil.textBox("VERIFY_WEBSITE_STATUS", "",verificationResult.getVerWebResult(),styleClass, "", HtmlUtil.VIEW, HtmlUtil.elementTagId("VERIFY_WEBSITE_STATUS"), "col-sm-4 col-md-4", request));     		      
		htmlTag.append("	</div>");
		htmlTag.append("</div>");				
		return htmlTag.toString();
	}	
	@Override
	public HashMap<String,Object> validateElement(HttpServletRequest request, Object objectElement) {
		FormErrorUtil formError = new FormErrorUtil();
		VerificationResultDataM verificationResult = (VerificationResultDataM)objectElement;
		if(null == verificationResult){
			verificationResult = new VerificationResultDataM();
		}
		ApplicationGroupDataM applicationGroup = (ApplicationGroupDataM)getObjectRequest();
		String resultCode= verificationResult.getVerWebResultCode();
		String ERROR_TYPE= VerifyUtil.validationVerification(applicationGroup);
		String REQUIRED_ALL=VerifyUtil.ERROR_TYPE_REQUIRED_ALL_VERIFICATION;
		String REQUIRED_SOME=VerifyUtil.ERROR_TYPE_REQUIRED_SOME_VERIFICATION;
		if(VALIDATION_STATUS_REQUIRED.equals(resultCode) && (REQUIRED_ALL.equals(ERROR_TYPE) ||REQUIRED_SOME.equals(ERROR_TYPE))){
			formError.mandatory(subformId, "VERIFY_WEBSITE_BTN","", request);
		}
		return formError.getFormError();
	}
}
