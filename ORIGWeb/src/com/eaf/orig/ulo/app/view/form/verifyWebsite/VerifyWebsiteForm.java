package com.eaf.orig.ulo.app.view.form.verifyWebsite;

import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.properties.CacheControl;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.j2ee.pattern.control.FormAction;
import com.eaf.j2ee.pattern.control.FormHelper;
import com.eaf.orig.ulo.control.util.PersonalInfoUtil;
import com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;
import com.eaf.orig.ulo.model.app.VerificationResultDataM;
import com.eaf.orig.ulo.model.app.WebVerificationDataM;

public class VerifyWebsiteForm  extends FormHelper implements FormAction {
	private static transient Logger logger = Logger.getLogger(VerifyWebsiteForm.class);
	@Override
	public String processForm() {
		String SSO_WEBSITE_CODE = SystemConstant.getConstant("SSO_WEBSITE_CODE");
		String RD_WEBSITE_CODE = SystemConstant.getConstant("RD_WEBSITE_CODE");
		String WEBSITE_UNAVAILABLE = SystemConstant.getConstant("WEBSITE_UNAVAILABLE");
		String NHSO_WEBSITE_CODE = SystemConstant.getConstant("NHSO_WEBSITE_CODE");
		String FIELD_ID_DATA_VALIDATION_STATUS = SystemConstant.getConstant("FIELD_ID_DATA_VALIDATION_STATUS");
		String VALIDATION_STATUS_WAIT_FOR_RE_CHECK = SystemConstant.getConstant("VALIDATION_STATUS_WAIT_FOR_RE_CHECK");
		String VALIDATION_STATUS_COMPLETED = SystemConstant.getConstant("VALIDATION_STATUS_COMPLETED");
		ArrayList<String> WEBSITE_CODE_SSO = SystemConstant.getArrayListConstant("WEBSITE_CODE_SSO");
		
		ORIGFormHandler ORIGForm = (ORIGFormHandler)request.getSession().getAttribute(ORIGFormHandler.ORIGForm);
		ApplicationGroupDataM applicationGroup = ORIGForm.getObjectForm(); 
		PersonalInfoDataM personalInfo = PersonalInfoUtil.getApplicationTypePersonalInfo(applicationGroup);
		VerificationResultDataM verificationResultM = (VerificationResultDataM)objectForm;
		
		personalInfo.setVerificationResult(verificationResultM);
		String webResult = "C";
		String ssoResult = "N";
		String otherResult = "N";
		int webSiteGroup = 0;
		ArrayList<WebVerificationDataM> otherWebList = verificationResultM.getOtherWebVerifications(WEBSITE_CODE_SSO);
		
		WebVerificationDataM rdWebVerification = verificationResultM.getWebVerificationWebCode(RD_WEBSITE_CODE);
		
		if(!Util.empty(rdWebVerification) && (WEBSITE_UNAVAILABLE.equals(rdWebVerification.getVerResult()))){
			ssoResult = "U";
			webSiteGroup ++;
		}
		
		if(!Util.empty(otherWebList)){
			otherResult = "F";
			webSiteGroup++;
			for(WebVerificationDataM otherWeb: otherWebList){
				if(WEBSITE_UNAVAILABLE.equals(otherWeb.getVerResult())){
					otherResult = "U";
				}
			}
		}
		if(webSiteGroup > 0){
			if(webSiteGroup==1&&("U".equals(otherResult))){
				webResult = "W";
			}else{
				if(("U".equals(ssoResult)&&"U".equals(otherResult))){
					webResult = "W";
				}else if(!"U".equals(ssoResult)&&"U".equals(otherResult)){
					webResult = "W";
				}
			}
		}
		if("C".equals(webResult)){
			verificationResultM.setVerWebResult(CacheControl.getName(FIELD_ID_DATA_VALIDATION_STATUS, VALIDATION_STATUS_COMPLETED));
			verificationResultM.setVerWebResultCode(VALIDATION_STATUS_COMPLETED);
		}else{
			verificationResultM.setVerWebResult(CacheControl.getName(FIELD_ID_DATA_VALIDATION_STATUS, VALIDATION_STATUS_WAIT_FOR_RE_CHECK));
			verificationResultM.setVerWebResultCode(VALIDATION_STATUS_WAIT_FOR_RE_CHECK);
		}
		
		return null;
	}

	@Override
	public Object getObjectForm() {
		ORIGFormHandler ORIGForm = (ORIGFormHandler)request.getSession().getAttribute(ORIGFormHandler.ORIGForm);
		ApplicationGroupDataM applicationGroup =	ORIGForm.getObjectForm();
		logger.debug("applicationGroup >>>> "+applicationGroup);
		PersonalInfoDataM personalInfo = PersonalInfoUtil.getApplicationTypePersonalInfo(applicationGroup);
		VerificationResultDataM verificationResultM = personalInfo.getVerificationResult();
		if(verificationResultM == null) {
			verificationResultM = new VerificationResultDataM();
			personalInfo.setVerificationResult(verificationResultM);
		}
		return verificationResultM; 
	}
  
}
