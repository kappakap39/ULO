package com.eaf.orig.ulo.app.view.form.subform;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.ElementInf;
import com.eaf.core.ulo.common.properties.ImplementControl;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.FormErrorUtil;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.shared.view.form.ORIGSubForm;
import com.eaf.orig.ulo.constant.MConstant;
import com.eaf.orig.ulo.model.app.VerificationResultDataM;
import com.eaf.orig.ulo.model.app.WebVerificationDataM;

public class VerifyWebsiteSubForm extends ORIGSubForm{
	private static transient Logger logger = Logger.getLogger(VerifyWebsiteSubForm.class);
	String SSO_WEBSITE_CODE = SystemConstant.getConstant("SSO_WEBSITE_CODE");
	String RD_WEBSITE_CODE = SystemConstant.getConstant("RD_WEBSITE_CODE");
	String WEBSITE_UNAVAILABLE = SystemConstant.getConstant("WEBSITE_UNAVAILABLE");
	String NHSO_WEBSITE_CODE = SystemConstant.getConstant("NHSO_WEBSITE_CODE");
	String WEBSITE_SSO = SystemConstant.getConstant("WEBSITE_SSO");
	@Override
	public void setProperties(HttpServletRequest request, Object appForm) {
		VerificationResultDataM verificationResultM = (VerificationResultDataM)appForm;
		if(verificationResultM == null) {
			verificationResultM = new VerificationResultDataM();
		}
		ArrayList<WebVerificationDataM> webList = verificationResultM.getWebVerifications();
		if(webList == null) {
			webList = new ArrayList<WebVerificationDataM>();
			verificationResultM.setWebVerifications(webList);
		}
		if(!Util.empty(webList)) {
			for(WebVerificationDataM webM : webList) {
				ElementInf element = ImplementControl.getElement(MConstant.IMPLEMENT_TYPE.WEBSITE, webM.getWebCode());
				element.processElement(request, webM);
			}
		}
		processWebVerificationCondition(request,verificationResultM);
	}

	@Override
	public HashMap validateForm(HttpServletRequest request, Object appForm) {
		FormErrorUtil formError = new FormErrorUtil();
		VerificationResultDataM verificationResultM = (VerificationResultDataM)appForm;
		if(verificationResultM == null) {
			verificationResultM = new VerificationResultDataM();
		}
		ArrayList<WebVerificationDataM> webList = verificationResultM.getWebVerifications();
		if(webList == null) {
			webList = new ArrayList<WebVerificationDataM>();
			verificationResultM.setWebVerifications(webList);
		}
		if(!Util.empty(webList)) {
			for(WebVerificationDataM webM : webList) {
				ElementInf element = ImplementControl.getElement(MConstant.IMPLEMENT_TYPE.WEBSITE, webM.getWebCode());
				formError.addAllFilterErrMsg(element.validateElement(request, webM));
			}
		}
		return formError.getFormError();
	}

	@Override
	public boolean validateSubForm(HttpServletRequest request) {
		// TODO Auto-generated method stub
		return false;
	}
 
	public void processWebVerificationCondition(HttpServletRequest request, VerificationResultDataM verificationResultM){
		ArrayList<WebVerificationDataM> webList = verificationResultM.getWebVerifications();
		WebVerificationDataM ssoWebVerification = verificationResultM.getWebVerificationWebCode(SSO_WEBSITE_CODE);
		WebVerificationDataM rdWebVerification = verificationResultM.getWebVerificationWebCode(RD_WEBSITE_CODE);
		WebVerificationDataM nhsoWebVerification = verificationResultM.getWebVerificationWebCode(NHSO_WEBSITE_CODE);
		if(!Util.empty(ssoWebVerification)) {
			 if(WEBSITE_UNAVAILABLE.equals(ssoWebVerification.getVerResult())){
				  if(Util.empty(nhsoWebVerification)){
					  logger.debug(">>>>ADD NEW NHSO>>");
					  WebVerificationDataM  nhsoWebVerificationNew =  new WebVerificationDataM();
					  nhsoWebVerificationNew.setVerResultId(verificationResultM.getVerResultId());
					  nhsoWebVerificationNew.setWebCode(NHSO_WEBSITE_CODE);
					  webList.add(nhsoWebVerificationNew);
				  }
			 }else{
				 removeWebVerificationWebCode(webList,NHSO_WEBSITE_CODE);
				 removeWebVerificationWebCode(webList,RD_WEBSITE_CODE);
			 }
		}
		 if(!Util.empty(nhsoWebVerification)){
			 if(!Util.empty(nhsoWebVerification.getVerResult()) && SystemConstant.lookup("WEBSITE_RD_CONDITION", nhsoWebVerification.getVerResult()) ){
				  if(Util.empty(rdWebVerification)){
					  logger.debug(">>>>ADD NEW RD>>");
					  WebVerificationDataM  rdWebVerificationNew =  new WebVerificationDataM();
					  rdWebVerificationNew.setVerResultId(verificationResultM.getVerResultId());
					  rdWebVerificationNew.setWebCode(RD_WEBSITE_CODE);
					  webList.add(searchIndexFromKey(webList,NHSO_WEBSITE_CODE),rdWebVerificationNew);
				  }
			 }else{
				 removeWebVerificationWebCode(webList,RD_WEBSITE_CODE);
			 }
		 }
	}	
	
	private int searchIndexFromKey(ArrayList<WebVerificationDataM>webList,String key){
	int index = 0;
		for(WebVerificationDataM web : webList){
			index++;
			if(!Util.empty(key) && key.equals(web.getWebCode())){
				return index;
			}
		}
		if(!Util.empty(webList)){
			return webList.size()-1;
		}
		return index;
	}
	
	private  void removeWebVerificationWebCode(ArrayList<WebVerificationDataM> webVerifications ,String webCode){
		if(null!=webVerifications && null!=webCode){
			for(WebVerificationDataM webVerification : webVerifications){
				if(webVerification.getWebCode().equals(webCode)){
					webVerifications.remove(webVerification);
					logger.debug(">>>REMOVE>>>RD>>");
					break;
				}
			}
		}
	}
}
